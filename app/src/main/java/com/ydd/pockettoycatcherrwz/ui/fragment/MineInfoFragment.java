package com.ydd.pockettoycatcherrwz.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.UserManager;
import com.ydd.pockettoycatcherrwz.entity.UserInfo;
import com.ydd.pockettoycatcherrwz.entity.User_mian_Messages;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.getMessagesforUser;
import com.ydd.pockettoycatcherrwz.ui.home.NewInviteActivity;
import com.ydd.pockettoycatcherrwz.ui.home.RechargeNewActivity;
import com.ydd.pockettoycatcherrwz.ui.home.Recharge_newActivity;
import com.ydd.pockettoycatcherrwz.ui.home.ShopActivity;
import com.ydd.pockettoycatcherrwz.ui.home.VipUserActivity;
import com.ydd.pockettoycatcherrwz.ui.home.WaWaBoxActivity;
import com.ydd.pockettoycatcherrwz.ui.login.LoginActivity;
import com.ydd.pockettoycatcherrwz.ui.login.SplashActivity;
import com.ydd.pockettoycatcherrwz.ui.order.OrderListActivity;
import com.ydd.pockettoycatcherrwz.ui.personal.AddressManageActivity;
import com.ydd.pockettoycatcherrwz.ui.personal.PersonalInfoActivity;
import com.ydd.pockettoycatcherrwz.ui.personal.RechargeRecordListActivity;
import com.ydd.pockettoycatcherrwz.ui.personal.SettingsActivity;
import com.ydd.pockettoycatcherrwz.ui.record.GrabRecordsActivity;
import com.ydd.pockettoycatcherrwz.util.ImgLoaderUtil;
import com.ydd.pockettoycatcherrwz.widget.CommonTitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MineInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MineInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MineInfoFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private CommonTitleBar mTitleBar;
    private ImageView iv_dlg_personal_avatar;
    private TextView tv_dlg_personal_name;
    private LinearLayout linearLayout;
    private TextView user_main_diamonds;
    private LinearLayout user_main_diamonds_linear;
    private TextView user_main_points_shop;
    private LinearLayout user_main_points_shop_linear;
    private TextView user_main_takes;
    private LinearLayout user_main_takes_linear;
    private TextView user_main_wawadai;
    private TextView user_mine_takes;//我的碎片
    private LinearLayout user_main_wawadai_linear;
    private LinearLayout user_main_adreess;
    private LinearLayout user_main_orders;
    private LinearLayout user_main_repay;
    private LinearLayout user_main_settings;
    private LinearLayout user_main_vip;
    private ImageView user_main_pic_vip;
    private LinearLayout user_mian_login_now;
    private TextView user_mian_vip_station;
    private PullToRefreshScrollView myscrollview_usermian;
    private LinearLayout user_mian_arrow;
    private LinearLayout scroll_view_addview;
    private LinearLayout user_mian_rechargr_message;
    private LinearLayout user_mine_invite;
    private LinearLayout user_mine_catched;
    private View mView;

    public MineInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MineInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MineInfoFragment newInstance(String param1, String param2) {
        MineInfoFragment fragment = new MineInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        EventBus.getDefault().register(this);
        return inflater.inflate(R.layout.fragment_mine_info, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

     /*   mTitleBar = (CommonTitleBar) view.findViewById(R.id.common_title_bar);
        mTitleBar.setTitle("我的");*/

        scroll_view_addview = (LinearLayout) view.findViewById(R.id.scroll_view_addview);
        myscrollview_usermian = (PullToRefreshScrollView) view.findViewById(R.id.myscrollview_usermian);
        user_mian_arrow = (LinearLayout) view.findViewById(R.id.user_mian_arrow);
        //用户是否是会员
        user_main_pic_vip = (ImageView)  view.findViewById(R.id.user_main_pic_vip);
        //头像
        iv_dlg_personal_avatar = (ImageView) view.findViewById(R.id.iv_dlg_personal_avatar);
        //昵称
        tv_dlg_personal_name = (TextView) view.findViewById(R.id.tv_dlg_personal_name);
        //个人信息
        linearLayout = (LinearLayout) view.findViewById(R.id.user_main_persson);
        //立即登陆
        user_mian_login_now = (LinearLayout)  view.findViewById(R.id.user_mian_login_now);
        if (RunningContext.loginTelInfo != null) {
            initDatas();
        }
    }


    private void initDatas() {
        getMessagesforUser mgetMessagesforUser = new getMessagesforUser();
        BusinessManager.getInstance().getUserMessageV15(mgetMessagesforUser,
                new MyCallback<User_mian_Messages>() {
                    @Override
                    public void onSuccess(User_mian_Messages result, String message) {
                        Log.i("dqrrrrrr", "onSuccess: " + result);
                        initviews(result);
                        UserManager.getInstance().refresh();
                    }

                    @Override
                    public void onError(String errorCode, String message) {
                    }

                    @Override
                    public void onFinished() {
                    }
                });

    }


    private void initviews(User_mian_Messages result) {
        //动态添加的linearlayout

        mView = LayoutInflater.from(getActivity()).inflate(R.layout.user_mine_scrollview_layout, null);

        myscrollview_usermian.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        //上拉监听函数

        scroll_view_addview.removeAllViews();
        scroll_view_addview.addView(mView);

        //箭头
        //钻石
        user_main_diamonds = (TextView) mView.findViewById(R.id.user_main_Diamonds);
        user_mine_takes = (TextView) mView.findViewById(R.id.user_mine_takes);
        user_main_diamonds_linear = (LinearLayout) mView.findViewById(R.id.user_main_Diamonds_linear);
        //积分商城
        user_main_points_shop = (TextView) mView.findViewById(R.id.user_main_points_shop);
        user_main_points_shop_linear = (LinearLayout) mView.findViewById(R.id.user_main_points_shop_linear);




        //抓取记录
        user_main_takes = (TextView) mView.findViewById(R.id.user_main_takes);
        user_mine_invite = (LinearLayout) mView.findViewById(R.id.user_mine_invite);
        user_mine_catched = (LinearLayout) mView.findViewById(R.id.user_mine_catched);
        user_main_takes_linear = (LinearLayout) mView.findViewById(R.id.user_main_takes_linear);
        //娃娃带
        user_main_wawadai = (TextView) mView.findViewById(R.id.user_main_wawadai);
        user_main_wawadai_linear = (LinearLayout) mView.findViewById(R.id.user_main_wawadai_linear);
        //地址管理
        user_main_adreess = (LinearLayout) mView.findViewById(R.id.user_main_adreess);
        //订单中心
        user_main_orders = (LinearLayout) mView.findViewById(R.id.user_main_orders);
        //兑换中心
        user_main_repay = (LinearLayout) mView.findViewById(R.id.user_main_repay);
        //设置
        user_main_settings = (LinearLayout) mView.findViewById(R.id.user_main_settings);
        //会员中心
        user_main_vip = (LinearLayout) mView.findViewById(R.id.user_main_vip);
        //会员状态
        user_mian_vip_station = (TextView) mView.findViewById(R.id.user_mian_vip_station);
        //充值记录
        user_mian_rechargr_message = (LinearLayout)  mView.findViewById(R.id.user_mian_rechargr_message);

        user_mine_takes.setText(result.fragmentCounts +"");
        if(RunningContext.loginTelInfo != null){
            myscrollview_usermian.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
                @Override
                public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                    //执行刷新函数
                    if (RunningContext.loginTelInfo != null) {
                        initDatas();
                    }
                }
            });
            user_main_takes.setText(result.grabCounts + "");
            user_main_wawadai.setText(result.dollCounts + "");
            //用户不是会员
            if (result.hasMember == 0) {
                user_main_vip.setVisibility(View.GONE);
            } else {
                user_main_pic_vip.setVisibility(View.VISIBLE);
                String sText = result.erpireDate + "<font color='#00c26f'>到期</font>";
                user_mian_vip_station.setText(Html.fromHtml(sText));
            }
            addListener();
        }else {
            user_main_vip.setVisibility(View.GONE);
            user_mian_login_now.setVisibility(View.VISIBLE);
            user_mian_login_now.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(),SplashActivity.class));
                    //finish();
                }
            });
        }
    }

    private void addListener() {
        linearLayout.setOnClickListener(this);
        user_main_diamonds_linear.setOnClickListener(this);
        user_main_points_shop_linear.setOnClickListener(this);
        user_main_takes_linear.setOnClickListener(this);
        user_main_wawadai_linear.setOnClickListener(this);
        user_main_adreess.setOnClickListener(this);
        user_main_orders.setOnClickListener(this);
        user_main_repay.setOnClickListener(this);
        user_main_settings.setOnClickListener(this);
        user_mine_catched.setOnClickListener(this);
        user_mine_invite.setOnClickListener(this);
        user_main_vip.setOnClickListener(this);
        user_mian_login_now.setOnClickListener(this);
        user_mian_arrow.setOnClickListener(this);
        user_mian_rechargr_message.setOnClickListener(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(UserInfo userInfo) {
        if (userInfo != null) {
            Log.i("dqqttt", "onMessage: " + userInfo);
            ImgLoaderUtil.displayImage(RunningContext.loginTelInfo.avatar,
                    iv_dlg_personal_avatar, getActivity().getResources()
                            .getDrawable(R.mipmap.user_mian_icon_new_1));
            tv_dlg_personal_name.setText(RunningContext.loginTelInfo.nickname);
            user_main_diamonds.setText(
                    String.valueOf(" " + RunningContext.loginTelInfo.money)
                            + " ");
            user_main_points_shop.setText(
                    String.valueOf(" " + RunningContext.loginTelInfo.points)
                            + " ");
            if (myscrollview_usermian.isRefreshing()) {
                myscrollview_usermian.onRefreshComplete();
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
          /*  throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");*/
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_mian_arrow:
            case R.id.user_main_persson:
                //个人中心
                startActivity(new Intent(getActivity(), PersonalInfoActivity.class));
                break;
            case R.id.user_main_Diamonds_linear:
                //我的钻石
              //  startActivity(new Intent(getActivity(), Recharge_newActivity.class));
                startActivity(new Intent(getActivity(), RechargeNewActivity.class));
                break;
            case R.id.user_main_points_shop_linear:
                //积分商城
                startActivity(new Intent(getActivity(), ShopActivity.class));
                //Toast.makeText(getActivity(), "暂未开放", Toast.LENGTH_SHORT).show();
                break;
            case R.id.user_main_takes_linear://我的碎片
                startActivity(new Intent(getActivity(), NewInviteActivity.class).putExtra("isTakes",true));
                break;
            case R.id.user_main_wawadai_linear:
                //娃娃带
                //startActivity(new Intent(getActivity(), ToysBagActivity.class));
                startActivity(new Intent(getActivity(), WaWaBoxActivity.class));
                break;
            case R.id.user_main_adreess:
                //地址管理
                startActivity(new Intent(getActivity(), AddressManageActivity.class));
                break;
            case R.id.user_main_orders:
                //订单中心
                startActivity(new Intent(getActivity(), OrderListActivity.class));
                break;

            case R.id.user_mine_catched:
                //抓取记录
                startActivity(new Intent(getActivity(), GrabRecordsActivity.class));
                break;
            case R.id.user_mine_invite: //兑换中心
                //startActivity(new Intent(getActivity(), InviteFriendActivity.class));
                startActivity(new Intent(getActivity(), NewInviteActivity.class));
                break;
            case R.id.user_main_repay:

                break;
            case R.id.user_main_settings:
                //设置 SettingsActivity
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                break;
            case R.id.user_main_vip:
                //会员中心
                //startActivity(new Intent(getActivity(), Vip_mainActivity.class));
                startActivity(new Intent(getActivity(), VipUserActivity.class));
                break;
            case R.id.user_mian_login_now:
                //立即登陆
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;

            case R.id.user_mian_rechargr_message:
                //充值记录
                startActivity(new Intent(getActivity(), RechargeRecordListActivity.class));
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
