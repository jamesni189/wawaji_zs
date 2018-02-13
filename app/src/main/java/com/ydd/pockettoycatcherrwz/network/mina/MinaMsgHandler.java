package com.ydd.pockettoycatcherrwz.network.mina;

import android.text.TextUtils;

import com.ydd.pockettoycatcherrwz.util.CommonUtil;
import com.ydd.pockettoycatcherrwz.util.Constants;
import com.ydd.pockettoycatcherrwz.util.HexUtil;
import com.ydd.pockettoycatcherrwz.util.LogUtil;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

/**
 * mina消息处理<br/>
 * 目前心跳包发送频率为3分钟一次，，则会发送心跳包
 * 如果3分钟内服务端没有收到客户端的请求
 * Created by czhang on 17/1/18.
 */

public class MinaMsgHandler extends IoHandlerAdapter {

	/**
	 * 上次处理剩余的数据
	 */
	private byte[] mLeftBuffers;

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		LogUtil.printMina("client exception");
		super.exceptionCaught(session, cause);
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		super.messageReceived(session, message);
		// TODO czhang 此处考虑处理消息
		if (message == null) {
			return;
		}

		IoBuffer ioBuffer = (IoBuffer) message;
		byte[] b = new byte[ioBuffer.limit()];
		if (b == null || b.length == 0) {
			return;
		}
		ioBuffer.get(b);
		// 加上上次剩余的总数据
		byte[] totalBytes = CommonUtil.addArray(mLeftBuffers, b);
		dealMsg(totalBytes);

		// byte[] datas = new byte[ioBuffer.limit() - 8];
		// for (int i = 0; i < b.length; i++) {
		// if (i < 8) {
		// continue;
		// }
		// datas[i - 8] = b[i];
		// }
		// String msgContent = HexUtil.byteArrayToString(datas);
		// LogUtil.printMina("message received:" + msgContent);
		// postMsg(msgContent);

	}

	private void dealMsg(byte[] bytes) throws Exception {
		if (bytes == null || bytes.length < 8) {
			// 长度小于8，放小下次处理
			mLeftBuffers = bytes;
			return;
		}
		// 校验doll开头，如果不是，直接舍弃
		for (int i = 0; i < 4; i++) {
			if (bytes[i] != Constants.DOLL_BYTE[i]) {
				mLeftBuffers = null;
				return;
			}
		}
		byte[] sizeB = CommonUtil.subArray(bytes, 4, 8);
		int size = HexUtil.byte2Int(sizeB);
		// int size = bytes[7] + bytes[6] * 256 + bytes[5] * 256 * 256
		// + bytes[4] * 256 * 256;
		if (bytes.length < size) {
			// 总长度小于需要接收的长度，把数据塞到剩余缓存，下次处理
			mLeftBuffers = bytes;
			return;
		}
		// 截取消息并post出去
		byte[] msgBytes = CommonUtil.subArray(bytes, 8, size);
		String msgContent = HexUtil.byteArrayToString(msgBytes);
		LogUtil.printMina("message received:" + msgContent);
		postMsg(msgContent);
		// 剩余buffer
		mLeftBuffers = CommonUtil.subArray(bytes, size, bytes.length);
		dealMsg(mLeftBuffers);
	}

	/**
	 * 将消息通过eventbus抛出去
	 */
	private void postMsg(String msg) throws Exception {
		JSONObject jsonObject = new JSONObject(msg);
		String cmd = jsonObject.optString("cmd");
		if (TextUtils.isEmpty(cmd)) {
			return;
		}
		if (jsonObject.optString("cmd").equals(MinaCmd.HB_R)) {
			// 心跳包忽略
			return;
		}
		// 构建消息并抛出
		BaseMinaMsg minaMsg = new BaseMinaMsg();
		minaMsg.cmd = cmd;
		minaMsg.msg = msg;
		EventBus.getDefault().post(minaMsg);

	}

	@Override
	public void messageSent(IoSession session, Object message)
			throws Exception {
		super.messageSent(session, message);
		if (message == null) {
			return;
		}
		// 此处处理消息发送
		LogUtil.printMina("message sent:" + message);
	}

}