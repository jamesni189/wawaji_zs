package com.ydd.pockettoycatcherrwz.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by C-yl on 2017/12/15.
 */

public class RechargBean implements Serializable {
    public List<RechargeItemInfo> normal;

    public Recharge_mouth mouth;

    public Recharge_week week;

    @Override
    public String toString() {
        return "RechargBean{" +
                "normal=" + normal +
                ", mouth=" + mouth +
                ", week=" + week +
                '}';
    }
}
