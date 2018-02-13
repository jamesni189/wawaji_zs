package com.ydd.pockettoycatcherrwz.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.entity.Doll;
import com.ydd.pockettoycatcherrwz.entity.DollCallback;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.DiamondExChargeRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.DollListRequest;
import com.ydd.pockettoycatcherrwz.ui.adapter.ShoppingCartAdapter;
import com.ydd.pockettoycatcherrwz.ui.personal.OrderActivity;
import com.ydd.pockettoycatcherrwz.util.ListUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WawaBoxFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WawaBoxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WawaBoxFragment extends Fragment implements View.OnClickListener,
        ShoppingCartAdapter.CheckInterface, ShoppingCartAdapter.ModifyCountInterface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private TextView tv_change;
    private TextView tv_get;
    private TextView tv_cancle;
    private TextView tv_diamond_num;
    private TextView tv_do_get;
    private GridView gv_gift_wawa;
    private ShoppingCartAdapter shoppingCartAdapter;


    private List<Doll> dolllist;
    private ArrayList<Doll> arrayListdoll = new ArrayList<>();
    private DollCallback dollCallback = null;
    private DollCallback callback;
    private double totalPrice = 0.00;// 选中商品总价
    private int totalCount = 0;// 选中商品总数量

    private boolean isCheckAll = false; //是否选择领取或者兑换
    private boolean isSelectDiamonds = false; //领取钻石
    private LinearLayout line_bottom1, line_bottom2;

    ArrayList<Integer> productIds = new ArrayList<>();
    ArrayList<Integer> nums = new ArrayList<>();
    private int total = 0;
    private LinearLayout line_tips;


    private Handler mHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 1) {
                if (dolllist.size() > 0) {
                    //tv_tip_diamond.setText(total);
                    tv_diamond_num.setText(total+"");
                    shoppingCartAdapter.refreshUi(dolllist);
                    line_tips.setVisibility(View.GONE);
                    line_bottom1.setVisibility(View.VISIBLE);
                }else {
                    line_tips.setVisibility(View.VISIBLE);
                    line_bottom1.setVisibility(View.GONE);
                }

            } else if (msg.what == 2) {
                if (isSelectDiamonds && !isCheckAll) {
                    tv_do_get.setText("兑换钻石" + "(" + totalPrice + ")");
                } else if (isCheckAll && !isSelectDiamonds) {
                    tv_do_get.setText("立即领取" + "(" + totalCount + ")");
                }
            }
        }
    };

    public WawaBoxFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WawaBoxFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WawaBoxFragment newInstance(String param1, String param2) {
        WawaBoxFragment fragment = new WawaBoxFragment();
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
        return inflater.inflate(R.layout.fragment_wawa_box, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_change = (TextView) view.findViewById(R.id.tv_change);
        tv_get = (TextView) view.findViewById(R.id.tv_get);
        tv_cancle = (TextView) view.findViewById(R.id.tv_cancel);
        tv_diamond_num = (TextView) view.findViewById(R.id.tv_diamond_num);
        tv_do_get = (TextView) view.findViewById(R.id.tv_do_get);
        gv_gift_wawa = (GridView) view.findViewById(R.id.gv_gift_wawa);
        line_bottom1 = (LinearLayout) view.findViewById(R.id.bottom_line1);
        line_bottom2 = (LinearLayout) view.findViewById(R.id.bottom_line2);
        line_tips = (LinearLayout) view.findViewById(R.id.line_tips);
        dolllist = new ArrayList<>();
        // initData();

        tv_cancle.setOnClickListener(this);
        tv_get.setOnClickListener(this);
        tv_do_get.setOnClickListener(this);
        tv_change.setOnClickListener(this);

        //initData();

        // list_shopping_cart.setAdapter(shoppingCartAdapter);
        // shoppingCartAdapter.setShoppingCartBeanList(dolllist);
    }

    private void initData() {

        shoppingCartAdapter = new ShoppingCartAdapter(getActivity());
        gv_gift_wawa.setAdapter(shoppingCartAdapter);
        BusinessManager.getInstance().getDollList(new DollListRequest(RunningContext.accessToken), new MyCallback<DollCallback>() {
            @Override
            public void onSuccess(DollCallback result, String message) {
                callback = result;
                dolllist.clear();
                if (!ListUtil.isEmpty(result.data)) {
                    dolllist = result.data;
                    total = result.total;

                    mHandle.sendEmptyMessage(1);
                    // getButton.setVisibility(View.VISIBLE);
                }
                dollCallback = result;
            }

            @Override
            public void onError(String errorCode, String message) {
                Log.i("===", errorCode + message);
            }

            @Override
            public void onFinished() {
            }
        });

        shoppingCartAdapter.setCheckInterface(this);
        shoppingCartAdapter.setModifyCountInterface(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        line_bottom1.setVisibility(View.VISIBLE);
        line_bottom2.setVisibility(View.GONE);
        initData();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
           /* throw new RuntimeException(context.toString()
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

            case R.id.tv_change://兑换
                isSelectDiamonds = true;
                isCheckAll = false;
                line_bottom1.setVisibility(View.GONE);
                line_bottom2.setVisibility(View.VISIBLE);
                totalCount = 0;
                totalPrice = 0.00;
                if (dolllist.size() > 0) {
                    for (int i = 0; i < dolllist.size(); i++) {
                        dolllist.get(i).setChoosed(true);
                        dolllist.get(i).selectNum = dolllist.get(i).num;
                        if (totalCount == 0) {
                            totalCount = dolllist.get(i).num;
                            totalPrice = dolllist.get(i).diamonds * totalCount;
                        } else {
                            totalCount = dolllist.get(i).num;
                            totalPrice = dolllist.get(i).diamonds * totalCount + totalPrice;
                        }
                    }

                    shoppingCartAdapter.isShow(false);
                    shoppingCartAdapter.notifyDataSetChanged();
                }
                tv_do_get.setText("立即兑换" + "(" + totalPrice + ")");
                //statistics();
                break;
            case R.id.tv_get://领取
                isSelectDiamonds = false;
                isCheckAll = true;
                line_bottom1.setVisibility(View.GONE);
                line_bottom2.setVisibility(View.VISIBLE);

                totalCount = 0;
                totalPrice = 0.00;
                if (dolllist.size() > 0) {

                    for (int i = 0; i < dolllist.size(); i++) {
                        dolllist.get(i).setChoosed(true);
                        dolllist.get(i).selectNum = dolllist.get(i).num;
                        if (totalCount == 0) {
                            totalCount = dolllist.get(i).num;
                        } else {
                            totalCount = totalCount + dolllist.get(i).num;
                        }
                    }
                    shoppingCartAdapter.isShow(false);
                    shoppingCartAdapter.notifyDataSetChanged();
                }

                tv_do_get.setText("立即领取" + "(" + totalCount + ")");
                //statistics();

                break;
            case R.id.tv_do_get://确定


                if (isCheckAll && !isSelectDiamonds) { //领取

                    if (ListUtil.isEmpty(dolllist)) {
                        //showToast("没有可选娃娃");
                        return;
                    } else {
                        arrayListdoll.clear();
                        arrayListdoll.addAll(dolllist);
                    }

                    Intent intent = new Intent();
                    // intent.putExtra("callback", new Gson().toJson(callback));
                    intent.putExtra("expressMoney", callback.expressMoney);
                    intent.putParcelableArrayListExtra("dolllist", arrayListdoll);
                    intent.setClass(getActivity(), OrderActivity.class);
                    startActivity(intent);
                } else { //兑换
                    if (ListUtil.isEmpty(dolllist)) {
                        //showToast("没有可选娃娃");
                        return;
                    }

                    for (int i = 0; i < dolllist.size(); i++) {
                        int selectNum = dolllist.get(i).selectNum;
                        if (dolllist.get(i).isChoosed && selectNum > 0) {
                            for (int x = 0; x < selectNum; x++) {
                                productIds.add(dolllist.get(i).productId);
                                nums.add(dolllist.get(i).selectNum);
                            }
                        }
                    }
                    exchangeDiamond();
                }

                break;
            case R.id.tv_cancel://取消
                isSelectDiamonds = false;
                isCheckAll = false;
                shoppingCartAdapter.isShow(true);
                line_bottom1.setVisibility(View.VISIBLE);
                line_bottom2.setVisibility(View.GONE);
                break;
        }

    }

    public void exchangeDiamond() {
        DiamondExChargeRequest request = new DiamondExChargeRequest(RunningContext.accessToken, nums, productIds);
        BusinessManager.getInstance().exChangeDiamond(request, new MyCallback() {
            @Override
            public void onSuccess(Object result, String message) {
                Log.i("===", message);
                Toast.makeText(getActivity(),"兑换成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String errorCode, String message) {
                Log.i("===", message);
            }

            @Override
            public void onFinished() {

            }
        });
    }


    @Override
    public void checkGroup(int position, boolean isChecked) {
        dolllist.get(position).setChoosed(isChecked);
        if (isAllCheck()) {
            dolllist.get(position).selectNum = dolllist.get(position).num;
        } else {
            dolllist.get(position).selectNum = 0;
        }
        shoppingCartAdapter.notifyDataSetChanged();
        statistics();
    }

    public void statistics2() {
        totalCount = 0;
        totalPrice = 0.00;
        for (int i = 0; i < dolllist.size(); i++) {
            Doll shoppingCartBean = dolllist.get(i);
            if (shoppingCartBean.isChoosed()) {
                totalCount++;
                totalPrice += shoppingCartBean.diamonds * shoppingCartBean.num;
            }
        }
        if (isSelectDiamonds && !isCheckAll) {
            tv_do_get.setText("兑换钻石" + "(" + totalPrice + ")");
        } else if (isCheckAll && !isSelectDiamonds) {
            tv_do_get.setText("立即领取" + "(" + totalCount + ")");
        }
    /*    tvShowPrice.setText("合计:" + totalPrice);
        tvSettlement.setText("结算(" + totalCount + ")");*/
    }

     /*
    * 增加
    * */

    @Override
    public void doIncrease(int position, View showCountView, boolean isChecked) {
        Doll shoppingCartBean = dolllist.get(position);
        int maxCount = shoppingCartBean.num;
        int currentCount = 0;
        if (shoppingCartBean.selectNum == 0) {
            currentCount = shoppingCartBean.num;
        } else {
            currentCount = shoppingCartBean.selectNum;
        }
        if (currentCount < maxCount) {
            currentCount++;
            //shoppingCartBean.setCount(currentCount);
            shoppingCartBean.selectNum = currentCount;
            //  ((TextView) showCountView).setText(currentCount + "");
            shoppingCartAdapter.notifyDataSetChanged();
            statistics();
        }

    }


    /*
    * 减少
    * */
    @Override
    public void doDecrease(int position, View showCountView, boolean isChecked) {
        Doll shoppingCartBean = dolllist.get(position);
        int currentCount = 0;
        if (shoppingCartBean.selectNum == 0) {
            currentCount = shoppingCartBean.num;
        } else {
            currentCount = shoppingCartBean.selectNum;
        }
        if (currentCount == 1) {
            return;
        }
        currentCount--;
        shoppingCartBean.selectNum = currentCount;
        //  ((TextView) showCountView).setText(currentCount + "");
        shoppingCartAdapter.notifyDataSetChanged();
        statistics();
    }

    @Override
    public void childDelete(int position) {

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


    /**
     * 遍历list集合
     *
     * @return
     */
    private boolean isAllCheck() {

        for (Doll group : dolllist) {
            if (!group.isChoosed())
                return false;
        }
        return true;
    }

    /**
     * 统计操作
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作
     * 3.给底部的textView进行数据填充
     */
    public void statistics() {
        totalCount = 0;
        totalPrice = 0.00;
        for (int i = 0; i < dolllist.size(); i++) {
            Doll shoppingCartBean = dolllist.get(i);
            if (shoppingCartBean.isChoosed()) {
                //totalCount++;
                if (shoppingCartBean.selectNum != 0) {
                    totalCount = shoppingCartBean.selectNum;
                } else {
                    totalCount = shoppingCartBean.num;
                    shoppingCartBean.selectNum = totalCount;
                }

                totalPrice += shoppingCartBean.diamonds * totalCount;
            }
        }
        //   tvShowPrice.setText("合计:" + totalPrice);
        //   tvSettlement.setText("结算(" + totalCount + ")");
        mHandle.sendEmptyMessage(2);
    }

    /**
     * 结算订单、支付
     */
    private void lementOnder() {
        //选中的需要提交的商品清单
        for (Doll bean : dolllist) {
            boolean choosed = bean.isChoosed();
            if (choosed) {
                /*String shoppingName = bean.getShoppingName();
                int count = bean.getCount();
                double price = bean.getPrice();
                int size = bean.getDressSize();
                String attribute = bean.getAttribute();
                int id = bean.getId();
                Log.d(TAG,id+"----id---"+shoppingName+"---"+count+"---"+price+"--size----"+size+"--attr---"+attribute);*/
            }
        }
        //ToastUtil.showL(this,"总价："+totalPrice);

        //跳转到支付界面
    }

}
