package com.ysy15350.easyquick.invite;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.easyquick.R;
import com.ysy15350.easyquick.account.AddBankActivity;
import com.ysy15350.easyquick.account.BankListPresenter;
import com.ysy15350.easyquick.account.BankListViewInterface;
import com.ysy15350.easyquick.adapters.ListViewAdpater_Bank;
import com.ysy15350.easyquick.adapters.ListViewAdpater_PersonWater;
import com.ysy15350.easyquick.invite.tabs.InviteTab1Fragment;
import com.ysy15350.easyquick.invite.tabs.InviteTab2Fragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import api.base.model.Response;
import api.model.BankInfo;
import api.model.PersonWater;
import base.data.BaseData;
import base.mvp.MVPBaseActivity;
import base.mvp.MVPBaseListViewActivity;
import common.CommFunAndroid;

/**
 * Created by yangshiyou on 2017/10/24.
 */

@ContentView(R.layout.activity_invite_list)
public class InviteUserTradeRecordActivity extends MVPBaseActivity<InviteUserTradeRecordViewInterface, InviteUserTradeRecordPresenter>
        implements InviteUserTradeRecordViewInterface {

    @Override
    protected InviteUserTradeRecordPresenter createPresenter() {

        return new InviteUserTradeRecordPresenter(InviteUserTradeRecordActivity.this);
    }

    @ViewInject(R.id.container)
    private ViewPager mViewPager;

    @ViewInject(R.id.ll_tab1)
    private View ll_tab1;
    @ViewInject(R.id.ll_tab2)
    private View ll_tab2;

    @ViewInject(R.id.cursor)
    private ImageView cursor;// 动画图片

    private int screenW = 0;
    private int offset = 0;// 动画图片偏移量
    private int bmpW;// 动画图片宽度

    /**
     * 显示指定选项卡
     */
    public int tab_position = 0;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    public void initView() {

        super.initView();
        setFormHead("推广收入明细");
        //setMenu(R.mipmap.icon_add_bank, "添加");

        try {
            bmpW = BitmapFactory.decodeResource(getResources(), R.mipmap.line_tab).getWidth();// 获取图片宽度

            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            screenW = dm.widthPixels;// 获取分辨率宽度
            offset = screenW / 2 - bmpW;// 计算偏移量
            final Matrix matrix = new Matrix();
            matrix.postTranslate(offset, 0);

            cursor.setImageMatrix(matrix);// 设置动画初始位置

            // cursor.setDrawingCacheEnabled(true);// 获取bm前执行，否则无法获取
            // Bitmap bm = cursor.getDrawingCache();
            // int bm_width = bm.getWidth();
            // int widht_1 = screenW / 4;


            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
            mViewPager.setAdapter(mSectionsPagerAdapter);
            //tabLayout.setupWithViewPager(mViewPager);


            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    setTab(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        setTab(tab_position);
        setSelect(tab_position);

    }

    private void setTab(int position) {
        tab_position = position;// 记录已打开选项卡位置，当跳转到登录界面或者其他界面，显示此界面
        switch (position) {
            case 0:
                setTabImage(position, ll_tab1);
                break;
            case 1:
                setTabImage(position, ll_tab2);
                break;

            default:
                break;
        }
    }

    private void setTabImage(int position, View view) {

        setView(view);

        int one = offset + bmpW;// 页卡1 -> 页卡2 偏移量
        int two = one * 2;// 页卡1 -> 页卡3 偏移量

        switch (position) {
            case 3:
                one = one + 15;
                break;

            default:
                break;
        }

        Animation animation = new TranslateAnimation(one * tab_position, one * position, 0, 0);
        animation.setFillAfter(true);// True:图片停在动画结束位置
        animation.setDuration(300);
        cursor.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                //setSelect(tab_position);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        tab_position = position;


    }


    /**
     * 记录当前view（图片切换）
     */
    private View currentView;

    /**
     * 切换图片（background 设置背景：xml->selector）
     *
     * @param v
     */
    private void setView(View v) {

        if (currentView != null) {
            if (currentView.getId() != v.getId()) {
                View imgview = currentView.findViewWithTag("tabimg");
                View textview = currentView.findViewWithTag("tabtext");
                if (imgview != null)
                    imgview.setEnabled(true);
                if (textview != null) {
                    textview.setEnabled(true);
                }
            }
        }
        if (v != null) {
            View imgview = v.findViewWithTag("tabimg");
            View textview = v.findViewWithTag("tabtext");
            if (imgview != null)
                imgview.setEnabled(false);
            if (textview != null) {
                textview.setEnabled(false);
            }
        }
        currentView = v;
    }

    @Event(value = R.id.ll_tab1)
    private void ll_tab1Click(View view) {
        setSelect(0);
        //setTabImage(0, view);

    }

    @Event(value = R.id.ll_tab2)
    private void ll_tab2Click(View view) {
        setSelect(1);
    }

    /**
     * 点击事件1:设置tab(改变图片和字体)和2:切换fragment
     *
     * @param position
     */
    protected void setSelect(int position) {


        tab_position = position;// 记录已打开选项卡位置，当跳转到登录界面或者其他界面，显示此界面

        mViewPager.setCurrentItem(position);


    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: {
                    return new InviteTab1Fragment();
                }
                case 1: {
                    return new InviteTab2Fragment();
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

    }


}