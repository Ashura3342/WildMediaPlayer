package fr.wildcodeschool.mediaplayer.notification;

import android.content.Context;
import android.support.v4.app.NotificationCompat;


public class MediaNotificationItem extends NotificationItem {

    private NotificationCompat.Action playAction;
    private NotificationCompat.Action pauseAction;
    private NotificationCompat.Action resetAction;

    public MediaNotificationItem(Context context, NotificationChannel notificationChannel,
                                 int title, int description, int smallIcon) {
        super(context, notificationChannel, title, description, smallIcon);
    }

    public MediaNotificationItem(NotificationChannel channel, String title, String description, int smallIcon) {
        super(channel, title, description, smallIcon);
    }

    public NotificationCompat.Action getPlayAction() {
        return playAction;
    }

    public void setPlayAction(NotificationCompat.Action playAction) {
        this.playAction = playAction;
    }

    public NotificationCompat.Action getPauseAction() {
        return pauseAction;
    }

    public void setPauseAction(NotificationCompat.Action pauseAction) {
        this.pauseAction = pauseAction;
    }

    public NotificationCompat.Action getResetAction() {
        return resetAction;
    }

    public void setResetAction(NotificationCompat.Action resetAction) {
        this.resetAction = resetAction;
    }
}
