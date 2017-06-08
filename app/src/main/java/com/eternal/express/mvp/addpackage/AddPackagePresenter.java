package com.eternal.express.mvp.addpackage;

import android.support.annotation.NonNull;

import com.eternal.express.data.source.CompaniesDataSource;
import com.eternal.express.data.source.PackagesDataSource;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author 邱永恒
 * @time 2017/6/7 20:46
 * @desc ${TODO}
 */

public class AddPackagePresenter implements AddPackageContract.Presenter{
    private final AddPackageContract.View view;
    private final PackagesDataSource packagesDataSource;
    private final CompaniesDataSource companiesDataSource;
    private final CompositeDisposable compositeDisposable;

    public AddPackagePresenter(@NonNull PackagesDataSource dataSource,
                               @NonNull CompaniesDataSource companiesDataSource,
                               @NonNull AddPackageContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
        this.packagesDataSource = dataSource;
        this.companiesDataSource = companiesDataSource;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
