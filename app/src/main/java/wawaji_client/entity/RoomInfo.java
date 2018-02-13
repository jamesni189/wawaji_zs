package wawaji_client.entity;



import java.util.List;

import wawaji_client.StreamInfo;

/**
 * Copyright © 2017 Zego. All rights reserved.
 */

public class RoomInfo {
    public String room_id;
    public String room_name;
    public String anchor_id_name;
    public String anchor_nick_name;
    public List<StreamInfo> stream_info;

    @Override
    public String toString() {
        return "RoomInfo{" +
                "room_id='" + room_id + '\'' +
                ", room_name='" + room_name + '\'' +
                ", anchor_id_name='" + anchor_id_name + '\'' +
                ", anchor_nick_name='" + anchor_nick_name + '\'' +
                ", stream_info=" + stream_info +
                '}';
    }
}
