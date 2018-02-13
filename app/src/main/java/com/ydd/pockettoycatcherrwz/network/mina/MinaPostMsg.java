package com.ydd.pockettoycatcherrwz.network.mina;

/**
 * ChatService抛出的消息
 *
 * Created by czhang on 17/3/1.
 */

public class MinaPostMsg {
    /**
     * 消息id
     */
    public String msgid;
    /**
     * 消息类型
     */
    public String funcid;
    /**
     * 状态码
     */
    public int code;
    /**
     * 发送者
     */
    public String from;
    /**
     * 接收者
     */
    public String to;
    /**
     * 消息内容
     */
    public String msgContent;
}
