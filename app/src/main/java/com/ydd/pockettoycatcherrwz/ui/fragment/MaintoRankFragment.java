package com.ydd.pockettoycatcherrwz.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.ui.adapter.MyFragmentPageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MaintoRankFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MaintoRankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


//大神榜单


public class MaintoRankFragment extends Fragment implements
        RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private RadioGroup radioGrop;
    private RadioButton rb_month;
    private RadioButton rb_total;
    private ViewPager view_pager_rank;

    private MonthFragment monthFragment;//月榜
    private TotalFragment totalFragment; //总榜
    private List<Fragment> fragmentLists;

    private MyFragmentPageAdapter adapter;
    //定义FragmentManager
    private FragmentManager fragmentManager;

    public MaintoRankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MaintoRankFragment newInstance(String param1, String param2) {
        MaintoRankFragment fragment = new MaintoRankFragment();
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
        return inflater.inflate(R.layout.fragment_rank, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        radioGrop = (RadioGroup) view.findViewById(R.id.radioGrop);
        rb_month = (RadioButton) view.findViewById(R.id.rb_month);
        rb_total = (RadioButton) view.findViewById(R.id.rb_total);
        view_pager_rank = (ViewPager) view.findViewById(R.id.view_pager_rank);

        radioGrop.setOnCheckedChangeListener(this);
        initViewPager();
    }


    private void initViewPager() {
        monthFragment = new MonthFragment();
        totalFragment = new TotalFragment();


        fragmentLists = new ArrayList<Fragment>();
        fragmentLists.add(monthFragment);
        fragmentLists.add(totalFragment);
        //获取FragmentManager对象
        fragmentManager = getFragmentManager();
        //获取FragmentPageAdapter对象
        adapter = new MyFragmentPageAdapter(fragmentManager, fragmentLists);
        //设置Adapter，使ViewPager 与 Adapter 进行绑定
        view_pager_rank.setAdapter(adapter);
        //设置ViewPager默认显示第一个View
        view_pager_rank.setCurrentItem(0);
        //设置第一个RadioButton为默认选中状态
        rb_month.setChecked(true);

        //ViewPager页面切换监听
        view_pager_rank.addOnPageChangeListener(this);
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        switch (position){
            case 0:
                radioGrop.check(R.id.rb_month);
                break;
            case 1:
                radioGrop.check(R.id.rb_total);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {

            case R.id.rb_month:
               view_pager_rank.setCurrentItem(0,false);
                break;
            case R.id.rb_total:
                view_pager_rank.setCurrentItem(1,false);
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
}
