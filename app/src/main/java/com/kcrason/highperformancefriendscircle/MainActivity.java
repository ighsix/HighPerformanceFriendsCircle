package com.kcrason.highperformancefriendscircle;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kcrason.highperformancefriendscircle.adapters.FriendCircleAdapter;
import com.kcrason.highperformancefriendscircle.beans.CommentBean;
import com.kcrason.highperformancefriendscircle.beans.FriendCircleBean;
import com.kcrason.highperformancefriendscircle.beans.ImageBean;
import com.kcrason.highperformancefriendscircle.beans.OtherInfoBean;
import com.kcrason.highperformancefriendscircle.beans.PraiseBean;
import com.kcrason.highperformancefriendscircle.beans.UserBean;
import com.kcrason.highperformancefriendscircle.interfaces.OnPraiseOrCommentClickListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
        OnPraiseOrCommentClickListener {

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
        mFriendCircleAdapter = new FriendCircleAdapter(this, mRecyclerView);
        mRecyclerView.setAdapter(mFriendCircleAdapter);
        asyncMakeData();
    }

    private List<FriendCircleBean> makeFriendCircleBeans() {
        List<FriendCircleBean> friendCircleBeans = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
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

//            if (((int) (Math.random() * 1000)) % 2 == 0) {
//                friendCircleBean.setShowContentTranslation(true);
//            } else {
//                friendCircleBean.setShowContentTranslation(false);
//            }

            UserBean userBean = new UserBean();
            userBean.setUserName(Constants.USER_NAME[(int) (Math.random() * 30)]);
            userBean.setUserAvatarUrl(Constants.IMAGE_URL[(int) (Math.random() * 50)]);
            friendCircleBean.setUserBean(userBean);


            OtherInfoBean otherInfoBean = new OtherInfoBean();
            otherInfoBean.setTime(Constants.TIMES[(int) (Math.random() * 20)]);
            int random = (int) (Math.random() * 30);
            if (random < 20) {
                otherInfoBean.setSource(Constants.SOURCE[random]);
            } else {
                otherInfoBean.setSource("");
            }
            friendCircleBean.setOtherInfoBean(otherInfoBean);
            friendCircleBeans.add(friendCircleBean);
        }
        return friendCircleBeans;
    }


    private List<ImageBean> makeImages() {
        List<ImageBean> imageBeans = new ArrayList<>();
        int randomCount = (int) (Math.random() * 9);
        if (randomCount == 0) {
            randomCount = randomCount + 1;
        }
        for (int i = 0; i < randomCount; i++) {
            ImageBean imageBean = new ImageBean();
            imageBean.setImageUrl(Constants.IMAGE_URL[(int) (Math.random() * 50)]);
            imageBeans.add(imageBean);
        }
        return imageBeans;
    }


    private List<PraiseBean> makePraiseBeans() {
        List<PraiseBean> praiseBeans = new ArrayList<>();
        int randomCount = (int) (Math.random() * 10);
        for (int i = 0; i < randomCount; i++) {
            PraiseBean praiseBean = new PraiseBean();
            praiseBean.setPraiseUserName(Constants.USER_NAME[(int) (Math.random() * 30)]);
            praiseBeans.add(praiseBean);
        }
        return praiseBeans;
    }


    private List<CommentBean> makeCommentBeans() {
        List<CommentBean> commentBeans = new ArrayList<>();
        int randomCount = (int) (Math.random() * 5);
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
//            if (((int) (Math.random() * 1000)) % 2 == 0) {
//                commentBean.setShowContentTranslation(true);
//            } else {
//                commentBean.setShowContentTranslation(false);
//            }
            commentBean.setCommentContent(Constants.COMMENT_CONTENT[(int) (Math.random() * 30)]);
            commentBean.build(this);
            commentBeans.add(commentBean);
        }
        return commentBeans;
    }

    private Disposable mDisposable;

    private void asyncMakeData() {
        mDisposable = Single.create((SingleOnSubscribe<List<FriendCircleBean>>) emitter ->
                emitter.onSuccess(makeFriendCircleBeans())).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((friendCircleBeans, throwable) -> {
                    mSwipeRefreshLayout.setRefreshing(false);
                    if (friendCircleBeans != null && throwable == null) {
                        mFriendCircleAdapter.setFriendCircleBeans(friendCircleBeans);
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    @Override
    public void onRefresh() {
        asyncMakeData();
    }

    @Override
    public void onPraiseClick() {
        Toast.makeText(this, "You Click Praise!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCommentClick() {
        Toast.makeText(this, "You Click Comment!", Toast.LENGTH_SHORT).show();
    }
}
