package com.kcrason.highperformancefriendscircle;

import java.util.List;

public class FriendCircleBean {

    private int viewType;

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<CommentBean> getCommentBeans() {
        return commentBeans;
    }

    public void setCommentBeans(List<CommentBean> commentBeans) {
        this.commentBeans = commentBeans;
    }

    public List<PraiseBean> getPraiseBeans() {
        return praiseBeans;
    }

    public void setPraiseBeans(List<PraiseBean> praiseBeans) {
        this.praiseBeans = praiseBeans;
    }

    public List<ImageBean> getImageBeans() {
        return imageBeans;
    }

    public void setImageBeans(List<ImageBean> imageBeans) {
        this.imageBeans = imageBeans;
    }

    private String userId;

    private String content;

    private List<CommentBean> commentBeans;

    private List<PraiseBean> praiseBeans;

    private List<ImageBean> imageBeans;


}
