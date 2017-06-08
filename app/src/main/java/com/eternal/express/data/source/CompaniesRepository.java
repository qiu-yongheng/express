package com.eternal.express.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.eternal.express.data.bean.Company;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author qiuyongheng
 * @time 2017/6/5  14:03
 * @desc 获取快递公司列表
 */

public class CompaniesRepository implements CompaniesDataSource{
    @Nullable
    private static CompaniesRepository INSTANCE = null;
    @Nullable
    private final CompaniesDataSource localDataSource;

    // Prevent direct instantiation
    private CompaniesRepository(@NonNull CompaniesDataSource localDataSource) {
        this.localDataSource = localDataSource;
    }

    public static CompaniesRepository getInstance(@NonNull CompaniesDataSource localDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new CompaniesRepository(localDataSource);
        }
        return INSTANCE;
    }

    /**
     * 获取所有快递公司列表
     * @return
     */
    @Override
    public Observable<List<Company>> getCompanies() {
        return localDataSource.getCompanies();
    }

    /**
     * 根据公司id获取公司
     * @param companyId
     * @return
     */
    @Override
    public Observable<Company> getCompany(@NonNull String companyId) {
        return localDataSource.getCompany(companyId);
    }

    /**
     * 手动在数据库中录入数据
     */
    @Override
    public void initData() {
        localDataSource.initData();
    }

    /**
     * 搜索公司
     * @param keyWords
     * @return
     */
    @Override
    public Observable<List<Company>> searchCompanies(@NonNull String keyWords) {
        return localDataSource.searchCompanies(keyWords);
    }
}
