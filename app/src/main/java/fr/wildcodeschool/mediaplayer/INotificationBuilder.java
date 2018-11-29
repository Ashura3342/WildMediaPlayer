package fr.wildcodeschool.mediaplayer;

import android.app.Notification;
import android.content.Context;

public interface INotificationBuilder<T extends NotificationItem> {
    Notification build(Context context, T item);
}
