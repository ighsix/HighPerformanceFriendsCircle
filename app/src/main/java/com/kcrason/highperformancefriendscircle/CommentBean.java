package com.kcrason.highperformancefriendscircle;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;

public class CommentBean {

    private int commentType;

    public int getCommentType() {
        return commentType;
    }

    public void setCommentType(int commentType) {
        this.commentType = commentType;
    }

    private String parentUserName;

    private String childUserName;

    public String getParentUserName() {
        return parentUserName;
    }

    public void setParentUserName(String parentUserName) {
        this.parentUserName = parentUserName;
    }

    public String getChildUserName() {
        return childUserName;
    }

    public void setChildUserName(String childUserName) {
        this.childUserName = childUserName;
    }

    private int parentUserId;


    public int getParentUserId() {
        return parentUserId;
    }

    public void setParentUserId(int parentUserId) {
        this.parentUserId = parentUserId;
    }

    public int getChildUserId() {
        return childUserId;
    }

    public void setChildUserId(int childUserId) {
        this.childUserId = childUserId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    private int childUserId;

    private String commentContent;


    /**
     * 富文本内容
     */
    private SpannableStringBuilder richTextCommentContent;

    public void build(Context context) {
        if (commentType == Constants.CommentType.COMMENT_TYPE_SINGLE) {
            richTextCommentContent = makeSingleCommentRichText(context, childUserName, commentContent);
        } else {
            richTextCommentContent = makeReplyCommentRichText(context, parentUserName, childUserName, commentContent);
        }
    }

    public SpannableStringBuilder getRichTextCommentContent() {
        return richTextCommentContent;
    }

    public static SpannableStringBuilder makeSingleCommentRichText(Context context, String childUserName, String commentContent) {
        String richText = String.format("%s:%s", childUserName, commentContent);
        SpannableStringBuilder builder = new SpannableStringBuilder(richText);
        if (!TextUtils.isEmpty(childUserName)) {
            builder.setSpan(new UserNameClickableSpan(context, childUserName), 0, childUserName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }


    public static SpannableStringBuilder makeReplyCommentRichText(Context context, String parentUserName, String childUserName, String commentContent) {
        String richText = String.format("%s回复%s:%s", parentUserName, childUserName, commentContent);
        SpannableStringBuilder builder = new SpannableStringBuilder(richText);
        int parentEnd = 0;
        if (!TextUtils.isEmpty(parentUserName)) {
            parentEnd = parentUserName.length();
            builder.setSpan(new UserNameClickableSpan(context, parentUserName), 0, parentEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        if (!TextUtils.isEmpty(childUserName)) {
            int childStart = parentEnd + 2;
            int childEnd = childStart + childUserName.length();
            builder.setSpan(new UserNameClickableSpan(context, childUserName), childStart, childEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }

}
