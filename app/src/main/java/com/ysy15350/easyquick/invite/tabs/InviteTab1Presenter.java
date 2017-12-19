package com.ysy15350.easyquick.invite.tabs;


import android.content.Context;

import api.MemberApi;
import api.base.ApiCallBack;
import api.base.model.Response;
import api.impl.MemberApiImpl;
import base.mvp.BasePresenter;

public class InviteTab1Presenter extends BasePresenter<InviteTab1ViewInterface> {

    public InviteTab1Presenter() {
    }

    public InviteTab1Presenter(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    MemberApi memberApi = new MemberApiImpl();

    public void personWater(int page, int pageSize) {
        memberApi.personWater(page, pageSize, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.personWaterCallback(isCache, response);
            }
        });
    }


}
