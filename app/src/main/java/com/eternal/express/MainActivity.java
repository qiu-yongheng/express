package com.eternal.express;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Slide;
import android.view.MenuItem;
import android.widget.FrameLayout;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setWindowTransition();
        initView();
        initFragment();
    }

    /**
     * 初始化要显示的fragment
     */
    private void initFragment() {

    }

    /**
     * 初始化控件(侧拉栏)
     */
    private void initView() {
        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mDrawerLayout.setDrawerListener(toggle);

        toggle.syncState();

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
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
