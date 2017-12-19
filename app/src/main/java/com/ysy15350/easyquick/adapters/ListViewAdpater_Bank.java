package com.ysy15350.easyquick.adapters;

import android.content.Context;

import com.ysy15350.easyquick.R;

import java.util.List;

import api.model.BankInfo;
import base.adapter.CommonAdapter;
import base.adapter.ViewHolder;

/**
 * 余额明细
 *
 * @author yangshiyou
 */
public class ListViewAdpater_Bank extends CommonAdapter<BankInfo> {


    public ListViewAdpater_Bank(Context context, List<BankInfo> list) {
        super(context, list, R.layout.list_item_bank);


    }

    @Override
    public void convert(ViewHolder holder, BankInfo t) {
        if (t != null) {
            holder.setText(R.id.tv_title, t.getTitle());

        }

    }


}
