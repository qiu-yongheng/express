package com.eternal.express.data.source.local;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.eternal.express.data.bean.Package;
import com.eternal.express.data.source.PackagesDataSource;
import com.eternal.express.realm.RealmHelper;

import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.Sort;


/**
 * Created by lizhaotailang on 2017/2/25.
 * 从数据库获取数据
 */

public class PackagesLocalDataSource implements PackagesDataSource {

    @Nullable
    private static PackagesLocalDataSource INSTANCE;

    // Prevent direct instantiation
    private PackagesLocalDataSource() {

    }

    // Access this instance for other classes.
    public static PackagesLocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PackagesLocalDataSource();
        }
        return INSTANCE;
    }

    // Destroy the instance.
    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * 获取数据库中的包，并按时间戳降序排序
     * @return
     */
    @Override
    public Observable<List<Package>> getPackages() {
        // 获取realm数据库
        Realm rlm = RealmHelper.newRealmInstance();

        return Observable.just(rlm.copyFromRealm(rlm.where(Package.class)
                .findAllSorted("timestamp", Sort.DESCENDING)));
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

    @Override
    public Observable<List<Package>> refreshPackages() {
        return null;
    }

    @Override
    public Observable<Package> refreshPackage(@NonNull String packageId) {
        return null;
    }

    @Override
    public void setAllPackagesRead() {

    }

    @Override
    public void setPackageReadable(@NonNull String packageId, boolean readable) {

    }

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