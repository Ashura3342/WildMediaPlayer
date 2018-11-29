package fr.wildcodeschool.mediaplayer.notification;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import fr.wildcodeschool.mediaplayer.notification.INotificationBuilder;
import fr.wildcodeschool.mediaplayer.notification.NotificationChannel;
import fr.wildcodeschool.mediaplayer.notification.NotificationItem;

public class PushNotification<I extends NotificationItem> {
    private NotificationManager notificationManager;
    private INotificationBuilder<I> notificationBuilder;

    public PushNotification(NotificationManager notificationManager, INotificationBuilder<I> notificationBuilder) {
        this.notificationManager = notificationManager;
        this.notificationBuilder = notificationBuilder;
    }

    public void show(Context context, int id, I notificationItem) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!channelExist(notificationItem.getChannel().getChannelId()))
                createChannel(context, notificationItem.getChannel());
        }
        notificationManager.notify(id, notificationBuilder.build(context, notificationItem));
    }

    public void cancel(int id) {
        notificationManager.cancel(id);
    }

    public void cancelAll() {
        notificationManager.cancelAll();
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void createChannel(Context context, NotificationChannel channel) {
        String name = context.getString(channel.getName());
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        android.app.NotificationChannel notificationChannel
                = new android.app.NotificationChannel(channel.getChannelId(), name, importance);
        notificationChannel.setShowBadge(true);
        notificationManager.createNotificationChannel(notificationChannel);
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private boolean channelExist(String channelId) {
        return notificationManager.getNotificationChannel(channelId) != null;
    }
}
