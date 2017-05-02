package com.eternal.express.onboarding;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * @author 邱永恒
 * @time 2017/4/18 21:12
 * @desc 引导页的适配器
 */

public class OnboardingPageAdapter extends FragmentPagerAdapter{
    private final int pageCount = 3;
    public OnboardingPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // 根据当前的position创建fragment
        return OnboardingFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return pageCount;
    }
}
