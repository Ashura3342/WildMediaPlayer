package fr.wildcodeschool.mediaplayer.notification;

public class NotificationChannel {

    private String channelId;
    private int name;

    public NotificationChannel(String channelId, int name) {
        this.channelId = channelId;
        this.name = name;
    }

    public String getChannelId() {
        return channelId;
    }

    public int getName() {
        return name;
    }
}
