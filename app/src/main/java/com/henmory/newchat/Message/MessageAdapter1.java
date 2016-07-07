package com.henmory.newchat.Message;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.henmory.newchat.Common.CommonViewHolder;

import java.util.List;

/**
 * Created by dan on 6/20/16.
 * it's a temprorary file,and CommonAdapter is the final file
 */
public abstract class MessageAdapter1 extends BaseAdapter {

    private List<Message> messages;
    private Context context;
    private int layout_menu_item;

    public MessageAdapter1(List<Message> messages, Context context, int layout_menu_item) {
        this.messages = messages;
        this.context = context;
        this.layout_menu_item = layout_menu_item;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonViewHolder viewHolder = CommonViewHolder.getViewHolder(context, position, convertView, parent, layout_menu_item);

        convert(viewHolder, (Message)getItem(position));

        return viewHolder.getConvertView(); //return convertView that is every item
    }

    public abstract void convert(CommonViewHolder viewHolder, Message messages);



}
