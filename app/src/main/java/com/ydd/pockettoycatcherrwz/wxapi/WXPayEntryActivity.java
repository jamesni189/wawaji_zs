package com.ydd.pockettoycatcherrwz.wxapi;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.ydd.pockettoycatcherrwz.UserManager;
import com.ydd.pockettoycatcherrwz.entity.WXentity.WXpayResult;
import com.ydd.pockettoycatcherrwz.ui.BaseActivity;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {


    private IWXAPI api;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);
    	api = WXAPIFactory.createWXAPI(this, Constants.WX_APPID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			if(resp.errCode==BaseResp.ErrCode.ERR_USER_CANCEL){
				Toast.makeText(WXPayEntryActivity.this,"取消支付",Toast.LENGTH_SHORT).show();
			}else if(resp.errCode==BaseResp.ErrCode.ERR_OK){
				Toast.makeText(WXPayEntryActivity.this,"支付成功",Toast.LENGTH_SHORT).show();
				UserManager.getInstance().refresh();
				EventBus.getDefault().post(new WXpayResult());
			}else {
				Toast.makeText(WXPayEntryActivity.this,"支付失败",Toast.LENGTH_SHORT).show();
			}
			finish();
		}
	}
}