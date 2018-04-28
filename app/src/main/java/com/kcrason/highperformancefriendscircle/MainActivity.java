package com.kcrason.highperformancefriendscircle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private RecyclerView mRecyclerView;

    private FriendCircleAdapter mFriendCircleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwipeRefreshLayout = findViewById(R.id.swpie_refresh_layout);
        mRecyclerView = findViewById(R.id.recycler_view);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(MainActivity.this).resumeRequests();
                } else {
                    Glide.with(MainActivity.this).pauseRequests();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFriendCircleAdapter = new FriendCircleAdapter(this, makeFriendCircleBeans());
        mRecyclerView.setAdapter(mFriendCircleAdapter);
    }

    private List<FriendCircleBean> makeFriendCircleBeans() {
        List<FriendCircleBean> friendCircleBeans = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            FriendCircleBean friendCircleBean = new FriendCircleBean();
            int randomValue = (int) (Math.random() * 300);
            if (randomValue < 100) {
                friendCircleBean.setViewType(Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_ONLY_WORD);
            } else if (randomValue < 200) {
                friendCircleBean.setViewType(Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_WORD_AND_IMAGES);
            } else {
                friendCircleBean.setViewType(Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_WORD_AND_URL);
            }
            friendCircleBean.setCommentBeans(makeCommentBeans());
            friendCircleBean.setImageBeans(makeImages());
            List<PraiseBean> praiseBeans = makePraiseBeans();
            friendCircleBean.setPraiseUserNameRichText(Utils.makePraiseNameRichText(this, praiseBeans));
            friendCircleBean.setPraiseBeans(praiseBeans);
            friendCircleBean.setContent(Constants.CONTENT[(int) (Math.random() * 10)]);
            friendCircleBean.setUserName(Constants.USER_NAME[(int) (Math.random() * 30)]);
            friendCircleBeans.add(friendCircleBean);
        }
        return friendCircleBeans;
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
            imageBean.setImageUrl("http://oh2qy1yiv.bkt.clouddn.com/" + Constants.urls[(int) (Math.random() * 20)] + ".jpg");
            imageBeans.add(imageBean);
        }
        return imageBeans;
    }


    private List<PraiseBean> makePraiseBeans() {
        List<PraiseBean> praiseBeans = new ArrayList<>();
        int randomCount = (int) (Math.random() * 3);
        for (int i = 0; i < randomCount; i++) {
            PraiseBean praiseBean = new PraiseBean();
            praiseBean.setPraiseUserName(Constants.USER_NAME[(int) (Math.random() * 30)]);
            praiseBeans.add(praiseBean);
        }
        return praiseBeans;
    }


    private List<CommentBean> makeCommentBeans() {
        List<CommentBean> commentBeans = new ArrayList<>();
        int randomCount = (int) (Math.random() * 30);
        for (int i = 0; i < randomCount; i++) {
            CommentBean commentBean = new CommentBean();
            if ((int) (Math.random() * 100) % 2 == 0) {
                commentBean.setCommentType(Constants.CommentType.COMMENT_TYPE_SINGLE);
                commentBean.setChildUserName(Constants.USER_NAME[(int) (Math.random() * 30)]);
            } else {
                commentBean.setCommentType(Constants.CommentType.COMMENT_TYPE_REPLY);
                commentBean.setChildUserName(Constants.USER_NAME[(int) (Math.random() * 30)]);
                commentBean.setParentUserName(Constants.USER_NAME[(int) (Math.random() * 30)]);
            }
            commentBean.setCommentContent(Constants.COMMENT_CONTENT[(int) (Math.random() * 30)]);
            commentBean.build(this);
            commentBeans.add(commentBean);
        }
        return commentBeans;
    }


    @Override
    public void onRefresh() {

    }
}
