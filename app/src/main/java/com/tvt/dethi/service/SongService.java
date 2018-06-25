package com.tvt.dethi.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tvt.dethi.R;
import com.tvt.dethi.activities.MainActivity;
import com.tvt.dethi.model.Song;
import com.tvt.dethi.utils.Const;
import com.tvt.dethi.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SongService extends Service {

    public static boolean IS_SERVICE_RUNNING = false;
    private MediaPlayer mediaPlayer;
    private NotificationCompat.Builder builder;
    private NotificationManager manager;
    private Notification notification;
    private NotificationChannel notificationChannel;
    private Song song;
    private List<Song> arrSong;
    private int positon ;
    private RemoteViews remoteViews;



    @Override
    public void onCreate() {
        super.onCreate();


    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }


    private void initNotification(int positon) {
    notification=new Notification();
        remoteViews = new RemoteViews(getPackageName(), R.layout.notification_song);

        remoteViews.setTextViewText(R.id.notifi_title, arrSong.get(positon).getTitle());
        remoteViews.setTextViewText(R.id.notifi_artist, arrSong.get(positon).getArtist());
        String url=Const.SOURCE_URL + arrSong.get(positon).getImage();

        Log.d("uri",url);
        Picasso.with(this).load(url).error(R.mipmap.ic_launcher).into(remoteViews,R.id.img,1010,notification);
//        remoteViews.setImageViewUri(R.id.img, myUri);

        Intent intent = new Intent(this, MainActivity.class);
        intent.setAction(Constants.ACTION.MAIN_ACTION);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 123, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notifi,pendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.notifi_title,pendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.notifi_img,pendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.notifi_artist,pendingIntent);



        Intent playIntent =new Intent(this,SongService.class);
        playIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
        PendingIntent playPendingIntent=PendingIntent.getService(this,0,playIntent,0);



        Intent closeIntent = new Intent(this, SongService.class);
        closeIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
        PendingIntent pcloseIntent = PendingIntent.getService(this, 0,
                closeIntent, 0);

        remoteViews.setOnClickPendingIntent(R.id.notifi_pause,playPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.notifi_pause,pcloseIntent);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel("music", "My Music", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
            builder.setChannelId(notificationChannel.getId());
        }


        builder = new NotificationCompat.Builder(this, "channelID")
                .setAutoCancel(false)
                .setSmallIcon(R.drawable.ic_album_white_36dp)
                .setCustomBigContentView(remoteViews)
                .setContentIntent(pendingIntent);
        notification=builder.build();
        manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(1010,notification);
        }

//        startForeground(1010,notification);
    }

    private void initMediaSong(int i) {
        String data=Const.SOURCE_URL+arrSong.get(i).getSource();
        mediaPlayer=new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


        try {
            mediaPlayer.setDataSource(data);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }


        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }

            mediaPlayer.start();



    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        arrSong = intent.getParcelableArrayListExtra("song");
        positon = intent.getIntExtra("position", 0);
        Toast.makeText(this, ""+positon, Toast.LENGTH_SHORT).show();
        Log.d("tien", "posi:" + positon);

        Log.d("tien", "Size:" + arrSong.size());

        if(intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)){
            initNotification(positon);
            remoteViews.setImageViewResource(R.id.notifi_pause,R.drawable.ic_pause_white_48dp);

            initMediaSong(positon);
        } else if (intent.getAction().equals(Constants.ACTION.STOPFOREGROUND_ACTION)){
            stopForeground(true);
            stopSelf();
            remoteViews.setImageViewResource(R.id.notifi_pause,R.drawable.ic_play_arrow_white_48dp);

        }



        return SongService.START_STICKY;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }




}
