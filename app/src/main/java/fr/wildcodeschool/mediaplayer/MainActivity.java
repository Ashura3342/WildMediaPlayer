package fr.wildcodeschool.mediaplayer;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
  // Audio player
  private WildPlayer mPlayer = null;
  // Progress bar
  private SeekBar mSeekbar = null;
  // Seekbar update delay
  private static final int SEEKBAR_DELAY = 1000;
  // Thread used to update the seekbar position
  private final Handler mSeekBarHandler = new Handler();
  private Runnable mSeekBarThread;

  // Application Context is static in order to access it everywhere.
  private static Context appContext;

  private static final String NOTIFICATION_MODE_PLAY = "NOTIFICATION_MODE_PLAY";
  private static final String NOTIFICATION_MODE_PAUSE = "NOTIFICATION_MODE_PAUSE";
  private static final String NOTIFICATION_MODE_RESET = "NOTIFICATION_MODE_RESET";

  private MediaNotificationItem mediaNotificationItem;
  private PushNotification<MediaNotificationItem> pushNotification;
  private static final int notificationID = 1;

  public static Intent getIntent(Context context, String action) {
    Intent intent = getIntent(context);
    intent.setAction(action);
    return intent;
  }

  public static Intent getIntent(Context context) {
    Intent intent = new Intent(context, MainActivity.class);
    return intent;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Initialization of the application context
    MainActivity.appContext = getApplicationContext();

    // Initialization of the wild audio player
    mPlayer = new WildPlayer(this);
    mPlayer.init(R.string.song, new WildOnPlayerListener() {
      @Override
      public void onPrepared(MediaPlayer mp) {
        mSeekbar.setMax(mp.getDuration());
      }

      @Override
      public void onCompletion(MediaPlayer mp) {
        mSeekBarHandler.removeCallbacks(mSeekBarThread);
        mSeekbar.setProgress(0);
      }
    });

    // Initialization of the seekbar
    mSeekbar = findViewById(R.id.seekBar);
    mSeekbar.setOnSeekBarChangeListener(this);

    // Thread used to update the seekbar position according to the audio player
    mSeekBarThread = new Runnable() {
      @Override
      public void run() {
        // Widget should only be manipulated in UI thread
        mSeekbar.post(() -> mSeekbar.setProgress(mPlayer.getCurrentPosition()));
        // Launch a new request
        mSeekBarHandler.postDelayed(this, SEEKBAR_DELAY);
      }
    };

    pushNotification = new PushNotification<>(
            (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE),
            new MediaNotificationBuilder<>()
    );

    Intent iPlay = getIntent(this, NOTIFICATION_MODE_PLAY);
    Intent iPause = getIntent(this, NOTIFICATION_MODE_PAUSE);
    Intent iStop = getIntent(this, NOTIFICATION_MODE_RESET);

    mediaNotificationItem = PushNotificationItem.getMediaPlayerItem(this,
            PendingIntent.getActivity(this, 0, iPlay, PendingIntent.FLAG_UPDATE_CURRENT),
            PendingIntent.getActivity(this, 0, iPause, PendingIntent.FLAG_UPDATE_CURRENT),
            PendingIntent.getActivity(this, 0, iStop, PendingIntent.FLAG_UPDATE_CURRENT));
  }

  @Override
  protected void onPause() {
    pushNotification.show(this, notificationID, mediaNotificationItem);
    super.onPause();
  }

  @Override
  protected void onResume() {
    pushNotification.cancel(notificationID);
    super.onResume();
  }

  @Override
  protected void onDestroy() {
    pushNotification.cancelAll();
    super.onDestroy();
  }

  @Override
  protected void onNewIntent(Intent intent) {
    if (intent.getAction() != null) {
      onNotificationAction(intent.getAction());
    }
    super.onNewIntent(intent);
  }

  protected void onNotificationAction(String action) {
    switch (action) {
      case NOTIFICATION_MODE_PLAY: playMedia(null); break;
      case NOTIFICATION_MODE_PAUSE: pauseMedia(null); break;
      case NOTIFICATION_MODE_RESET: resetMedia(null); break;
      default:
    }
  }

  /**
   * OnSeekBarChangeListener interface method implementation
   * @param seekBar Widget related to the event
   * @param progress Current position on the seekbar
   * @param fromUser Define if it is a user action or a programmatic seekTo
   */
  @Override
  public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
      if (fromUser) {
        mPlayer.seekTo(progress);
      }
  }

  /**
   * OnSeekBarChangeListener interface method implementation
   * @param seekBar Widget related to the event
   */
  @Override
  public void onStartTrackingTouch(SeekBar seekBar) {
    Log.e("Activity", "onStartTrackingTouch");
    // Stop seekBarUpdate here
    mSeekBarHandler.removeCallbacks(mSeekBarThread);
  }

  /**
   * OnSeekBarChangeListener interface method implementation
   * @param seekBar Widget related to the event
   */
  @Override
  public void onStopTrackingTouch(SeekBar seekBar) {
    Log.e("Activity", "onStopTrackingTouch");
    // Restart seekBarUpdate here
    if (null != mPlayer && mPlayer.isPlaying()) {
      mSeekBarHandler.postDelayed(mSeekBarThread, SEEKBAR_DELAY);
    }
  }

  /**
   * On play button click
   * Launch the playback of the media
   */
  public void playMedia(View v) {
    if (null != mPlayer && mPlayer.play()) {
      mSeekBarHandler.postDelayed(mSeekBarThread, SEEKBAR_DELAY);
    }
  }

  /**
   * On pause button click
   * Pause the playback of the media
   */
  public void pauseMedia(View v) {
    if (null != mPlayer && mPlayer.pause()) {
      mSeekBarHandler.removeCallbacks(mSeekBarThread);
    }
  }

  /**
   * On reset button click
   * Stop the playback of the media
   */
  public void resetMedia(View v) {
    if (null != mPlayer && mPlayer.reset()) {
      mSeekbar.setProgress(0);
    }
  }

  /**
   * Application context accessor
   * https://possiblemobile.com/2013/06/context/
   * @return The application context
   */
  public static Context getAppContext() {
    return appContext;
  }
}
