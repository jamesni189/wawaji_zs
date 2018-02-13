package com.ydd.pockettoycatcherrwz.entity;

import java.util.List;

/**
 * 签到列表信息
 */
public class SignListInfo {
    /**
     * 1未签到，0已签到
     */
	public int status;

	public List<SignInfo> list;
}
