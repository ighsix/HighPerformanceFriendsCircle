package com.kcrason.highperformancefriendscircle.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.kcrason.highperformancefriendscircle.Constants;
import com.kcrason.highperformancefriendscircle.enums.TranslationState;
import com.kcrason.highperformancefriendscircle.interfaces.OnItemClickPopupMenuListener;
import com.kcrason.highperformancefriendscircle.interfaces.OnPraiseOrCommentClickListener;
import com.kcrason.highperformancefriendscircle.interfaces.OnTranslationListener;
import com.kcrason.highperformancefriendscircle.widgets.CommentOrPraisePopupWindow;
import com.kcrason.highperformancefriendscircle.widgets.NineGridView;
import com.kcrason.highperformancefriendscircle.R;
import com.kcrason.highperformancefriendscircle.Utils;
import com.kcrason.highperformancefriendscircle.beans.FriendCircleBean;
import com.kcrason.highperformancefriendscircle.beans.OtherInfoBean;
import com.kcrason.highperformancefriendscircle.beans.UserBean;
import com.kcrason.highperformancefriendscircle.span.TextMovementMothod;
import com.kcrason.highperformancefriendscircle.widgets.VerticalCommentWidget;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KCrason
 * @date 2018/4/27
 */
public class FriendCircleAdapter extends RecyclerView.Adapter<FriendCircleAdapter.BaseFriendCircleViewHolder>
        implements OnTranslationListener, OnItemClickPopupMenuListener {

    private Context mContext;

    private LayoutInflater mLayoutInflater;

    private List<FriendCircleBean> mFriendCircleBeans;

    private RequestOptions mRequestOptions;

    private int mAvatarSize;

    private DrawableTransitionOptions mDrawableTransitionOptions;

    private CommentOrPraisePopupWindow mCommentOrPraisePopupWindow;

    private OnPraiseOrCommentClickListener mOnPraiseOrCommentClickListener;

    private LinearLayoutManager mLayoutManager;

    private RecyclerView mRecyclerView;

    public FriendCircleAdapter(Context context, RecyclerView recyclerView) {
        this.mContext = context;
        mRecyclerView = recyclerView;
        this.mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        this.mAvatarSize = Utils.dp2px(context, 44f);
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mRequestOptions = new RequestOptions().centerCrop();
        this.mDrawableTransitionOptions = DrawableTransitionOptions.withCrossFade();
        if (context instanceof OnPraiseOrCommentClickListener) {
            this.mOnPraiseOrCommentClickListener = (OnPraiseOrCommentClickListener) context;
        }
    }

    public void setFriendCircleBeans(List<FriendCircleBean> friendCircleBeans) {
        this.mFriendCircleBeans = friendCircleBeans;
        notifyDataSetChanged();
    }

    public void addFriendCircleBeans(List<FriendCircleBean> friendCircleBeans) {
        if (friendCircleBeans != null) {
            if (mFriendCircleBeans == null) {
                mFriendCircleBeans = new ArrayList<>();
            }
            this.mFriendCircleBeans.addAll(friendCircleBeans);
            notifyItemRangeInserted(mFriendCircleBeans.size(), friendCircleBeans.size());
        }
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
        if (holder != null && mFriendCircleBeans != null && position < mFriendCircleBeans.size()) {
            FriendCircleBean friendCircleBean = mFriendCircleBeans.get(position);
            makeUserBaseData(holder, friendCircleBean, position);
            if (holder instanceof OnlyWordViewHolder) {
                OnlyWordViewHolder onlyWordViewHolder = (OnlyWordViewHolder) holder;
            } else if (holder instanceof WordAndUrlViewHolder) {
                WordAndUrlViewHolder wordAndUrlViewHolder = (WordAndUrlViewHolder) holder;
                wordAndUrlViewHolder.layoutUrl.setOnClickListener(v -> Toast.makeText(mContext, "You Click Layout Url", Toast.LENGTH_SHORT).show());
            } else if (holder instanceof WordAndImagesViewHolder) {
                WordAndImagesViewHolder wordAndImagesViewHolder = (WordAndImagesViewHolder) holder;
                wordAndImagesViewHolder.nineGridView.setOnImageClickListener((position1, view) -> Toast.makeText(mContext, "You Click position " + position1, Toast.LENGTH_SHORT).show());
                wordAndImagesViewHolder.nineGridView.setAdapter(new NineImageAdapter(mContext, mRequestOptions,
                        mDrawableTransitionOptions, friendCircleBean.getImageBeans()));
            }
        }
    }

    private void makeUserBaseData(BaseFriendCircleViewHolder holder, FriendCircleBean friendCircleBean, int position) {
        holder.txtContent.setText(friendCircleBean.getContentSpan());
        holder.txtContent.setOnLongClickListener(v -> {
            if (friendCircleBean.isShowContentTranslation()) {
                Utils.showPopupMenu(mContext, this, position, v, TranslationState.END);
            } else {
                Utils.showPopupMenu(mContext, this, position, v, TranslationState.START);
            }
            return false;
        });
        if (friendCircleBean.isShowContentTranslation()) {
            holder.txtTranslationContent.setText(friendCircleBean.getContentSpan());
            holder.txtTranslationContent.setOnLongClickListener(v -> {
                Utils.showPopupMenu(mContext, this, position, v, TranslationState.END);
                return false;
            });
            holder.layoutTranslation.setVisibility(View.VISIBLE);
        } else {
            holder.layoutTranslation.setVisibility(View.GONE);
        }

        UserBean userBean = friendCircleBean.getUserBean();
        if (userBean != null) {
            holder.txtUserName.setText(userBean.getUserName());
            Glide.with(mContext).load(userBean.getUserAvatarUrl())
                    .apply(mRequestOptions.override(mAvatarSize, mAvatarSize))
                    .transition(mDrawableTransitionOptions)
                    .into(holder.imgAvatar);
        }

        OtherInfoBean otherInfoBean = friendCircleBean.getOtherInfoBean();

        if (otherInfoBean != null) {
            holder.txtSource.setText(otherInfoBean.getSource());
            holder.txtPublishTime.setText(otherInfoBean.getTime());
        }

        if (friendCircleBean.isShowPraise() || friendCircleBean.isShowComment()) {
            holder.layoutPraiseAndComment.setVisibility(View.VISIBLE);
            if (friendCircleBean.isShowPraise()) {
                holder.txtPraiseContent.setVisibility(View.VISIBLE);
                holder.txtPraiseContent.setText(friendCircleBean.getPraiseUserNameRichText());
            } else {
                holder.txtPraiseContent.setVisibility(View.GONE);
            }

            if (friendCircleBean.isShowComment()) {
                if (friendCircleBean.isShowPraise()) {
                    holder.viewLine.setVisibility(View.VISIBLE);
                } else {
                    holder.viewLine.setVisibility(View.GONE);
                }
                holder.verticalCommentWidget.addComments(friendCircleBean.getCommentBeans());
            } else {
                holder.viewLine.setVisibility(View.GONE);
            }

            holder.imgPraiseOrComment.setOnClickListener(v -> {
                if (mContext instanceof Activity) {
                    if (mCommentOrPraisePopupWindow == null) {
                        mCommentOrPraisePopupWindow = new CommentOrPraisePopupWindow.Builder(mContext)
                                .setOnPraiseOrCommentClickListener(mOnPraiseOrCommentClickListener).build();
                    }
                    if (mCommentOrPraisePopupWindow.isShowing()) {
                        mCommentOrPraisePopupWindow.dismiss();
                    } else {
                        mCommentOrPraisePopupWindow.showPopupWindow(v);
                    }
                }
            });

        } else {
            holder.layoutPraiseAndComment.setVisibility(View.GONE);
        }


        holder.txtLocation.setOnClickListener(v -> Toast.makeText(mContext, "You Click Location", Toast.LENGTH_SHORT).show());
    }


    @Override
    public int getItemViewType(int position) {
        return mFriendCircleBeans.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return mFriendCircleBeans == null ? 0 : mFriendCircleBeans.size();
    }

    @Override
    public void onTranslation(int position) {
        if (mFriendCircleBeans != null && position < mFriendCircleBeans.size()) {
            mFriendCircleBeans.get(position).setShowContentTranslation(true);
        }
    }

    @Override
    public void onItemClickCopy(int position) {
        Toast.makeText(mContext, "已复制", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClickTranslation(int position) {
        if (mFriendCircleBeans != null && position < mFriendCircleBeans.size()) {
            mFriendCircleBeans.get(position).setShowContentTranslation(true);
            notifyItemViewTargetContent(position, true, mFriendCircleBeans.get(position).getContentSpan());
        }
    }

    @Override
    public void onItemClickHideTranslation(int position) {
        if (mFriendCircleBeans != null && position < mFriendCircleBeans.size()) {
            mFriendCircleBeans.get(position).setShowContentTranslation(false);
            notifyItemViewTargetContent(position, false, null);
        }
    }


    private void notifyItemViewTargetContent(int position, boolean isShowTranslation, SpannableStringBuilder translationResult) {
        View childView = mLayoutManager.findViewByPosition(position);
        if (childView != null) {
            RecyclerView.ViewHolder viewHolder = mRecyclerView.getChildViewHolder(childView);
            if (viewHolder != null && viewHolder instanceof BaseFriendCircleViewHolder) {
                if (isShowTranslation) {
                    ((BaseFriendCircleViewHolder) viewHolder).txtTranslationContent.setText(translationResult);
                    ((BaseFriendCircleViewHolder) viewHolder).txtTranslationContent.setOnLongClickListener(v -> {
                        Utils.showPopupMenu(mContext, FriendCircleAdapter.this, position, v, TranslationState.END);
                        return true;
                    });
                    ((BaseFriendCircleViewHolder) viewHolder).layoutTranslation.setVisibility(View.VISIBLE);
                } else {
                    ((BaseFriendCircleViewHolder) viewHolder).layoutTranslation.setVisibility(View.GONE);
                }
            }
        }
    }


    @Override
    public void onItemClickCollection(int position) {
        Toast.makeText(mContext, "已收藏", Toast.LENGTH_SHORT).show();
    }


    static class WordAndImagesViewHolder extends BaseFriendCircleViewHolder {

        NineGridView nineGridView;

        public WordAndImagesViewHolder(View itemView) {
            super(itemView);
            nineGridView = itemView.findViewById(R.id.nine_grid_view);
        }
    }


    static class WordAndUrlViewHolder extends BaseFriendCircleViewHolder {

        LinearLayout layoutUrl;

        public WordAndUrlViewHolder(View itemView) {
            super(itemView);
            layoutUrl = itemView.findViewById(R.id.layout_url);
        }
    }

    static class OnlyWordViewHolder extends BaseFriendCircleViewHolder {

        public OnlyWordViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class BaseFriendCircleViewHolder extends RecyclerView.ViewHolder {

        public VerticalCommentWidget verticalCommentWidget;
        public TextView txtUserName;
        public View viewLine;
        public TextView txtPraiseContent;
        public ImageView imgAvatar;
        public TextView txtSource;
        public TextView txtPublishTime;
        public ImageView imgPraiseOrComment;
        public TextView txtLocation;
        public TextView txtContent;
        public LinearLayout layoutTranslation;
        public TextView txtTranslationContent;
        public LinearLayout layoutPraiseAndComment;

        public BaseFriendCircleViewHolder(View itemView) {
            super(itemView);
            verticalCommentWidget = itemView.findViewById(R.id.vertical_comment_widget);
            txtUserName = itemView.findViewById(R.id.txt_user_name);
            txtPraiseContent = itemView.findViewById(R.id.praise_content);
            viewLine = itemView.findViewById(R.id.view_line);
            imgAvatar = itemView.findViewById(R.id.img_avatar);
            txtSource = itemView.findViewById(R.id.txt_source);
            txtPublishTime = itemView.findViewById(R.id.txt_publish_time);
            imgPraiseOrComment = itemView.findViewById(R.id.img_click_praise_or_comment);
            txtLocation = itemView.findViewById(R.id.txt_location);
            txtContent = itemView.findViewById(R.id.txt_content);
            txtTranslationContent = itemView.findViewById(R.id.txt_translation_content);
            layoutTranslation = itemView.findViewById(R.id.layout_translation);
            layoutPraiseAndComment = itemView.findViewById(R.id.layout_praise_and_comment);
            txtPraiseContent.setMovementMethod(new TextMovementMothod());
        }
    }
}
