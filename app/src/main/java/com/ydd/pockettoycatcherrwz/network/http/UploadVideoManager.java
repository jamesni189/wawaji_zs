package com.ydd.pockettoycatcherrwz.network.http;

import android.util.Log;

import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.UploadVideoRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;
import com.ydd.pockettoycatcherrwz.util.ListUtil;
import com.ydd.pockettoycatcherrwz.util.LogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 上传视频管理器
 */
public class UploadVideoManager {
    private static UploadVideoManager instance;

    private UploadVideoManager() {
        mVideoList = new ArrayList<>();
    }

    /**
     * 单例模式，获取实例
     *
     * @return
     */
    public static UploadVideoManager getInstance() {
        if (instance == null) {
            synchronized (UploadVideoManager.class) {
                if (instance == null) {
                    instance = new UploadVideoManager();
                }
            }
        }
        return instance;
    }

    private List<String> mVideoList;

    /**
     * 是否在上传中
     */
    private boolean mIsUploading;

    public void checkUpload() {
        String filePath = Constants.VIDEO_PATH;
        File f = new File(filePath);
        if (f.exists() && f.isDirectory()) {
            File[] files = f.listFiles();
            for (int i = 0; i < files.length; i++) {
                String logId = files[i].getName().replace(".mp4", "");
                addToUpload(logId);
            }
        }
    }

    /**
     * 加到上传队列
     *
     * @param logId
     */
    public void addToUpload(String logId) {
        if (!mVideoList.contains(logId)) {
            mVideoList.add(logId);
        }
        uploadList();
    }

    private void uploadList() {
        if (!ListUtil.isEmpty(mVideoList) && !mIsUploading) {
            mIsUploading = true;
            upload(mVideoList.get(0));
        }
    }

    /**
     * 根据logId来上传文件
     *
     * @param logId
     */
    private void upload(final String logId) {
        String filePath = Constants.VIDEO_PATH + "/" + logId + ".mp4";
        final File file = new File(filePath);
        if (!file.exists()) {
            mVideoList.remove(logId);
            uploadList();
            LogUtil.printJ("file not ");
            return;
        }
        LogUtil.printJ("real upload");
        Log.i("caaqqqqqqq", "upload: "+RunningContext.accessToken);
        UploadVideoRequest request = new UploadVideoRequest(
                RunningContext.accessToken, logId, file);
        BusinessManager.getInstance().uploadVideo(request,
                new MyCallback<String>() {
                    @Override
                    public void onSuccess(String result, String message) {
                        // 删除
                        LogUtil.printJ("upload success");
                        file.delete();
                    }

                    @Override
                    public void onError(String errorCode, String message) {
                        // 下次检测上传
                        LogUtil.printJ("upload error" + message);
                    }

                    @Override
                    public void onFinished() {
                        // 从上传列表中移除logId
                        mVideoList.remove(logId);
                        mIsUploading = false;
                        uploadList();
                    }
                });
    }

}
