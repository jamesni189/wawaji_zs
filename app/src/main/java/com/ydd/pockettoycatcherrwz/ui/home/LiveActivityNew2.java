package com.ydd.pockettoycatcherrwz.ui.home;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMMessage;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.umeng.analytics.MobclickAgent;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.entity.Players;
import com.ydd.pockettoycatcherrwz.entity.RoomItem;
import com.ydd.pockettoycatcherrwz.entity.RoomLogBack2;
import com.ydd.pockettoycatcherrwz.entity.RoomLogInfo;
import com.ydd.pockettoycatcherrwz.entity.TokenInvalidMsg;
import com.ydd.pockettoycatcherrwz.entity.UserInfo;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.UploadVideoManager;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.EnterMachineRequest;
import com.ydd.pockettoycatcherrwz.network.mina.BaseMinaMsg;
import com.ydd.pockettoycatcherrwz.network.mina.MinaCmd;
import com.ydd.pockettoycatcherrwz.network.mina.MinaManager;
import com.ydd.pockettoycatcherrwz.network.mina.request.ConnectParam;
import com.ydd.pockettoycatcherrwz.network.mina.request.GrabParam;
import com.ydd.pockettoycatcherrwz.network.mina.request.HbParam;
import com.ydd.pockettoycatcherrwz.network.mina.request.JoinParam;
import com.ydd.pockettoycatcherrwz.network.mina.request.MoveParam;
import com.ydd.pockettoycatcherrwz.network.mina.request.StartParam;
import com.ydd.pockettoycatcherrwz.network.mina.result.ChatResult;
import com.ydd.pockettoycatcherrwz.network.mina.result.ConnectResult;
import com.ydd.pockettoycatcherrwz.network.mina.result.GrabResult;
import com.ydd.pockettoycatcherrwz.network.mina.result.StartResult;
import com.ydd.pockettoycatcherrwz.network.mina.result.SystemResult;
import com.ydd.pockettoycatcherrwz.network.mina.result.VmcStatusResult;
import com.ydd.pockettoycatcherrwz.ui.adapter.ImageShowAdapter;
import com.ydd.pockettoycatcherrwz.ui.adapter.MemberAdapter;
import com.ydd.pockettoycatcherrwz.ui.adapter.MessageAdapter;
import com.ydd.pockettoycatcherrwz.ui.adapter.NewGrabRecordAdapter;
import com.ydd.pockettoycatcherrwz.ui.personal.RechargeListDialog;
import com.ydd.pockettoycatcherrwz.util.BusinessUtil;
import com.ydd.pockettoycatcherrwz.util.CommentFun;
import com.ydd.pockettoycatcherrwz.util.CommonUtil;
import com.ydd.pockettoycatcherrwz.util.Constants;
import com.ydd.pockettoycatcherrwz.util.ImgLoaderUtil;
import com.ydd.pockettoycatcherrwz.util.LogUtil;
import com.ydd.pockettoycatcherrwz.util.ScreenRecorder;
import com.ydd.pockettoycatcherrwz.util.SharedPrefConfig;
import com.ydd.pockettoycatcherrwz.util.hx.HXChatUtil;
import com.ydd.pockettoycatcherrwz.view.HorizontialListView;
import com.ydd.pockettoycatcherrwz.view.NoScrollListView;
import com.ydd.pockettoycatcherrwz.widget.LiveDetailDialog;
import com.ydd.pockettoycatcherrwz.widget.StrokeTextView;
import com.zego.zegoliveroom.ZegoLiveRoom;
import com.zego.zegoliveroom.callback.IZegoLivePlayerCallback;
import com.zego.zegoliveroom.callback.IZegoLivePublisherCallback;
import com.zego.zegoliveroom.callback.IZegoLoginCompletionCallback;
import com.zego.zegoliveroom.callback.IZegoRoomCallback;
import com.zego.zegoliveroom.constants.ZegoConstants;
import com.zego.zegoliveroom.entity.AuxData;
import com.zego.zegoliveroom.entity.ZegoStreamInfo;
import com.zego.zegoliveroom.entity.ZegoStreamQuality;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import io.agora.openlive.ui.BaseActivity;
import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;
import wawaji_client.CommandUtil;
import wawaji_client.ZegoApiManager;
import wawaji_client.ZegoStream;

/**
 *
 */
public class LiveActivityNew2 extends BaseActivity implements View.OnLayoutChangeListener {

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
    private TextView mGamePriceTv;
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
    private RelativeLayout mLiveStart;
    /**
     * 房间信息
     */
    private RoomItem mRoomItem;
    /**
     * 房间流信息
     */
    /**
     * 当前流信息
     */

    /**
     * 充值对话框
     */
    private RechargeListDialog mRechargeListDialog;
    /**
     * 抓取结果对话框
     */
    private LiveGrabResultDialog mResultDialog;
    private GrabResultDialog mGrabResultDialog;
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

    private   Dialog dialog;
    //
    int uid = 0;
    Integer[] uids = new Integer[]{0, 1};

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
    //直播流
    private List<ZegoStream> mListStream = new ArrayList<>();
    private ZegoLiveRoom mZegoLiveRoom = ZegoApiManager.getInstance().getZegoLiveRoom();
    private LinearLayout ln_live_start;
    private boolean noStream;


    private long startTime = 0;
    private long endTime = 0;
    private int firstViewId = 0;
    private int lastViewId = 0;
    private View live_bottom_view;
    private RelativeLayout record_line;
    private NoScrollListView lv_pic;
    private ListView list_message;
    private HorizontialListView lv_grab_records_list;
    private HorizontialListView avatar_list;
    private TextView tv_live_money;
    private MemberAdapter memberAdapter;
    //  private LiveImgView lv_pic;

    /**
     * 列表适配器
     * 列表适配器
     */
    // private LiveGrabRecordAdapter mAdapter;
    private NewGrabRecordAdapter mAdapter;
    /**
     * 当前页码
     */
    private int mCurrentPage;

    private Activity context;

    private String machineId;
    private TextView tv_show_message;

    private boolean isShowMsg;

    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;
    private TextView tv_send;


    Handler mHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // super.handleMessage(msg);
            if (msg.what == 1) {
                //doLoosen(msg.);
                getDirection(msg.arg1);
                Log.i("===st", ">" + "5");
            } else if (msg.what == 2) {
                line_live.fullScroll(ScrollView.FOCUS_UP);
            }
        }
    };

    private int member_count = 1;
    private String member_nickname = null;
    private MessageAdapter messageAdapter;// 消息适配器
    private List<ChatResult> messages = new ArrayList<>(); //消息列表


    private View sendView;
    private EditText sendEditText;

    private ScrollView line_live;
    private ImageView iv_set_music_play;
    private boolean isPlay = false;

    ImageShowAdapter picAdapter;

    private LinearLayout ln_live_share;
    private TextView tv_start_btn;

    private SharedPrefConfig mSpConfig;
    private String mLoginAccount;
    String result1=null;
    private RecyclerView id_recyclerview_horizontal;
    private GalleryAdapter galleryAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
      /*  if (RunningContext.loginTelInfo == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }*/
        mRoomItem = (RoomItem) getIntent().getSerializableExtra("roomItem");
        Log.i("dqqrrrtt", "onCreate: " + mRoomItem.toString());
        if (mRoomItem == null) {
            finish();
            return;
        }
        mMediaProjectionManager = (MediaProjectionManager) getSystemService(
                MEDIA_PROJECTION_SERVICE);
        setContentView(R.layout.activity_live_new5);
        if (mRoomItem.name == null) {
            initRoomInfo(mRoomItem.machineSn);
        }
        initView();

        getAndroiodScreenProperty();
        loadEnterRoom();
        // iv_set_music_play.setSelected(true);
        line_live.addOnLayoutChangeListener(this);
        machineId = mRoomItem.machineId;
        messageAdapter = new MessageAdapter(LiveActivityNew2.this);
        list_message.setAdapter(messageAdapter);
        mAdapter = new NewGrabRecordAdapter(LiveActivityNew2.this);
        lv_grab_records_list.setAdapter(mAdapter);
        if (mRoomItem.imgs != null && mRoomItem.imgs.size() > 0) {
            picAdapter.setDatas(mRoomItem.imgs);
            picAdapter.notifyDataSetChanged();
        }


        //lv_pic.setData(mRoomItem.imgs);
        MinaManager.getInstance().connect();
        joinRoom();
        // 定时发送心跳包
        doHb();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doConnect();
            }
        }, 1000);
         initSound();
        xutilsPost("",machineId);
       // loadLiveGrabData();
       // loadGrabData(1);

    /*    tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = sendEditText.getText().toString();
                if (!TextUtils.isEmpty(s)) {
                    sendChat(s);
                }
            }
        });*/


    }

    @Override
    protected void initUIandEvent() {

    }

    @Override
    protected void deInitUIandEvent() {

    }

    public void initRoomInfo(String machineId) {
        BusinessManager.getInstance().enterRoomByMachine(new EnterMachineRequest(RunningContext.accessToken, machineId), new MyCallback<RoomItem>() {
            @Override
            public void onSuccess(RoomItem result, String message) {
                mRoomItem = result;
            }

            @Override
            public void onError(String errorCode, String message) {
               Log.i("===",message);
               Toast.makeText(LiveActivityNew2.this,message,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinished() {

            }
        });
    }


    private ZegoStream constructStream(int index, String streamID) {
        String sateStrings[] = null;
        TextureView textureView;
        if (index == 0) {
            //      sateStrings = getResources().getStringArray(R.array.video1_state);
            //   sateStrings = getResources().getStringArray(R.array.video1_state);
            textureView = (TextureView) findViewById(R.id.textureview1);
        } else {
            //  sateStrings = getResources().getStringArray(R.array.video2_state);
            textureView = (TextureView) findViewById(R.id.textureview2);
        }


        return new ZegoStream(streamID, textureView, sateStrings);
    }

    /**
     * 加入房间
     */
    private void startPlay() {
        mZegoLiveRoom.loginRoom(mRoomItem.liveRoomCode, ZegoConstants.RoomRole.Audience, new IZegoLoginCompletionCallback() {
            @Override
            public void onLoginCompletion(int errCode, ZegoStreamInfo[] zegoStreamInfos) {

                CommandUtil.getInstance().printLog("[onLoginCompletion], roomID: " + zegoStreamInfos.length);
                if (zegoStreamInfos.length == 0) {
                    showToast("当前没有视屏流");
                    noStream = true;
                    //live_start_button_go_press
                    ln_live_start.setBackgroundResource(R.mipmap.yz_live_start_game_icon);
                    return;
                }
                //播放的是从流
                if (zegoStreamInfos.length == 1) {
                    if (zegoStreamInfos[0].streamID.endsWith("_2")) {
                        uid = 1;
                        mListStream.add(constructStream(0, zegoStreamInfos[0].streamID));
                        mListStream.get(0).playStream(0);
                    } else {
                        uid = 0;
                        mListStream.add(constructStream(0, zegoStreamInfos[0].streamID));
                        mListStream.get(0).playStream(0);
                    }
                    return;
                }
                if (zegoStreamInfos[0].streamID.endsWith("_2")) {
                    mListStream.add(constructStream(0, zegoStreamInfos[1].streamID));
                    mListStream.add(constructStream(1, zegoStreamInfos[0].streamID));
                } else {
                    mListStream.add(constructStream(0, zegoStreamInfos[0].streamID));
                    mListStream.add(constructStream(1, zegoStreamInfos[1].streamID));
                }
                for (int i = 0; i < zegoStreamInfos.length; i++) {
                    mListStream.get(i).playStream(0);
                    mListStream.get(i).playStream(0);
                }

            }
        });
        CommandUtil.getInstance().printLog("[onLoginCompletion], roomID: " + mListStream.size());
        mZegoLiveRoom.setZegoLivePlayerCallback(new IZegoLivePlayerCallback() {
            @Override
            public void onPlayStateUpdate(int errCode, String streamID) {

                int currentShowIndex = 0;

                if (errCode != 0) {
                    // 设置流的状态
                    for (ZegoStream zegoStream : mListStream) {
                        if (zegoStream.getStreamID().equals(streamID)) {
                            zegoStream.setStreamSate(ZegoStream.StreamState.PlayFail);
                            break;
                        }
                    }
                    ZegoStream currentShowStream = mListStream.get(currentShowIndex);

                }
            }

            @Override
            public void onPlayQualityUpdate(String streamID, ZegoStreamQuality zegoStreamQuality) {
                // 当前显示的流质量

            }

            @Override
            public void onInviteJoinLiveRequest(int i, String s, String s1, String s2) {

            }

            @Override
            public void onRecvEndJoinLiveCommand(String s, String s1, String s2) {

            }

            @Override
            public void onVideoSizeChangedTo(String streamID, int i, int i1) {
                for (ZegoStream zegoStream : mListStream) {
                    if (zegoStream.getStreamID().equals(streamID)) {
                        zegoStream.setStreamSate(ZegoStream.StreamState.PlaySuccess);
                        break;
                    }
                }
                int currentShowIndex = 0;

                ZegoStream currentShowStream = mListStream.get(0);
                if (currentShowStream.getStreamID().equals(streamID)) {
                    currentShowStream.show();
                }

                CommandUtil.getInstance().printLog("[onVideoSizeChanged], streamID: " + streamID + ", currentShowIndex: " + currentShowIndex);
            }
        });

        mZegoLiveRoom.setZegoLivePublisherCallback(new IZegoLivePublisherCallback() {
            @Override
            public void onPublishStateUpdate(int errCode, String streamID, HashMap<String, Object> hashMap) {
                CommandUtil.getInstance().printLog("[onPublishStateUpdate], streamID: " + streamID + ", errorCode: " + errCode);
            }

            @Override
            public void onJoinLiveRequest(int i, String s, String s1, String s2) {

            }

            @Override
            public void onPublishQualityUpdate(String s, ZegoStreamQuality zegoStreamQuality) {

            }

            @Override
            public AuxData onAuxCallback(int i) {
                return null;
            }

            @Override
            public void onCaptureVideoSizeChangedTo(int i, int i1) {

            }

            @Override
            public void onMixStreamConfigUpdate(int i, String s, HashMap<String, Object> hashMap) {

            }
        });

        mZegoLiveRoom.setZegoRoomCallback(new IZegoRoomCallback() {
            @Override
            public void onKickOut(int reason, String roomID) {

            }

            @Override
            public void onDisconnect(int errorCode, String roomID) {
            }

            @Override
            public void onReconnect(int i, String s) {

            }

            @Override
            public void onTempBroken(int i, String s) {

            }

            @Override
            public void onStreamUpdated(final int type, final ZegoStreamInfo[] listStream, final String roomID) {
            }

            @Override
            public void onStreamExtraInfoUpdated(ZegoStreamInfo[] zegoStreamInfos, String s) {

            }

            @Override
            public void onRecvCustomCommand(String userID, String userName, String content, String roomID) {
                // 只接收当前房间，当前主播发来的消息
            }
        });
    }


    private void initSound() {
     /*   SharedPrefConfig spConfig = new SharedPrefConfig();
        spConfig.open(this,
                Constants.SP_FILE_CONFIG);*/
        mSpConfig = new SharedPrefConfig();
        mSpConfig.open(this, Constants.SP_FILE_CONFIG);

        String loginAccont = mSpConfig.getString("last_login", "");

        mLoginAccount = mSpConfig.getString("last_login", "");
        if (!TextUtils.isEmpty(mLoginAccount)) {
            mSpConfig.open(this,
                    mLoginAccount + "_" + Constants.SP_FILE_CONFIG);
        }

     /*   if (!TextUtils.isEmpty(loginAccont)) {
            mSpConfig.open(this, loginAccont + "_"
                    + Constants.SP_FILE_CONFIG);
        }*/
        if (mSpConfig.getBoolean(Constants.SP_MUSIC_ON_1, true)) {
            iv_set_music_play.setSelected(true);
            isPlay = false;
        } else {
            iv_set_music_play.setSelected(false);
            isPlay = true;
        }

        isMusicOn1 = mSpConfig.getBoolean(
                Constants.SP_MUSIC_ON_1,
                true);
        isMusicOn2 = mSpConfig.getBoolean(
                Constants.SP_MUSIC_ON_2,
                true);
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
        //  mLoadingIv = (ImageView) findViewById(R.id.iv_live_loading);
        mAvatarIv = (ImageView) findViewById(R.id.iv_live_avatar);
        iv_set_music_play = (ImageView) findViewById(R.id.iv_set_music_play);
        mMoneyTv = (TextView) findViewById(R.id.tv_live_money1);
        //tv_send = (TextView) findViewById(R.id.tv_send);

        mStartContainer = (LinearLayout) findViewById(
                R.id.ln_live_start_container);

        record_line = (RelativeLayout) findViewById(
                R.id.record_line);
        lv_pic = (NoScrollListView) findViewById(R.id.lv_pic);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);


        id_recyclerview_horizontal = (RecyclerView) findViewById(R.id.id_recyclerview_horizontal);
        galleryAdapter = new GalleryAdapter(LiveActivityNew2.this);
        id_recyclerview_horizontal.setLayoutManager(linearLayoutManager);
        id_recyclerview_horizontal.setAdapter(galleryAdapter);

      //  ln_live_share = (LinearLayout) findViewById(R.id.ln_live_share);

        list_message = (ListView) findViewById(R.id.list_message);
        lv_grab_records_list = (HorizontialListView) findViewById(R.id.lv_grab_records_list);
        avatar_list = (HorizontialListView) findViewById(R.id.avatar_list);
        line_live = (ScrollView) findViewById(R.id.my_scroll);

        mGamePriceTv = (TextView) findViewById(R.id.tv_live_game_price);
        tv_start_btn = (TextView) findViewById(R.id.tv_start_btn);
        //  tv_live_money = (TextView) findViewById(R.id.tv_live_money);

        mPersonsNumTv = (TextView) findViewById(R.id.tv_live_persons_num);
        tv_show_message = (TextView) findViewById(R.id.tv_show_message);
        mGrabContainer = (LinearLayout) findViewById(
                R.id.ln_live_grab_container);
        danmakuView = (DanmakuView) findViewById(R.id.view_live_danmu);
        mCountTv = (TextView) findViewById(R.id.tv_live_count);
        ln_live_start = (LinearLayout) findViewById(R.id.ln_live_start);
        live_bottom_view = findViewById(R.id.live_bottom_view);

       // sendView = findViewById(R.id.layout_send_message);
       // sendEditText = (EditText) sendView.findViewById(R.id.send_edit);

        //   mStartTv = (StrokeTextView) findViewById(R.id.tv_live_start);
        mLiveContainerRl = (RelativeLayout) findViewById(
                R.id.rl_live_container);

//        memberAdapter = new MemberAdapter(LiveActivityNew.this);
//        avatar_list.setAdapter(memberAdapter);

        picAdapter = new ImageShowAdapter(LiveActivityNew2.this);
        lv_pic.setAdapter(picAdapter);

        mLiveStart = (RelativeLayout) findViewById(
                R.id.line_live);
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
        findViewById(R.id.ln_live_start).setOnClickListener(mOnClickListener);
        findViewById(R.id.iv_set_music_play).setOnClickListener(mOnClickListener);
        findViewById(R.id.ln_live_recharge)
                .setOnClickListener(mOnClickListener);
        findViewById(R.id.iv_live_refresh).setOnClickListener(mOnClickListener);
        findViewById(R.id.tv_show_message).setOnClickListener(mOnClickListener);

        mMoneyTv.setText(String.valueOf(RunningContext.loginTelInfo.money));
        mGamePriceTv.setText(String.valueOf(mRoomItem.price));

        initDanmuView();

        //   sendEditText.setOnClickListener(mOnClickListener);
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

        //获取屏幕高度
        screenHeight = getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight / 3;
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
        if (danmaku == null) {
            return;
        }
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
                    if (mListStream.size() == 0) {
                        return;
                    }
                    doStart();
                    break;


                case R.id.ln_live_details: //发言


                    inputComment(LiveActivityNew2.this,list_message,mLiveStart);

                    // 详情
                    //    showLiveDetailsDialog();
                    break;
                case R.id.ln_live_recharge:
                    // 充值
                    startActivity(new Intent(LiveActivityNew2.this, Recharge_newActivity.class));
                    //     showRechargeDialog();
                    break;
                case R.id.iv_live_refresh:
                    // 切换流
                    doSwitch();
                    // doRefresh();
                    break;
                case R.id.iv_set_music_play:
                    if (!isPlay) {
                        if (mBgmPlayer != null) {
                            mBgmPlayer.pause();
                        }
                        isPlay = true;
                        mSpConfig.putBoolean(Constants.SP_MUSIC_ON_1, false);

                        iv_set_music_play.setSelected(false);
                    } else {
                        if (mBgmPlayer != null) {
                            mBgmPlayer.start();
                        }
                        isPlay = false;
                        mSpConfig.putBoolean(Constants.SP_MUSIC_ON_1, true);

                        iv_set_music_play.setSelected(true);
                    }
                    // doRefresh();
                    break;

                case R.id.tv_show_message:
                    if (isShowMsg) {
                        tv_show_message.setText("展开");
                        isShowMsg = false;
                        list_message.setVisibility(View.VISIBLE);
                    } else {
                        tv_show_message.setText("收起");
                        isShowMsg = true;
                        list_message.setVisibility(View.INVISIBLE);
                    }
                    break;


            }
        }
    };

    private void sendChat(String content) {
        ChatResult chatParam = new ChatResult();
        chatParam.cmd = "text_message";
        chatParam.content = content;
        chatParam.vmc_no = mRoomItem.machineSn;
        MinaManager.getInstance().sendMsg(new Gson().toJson(chatParam));
        chatParam.sender = RunningContext.loginTelInfo.nickname;
        messages.add(chatParam);
        messageAdapter.setDatas(messages);
        messageAdapter.notifyDataSetChanged();

        // messageAdapter.setDatas();
    }


    private void doSwitch() {
        if (mListStream.size() == 0) {
            showToast("当前房间没有摄像头！");
            return;
        }
        if (mListStream.size() == 1) {
            showToast("当前房间只有1个摄像头！");
            return;
        }
        if (mListStream.get(uid) != null)
            mListStream.get(uid).hide();
        int lastUid = uid;
        if (uid == uids[0]) {
            lastUid = uids[1];
        } else {
            lastUid = uids[0];
        }
        uid = lastUid;
        Log.i("uid", "doSwitch: " + uid);
        mListStream.get(uid).show();
    }

    private OnGrabTouchListener mOnGrabTouchListener = new OnGrabTouchListener() {
        @Override
        protected void onViewPressed(int viewId) {
            switch (viewId) {
                case R.id.iv_live_grab_bottom:
                    //   后移
                    if (uid == uids[0]) {
                        doMove(2);
                    } else {
                        doMove(4);
                    }
                    break;
                case R.id.iv_live_grab_top:
                    //  前移
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
            Message message = new Message();
            message.what = 1;
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    endTime = new Date(System.currentTimeMillis()).getTime();
                    long diff = endTime - startTime;
                    lastViewId = v.getId();
                    message.arg1 = firstViewId;
                    if (diff > 200) {
                        mHandle.sendMessage(message);
                        Log.i("===st", diff + ">" + "3" + "message" + message.arg1);
                    } else {
                        mHandle.sendMessageDelayed(message, 200);
                        Log.i("===st", diff + ">" + "4" + "message" + message.arg1);
                    }
                    break;
                case MotionEvent.ACTION_DOWN:
                    //firstViewId = v.getId();
                    startTime = new Date(System.currentTimeMillis()).getTime();
                    long diff2 = startTime - endTime;
                    if (v.getId() != firstViewId) {
                        //doLoosen(firstViewId);
                        getDirection(firstViewId);
                        onViewPressed(v.getId());

                        mHandle.removeMessages(1);
                        Log.i("===do", firstViewId + ">" + lastViewId);
                    } else {
                        if (diff2 > 200) {
                            // doLoosen();
                            getDirection(firstViewId);
                            onViewPressed(v.getId());
                            Log.i("===id", diff2 + ">" + 1);
                        } else {
                            playPoolMusic(mMusicMove);
                            mHandle.removeMessages(1);

                            Log.i("===st", diff2 + ">" + "2");
                        }
                    }
                    firstViewId = v.getId();
                    Log.i("===st", firstViewId + ">");
                    break;
            }
            return true;
        }
    };

    private void doConnect() {
        ConnectParam param = new ConnectParam();
        param.token = RunningContext.accessToken;
        param.vmc_no = mRoomItem.machineSn;
        MinaManager.getInstance().sendMsg(new Gson().toJson(param));
    }

    private void doGrab() {
        if (!mIsGrabed) {
            playPoolMusic(mMusicTake);
        }
        if (mIsGrabed) {
            return;
        }
        mIsGrabed = true;
        stopTimer();
        GrabParam grabParam = new GrabParam();
        grabParam.vmc_no = mRoomItem.machineSn;
        MinaManager.getInstance().sendMsg(new Gson().toJson(grabParam));
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
        switchPlaySource(true);
        StartParam startParam = new StartParam();
        startParam.vmc_no = mRoomItem.machineSn;
        MinaManager.getInstance().sendMsg(new Gson().toJson(startParam));
    }

    private void doMove(int direction) {
        // FIXME 移动声音
        playPoolMusic(mMusicMove);
        MoveParam moveParam = new MoveParam();
        moveParam.direction = direction;
        moveParam.cmd = "move_direction";
        moveParam.vmc_no = mRoomItem.machineSn;
        MinaManager.getInstance().sendMsg(new Gson().toJson(moveParam));
    }

    /**
     * 加载房间信息
     */
    private void loadEnterRoom() {
        initSound();
        /**
         * 初始化房间流
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 进入房间
                startPlay();
                if (!mIsStarted) {
                    mStartContainer.setVisibility(View.VISIBLE);
                    record_line.setVisibility(View.VISIBLE);
                    lv_pic.setVisibility(View.VISIBLE);
                  //  ln_live_share.setVisibility(View.VISIBLE);
                    live_bottom_view.setVisibility(View.VISIBLE);
                }
            }
        }, 500);
//环信聊天室

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(final BaseMinaMsg minaMsg) {
        Log.i("qqeerr", "onMessage: " + minaMsg.toString());
        // 消息接收
        if (MinaCmd.CONN_R.equals(minaMsg.cmd)) {
            // 连接
            boolean isFirst = mConnectResult == null;
            mConnectResult = mGson.fromJson(minaMsg.msg, ConnectResult.class);
            ConnectResult mConnectResult1 = mGson.fromJson(minaMsg.msg, ConnectResult.class);
            mRoomState = mConnectResult1.room_status;

            showStartState();
            if (mConnectResult1.status != 200) {
                // 连接失败
                showToast(mConnectResult1.msg);
                return;
            }
            List<Players> players = mConnectResult1.getPlayers();
            if (players != null && players.size() > 0) {
                galleryAdapter.setDatas(players);
//                memberAdapter.setDatas(players);
//                memberAdapter.notifyDataSetChanged();
            }
            mPersonsNumTv.setText(mConnectResult1.member_count+"");
            ImgLoaderUtil.displayImage(mConnectResult1.headUrl, mAvatarIv);

            //   changeNum(member_count);
            Log.i("===2", member_count + "");
            if (mConnectResult1.isGame == 1) {
                mIsStarted = true;
                mIsGrabed = false;
                // 隐藏关闭按钮
                //findViewById(R.id.iv_live_close).setVisibility(View.INVISIBLE);
                startRecord(mConnectResult1.dollLogId);
                mStartContainer.setVisibility(View.GONE);//invisiable
                record_line.setVisibility(View.GONE);
                lv_pic.setVisibility(View.GONE);
           //     ln_live_share.setVisibility(View.GONE);
                //live_bottom_view.setVisibility(View.GONE);
                mGrabContainer.setVisibility(View.VISIBLE);
                ;
                mCountTv.setText("倒计时 " + mConnectResult1.remainSecond + "秒");
                startCountDown(mConnectResult1.remainSecond);
                return;
            }
            if (!isFirst && mIsStartClicked) {
                // 非进来连接，直接调用开始
                doStart();
            }
            // member_count = mConnectResult.member_count;
            //mPersonsNumTv.setText(member_count);
        } else if (MinaCmd.START_R.equals(minaMsg.cmd)) {
            // 开始
            StartResult startResult = mGson.fromJson(minaMsg.msg,
                    StartResult.class);
            if (startResult.status != 200) {
                showToast(startResult.msg);
                return;
            }
            // 设置扣减后剩余的金额
            long remainGold = -1;
            try {
                remainGold = Long.parseLong(startResult.remainGold);
            } catch (Exception e) {
            }
            if (remainGold != -1) {
                mMoneyTv.setText(startResult.remainGold);
                RunningContext.loginTelInfo.money = remainGold;
            }
            if (mIsStarted) {
                return;
            }
            //doConfigEngine(Constants.CLIENT_ROLE_BROADCASTER);
          /*  ImgLoaderUtil.displayImage(RunningContext.loginTelInfo.avatar,
                    mAvatarIv,
                    getResources().getDrawable(R.mipmap.pic_zww_default));*/
            ImgLoaderUtil.displayImage(RunningContext.loginTelInfo.avatar,
                    mAvatarIv);
            mIsStarted = true;
            mIsGrabed = false;
            // 隐藏关闭按钮
            //findViewById(R.id.iv_live_close).setVisibility(View.INVISIBLE);
            showCountDialog();
            startRecord(startResult.dollLogId);
            mStartContainer.setVisibility(View.GONE); //INVISIBLE
            record_line.setVisibility(View.GONE);
            lv_pic.setVisibility(View.GONE);
         //   ln_live_share.setVisibility(View.GONE);
            //   live_bottom_view.setVisibility(View.INVISIBLE);
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
            //  mPersonsNumTv.setText(member_count);
            // 设置开始按钮颜色
            showStartState();
        } else if (MinaCmd.GRAB_R.equals(minaMsg.cmd)) {
            // 抓取
            if (!mIsStarted) {
                return;
            }
            //	doConfigEngine(Constants.CLIENT_ROLE_AUDIENCE);
            mGrabContainer.setVisibility(View.GONE);
            mStartContainer.setVisibility(View.VISIBLE);
            record_line.setVisibility(View.VISIBLE);
            lv_pic.setVisibility(View.VISIBLE);
          //  ln_live_share.setVisibility(View.VISIBLE);
            // live_bottom_view.setVisibility(View.VISIBLE);

            mOnGrabTouchListener.release();
            findViewById(R.id.iv_live_close).setVisibility(View.VISIBLE);
            stopRecord();
            GrabResult grabResult = mGson.fromJson(minaMsg.msg,
                    GrabResult.class);
            if (grabResult.value == 0) {
                // 抓取失败
                showGarbResultDialog(false, grabResult);

                Log.i("qqerrrtt", "onMessage: " + grabResult.value);
                if (mEMChatRoom != null) {
                    HXChatUtil.sendRoomMsg(RunningContext.loginTelInfo.nickname,
                            Constants.HX_TYPE_NEARLY_SUCCESS,
                            mEMChatRoom.getId());
                }
            } else if (grabResult.value == 1) {
                // 抓取成功
                if (mEMChatRoom != null) {
                    HXChatUtil.sendRoomMsg(RunningContext.loginTelInfo.nickname,
                            Constants.HX_TYPE_SUCCESS,
                            mEMChatRoom.getId());
                }
                //showResultDialog(true);
                showGarbResultDialog(true, grabResult);

            }
            mIsStarted = false;
        } else if (MinaCmd.INTO_ROOM.equals(minaMsg.cmd)) { //进入房间
            JoinParam joinResult = mGson.fromJson(minaMsg.msg,
                    JoinParam.class);
            member_nickname = joinResult.nickname;
            //changeNum(1);
            changePeopleNum(joinResult.member_count);
            Log.i("===4", member_count + "");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String desc = BusinessUtil.getChatTypeDesc(
                            member_nickname,
                            String.valueOf(
                                    Constants.HX_TYPE_JOIN));
                    addDanmaku(desc);
                }
            });
            List<Players> players = joinResult.getPlayers();
            if (players != null && players.size() > 0) {
                galleryAdapter.setDatas(players);
            //    memberAdapter.setDatas(players);
             //   memberAdapter.notifyDataSetChanged();
            }
        } else if (MinaCmd.LEAVE_ROOM.equals(minaMsg.cmd)) {//离开房间
            JoinParam joinResult = mGson.fromJson(minaMsg.msg,
                    JoinParam.class);
            member_nickname = joinResult.nickname;
            Log.i("===3", member_count + "");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String desc = BusinessUtil.getChatTypeDesc(
                            member_nickname,
                            String.valueOf(
                                    Constants.HX_TYPE_QUIT));
                    addDanmaku(desc);
                }
            });
            //  changeNum(-1);
            changePeopleNum(joinResult.member_count);
            List<Players> players = joinResult.getPlayers();
            if (players != null && players.size() > 0) {
             galleryAdapter.setDatas(players);
              //  memberAdapter.setDatas(players);
               // memberAdapter.notifyDataSetChanged();
            }
        } else if (MinaCmd.TEXT_MESSAGE.equals(minaMsg.cmd)) {//文字聊天
            ChatResult chatResult = mGson.fromJson(minaMsg.msg, ChatResult.class);
            messages.add(chatResult);
            messageAdapter.setDatas(messages);
            messageAdapter.notifyDataSetChanged();
        } else if (MinaCmd.SYSTEM.equals(minaMsg.cmd)) {
            SystemResult systemResult = mGson.fromJson(minaMsg.msg, SystemResult.class);
            ChatResult chatResult = new ChatResult();
            chatResult.content = systemResult.content;
            chatResult.system = 1;
            messages.add(chatResult);
            messageAdapter.setDatas(messages);
            messageAdapter.notifyDataSetChanged();

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(UserInfo minaMsg) {
        Log.i("daaqqe", "onMessage: " + minaMsg);
        // mMoneyTv.setText(String.valueOf(RunningContext.loginTelInfo.money));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(TokenInvalidMsg msg) {
        finish();
    }


    /**
     * 设置开始按钮颜色
     */
    private void showStartState() {
        //空闲中
        if (mRoomState == 0) {
            if (noStream) {
                //   live_start_button_go_press
                tv_start_btn.setTextColor(getResources().getColor(R.color.app_common_color));
                ln_live_start.setBackgroundResource(R.mipmap.yz_live_start_game_icon);
            } else {
                tv_start_btn.setTextColor(getResources().getColor(R.color.app_login_title_color));
                ln_live_start.setBackgroundResource(R.mipmap.yz_live_start_game_icon);
            }
            //  mStartTv.setInnerColor(Color.WHITE);
        } else {//live_start_button_go_press
            tv_start_btn.setTextColor(getResources().getColor(R.color.app_common_color));
            ln_live_start.setBackgroundResource(R.mipmap.yz_live_start_game_icon);
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
                ? Constants.HX_TYPE_SUCCESS
                : Constants.HX_TYPE_NEARLY_SUCCESS;
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
//        mResultDialog.setChangeStream(new LiveGrabResultDialog.changeStreamListean(){
//            @Override
//            public void changestream() {
//                switchPlaySource(false);
//            }
//        });
        mResultDialog.show();
    }

    private void showGarbResultDialog(boolean isSuccess, GrabResult grabResult) {


        int musicId = isSuccess ? mMusicSucceed : mMusicFailed;
        playPoolMusic(musicId);
        int type = isSuccess
                ? Constants.HX_TYPE_SUCCESS
                : Constants.HX_TYPE_NEARLY_SUCCESS;
        String desc = BusinessUtil.getChatTypeDesc(
                RunningContext.loginTelInfo.nickname, String.valueOf(type));
        addDanmaku(desc);
        mGrabResultDialog = new GrabResultDialog(this, mRoomItem, grabResult, isSuccess);
        // mResultDialog = new LiveGrabResultDialog(this, mRoomItem, true);
        mGrabResultDialog.setRetryListener(
                new GrabResultDialog.OnRetryClickListener() {
                    @Override
                    public void onRetryClick() {
                        // 再次开始
                        doStart();
                        mGrabResultDialog.dismiss();
                    }
                });

        mGrabResultDialog.show();
        line_live.fullScroll(ScrollView.FOCUS_UP);
        //mHandle.sendEmptyMessage(2);
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
        if (mGrabResultDialog != null) {
            mGrabResultDialog.release();
        }
        if (danmakuView != null) {
            danmakuView.clear();
        }
        mOnGrabTouchListener.release();
        leaveRoom();

        CommonUtil.dismissDialog(mLiveDetailDialog);
        CommonUtil.dismissDialog(mRechargeListDialog);
        CommonUtil.dismissDialog(mResultDialog);
        CommonUtil.dismissDialog(mGrabResultDialog);
        EventBus.getDefault().unregister(this);
        stopRecord();
        MinaManager.getInstance().disconnected();
        //退出即构房间
        switchPlaySource(false);
        doLogout();

        super.onDestroy();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == RECORD_REQUEST_CODE) {
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
                    Constants.VIDEO_PATH + "/"
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

    /**
     * 开始录制
     *
     * @param dollLogId 游戏记录id
     */

    private void startRecord(String dollLogId) {
        mDollLogId = dollLogId;
        Intent captureIntent = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            captureIntent = mMediaProjectionManager
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

    private void joinRoom() {
        JoinParam joinParam = new JoinParam();
        joinParam.cmd = "into_room";
        joinParam.vmc_no = mRoomItem.machineSn;
        joinParam.member_count = member_count;
        joinParam.nickname = RunningContext.loginTelInfo.nickname;
        MinaManager.getInstance().sendMsg(new Gson().toJson(joinParam));

    }


    /**
     * 离开聊天室
     */
    private void leaveRoom() {
        JoinParam joinParam = new JoinParam();
        joinParam.cmd = "leave";
        joinParam.vmc_no = mRoomItem.machineSn;
        MinaManager.getInstance().sendMsg(new Gson().toJson(joinParam));
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
                    if (typeInt == Constants.HX_TYPE_JOIN) {
                        // 进入，人数+1
                        changeNum(1);
                    } else if (typeInt == Constants.HX_TYPE_QUIT) {
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
                    member_count = Integer.parseInt(num) + size;
                    Log.i("===5", member_count + "");
                }
            }
        });
    }

    private void changePeopleNum(final int size) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                mPersonsNumTv.setText(size + "");
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

    @Override
    protected void onResume() {
        super.onResume();
      // initSound();
        if (mSpConfig.getBoolean(Constants.SP_MUSIC_ON_1, true)) {
            iv_set_music_play.setSelected(true);
            isPlay = false;
            if (mBgmPlayer != null) {
                mBgmPlayer.start();
            }
        } else {
            iv_set_music_play.setSelected(false);
            isPlay = true;
            if (mBgmPlayer != null) {
                mBgmPlayer.pause();
            }
        }
        // 友盟统计
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 友盟统计
        MobclickAgent.onPause(this);
    }

    /**
     * @param useUltraSource 即构切换流
     */
    private void switchPlaySource(boolean useUltraSource) {
        // 停止推流
        for (ZegoStream zegoStream : mListStream) {
            zegoStream.stopPlayStream();
        }

        String config;
        if (useUltraSource) {
            // 切到zego服务器拉流
            config = ZegoConstants.Config.PREFER_PLAY_ULTRA_SOURCE + "=1";
        } else {
            //切回cdn拉流
            config = ZegoConstants.Config.PREFER_PLAY_ULTRA_SOURCE + "=0";
        }

        ZegoLiveRoom.setConfig(config);
        for (int i = 0; i < mListStream.size(); i++) {
            mListStream.get(i).playStream(0);
        }
    }

    private void doLogout() {

        // 回复从CDN拉流
        ZegoLiveRoom.setConfig(ZegoConstants.Config.PREFER_PLAY_ULTRA_SOURCE + "=0");

        for (ZegoStream zegoStream : mListStream) {
            zegoStream.stopPlayStream();
        }

        mZegoLiveRoom.stopPublishing();
        mZegoLiveRoom.logoutRoom();
    }


    private void getDirection(int direction) {
        switch (direction) {
            case R.id.iv_live_grab_bottom:
                // 后移
                if (uid == uids[0]) {
                    doLoosen(2);
                } else {
                    doLoosen(4);
                }
                break;
            case R.id.iv_live_grab_top:
                // 前移
                if (uid == uids[0]) {
                    doLoosen(1);
                } else {
                    doLoosen(3);
                }
                break;
            case R.id.iv_live_grab_left:
                // 左移
                if (uid == uids[0]) {
                    doLoosen(3);
                } else {
                    doLoosen(2);
                }
                break;
            case R.id.iv_live_grab_right:
                // 右移
                if (uid == uids[0]) {
                    doLoosen(4);
                } else {
                    doLoosen(1);
                }
                break;
        }
    }


    private void doLoosen(int direction) {
        // FIXME 松开后的操作
        // //playPoolMusic(mMusicMove);
        Log.i("doLoosen", "2");
        MoveParam moveParam = new MoveParam();
        moveParam.cmd = "stop";
        moveParam.vmc_no = mRoomItem.machineSn;
        moveParam.direction = direction;
        MinaManager.getInstance().sendMsg(new Gson().toJson(moveParam));
    }


    public void getAndroiodScreenProperty() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        //  int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
        // int screenHeight = (int) (height / density);// 屏幕高度(dp)
        int screenHeight = (int) width * 4 / 3;// 屏幕高度(dp)
        int playHeight = height - screenHeight;
        mLiveContainerRl.setLayoutParams((new RelativeLayout.LayoutParams(width, screenHeight)));
        mGrabContainer.setLayoutParams((new RelativeLayout.LayoutParams(width, playHeight)));

        Log.d("h_bl", "屏幕宽度（像素）：" + width);
        Log.d("h_bl", "屏幕高度（像素）：" + height);
        Log.d("h_bl", "屏幕密度（0.75 / 1.0 / 1.5）：" + density);
        Log.d("h_bl", "屏幕密度dpi（120 / 160 / 240）：" + densityDpi);
        // Log.d("h_bl", "屏幕宽度（dp）：" + screenWidth);
        Log.d("h_bl", "屏幕高度（dp）：" + screenHeight);
        Log.d("h_bl", "游戏控制屏幕高度（dp）：" + playHeight);
    }

    /**
     * 加载抓取数据
     *
     * @param page
     */
    private void loadGrabData(final int page) {

      /*  RoomLogRequest request = new RoomLogRequest(machineId, RunningContext.accessToken, page, 20);
        BusinessManager.getInstance().getRoomLog(request,
                new MyCallback<RoomLogBack>() {
                    @Override
                    public void onSuccess(RoomLogBack result,
                                          String message) {
                      *//*  Activity activity = context;
                        if (activity == null || activity.isFinishing()) {
                            return;
                        }*//*
                        if (result == null) {
                            return;
                        }
                        if (ListUtil.isEmpty(result.data)) {
                            lv_grab_records_list.setVisibility(View.GONE);
                            return;
                        } else {
                            lv_grab_records_list.setVisibility(View.VISIBLE);
                        }
                        mCurrentPage = page;
                        if (page == 1) {
                            mAdapter.refreshUi(result.data);
                        } else {
                            mAdapter.refreshUiByAdd(result.data);
                        }
                    }

                    @Override
                    public void onError(String errorCode, String message) {
                       *//* Activity activity = context;
                        if (activity != null && !activity.isFinishing()) {
                            Toast.makeText(activity, "没有更多数据",
                                    Toast.LENGTH_SHORT).show();
                        }*//*
                    }

                    @Override
                    public void onFinished() {
                        // mGrabLv.onRefreshComplete();
                    }
                });*/
    }



    private void loadLiveGrabData() {
        xutilsPost("",machineId);

       /* BusinessManager.getInstance().getLiveRoomLog(new RoomLogRequest2(machineId, RunningContext.accessToken), new MyCallback<RoomLogBack2>() {
            @Override
            public void onSuccess(RoomLogBack2 result, String message) {
                Log.i("===",message);
            }

            @Override
            public void onError(String errorCode, String message) {
                Log.i("===",message);
            }

            @Override
            public void onFinished() {

            }
        });*/

    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        Log.i("===", "");

        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
           // sendView.setVisibility(View.VISIBLE);
            live_bottom_view.setVisibility(View.GONE);
            //   topView.setVisibility(View.GONE);
//            Toast.makeText(MainActivity.getActivity(), "监听到软键盘弹起...", Toast.LENGTH_SHORT).show();
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
         //   sendView.setVisibility(View.GONE);
            live_bottom_view.setVisibility(View.VISIBLE);
            if (dialog != null){
                dialog.dismiss();
            }
            // topView.setVisibility(View.VISIBLE);
//            Toast.makeText(MainActivity.getActivity(), "监听到软件盘关闭...", Toast.LENGTH_SHORT).show();
        }
    }

    public String getReultForHttpPost(String name,String pwd) throws ClientProtocolException, IOException {
        //服务器  ：服务器项目  ：servlet名称
        String path=Constants.BASE_URL + "/doll/log/last";
        HttpPost httpPost=new HttpPost(path);
        List<NameValuePair>list=new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("machineId", name));
        list.add(new BasicNameValuePair("accessToken", pwd));
        list.add(new BasicNameValuePair("key", "8dd758066c594324962cc2de7ee7a306"));

        httpPost.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));//编者按：与HttpGet区别所在，这里是将参数用List传递

        String result="";

        HttpResponse response=new DefaultHttpClient().execute(httpPost);
        if(response.getStatusLine().getStatusCode()==200){
            HttpEntity entity=response.getEntity();
            result= EntityUtils.toString(entity, HTTP.UTF_8);
        }
        return result;
    }

    public String xutilsPost( String url,String sign ){

       url = Constants.BASE_URL + "/doll/log/last";
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("key", "8dd758066c594324962cc2de7ee7a306" );
        params.addQueryStringParameter("accessToken", RunningContext.accessToken);
        params.addQueryStringParameter("machineId", sign );

        // 只包含字符串参数时默认使用BodyParamsEntity，
        // 类似于UrlEncodedFormEntity（"application/x-www-form-urlencoded"）。
        //params.addBodyParameter("name", "value");

        // 加入文件参数后默认使用MultipartEntity（"multipart/form-data"），
        // 如需"multipart/related"，xUtils中提供的MultipartEntity支持设置subType为"related"。
        // 使用params.setBodyEntity(httpEntity)可设置更多类型的HttpEntity（如：
        // MultipartEntity,BodyParamsEntity,FileUploadEntity,InputStreamUploadEntity,StringEntity）。
        // 例如发送json参数：params.setBodyEntity(new StringEntity(jsonStr,charset));

        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(1000 * 10); //设置超时时间   10s
        http.send(HttpRequest.HttpMethod.POST ,
                url ,
                params,
                new RequestCallBack<String>() {

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        result1 = responseInfo.result.toString() ;
                        if (result1 != null){
                            RoomLogBack2 roomLogBack2 = new Gson().fromJson(result1, RoomLogBack2.class);
                            List<RoomLogInfo> data1 = roomLogBack2.getData();
                            if (data1 != null && data1.size()>0){
                                mAdapter.refreshUi(data1);
                                findViewById(R.id.tv_no_record_tips).setVisibility(View.GONE);
                            }else {
                                findViewById(R.id.tv_no_record_tips).setVisibility(View.VISIBLE);
                            }
                        }else {
                            findViewById(R.id.tv_no_record_tips).setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                    Log.i("===","");
                    }
                });

        return result1 ;
    }


    /**
     * 弹出评论对话框
     */
    private  Dialog showInputComment(Activity activity, final CommentFun.CommentDialogListener listener) {
         dialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
         //final Dialog
        dialog.setContentView(R.layout.view_input_comment);
        dialog.findViewById(R.id.input_comment_dialog_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (listener != null) {
                    listener.onDismiss();
                }
            }
        });
        final EditText input = (EditText) dialog.findViewById(R.id.input_comment);
        final TextView btn = (TextView) dialog.findViewById(R.id.btn_publish_comment);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickPublish(dialog, input, btn);
                }
            }
        });
        dialog.setCancelable(true);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (listener != null) {
                    int[] coord = new int[2];
                    dialog.findViewById(R.id.input_comment_container).getLocationOnScreen(coord);
                    // 传入 输入框距离屏幕顶部（不包括状态栏）的长度
                    listener.onShow(coord);
                }
            }
        }, 300);
        return dialog;
    }

    public  void inputComment(final Activity activity, final ListView listView,
                                    final View btnComment) {

        // 获取评论的位置,不要在CommentDialogListener.onShow()中获取，onShow在输入法弹出后才调用，
        // 此时btnComment所属的父布局可能已经被ListView回收
        final int[] coord = new int[2];
        if (listView != null) {
            btnComment.getLocationOnScreen(coord);
        }

        showInputComment(activity, new CommentFun.CommentDialogListener() {
            @Override
            public void onClickPublish(final Dialog dialog, EditText input, final TextView btn) {
                final String content = input.getText().toString();
                if (content.trim().equals("")) {
                    Toast.makeText(activity, "发送内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                btn.setClickable(false);

                sendChat(content);
                dialog.dismiss();


            }

            @Override
            public void onShow(int[] inputViewCoordinatesInScreen) {
                if (listView != null) {
                    // 点击某条评论则这条评论刚好在输入框上面，点击评论按钮则输入框刚好挡住按钮
                        int span =btnComment.getHeight();
                        listView.smoothScrollBy(coord[1] + span - inputViewCoordinatesInScreen[1], 1000);
                }
            }

            @Override
            public void onDismiss() {

            }
        });

    }


    public class GalleryAdapter extends
            RecyclerView.Adapter<GalleryAdapter.ViewHolder>
    {
        private LayoutInflater mInflater;
        private List<Players> mDatas = new ArrayList<>();

        public GalleryAdapter(Context context, List<Players> datats)
        {
            mInflater = LayoutInflater.from(context);
            mDatas = datats;
        }
        public GalleryAdapter(Context context)
        {
            mInflater = LayoutInflater.from(context);

        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            public ViewHolder(View arg0)
            {
                super(arg0);
            }

            ImageView mImg;
            TextView mTxt;
        }

        @Override
        public int getItemCount()
        {
            return mDatas.size();
        }

        /**
         * 创建ViewHolder
         */
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
        {
            View view = mInflater.inflate(R.layout.item_menber,
                    viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(view);

            viewHolder.mImg = (ImageView) view
                    .findViewById(R.id.avatar);
            return viewHolder;
        }

        /**
         * 设置值
         */
        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i)
        {
           // viewHolder.mImg.setImageResource(mDatas.get(i));
            Players players = mDatas.get(i);
            if (!TextUtils.isEmpty(players.avatar)) {
                // Glide.with(ctx).load(member.img).placeholImageViewder(R.drawable.default_head).into(viewHolder.avatar);
                ImgLoaderUtil.displayImage(players.avatar,  viewHolder.mImg);
            }else {
                viewHolder.mImg.setImageResource(R.mipmap.pic_zww_default);
            }
        }

        public void setDatas(List<Players> datas) {
            this.mDatas = datas;
            notifyDataSetChanged();
        }

    }

}
