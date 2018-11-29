package fr.wildcodeschool.mediaplayer;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

public class MediaNotificationBuilder<I extends MediaNotificationItem>
        implements INotificationBuilder<I> {

    @Override
    public Notification build(Context context, I item) {
        return new NotificationCompat.Builder(context, item.getChannel().getChannelId())
                .setContentTitle(item.getTitle())
                .setSmallIcon(item.getSmallIcon())
                .setContentText(item.getDescription())
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                .addAction(item.getPlayAction())
                .addAction(item.getPauseAction())
                .addAction(item.getResetAction())
                .build();
    }
}
