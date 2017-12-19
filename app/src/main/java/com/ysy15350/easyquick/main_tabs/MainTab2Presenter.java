package com.ysy15350.easyquick.main_tabs;


import android.content.Context;

import api.MemberApi;
import api.base.ApiCallBack;
import api.base.model.Response;
import api.impl.MemberApiImpl;
import base.mvp.BasePresenter;

public class MainTab2Presenter extends BasePresenter<MainTab2ViewInterface> {

    public MainTab2Presenter() {
    }

    public MainTab2Presenter(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    MemberApi memberApi = new MemberApiImpl();

    public void order_list(String start_time, String end_time, int page, int pageSize) {

        memberApi.order_list(start_time, end_time, page, pageSize, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.order_listCallback(isCache, response);
            }
        });
    }


}
