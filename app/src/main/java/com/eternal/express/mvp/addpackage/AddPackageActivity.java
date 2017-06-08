package com.eternal.express.mvp.addpackage;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.eternal.express.R;
import com.eternal.express.data.source.CompaniesRepository;
import com.eternal.express.data.source.PackagesRepository;
import com.eternal.express.data.source.local.CompaniesLocalDataSource;
import com.eternal.express.data.source.local.PackagesLocalDataSource;
import com.eternal.express.data.source.remote.PackagesRemoteDataSource;

/**
 * @author 邱永恒
 * @time 2017/5/13 15:50
 * @desc 添加包裹的界面
 */

public class AddPackageActivity extends AppCompatActivity{

    private AddPackageFragment mAddPackageFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        /* 1. 设置状态栏的背景颜色 */
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("navigation_bar_tint", true)) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }

        /* 2. 设置界面进入动画 */
        initTransition();

        /* 3. 初始化Fragment */
        initFragment(savedInstanceState);

        /* 4. 初始化presenter */
        initPresenter();
    }



    /**
     * 设置界面进入动画
     * 爆炸动画
     */
    private void initTransition() {
        Explode explode = new Explode();
        explode.setDuration(500);
        explode.setInterpolator(new AccelerateDecelerateInterpolator());
        getWindow().setEnterTransition(explode);
    }

    /**
     * 初始化Fragment
     * @param savedInstanceState
     */
    private void initFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mAddPackageFragment = (AddPackageFragment) getSupportFragmentManager().getFragment(savedInstanceState, "AddPackageFragment");
        } else {
            mAddPackageFragment = AddPackageFragment.getInstance();
        }

        if (!mAddPackageFragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_main, mAddPackageFragment, "AddPackageFragment")
                    .commit();
        }
    }

    /**
     * 初始化presenter
     */
    private void initPresenter() {
        new AddPackagePresenter(PackagesRepository.getInstance(
                PackagesRemoteDataSource.getInstance(),
                PackagesLocalDataSource.getInstance()),
                CompaniesRepository.getInstance(CompaniesLocalDataSource.getInstance()),
                mAddPackageFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mAddPackageFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "AddPackageFragment", mAddPackageFragment);
        }
    }
}
