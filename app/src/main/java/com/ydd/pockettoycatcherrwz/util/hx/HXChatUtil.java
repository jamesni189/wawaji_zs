package com.ydd.pockettoycatcherrwz.util.hx;

import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

/**
 * 弹幕相关
 */
public class HXChatUtil {

	/**
	 * 加入聊天室
	 */
	public static void joinChatRoom(String roomId,
			EMValueCallBack<EMChatRoom> callBack) {
		EMClient.getInstance().chatroomManager().joinChatRoom(roomId, callBack);
	}

	/**
	 * 离开聊天室
	 * 
	 * @param toChatUserName
	 */
	public static void leaveChatRoom(String toChatUserName) {
		EMClient.getInstance().chatroomManager().leaveChatRoom(toChatUserName);
	}

	/**
	 * 发送聊天室消息
	 * 
	 * @param content
	 * @param toChatUserName
	 */
	public static void sendRoomMsg(String content, String toChatUserName) {
		// 创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
		EMMessage message = EMMessage.createTxtSendMessage(content,
				toChatUserName);
		// 设置消息类型为聊天室
		message.setChatType(EMMessage.ChatType.ChatRoom);
		// 发送消息
		EMClient.getInstance().chatManager().sendMessage(message);
	}

	/**
	 * 发送聊天室消息
	 *
	 * @param toChatUserName
	 */
	public static void sendRoomMsg(String name,int type, String toChatUserName) {
		// 创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
		EMMessage message = EMMessage.createTxtSendMessage("send", toChatUserName);
		message.setAttribute("name",name);
		message.setAttribute("type",type);
		// 设置消息类型为聊天室
		message.setChatType(EMMessage.ChatType.ChatRoom);
		// 发送消息
		EMClient.getInstance().chatManager().sendMessage(message);
	}
}
