package com.ydd.pockettoycatcherrwz.network.http;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.entity.AccessToken;
import com.ydd.pockettoycatcherrwz.entity.AddressInfo;
import com.ydd.pockettoycatcherrwz.entity.AddressListBack;
import com.ydd.pockettoycatcherrwz.entity.AppUpdataMessage;
import com.ydd.pockettoycatcherrwz.entity.BannerInfo;
import com.ydd.pockettoycatcherrwz.entity.DollCallback;
import com.ydd.pockettoycatcherrwz.entity.EnterRoomInfo;
import com.ydd.pockettoycatcherrwz.entity.GetPointsEverDay;
import com.ydd.pockettoycatcherrwz.entity.GrabDetail;
import com.ydd.pockettoycatcherrwz.entity.GrabRecordsInfo;
import com.ydd.pockettoycatcherrwz.entity.HomeInfo;
import com.ydd.pockettoycatcherrwz.entity.LoginTelInfo;
import com.ydd.pockettoycatcherrwz.entity.MainPage_Messages;
import com.ydd.pockettoycatcherrwz.entity.OrderDetail;
import com.ydd.pockettoycatcherrwz.entity.OrderListBack;
import com.ydd.pockettoycatcherrwz.entity.RankCallback;
import com.ydd.pockettoycatcherrwz.entity.RechargBean;
import com.ydd.pockettoycatcherrwz.entity.RechargeItemInfo;
import com.ydd.pockettoycatcherrwz.entity.RechargeLogInfo;
import com.ydd.pockettoycatcherrwz.entity.RoomItem;
import com.ydd.pockettoycatcherrwz.entity.RoomLogBack;
import com.ydd.pockettoycatcherrwz.entity.RoomLogBack2;
import com.ydd.pockettoycatcherrwz.entity.RoomStatusInfo;
import com.ydd.pockettoycatcherrwz.entity.SignListInfo;
import com.ydd.pockettoycatcherrwz.entity.SignSuccessInfo;
import com.ydd.pockettoycatcherrwz.entity.TokenInvalidMsg;
import com.ydd.pockettoycatcherrwz.entity.UserInfo;
import com.ydd.pockettoycatcherrwz.entity.User_mian_Messages;
import com.ydd.pockettoycatcherrwz.entity.ViewPagerIndicateMessage;
import com.ydd.pockettoycatcherrwz.entity.Vip_user_WeekorMonth;
import com.ydd.pockettoycatcherrwz.entity.WXentity.AuthorResult;
import com.ydd.pockettoycatcherrwz.entity.WXentity.CheckResult;
import com.ydd.pockettoycatcherrwz.entity.WXentity.WXuserInfo;
import com.ydd.pockettoycatcherrwz.entity.WeekOrMouthBean;
import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.AppealRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.BannerRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.CetPointsDayMessages;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.ChargeListRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.ChargeLogRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.ChargeRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.DeleteAddressRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.DiamondExChargeRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.DollListRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.EnterMachineRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.EnterRoomRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.GetAddressListRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.GetCodeRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.GetDefaultAddress;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.GetGrabDetailRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.GetUserInfoRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.GetUserVipKinds;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.GetViewPagerIndicateMessage;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.GetupDataMessage;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.GetweekorMonthMessage;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.GrabListRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.HomeRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.InviteCodeRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.InvitePointsRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.LoginTelRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.LoginWechatRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.ManitoMonthRankRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.ManitoRankRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.OrderDetailRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.OrderListRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.RefreshTokenRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.RewardsPoints;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.RichMonthRankRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.RichRankRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.RoomLogRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.RoomLogRequest2;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.RoomStatusRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.SaveAddressRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.SendOrderRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.SetDefaultAddressRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.Share_accice;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.SignListRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.SignRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.UploadImageRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.UploadVideoRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.UserEditRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.getMessagesforUser;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.wechatimpl.DelteMessages;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.wechatimpl.GetMessages;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.wechatimpl.WeChatAccessTokenRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.wechatimpl.WeChatCheckAccessRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.wechatimpl.WechatGetUserInfoRequest;
import com.ydd.pockettoycatcherrwz.ui.login.LoginActivity;
import com.ydd.pockettoycatcherrwz.util.Constants;
import com.ydd.pockettoycatcherrwz.util.LogUtil;
import com.ydd.pockettoycatcherrwz.util.NetUtil;
import com.ydd.pockettoycatcherrwz.util.SharedPrefConfig;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.util.EntityUtils;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.List;

/**
 * 业务请求类
 */
public class BusinessManager implements ResultCode {
    /**
     * 超时时间
     */
    private static final int TIMEOUT = 18000;

    public static final String KEY_CODE = "errCode";

    public static final String KEY_MSG = "errMsg";

    public static final String KEY_DATA = "data";

    private static BusinessManager instance;

    private Context mContext;

    private HttpUtils mHttpUtils;

    private BusinessManager() {
        // 超时时间设置为30秒
        this.mHttpUtils = new HttpUtils(TIMEOUT);
        // 关闭缓存功能
        mHttpUtils.configCurrentHttpCacheExpiry(0 * 1000);
        mHttpUtils.configDefaultHttpCacheExpiry(0);
    }

    /**
     * 获取业务请求管理对象
     */
    public static BusinessManager getInstance() {
        if (instance == null) {
            synchronized (BusinessManager.class) {
                if (instance == null) {
                    instance = new BusinessManager();
                }
            }
        }
        return instance;
    }

    /**
     * context初始化，用applicationcontext比较好
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
    }

    /**
     * 执行具体的网络请求及响应解析
     *
     * @param request
     * @param callback
     * @return
     */
    private <DataType> HttpHandler sendRequest(final AbstractRequest request,
                                               final MyCallback<DataType> callback) {
        // 检查网络是否可用
        if (!NetUtil.isNetworkAvailable(mContext)) {
            requestErrorCallback(CODE_ERROR_NET_INVALID,
                    mContext.getString(R.string.network_invalide), callback);
            return null;
        }
        request.addParam("key", "8dd758066c594324962cc2de7ee7a306");
        //request.addParam("key", "6e36c12e357543c786d6322057017c38");
        // 初始化请求参数
        request.initRequestParams();
        RequestParams params = request.getAddedParams();
        if (request.getHeaders() != null) {
            params.addHeaders(request.getHeaders());
        }
        LogUtil.log("Request",
                "request url = " + getRequestUrl(request.getUrl()), "i");
        trackRequestParams(params);
        return mHttpUtils.configTimeout(TIMEOUT).send(
                request.getRequestMethod(), getRequestUrl(request.getUrl()),
                params, new RequestCallBack<String>() {
                    @Override
                    public void onFailure(HttpException error, String arg1) {
                        if (error != null) {
                            error.printStackTrace();
                        }
                        // 封装网络层面的错误，向上层提供较友好的消息提示
                        String errorCode = CODE_ERROR_DEFAULT;
                        String errorMessage = mContext
                                .getString(R.string.network_error_common);
                        if (error != null && (error
                                .getCause() instanceof ConnectException
                                || error.getCause() instanceof SocketException
                                || error.getCause() instanceof NoHttpResponseException)) {
                            errorCode = CODE_ERROR_NET_INVALID;
                            errorMessage = mContext
                                    .getString(R.string.network_error);
                        } else if (error != null && (error
                                .getCause() instanceof SocketTimeoutException
                                || error.getCause() instanceof ConnectTimeoutException)) {
                            errorCode = CODE_ERROR_NET_INVALID;
                            errorMessage = mContext
                                    .getString(R.string.network_timeout);
                        }
                        requestErrorCallback(errorCode, errorMessage, callback);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        if (arg0 == null || arg0.result == null) {
                            String errorMessage = mContext
                                    .getString(R.string.unknown_error);
                            requestErrorCallback(ResultCode.CODE_ERROR_FORMAT,
                                    errorMessage, callback);
                            return;
                        }
                        LogUtil.log("response", "response:" + arg0.result, "i");
                        try {
                            JSONObject response = new JSONObject(arg0.result);
                            // 如果需要自己解析（不走通用解析逻辑）
                            if (request.parseResultMyself()) {
                                request.parseResult(response, callback);
                                return;
                            }
                            requestSuccessCallback(request, callback, response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            String errorMessage = mContext.getString(
                                    R.string.network_error_format_error);
                            requestErrorCallback(ResultCode.CODE_ERROR_FORMAT,
                                    errorMessage, callback);
                        }
                    }

                    @Override
                    public void onLoading(long total, long current,
                                          boolean isUploading) {
                        super.onLoading(total, current, isUploading);
                    }

                    @Override
                    public void onCancelled() {
                        super.onCancelled();
                        if (callback != null) {
                            callback.onCancel();
                        }
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        if (callback != null) {
                            callback.onStart();
                        }
                    }

                });
    }

    /**
     * 这边需要对请求url做统一处理，如加入authtoken
     *
     * @param url
     * @return
     */
    private String getRequestUrl(String url) {
        // if (TextUtils.isEmpty(Config.authtoken))
        // {
        // return url;
        // }
        // return url + "?authtoken=" + Config.authtoken;
        return url;
    }

    /**
     * 打印请求参数
     *
     * @param params
     */
    private void trackRequestParams(RequestParams params) {
        if (params != null && params.getQueryStringParams() != null) {
            for (NameValuePair np : params.getQueryStringParams()) {
                LogUtil.log("Request", String.format("param [%s]=[%s]",
                        np.getName(), np.getValue()), "i");
            }
        }
        if (params != null) {
            HttpEntity en = params.getEntity();
            if (en != null) {
                try {
                    String p = EntityUtils.toString(en);
                    LogUtil.log("Request", "body params = " + p, "i");
                } catch (Exception e) {
                    LogUtil.log("Request", "print body params error", "i");
                }

            }
        }

    }

    /**
     * 请求成功后的数据解析，此方法仅仅是网络请求成功后回调，网络请求成功并不代表业务接口调用成功
     *
     * @param request
     * @param callback
     * @param response
     */
    @SuppressWarnings("unchecked")
    private <DataType> void requestSuccessCallback(
            final AbstractRequest request, final MyCallback<DataType> callback,
            JSONObject response) {
        String resultCode = response.optString(KEY_CODE);
        // 业务处理成功
        // 接口返回数据格式比对
        if (CODE_SUCCESS.equals(resultCode)) {
            String resultData = response.optString(KEY_DATA);
            String message = response.optString(KEY_MSG);
            Gson resultGson = new Gson();
            try {
                if (request.getResultType() != null
                        && request.getResultType() != Void.class) {
                    // added by czhang， String解析单独拎出来
                    // resultGson.fromJson()解析的字符串中有空格时解析抛出异常，特修改 20151217 商好值
                    if (request.getResultType() == String.class) {
                        callback.onSuccess((DataType) resultData, message);
                        callback.onFinished();
                        return;
                    }
                    Type resultType = request.getResultType();

                    DataType resultObj = (DataType) resultGson
                            .fromJson(resultData, request.getResultType());
                    if (resultObj == null)// result必须非空
                    {
                        String errorMessage = mContext
                                .getString(R.string.unknown_error);
                        requestErrorCallback(ResultCode.CODE_ERROR_FORMAT,
                                errorMessage, callback);
                    } else {
                        callback.onSuccess(resultObj, message);
                    }
                } else {
                    callback.onSuccess(null, message);
                }
            } catch (Exception e) {
                e.printStackTrace();
                // 数据解析失败
                String errorMessage = mContext
                        .getString(R.string.unknown_error);
                requestErrorCallback(ResultCode.CODE_ERROR_FORMAT, errorMessage,
                        callback);
            }
            callback.onFinished();
        }
        // 业务处理失败，resultCode为1
        else {
            String message = response.optString(KEY_MSG);
            String errorCode = response.optString(KEY_CODE);
//			if (CODE_TOKEN_INVALID.equals(errorCode)
//					&& RunningContext.loginTelInfo != null
//					&& RunningContext.loginTelInfo.accessToken != null
//					&& !TextUtils.isEmpty(
//							RunningContext.loginTelInfo.accessToken.refreshToken)) {
//				refreshToken(
//						new RefreshTokenRequest(
//								RunningContext.loginTelInfo.accessToken.refreshToken),
//						new MyCallback<AccessToken>() {
//							@Override
//							public void onSuccess(AccessToken result,
//									String message) {
//
//							}
//
//							@Override
//							public void onError(String errorCode,
//									String message) {
//							}
//
//							@Override
//							public void onFinished() {
//							}
//						});
//			}
            if (CODE_TOKEN_INVALID.equals(errorCode)) {
                EventBus.getDefault().post(new TokenInvalidMsg());

                RunningContext.loginTelInfo = null;
                Intent intent = new Intent(mContext,
                        LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                SharedPrefConfig spConfig = new SharedPrefConfig();
                spConfig.open(mContext, Constants.SP_FILE_CONFIG);
                spConfig.remove("last_login");
                mContext.startActivity(intent);
            }
            callback.onError(errorCode, message);
            callback.onFinished();
        }
    }

    private <DataType> void requestErrorCallback(String errorCode,
                                                 String message, final MyCallback<DataType> callback) {
        callback.onError(errorCode, message);
        callback.onFinished();
    }

    // ********************************************业务请求***************************************

    /**
     * 手机号登录
     */
    public void loginTel(LoginTelRequest request,
                         MyCallback<LoginTelInfo> callback) {
        sendRequest(request, callback);
    }

    /**
     * 发送短信，获取验证码
     */
    public void getCode(GetCodeRequest request, MyCallback<Void> callback) {
        sendRequest(request, callback);
    }

    // －－－－－－－－－－－－－首页－－－－－－－－－－－－－

    /**
     * 获取首页热点活动
     */
    public void getBanner(BannerRequest request,
                          MyCallback<List<BannerInfo>> callback) {
        sendRequest(request, callback);
    }

    /**
     * 获取首页信息
     */
    public void getHomeInfo(HomeRequest request,
                            MyCallback<HomeInfo> callback) {
        sendRequest(request, callback);
    }

    /**
     * 进入房间
     */
    public void enterRoom(EnterRoomRequest request,
                          MyCallback<EnterRoomInfo> callback) {
        sendRequest(request, callback);
    }

    /**
     * 签到
     */
    public void sign(SignRequest request,
                     MyCallback<SignSuccessInfo> callback) {
        sendRequest(request, callback);
    }

    /**
     * 提交风险任务
     */
    public void share_submit(Share_accice request,
                             MyCallback<Void> callback) {
        sendRequest(request, callback);
    }


    /**
     * 签到列表
     */
    public void signList(SignListRequest request,
                         MyCallback<SignListInfo> callback) {
        sendRequest(request, callback);
    }

    /**
     * 完成任务领取奖励
     */
    public void reward(RewardsPoints request,
                       MyCallback<Void> callback) {
        sendRequest(request, callback);
    }


    /**
     * 充值列表
     */
    public void chargeList(ChargeListRequest request,
                           MyCallback<List<RechargeItemInfo>> callback) {
        sendRequest(request, callback);
    }

    /**
     * 充值列表2
     */
    public void chargeList_2(ChargeListRequest request,
                             MyCallback<RechargBean> callback) {
        sendRequest(request, callback);
    }

    /**
     * 获取周卡或月卡信息
     */
    public void getCardWeekorMouth(GetweekorMonthMessage request,
                                   MyCallback<WeekOrMouthBean> callback) {
        sendRequest(request, callback);
    }


    /**
     * 充值记录列表
     */
    public void chargeLog(ChargeLogRequest request,
                          MyCallback<RechargeLogInfo> callback) {
        sendRequest(request, callback);
    }

    /**
     * 刷新token
     */
    public void refreshToken(RefreshTokenRequest request,
                             MyCallback<AccessToken> callback) {
        sendRequest(request, callback);
    }

    /**
     * 获取app更新信息
     */
    public void GetAppUpadataMessgae(GetupDataMessage request,
                                     MyCallback<AppUpdataMessage> callback) {
        sendRequest(request, callback);
    }


    /**
     * 上传头像
     */
    public void uploadImage(UploadImageRequest request,
                            MyCallback<String> callback) {
        sendRequest(request, callback);
    }

    /**
     * 申诉
     */
    public void appeal(AppealRequest request, MyCallback<Void> callback) {
        sendRequest(request, callback);
    }

    /**
     * 获取抓取详情页
     */
    public void getGrabDetail(GetGrabDetailRequest request,
                              MyCallback<GrabDetail> callback) {
        sendRequest(request, callback);
    }

    /**
     * 邀请码获积分
     */
    public void inviteCode(InviteCodeRequest request,
                           MyCallback<Void> callback) {
        sendRequest(request, callback);
    }

    // －－－－－－－－－－－－－订单－－－－－－－－－－－－－

    /**
     * 订单列表
     */
    public void getOrderList(OrderListRequest request,
                             MyCallback<OrderListBack> callback) {
        sendRequest(request, callback);
    }

    /**
     * 订单详情
     */
    public void getOrderDetail(OrderDetailRequest request,
                               MyCallback<OrderDetail> callback) {
        sendRequest(request, callback);
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo(GetUserInfoRequest request,
                            MyCallback<UserInfo> callback) {
        sendRequest(request, callback);
    }

    /**
     * 修改用户信息
     */
    public void userEdit(UserEditRequest request, MyCallback<Void> callback) {
        sendRequest(request, callback);
    }

    /**
     * 设置默认地址
     */
    public void setDefaultAddress(SetDefaultAddressRequest request,
                                  MyCallback<Void> callback) {
        sendRequest(request, callback);
    }

    /**
     * 删除地址
     */
    public void deleteAddress(DeleteAddressRequest request,
                              MyCallback<Void> callback) {
        sendRequest(request, callback);
    }

    /**
     * 新增或者修改地址
     */
    public void saveAddress(SaveAddressRequest request,
                            MyCallback<Void> callback) {
        sendRequest(request, callback);
    }

    /**
     * 获取抓取记录列表
     */
    public void getGrabList(GrabListRequest request,
                            MyCallback<GrabRecordsInfo> callback) {
        sendRequest(request, callback);
    }

    /**
     * 上传视频
     */
    public void uploadVideo(UploadVideoRequest request,
                            MyCallback<String> callback) {
        sendRequest(request, callback);
    }

    /**
     * 娃娃袋
     */
    public void getDollList(DollListRequest request,
                            MyCallback<DollCallback> callback) {
        sendRequest(request, callback);
    }



    /**
     * 获取地址列表
     */

    public void getAddressList(GetAddressListRequest request,
                               MyCallback<AddressListBack> callback) {
        sendRequest(request, callback);
    }

    /**
     * 获取默认地址
     *
     * @param request
     * @param callback
     */
    public void getDefaultAddress(GetDefaultAddress request,
                                  MyCallback<AddressInfo> callback) {
        sendRequest(request, callback);
    }

    /**
     * 下单
     */
    public void sendOrder(SendOrderRequest request, MyCallback<Void> callback) {
        sendRequest(request, callback);
    }

    /**
     * 获取微信code 微信登录第二步
     */
    public void getWXcode(WeChatAccessTokenRequest request,
                          MyCallback<AuthorResult> callback) {
        sendRequest(request, callback);
    }

    /**
     * @param request  获取首页 条目
     * @param callback
     */
    public void getViewPagerIndicateMessage(GetViewPagerIndicateMessage request,
                                            MyCallback<List<ViewPagerIndicateMessage>> callback) {
        sendRequest(request, callback);
    }

    /**
     * @param request  1.5版本获得用户信息
     * @param callback
     */
    public void getUserMessageV15(getMessagesforUser request,
                                  MyCallback<User_mian_Messages> callback) {
        sendRequest(request, callback);
    }

    /**
     * @param request  会员中心种类信息
     * @param callback
     */
    public void getUserVipKings(GetUserVipKinds request,
                                MyCallback<Vip_user_WeekorMonth> callback) {
        sendRequest(request, callback);
    }


    /**
     * @param request  获取每日任务
     * @param callback
     */
    public void getPointsBydaystation(CetPointsDayMessages request, MyCallback<List<GetPointsEverDay>> callback) {
        sendRequest(request, callback);
    }


    /**
     * 拉取微信用户信息
     */
    public void getWXUserInfo(WechatGetUserInfoRequest request,
                              MyCallback<WXuserInfo> callback) {
        sendRequest(request, callback);
    }

    /**
     * 微信检验授权凭证（access_token）是否有效
     */

    public void checkWXAccess(WeChatCheckAccessRequest request,
                              MyCallback<CheckResult> callback) {
        sendRequest(request, callback);
    }

    /**
     * 微信登录
     */
    public void loginWX(LoginWechatRequest request,
                        MyCallback<LoginTelInfo> callback) {
        sendRequest(request, callback);
    }

    public void getRoomStatus(RoomStatusRequest request,
                              MyCallback<List<RoomStatusInfo>> callback) {
        sendRequest(request, callback);
    }

    public void getRoomLog(RoomLogRequest request,
                           MyCallback<RoomLogBack> callback) {
        sendRequest(request, callback);
    }

    public void charge(ChargeRequest request, MyCallback<String> callback) {
        sendRequest(request, callback);
    }

    /**
     * 邀请描述
     */
    public void inviteDesc(InvitePointsRequest request,
                           MyCallback<String> callback) {
        sendRequest(request, callback);
    }


    /**
     * 土豪总榜
     */
    public void getRicherList(RichRankRequest request,
                              MyCallback<RankCallback> callback) {
        sendRequest(request, callback);
    }

    /**
     * 土豪月榜
     */
    public void getMonthRicherList(RichMonthRankRequest request,
                                   MyCallback<RankCallback> callback) {
        sendRequest(request, callback);
    }


    /**
     * 大神总榜
     */
    public void getManitoList(ManitoRankRequest request,
                              MyCallback<RankCallback> callback) {
        sendRequest(request, callback);
    }

    /**
     * 大神月榜
     */
    public void getManitoMonthList(ManitoMonthRankRequest request,
                                   MyCallback<RankCallback> callback) {
        sendRequest(request, callback);
    }

    /**
     * @param request 获取首页消息目录
     * @param callback
     */
    public void getMessages(GetMessages request,
                            MyCallback<List<MainPage_Messages>> callback) {
        sendRequest(request, callback);
    }
    public void delteMessages(DelteMessages request,
                                MyCallback callback) {
        sendRequest(request, callback);
    }


    /*
    * 兑换钻石
    * */
    public void exChangeDiamond(DiamondExChargeRequest request,
                              MyCallback callback) {
        sendRequest(request, callback);
    }

    /*
   * 兑换钻石
   * */
    public void enterRoomByMachine(EnterMachineRequest request,
                                MyCallback<RoomItem> callback) {
        sendRequest(request, callback);
    }

    public void getLiveRoomLog(RoomLogRequest2 request,
                           MyCallback<RoomLogBack2> callback) {
        sendRequest(request, callback);
    }

}
