package fr.wildcodeschool.mediaplayer;

import android.content.Context;

public class NotificationItem {
    private NotificationChannel channel;
    private String title;
    private String description;
    private int smallIcon;

    public NotificationItem(Context context,
                            NotificationChannel notificationChannel,
                            int title, int description, int smallIcon) {
        this(notificationChannel,
                context.getString(title),
                context.getString(description),
                smallIcon);
    }

    public NotificationItem(NotificationChannel channel,
                            String title, String description,
                            int smallIcon) {
        this.channel = channel;
        this.title = title;
        this.description = description;
        this.smallIcon = smallIcon;
    }

    public NotificationChannel getChannel() {
        return channel;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getSmallIcon() {
        return smallIcon;
    }
}
