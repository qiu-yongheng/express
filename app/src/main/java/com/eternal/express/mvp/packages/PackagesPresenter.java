package com.eternal.express.mvp.packages;

import android.support.annotation.NonNull;

import com.eternal.express.data.source.PackagesRepository;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author 邱永恒
 * @time 2017/5/4 20:29
 * @desc ${TODO}
 */

public class PackagesPresenter implements PackagesContract.Presenter{
    private final PackagesRepository packagesRepository;
    private final PackagesContract.View view;
    @NonNull
    private PackageFilterType currentFiltering = PackageFilterType.ALL_PACKAGES;
    private final CompositeDisposable mCompositeDisposable;

    public PackagesPresenter(PackagesContract.View view, PackagesRepository packagesRepository) {
        this.view = view;
        this.packagesRepository = packagesRepository;
        mCompositeDisposable = new CompositeDisposable();
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void loadPackages() {

    }

    @Override
    public void refreshPackages() {

    }

    @Override
    public void markAllPacksRead() {

    }

    @Override
    public void setFiltering(@NonNull PackageFilterType requestType) {
        currentFiltering = requestType;
    }

    @Override
    public PackageFilterType getFiltering() {
        return null;
    }

    @Override
    public void setPackageReadable(@NonNull String packageId, boolean readable) {

    }

    @Override
    public void deletePackage(int position) {

    }

    @Override
    public void setShareData(@NonNull String packageId) {

    }

    @Override
    public void recoverPackage() {

    }
}
