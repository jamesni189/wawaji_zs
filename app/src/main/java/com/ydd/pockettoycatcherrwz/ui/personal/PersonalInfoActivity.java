package com.ydd.pockettoycatcherrwz.ui.personal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.UserManager;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.UploadImageRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.UserEditRequest;
import com.ydd.pockettoycatcherrwz.ui.BaseTitleActivity;
import com.ydd.pockettoycatcherrwz.ui.record.CameraDialog;
import com.ydd.pockettoycatcherrwz.util.ImageUtil;
import com.ydd.pockettoycatcherrwz.util.ImgLoaderUtil;

import java.io.File;

/**
 * 个人信息
 */
public class PersonalInfoActivity extends BaseTitleActivity {

    /**
     * 头像
     */
    private ImageView mAvatarIv;
    /**
     * 昵称
     */
    private TextView mNameTv;

    private CameraDialog dialog;

    private EditPutDialog editPutDialog;

    public static final int CHANGENAME=1989;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (RunningContext.loginTelInfo == null) {
            finish();
            return;
        }
        setContent(R.layout.activity_personal_info);
        setTitle("个人信息");
        initView();
    }

    private void initView() {
        mAvatarIv = (ImageView) findViewById(R.id.iv_personal_info_avatar);
        mNameTv = (TextView) findViewById(R.id.tv_personal_info_nickname);

//        LogUtil.printJ(RunningContext.userInfo.avatar + " =头像");
        ImgLoaderUtil.displayImage(RunningContext.loginTelInfo.avatar, mAvatarIv);
        mNameTv.setText(RunningContext.loginTelInfo.nickname);

        findViewById(R.id.ln_personal_info_avatar)
                .setOnClickListener(mOnClickListener);
        findViewById(R.id.ln_personal_info_nickname)
                .setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ln_personal_info_avatar:
                    // 头像
                    showDialog();
                    break;
                case R.id.ln_personal_info_nickname:
                    // 昵称
//                    showEditDialog();
                    Intent intent=new Intent(PersonalInfoActivity.this,EditNickNameActivity.class);
                    startActivityForResult(intent,CHANGENAME);
                    break;
            }
        }
    };

    private void showDialog() {
        if (dialog == null) {
            dialog = new CameraDialog(this);
        }
        dialog.show();
    }


    private void showEditDialog() {
        if (editPutDialog == null) {
            editPutDialog = new EditPutDialog(this);
            editPutDialog.setInputListener(new EditPutDialog.InputListener() {
                @Override
                public void onConfirm(final String s) {
                    BusinessManager.getInstance().userEdit(new UserEditRequest(RunningContext.accessToken, RunningContext.loginTelInfo.avatar, s), new MyCallback<Void>() {
                        @Override
                        public void onSuccess(Void result, String message) {
                            mNameTv.setText(s);
                            RunningContext.loginTelInfo.nickname=s;
                            UserManager.getInstance().refresh();
                        }

                        @Override
                        public void onError(String errorCode, String message) {
                            showToast("昵称上传失败");
                        }

                        @Override
                        public void onFinished() {

                        }
                    });
                }
            });
        }
        editPutDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CameraDialog.TAKEPHOTO:
                File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
                cropPhoto(Uri.fromFile(temp));// 裁剪图片
                break;
            case CameraDialog.CHOOSE:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());
                } else {
                    showToast("选取失败");
                }
                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    final Bitmap head = extras.getParcelable("data");
                    if (head != null) {
                        /**
                         * 上传服务器代码
                         */
                        ImageUtil.saveBitmapToDisk(head, getFilesDir().getAbsolutePath(), "head");

                        BusinessManager.getInstance().uploadImage(new UploadImageRequest(new File(getFilesDir().getAbsolutePath(), "head.png")), new MyCallback<String>() {
                            @Override
                            public void onSuccess(String result, String message) {

                                final String avatar = result;
                                BusinessManager.getInstance().userEdit(new UserEditRequest(RunningContext.accessToken, result, mNameTv.getText().toString()), new MyCallback<Void>() {
                                    @Override
                                    public void onSuccess(Void result, String message) {
                                        mAvatarIv.setImageBitmap(head);// 用ImageView显示出来
                                        RunningContext.loginTelInfo.avatar=avatar;
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

                            @Override
                            public void onError(String errorCode, String message) {
                                showToast("上传头像失败");
                            }

                            @Override
                            public void onFinished() {

                            }
                        });
                    }
                }
                break;
            case CHANGENAME:
                if(data!=null){
                    String nickname=data.getStringExtra("name");
                    mNameTv.setText(nickname);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 调用系统的裁剪
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }



}
