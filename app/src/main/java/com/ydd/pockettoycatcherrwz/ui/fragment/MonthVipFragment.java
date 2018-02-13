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
import android.widget.TextView;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.entity.RechargeGridBean;
import com.ydd.pockettoycatcherrwz.entity.Vip_user_WeekorMonth;
import com.ydd.pockettoycatcherrwz.entity.WeekOrMouthBean;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.GetUserVipKinds;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.GetweekorMonthMessage;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.RewardsPoints;
import com.ydd.pockettoycatcherrwz.ui.home.LookForDeter_Activity;
import com.ydd.pockettoycatcherrwz.ui.home.SignSuccessDialog;
import com.ydd.pockettoycatcherrwz.ui.personal.RechargeConfirmActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MonthVipFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MonthVipFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonthVipFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private TextView tv_vip_title;

    private TextView vip_end_time;
    private TextView tv_vip_add_money; //续费
    private TextView tv_get_tip2;//已领取
    private TextView tv_today_money; //今日领取
    private TextView tv_get_jiangli; //查看明细
    private TextView tv_geted_money; //
    private TextView tv_get_; //立即领取


    public MonthVipFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MonthVipFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MonthVipFragment newInstance(String param1, String param2) {
        MonthVipFragment fragment = new MonthVipFragment();
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
        return inflater.inflate(R.layout.fragment_week_vip, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initviews(view);
        tv_vip_title.setText("月卡会员");
        initdatas();
    }

    private  void initviews(View view){
        tv_vip_title = (TextView) view.findViewById(R.id.tv_vip_title);
        vip_end_time = (TextView) view.findViewById(R.id.vip_end_time);
        tv_geted_money = (TextView) view.findViewById(R.id.tv_geted_money);
        tv_vip_add_money = (TextView) view.findViewById(R.id.tv_vip_add_money);
        tv_get_tip2 = (TextView) view.findViewById(R.id.tv_get_tip2);
        tv_today_money = (TextView) view.findViewById(R.id.tv_today_money);
        tv_get_jiangli = (TextView) view.findViewById(R.id.tv_get_jiangli);
        tv_get_ = (TextView) view.findViewById(R.id.tv_get_);
    }

    public  void setView(final Vip_user_WeekorMonth result){
        if (result.mouth != null){
            String str = "<u>立即续费</u>";
            vip_end_time.setText(result.mouth.erpireDate + "到期, ");
            tv_vip_add_money.setText(Html.fromHtml(str));
            if (result.mouth.receiveStatus == 0) {
                //vip_stataion.setText("尚未领取专属奖励");
                tv_get_.setText("立即领取");
                tv_get_.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        drwapoints("month", result.mouth.dayMoney + "");
                    }
                });

            }
            if (result.mouth.receiveStatus == 1) {
                //  vip_stataion.setText("已领取专属奖励");
                tv_get_.setText("已领取");
            }

            tv_today_money.setText(result.mouth.totalMoney + "");
            tv_geted_money.setText(result.mouth.dayMoney );
            tv_get_jiangli.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), LookForDeter_Activity.class);
                    intent.putExtra("kinds", 1);
                    intent.putExtra("VIP_week", result.mouth);
                    startActivity(intent);
                }
            });

            tv_vip_add_money.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    GetweekorMonthMessage monthMessage = new GetweekorMonthMessage(2);
                    BusinessManager.getInstance().getCardWeekorMouth(monthMessage,
                            new MyCallback<WeekOrMouthBean>() {
                                @Override
                                public void onSuccess(WeekOrMouthBean result, String message) {
                                    Log.i("caaaqq", "onSuccess: " + result);
                                    RechargeGridBean mRechargeGridBean = new RechargeGridBean(result.price + "", result.money,
                                            result.desc, 1, 0, result.mark, "mouth", result.detail,null);
                                    Intent intent = new Intent(getActivity(), RechargeConfirmActivity.class);
                                    intent.putExtra(
                                            RechargeConfirmActivity.INTENT_EXTRA_KEY_RECHARGE_INFO,
                                            mRechargeGridBean);
                                    startActivity(intent);

                                }

                                @Override
                                public void onError(String errorCode, String message) {

                                }

                                @Override
                                public void onFinished() {

                                }
                            });

                    //     startActivity(new Intent(Vip_mainActivity.this, Recharge_newActivity.class));

                }
            });
        }
    }


    //领取奖励
    private void drwapoints(final String type, final String reword) {
        RewardsPoints mRewardsPoints = new RewardsPoints(type);
        //有问题
        BusinessManager.getInstance().reward(
                mRewardsPoints,
                new MyCallback<Void>() {
                    @Override
                    public void onSuccess(Void result, String message) {
                        initdatas();
                        String comment = "恭喜领取"+reword+"钻石";
                        SignSuccessDialog mSignSuccessDialog = new SignSuccessDialog(getActivity(),
                                comment, type);
                        mSignSuccessDialog.show();

                    }

                    @Override
                    public void onError(String errorCode, String message) {
                        Log.i("ccaa", "onSuccess: " + message);

                    }

                    @Override
                    public void onFinished() {
                    }
                });
    }



    private void initdatas() {
        GetUserVipKinds mGetUserVipKinds = new GetUserVipKinds();
        BusinessManager.getInstance().getUserVipKings(mGetUserVipKinds,
                new MyCallback<Vip_user_WeekorMonth>() {
                    @Override
                    public void onSuccess(Vip_user_WeekorMonth result, String message) {
                        Log.i("dqrrrrrr", "onSuccess: " + result.toString());
                        setView(result);
                    }

                    @Override
                    public void onError(String errorCode, String message) {
                        //   showToast(message);
                    }

                    @Override
                    public void onFinished() {
                    }
                });

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
            /*throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");*/
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
}
