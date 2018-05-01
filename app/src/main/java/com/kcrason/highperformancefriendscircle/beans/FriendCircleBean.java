package com.kcrason.highperformancefriendscircle.beans;

import android.text.SpannableStringBuilder;

import java.util.List;

public class FriendCircleBean {

    private int viewType;

    private String content;

    private List<CommentBean> commentBeans;

    private List<PraiseBean> praiseBeans;

    private List<ImageBean> imageBeans;

    private UserBean userBean;

    private OtherInfoBean otherInfoBean;

    private boolean isShowPraise;

    private boolean isShowComment;


    private boolean isShowContentTranslation;

    public boolean isShowContentTranslation() {
        return isShowContentTranslation;
    }

    public void setShowContentTranslation(boolean showContentTranslation) {
        isShowContentTranslation = showContentTranslation;
    }

    public boolean isShowComment() {
        return isShowComment;
    }

    public boolean isShowPraise() {
        return isShowPraise;
    }

    public OtherInfoBean getOtherInfoBean() {
        return otherInfoBean;
    }

    public void setOtherInfoBean(OtherInfoBean otherInfoBean) {
        this.otherInfoBean = otherInfoBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getContent() {
        return content;
    }

    public SpannableStringBuilder getContentSpan() {
        return contentSpan;
    }

    public void setContentSpan(SpannableStringBuilder contentSpan) {
        this.contentSpan = contentSpan;
    }

    private SpannableStringBuilder contentSpan;


    public void setContent(String content) {
        this.content = content;
        setContentSpan(new SpannableStringBuilder(content));
    }

    public List<CommentBean> getCommentBeans() {
        return commentBeans;
    }

    public void setCommentBeans(List<CommentBean> commentBeans) {
        isShowComment = commentBeans != null && commentBeans.size() > 0;
        this.commentBeans = commentBeans;
    }

    public List<PraiseBean> getPraiseBeans() {
        return praiseBeans;
    }

    public void setPraiseBeans(List<PraiseBean> praiseBeans) {
        isShowPraise = praiseBeans != null && praiseBeans.size() > 0;
        this.praiseBeans = praiseBeans;
    }

    public List<ImageBean> getImageBeans() {
        return imageBeans;
    }

    public void setImageBeans(List<ImageBean> imageBeans) {
        this.imageBeans = imageBeans;
    }


    public SpannableStringBuilder getPraiseUserNameRichText() {
        return praiseUserNameRichText;
    }

    public void setPraiseUserNameRichText(SpannableStringBuilder praiseUserNameRichText) {
        this.praiseUserNameRichText = praiseUserNameRichText;
    }

    private SpannableStringBuilder praiseUserNameRichText;


}
