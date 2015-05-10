package com.insthub.ecmobile.event;

/**
 * Created by Administrator on 2015/2/4.
 */
public class AddressItemClickEvent {
    private String position;
    public AddressItemClickEvent(String position){
        this.position =position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
