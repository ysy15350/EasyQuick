package com.ysy15350.easyquick.account;

/**
 * Created by yangshiyou on 2017/9/20.
 */


import android.content.Intent;
import android.view.View;

import com.ysy15350.easyquick.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import api.MemberApi;
import api.base.ApiCallBack;
import api.base.model.Response;
import api.impl.MemberApiImpl;
import base.BaseActivity;
import base.data.BaseData;
import base.data.entity.UserInfo;

/**
 * 余额
 */
@ContentView(R.layout.activity_balance)
public class BalanceActivity extends BaseActivity {

    @Override
    public void initView() {

        super.initView();
        setFormHead("余额");
        mHolder.setVisibility_VISIBLE(R.id.btn_detail);//显示标题栏明细按钮
    }

    MemberApi memberApi = new MemberApiImpl();

    @Override
    protected void onResume() {
        super.onResume();

        memberApi.store_info(new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                try {
                    if (response != null) {
                        int code = response.getCode();
                        String msg = response.getMessage();
                        if (code == 200) {

                            UserInfo userInfo = response.getData(UserInfo.class);
                            userInfo.setUid(BaseData.getInstance().getUid());
                            BaseData.getInstance().setUserInfo(userInfo);

                            bindUserInfo(userInfo);

                        } else {
                            showMsg(msg);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

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
                mHolder.setText(R.id.tv_balance, String.format("%.2f", userInfo.getBalance()));
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 余额明细
     *
     * @param view
     */
    @Event(value = R.id.btn_detail)
    private void btn_detailClick(View view) {
        if (isLogin())
            startActivity(new Intent(this, BalanceDetailListActivity.class));
    }

    /**
     * 充值
     *
     * @param view
     */
    @Event(value = R.id.btn_recharge)
    private void btn_rechargeClick(View view) {
        if (isLogin())
            startActivity(new Intent(this, RechargeActivity.class));
    }

    /**
     * 提现
     *
     * @param view
     */
    @Event(value = R.id.btn_withdraw)
    private void btn_withdrawClick(View view) {
        if (isLogin())
            startActivity(new Intent(this, WithdrawActivity.class));
    }
}
