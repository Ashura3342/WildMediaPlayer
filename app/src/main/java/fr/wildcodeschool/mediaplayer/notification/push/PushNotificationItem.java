package fr.wildcodeschool.mediaplayer.notification.push;

import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import fr.wildcodeschool.mediaplayer.R;
import fr.wildcodeschool.mediaplayer.notification.MediaNotificationItem;

public class PushNotificationItem {

    public static MediaNotificationItem getMediaPlayerItem(Context context,
                                                           PendingIntent play,
                                                           PendingIntent pause,
                                                           PendingIntent reset) {

        MediaNotificationItem item = new MediaNotificationItem(context,
                PushNotificationChannel.WildPlayer,
                R.string.notification_title,
                R.string.notification_desc,
                R.drawable.ic_launcher_background);

        item.setPlayAction(new NotificationCompat.Action(
                android.R.drawable.ic_media_play,
                context.getString(R.string.play),
                play));

        item.setPauseAction(new NotificationCompat.Action(
                android.R.drawable.ic_media_pause,
                context.getString(R.string.pause),
                pause
        ));

        item.setResetAction(new NotificationCompat.Action(
                android.R.drawable.ic_media_next,
                context.getString(R.string.reset),
                reset
        ));

        return item;
    }
}
