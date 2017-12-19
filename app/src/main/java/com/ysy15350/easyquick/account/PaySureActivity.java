package com.ysy15350.easyquick.account;

import android.content.Intent;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.easyquick.R;

import org.xutils.view.annotation.ContentView;

import java.util.HashMap;
import java.util.Map;

import api.MemberApi;
import api.base.ApiCallBack;
import api.base.model.Response;
import api.impl.MemberApiImpl;
import base.BaseActivity;
import base.data.BaseData;
import common.CommFunAndroid;
import common.string.JsonConvertor;
import custom_view.dialog.PayDialog;

/**
 * Created by yangshiyou on 2017/9/25.
 */

@ContentView(R.layout.activity_pay_sure)
public class PaySureActivity extends BaseActivity {

    String mContent = "";


    @Override
    public void initView() {

        super.initView();
        setFormHead("确认收款");

        Intent intent = getIntent();
        mContent = intent.getStringExtra("content");

        if (!CommFunAndroid.isNullOrEmpty(mContent)) {
            Map<String, String> map = JsonConvertor.fromJson(mContent, new TypeToken<HashMap<String, String>>() {
            }.getType());

            if (map != null) {
                String user_type = map.get("user_type");//type:1：商家；2：用户
                String uid = map.get("uid");
                String price = map.get("price");
                String name = map.get("name");
                String remark = map.get("remark");
                if ("1".equals(user_type)) {//如果对方是商家，
                    if (!CommFunAndroid.isNullOrEmpty(name)) {
                        String title = String.format("向\" %s \"付款 %s 元", name, price);
                        mHolder.setText(R.id.tv_title, title).setText(R.id.tv_business, name).setText(R.id.tv_pay_type, "收款方")
                                .setText(R.id.btn_ok, "确认付款");
                    }
                } else if ("2".equals(user_type)) {
                    if (!CommFunAndroid.isNullOrEmpty(name)) {
                        String title = String.format("向\" %s \"收款 %s 元", name, price);
                        mHolder.setText(R.id.tv_title, title).setText(R.id.tv_business, name).setText(R.id.tv_pay_type, "付款方").setText(R.id.btn_ok, "确认收款");
                    }
                }
                if (!CommFunAndroid.isNullOrEmpty(remark)) {

                    mHolder.setText(R.id.et_remark, remark);
                }
                mHolder.setText(R.id.tv_price, "￥" + price);
            }

        }

    }

    @Override
    protected void btn_okOnClick(View view) {
        super.btn_okOnClick(view);
        paySure1(mContent);
    }

    //    https://github.com/yuzhiqiang1993/zxing

//    /**
//     * 确认支付
//     *
//     * @param content
//     */
//    private void paySure(String content) {
//
//        try {
//            if (!CommFunAndroid.isNullOrEmpty(content)) {
//
//                Map<String, String> map = JsonConvertor.fromJson(content, new TypeToken<HashMap<String, String>>() {
//                }.getType());
//
//                if (map != null) {
//                    //                        map.put("uid", BaseData.getInstance().getUid() + "");
//                    //                        map.put("price", price + "");
//                    //                        uid：会员ID;sid店铺id
//                    String uid = map.get("uid");
//                    String price = map.get("price");
//                    String remark = mHolder.getViewText(R.id.et_remark);
//                    MemberApi memberApi = new MemberApiImpl();
//
//                    showWaitDialog("服务器处理中...");
//
//                    memberApi.user_payment(uid, price, remark, new ApiCallBack() {
//                        @Override
//                        public void onSuccess(boolean isCache, Response response) {
//                            super.onSuccess(isCache, response);
//                            if (response != null) {
//                                int code = response.getCode();
//                                String msg = response.getMessage();
//                                if (code == 200) {
//                                    Intent intent = new Intent(PaySureActivity.this, AccountResultActivity.class);
//                                    intent.putExtra("title", "收款成功");
//                                    intent.putExtra("content", "");
//                                    startActivity(intent);
//                                    finish();
//                                }
//                                showMsg(msg);
//                            }
//                        }
//                    });
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    MemberApi memberApi = new MemberApiImpl();

    /**
     * 确认支付
     *
     * @param content
     */
    private void paySure1(String content) {

        try {
            if (!CommFunAndroid.isNullOrEmpty(content)) {

                final Map<String, String> map = JsonConvertor.fromJson(content, new TypeToken<HashMap<String, String>>() {
                }.getType());

                if (map != null) {
                    //                        map.put("uid", BaseData.getInstance().getUid() + "");
                    //                        map.put("price", price + "");
                    //                        uid：会员ID;sid店铺id
                    final String user_type = map.get("user_type");//type:1：商家；2：用户
                    String uid = map.get("uid");
                    String price = map.get("price");
                    String remark = mHolder.getViewText(R.id.et_remark);


                    showWaitDialog("服务器处理中...");

                    int payType = 1;//1：个人付款给商家，2商家付款给商家
                    String uid_a = "";//付款方id
                    String uid_b = "";//收款方id
                    if ("1".equals(user_type)) {
                        //如果对方用户类型是：商家,自己是扫描方（付款方）
                        payType = 2;
                        uid_a = BaseData.getInstance().getUid() + "";//付款方id
                        uid_b = uid;//收款方id

                        PayDialog dialog = new PayDialog(PaySureActivity.this);
                        dialog.setDialogListener(new PayDialog.DialogListener() {
                            @Override
                            public void onCancelClick() {

                            }

                            @Override
                            public void onOkClick(String password) {


                                memberApi.personPayPassword(password, new ApiCallBack() {
                                    @Override
                                    public void onSuccess(boolean isCache, Response response) {
                                        super.onSuccess(isCache, response);
                                        if (response != null) {
                                            int code = response.getCode();
                                            String msg = response.getMessage();
                                            if (code == 200) {

                                                //                        map.put("uid", BaseData.getInstance().getUid() + "");
                                                //                        map.put("price", price + "");
                                                //                        uid：会员ID;sid店铺id


                                                String uid = map.get("uid");
                                                String price = map.get("price");
                                                String remark = map.get("remark");


                                                showWaitDialog("服务器处理中...");

                                                int payType = 2;//1：个人付款给商家，2商家付款给商家
                                                String uid_a = BaseData.getInstance().getUid() + "";//付款方id
                                                String uid_b = uid;//收款方id

                                                memberApi.user_payment(payType, uid_a, uid_b, price, remark, new ApiCallBack() {
                                                    @Override
                                                    public void onSuccess(boolean isCache, Response response) {
                                                        super.onSuccess(isCache, response);
                                                        if (response != null) {
                                                            int code = response.getCode();
                                                            String msg = response.getMessage();
                                                            if (code == 200) {
                                                                Intent intent = new Intent(PaySureActivity.this, AccountResultActivity.class);
                                                                intent.putExtra("title", "付款成功");
                                                                intent.putExtra("content", "");
                                                                startActivity(intent);
                                                                finish();
                                                            }
                                                            showMsg(msg);
                                                        }
                                                    }
                                                });


                                            }
                                            showMsg(msg);

                                        }
                                    }
                                });

                            }
                        });
                        dialog.show();


                    } else {
                        payType = 1;
                        uid_a = uid;//付款方id
                        uid_b = BaseData.getInstance().getUid() + "";//收款方id

                        memberApi.user_payment(payType, uid_a, uid_b, price, remark, new ApiCallBack() {
                            @Override
                            public void onSuccess(boolean isCache, Response response) {
                                super.onSuccess(isCache, response);
                                if (response != null) {
                                    int code = response.getCode();
                                    String msg = response.getMessage();
                                    if (code == 200) {
                                        Intent intent = new Intent(PaySureActivity.this, AccountResultActivity.class);
                                        if ("1".equals(user_type)) {
                                            intent.putExtra("title", "收款成功");
                                        } else if ("2".equals(user_type)) {
                                            intent.putExtra("title", "付款成功");
                                        }

                                        intent.putExtra("content", "");
                                        startActivity(intent);
                                        finish();
                                    }
                                    showMsg(msg);
                                }
                            }
                        });
                    }


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
