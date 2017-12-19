package com.ysy15350.easyquick.others;

import android.view.View;

import com.ysy15350.easyquick.R;
import com.ysy15350.easyquick.print.ShangMiPrintHelper;
import com.ysy15350.easyquick.print.ShengTengHelper;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import base.BaseActivity;
import common.message.CommFunMessage;

/**
 * Created by yangshiyou on 2017/10/19.
 */

@ContentView(R.layout.activity_printer_test)
public class PrinterTestActivity extends BaseActivity {


    @Override
    public void initView() {

        super.initView();
        setFormHead("打印测试");

    }

    @Override
    protected void btn_okOnClick(View view) {
        super.btn_okOnClick(view);

        ShangMiPrintHelper.print("标题表头", "内容内容内容");

    }

    /**
     * 清除缓存
     *
     * @param view
     */
    @Event(value = R.id.btn_ok1)
    private void btn_ok1Click(View view) {
        CommFunMessage.showMsgBox("sdf");
        ShengTengHelper.getInstance().print("趣收款", "收款人：商家\n订单号：T20134345\n收款备注：内容内容内容");
    }
}
