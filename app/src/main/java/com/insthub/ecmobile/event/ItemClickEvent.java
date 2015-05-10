package com.insthub.ecmobile.event;

/**
 * Created by Administrator on 2015/2/4.
 */
public class ItemClickEvent {
    private int position;
    public ItemClickEvent(int position){
        this.position =position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
