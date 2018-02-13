package com.ydd.pockettoycatcherrwz.ui.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.UserManager;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.UserEditRequest;
import com.ydd.pockettoycatcherrwz.ui.BaseActivity;
import com.ydd.pockettoycatcherrwz.widget.CommonTitleBar;

/**
 * Created by jia on 17/11/9.
 */

public class EditNickNameActivity extends BaseActivity {

    private EditText nameEdt;
    private CommonTitleBar titleBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name);
        initView();
    }

    private void initView(){
        nameEdt=(EditText)findViewById(R.id.nameEdt);
        titleBar=(CommonTitleBar)findViewById(R.id.common_title_bar);


        nameEdt.setText(RunningContext.loginTelInfo.nickname);
        titleBar.setTitle("修改昵称");

        titleBar.setmRightTv("保存", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(nameEdt.getText().toString())){
                    showToast("昵称不能为空");
                    return;
                }
                if(nameEdt.getText().toString().equals(RunningContext.loginTelInfo.nickname)){
                    finish();
                    return;
                }
                showDialog("");
                BusinessManager.getInstance().userEdit(new UserEditRequest(RunningContext.accessToken, RunningContext.loginTelInfo.avatar, nameEdt.getText().toString()), new MyCallback<Void>() {
                    @Override
                    public void onSuccess(Void result, String message) {
                        RunningContext.loginTelInfo.nickname=nameEdt.getText().toString();
                        UserManager.getInstance().refresh();
                        Intent intent=new Intent();
                        intent.putExtra("name",nameEdt.getText().toString());
                        setResult(RESULT_OK,intent);
                        finish();
                    }

                    @Override
                    public void onError(String errorCode, String message) {
                        showToast("昵称上传失败");
                    }

                    @Override
                    public void onFinished() {
                        dismissDialog();
                    }
                });
            }
        });
    }
}
