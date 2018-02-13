package com.ydd.pockettoycatcherrwz.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.network.mina.MinaManager;
import com.ydd.pockettoycatcherrwz.network.mina.request.ConnectParam;
import com.ydd.pockettoycatcherrwz.network.mina.request.GrabParam;
import com.ydd.pockettoycatcherrwz.network.mina.request.HbParam;
import com.ydd.pockettoycatcherrwz.network.mina.request.MoveParam;
import com.ydd.pockettoycatcherrwz.network.mina.request.StartParam;
import com.ydd.pockettoycatcherrwz.ui.home.LiveActivity;

import io.agora.openlive.model.ConstantApp;


/**
 * Created by mac on 17/11/2.
 */

public class MinaTestActivity extends BaseActivity
		implements View.OnClickListener {

	// 62e01a8c57af680a99562f7d43205877
	private String token = "036520394c24d93f9dfabf3cf001a2a1";

	private String vmc_no = "f527008d024800";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mina_test);

		findViewById(R.id.btn_test_hb).setOnClickListener(this);
		findViewById(R.id.btn_test_connect).setOnClickListener(this);
		findViewById(R.id.btn_test_grab).setOnClickListener(this);
		findViewById(R.id.btn_test_start).setOnClickListener(this);
		findViewById(R.id.btn_test_move_behind).setOnClickListener(this);
		findViewById(R.id.btn_test_move_front).setOnClickListener(this);
		findViewById(R.id.btn_test_move_left).setOnClickListener(this);
		findViewById(R.id.btn_test_move_right).setOnClickListener(this);
		findViewById(R.id.btn_test_live).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_test_connect:
			doConnect();
			break;
		case R.id.btn_test_hb:
			doHb();
			break;
		case R.id.btn_test_start:
			doStart();
			break;
		case R.id.btn_test_move_behind:
			doMove(2);
			break;
		case R.id.btn_test_move_front:
			doMove(1);
			break;
		case R.id.btn_test_move_left:
			doMove(3);
			break;
		case R.id.btn_test_move_right:
			doMove(4);
			break;
		case R.id.btn_test_live:
			doLive();
			break;
		case R.id.iv_live_grab:
			doGrab();
			break;
		}
	}

	private void doConnect() {
		ConnectParam param = new ConnectParam();
		param.token = token;
		param.vmc_no = vmc_no;
		MinaManager.getInstance().sendMsg(new Gson().toJson(param));
	}

	private void doHb() {
		HbParam hbParam = new HbParam();
		hbParam.vmc_no = vmc_no;
		MinaManager.getInstance().sendMsg(new Gson().toJson(hbParam));
	}

	private void doGrab() {
		GrabParam grabParam = new GrabParam();
		grabParam.vmc_no = vmc_no;
		MinaManager.getInstance().sendMsg(new Gson().toJson(grabParam));
	}

	private void doStart() {
		StartParam startParam = new StartParam();
		startParam.vmc_no = vmc_no;
		MinaManager.getInstance().sendMsg(new Gson().toJson(startParam));
	}

	private void doMove(int direction) {
		MoveParam moveParam = new MoveParam();
		moveParam.direction = direction;
		moveParam.vmc_no = vmc_no;
		MinaManager.getInstance().sendMsg(new Gson().toJson(moveParam));
	}

	private void doLive() {
		Intent i = new Intent(this, LiveActivity.class);
//		i.putExtra(ConstantApp.ACTION_KEY_CROLE,
//				Constants.CLIENT_ROLE_AUDIENCE);
		i.putExtra(ConstantApp.ACTION_KEY_ROOM_NAME, "133506a147");
		startActivity(i);
	}

}
