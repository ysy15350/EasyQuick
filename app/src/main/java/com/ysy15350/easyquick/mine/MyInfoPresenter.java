package com.ysy15350.easyquick.mine;

import android.content.Context;

import java.io.File;

import api.MemberApi;
import api.PublicApi;
import api.base.ApiCallBack;
import api.base.model.Response;
import api.impl.MemberApiImpl;
import api.impl.PublicApiImpl;
import base.mvp.BasePresenter;

public class MyInfoPresenter extends BasePresenter<MyInfoViewInterface> {

    public MyInfoPresenter(Context context) {
        super(context);

    }

    PublicApi publicApi = new PublicApiImpl();
    MemberApi memberApi = new MemberApiImpl();


    /**
     * 用户信息
     */
    public void store_info() {
        memberApi.store_info(new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                mView.store_infoCallback(isCache, response);
            }
        });
    }

    /**
     * 修改个人资料
     *
     * @param headimgurl
     * @param nickname
     * @param cards
     * @param address
     */
    public void save_info(String headimgurl, String nickname, String cards, String address) {
        memberApi.save_info(headimgurl, nickname, cards, address, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.save_infoCallback(isCache, response);
            }
        });
    }

    /**
     * 修改店铺资料
     *
     * @param icon         头像
     * @param name         店铺名称
     * @param licenes      执照名称
     * @param license_icon 执照图片
     * @param mobile
     * @param address      地址
     * @param business     经营范围，实例:1,2,3
     * @param description  店铺描述
     */
    public void store_save_info(int icon, String name, String license, String license_icon, String mobile, String address, String business, String description, String pay_password) {
        memberApi.store_save_info(icon, name, license, license_icon, mobile, address, business, description, pay_password, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.store_save_infoCallback(isCache, response);
            }
        });
    }

    /**
     * 上传头像
     *
     * @param file
     */
    public void uppicture(File file) {
        memberApi.uppicture(file, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                //{"code":200,"pic":"\/Uploads\/Picture\/2017-08-31\/59a7a5689e7f9.jpg","id":2}
                if (response != null)
                    mView.uppictureCallback(response);
            }


        });
    }

}
