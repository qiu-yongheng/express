package com.eternal.express.onboarding;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.eternal.express.MainActivity;
import com.eternal.express.R;
import com.eternal.express.util.SettingsUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.eternal.express.R.id.buttonFinish;

/**
 * @author 邱永恒
 * @time 2017/4/18 21:10
 * @desc ${TODO}
 */

public class OnboardingActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.imageButtonPre)
    ImageButton mImageButtonPre;
    @BindView(R.id.imageViewIndicator0)
    ImageView mImageViewIndicator0;
    @BindView(R.id.imageViewIndicator1)
    ImageView mImageViewIndicator1;
    @BindView(R.id.imageViewIndicator2)
    ImageView mImageViewIndicator2;
    @BindView(buttonFinish)
    AppCompatButton mButtonFinish;
    @BindView(R.id.imageButtonNext)
    ImageButton mImageButtonNext;
    @BindView(R.id.container)
    ViewPager mViewPager;
    @BindView(R.id.main_content)
    CoordinatorLayout mMainContent;
    private ImageView[] mIndicators;
    private int[] mBgcolors;
    private int currentPosition;
    private SharedPreferences mSp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 实现背景图与状态栏融合
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_onboarding);
        ButterKnife.bind(this);
        initBoard();
    }

    /**
     * 初始化引导页
     */
    private void initBoard() {
        mSp = PreferenceManager.getDefaultSharedPreferences(this);
        // 判断是否第一次打开APP
        boolean aBoolean = mSp.getBoolean(SettingsUtil.KEY_FIRST_LAUNCH, true);
        if (mSp.getBoolean(SettingsUtil.KEY_FIRST_LAUNCH, true)) {
            initView();
            initData();
            initListener();
        } else {
            // 不是第一次打开, 直接跳转
            navigateToMainActivity();
        }
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        // 监听viewpage切换
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             * 监听viewpage滚动, 渐变颜色
             * @param position
             * @param positionOffset
             * @param positionOffsetPixels
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                /** 获取渐变色: 参数一: 当前百分比   参数二: 开始颜色   参数三: 结束颜色 */
                int colorUpdate = (Integer) new ArgbEvaluator().evaluate(positionOffset, mBgcolors[position], mBgcolors[position == 2 ? position : position + 1]);
                mViewPager.setBackgroundColor(colorUpdate);
            }

            /**
             * viewpage界面被选中的监听, 更新指示器, 改变界面颜色
             * @param position
             */
            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                updateIndicators(position);
                // 改变界面颜色
                mViewPager.setBackgroundColor(mBgcolors[position]);
                // 判断是否显示上一步
                mImageButtonPre.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
                // 判断是否显示下一步
                mImageButtonNext.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
                // 判断是否显示结束按钮
                mButtonFinish.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        // 点击结束按钮, 保存状态, 跳转界面
        mButtonFinish.setOnClickListener(this);
        mImageButtonPre.setOnClickListener(this);
        mImageButtonNext.setOnClickListener(this);
    }

    /**
     * 更新指示器显示
     * 直接遍历指示器, 设置每一个指示器的显示状态, 提高代码的复用性
     * @param position
     */
    private void updateIndicators(int position) {

        for (int i = 0; i < mIndicators.length; i++) {
            mIndicators[i].setBackgroundResource(
                    i == position ? R.drawable.onboarding_indicator_selected : R.drawable.onboarding_indicator_unselected
            );
        }
    }

    private void initData() {
        // 创建fragment颜色的集合
        mBgcolors = new int[]{ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.cyan_500),
                ContextCompat.getColor(this, R.color.light_blue_500)};
    }

    private void initView() {
        // 创建adapter
        OnboardingPageAdapter pageAdapter = new OnboardingPageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pageAdapter);

        // 创建指示器的集合
        mIndicators = new ImageView[]{
                mImageViewIndicator0,
                mImageViewIndicator1,
                mImageViewIndicator2
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonFinish: //点击跳转
                SharedPreferences.Editor ed = mSp.edit();
                ed.putBoolean(SettingsUtil.KEY_FIRST_LAUNCH, false);
                ed.apply();
                navigateToMainActivity();
                break;
            case R.id.imageButtonPre: // 上一个
                currentPosition -= 1;
                // 设置当前item, 设置平滑移动true
                mViewPager.setCurrentItem(currentPosition, true);
                break;
            case R.id.imageButtonNext: // 下一个
                currentPosition += 1;
                mViewPager.setCurrentItem(currentPosition, true);
                break;
        }
    }

    /**
     * 跳转到主界面
     */
    private void navigateToMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        // 在新的栈启动activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}
