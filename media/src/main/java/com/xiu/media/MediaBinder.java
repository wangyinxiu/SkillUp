package com.xiu.media;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;

import com.xiu.common.utils.LogUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class MediaBinder extends Binder implements
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnPreparedListener,
        MediaAudioManager.OnAudioFocusChangeListener,
        MediaPlayer.OnCompletionListener{

    private static final String TAG = "MediaBinder";

    private static final int IDEL = 0;
    private static final int PLAYING = 1;
    private static final int PAUSE = 2;
    private static final int LOSS_FOCUS = 3;
    public static final int STATE_PLAY = 10;
    public static final int STATE_STOP = 11;
    private static final int STATE_PALY_COMPLETE = 12;
    private static final int SEEK_BAR_CHANGED = 20;
    private static final int SEEK_COMPLETE = 21;


    private MediaPlayer player;
    private MediaAudioManager manager;
    private String path;
    private int last = IDEL;
    private List<OnMediaBinderListener> listeners;
    private int curPosition = 0;
    private int duration = 0;
    private Disposable disposable;


    public MediaBinder(Context context) {
        player = new MediaPlayer();
        player.setOnErrorListener(this);
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        manager = MediaAudioManager.MediaAudioManagerHolder.getMediaAudioManager(context, AudioManager.STREAM_MUSIC);
        manager.addOnMediaAudioManagerListener(this);
        listeners = new ArrayList<>();
    }

    public void start(String path) {
        LogUtil.i(TAG,"last == " + last);
        this.path = path;

        MediaBinder.this.notify(SEEK_BAR_CHANGED,0,0);
        if(manager.hasFocus()){
            if(last == PAUSE){
                player.start();
                notify(STATE_PLAY,curPosition,duration);
            }else {
                try {
                    player.reset();
                    curPosition= 0;
                    MediaBinder.this.notify(SEEK_BAR_CHANGED,0,duration);
                    player.setDataSource(new File(path).getCanonicalPath());
                    player.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            last = PLAYING;
        }else {
//            LogUtil.i(TAG,"start request audio focus");
            last = PLAYING;
            manager.requestAudioFocus();
        }

    }

    public void pause() {
        LogUtil.i(TAG,"pause");
        if (player.isPlaying()) {
            last = PAUSE;
            player.pause();
        }
        notify(STATE_STOP,0,0);
    }

    public void stop() {
        last = IDEL;
        player.stop();
        player.reset();
    }

    public void release() {
        last = IDEL;
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


    @Override
    public void onCompletion(MediaPlayer mp) {
        if(manager.hasFocus()){
            notify(STATE_PALY_COMPLETE,0,0);
        }
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

    public void seekTo(int progress){
        if(!player.isPlaying()){
            player.start();
        }
        duration = player.getDuration();
        curPosition = progress/1000*1000;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            player.seekTo(progress,MediaPlayer.SEEK_CLOSEST);
        }else {
            player.seekTo(progress);
        }
    }


    @Override
    public void onFocusChanged(boolean focus) {
        LogUtil.i(TAG,"focus == "+ focus);
        if (focus) {
            try {
                if (last == PLAYING) {
                    player.reset();
                    player.setDataSource(path);
                    player.prepare();
                }else if(last == PAUSE) {
                    player.start();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (player.isPlaying()) {
                last = LOSS_FOCUS;
                player.pause();
                notify(STATE_STOP,0,0);
            } else {
                last = IDEL;
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
                startSeek();
                for (OnMediaBinderListener listener : listeners) {
                    listener.onPlayStateChanged(STATE_PLAY);
                }
                LogUtil.i(TAG,"notify : "+what+" , "+ arg1+" ,"+arg2);
                break;
            case STATE_STOP:
                stopSeek();
                for (OnMediaBinderListener listener : listeners) {
                    listener.onPlayStateChanged(STATE_STOP);
                }
                break;
            case SEEK_BAR_CHANGED:
                for (OnMediaBinderListener listener : listeners) {
                    listener.onSeekBarChanged(arg1, arg2);
                }
                break;
            case STATE_PALY_COMPLETE:
                for (OnMediaBinderListener listener : listeners) {
                    listener.onComplete();
                }
                break;
            default:

                break;
        }
    }

    private void startSeek(){
        LogUtil.i(TAG,"startSeek");
        Observable
                .interval(10,TimeUnit.MILLISECONDS)
                .takeWhile(aLong -> {
                    curPosition += 10;
                    return isPlaying();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    if(curPosition%1000 == 0 && isPlaying()){
                        MediaBinder.this.notify(SEEK_BAR_CHANGED,curPosition,duration);
                    }
                });
    }

    private void stopSeek(){
        if(disposable != null && !disposable.isDisposed()){
            disposable.dispose();
            disposable = null;
        }
    }


    public interface OnMediaBinderListener {
        void onPlayStateChanged(int state);

        void onComplete();

        void onSeekBarChanged(int progress, int all);
    }
}
