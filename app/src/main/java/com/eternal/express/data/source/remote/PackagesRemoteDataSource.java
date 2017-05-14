package com.eternal.express.data.source.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.eternal.express.data.bean.Package;
import com.eternal.express.data.source.PackagesDataSource;
import com.eternal.express.retrofit.RetrofitClient;
import com.eternal.express.retrofit.RetrofitService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmConfiguration;

import static com.eternal.express.realm.RealmHelper.DATABASE_NAME;

/**
 * Created by 邱永恒
 * 从网络获取数据
 */

public class PackagesRemoteDataSource implements PackagesDataSource {

    @Nullable
    private static PackagesRemoteDataSource INSTANCE;

    // Prevent direct instantiation
    private PackagesRemoteDataSource() {

    }

    // Access this instance for outside classes.
    public static PackagesRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PackagesRemoteDataSource();
        }
        return INSTANCE;
    }

    // Destroy the instance.
    public static void destroyInstance() {
        INSTANCE = null;
    }


    @Override
    public Observable<List<Package>> getPackages() {
        return null;
    }

    @Override
    public Observable<Package> getPackage(@NonNull String packNumber) {
        return null;
    }

    @Override
    public void savePackage(@NonNull Package pack) {

    }

    @Override
    public void deletePackage(@NonNull String packageId) {

    }

    /**
     * 通过网络更新数据
     * @return
     */
    @Override
    public Observable<List<Package>> refreshPackages() {
        // It is necessary to build a new realm instance (有必要创建一个新的realm对象)
        // in a different thread. (在不同的线程)
        Realm realm = Realm.getInstance(new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .name(DATABASE_NAME)
                .build());

        return Observable.fromIterable(realm.copyFromRealm(realm.where(Package.class).findAll()))
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<Package, ObservableSource<Package>>() {
                    @Override
                    public ObservableSource<Package> apply(Package aPackage) throws Exception {
                        // A nested request. (单独请求每一个item)
                        return refreshPackage(aPackage.getNumber());
                    }
                })
                .toList()
                .toObservable();
    }

    /**
     * retrofit + rxjava 网络请求
     * 保存数据到数据库
     * @param packageId
     * @return
     */
    @Override
    public Observable<Package> refreshPackage(@NonNull String packageId) {
        // It is necessary to build a new realm instance (有必要创建一个新的realm对象)
        // in a different thread. (在不同的线程)
        Realm realm = Realm.getInstance(new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .name(DATABASE_NAME)
                .build());

        // 设置一个副本，而不是使用原始数据 ()
        final Package p = realm.copyFromRealm(realm.where(Package.class)
                .equalTo("number", packageId)
                .findFirst());

        // retrofit + rxjava 网络请求
        return RetrofitClient.getInstance()
                .create(RetrofitService.class) // 创建API对象
                .getPackageState(p.getCompany(), p.getNumber()) // 调用retrofit方法请求
                .filter(new Predicate<Package>() {
                    @Override
                    public boolean test(Package aPackage) throws Exception {
                        // 筛选更新日期最新的数据, 返回
                        return aPackage.getData() != null && aPackage.getData().size() > p.getData().size();
                    }
                })
                .subscribeOn(Schedulers.io()) // 在IO线程订阅
                .doOnNext(new Consumer<Package>() {
                    @Override
                    public void accept(Package aPackage) throws Exception {

                        // To avoid the server error or other problems
                        // making the data in database being dirty.
                        if (aPackage != null && aPackage.getData() != null) {
                            // It is necessary to build a new realm instance
                            // in a different thread.
                            Realm rlm = Realm.getInstance(new RealmConfiguration.Builder()
                                    .deleteRealmIfMigrationNeeded()
                                    .name(DATABASE_NAME)
                                    .build());

                            // Only when the origin data is null or the origin
                            // data's size is less than the latest data's size
                            // set the package unread new(readable = true). 设置未读
                            if (p.getData() == null || aPackage.getData().size() > p.getData().size()) {
                                p.setReadable(true);
                                p.setPushable(true);
                                p.setState(aPackage.getState());
                            }

                            p.setData(aPackage.getData());
                            // DO NOT forget to begin a transaction. 不要忘记开启事务
                            rlm.beginTransaction();
                            rlm.copyToRealmOrUpdate(p);
                            // 提交事务
                            rlm.commitTransaction();

                            // 关闭数据库
                            rlm.close();
                        }
                    }
                });
    }

    @Override
    public void setAllPackagesRead() {

    }

    @Override
    public void setPackageReadable(@NonNull String packageId, boolean readable) {

    }

    /**
     *
     * @param packageId
     * @return
     */
    @Override
    public boolean isPackageExist(@NonNull String packageId) {
        return false;
    }

    @Override
    public void updatePackageName(@NonNull String packageId, @NonNull String name) {

    }

    @Override
    public Observable<List<Package>> searchPackages(@NonNull String keyWords) {
        return null;
    }
}