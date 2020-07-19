package com.route.islami.player

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.route.islami.MyApplication
import com.route.islami.R

class PlayerService : Service() {


    var mediaPlayer = MediaPlayer();

    override fun onCreate() {
        super.onCreate()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val urlToPlay = intent?.getStringExtra("url");
        val name = intent?.getStringExtra("name");
        val action = intent?.getStringExtra("action");
        if (urlToPlay != null && name != null)
            startMediaPlayer(urlToPlay, name)

        if (action != null) {
            Log.e("action", action)

            if (action.equals(PLAY_ACTION)) {
                PlayPauseMediaPlayer()
            } else if (action.equals((STOP_ACTION))) {
                stopMediaPlayer()
            }

        }
        return START_NOT_STICKY
    }

    var name: String = "";

    private fun PlayPauseMediaPlayer() {
        Log.e("action", "play pause")
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        } else if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
        updateNotification();
        //    createNotificationForMediaPlayer("stopped");
    }

    private fun updateNotification() {
        val defaultView = RemoteViews(packageName, R.layout.notification_default_view);
        defaultView.setTextViewText(R.id.title, "Islami App Radio")
        defaultView.setTextViewText(R.id.desc, name)
        defaultView.setImageViewResource(
            R.id.play,
            if (mediaPlayer.isPlaying) R.drawable.ic_stop else R.drawable.ic_play
        )
        defaultView.setOnClickPendingIntent(R.id.play, getPlayButtonPendingIntent())
        defaultView.setOnClickPendingIntent(R.id.stop, getStopButtonPendingIntent())

        var builder =
            NotificationCompat.Builder(this, MyApplication.RADIO_PLAYER_NOTIFCATION_CHANNEL)
                .setSmallIcon(R.drawable.ic_notification)
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(defaultView)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setDefaults(0)
                .setSound(null)

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager;
        notificationManager.notify(RADIO_PLAYER_NOTIFICATION_ID, builder.build())

    }

    fun startMediaPlayer(url: String, name: String) {
        pauseMediaPlayer()
        this.name = name
        mediaPlayer = MediaPlayer();
        mediaPlayer.setDataSource(this, Uri.parse(url))
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener {
            it.start();
        }
        createNotificationForMediaPlayer(name);
    }

    val RADIO_PLAYER_NOTIFICATION_ID = 20;
    private fun createNotificationForMediaPlayer(name: String) {
        /*  var builder = NotificationCompat.Builder(this, MyApplication.RADIO_PLAYER_NOTIFCATION_CHANNEL)
              .setSmallIcon(R.drawable.ic_notification)
              .setContentTitle("Islami App Radio")
              .setContentText(name)
              .setPriority(NotificationCompat.PRIORITY_DEFAULT)
  */

        val defaultView = RemoteViews(packageName, R.layout.notification_default_view);
        defaultView.setTextViewText(R.id.title, "Islami App Radio")
        defaultView.setTextViewText(R.id.desc, name)
        defaultView.setImageViewResource(
            R.id.play,
            if (mediaPlayer.isPlaying) R.drawable.ic_stop else R.drawable.ic_play
        )

        defaultView.setOnClickPendingIntent(R.id.play, getPlayButtonPendingIntent())
        defaultView.setOnClickPendingIntent(R.id.stop, getStopButtonPendingIntent())

        var builder =
            NotificationCompat.Builder(this, MyApplication.RADIO_PLAYER_NOTIFCATION_CHANNEL)
                .setSmallIcon(R.drawable.ic_notification)
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(defaultView)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setSound(null)

        startForeground(RADIO_PLAYER_NOTIFICATION_ID, builder.build())


//        val notificationManager:NotificationManager =
        //          getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager;
        //  notificationManager.notify(RADIO_PLAYER_NOTIFICATION_ID,builder.build())
    }

    val PLAY_ACTION = "play"
    val STOP_ACTION = "stop"
    private fun getPlayButtonPendingIntent(): PendingIntent {
        val intent = Intent(this, PlayerService::class.java)
        intent.putExtra("action", PLAY_ACTION)
        val pendingIntent = PendingIntent.getService(
            this,
            12, intent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        return pendingIntent;

    }

    private fun getStopButtonPendingIntent(): PendingIntent {
        val intent = Intent(this, PlayerService::class.java)
        intent.putExtra("action", STOP_ACTION)
        val pendingIntent = PendingIntent.getService(
            this,
            0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        return pendingIntent;

    }

    fun pauseMediaPlayer() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            //  mediaPlayer.reset()
        }
    }

    fun stopMediaPlayer() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.reset()
        }
        stopForeground(true)
        stopSelf()
    }

    val myBinder = MyBinder()
    override fun onBind(intent: Intent?): IBinder? {
        return myBinder
    }

    inner class MyBinder : Binder() {
        public fun getService(): PlayerService {
            return this@PlayerService
        }
    }
}