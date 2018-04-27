package com.kcrason.highperformancefriendscircle;

/**
 * @author KCrason
 * @date 2018/4/27
 */
public class Constants {

    public static String[] urls = new String[]{
            "IMG_6410_Thumbnail", "IMG_6412_Thumbnail", "IMG_6415_Thumbnail", "IMG_6430_Thumbnail", "IMG_6460_Thumbnail",
            "IMG_6521_Thumbnail", "IMG_6522_Thumbnail", "IMG_6559_Thumbnail", "IMG_6565_Thumbnail", "IMG_6590_Thumbnail",
            "IMG_6613_Thumbnail", "IMG_6623_Thumbnail", "IMG_6626_Thumbnail", "IMG_6630_Thumbnail", "IMG_6633_Thumbnail",
            "IMG_6643_Thumbnail", "IMG_6644_Thumbnail", "IMG_6646_Thumbnail", "IMG_6671_Thumbnail", "IMG_6673_Thumbnail",
    };

    public final static class FriendCircleType {
        //纯文字
        public final static int FRIEND_CIRCLE_TYPE_ONLY_WORD = 0;
        //文字和图片
        public final static int FRIEND_CIRCLE_TYPE_WORD_AND_IMAGES = 1;
        //分享链接
        public final static int FRIEND_CIRCLE_TYPE_WORD_AND_URL = 2;
    }

    public final static class CommentType {
        //单一评论
        public final static int COMMENT_TYPE_SINGLE = 0;
        //回复评论
        public final static int COMMENT_TYPE_REPLY = 1;
    }

}
