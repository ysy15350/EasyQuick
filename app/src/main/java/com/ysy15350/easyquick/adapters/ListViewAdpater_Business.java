package com.ysy15350.easyquick.adapters;

import android.content.Context;

import com.ysy15350.easyquick.R;

import java.util.List;

import api.model.StoreCate;
import base.adapter.CommonAdapter;
import base.adapter.ViewHolder;

/**
 * 选择主营范围
 *
 * @author yangshiyou
 */
public class ListViewAdpater_Business extends CommonAdapter<StoreCate> {


    public ListViewAdpater_Business(Context context, List<StoreCate> list) {
        super(context, list, R.layout.list_item_business);


    }

    @Override
    public void convert(ViewHolder holder, StoreCate t) {
        if (t != null) {
            holder.setText(R.id.tv_title, t.getTitle());
        }

    }


}
