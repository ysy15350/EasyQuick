package com.ysy15350.easyquick.account;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.easyquick.R;
import com.ysy15350.easyquick.adapters.ListViewAdpater_Business;

import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.List;

import api.base.model.Response;
import api.model.StoreCate;
import base.data.BaseData;
import base.mvp.MVPBaseListViewActivity;
import common.CommFunAndroid;
import common.string.JsonConvertor;

/**
 * Created by yangshiyou on 2017/9/21.
 */

/**
 * 选择主营范围
 */
@ContentView(R.layout.activity_list)
public class MainBusinessListActivity extends MVPBaseListViewActivity<MainBusinessListViewInterface, MainBusinessListPresenter>
        implements MainBusinessListViewInterface {

    @Override
    protected MainBusinessListPresenter createPresenter() {

        return new MainBusinessListPresenter(MainBusinessListActivity.this);
    }

    @Override
    public void initView() {

        super.initView();
        setFormHead("选择主营范围");
//        setMenu(R.mipmap.icon_add_bank, "添加");
        setMenuText("确定");
        xListView.setDividerHeight(CommFunAndroid.dip2px(1)); // 设置间距高度(此必须设置在setDivider（）之后，否则无效果)
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        String store_cateJson = BaseData.getCache("store_cate");
        if (!CommFunAndroid.isNullOrEmpty(store_cateJson)) {
            List<StoreCate> list = JsonConvertor.fromJson(store_cateJson, new TypeToken<List<StoreCate>>() {
            }.getType());

            bindData(list);
        }

        page = 1;
        initData(page, pageSize);
    }

    @Override
    public void initData(int page, int pageSize) {
        mPresenter.store_cate(page, pageSize);
    }

    ListViewAdpater_Business mAdapter;
    List<StoreCate> mList = new ArrayList<>();

    @Override
    public void store_cateCallback(boolean isCache, Response response) {

        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    BaseData.setCache("store_cate", response.getResultJson());
                    List<StoreCate> list = response.getData(new TypeToken<List<StoreCate>>() {
                    }.getType());
                    bindData(list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void bindData(List<StoreCate> list) {


        if (page == 1) {
            mList.clear();
        } else {
            if (list == null) {
                showMsg("没有更多了");
                xListView.stopLoadMore();
            } else if (list.isEmpty()) {
                showMsg("没有更多了");
                xListView.stopLoadMore();
            }
        }

        if (list != null)
            mList.addAll(list);
        mAdapter = new ListViewAdpater_Business(this, mList);

        bindListView(mAdapter);// 调用父类绑定数据方法

        if (list != null && !list.isEmpty()) {
            page++;
        }
    }

    /**
     * 标题栏菜单点击事件，确定选择分类
     *
     * @param view
     */
    @Override
    protected void ll_menuOnClick(View view) {
        super.ll_menuOnClick(view);

        getCheckedIds();
    }

    private void getCheckedIds() {
        String ids = "";
        String titles = "";

        try {
            if (mList != null && !mList.isEmpty()) {
                for (StoreCate storeCate :
                        mList) {
                    if (storeCate != null) {
                        boolean isChecked = storeCate.isChecked();
                        if (isChecked) {
                            ids = ids + storeCate.getId() + ",";
                            titles = titles + storeCate.getTitle() + ",";
                        }
                    }
                }

                if (null != ids && ids.length() > 0)
                    ids = ids.substring(0, ids.length() - 1);
                if (null != titles && titles.length() > 0)
                    titles = titles.substring(0, titles.length() - 1);

                Intent intent = getIntent();
                intent.putExtra("ids", ids);
                intent.putExtra("titles", titles);
                setResult(RESULT_OK, intent);

                this.finish();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        StoreCate storeCate = (StoreCate) parent.getItemAtPosition(position);

        View img_checked = view.findViewById(R.id.img_checked);
        if (img_checked != null) {
            if (storeCate.isChecked()) {
                img_checked.setVisibility(View.GONE);
            } else {
                img_checked.setVisibility(View.VISIBLE);
            }
            storeCate.setChecked(!storeCate.isChecked());

        }
    }
}


