package com.ydd.pockettoycatcherrwz.ui.personal;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;

/**
 * Created by jia on 17/11/3.
 */

public class EditPutDialog extends Dialog  implements View.OnClickListener{



    private EditText inputEdit;

    private Context context;

    public EditPutDialog(Context context) {
        super(context,R.style.bottom_dialog);
        initWindow();
        setCancelable(false);
        this.context=context;
        setContentView(R.layout.dialog_edit);
        initView();
    }


    /**
     * 初始化window属性，使对话框居于底部，水平向填满屏幕
     */
    private void initWindow() {
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = RunningContext.screenWidth*2/3;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }


    private void initView(){
        inputEdit=(EditText)findViewById(R.id.edittext);
        findViewById(R.id.tv_ok).setOnClickListener(this);
        findViewById(R.id.tv_cancle).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
            switch (view.getId()){
                case R.id.tv_ok:
                    if(!TextUtils.isEmpty(inputEdit.getText().toString())) {
//                        textView.setText(inputEdit.getText().toString());
                        if(listener!=null){
                            listener.onConfirm(inputEdit.getText().toString());
                        }
                        dismiss();
                    }else {
                        Toast.makeText(context,"输入不能为空",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.tv_cancle:
                    dismiss();
                    break;
            }
    }

    public interface InputListener{
        void onConfirm(String s);
    }

    private InputListener listener;

    public void setInputListener(InputListener listener){
        this.listener=listener;
    }

}
