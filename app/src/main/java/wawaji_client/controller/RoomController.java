package wawaji_client.controller;


import android.util.Log;


import com.google.gson.Gson;


import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import wawaji_client.ZegoApiManager;
import wawaji_client.entity.RoomInfo;
import wawaji_client.entity.RoomInfoEx;


public class RoomController {

    private static RoomController sInstance;

    private OnUpdateRoomListListener mUpdateRoomListListener;

    /**
     * 线程池.
     */
    private ExecutorService mExecutorService;


    private RoomController() {
        mExecutorService = Executors.newFixedThreadPool(4);
    }

    public static RoomController getInstance() {
        if (sInstance == null) {
            synchronized (RoomController.class) {
                if (sInstance == null) {
                    sInstance = new RoomController();
                }
            }
        }
        return sInstance;
    }

    /**
     * 设置房间列表监听.
     *
     * @param updateRoomListListener
     */
    public void setUpdateRoomListListener(OnUpdateRoomListListener updateRoomListListener) {
        mUpdateRoomListListener = updateRoomListListener;
    }


    /**
     * 获取房间列表.
     */
    public void getRoomList() {
       /* mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                RequestQueue mQueue = Volley.newRequestQueue(PTCApplication.instance);

                long appID = ZegoApiManager.getInstance().getAppID();
                String url = String.format("https://liveroom%d-api.%s/demo/roomlist?appid=%s", appID, "zego.im", appID);

                StringRequest request = new StringRequest(url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Gson gson = new Gson();
                                RoomInfoEx roomInfoEx = gson.fromJson(response, RoomInfoEx.class);
                                Log.i("dqqdfftg", "onResponse: "+roomInfoEx.toString());
                                if(roomInfoEx != null && roomInfoEx.data != null){
                                    if(mUpdateRoomListListener != null){
                                        mUpdateRoomListListener.onUpdateRoomList(roomInfoEx.data.room_list);
                                    }
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                mQueue.add(request);
            }
        });*/
    }


    public interface OnUpdateRoomListListener {
        void onUpdateRoomList(List<RoomInfo> listRoom);
    }
}