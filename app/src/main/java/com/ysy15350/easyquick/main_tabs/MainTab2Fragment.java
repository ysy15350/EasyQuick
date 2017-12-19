package com.ysy15350.easyquick.main_tabs;

import android.content.Intent;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.easyquick.R;
import com.ysy15350.easyquick.adapters.ListViewAdpater_Order;
import com.ysy15350.easyquick.invite.InviteUserTradeRecordActivity;
import com.ysy15350.easyquick.others.PrinterTestActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.util.ArrayList;
import java.util.List;

import api.base.model.Response;
import api.model.OrderInfo;
import base.mvp.MVPBaseListViewFragment;
import common.CommFunAndroid;


/**
 * Created by yangshiyou on 2017/8/30.
 */

@ContentView(R.layout.activity_main_tab2)
public class MainTab2Fragment extends MVPBaseListViewFragment<MainTab2ViewInterface, MainTab2Presenter>
        implements MainTab2ViewInterface {

    public MainTab2Fragment() {
    }

    @Override
    public MainTab2Presenter createPresenter() {
        // TODO Auto-generated method stub
        return new MainTab2Presenter(getActivity());
    }

    @Override
    public void initView() {
        // TODO Auto-generated method stub
        super.initView();
        xListView.setDividerHeight(CommFunAndroid.dip2px(0));
        setFormHead("订单");
        setBtnBack(false);
    }


    @Event(value = R.id.btn_invite_user_trade_record_detail)
    private void btn_invite_user_trade_record_detailClick(View view) {
        startActivity(new Intent(getActivity(), InviteUserTradeRecordActivity.class));
    }


    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        page = 1;
        initData(page, pageSize);
    }

    String start_time;
    String end_time;

    @Override
    public void initData(int page, int pageSize) {
        showWaitDialog("数据加载中...");
        mPresenter.order_list(start_time, end_time, page, pageSize);
    }

    ListViewAdpater_Order mAdapter;
    List<OrderInfo> mList = new ArrayList<>();


    @Override
    public void order_listCallback(boolean isCache, Response response) {

        try {
            if (response != null) {

                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {

                    float total_price = response.getTotal_price();
                    mHolder.setText(R.id.tv_total_price, String.format("%.2f", total_price));

                    List<OrderInfo> list = response.getData(new TypeToken<List<OrderInfo>>() {
                    }.getType());

                    bindData(list);

                } else showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bindData(List<OrderInfo> list) {


        if (page == 1) {
            mList.clear();
        } else {
            if (list == null || list.isEmpty()) {
                showMsg("没有更多了");
                xListView.stopLoadMore();
            }
        }

        if (list != null)
            mList.addAll(list);
        mAdapter = new ListViewAdpater_Order(mContext, mList);

        bindListView(mAdapter);// 调用父类绑定数据方法

        if (list != null || !list.isEmpty()) {
            page++;
        }
    }
}
