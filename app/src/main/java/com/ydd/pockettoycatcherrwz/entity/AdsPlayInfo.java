package com.ydd.pockettoycatcherrwz.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 头部广告播放信息，包括广告信息，播放时间和播放顺序
 * 
 * @author czhang
 */
public class AdsPlayInfo implements Serializable
{
    
    private static final long serialVersionUID = 6962808163355510521L;
    
    /** 播放顺序 0为从左到右，1为从右到左 */
    public int playingOrder;
    
    /** 播放定时时间 */
    public long timing;
    
    /** 广告列表 */
    public List<BannerInfo> pool = new ArrayList<>();
    
    @Override
    public String toString()
    {
        return "playingOrder:" + playingOrder + "|||" + "timing:" + timing + "|||" + "adpool.size:" + pool.size();
    }
}
