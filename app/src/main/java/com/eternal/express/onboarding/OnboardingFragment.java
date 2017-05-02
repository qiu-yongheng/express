package com.eternal.express.onboarding;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.eternal.express.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 邱永恒
 * @time 2017/4/18 21:11
 * @desc 引导页的界面
 */

public class OnboardingFragment extends Fragment {
    @BindView(R.id.section_img)
    ImageView mSectionImg;
    @BindView(R.id.section_label)
    AppCompatTextView mSectionLabel;
    @BindView(R.id.section_intro)
    AppCompatTextView mSectionIntro;

    // 记录当前页面position的key值
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int page;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static OnboardingFragment newInstance(int sectionNumber) {
        OnboardingFragment fragment = new OnboardingFragment();
        Bundle args = new Bundle();
        
        // 给fragment保存当前的position
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * 创建界面前的初始化操作
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取当前页面的position
        page = getArguments().getInt(ARG_SECTION_NUMBER);
    }

    /**
     * 初始化界面
     * 根据当前界面加载不同的控件
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding, container, false);
        ButterKnife.bind(this, view);

        switch (page) {
            case 0:
                mSectionImg.setBackgroundResource(R.drawable.ic_beenhere_black_24dp);
                mSectionLabel.setText(R.string.onboarding_section_1);
                mSectionIntro.setText(R.string.onboarding_intro_1);
                break;
            case 1:
                mSectionImg.setBackgroundResource(R.drawable.ic_notifications_black_24dp);
                mSectionLabel.setText(R.string.onboarding_section_2);
                mSectionIntro.setText(R.string.onboarding_intro_2);
                break;
            case 2:
                mSectionImg.setBackgroundResource(R.drawable.ic_watch_black_24dp);
                mSectionLabel.setText(R.string.onboarding_section_3);
                mSectionIntro.setText(R.string.onboarding_intro_3);
                break;
            default:
                break;
        }

        return view;
    }
}
