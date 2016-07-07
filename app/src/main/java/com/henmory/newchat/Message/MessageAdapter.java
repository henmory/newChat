package com.henmory.newchat.Message;

import android.content.Context;

import com.henmory.newchat.R;
import com.henmory.newchat.Common.CommonAdapter;
import com.henmory.newchat.Common.CommonViewHolder;

import java.util.List;

/**
 * Created by dan on 6/20/16.
 */
public class MessageAdapter extends CommonAdapter<Message> {

    public MessageAdapter(List<Message> messages, Context context, int layout_menu_item) {
        super(messages, context, layout_menu_item);
    }

    @Override
    public void convert(CommonViewHolder viewHolder, Message messages) {
        viewHolder.setText(R.id.message_item_phone_num_value, messages.getPhoneNum());
        viewHolder.setText(R.id.message_item_msg_id_value, messages.getMsgId() + "");
        viewHolder.setText(R.id.message_item_msg_content, messages.getMsgContent());
    }

}
