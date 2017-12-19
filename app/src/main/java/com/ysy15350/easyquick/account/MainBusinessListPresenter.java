package com.ysy15350.easyquick.account;

import android.content.Context;

import api.MemberApi;
import api.PublicApi;
import api.base.ApiCallBack;
import api.base.model.Response;
import api.impl.MemberApiImpl;
import api.impl.PublicApiImpl;
import base.mvp.BasePresenter;

public class MainBusinessListPresenter extends BasePresenter<MainBusinessListViewInterface> {

    public MainBusinessListPresenter(Context context) {
        super(context);

    }

    PublicApi publicApi = new PublicApiImpl();


    MemberApi memberApi = new MemberApiImpl();

    public void store_cate(int page, int pageSize) {
        memberApi.store_cate(page, pageSize, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.store_cateCallback(isCache, response);
            }
        });
    }

}
