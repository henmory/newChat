package com.henmory.newchat.Common;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dan on 6/20/16.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    private List<T> datas;
    private Context context;
    private int layout_menu_item;

    public CommonAdapter(List<T> datas, Context context, int layout_menu_item) {
        this.datas = datas;
        this.context = context;
        this.layout_menu_item = layout_menu_item;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonViewHolder viewHolder = CommonViewHolder.getViewHolder(context, position, convertView, parent, layout_menu_item);

        convert(viewHolder, getItem(position));

        return viewHolder.getConvertView(); //return convertView that is every item
    }

    public abstract void convert(CommonViewHolder viewHolder, T messages);


    /*
   * 范型数据增删
   * */
    public void addListElements(List<T> data) {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        for (T t: data) {
            datas.add(t);

        }
        notifyDataSetChanged();
    }
    public void add(T data) {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        datas.add(data);
        notifyDataSetChanged();
    }

    public void add(T data, int position) {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        datas.add(position, data);
        notifyDataSetChanged();
    }

    public void delete(T data) {
        if (datas != null) {
            datas.remove(data);
        }
        notifyDataSetChanged();
    }

    public void delete(int position) {
        if (datas != null) {
            datas.remove(position);
        }
        notifyDataSetChanged();
    }

}
