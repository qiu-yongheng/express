package com.eternal.express.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.eternal.express.data.bean.Package;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 将包从数据源加载到缓存中的具体实现。
 * 为了简单起见，这实现了本地持久数据和数据之间的哑同步
 * 从服务器获取，仅使用本地数据库的远程数据源
 * 不是最新的。
 * 个人理解是model层的封装, 所有数据的获取与缓存都在这里进行
 */

public class PackagesRepository implements PackagesDataSource {

    @Nullable
    private static PackagesRepository INSTANCE = null;

    @NonNull
    private final PackagesDataSource packagesRemoteDataSource;

    @NonNull
    private final PackagesDataSource packagesLocalDataSource;

    private Map<String, Package> cachedPackages;

    private Package cachePackage = null;

    // Prevent direct instantiation
    // 防止直接实例化
    private PackagesRepository(@NonNull PackagesDataSource packagesRemoteDataSource,
                               @NonNull PackagesDataSource packagesLocalDataSource) {
        this.packagesRemoteDataSource = packagesRemoteDataSource;
        this.packagesLocalDataSource = packagesLocalDataSource;
    }

    // The access for other classes.
    public static PackagesRepository getInstance(@NonNull PackagesDataSource packagesRemoteDataSource,
                                                 @NonNull PackagesDataSource packagesLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new PackagesRepository(packagesRemoteDataSource, packagesLocalDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     *  从本地数据库获取数据
     * @return
     */
    @Override
    public Observable<List<Package>> getPackages() {
        /** 如果已经从数据库获取过数据, 因为realm数据库是同步更新数据的, 我们只要重新根据时间戳更新排序就好了 */
        if (cachedPackages != null) {
            return Observable.fromCallable(new Callable<List<Package>>() {
                @Override
                public List<Package> call() throws Exception {
                    List<Package> arrayList = new ArrayList<Package>(cachedPackages.values());
                    // 按时间戳排序，使列表以下降方式显示
                    Collections.sort(arrayList, new Comparator<Package>() {
                        @Override
                        public int compare(Package o1, Package o2) {
                            if (o1.getTimestamp() > o2.getTimestamp()) {
                                return -1;
                            } else if (o1.getTimestamp() < o2.getTimestamp()) {
                                return 1;
                            }
                            return 0;
                        }
                    });
                    return arrayList;
                }
            });
        } else {

            cachedPackages = new LinkedHashMap<>();

            /** 从本地数据库获取数据, 保存到map集合中 */
            return packagesLocalDataSource
                    .getPackages()
                    .flatMap(new Function<List<Package>, ObservableSource<List<Package>>>() {
                        @Override
                        public ObservableSource<List<Package>> apply(List<Package> packages) throws Exception {
                            return Observable
                                    .fromIterable(packages)
                                    .doOnNext(new Consumer<Package>() {
                                        @Override
                                        public void accept(Package aPackage) throws Exception {
                                            cachedPackages.put(aPackage.getNumber(), aPackage);
                                        }
                                    })
                                    .toList()
                                    .toObservable();
                        }
                    });
        }
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