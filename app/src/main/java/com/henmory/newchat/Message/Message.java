package com.henmory.newchat.Message;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;


/**
 * Created by dan on 6/20/16.
 */
public class Message  implements Serializable{

    private String phoneNum;
    private int msgId;
    private String msgContent;

    public Message() {
    }

    public Message(String phoneNum, int msgId, String msgContent) {
        this.phoneNum = phoneNum;
        this.msgId = msgId;
        this.msgContent = msgContent;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgIdd) {
        this.msgId = msgId;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(phoneNum);
//        dest.writeInt(msgId);
//        dest.writeString(msgContent);
//    }
//
//    //3、自定义类型中必须含有一个名称为CREATOR的静态成员，该成员对象要求实现Parcelable.Creator接口及其方法
//    public static final Parcelable.Creator<Message> CREATOR =
//            new Parcelable.Creator<Message>() {
//        @Override
//        public Message createFromParcel(Parcel source) {
//            //从Parcel中读取数据
//            //此处read顺序依据write顺序
//            return new Message(source.readString(), source.readInt(), source.readString());
//        }
//        @Override
//        public Message[] newArray(int size) {
//
//            return new Message[size];
//        }
//
//    };

    @Override
    public String toString() {
        return "phone_num = " + phoneNum + "; msg_content = " + msgContent + "; msg_id = " + msgId;
    }
}
