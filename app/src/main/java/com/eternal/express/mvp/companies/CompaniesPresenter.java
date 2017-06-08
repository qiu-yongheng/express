package com.eternal.express.mvp.companies;

import com.eternal.express.data.bean.Company;
import com.eternal.express.data.source.CompaniesRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 邱永恒
 * @time 2017/5/4 20:30
 * @desc ${TODO}
 */

public class CompaniesPresenter implements CompaniesContract.Presenter{
    private final CompaniesContract.View view;
    private final CompaniesRepository companiesRepository;
    private final CompositeDisposable compositeDisposable;

    public CompaniesPresenter(CompaniesContract.View view, CompaniesRepository companiesRepository) {
        this.view = view;
        this.companiesRepository = companiesRepository;
        // 任务容器
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    /**
     * 订阅
     */
    @Override
    public void subscribe() {
        getCompanies();
    }

    /**
     * 取消订阅
     */
    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    /**
     * 获取快递公司列表
     */
    private void getCompanies() {
        Disposable disposable = companiesRepository
                .getCompanies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Company>>() {
                    @Override
                    public void onNext(List<Company> value) {
                        view.showCompanies(value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        compositeDisposable.add(disposable);
    }
}
