package com.ydd.pockettoycatcherrwz.ui.record;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.widget.BaseBottomDialog;

import java.io.File;

/**
 * 申诉对话框
 */
public class CameraDialog extends BaseBottomDialog {

    private Activity context;

    public static final int CHOOSE = 10001;

    public static final int TAKEPHOTO = 10002;

    public CameraDialog(Activity context) {
        super(context);
        this.context = context;
        setContentView(R.layout.dialog_camera);
        initView();
    }

    private void initView() {
        findViewById(R.id.tv_dlg_appeal_cancel)
                .setOnClickListener(mOnClickListener);
        findViewById(R.id.tv_choose)
                .setOnClickListener(mOnClickListener);
        findViewById(R.id.tv_takephoto)
                .setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_dlg_appeal_cancel:
                    dismiss();
                    break;
                case R.id.tv_takephoto:
                    Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
                    context.startActivityForResult(intent2, TAKEPHOTO);// 采用ForResult打开
                    dismiss();
                    break;
                case R.id.tv_choose:
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    context.startActivityForResult(intent, CHOOSE);
                    dismiss();
                    break;
            }
        }
    };

}
