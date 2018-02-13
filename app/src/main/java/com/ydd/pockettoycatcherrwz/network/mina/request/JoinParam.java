package com.ydd.pockettoycatcherrwz.network.mina.request;

import com.ydd.pockettoycatcherrwz.entity.Players;

import java.util.List;

/**
 * 进入房间离开房间
 */
public class JoinParam {

	public String cmd ;

	public String vmc_no;
	public int member_count;
	public String nickname;

	private List<Players> players;

	public List<Players> getPlayers() {
		return players;
	}

	public void setPlayers(List<Players> players) {
		this.players = players;
	}
}
