package com.eternal.express.data.source.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.eternal.express.data.source.PackagesDataSource;

import java.util.List;

import io.reactivex.Observable;

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