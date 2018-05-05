package com.kcrason.highperformancefriendscircle.others;

import android.content.Context;
import android.support.v4.util.ArrayMap;

import com.kcrason.highperformancefriendscircle.Constants;
import com.kcrason.highperformancefriendscircle.R;
import com.kcrason.highperformancefriendscircle.beans.CommentBean;
import com.kcrason.highperformancefriendscircle.beans.EmojiBean;
import com.kcrason.highperformancefriendscircle.beans.FriendCircleBean;
import com.kcrason.highperformancefriendscircle.beans.OtherInfoBean;
import com.kcrason.highperformancefriendscircle.beans.PraiseBean;
import com.kcrason.highperformancefriendscircle.beans.UserBean;
import com.kcrason.highperformancefriendscircle.utils.SpanUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * @author KCrason
 * @date 2018/5/2
 */
public class DataCenter {

    public final static ArrayMap<String, Integer> EMOJI_01 = new ArrayMap<>();

    public final static ArrayMap<String, Integer> EMOJI_02 = new ArrayMap<>();

    static {
        //01系列表情
        EMOJI_01.put("[emoji_01_angry]", R.drawable.emoji_01_angry);
        EMOJI_01.put("[emoji_01_angry_1]", R.drawable.emoji_01_angry_1);
        EMOJI_01.put("[emoji_01_bored]", R.drawable.emoji_01_bored);
        EMOJI_01.put("[emoji_01_bored_1]", R.drawable.emoji_01_bored_1);
        EMOJI_01.put("[emoji_01_bored_2]", R.drawable.emoji_01_bored_2);
        EMOJI_01.put("[emoji_01_confused]", R.drawable.emoji_01_confused);
        EMOJI_01.put("[emoji_01_confused_1]", R.drawable.emoji_01_confused_1);
        EMOJI_01.put("[emoji_01_crying]", R.drawable.emoji_01_crying);
        EMOJI_01.put("[emoji_01_crying_1]", R.drawable.emoji_01_crying_1);
        EMOJI_01.put("[emoji_01_embarrassed]", R.drawable.emoji_01_embarrassed);
        EMOJI_01.put("[emoji_01_emoticons]", R.drawable.emoji_01_emoticons);
        EMOJI_01.put("[emoji_01_happy]", R.drawable.emoji_01_happy);
        EMOJI_01.put("[emoji_01_happy_1]", R.drawable.emoji_01_happy_1);
        EMOJI_01.put("[emoji_01_happy_2]", R.drawable.emoji_01_happy_2);
        EMOJI_01.put("[emoji_01_happy_3]", R.drawable.emoji_01_happy_3);
        EMOJI_01.put("[emoji_01_happy_4]", R.drawable.emoji_01_happy_4);
        EMOJI_01.put("[emoji_01_ill]", R.drawable.emoji_01_ill);
        EMOJI_01.put("[emoji_01_in_love]", R.drawable.emoji_01_in_love);
        EMOJI_01.put("[emoji_01_kissing]", R.drawable.emoji_01_kissing);
        EMOJI_01.put("[emoji_01_mad]", R.drawable.emoji_01_mad);
        EMOJI_01.put("[emoji_01_nerd]", R.drawable.emoji_01_nerd);
        EMOJI_01.put("[emoji_01_ninja]", R.drawable.emoji_01_ninja);
        EMOJI_01.put("[emoji_01_quiet]", R.drawable.emoji_01_quiet);
        EMOJI_01.put("[emoji_01_sad]", R.drawable.emoji_01_sad);
        EMOJI_01.put("[emoji_01_secret]", R.drawable.emoji_01_secret);
        EMOJI_01.put("[emoji_01_smart]", R.drawable.emoji_01_smart);
        EMOJI_01.put("[emoji_01_smile]", R.drawable.emoji_01_smile);
        EMOJI_01.put("[emoji_01_smiling]", R.drawable.emoji_01_smiling);
        EMOJI_01.put("[emoji_01_surprised]", R.drawable.emoji_01_surprised);
        EMOJI_01.put("[emoji_01_surprised_1]", R.drawable.emoji_01_surprised_1);
        EMOJI_01.put("[emoji_01_suspicious]", R.drawable.emoji_01_suspicious);
        EMOJI_01.put("[emoji_01_suspicious_1]", R.drawable.emoji_01_suspicious_1);
        EMOJI_01.put("[emoji_01_tongue_out]", R.drawable.emoji_01_tongue_out);
        EMOJI_01.put("[emoji_01_tongue_out_1]", R.drawable.emoji_01_tongue_out_1);
        EMOJI_01.put("[emoji_01_unhappy]", R.drawable.emoji_01_unhappy);
        EMOJI_01.put("[emoji_01_wink]", R.drawable.emoji_01_wink);


        //02系列表情
        EMOJI_02.put("[emoji_02_angel]", R.drawable.emoji_02_angel);
        EMOJI_02.put("[emoji_02_angry]", R.drawable.emoji_02_angry);
        EMOJI_02.put("[emoji_02_astonished]", R.drawable.emoji_02_astonished);
        EMOJI_02.put("[emoji_02_astonished_1]", R.drawable.emoji_02_astonished_1);
        EMOJI_02.put("[emoji_02_confused]", R.drawable.emoji_02_confused);
        EMOJI_02.put("[emoji_02_cool]", R.drawable.emoji_02_cool);
        EMOJI_02.put("[emoji_02_cool_1]", R.drawable.emoji_02_cool_1);
        EMOJI_02.put("[emoji_02_cry]", R.drawable.emoji_02_cry);
        EMOJI_02.put("[emoji_02_cry_1]", R.drawable.emoji_02_cry_1);
        EMOJI_02.put("[emoji_02_devil]", R.drawable.emoji_02_devil);
        EMOJI_02.put("[emoji_02_dizzy]", R.drawable.emoji_02_dizzy);
        EMOJI_02.put("[emoji_02_expressionless]", R.drawable.emoji_02_expressionless);
        EMOJI_02.put("[emoji_02_flushed]", R.drawable.emoji_02_flushed);
        EMOJI_02.put("[emoji_02_happy]", R.drawable.emoji_02_happy);
        EMOJI_02.put("[emoji_02_happy_1]", R.drawable.emoji_02_happy_1);
        EMOJI_02.put("[emoji_02_happy_2]", R.drawable.emoji_02_happy_2);
        EMOJI_02.put("[emoji_02_in_love]", R.drawable.emoji_02_in_love);
        EMOJI_02.put("[emoji_02_injury]", R.drawable.emoji_02_injury);
        EMOJI_02.put("[emoji_02_joy]", R.drawable.emoji_02_joy);
        EMOJI_02.put("[emoji_02_kiss]", R.drawable.emoji_02_kiss);
        EMOJI_02.put("[emoji_02_kiss_1]", R.drawable.emoji_02_kiss_1);
        EMOJI_02.put("[emoji_02_kiss_2]", R.drawable.emoji_02_kiss_2);
        EMOJI_02.put("[emoji_02_mask]", R.drawable.emoji_02_mask);
        EMOJI_02.put("[emoji_02_mute]", R.drawable.emoji_02_mute);
        EMOJI_02.put("[emoji_02_neutral]", R.drawable.emoji_02_neutral);
        EMOJI_02.put("[emoji_02_sad]", R.drawable.emoji_02_sad);
        EMOJI_02.put("[emoji_02_sad_1]", R.drawable.emoji_02_sad_1);
        EMOJI_02.put("[emoji_02_scared]", R.drawable.emoji_02_scared);
        EMOJI_02.put("[emoji_02_scared_1]", R.drawable.emoji_02_scared_1);
        EMOJI_02.put("[emoji_02_secret]", R.drawable.emoji_02_secret);
        EMOJI_02.put("[emoji_02_shocked]", R.drawable.emoji_02_shocked);
        EMOJI_02.put("[emoji_02_sick]", R.drawable.emoji_02_sick);
        EMOJI_02.put("[emoji_02_sleeping]", R.drawable.emoji_02_sleeping);
        EMOJI_02.put("[emoji_02_smile]", R.drawable.emoji_02_smile);
        EMOJI_02.put("[emoji_02_smile_1]", R.drawable.emoji_02_smile_1);
        EMOJI_02.put("[emoji_02_smiling]", R.drawable.emoji_02_smiling);
        EMOJI_02.put("[emoji_02_smiling_1]", R.drawable.emoji_02_smiling_1);
        EMOJI_02.put("[emoji_02_smirking]", R.drawable.emoji_02_smirking);
        EMOJI_02.put("[emoji_02_surprised]", R.drawable.emoji_02_surprised);
        EMOJI_02.put("[emoji_02_sweat]", R.drawable.emoji_02_sweat);
        EMOJI_02.put("[emoji_02_thinking]", R.drawable.emoji_02_thinking);
        EMOJI_02.put("[emoji_02_tired]", R.drawable.emoji_02_tired);
        EMOJI_02.put("[emoji_02_tongue]", R.drawable.emoji_02_tongue);
        EMOJI_02.put("[emoji_02_tongue_1]", R.drawable.emoji_02_tongue_1);
        EMOJI_02.put("[emoji_02_tongue_2]", R.drawable.emoji_02_tongue_2);
        EMOJI_02.put("[emoji_02_vomiting]", R.drawable.emoji_02_vomiting);
        EMOJI_02.put("[emoji_02_wink]", R.drawable.emoji_02_wink);
    }

    public static void init() {
        new Thread(DataCenter::mapT0List).start();
    }

    private static void mapT0List() {
        List<String> emojiNames = new ArrayList<>(EMOJI_01.keySet());
        List<Integer> emojiResource = new ArrayList<>(EMOJI_01.values());
        for (int i = 0; i < emojiNames.size(); i++) {
            EmojiBean emojiBean = new EmojiBean();
            emojiBean.setEmojiName(emojiNames.get(i));
            emojiBean.setEmojiResource(emojiResource.get(i));
            emoji01Beans.add(emojiBean);
        }

        emojiNames = new ArrayList<>(EMOJI_02.keySet());
        emojiResource = new ArrayList<>(EMOJI_02.values());
        for (int i = 0; i < emojiNames.size(); i++) {
            EmojiBean emojiBean = new EmojiBean();
            emojiBean.setEmojiName(emojiNames.get(i));
            emojiBean.setEmojiResource(emojiResource.get(i));
            emoji02Beans.add(emojiBean);
        }
    }

    public static List<List<EmojiBean>> makeEmojiPanelAllTypes() {
        List<List<EmojiBean>> emojiPanelAllTypes = new ArrayList<>();
        emojiPanelAllTypes.add(DataCenter.emoji01Beans);
        emojiPanelAllTypes.add(DataCenter.emoji02Beans);
        return emojiPanelAllTypes;
    }

    public static List<EmojiBean> emoji01Beans = new ArrayList<>();

    public static List<EmojiBean> emoji02Beans = new ArrayList<>();

    public static List<FriendCircleBean> makeFriendCircleBeans(Context context) {
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
            friendCircleBean.setCommentBeans(makeCommentBeans(context));
            friendCircleBean.setImageUrls(makeImages());
            List<PraiseBean> praiseBeans = makePraiseBeans();
            friendCircleBean.setPraiseSpan(SpanUtils.makePraiseSpan(context, praiseBeans));
            friendCircleBean.setPraiseBeans(praiseBeans);
            friendCircleBean.setContent(Constants.CONTENT[(int) (Math.random() * 10)]);

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


    private static List<String> makeImages() {
        List<String> imageBeans = new ArrayList<>();
        int randomCount = (int) (Math.random() * 9);
        if (randomCount == 0) {
            randomCount = randomCount + 1;
        } else if (randomCount == 8) {
            randomCount = randomCount + 1;
        }
        for (int i = 0; i < randomCount; i++) {
            imageBeans.add(Constants.IMAGE_URL[(int) (Math.random() * 50)]);
        }
        return imageBeans;
    }


    private static List<PraiseBean> makePraiseBeans() {
        List<PraiseBean> praiseBeans = new ArrayList<>();
        int randomCount = (int) (Math.random() * 20);
        for (int i = 0; i < randomCount; i++) {
            PraiseBean praiseBean = new PraiseBean();
            praiseBean.setPraiseUserName(Constants.USER_NAME[(int) (Math.random() * 30)]);
            praiseBeans.add(praiseBean);
        }
        return praiseBeans;
    }


    private static List<CommentBean> makeCommentBeans(Context context) {
        List<CommentBean> commentBeans = new ArrayList<>();
        int randomCount = (int) (Math.random() * 20);
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
            commentBean.build(context);
            commentBeans.add(commentBean);
        }
        return commentBeans;
    }
}
