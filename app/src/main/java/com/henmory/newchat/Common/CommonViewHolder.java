package com.henmory.newchat.Common;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by dan on 6/20/16.
 */
public class CommonViewHolder {

    private View convertView;
    private SparseArray<View> views;
    private int position;

    private CommonViewHolder(Context context, int position, ViewGroup parent, int res) {
        views = new SparseArray<>();
        convertView = LayoutInflater.from(context).inflate(res, parent, false);
        convertView.setTag(this);
        this.position = position;
    }

    public static CommonViewHolder getViewHolder(Context context, int position, View convertView, ViewGroup parent, int res){
        CommonViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new CommonViewHolder(context, position, parent, res);

        }else{
            viewHolder = (CommonViewHolder) convertView.getTag();
            viewHolder.position = position;
        }
        return viewHolder;
    }
    public View getConvertView(){
        return convertView;
    }
    public View getView(int view_id){
        View view = views.get(view_id);
        if (view == null) {
            view = convertView.findViewById(view_id);
            views.put(view_id, view);
        }
        return view;
    }
    public void setText(int view_id, CharSequence text){
        TextView tv = (TextView) getView(view_id);
        tv.setText(text);
    }


}
