package com.ysy15350.easyquick.account;

import android.content.Intent;
import android.view.View;

import com.ysy15350.easyquick.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.util.HashMap;
import java.util.Map;

import api.MemberApi;
import api.base.ApiCallBack;
import api.base.model.Response;
import api.impl.MemberApiImpl;
import api.model.wx_alipay.Body;
import api.model.wx_alipay.WxAlipayCode;
import base.BaseActivity;
import base.data.BaseData;
import base.data.entity.UserInfo;
import common.CommFunAndroid;
import common.string.JsonConvertor;
import custom_view.popupwindow.PaySelectPopupWindow;

/**
 * Created by yangshiyou on 2017/9/20.
 */

/**
 * 设置金额
 */
@ContentView(R.layout.activity_set_price)
public class SetPayPriceActivity extends BaseActivity {


    @Override
    public void initView() {

        super.initView();
        setFormHead("设置金额");
    }

    @Override
    protected void btn_okOnClick(View view) {
        super.btn_okOnClick(view);


    }

    private float checkPrice() {
        String et_priceText = mHolder.getViewText(R.id.et_price);
        if (CommFunAndroid.isNullOrEmpty(et_priceText)) {
            showMsg("请输入金额");

            return 0;
        }

        if (!CommFunAndroid.isNumber(et_priceText)) {
            showMsg("金额输入有误");
            return 0;
        }

        float price = Float.parseFloat(et_priceText);

        if (price == 0) {
            showMsg("金额不能为零");
            return 0;
        }

        return price;
    }


    private void choosePayType() {

        float price = checkPrice();

        if (price <= 0)
            return;

        CommFunAndroid.hideSoftInput(mHolder.getView(R.id.et_price));

        PaySelectPopupWindow popupWindow = new PaySelectPopupWindow(this);
        popupWindow.showPopupWindow(mContentView);

        popupWindow.setPopListener(new PaySelectPopupWindow.PopListener() {
            @Override
            public void onTV1Click() {
                float price = checkPrice();

                if (price <= 0)
                    return;

                UserInfo userInfo = BaseData.getInstance().getUserInfo();

                Map<String, String> map = new HashMap<>();
                map.put("type", "0");//0:余额；1：支付宝；2：微信
                map.put("user_type", "1");//type:1：商家；2：用户
                map.put("uid", BaseData.getInstance().getUid() + "");
                map.put("price", price + "");

                String remark = mHolder.getViewText(R.id.et_remark);

                map.put("remark", remark);//备注

                if (userInfo != null) {
                    String name = userInfo.getName();
                    map.put("name", name);//用户名称
                }


                Intent intent = new Intent(SetPayPriceActivity.this, QRCodePayActivity.class);//收款二维码
                intent.putExtra("type", 0);//type:0:余额；1：支付宝；2：微信
                intent.putExtra("price", price);
                intent.putExtra("url", JsonConvertor.toJson(map));
                intent.putExtra("remark", remark);
                startActivity(intent);
                finish();
            }

            @Override
            public void onTV2Click() {
                float price = checkPrice();
                String remark = mHolder.getViewText(R.id.et_remark);
                wx_alipay_code(1, price, remark);
            }

            @Override
            public void onTV3Click() {
                float price = checkPrice();
                String remark = mHolder.getViewText(R.id.et_remark);
                wx_alipay_code(2, price, remark);
            }
        });
    }


    @Event(value = R.id.ll_pay_type)
    private void ll_pay_typeClick(View view) {
        choosePayType();
    }


    private void wx_alipay_code(final int type, final float price, String remark) {
        if (price <= 0)
            return;


        MemberApi memberApi = new MemberApiImpl();
        int uid = 0;

        showWaitDialog("正在生成二维码,请稍后...");

        String remark_1 = mHolder.getViewText(R.id.et_remark);

        memberApi.wx_alipay_code(uid, type, price, remark, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);

                try {
                    if (response != null) {
                        int code = response.getCode();
                        String msg = response.getMessage();
                        if (code == 200) {
                            String result = response.getResult().toString();
                            WxAlipayCode wxAlipayCode = JsonConvertor.fromJson(result, WxAlipayCode.class);
                            if (wxAlipayCode != null) {
                                Body body = wxAlipayCode.getBody();
                                if (body != null) {

                                    String remark = mHolder.getViewText(R.id.et_remark);
//                                    if (!CommFunAndroid.isNullOrEmpty(remark))
//                                        remark = "备注：" + remark;


                                    Intent intent = new Intent(SetPayPriceActivity.this, QRCodePayActivity.class);
                                    intent.putExtra("type", type);
                                    intent.putExtra("price", price);
                                    intent.putExtra("url", body.getQrCode());
                                    intent.putExtra("remark", remark);
                                    startActivity(intent);
                                    finish();
                                    //img_qr_code.setUrl(body.getQrCode());
                                }
                            }
                        } else {
                            showMsg(msg);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
