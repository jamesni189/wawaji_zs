package wawaji_client.entity;

/**
 * Copyright © 2017 Zego. All rights reserved.
 */

public class RoomInfoEx {
    public int code;
    public RoomList data;
    public String message;

    @Override
    public String toString() {
        return "RoomInfoEx{" +
                "code=" + code +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
