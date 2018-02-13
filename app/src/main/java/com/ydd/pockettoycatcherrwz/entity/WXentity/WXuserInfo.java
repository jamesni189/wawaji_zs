package com.ydd.pockettoycatcherrwz.entity.WXentity;

import java.util.List;

/**
 * Created by jia on 17/11/7.
 *
 * 微信获取用户个人信息（UnionID机制）返回
 */

public class WXuserInfo {

    /**
     * 普通用户的标识，对当前开发者帐号唯一
     */
    public String openid;

    /**
     * 普通用户昵称
     */
    public String nickname;


    /**
     * 普通用户性别，1为男性，2为女性
     */
    public int sex;

    /**
     * 省份
     */
    public String province;

    /**
     * 城市
     */
    public String city;


    /**
     * 国家
     */
    public String country;

    public String headimgurl;

    public String unionid;

    /**
     * 用户特权信息
     */
    public List<String> privilege;

}
