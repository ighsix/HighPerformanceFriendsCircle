package com.kcrason.highperformancefriendscircle.beans.emoji;

import java.util.List;

/**
 * @author KCrason
 * @date 2018/5/9
 */
public class EmojiDataSource {

    private int emojiType;

    public int getEmojiType() {
        return emojiType;
    }

    public void setEmojiType(int emojiType) {
        this.emojiType = emojiType;
    }

    public List<EmojiBean> getEmojiList() {
        return emojiList;
    }

    public void setEmojiList(List<EmojiBean> emojiList) {
        this.emojiList = emojiList;
    }

    private List<EmojiBean> emojiList;

}
