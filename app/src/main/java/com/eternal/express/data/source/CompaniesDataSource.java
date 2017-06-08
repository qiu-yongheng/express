package com.eternal.express.data.source;

import android.support.annotation.NonNull;

import com.eternal.express.data.bean.Company;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author qiuyongheng
 * @time 2017/6/5  14:00
 * @desc
 */

public interface CompaniesDataSource {
    Observable<List<Company>> getCompanies();

    Observable<Company> getCompany(@NonNull String companyId);

    void initData();

    Observable<List<Company>> searchCompanies(@NonNull String keyWords);
}
