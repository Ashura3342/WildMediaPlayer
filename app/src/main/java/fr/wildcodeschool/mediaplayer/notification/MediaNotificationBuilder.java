package fr.wildcodeschool.mediaplayer.notification;

import android.app.Notification;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class MediaNotificationBuilder<I extends MediaNotificationItem>
        implements INotificationBuilder<I> {

    @Override
    public Notification build(Context context, I item) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, item.getChannel().getChannelId())
                .setContentTitle(item.getTitle())
                .setSmallIcon(item.getSmallIcon())
                .setContentText(item.getDescription())
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL);

        if (item.getPlayAction() != null)
            builder.addAction(item.getPlayAction());
        if (item.getPauseAction() != null)
            builder.addAction(item.getPauseAction());
        if (item.getResetAction() != null)
            builder.addAction(item.getResetAction());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(1)
            );
        }
        return builder.build();
    }
}
