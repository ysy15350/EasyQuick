package com.ysy15350.easyquick.account;

import android.content.Context;

import api.MemberApi;
import api.PublicApi;
import api.base.ApiCallBack;
import api.base.model.Response;
import api.impl.MemberApiImpl;
import api.impl.PublicApiImpl;
import base.mvp.BasePresenter;

public class BankCardListPresenter extends BasePresenter<BankCardListViewInterface> {

    public BankCardListPresenter(Context context) {
        super(context);

    }

    PublicApi publicApi = new PublicApiImpl();



    MemberApi memberApi = new MemberApiImpl();

    public void bank_cardlist() {
        memberApi.user_bank(1, 1, 100, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.bank_cardlistCallback(isCache, response);
            }
        });
    }

}
