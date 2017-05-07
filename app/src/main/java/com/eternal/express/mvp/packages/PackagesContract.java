package com.eternal.express.mvp.packages;

import android.support.annotation.NonNull;

import com.eternal.express.mvp.BasePresenter;
import com.eternal.express.mvp.BaseView;

/**
 * @author 邱永恒
 * @time 2017/5/3 21:45
 * @desc
 */

public interface PackagesContract {
    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showEmptyView(boolean toShow);

        void showPackages();

        void shareTo();

        void showPackageRemovedMsg(String packageName);

        void copyPackageNumber();

        void showNetworkError();

    }

    interface Presenter extends BasePresenter {

        void loadPackages();

        void refreshPackages();

        void markAllPacksRead();

        void setFiltering(@NonNull PackageFilterType requestType);

        PackageFilterType getFiltering();

        void setPackageReadable(@NonNull String packageId, boolean readable);

        void deletePackage(int position);

        void setShareData(@NonNull String packageId);

        void recoverPackage();

    }
}
