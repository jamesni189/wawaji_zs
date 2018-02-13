package com.ydd.pockettoycatcherrwz.network.mina;

import android.text.TextUtils;

import com.ydd.pockettoycatcherrwz.util.HexUtil;
import com.ydd.pockettoycatcherrwz.util.LogUtil;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;

/**
 * mina管理器
 * 
 * Created by czhang on 17/1/18.
 */

public class MinaManager implements MinaConfig {

	/**
	 * 会话session
	 */
	private IoSession mSession = null;
	/**
	 * 连结器
	 */
	private IoConnector mConnector = null;

	private static MinaManager instance;

	private MinaManager() {
		initConnector();
	}

	/**
	 * 单例模式，获取实例
	 * 
	 * @return
	 */
	public static MinaManager getInstance() {
		if (instance == null) {
			synchronized (MinaManager.class) {
				if (instance == null) {
					instance = new MinaManager();
				}
			}
		}
		return instance;
	}

	/**
	 * 初始化连接器
	 */
	private void initConnector() {
		mConnector = new NioSocketConnector();
		// 设置链接超时时间
		mConnector.setConnectTimeoutMillis(CONNECT_TIME_OUT);
		// 添加过滤器
		// PrefixedStringCodecFactory stringCodecFactory = new
		// PrefixedStringCodecFactory(
		// Charset.forName("UTF-8"));
		// mConnector.getFilterChain().addLast("codec",
		// new ProtocolCodecFilter(stringCodecFactory));// 解码器
		// 消息处理
		mConnector.setHandler(new MinaMsgHandler());
		// 设置默认连接远程服务器的IP地址和端口
		new Thread(new Runnable() {
			@Override
			public void run() {
				mConnector.setDefaultRemoteAddress(
						new InetSocketAddress(HOST, PORT));
			}
		}).start();
		// 添加监听器，断线重连
		mConnector.addListener(mIoListener);
	}

	public void connect() {
		if (mConnector.isActive() && mSession != null
				&& mSession.isConnected()) {
			return;
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				// 开始连接
				try {
					ConnectFuture future = mConnector.connect();
					future.awaitUninterruptibly();// 等待连接创建完成
					mSession = future.getSession();// 获得session
				} catch (Exception e) {
					e.printStackTrace();
					LogUtil.printMina("client connect failed");
				}
			}
		}).start();
	}

	/**
	 * 发送消息
	 * 
	 * @param msg
	 *            待发送给服务端的消息
	 */
	public void sendMsg(String msg) {
		if (!isConnected()) {
			connect();
			return;
		}
		if (TextUtils.isEmpty(msg)) {
			return;
		}
		LogUtil.printMina("start write data:" + msg);
		byte[] data1 = HexUtil.stringToByteArray("doll");
		byte[] data3 = HexUtil.stringToByteArray(msg);
		int length = data3.length + 8;
		byte[] data2 = HexUtil.intToByteArray(length);

		int length3 = data1.length + data2.length + data3.length;
		byte[] datas = new byte[length3];
		for (int i = 0; i < data1.length; i++) {
			datas[i] = data1[i];
		}
		for (int i = 0; i < data2.length; i++) {
			datas[i + data1.length] = data2[i];
		}
		for (int i = 0; i < data3.length; i++) {
			datas[i + data1.length + data2.length] = data3[i];
		}
		mSession.write(IoBuffer.wrap(datas));
	}

	/**
	 * io监听器，当session被销毁后，考虑重建session
	 */
	private MinaIoListener mIoListener = new MinaIoListener() {

		@Override
		public void sessionDestroyed(IoSession session) throws Exception {
			super.sessionDestroyed(session);
			// TODO czhang session被销毁后，重新建立session
			while (!isConnected()) {
				Thread.sleep(5000);
				connect();
			}
		}
	};

	/**
	 * 判断是否连接
	 */
	public boolean isConnected() {
		if (mConnector != null && mSession != null && mSession.isConnected()) {
			return true;
		}
		return false;
	}

	/**
	 * 断开连接
	 */
	public void disconnected() {
		if (mSession != null && mSession.isConnected()) {
			mSession.closeOnFlush();
		}
	}
}
