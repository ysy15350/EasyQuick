package com.ysy15350.easyquick.invite.tabs;


import android.content.Context;

import api.MemberApi;
import api.base.ApiCallBack;
import api.base.model.Response;
import api.impl.MemberApiImpl;
import base.mvp.BasePresenter;

public class InviteTab2Presenter extends BasePresenter<InviteTab2ViewInterface> {

    public InviteTab2Presenter() {
    }

    public InviteTab2Presenter(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    MemberApi memberApi = new MemberApiImpl();

    public void shopYonjinWater( int page, int pageSize) {

        memberApi.shopYonjinWater( page, pageSize, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.shopYonjinWaterCallback(isCache, response);
            }
        });
    }


}
