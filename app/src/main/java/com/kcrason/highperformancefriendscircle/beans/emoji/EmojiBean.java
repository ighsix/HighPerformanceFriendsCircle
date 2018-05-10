package com.kcrason.highperformancefriendscircle.beans.emoji;

import java.io.Serializable;

public class EmojiBean implements Serializable {

    private String emojiName;
    private int emojiResource;

    public String getEmojiName() {
        return emojiName;
    }

    public void setEmojiName(String emojiName) {
        this.emojiName = emojiName;
    }

    public int getEmojiResource() {
        return emojiResource;
    }

    public void setEmojiResource(int emojiResource) {
        this.emojiResource = emojiResource;
    }
}
