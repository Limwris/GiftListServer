package com.nichesoftware.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Kattleya on 30/06/2016.
 */
public class NotificationDto {
    private String body;
    private String title;
    @SerializedName("click_action")
    private String clickAction;
    private String icon;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getClickAction() {
        return clickAction;
    }

    public void setClickAction(String clickAction) {
        this.clickAction = clickAction;
    }
}
