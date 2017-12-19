package com.ysy15350.easyquick.account;

/**
 * Created by yangshiyou on 2017/9/20.
 */


import android.content.Intent;
import android.widget.ImageView;

import com.ysy15350.easyquick.R;
import com.ysy15350.easyquick.broadcast.MyReceiver;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import base.BaseActivity;
import base.data.BaseData;
import base.data.entity.UserInfo;
import common.CommFunAndroid;
import common.string.JsonConvertor;
import custom_view.qrcode.CanvasRQ;

/**
 * 付款，收款二维码
 */
@ContentView(R.layout.activity_qr_code_pay)
public class QRCodePayActivity extends BaseActivity {

    @ViewInject(R.id.img_qr_code)
    private CanvasRQ img_qr_code;

    @ViewInject(R.id.img_pay_type)
    private ImageView img_pay_type;

    @Override
    public void initView() {

        MyReceiver.addReceiverListener(new MyReceiver.ReceiverListener() {
            @Override
            public void test(int type, String message) {
                showMsg(message);
                finish();
            }
        });

        super.initView();
        setFormHead("收款");

        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);//0:余额支付；1：支付宝；2：微信
        int user_type = intent.getIntExtra("user_type", 0);//type:1：商家；2：用户
        float price = intent.getFloatExtra("price", 0);
        String url = intent.getStringExtra("url");
        String remark = intent.getStringExtra("remark");

        if (CommFunAndroid.isNullOrEmpty(url) || price == 0) {
            showMsg("金额有误");
            this.finish();
        }

        String scanTypeStr = "";
        switch (type) {
            case 0:
                mHolder.setVisibility_VISIBLE(R.id.img_pay_type);
                img_pay_type.setImageResource(R.mipmap.icon_yuepay);
                scanTypeStr = "(趣支付扫描)";
                break;
            case 1:
                mHolder.setVisibility_VISIBLE(R.id.img_pay_type);
                img_pay_type.setImageResource(R.mipmap.icon_alipay);
                scanTypeStr = "(支付宝扫描)";
                break;
            case 2:
                mHolder.setVisibility_VISIBLE(R.id.img_pay_type);
                img_pay_type.setImageResource(R.mipmap.icon_wxpay);
                scanTypeStr = "(微信扫描)";
                break;
        }

        scanTypeStr = "";

        mHolder.setText(R.id.tv_price, String.format("%.2f 元%s", price, scanTypeStr));
        if (!CommFunAndroid.isNullOrEmpty(remark)) {
            mHolder.setText(R.id.tv_remark, remark);
        }

        String storeInfoJson = BaseData.getCache("storeInfo");
        if (!CommFunAndroid.isNullOrEmpty(storeInfoJson)) {
            UserInfo storeInfo = JsonConvertor.fromJson(storeInfoJson, UserInfo.class);
            if (storeInfo != null) {
                String name = storeInfo.getNickname();
//                if (CommFun.isNullOrEmpty(name))
//                    name = storeInfo.getFullname();
//                if (CommFun.isNullOrEmpty(name))
//                    name = storeInfo.getNickname();
                mHolder.setText(R.id.tv_nickName, name);
            }
        }

        img_qr_code.setUrl(url);

    }


}
