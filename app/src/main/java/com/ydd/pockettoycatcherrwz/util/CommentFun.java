package com.ydd.pockettoycatcherrwz.util;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ydd.pockettoycatcherrwz.R;

/**
 * Created by Administrator on 2018/2/8.
 */

public class CommentFun {

    /**
     * 弹出评论对话框
     */
    public static void inputComment(final Activity activity, final ListView listView,
                                    final View btnComment,
                                    final InputCommentListener listener) {
        String hint;

        // 获取评论的位置,不要在CommentDialogListener.onShow()中获取，onShow在输入法弹出后才调用，
        // 此时btnComment所属的父布局可能已经被ListView回收
        final int[] coord = new int[2];
        if (listView != null) {
            btnComment.getLocationOnScreen(coord);
        }

            showInputComment(activity, "", new CommentDialogListener() {
                @Override
                public void onClickPublish(final Dialog dialog, EditText input, final TextView btn) {
                    final String content = input.getText().toString();
                    if (content.trim().equals("")) {
                        Toast.makeText(activity, "发送内容不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    btn.setClickable(false);
                    //final String receiverId = receiver == null ? "-1" : receiver.getByReplyId();
                    //final String receiverNmae = receiver == null ? "" : receiver.getByReplyName();

                    dialog.dismiss();


                }

                @Override
                public void onShow(int[] inputViewCoordinatesInScreen) {
                    if (listView != null) {
                        // 点击某条评论则这条评论刚好在输入框上面，点击评论按钮则输入框刚好挡住按钮
                     /*   int span = btnComment.getId() == R.id.chat_replay ? 0 : btnComment.getHeight();
                        listView.smoothScrollBy(coord[1] + span - inputViewCoordinatesInScreen[1], 1000);*/
                    }
                }

                @Override
                public void onDismiss() {

                }
            });




    }
    public static class InputCommentListener {
        //　评论成功时调用
        public void onCommitComment() {

        }
    }



    /**
     * 弹出评论对话框
     */
    private static Dialog showInputComment(Activity activity, CharSequence hint, final CommentDialogListener listener) {
        final Dialog dialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.view_input_comment);
        dialog.findViewById(R.id.input_comment_dialog_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (listener != null) {
                    listener.onDismiss();
                }
            }
        });
        final EditText input = (EditText) dialog.findViewById(R.id.input_comment);
        input.setHint(hint);
        final TextView btn = (TextView) dialog.findViewById(R.id.btn_publish_comment);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickPublish(dialog, input, btn);
                }
            }
        });
        dialog.setCancelable(true);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (listener != null) {
                    int[] coord = new int[2];
                    dialog.findViewById(R.id.input_comment_container).getLocationOnScreen(coord);
                    // 传入 输入框距离屏幕顶部（不包括状态栏）的长度
                    listener.onShow(coord);
                }
            }
        }, 300);
        return dialog;
    }

    public interface CommentDialogListener {
        void onClickPublish(Dialog dialog, EditText input, TextView btn);

        void onShow(int[] inputViewCoordinatesOnScreen);

        void onDismiss();
    }
}
