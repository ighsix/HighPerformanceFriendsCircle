package com.kcrason.highperformancefriendscircle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * @author KCrason
 * @date 2018/4/27
 */
public class FriendCircleAdapter extends RecyclerView.Adapter<FriendCircleAdapter.BaseFriendCircleViewHolder> {

    private Context mContext;

    private LayoutInflater mLayoutInflater;

    private List<FriendCircleBean> mFriendCircleBeans;

    public FriendCircleAdapter(Context context, List<FriendCircleBean> friendCircleBeans) {
        this.mContext = context;
        this.mFriendCircleBeans = friendCircleBeans;
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
        if (holder != null && mFriendCircleBeans != null && position < mFriendCircleBeans.size()) {
            FriendCircleBean friendCircleBean = mFriendCircleBeans.get(position);
            makeBaseData(holder, friendCircleBean,position);
            if (holder instanceof OnlyWordViewHolder) {
                OnlyWordViewHolder onlyWordViewHolder = (OnlyWordViewHolder) holder;
            } else if (holder instanceof WordAndUrlViewHolder) {
                WordAndUrlViewHolder wordAndUrlViewHolder = (WordAndUrlViewHolder) holder;
                wordAndUrlViewHolder.layoutUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "You Click Layout Url", Toast.LENGTH_SHORT).show();
                    }
                });
            } else if (holder instanceof WordAndImagesViewHolder) {
                WordAndImagesViewHolder wordAndImagesViewHolder = (WordAndImagesViewHolder) holder;
                wordAndImagesViewHolder.nineGridView.setOnImageClickListener(new NineGridView.OnImageClickListener() {
                    @Override
                    public void onImageClick(int position, View view) {
                        Toast.makeText(mContext, "You Click position " + position, Toast.LENGTH_SHORT).show();
                    }
                });
                wordAndImagesViewHolder.nineGridView.setAdapter(new NineImageAdapter(mContext, friendCircleBean.getImageBeans()));
            }
        }
    }

    private void makeBaseData(BaseFriendCircleViewHolder holder, FriendCircleBean friendCircleBean, int position) {
        holder.txtUserName.setText(friendCircleBean.getUserName());
        holder.txtContent.setText(friendCircleBean.getContent());
        List<PraiseBean> praiseBeans = friendCircleBean.getPraiseBeans();
        Log.i("KCrason", "makeBaseData: " + praiseBeans.size() + "//" + position);
        if (praiseBeans != null && praiseBeans.size() > 0) {
            holder.txtPraiseContent.setVisibility(View.VISIBLE);
            holder.txtPraiseContent.setText(friendCircleBean.getPraiseUserNameRichText());
        } else {
            holder.txtPraiseContent.setVisibility(View.GONE);
        }
        List<CommentBean> commentBeans = friendCircleBean.getCommentBeans();
        if (commentBeans != null && commentBeans.size() > 0) {
            if (praiseBeans != null && praiseBeans.size() > 0) {
                holder.viewLine.setVisibility(View.VISIBLE);
            } else {
                holder.viewLine.setVisibility(View.GONE);
            }
            holder.verticalCommentWidget.addComments(commentBeans);
        } else {
            holder.viewLine.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return mFriendCircleBeans.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return mFriendCircleBeans == null ? 0 : mFriendCircleBeans.size();
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

        public TextView txtContent;
        public TextView txtUserName;

        public View viewLine;
        public TextView txtPraiseContent;

        public BaseFriendCircleViewHolder(View itemView) {
            super(itemView);
            verticalCommentWidget = itemView.findViewById(R.id.vertical_comment_widget);
            txtContent = itemView.findViewById(R.id.txt_content);
            txtUserName = itemView.findViewById(R.id.txt_user_name);
            txtPraiseContent = itemView.findViewById(R.id.praise_content);
            viewLine = itemView.findViewById(R.id.view_line);
            txtPraiseContent.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }
}
