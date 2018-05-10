package com.kcrason.highperformancefriendscircle.beans.emoji;

/**
 * @author KCrason
 * @date 2018/5/9
 */
public class EmojiIndicatorInfo {
    public EmojiIndicatorInfo(int emojiType, int indicatorIndex) {
        this.emojiType = emojiType;
        this.indicatorIndex = indicatorIndex;
    }

    private int emojiType;

    public int getEmojiType() {
        return emojiType;
    }

    public int getIndicatorIndex() {
        return indicatorIndex;
    }

    private int indicatorIndex;
}
