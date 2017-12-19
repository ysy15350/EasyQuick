package com.ysy15350.easyquick.adapters;

import android.content.Context;

import com.ysy15350.easyquick.R;

import java.util.List;

import base.adapter.CommonAdapter;
import base.adapter.ViewHolder;

/**
 * 车辆品牌gridview
 *
 * @author yangshiyou
 */
public class ListViewAdpater_Image extends CommonAdapter<String> {


    public ListViewAdpater_Image(Context context, List<String> list) {
        super(context, list, R.layout.list_item_img);


    }

    @Override
    public void convert(ViewHolder holder, String t) {
        if (t != null) {

            holder.setImageURL(R.id.img,
                    t, 480, 480);

        }

    }


}
