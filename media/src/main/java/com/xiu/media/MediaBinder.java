package com.xiu.media;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;

import com.xiu.common.utils.LogUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MediaBinder extends Binder implements
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnPreparedListener,
        MediaAudioManager.OnAudioFocusChangeListener {

    private static final String TAG = "MediaBinder";

    private static final int IEDL = 0;
    private static final int PLAYING = 1;
    private static final int PAUSE = 2;
    private static final int LOSS_FOCUS = 3;
    public static final int STATE_PLAY = 10;
    public static final int STATE_STOP = 11;
    private static final int SEEK_BAR_CHANGED = 20;

    private MediaPlayer player;
    private MediaAudioManager manager;
    private String path;
    private int last = IEDL;
    private List<OnMediaBinderListener> listeners;


    public MediaBinder(Context context) {
        player = new MediaPlayer();
        player.setOnErrorListener(this);
        player.setOnPreparedListener(this);
        manager = MediaAudioManager.MediaAudioManagerHolder.getMediaAudioManager(context, AudioManager.STREAM_MUSIC);
        manager.addOnMediaAudioManagerListener(this);
        listeners = new ArrayList<>();
    }

    public void start(String path) {
//        LogUtil.i(TAG,"to start path == "+ path);
        this.path = path;
        last = PLAYING;
        if(manager.hasFocus()){
            try {
                player.reset();
                player.setDataSource(new File(path).getCanonicalPath());
                player.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
//            LogUtil.i(TAG,"start request audio focus");
            manager.requestAudioFocus();
        }
    }

    public void pause() {
        if (player.isPlaying()) {
            last = PAUSE;
            player.pause();
        }
        notify(STATE_STOP,0,0);
    }

    public void stop() {
        last = IEDL;
        player.stop();
        player.reset();
    }

    public void release() {
        last = IEDL;
        if (player.isPlaying()) {
            player.stop();
        }
        player.release();
    }


    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        LogUtil.e(TAG, "onError what == " + what + " , extra == " + extra);
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
//        LogUtil.i(TAG, "onPrepared");
        last = PLAYING;
        player.start();
        notify(STATE_PLAY,0,0);
    }

    public void volumeUp() {
        manager.volumeUp();
    }

    public void volumeDown() {
        manager.volumeDown();
    }

    public boolean isPlaying(){
        return player.isPlaying();
    }


    @Override
    public void onFocusChanged(boolean focus) {
        LogUtil.i(TAG,"focus == "+ focus);
        if (focus) {
            try {
                if (last == PLAYING) {
                    player.reset();
                    player.setDataSource(path);
                }
                player.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (player.isPlaying()) {
                last = LOSS_FOCUS;
                player.pause();
                notify(STATE_STOP,0,0);
            } else {
                last = IEDL;
            }

        }
    }

    public void addOnMediaBinderListener(OnMediaBinderListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeOnMediaBinderListener(OnMediaBinderListener listener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    public void clearOnMediaBinderListener() {
        listeners.clear();
    }

    private void notify(int what, int arg1, int arg2) {
        switch (what) {
            case STATE_PLAY:
                for (OnMediaBinderListener listener : listeners) {
                    listener.onPlayStateChanged(STATE_PLAY);
                }
                break;
            case STATE_STOP:
                for (OnMediaBinderListener listener : listeners) {
                    listener.onPlayStateChanged(STATE_STOP);
                }
                break;
            case SEEK_BAR_CHANGED:
                for (OnMediaBinderListener listener : listeners) {
                    listener.onSeekBarChanged(arg1, arg2);
                }
                break;
            default:

                break;
        }
    }


    public interface OnMediaBinderListener {
        void onPlayStateChanged(int state);

        void onSeekBarChanged(long progress, long all);
    }
}
