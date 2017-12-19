package com.ysy15350.easyquick.invite.tabs;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.easyquick.R;
import com.ysy15350.easyquick.account.ProfitDetailListActivity;
import com.ysy15350.easyquick.account.RechargeActivity;
import com.ysy15350.easyquick.account.WithdrawActivity;
import com.ysy15350.easyquick.adapters.ListViewAdpater_Order;
import com.ysy15350.easyquick.adapters.ListViewAdpater_PersonWater;
import com.ysy15350.easyquick.invite.InviteUserTradeRecordActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import api.base.model.Config;
import api.base.model.Response;
import api.model.OrderInfo;
import api.model.PersonWater;
import base.data.BaseData;
import base.data.ConfigHelper;
import base.data.entity.UserInfo;
import base.mvp.MVPBaseFragment;
import base.mvp.MVPBaseListViewFragment;
import common.CommFunAndroid;
import custom_view.progress_bar.CircleBar;


/**
 * Created by yangshiyou on 2017/8/30.
 */

@ContentView(R.layout.fragment_list)
public class InviteTab1Fragment extends MVPBaseListViewFragment<InviteTab1ViewInterface, InviteTab1Presenter>
        implements InviteTab1ViewInterface {

    private static final String TAG = "InviteTab1Fragment";

    private static int count = 0;

    public InviteTab1Fragment() {
    }


    @Override
    public InviteTab1Presenter createPresenter() {
        // TODO Auto-generated method stub
        return new InviteTab1Presenter(getActivity());
    }


    @ViewInject(R.id.myProgress)
    private CircleBar myProgress;

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
        mPresenter.personWater(page, pageSize);
    }

    ListViewAdpater_PersonWater mAdapter;
    List<PersonWater> mList = new ArrayList<>();


    @Override
    public void personWaterCallback(boolean isCache, Response response) {

        try {
            if (response != null) {

                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {

                    float total_price = response.getTotal_price();
                    mHolder.setText(R.id.tv_total_price, String.format("%.2f", total_price));

                    List<PersonWater> list = response.getData(new TypeToken<List<PersonWater>>() {
                    }.getType());

                    bindData(list);

                } else showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void bindData(List<PersonWater> list) {


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
        mAdapter = new ListViewAdpater_PersonWater(mContext, mList);

        bindListView(mAdapter);// 调用父类绑定数据方法

        if (list != null || !list.isEmpty()) {
            page++;
        }
    }
}
