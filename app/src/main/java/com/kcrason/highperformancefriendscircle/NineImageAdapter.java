package com.kcrason.highperformancefriendscircle;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * @author KCrason
 * @date 2018/4/27
 */
public class NineImageAdapter implements NineGridView.NineGridAdapter<ImageBean> {


    private List<ImageBean> mImageBeans;

    private Context mContext;

    private int mItemSize;


    public NineImageAdapter(Context context, int itemSize, List<ImageBean> imageBeans) {
        this.mContext = context;
        this.mItemSize = itemSize;
        this.mImageBeans = imageBeans;
    }

    @Override
    public int getCount() {
        return mImageBeans == null ? 0 : mImageBeans.size();
    }

    @Override
    public ImageBean getItem(int position) {
        return mImageBeans == null ? null :
                position < mImageBeans.size() ? mImageBeans.get(position) : null;
    }

    @Override
    public View getView(int position, View itemView) {
        ImageView imageView = null;
        if (itemView == null) {
            imageView = new ImageView(mContext);
        } else {
            imageView = (ImageView) itemView;
        }

        if (mItemSize > 0) {
            Glide.with(mContext).load(mImageBeans.get(position).getImageUrl())
                    .apply(new RequestOptions().centerCrop()
                            .placeholder(R.drawable.avatar_icon).override(mItemSize, mItemSize))
                    .into(imageView);
        }
        return imageView;
    }
}
