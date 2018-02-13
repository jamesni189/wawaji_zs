package com.ydd.pockettoycatcherrwz.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jia on 17/11/4.
 */

public class Doll implements Parcelable{

    /**
     * 娃娃图片地址
     */
    public String img;


    /**
     * 娃娃名称
     */
    public String name;

    /**
     * 娃娃数量
     */
    public int num;
    public int selectNum;

    public int  productId;
    public int  id;
    public int  diamonds; //单个产品的兑换钻石数


    public boolean isChoosed;

    protected Doll(Parcel in) {
        img = in.readString();
        name = in.readString();
        num = in.readInt();
        selectNum = in.readInt();
        productId = in.readInt();
        id = in.readInt();
        diamonds = in.readInt();
        isChoosed = in.readByte() != 0;
    }

    public static final Creator<Doll> CREATOR = new Creator<Doll>() {
        @Override
        public Doll createFromParcel(Parcel in) {
            return new Doll(in);
        }

        @Override
        public Doll[] newArray(int size) {
            return new Doll[size];
        }
    };

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(img);
        dest.writeString(name);
        dest.writeInt(num);
        dest.writeInt(selectNum);
        dest.writeInt(productId);
        dest.writeInt(id);
        dest.writeInt(diamonds);
        dest.writeByte((byte) (isChoosed ? 1 : 0));
    }
}
