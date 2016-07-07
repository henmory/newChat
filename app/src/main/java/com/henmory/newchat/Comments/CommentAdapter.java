package com.henmory.newchat.Comments;

import android.content.Context;

import com.henmory.newchat.R;
import com.henmory.newchat.Common.CommonAdapter;
import com.henmory.newchat.Common.CommonViewHolder;

import java.util.List;

/**
 * Created by dan on 6/20/16.
 */
public class CommentAdapter extends CommonAdapter<Comments> {


    public CommentAdapter(List<Comments> datas, Context context, int layout_menu_item) {
        super(datas, context, layout_menu_item);
    }

    @Override
    public void convert(CommonViewHolder viewHolder, Comments messages) {
        viewHolder.setText(R.id.comment_item_comment_content, messages.getContent());
        viewHolder.setText(R.id.comment_item_phone_num_value, messages.getPhoneNum());
        viewHolder.setText(R.id.comment_item_time, messages.getTime());

    }
}