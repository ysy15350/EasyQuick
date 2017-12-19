package com.ysy15350.easyquick.main_tabs;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.ysy15350.easyquick.R;
import com.ysy15350.easyquick.account.ProfitDetailListActivity;
import com.ysy15350.easyquick.account.RechargeActivity;
import com.ysy15350.easyquick.account.WithdrawActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import api.base.model.Config;
import base.data.BaseData;
import base.data.ConfigHelper;
import base.data.entity.UserInfo;
import base.mvp.MVPBaseFragment;
import common.CommFunAndroid;
import custom_view.progress_bar.CircleBar;


/**
 * Created by yangshiyou on 2017/8/30.
 */

@ContentView(R.layout.activity_main_tab1)
public class MainTab1Fragment extends MVPBaseFragment<MainTab1ViewInterface, MainTab1Presenter>
        implements MainTab1ViewInterface {

    private static final String TAG = "InviteTab1Fragment";

    private static int count = 0;

    public MainTab1Fragment() {
    }


    @Override
    public MainTab1Presenter createPresenter() {
        // TODO Auto-generated method stub
        return new MainTab1Presenter(getActivity());
    }


    @ViewInject(R.id.myProgress)
    private CircleBar myProgress;

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume() called");

        UserInfo userInfo = BaseData.getInstance().getUserInfo();
        bindUserInfo(userInfo);

    }

    /**
     * 绑定用户信息
     *
     * @param userInfo
     */
    private void bindUserInfo(UserInfo userInfo) {

        try {
            if (userInfo != null) {
                //
                mHolder
                        .setImageURL(R.id.img_head, ConfigHelper.getServerUrl(Config.isDebug, false) + userInfo.getHeadimgurl(), true)
                        .setText(R.id.tv_nickName, userInfo.getNickname())
                        .setText(R.id.tv_mobile, userInfo.getMobile())
                        .setText(R.id.tv_total_price, String.format("%.2f", userInfo.getTotal_price()))
                        .setText(R.id.tv_day_total_price, String.format("%.2f", userInfo.getDay_total_price()))
                        .setText(R.id.tv_balance, String.format("%.2f", userInfo.getBalance() + userInfo.getProfit()))//余额+收益
                        .setText(R.id.tv_rate, String.format("%.2f", userInfo.getRate()))
                ;

                int rate = CommFunAndroid.toInt32(userInfo.getRate() * 100, 0);

                float sweepAngle = CommFunAndroid.toInt32(300 * rate / 1000, 0);

                myProgress.setText(rate);
                myProgress.setSweepAngle(sweepAngle);//总共300°
                myProgress.startCustomAnimation();

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 收益明细
     *
     * @param view
     */
    @Event(value = R.id.btn_detail)
    private void btn_detailClick(View view) {
        if (isLogin())
            startActivity(new Intent(mContext, ProfitDetailListActivity.class));
    }


    /**
     * 充值
     *
     * @param view
     */
    @Event(value = R.id.btn_recharge)
    private void btn_rechargeClick(View view) {
        if (isLogin())
            startActivity(new Intent(mContext, RechargeActivity.class));
    }

    /**
     * 提现
     *
     * @param view
     */
    @Event(value = R.id.btn_withdraw)
    private void btn_withdrawClick(View view) {
        if (isLogin())
            startActivity(new Intent(mContext, WithdrawActivity.class));
    }
}
