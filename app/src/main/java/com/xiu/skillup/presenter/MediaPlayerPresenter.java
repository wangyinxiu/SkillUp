package com.xiu.skillup.presenter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.xiu.common.utils.LogUtil;
import com.xiu.datalib.common.MediaInfo;
import com.xiu.media.MediaBinder;
import com.xiu.media.MediaService;
import com.xiu.skillup.mvp_view.MediaPlayerView;
import com.xiu.ui.mvp.MvpBasePresenter;

import java.util.List;

public class MediaPlayerPresenter extends MvpBasePresenter<MediaPlayerView>
        implements ServiceConnection, MediaBinder.OnMediaBinderListener {

    private static final String TEXT_PLAY = "播放";
    private static final String TEXT_PAUSE = "暂停";

    private MediaBinder mediaBinder;
    private List<MediaInfo> data;
    private int position;

    private int state = MediaBinder.STATE_STOP;
    private boolean isSeekbarToucn = false;

    public void setDataSource(int position ,List<MediaInfo> data){
        this.position = position;
        this.data = data;
        getView().onItButton();
        getView().onInitGallery(position,data);
        getView().onInitName(position,data);
    }

    public void playOrPause(Context context) {
        LogUtil.i(getClass().getSimpleName(), "playOrPause state == " + state);
        if (state == MediaBinder.STATE_STOP) {
            play(context);
        } else {
            mediaBinder.pause();
        }
    }

    public void play(Context context,int position){
        if(this.position == position && state == MediaBinder.STATE_PLAY){
            return;
        }
        this.position = position;
        play(context);
    }

    public void seekTo(int position){
        if(mediaBinder!=null){
            mediaBinder.seekTo(position);
        }
    }

    public void playLast(Context context){
        if(--position <= 0){
            position = data.size()-1;
        }
        getView().onPageChanged(position);
        play(context);
    }

    public void playNext(Context context){
        if(++position >= data.size()){
            position = 0;
        }
        getView().onPageChanged(position);
        play(context);
    }

    private void play(Context context){
        if (mediaBinder == null) {
            initService(context);
        } else {
            mediaBinder.start(data.get(position).getPath());
        }
    }

    private void initService(Context context) {
        Intent intent = new Intent(context, MediaService.class);
        context.startService(intent);
        context.bindService(intent, this, Context.BIND_AUTO_CREATE);
    }


    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mediaBinder = (MediaBinder) service;
        mediaBinder.addOnMediaBinderListener(this);
        mediaBinder.start(data.get(position).getPath());
    }

    public void unBindMediaService(Context context){
        mediaBinder.removeOnMediaBinderListener(this);
        context.unbindService(this);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mediaBinder = null;
    }

    @Override
    public void onPlayStateChanged(int state) {
        LogUtil.i(getClass().getSimpleName(), "onPlayStateChanged == " + state);
        this.state = state;
        if (isViewAttached()) {
            if (state == MediaBinder.STATE_PLAY) {
                getView().onChangePlayButtonText(TEXT_PAUSE);
            } else {
                getView().onChangePlayButtonText(TEXT_PLAY);
            }
        }
    }

    @Override
    public void onComplete() {
        playNext(null);
        getView().onSeekChanged(0,0);
    }

    @Override
    public void onSeekBarChanged(int progress, int all) {
        if(!isSeekbarToucn){
            getView().onSeekChanged(progress,all);
        }
    }

    public int getPosition() {
        return position;
    }

    public void setSeekBarTouchState(boolean isTouch){
        this.isSeekbarToucn = isTouch;
    }
}
