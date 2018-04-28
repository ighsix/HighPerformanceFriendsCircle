package com.kcrason.highperformancefriendscircle;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
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

    private RequestOptions mRequestOptions;
    private int mImageBackgroundColor = Color.parseColor("#f2f2f2");


    public NineImageAdapter(Context context, List<ImageBean> imageBeans) {
        this.mContext = context;
        this.mItemSize = (Utils.getScreenWidth(context) -
                2 * Utils.dp2px(context, 4) - Utils.dp2px(context, 54)) /3;
        this.mRequestOptions = new RequestOptions().centerCrop().override(mItemSize);
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
        ImageView imageView;
        if (itemView == null) {
            imageView = new ImageView(mContext);
            imageView.setBackgroundColor(mImageBackgroundColor);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        } else {
            imageView = (ImageView) itemView;
        }
        String url = mImageBeans.get(position).getImageUrl();
        Glide.with(mContext).load(url).apply(mRequestOptions).transition(DrawableTransitionOptions.withCrossFade()).into(imageView);
        return imageView;
    }
}
