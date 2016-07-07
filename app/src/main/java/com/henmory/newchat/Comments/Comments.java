package com.henmory.newchat.Comments;

/**
 * Created by dan on 6/20/16.
 */
public class Comments {

    private String mPhoneNum;
    private String mContent;
    private String mTime;

    public Comments() {
    }

    public Comments(String mPhoneNum, String mContent, String mTime) {
        this.mPhoneNum = mPhoneNum;
        this.mContent = mContent;
        this.mTime = mTime;
    }

    public String getPhoneNum() {
        return mPhoneNum;
    }

    public void setPhoneNum(String mPhoneNum) {
        this.mPhoneNum = mPhoneNum;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String mTime) {
        this.mTime = mTime;
    }
}
