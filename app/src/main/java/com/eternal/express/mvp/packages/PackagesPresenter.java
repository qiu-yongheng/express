package com.eternal.express.mvp.packages;

import android.support.annotation.NonNull;

import com.eternal.express.data.bean.Package;
import com.eternal.express.data.source.PackagesRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

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
        loadPackages();
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    /**
     * 创建读取数据的任务, 添加到RX队列中
     * 从数据库获取本地数据(不通过网络)
     */
    @Override
    public void loadPackages() {
        // 清除之前的任务
        mCompositeDisposable.clear();
        /** 从数据库获取数据 */
        Disposable disposable = packagesRepository
                .getPackages() // 获取数据库中的数据
                .flatMap(new Function<List<Package>, ObservableSource<Package>>() { // 遍历
                    @Override
                    public ObservableSource<Package> apply(List<Package> packages) throws Exception {
                        return Observable.fromIterable(packages);
                    }
                })
                .filter(new Predicate<Package>() { // 过滤
                    @Override
                    public boolean test(Package aPackage) throws Exception {
                        // 获取包裹快递状态
                        int state = Integer.parseInt(aPackage.getState());
                        // 根据当前选中的界面, 显示对应状态的数据
                        switch (currentFiltering) {
                            case ON_THE_WAY_PACKAGES:
                                return state != Package.STATUS_DELIVERED;
                            case DELIVERED_PACKAGES:
                                return state == Package.STATUS_DELIVERED;
                            case ALL_PACKAGES:
                                return true;
                            default:
                                return true;
                        }
                    }
                })
                .toList()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Package>>() {
                    @Override
                    public void onNext(List<Package> value) {
                        // 回调从数据库获取到的packages数据
                        view.showPackages(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // 显示空界面
                        view.showEmptyView(true);
                        // 停止显示加载
                        view.setLoadingIndicator(false);
                    }

                    @Override
                    public void onComplete() {
                        // 停止显示加载
                        view.setLoadingIndicator(false);
                    }
                });

        // 添加
        mCompositeDisposable.add(disposable);
    }

    /**
     * 通过网络来更新packages数据
     * 网络获取数据后, 保存到本地
     * 调用loadPackages()方法获取本地数据库数据, 回调给fragment
     */
    @Override
    public void refreshPackages() {
        Disposable disposable = packagesRepository
                .refreshPackages() // 从网络获取数据, 并保存到数据库
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Package>>() {
                    @Override
                    public void onNext(List<Package> value) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.setLoadingIndicator(false);
                        view.showNetworkError();
                    }

                    @Override
                    public void onComplete() {
                        view.setLoadingIndicator(false);
                        loadPackages();
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    /**
     * 设置所有信息已读
     */
    @Override
    public void markAllPacksRead() {
        packagesRepository.setAllPackagesRead();
        loadPackages();
    }

    /**
     * 设置当前选中的item
     * @param requestType
     */
    @Override
    public void setFiltering(@NonNull PackageFilterType requestType) {
        currentFiltering = requestType;
    }

    @Override
    public PackageFilterType getFiltering() {
        return currentFiltering;
    }

    /**
     * 设置指定信息已读或未读
     * @param packageId
     * @param readable
     */
    @Override
    public void setPackageReadable(@NonNull String packageId, boolean readable) {
        packagesRepository.setPackageReadable(packageId, readable);
        loadPackages();
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
