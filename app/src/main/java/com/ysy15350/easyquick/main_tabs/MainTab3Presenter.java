package com.ysy15350.easyquick.main_tabs;


import android.content.Context;

import api.MemberApi;
import api.ProductApi;
import api.base.ApiCallBack;
import api.base.model.Response;
import api.impl.MemberApiImpl;
import api.impl.ProductApiImpl;
import base.mvp.BasePresenter;

public class MainTab3Presenter extends BasePresenter<MainTab3ViewInterface> {

    public MainTab3Presenter() {
    }

    public MainTab3Presenter(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    MemberApi memberApi = new MemberApiImpl();

    ProductApi productApi = new ProductApiImpl();

    public void micro_pay(String qrcode) {
        productApi.micro_pay(qrcode, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
            }
        });
    }

    /**
     * 个人中心
     */
    public void user_info() {

        memberApi.store_info(new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.user_infoCallback(isCache, response);
            }
        });

    }

    /**
     * 广告图
     */
    public void adlist() {
        //1:会员中心首页广告图
        memberApi.adlist(1, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.adlistCallback(isCache, response);
            }
        });
    }

    public void checkUserinfo() {
        memberApi.checkUserinfo(new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.checkUserinfoCallback(isCache, response);
            }
        });
    }


}
