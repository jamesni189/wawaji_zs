package com.ydd.pockettoycatcherrwz.network.mina.result;

/**
 * Created by Administrator on 2018/1/25.
 */

import com.ydd.pockettoycatcherrwz.entity.Players;

import java.util.List;

/**
 * 进入房间离开房间
 */

public class JoinResult {
    public   String  cmd;
    public  String vmc_no;
    public  String  headUrl;
    public  String  nickname;
    public  int  gameStatus;
    public  int  member_count;
    public List<Players> players;
}
