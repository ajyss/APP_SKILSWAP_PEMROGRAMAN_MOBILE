package com.example.projectskillswap;

public class NotificationItem {
    private String title;
    private String description;
    private String time;
    private int iconRes;

    public NotificationItem(String title, String description, String time, int iconRes) {
        this.title = title;
        this.description = description;
        this.time = time;
        this.iconRes = iconRes;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getTime() { return time; }
    public int getIconRes() { return iconRes; }
}
