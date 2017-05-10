package com.eternal.express;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Slide;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.eternal.express.data.source.PackagesRepository;
import com.eternal.express.data.source.local.PackagesLocalDataSource;
import com.eternal.express.data.source.remote.PackagesRemoteDataSource;
import com.eternal.express.mvp.companies.CompaniesFragment;
import com.eternal.express.mvp.companies.CompaniesPresenter;
import com.eternal.express.mvp.packages.PackageFilterType;
import com.eternal.express.mvp.packages.PackagesFragment;
import com.eternal.express.mvp.packages.PackagesPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.content_main)
    FrameLayout mContentMain;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    /**
     * 当前侧拉栏选中item
     */
    private static final String KEY_NAV_ITEM = "CURRENT_NAV_ITEM";
    /**
     * 当前导航栏选中item
     */
    private static final String CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY";
    private PackagesFragment packagesFragment;
    private CompaniesFragment companiesFragment;
    private int selectedNavItem;
    private PackagesPresenter packagesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setWindowTransition();
        initView();
        initFragment(savedInstanceState);
        initPresenter(savedInstanceState);

        if (selectedNavItem == 0) {
            showFragment(packagesFragment);
        } else if (selectedNavItem == 1) {
            showFragment(companiesFragment);
        }
    }

    /**
     * 显示指定的fragment
     * @param fragment
     */
    private void showFragment(Fragment fragment) {
        if (fragment instanceof PackagesFragment) {
            getSupportFragmentManager().beginTransaction()
                    .show(fragment)
                    .hide(companiesFragment)
                    .commit();

            mToolbar.setTitle(R.string.app_name);
            mNavView.setCheckedItem(R.id.nav_home);
        } else if (fragment instanceof CompaniesFragment) {
            getSupportFragmentManager().beginTransaction()
                    .show(fragment)
                    .hide(packagesFragment)
                    .commit();

            mToolbar.setTitle(R.string.nav_companies);
            mNavView.setCheckedItem(R.id.nav_companies);
        }
    }

    /**
     * 初始化presenter
     * @param savedInstanceState
     */
    private void initPresenter(Bundle savedInstanceState) {
        packagesPresenter = new PackagesPresenter(packagesFragment, PackagesRepository.getInstance(PackagesRemoteDataSource.getInstance(), PackagesLocalDataSource.getInstance()));
        new CompaniesPresenter();

        // Get data from Bundle.
        if (savedInstanceState != null) {
            PackageFilterType currentFiltering = (PackageFilterType) savedInstanceState.getSerializable(CURRENT_FILTERING_KEY);
            packagesPresenter.setFiltering(currentFiltering);
        }
    }

    /**
     * 初始化要显示的fragment
     *
     * @param savedInstanceState
     */
    private void initFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // 1. 获取保存的fragment
            packagesFragment = (PackagesFragment) getSupportFragmentManager().getFragment(savedInstanceState, "PackagesFragment");
            companiesFragment = (CompaniesFragment) getSupportFragmentManager().getFragment(savedInstanceState, "CompaniesFragment");

            // 2. 获取当前显示的fragment(当界面被回收时, 保存有当前显示的界面)
            selectedNavItem = savedInstanceState.getInt(KEY_NAV_ITEM);
        } else {
            if (packagesFragment == null) {
                packagesFragment = PackagesFragment.newInstance();
            }
            if (companiesFragment == null) {
                companiesFragment = CompaniesFragment.newInstance();
            }
        }

        // 添加fragments.
        if (!packagesFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_main, packagesFragment, "PackagesFragment")
                    .commit();
        }

        if (!companiesFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_main, companiesFragment, "CompaniesFragment")
                    .commit();
        }
    }

    /**
     * 初始化控件(侧拉栏)
     */
    private void initView() {
        // 设置导航栏背景色
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("navigation_bar_tint", true)) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        // 设置toolbar
        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mDrawerLayout.setDrawerListener(toggle);

        toggle.syncState();

        // 设置侧拉栏item的点击事件
        mNavView.setNavigationItemSelectedListener(this);
    }

    /**
     * 设置界面 enter and exit 的动画
     */
    private void setWindowTransition() {
        // 离开的动画
        Slide slide = new Slide();
        slide.setDuration(500);
        getWindow().setExitTransition(slide);

        // 重新进入的动画
        Explode explode = new Explode();
        explode.setDuration(500);
        getWindow().setReenterTransition(explode);
    }

    /**
     * 侧拉框item的点击监听
     *
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                showFragment(packagesFragment);
                break;
            case R.id.nav_companies:
                showFragment(companiesFragment);
                break;
            case R.id.nav_switch_theme:
                break;
            case R.id.nav_settings:
                break;
            case R.id.nav_about:
                break;
        }
        // 关闭drawerlayout
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 当界面容易被销毁时(还没销毁, 如点击HOME回到桌面, APP在后台运行), 执行这个方法, 保存数据
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // 1. 保存当前导航栏选中item
        outState.putSerializable(CURRENT_FILTERING_KEY, packagesPresenter.getFiltering());
        // 2. 保存当前显示的fragment
        Menu menu = mNavView.getMenu();
        if (menu.findItem(R.id.nav_home).isChecked()) {
            outState.putInt(KEY_NAV_ITEM, 0);
        } else if (menu.findItem(R.id.nav_companies).isChecked()) {
            outState.putInt(KEY_NAV_ITEM, 1);
        }
        // 3. 保存fragment的状态
        if (packagesFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "PackagesFragment", packagesFragment);
        }
        if (companiesFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "CompaniesFragment", companiesFragment);
        }
    }

    /**
     * 可以获取当前选中的item的运单号
     * @param number
     */
    public void setSelectedPackageId(String number) {
        packagesFragment.setSelectedPackage(number);
    }
}
