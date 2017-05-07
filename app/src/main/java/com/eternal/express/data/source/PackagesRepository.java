package com.eternal.express.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

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