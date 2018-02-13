package com.ydd.pockettoycatcherrwz.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyphenate.EMMessageListener;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.entity.EnterRoomInfo;
import com.ydd.pockettoycatcherrwz.entity.LiveRoom;
import com.ydd.pockettoycatcherrwz.entity.RoomItem;
import com.ydd.pockettoycatcherrwz.entity.TokenInvalidMsg;
import com.ydd.pockettoycatcherrwz.entity.UserInfo;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.UploadVideoManager;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.EnterRoomRequest;
import com.ydd.pockettoycatcherrwz.network.mina.BaseMinaMsg;
import com.ydd.pockettoycatcherrwz.network.mina.MinaCmd;
import com.ydd.pockettoycatcherrwz.network.mina.MinaManager;
import com.ydd.pockettoycatcherrwz.network.mina.request.ConnectParam;
import com.ydd.pockettoycatcherrwz.network.mina.request.GrabParam;
import com.ydd.pockettoycatcherrwz.network.mina.request.HbParam;
import com.ydd.pockettoycatcherrwz.network.mina.request.MoveParam;
import com.ydd.pockettoycatcherrwz.network.mina.request.StartParam;
import com.ydd.pockettoycatcherrwz.network.mina.result.ConnectResult;
import com.ydd.pockettoycatcherrwz.network.mina.result.GrabResult;
import com.ydd.pockettoycatcherrwz.network.mina.result.StartResult;
import com.ydd.pockettoycatcherrwz.network.mina.result.VmcStatusResult;
import com.ydd.pockettoycatcherrwz.ui.login.LoginActivity;
import com.ydd.pockettoycatcherrwz.ui.personal.BottomShareDialog;
import com.ydd.pockettoycatcherrwz.ui.personal.RechargeListDialog;
import com.ydd.pockettoycatcherrwz.util.BusinessUtil;
import com.ydd.pockettoycatcherrwz.util.CommonUtil;
import com.ydd.pockettoycatcherrwz.util.ImageUtil;
import com.ydd.pockettoycatcherrwz.util.ImgLoaderUtil;
import com.ydd.pockettoycatcherrwz.util.LogUtil;
import com.ydd.pockettoycatcherrwz.util.ScreenRecorder;
import com.ydd.pockettoycatcherrwz.util.SharedPrefConfig;
import com.ydd.pockettoycatcherrwz.util.WaterImgUtil;
import com.ydd.pockettoycatcherrwz.util.hx.HXChatUtil;
import com.ydd.pockettoycatcherrwz.widget.LiveDetailDialog;
import com.ydd.pockettoycatcherrwz.widget.StrokeTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import io.agora.openlive.model.AGEventHandler;
import io.agora.openlive.model.ConstantApp;
import io.agora.openlive.ui.BaseActivity;
import io.agora.openlive.ui.LiveRoomActivity;
import io.agora.openlive.ui.SmallVideoViewAdapter;
import io.agora.openlive.ui.VideoViewEventListener;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

public class LiveActivity extends BaseActivity implements AGEventHandler {

    private final static Logger log = LoggerFactory
            .getLogger(LiveRoomActivity.class);

    private RelativeLayout mSmallVideoViewDock;

    private final HashMap<Integer, SurfaceView> mUidsList = new HashMap<>();
    private BottomShareDialog dialog;
    private TextView li_point_top;


    Handler mHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // super.handleMessage(msg);
            if (msg.what == 1) {
                doLoosen();
                Log.i("===st", ">" + "5");
            }
        }
    };

    private long startTime = 0;
    private long endTime = 0;
    private int firstViewId = 0;
    private int lastViewId = 0;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    private boolean isBroadcaster(int cRole) {

     //   return cRole == Constants.CLIENT_ROLE_BROADCASTER;
        return true;
    }

    private boolean isBroadcaster() {
        return isBroadcaster(config().mClientRole);
    }

    @Override
    protected void initUIandEvent() {
        event().addEventHandler(this);

       // doConfigEngine(Constants.CLIENT_ROLE_AUDIENCE);

    }

    private void doConfigEngine(int cRole) {
        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(this);
        int prefIndex = pref.getInt(
                ConstantApp.PrefManager.PREF_PROPERTY_PROFILE_IDX,
                ConstantApp.DEFAULT_PROFILE_IDX);
        if (prefIndex > ConstantApp.VIDEO_PROFILES.length - 1) {
            prefIndex = ConstantApp.DEFAULT_PROFILE_IDX;
        }
        // FIXME czhang 调整视频
        int vProfile = ConstantApp.VIDEO_PROFILES[0];

        worker().configEngine(cRole, vProfile);
    }

    @Override
    protected void deInitUIandEvent() {
        doLeaveChannel();
        event().removeEventHandler(this);

        mUidsList.clear();
    }

    private void doLeaveChannel() {
        worker().leaveChannel(config().mChannel);
        if (isBroadcaster()) {
            worker().preview(false, null, 0);
        }
    }

    @Override
    public void onFirstRemoteVideoDecoded(int uid, int width, int height,
                                          int elapsed) {
    }

    private void doRenderRemoteUi(final int uid) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isFinishing()) {
                    return;
                }
//
//                SurfaceView surfaceV = RtcEngine
//                        .CreateRendererView(getApplicationContext());
//                surfaceV.setZOrderOnTop(true);
//                surfaceV.setZOrderMediaOverlay(true);
//                mUidsList.put(uid, surfaceV);
//                if (config().mUid == uid) {
//                    rtcEngine().setupLocalVideo(new VideoCanvas(surfaceV,
//                            VideoCanvas.RENDER_MODE_HIDDEN, uid));
//                } else {
//                    rtcEngine().setupRemoteVideo(new VideoCanvas(surfaceV,
//                            VideoCanvas.RENDER_MODE_HIDDEN, uid));
//                }
//
//                if (mViewType == VIEW_TYPE_DEFAULT) {
//                    log.debug("doRenderRemoteUi VIEW_TYPE_DEFAULT" + " "
//                            + (uid & 0xFFFFFFFFL));
//                    switchToDefaultVideoView();
//                } else {
//                    int bigBgUid = mSmallVideoViewAdapter.getExceptedUid();
//                    log.debug("doRenderRemoteUi VIEW_TYPE_SMALL" + " "
//                            + (uid & 0xFFFFFFFFL) + " "
//                            + (bigBgUid & 0xFFFFFFFFL));
//                    switchToSmallVideoView(bigBgUid);
//                }
            }
        });
    }

    @Override
    public void onJoinChannelSuccess(final String channel, final int uid,
                                     final int elapsed) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isFinishing()) {
                    return;
                }

                if (mUidsList.containsKey(uid)) {
                    log.debug("already added to UI, ignore it "
                            + (uid & 0xFFFFFFFFL) + " " + mUidsList.get(uid));
                    return;
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mLoadingIv.setVisibility(View.GONE);
                    }
                }, 500);

                final boolean isBroadcaster = isBroadcaster();
                log.debug("onJoinChannelSuccess " + channel + " " + uid + " "
                        + elapsed + " " + isBroadcaster);

                worker().getEngineConfig().mUid = uid;

                SurfaceView surfaceV = mUidsList.remove(0);
                if (surfaceV != null) {
                    mUidsList.put(uid, surfaceV);
                }
            }
        });
    }

    @Override
    public void onUserOffline(int uid, int reason) {
        log.debug("onUserOffline " + (uid & 0xFFFFFFFFL) + " " + reason);
        doRemoveRemoteUi(uid);
    }

    @Override
    public void onUserJoined(int uid, int elapsed) {
        doRenderRemoteUi(uid);
    }

    private void requestRemoteStreamType(final int currentHostCount) {
        log.debug("requestRemoteStreamType " + currentHostCount);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                HashMap.Entry<Integer, SurfaceView> highest = null;
                for (HashMap.Entry<Integer, SurfaceView> pair : mUidsList
                        .entrySet()) {
                    log.debug("requestRemoteStreamType " + currentHostCount
                            + " local " + (config().mUid & 0xFFFFFFFFL) + " "
                            + (pair.getKey() & 0xFFFFFFFFL) + " "
                            + pair.getValue().getHeight() + " "
                            + pair.getValue().getWidth());
                    if (pair.getKey() != config().mUid && (highest == null
                            || highest.getValue().getHeight() < pair.getValue()
                            .getHeight())) {
                        if (highest != null) {
//                            rtcEngine().setRemoteVideoStreamType(
//                                    highest.getKey(),
//                                    Constants.VIDEO_STREAM_LOW);
                            log.debug(
                                    "setRemoteVideoStreamType switch highest VIDEO_STREAM_LOW "
                                            + currentHostCount + " "
                                            + (highest.getKey() & 0xFFFFFFFFL)
                                            + " "
                                            + highest.getValue().getWidth()
                                            + " "
                                            + highest.getValue().getHeight());
                        }
                        highest = pair;
                    } else if (pair.getKey() != config().mUid
                            && (highest != null
                            && highest.getValue().getHeight() >= pair
                            .getValue().getHeight())) {
//                        rtcEngine().setRemoteVideoStreamType(pair.getKey(),
//                                Constants.VIDEO_STREAM_LOW);
                        log.debug("setRemoteVideoStreamType VIDEO_STREAM_LOW "
                                + currentHostCount + " "
                                + (pair.getKey() & 0xFFFFFFFFL) + " "
                                + pair.getValue().getWidth() + " "
                                + pair.getValue().getHeight());
                    }
                }
                if (highest != null && highest.getKey() != 0) {
//                    rtcEngine().setRemoteVideoStreamType(highest.getKey(),
//                            Constants.VIDEO_STREAM_HIGH);
                    log.debug("setRemoteVideoStreamType VIDEO_STREAM_HIGH "
                            + currentHostCount + " "
                            + (highest.getKey() & 0xFFFFFFFFL) + " "
                            + highest.getValue().getWidth() + " "
                            + highest.getValue().getHeight());
                }
            }
        }, 500);
    }

    private void doRemoveRemoteUi(final int uid) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isFinishing()) {
                    return;
                }

                mUidsList.remove(uid);

                int bigBgUid = -1;
                if (mSmallVideoViewAdapter != null) {
                    bigBgUid = mSmallVideoViewAdapter.getExceptedUid();
                }

                log.debug("doRemoveRemoteUi " + (uid & 0xFFFFFFFFL) + " "
                        + (bigBgUid & 0xFFFFFFFFL));

                if (mViewType == VIEW_TYPE_DEFAULT || uid == bigBgUid) {
                    switchToDefaultVideoView();
                } else {
                    switchToSmallVideoView(bigBgUid);
                }
            }
        });
    }

    private SmallVideoViewAdapter mSmallVideoViewAdapter;

    int uid = 1;

    Integer[] uids = new Integer[]{1, 2};

    private void doSwitch() {
        if (mSmallVideoViewDock != null)
            mSmallVideoViewDock.setVisibility(View.GONE);
        int lastUid = uid;
        if (uid == uids[0]) {
            uid = uids[1];
        } else {
            uid = uids[0];
        }

        SurfaceView surfaceView = mUidsList.get(uid);
        if (surfaceView == null) {
            uid = lastUid;
            return;
        }
        stripSurfaceView(surfaceView);
        mLiveContainerRl.removeAllViews();
        mLiveContainerRl.addView(surfaceView);
//        rtcEngine().setupRemoteVideo(new VideoCanvas(mUidsList.get(uid),
//                VideoCanvas.RENDER_MODE_HIDDEN, uid));
//
//        rtcEngine().setRemoteVideoStreamType(uid, Constants.VIDEO_STREAM_HIGH);
    }

    private void switchToDefaultVideoView() {
        uid = 1;
        SurfaceView surfaceView = mUidsList.get(uid);
        if (mUidsList.containsKey(1)) {
            LogUtil.printCZ("uid contains:" + 1);
        }
        if (mUidsList.containsKey(2)) {
            LogUtil.printCZ("uid contains:" + 2);
        }
        if (surfaceView == null) {
            LogUtil.printCZ("surface is null");
            return;
        }
        LogUtil.printCZ("surface is not null");

        stripSurfaceView(surfaceView);
        mLiveContainerRl.removeAllViews();
        mLiveContainerRl.addView(surfaceView);
//        rtcEngine().setupRemoteVideo(new VideoCanvas(mUidsList.get(uid),
//                VideoCanvas.RENDER_MODE_HIDDEN, uid));
//
//        rtcEngine().setRemoteVideoStreamType(uid, Constants.VIDEO_STREAM_HIGH);
    }

    protected final void stripSurfaceView(SurfaceView view) {
        ViewParent parent = view.getParent();
        if (parent != null) {
            ((RelativeLayout) parent).removeView(view);
        }
    }

    private void switchToSmallVideoView(int uid) {
        HashMap<Integer, SurfaceView> slice = new HashMap<>(1);
        slice.put(uid, mUidsList.get(uid));

        // bindToSmallVideoView(uid);

        mViewType = VIEW_TYPE_SMALL;

        requestRemoteStreamType(mUidsList.size());
    }

    public int mViewType = VIEW_TYPE_DEFAULT;

    public static final int VIEW_TYPE_DEFAULT = 0;

    public static final int VIEW_TYPE_SMALL = 1;

    private void bindToSmallVideoView(int exceptUid) {
        if (mSmallVideoViewDock == null) {
            ViewStub stub = (ViewStub) findViewById(R.id.small_video_view_dock);
            mSmallVideoViewDock = (RelativeLayout) stub.inflate();
        }

        RecyclerView recycler = (RecyclerView) findViewById(
                R.id.small_video_view_container);

        boolean create = false;

        if (mSmallVideoViewAdapter == null) {
            create = true;
            mSmallVideoViewAdapter = new SmallVideoViewAdapter(this, exceptUid,
                    mUidsList, new VideoViewEventListener() {
                @Override
                public void onItemDoubleClick(View v, Object item) {
                    switchToDefaultVideoView();
                }
            });
            mSmallVideoViewAdapter.setHasStableIds(true);
        }
        recycler.setHasFixedSize(true);

        recycler.setLayoutManager(new GridLayoutManager(this, 3,
                GridLayoutManager.VERTICAL, false));
        recycler.setAdapter(mSmallVideoViewAdapter);

        recycler.setDrawingCacheEnabled(true);
        recycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);

        if (!create) {
            mSmallVideoViewAdapter.notifyUiChanged(mUidsList, exceptUid, null,
                    null);
        }
        recycler.setVisibility(View.VISIBLE);
        mSmallVideoViewDock.setVisibility(View.VISIBLE);
    }

    // －－－－－－－－－－－以上demo未删除部分，暂时保持不动－－－－－－－－－－－－－
    /**
     * 录制请求码
     */
    private static final int RECORD_REQUEST_CODE = 0x1001;
    private static final int COUNT_DOWN = 30;
    // danmaku描边颜色
    private static int[] textColor = new int[]{Color.parseColor("#FFB6C1"),
            Color.parseColor("#FFA500"), Color.parseColor("#7FFF00")};
    /**
     * 头像
     */
    private ImageView mAvatarIv;
    /**
     * 人数
     */
    private TextView mPersonsNumTv;
    /**
     * 金额
     */
    private TextView mMoneyTv;
    /**
     * 开始layout
     */
    private LinearLayout mStartContainer;
    /**
     * 游戏价格
     */
    private StrokeTextView mGamePriceTv;
    /**
     * 开始游戏按钮，用于显示状态
     */
    private StrokeTextView mStartTv;
    /**
     * 倒计时显示
     */
    private TextView mCountTv;
    /**
     * 加载页
     */
    private ImageView mLoadingIv;
    /**
     * 抓取layout
     */
    private LinearLayout mGrabContainer;
    private RelativeLayout mLiveContainerRl;
    /**
     * 房间信息
     */
    private RoomItem mRoomItem;
    /**
     * 房间流信息
     */
    private EnterRoomInfo mEnterRoomInfo;
    /**
     * 当前流信息
     */
    private LiveRoom mCurrentLive;
    /**
     * 充值对话框
     */
    private RechargeListDialog mRechargeListDialog;
    /**
     * 抓取结果对话框
     */
    private LiveGrabResultDialog mResultDialog;
    /**
     * 倒计时对话框
     */
    private LiveCountDialog mLiveCountDialog;
    /**
     * 详情对话框
     */
    private LiveDetailDialog mLiveDetailDialog;
    /**
     * 连接消息
     */
    private ConnectResult mConnectResult;
    // 屏幕录制相关
    private MediaProjectionManager mMediaProjectionManager;
    private ScreenRecorder mRecorder;
    /**
     * 游戏记录id
     */
    private String mDollLogId;
    /**
     * 是否成功
     */
    private int mRoomState;
    /**
     * 聊天室信息
     */
    private EMChatRoom mEMChatRoom;
    /**
     * 是否已经成功开始
     */
    private boolean mIsStarted = false;
    /**
     * 开始后是否已经发送抓取指令
     */
    private boolean mIsGrabed = false;
    /**
     * 已经点击开始
     */
    private boolean mIsStartClicked = false;
    /**
     * 心跳包定时任务
     */
    private Timer mTimer;
    /**
     * 声音相关
     */
    private SoundPool sp;
    private MediaPlayer mBgmPlayer;
    private int mMusicMove;
    private int mMusicReady;
    private int mMusicFailed;
    private int mMusicSucceed;
    private int mMusicTake;
    private int mMusicWhistle;
    boolean isMusicOn1 = true;
    boolean isMusicOn2 = true;

    // 弹幕相关
    private DanmakuView danmakuView;
    private DanmakuContext danmakuContext;
    private int mDanmakuColorIndex;
    private BaseDanmakuParser parser = new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };

    private Gson mGson = new Gson();


    /*
  * 按钮方向控制
  * */
    private ImageView ivRight;//往右边移动按钮
    private ImageView ivLeft;//往左边移动按钮
    private ImageView ivTop;//往前边移动按钮
    private ImageView ivBottom;//往后边移动按钮
    private ImageView ivCatch;//下抓按钮

    private boolean isRun = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        if (RunningContext.loginTelInfo == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        mRoomItem = (RoomItem) getIntent().getSerializableExtra("roomItem");
        Log.i("vaarfrr", "onCreate: " + mRoomItem.toString());
        if (mRoomItem == null) {
            finish();
            return;
        }
        mMediaProjectionManager = (MediaProjectionManager) getSystemService(
                MEDIA_PROJECTION_SERVICE);
        setContentView(R.layout.activity_live);
        initView();

        loadEnterRoom();
        MinaManager.getInstance().connect();
        // 定时发送心跳包
        doHb();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doConnect();
            }
        }, 1000);
    }

    private void initSound() {
        SharedPrefConfig spConfig = new SharedPrefConfig();
        spConfig.open(this,
                com.ydd.pockettoycatcherrwz.util.Constants.SP_FILE_CONFIG);

        String loginAccont = spConfig.getString("last_login", "");
        if (!TextUtils.isEmpty(loginAccont)) {
            spConfig.open(this, loginAccont + "_"
                    + com.ydd.pockettoycatcherrwz.util.Constants.SP_FILE_CONFIG);
            isMusicOn1 = spConfig.getBoolean(
                    com.ydd.pockettoycatcherrwz.util.Constants.SP_MUSIC_ON_1,
                    true);
            isMusicOn2 = spConfig.getBoolean(
                    com.ydd.pockettoycatcherrwz.util.Constants.SP_MUSIC_ON_2,
                    true);
        }

        if (isMusicOn1) {
            int bgmId = new Random().nextInt(2) == 1 ? R.raw.bgm02
                    : R.raw.bgm01;
            mBgmPlayer = MediaPlayer.create(this, bgmId);
            mBgmPlayer.setLooping(true);
            // mBgmPlayer.start();
        }
        if (isMusicOn2) {
            sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);// 第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
            mMusicMove = sp.load(this, R.raw.move, 1); // 把你的声音素材放到res/raw里，第2个参数即为资源文件，第3个为音乐的优先级
            mMusicTake = sp.load(this, R.raw.take, 1);
            mMusicWhistle = sp.load(this, R.raw.whistle, 1);

            mMusicReady = sp.load(this, R.raw.readygo, 1);
            mMusicFailed = sp.load(this, R.raw.result_failed, 1);
            mMusicSucceed = sp.load(this, R.raw.result_succeed, 1);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isMusicOn1) {
            mBgmPlayer.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isMusicOn1) {
            mBgmPlayer.pause();
        }
    }

    private void playPoolMusic(int musicId) {
        if (!isMusicOn2) {
            return;
        }
        sp.play(musicId, 1, 1, 0, 0, 1);
    }

    private void doHb() {
        TimerTask mHbTask = new TimerTask() {
            @Override
            public void run() {
                HbParam hbParam = new HbParam();
                hbParam.vmc_no = mRoomItem.machineSn;
                MinaManager.getInstance().sendMsg(new Gson().toJson(hbParam));
            }
        };
        mTimer = new Timer();
        mTimer.schedule(mHbTask, 0, 30000);
    }

    private void initView() {
        dealWithWater();
        mLoadingIv = (ImageView) findViewById(R.id.iv_live_loading);
        mAvatarIv = (ImageView) findViewById(R.id.iv_live_avatar);
        mMoneyTv = (TextView) findViewById(R.id.tv_live_money);
        li_point_top = (TextView) findViewById(R.id.li_point_top);
        mStartContainer = (LinearLayout) findViewById(
                R.id.ln_live_start_container);
        mGamePriceTv = (StrokeTextView) findViewById(R.id.tv_live_game_price);
        mPersonsNumTv = (TextView) findViewById(R.id.tv_live_persons_num);
        mGrabContainer = (LinearLayout) findViewById(
                R.id.ln_live_grab_container);
        danmakuView = (DanmakuView) findViewById(R.id.view_live_danmu);
        mCountTv = (TextView) findViewById(R.id.tv_live_count);
        mStartTv = (StrokeTextView) findViewById(R.id.tv_live_start);
        mLiveContainerRl = (RelativeLayout) findViewById(
                R.id.rl_live_container);

        ivLeft = (ImageView) findViewById(R.id.iv_live_grab_left);
        ivRight = (ImageView) findViewById(R.id.iv_live_grab_right);
        ivTop = (ImageView) findViewById(R.id.iv_live_grab_top);
        ivBottom = (ImageView) findViewById(R.id.iv_live_grab_bottom);
        ivCatch = (ImageView) findViewById(R.id.iv_live_grab);

        findViewById(R.id.iv_live_close).setOnClickListener(mOnClickListener);
        findViewById(R.id.iv_live_grab_left)
                .setOnClickListener(mOnClickListener);
        findViewById(R.id.iv_live_grab_top)
                .setOnClickListener(mOnClickListener);
        findViewById(R.id.iv_live_grab_right)
                .setOnClickListener(mOnClickListener);
        findViewById(R.id.iv_live_grab_bottom)
                .setOnClickListener(mOnClickListener);
        findViewById(R.id.iv_live_grab).setOnClickListener(mOnClickListener);
        findViewById(R.id.iv_live_refresh).setOnClickListener(mOnClickListener);
        findViewById(R.id.ln_live_details).setOnClickListener(mOnClickListener);
        findViewById(R.id.ln_live_share).setOnClickListener(mOnClickListener);
        findViewById(R.id.ln_live_start).setOnClickListener(mOnClickListener);
        findViewById(R.id.ln_live_recharge)
                .setOnClickListener(mOnClickListener);
        findViewById(R.id.iv_live_refresh).setOnClickListener(mOnClickListener);
        //ln_live_share


        mGamePriceTv.setText(String.valueOf(mRoomItem.price));

        initDanmuView();

        findViewById(R.id.iv_live_grab_left)
                .setOnTouchListener(mOnGrabTouchListener);
        findViewById(R.id.iv_live_grab_top)
                .setOnTouchListener(mOnGrabTouchListener);
        findViewById(R.id.iv_live_grab_right)
                .setOnTouchListener(mOnGrabTouchListener);
        findViewById(R.id.iv_live_grab_bottom)
                .setOnTouchListener(mOnGrabTouchListener);

        mPersonsNumTv.setText(String.valueOf(mRoomItem.num + 1));
        if (mRoomItem.status == 2) {
            // 如果房间状态为2，直接展示异常页面
            findViewById(R.id.ln_live_error_container)
                    .setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化弹幕
     */
    private void initDanmuView() {
        danmakuView.enableDanmakuDrawingCache(true);
        danmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                danmakuView.start();
            }

            @Override
            public void updateTimer(DanmakuTimer timer) {
            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {
            }

            @Override
            public void drawingFinished() {
            }
        });
        danmakuContext = DanmakuContext.create();
        danmakuView.prepare(parser, danmakuContext);

    }

    /**
     * 向弹幕View中添加一条弹幕
     *
     * @param content 弹幕的具体内容
     */
    private void addDanmaku(String content) {
        BaseDanmaku danmaku = danmakuContext.mDanmakuFactory
                .createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text = content;
        danmaku.padding = 5;
        danmaku.textSize = getResources()
                .getDimension(R.dimen.common_text_size_normal);
        danmaku.textColor = Color.WHITE;
        danmaku.textShadowColor = textColor[mDanmakuColorIndex % 3];
        danmaku.setTime(danmakuView.getCurrentTime());
        danmakuView.addDanmaku(danmaku);
        mDanmakuColorIndex++;

    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_live_close:
                    // 关闭按钮
                    finish();
                    break;
                case R.id.iv_live_grab:
                    // 抓取
                    doGrab();
                    break;
                case R.id.ln_live_start:
                    // 开始游戏
                    doStart();
                    break;
                case R.id.ln_live_details:
                    // 详情
                    showLiveDetailsDialog();
                    break;
                case R.id.ln_live_recharge:
                    // 充值
                    startActivity(new Intent(LiveActivity.this, Recharge_newActivity.class));
                    //	showRechargeDialog();
                    break;
                case R.id.iv_live_refresh:
                    // 切换流
                    doSwitch();
                    // doRefresh();
                    break;
                //分享
                case R.id.ln_live_share:
                    showShareDialog();
                    break;
            }
        }
    };

    private void showShareDialog() {
        if (dialog == null) {
            dialog = new BottomShareDialog(false, this);
        }
        dialog.show();
    }

    /*private void initListen(){
        ivBottom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }*/


    private OnGrabTouchListener mOnGrabTouchListener = new OnGrabTouchListener() {
        @Override
        protected void onViewPressed(int viewId) {
            switch (viewId) {
                case R.id.iv_live_grab_bottom:
                    // 后移
                    if (uid == uids[0]) {
                        doMove(2);
                    } else {
                        doMove(4);
                    }
                    break;
                case R.id.iv_live_grab_top:
                    // 前移
                    if (uid == uids[0]) {
                        doMove(1);
                    } else {
                        doMove(3);
                    }
                    break;
                case R.id.iv_live_grab_left:
                    // 左移
                    if (uid == uids[0]) {
                        doMove(3);
                    } else {
                        doMove(2);
                    }
                    break;
                case R.id.iv_live_grab_right:
                    // 右移
                    doLoosen();
                    if (uid == uids[0]) {
                        doMove(4);
                    } else {
                        doMove(1);
                    }
                    break;
            }

        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            //return super.onTouch(v, event);
            //抬起
            Message message = new Message();
            message.what = 1;
            switch (event.getAction()){
                case  MotionEvent.ACTION_UP:
                    endTime = new Date(System.currentTimeMillis()).getTime();
                    long diff = endTime - startTime;
                    lastViewId = v.getId();
                    if (diff > 200) {
                        mHandle.sendMessage(message);
                        Log.i("===st", diff + ">" + "3");
                    } else {
                        mHandle.sendMessageDelayed(message, 200);
                        Log.i("===st", diff + ">" + "4");
                    }
                    break;
                case MotionEvent.ACTION_DOWN:
                    firstViewId = v.getId();
                    startTime = new Date(System.currentTimeMillis()).getTime();
                    long diff2 = startTime - endTime;
                    if (lastViewId != firstViewId) {
                        doLoosen();
                        onViewPressed(v.getId());

                        mHandle.removeMessages(1);
                        Log.i("===do", firstViewId + ">" + lastViewId);
                    } else {
                        if (diff2 > 200) {
                            doLoosen();
                            onViewPressed(v.getId());
                            Log.i("===id", diff2 + ">" + 1);
                        } else {
                            playPoolMusic(mMusicMove);
                            mHandle.removeMessages(1);

                            Log.i("===st", diff2 + ">" + "2");
                        }
                    }

                    Log.i("===st", firstViewId + ">");
                    break;
            }

            return true;
        }
    };

    private long getTime(long millisecond) {
        Calendar calendar = Calendar.getInstance();
        Long currentMillisecond = calendar.getTimeInMillis();
        startTime = currentMillisecond;

        //间隔秒
        long spaceSecond = (currentMillisecond - millisecond) / 1000;
        return spaceSecond;
    }


    private void doConnect() {
        ConnectParam param = new ConnectParam();
        param.token = RunningContext.accessToken;
        param.vmc_no = mRoomItem.machineSn;
        MinaManager.getInstance().sendMsg(new Gson().toJson(param));
    }

    private void doGrab() {
        if (!mIsGrabed) {
            playPoolMusic(mMusicTake);
            setBtnClick(true);
        }
        mIsGrabed = true;
        stopTimer();
        GrabParam grabParam = new GrabParam();
        grabParam.vmc_no = mRoomItem.machineSn;
        MinaManager.getInstance().sendMsg(new Gson().toJson(grabParam));
    }

    private void setBtnClick(boolean isClick) {
        ivBottom.setSelected(isClick);
        ivTop.setSelected(isClick);
        ivLeft.setSelected(isClick);
        ivRight.setSelected(isClick);
        ivCatch.setSelected(isClick);

    }


    private void doStart() {
        if (mRoomItem.status == 2) {
            showToast("正在维护中");
            // 如果房间状态为2，点击无效
            return;
        }
        if (mRoomState == 1) {
            showToast("玩家正在游戏，请稍后");
            return;
        }
        mIsStartClicked = true;
        setBtnClick(false);
        StartParam startParam = new StartParam();
        startParam.vmc_no = mRoomItem.machineSn;
        MinaManager.getInstance().sendMsg(new Gson().toJson(startParam));
    }

    private void doMove(int direction) {
        // FIXME 移动声音
        playPoolMusic(mMusicMove);
        MoveParam moveParam = new MoveParam();
        moveParam.direction = direction;
        moveParam.vmc_no = mRoomItem.machineSn;
        MinaManager.getInstance().sendMsg(new Gson().toJson(moveParam));


    }


    private void doLoosen() {
        // FIXME 松开后的操作
        // //playPoolMusic(mMusicMove);
        Log.i("doLoosen", "2");
        MoveParam moveParam = new MoveParam();
        moveParam.cmd = "stop";
        moveParam.vmc_no = mRoomItem.machineSn;
        MinaManager.getInstance().sendMsg(new Gson().toJson(moveParam));
    }

    /**
     * 加载房间信息
     */
    private void loadEnterRoom() {
        mEnterRoomInfo = new EnterRoomInfo();
        //  mEnterRoomInfo.roomId = mRoomItem.machineId;
        mEnterRoomInfo.liveRoom1 = new LiveRoom();
        mEnterRoomInfo.liveRoom1.channelKey = mRoomItem.channelKey1;
        mEnterRoomInfo.liveRoom1.room = mRoomItem.liveRoom1;
        mEnterRoomInfo.liveRoom2 = new LiveRoom();
        mEnterRoomInfo.liveRoom2.channelKey = mRoomItem.channelKey2;
        mEnterRoomInfo.liveRoom2.room = mRoomItem.liveRoom2;
        initSound();
        if (mEnterRoomInfo.liveRoom1 != null
                && !TextUtils.isEmpty(mEnterRoomInfo.liveRoom1.channelKey)) {
            mCurrentLive = mEnterRoomInfo.liveRoom1;
        } else if (mEnterRoomInfo.liveRoom2 != null
                && !TextUtils.isEmpty(mEnterRoomInfo.liveRoom2.channelKey)) {
            mCurrentLive = mEnterRoomInfo.liveRoom2;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mCurrentLive != null) {
                    // 进入房间
                    worker().joinChannel(mCurrentLive.channelKey,
                            mCurrentLive.room, config().mUid);
                    if (!mIsStarted) {
                        mStartContainer.setVisibility(View.VISIBLE);
                    }
                }
            }
        }, 500);

        BusinessManager.getInstance()
                .enterRoom(
                        new EnterRoomRequest(RunningContext.accessToken,
                                mRoomItem.machineSn),
                        new MyCallback<EnterRoomInfo>() {
                            @Override
                            public void onSuccess(EnterRoomInfo result,
                                                  String message) {
                                if (result == null) {
                                    showToast("网络异常");
                                    return;
                                }
                                mEnterRoomInfo.roomId = result.roomId;
                                if (mRoomItem.liveRoom1 == null && mRoomItem.liveRoom2 == null) {
                                    mEnterRoomInfo = result;
//									initSound();
                                    if (mEnterRoomInfo.liveRoom1 != null
                                            && !TextUtils.isEmpty(mEnterRoomInfo.liveRoom1.channelKey)) {
                                        mCurrentLive = mEnterRoomInfo.liveRoom1;
                                    } else if (mEnterRoomInfo.liveRoom2 != null
                                            && !TextUtils.isEmpty(mEnterRoomInfo.liveRoom2.channelKey)) {
                                        mCurrentLive = mEnterRoomInfo.liveRoom2;
                                    }
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (mCurrentLive != null) {
                                                // 进入房间
                                                worker().joinChannel(mCurrentLive.channelKey,
                                                        mCurrentLive.room, config().mUid);
                                                if (!mIsStarted) {
                                                    mStartContainer.setVisibility(View.VISIBLE);
                                                }
                                            }
                                        }
                                    }, 500);
                                }
                                // 加入聊天室
                                joinHXRoom();
                            }

                            @Override
                            public void onError(String errorCode,
                                                String message) {
                                showToast(message);
                            }

                            @Override
                            public void onFinished() {

                            }
                        });
    }
//nj44
//
// jj

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(final BaseMinaMsg minaMsg) {
        // 消息接收
        if (MinaCmd.CONN_R.equals(minaMsg.cmd)) {
            // 连接
            boolean isFirst = mConnectResult == null;
            mConnectResult = mGson.fromJson(minaMsg.msg, ConnectResult.class);
            Log.i("dfqqrt", "onMessage: " + mConnectResult.toString());
            li_point_top.setText(mConnectResult.point + "");
            mMoneyTv.setText(mConnectResult.remainGold);
            mRoomState = mConnectResult.room_status;
            showStartState();
            if (mConnectResult.status != 200) {
                // 连接失败
                showToast(mConnectResult.msg);
                return;
            }
            ImgLoaderUtil.displayImage(mConnectResult.headUrl, mAvatarIv,
                    getResources().getDrawable(R.mipmap.pic_zww_default));
            if (mConnectResult.isGame == 1) {
                mIsStarted = true;
                mIsGrabed = false;
                // 隐藏关闭按钮
                findViewById(R.id.iv_live_close).setVisibility(View.INVISIBLE);
                startRecord(mConnectResult.dollLogId);
                mStartContainer.setVisibility(View.INVISIBLE);
                mGrabContainer.setVisibility(View.VISIBLE);
                mCountTv.setText("倒计时 " + mConnectResult.remainSecond + "秒");
                startCountDown(mConnectResult.remainSecond);
                return;
            }
            if (!isFirst && mIsStartClicked) {
                // 非进来连接，直接调用开始
                doStart();
            }
        } else if (MinaCmd.START_R.equals(minaMsg.cmd)) {
            // 开始
            StartResult startResult = mGson.fromJson(minaMsg.msg,
                    StartResult.class);
            Log.i("dfqqrt", "onMessage: " + startResult.toString());
            if (startResult.status != 200) {
                showToast(startResult.msg);
                return;
            }
            mMoneyTv.setText(startResult.remainGold);
            li_point_top.setText(startResult.point + "");
            // 设置扣减后剩余的金额
//            long remainGold = -1;
//            try {
//                remainGold = Long.parseLong(startResult.remainGold);
//            } catch (Exception e) {
//            }
//            if (remainGold != -1) {
//                mMoneyTv.setText(startResult.remainGold);
//                li_point_top.setText(startResult.point+"");
//                RunningContext.loginTelInfo.money = remainGold;
//            }
            if (mIsStarted) {
                return;
            }
        //    doConfigEngine(Constants.CLIENT_ROLE_BROADCASTER);
            ImgLoaderUtil.displayImage(RunningContext.loginTelInfo.avatar,
                    mAvatarIv,
                    getResources().getDrawable(R.mipmap.pic_zww_default));
            mIsStarted = true;
            mIsGrabed = false;
            // 隐藏关闭按钮
            findViewById(R.id.iv_live_close).setVisibility(View.INVISIBLE);
            showCountDialog();
            startRecord(startResult.dollLogId);
            mStartContainer.setVisibility(View.INVISIBLE);
            mGrabContainer.setVisibility(View.VISIBLE);
            mCountTv.setText("倒计时 " + "30秒");
            // 3秒后开始即时
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startCountDown(30);
                }
            }, 3000);
        } else if (MinaCmd.STATUS.equals(minaMsg.cmd)) {
            // 状态
            if (mIsStarted) {
                return;
            }
            VmcStatusResult statusResult = mGson.fromJson(minaMsg.msg,
                    VmcStatusResult.class);
            mRoomState = statusResult.gameStatus;
            ImgLoaderUtil.displayImage(statusResult.headUrl, mAvatarIv,
                    getResources().getDrawable(R.mipmap.pic_zww_default));
            // 设置开始按钮颜色
            showStartState();
        } else if (MinaCmd.GRAB_R.equals(minaMsg.cmd)) {
            // 抓取
            if (!mIsStarted) {
                return;
            }
      //      doConfigEngine(Constants.CLIENT_ROLE_AUDIENCE);
            mGrabContainer.setVisibility(View.INVISIBLE);
            mStartContainer.setVisibility(View.VISIBLE);
            mOnGrabTouchListener.release();
            findViewById(R.id.iv_live_close).setVisibility(View.VISIBLE);
            stopRecord();
            GrabResult grabResult = mGson.fromJson(minaMsg.msg,
                    GrabResult.class);
            //  UserManager.getInstance().refresh();
            if (grabResult.value == 0) {
                // 抓取失败
                showResultDialog(false);
                if (mEnterRoomInfo != null && mEMChatRoom != null) {
                    HXChatUtil.sendRoomMsg(RunningContext.loginTelInfo.nickname,
                            com.ydd.pockettoycatcherrwz.util.Constants.HX_TYPE_NEARLY_SUCCESS,
                            mEMChatRoom.getId());
                }
            } else if (grabResult.value == 1) {
                // 抓取成功
                if (mEnterRoomInfo != null && mEMChatRoom != null) {
                    HXChatUtil.sendRoomMsg(RunningContext.loginTelInfo.nickname,
                            com.ydd.pockettoycatcherrwz.util.Constants.HX_TYPE_SUCCESS,
                            mEMChatRoom.getId());
                }
                showResultDialog(true);
            }
            mIsStarted = false;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(UserInfo minaMsg) {
//        mMoneyTv.setText(String.valueOf(RunningContext.loginTelInfo.money));
//        li_point_top.setText(RunningContext.loginTelInfo.points+"");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(TokenInvalidMsg msg) {
        finish();
    }

    /**
     * 设置开始按钮颜色
     */
    private void showStartState() {
        if (mRoomState == 0) {
            mStartTv.setInnerColor(Color.parseColor("#6bf680"));
        } else {
            mStartTv.setInnerColor(
                    getResources().getColor(R.color.app_common_color));
        }
    }

    private void showRechargeDialog() {
        if (mRechargeListDialog == null) {
            mRechargeListDialog = new RechargeListDialog(this);
        }
        mRechargeListDialog.show();
    }

    private void showResultDialog(boolean isSuccess) {
        int musicId = isSuccess ? mMusicSucceed : mMusicFailed;
        playPoolMusic(musicId);
        int type = isSuccess
                ? com.ydd.pockettoycatcherrwz.util.Constants.HX_TYPE_SUCCESS
                : com.ydd.pockettoycatcherrwz.util.Constants.HX_TYPE_NEARLY_SUCCESS;
        String desc = BusinessUtil.getChatTypeDesc(
                RunningContext.loginTelInfo.nickname, String.valueOf(type));
        addDanmaku(desc);
        mResultDialog = new LiveGrabResultDialog(this, mRoomItem, isSuccess);
        // mResultDialog = new LiveGrabResultDialog(this, mRoomItem, true);
        mResultDialog.setRetryListener(
                new LiveGrabResultDialog.OnRetryClickListener() {
                    @Override
                    public void onRetryClick() {
                        // 再次开始
                        doStart();
                        mResultDialog.dismiss();
                    }
                });
        mResultDialog.show();
    }

    @Override
    public void onBackPressed() {
        if (mIsStarted) {
            return;
        }
        super.onBackPressed();
    }

    private void showLiveDetailsDialog() {
        if (mLiveDetailDialog == null) {
            mLiveDetailDialog = new LiveDetailDialog(this, mRoomItem);
        }
        mLiveDetailDialog.show();
    }

    private void showCountDialog() {
        mLiveCountDialog = new LiveCountDialog(this);
        mLiveCountDialog.show();
        playPoolMusic(mMusicReady);
    }

    @Override
    protected void onDestroy() {
        if (mTimer != null) {
            mTimer.cancel();
        }
        if (sp != null) {
            sp.stop(mMusicFailed);
            sp.stop(mMusicReady);
            sp.stop(mMusicSucceed);
            sp.release();
        }
        if (mBgmPlayer != null) {
            mBgmPlayer.stop();
            mBgmPlayer.release();
        }
        if (mResultDialog != null) {
            mResultDialog.release();
        }
        if (danmakuView != null) {
            danmakuView.clear();
        }
        if (mOnGrabTouchListener != null) {
            mOnGrabTouchListener.release();
        }
        MinaManager.getInstance().disconnected();
        CommonUtil.dismissDialog(mLiveDetailDialog);
        CommonUtil.dismissDialog(mRechargeListDialog);
        CommonUtil.dismissDialog(mResultDialog);
        EventBus.getDefault().unregister(this);
        stopRecord();
        leaveHXRoom();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == RECORD_REQUEST_CODE) {

            if (Build.VERSION.SDK_INT >= 21) {
                MediaProjection mediaProjection = mMediaProjectionManager
                        .getMediaProjection(resultCode, data);
                if (mediaProjection == null) {
                    showToast("录制失败");
                    return;
                }

                // video size
                final int width = 480;
                final int height = 640;
                File file = new File(
                        com.ydd.pockettoycatcherrwz.util.Constants.VIDEO_PATH + "/"
                                + mDollLogId + ".mp4");
                final int bitrate = 1500000;
                mRecorder = new ScreenRecorder(width, height, bitrate, 1,
                        mediaProjection, file.getAbsolutePath());
                mRecorder.start();
                // moveTaskToBack(true);

                return;

            }

            Tencent.onActivityResultData(requestCode, resultCode, data,
                    new IUiListener() {
                        @Override
                        public void onComplete(Object o) {
                            LogUtil.printJ("activity complete");
                        }

                        @Override
                        public void onError(UiError uiError) {
                            LogUtil.printJ("activity errorcode=" + uiError.errorCode
                                    + "  detail=" + uiError.errorDetail
                                    + "  message=" + uiError.errorMessage);
                        }

                        @Override
                        public void onCancel() {
                            LogUtil.printJ("activity cancel");
                        }
                    });

        }
    }

    /**
     * 开始录制
     *
     * @param dollLogId 游戏记录id
     */
    private void startRecord(String dollLogId) {
        if(Build.VERSION.SDK_INT>=21) {
            mDollLogId = dollLogId;
            Intent captureIntent = mMediaProjectionManager
                    .createScreenCaptureIntent();
            startActivityForResult(captureIntent, RECORD_REQUEST_CODE);
        }
    }

    /**
     * 停止录制
     */
    private void stopRecord() {
        if (mRecorder != null) {
            mRecorder.quit();
            mRecorder = null;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    UploadVideoManager.getInstance().addToUpload(mDollLogId);
                }
            }, 2000);

        }
    }

    /**
     * 加入聊天室
     */
    private void joinHXRoom() {
        HXChatUtil.joinChatRoom(mEnterRoomInfo.roomId,
                new EMValueCallBack<EMChatRoom>() {
                    @Override
                    public void onSuccess(final EMChatRoom emChatRoom) {
                        // 加入房间
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mEMChatRoom = emChatRoom;
                                // changeNum(1);
                                EMClient.getInstance().chatManager()
                                        .addMessageListener(mMessageListener);
                                HXChatUtil.sendRoomMsg(
                                        RunningContext.loginTelInfo.nickname,
                                        com.ydd.pockettoycatcherrwz.util.Constants.HX_TYPE_JOIN,
                                        emChatRoom.getId());
                                String desc = BusinessUtil.getChatTypeDesc(
                                        RunningContext.loginTelInfo.nickname,
                                        String.valueOf(
                                                com.ydd.pockettoycatcherrwz.util.Constants.HX_TYPE_JOIN));
                                addDanmaku(desc);
                            }
                        });
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });
    }

    /**
     * 离开聊天室
     */
    private void leaveHXRoom() {
        if (mEnterRoomInfo != null && mEMChatRoom != null) {
            HXChatUtil.sendRoomMsg(RunningContext.loginTelInfo.nickname,
                    com.ydd.pockettoycatcherrwz.util.Constants.HX_TYPE_QUIT,
                    mEMChatRoom.getId());
            HXChatUtil.leaveChatRoom(mEnterRoomInfo.roomId);
        }
        EMClient.getInstance().chatManager()
                .removeMessageListener(mMessageListener);
    }

    EMMessageListener mMessageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            LogUtil.printCZ("message");
            // 收到消息
            try {
                // 捕获下异常，第三方未必可靠
                for (EMMessage emMessage : messages) {
                    if (emMessage
                            .getChatType() != EMMessage.ChatType.ChatRoom) {
                        continue;
                    }
                    String type = String.valueOf(emMessage.ext().get("type"));
                    String name = (String) emMessage.ext().get("name");
                    String desc = BusinessUtil.getChatTypeDesc(name, type);
                    addDanmaku(desc);
                    int typeInt = Integer.valueOf(type);
                    if (typeInt == com.ydd.pockettoycatcherrwz.util.Constants.HX_TYPE_JOIN) {
                        // 进入，人数+1
                        changeNum(1);
                    } else if (typeInt == com.ydd.pockettoycatcherrwz.util.Constants.HX_TYPE_QUIT) {
                        // 离开，人数-1
                        changeNum(-1);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            // 收到透传消息
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            // 收到已读回执
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            // 收到已送达回执
        }

        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            // 消息被撤回
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            // 消息状态变动
        }
    };

    private void changeNum(final int size) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String num = mPersonsNumTv.getText().toString();
                if (!TextUtils.isEmpty(num)) {
                    mPersonsNumTv.setText(Integer.parseInt(num) + size + "");
                }
            }
        });
    }

    /**
     * 开始倒计时
     */
    private void startCountDown(int count) {
        stopTimer();
        mCountDownTimer = new CountDownTimer(count * 1000 + 50, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                int count = (int) (millisUntilFinished / 1000);
                mCountTv.setText("倒计时 " + count + "秒");
            }

            @Override
            public void onFinish() {
                doGrab();
                if (!mIsGrabed) {
                    playPoolMusic(mMusicWhistle);
                }
                mCountTv.setText("倒计时 " + 0 + "秒");
            }
        };
        mCountDownTimer.start();
    }

    private void stopTimer() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    /**
     * 倒计时计数器
     */
    private CountDownTimer mCountDownTimer = null;

    /**
     * toast提示
     */
    private Toast mToast;

    /**
     * toast提示
     *
     * @param msg 待提示内容
     */
    private void showToast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }

    private void dealWithWater() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File f = new File(com.ydd.pockettoycatcherrwz.util.Constants.CACHE_PATH, "failed"
                        + RunningContext.loginTelInfo.inviteCode + ".png");
                if (f.exists())
                    return;
                LogUtil.printJ("not exists");
                Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                        R.mipmap.share_fail);
                Bitmap bmp2 = WaterImgUtil.drawInviteFailedCode(
                        LiveActivity.this, bmp,
                        RunningContext.loginTelInfo.inviteCode);
                ImageUtil.saveBitmapToDisk(bmp2, com.ydd.pockettoycatcherrwz.util.Constants.CACHE_PATH,
                        "failed" + RunningContext.loginTelInfo.inviteCode);
            }
        }).start();
    }


}
