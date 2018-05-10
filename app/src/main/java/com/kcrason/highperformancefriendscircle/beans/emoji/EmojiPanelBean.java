package com.kcrason.highperformancefriendscircle.beans.emoji;

import java.util.List;

/**
 * @author KCrason
 * @date 2018/5/9
 */
public class EmojiPanelBean {
    public int getEmojiType() {
        return emojiType;
    }

    public void setEmojiType(int emojiType) {
        this.emojiType = emojiType;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public List<EmojiBean> getEmojiBeansPerPage() {
        return emojiBeansPerPage;
    }

    public void setEmojiBeansPerPage(List<EmojiBean> emojiBeansPerPage) {
        this.emojiBeansPerPage = emojiBeansPerPage;
    }

    /**
     * emoji类型
     */
    private int emojiType;

    /**
     * 同一种类型的emoji需要占用的最大页数
     */
    private int maxPage;

    /**
     * 每一页emoji的数量
     */
    private List<EmojiBean> emojiBeansPerPage;
}
