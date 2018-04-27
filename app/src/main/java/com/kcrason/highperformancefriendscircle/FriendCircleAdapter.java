package com.kcrason.highperformancefriendscircle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KCrason
 * @date 2018/4/27
 */
public class FriendCircleAdapter extends RecyclerView.Adapter<FriendCircleAdapter.BaseFriendCircleViewHolder> {

    private Context mContext;

    private LayoutInflater mLayoutInflater;

    public FriendCircleAdapter(Context context) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }


    @Override
    public BaseFriendCircleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_ONLY_WORD) {
            return new OnlyWordViewHolder(mLayoutInflater.inflate(R.layout.item_recycler_firend_circle_only_word, parent, false));
        } else if (viewType == Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_WORD_AND_URL) {
            return new WordAndUrlViewHolder(mLayoutInflater.inflate(R.layout.item_recycler_firend_circle_word_and_url, parent, false));
        } else if (viewType == Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_WORD_AND_IMAGES) {
            return new WordAndImagesViewHolder(mLayoutInflater.inflate(R.layout.item_recycler_firend_circle_word_and_images, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseFriendCircleViewHolder holder, int position) {
        if (holder != null) {
            if (holder instanceof OnlyWordViewHolder) {
                OnlyWordViewHolder onlyWordViewHolder = (OnlyWordViewHolder) holder;
            } else if (holder instanceof WordAndUrlViewHolder) {

            } else if (holder instanceof WordAndImagesViewHolder) {
                WordAndImagesViewHolder wordAndImagesViewHolder = (WordAndImagesViewHolder) holder;
                int itemSize = wordAndImagesViewHolder.nineGridView.getChildWidth();
                wordAndImagesViewHolder.nineGridView.setAdapter(new NineImageAdapter(mContext, itemSize, makeImages()));
            }
        }
    }

    private List<ImageBean> makeImages() {
        List<ImageBean> imageBeans = new ArrayList<>();
        int randomCount = (int) (Math.random() * 9);
        if (randomCount == 0) {
            randomCount = randomCount + 2;
        } else if (randomCount == 1) {
            randomCount = randomCount + 1;
        }
        for (int i = 0; i < randomCount; i++) {
            ImageBean imageBean = new ImageBean();
            imageBean.setImageUrl("http://oh2qy1yiv.bkt.clouddn.com/" + Constants.urls[(int) (Math.random() * 25)]);
            imageBeans.add(imageBean);
        }
        return imageBeans;
    }

    @Override
    public int getItemViewType(int position) {
        int randomValue = (int) (Math.random() * 300);
        if (randomValue < 100) {
            return Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_ONLY_WORD;
        } else if (randomValue < 200) {
            return Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_WORD_AND_URL;
        }
        return Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_WORD_AND_IMAGES;
    }

    @Override
    public int getItemCount() {
        return 300;
    }

    static class WordAndImagesViewHolder extends BaseFriendCircleViewHolder {

        NineGridView nineGridView;

        public WordAndImagesViewHolder(View itemView) {
            super(itemView);
            nineGridView = itemView.findViewById(R.id.nine_grid_view);
        }
    }


    static class WordAndUrlViewHolder extends BaseFriendCircleViewHolder {

        public WordAndUrlViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class OnlyWordViewHolder extends BaseFriendCircleViewHolder {

        public OnlyWordViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class BaseFriendCircleViewHolder extends RecyclerView.ViewHolder {

        public BaseFriendCircleViewHolder(View itemView) {
            super(itemView);
        }
    }
}
