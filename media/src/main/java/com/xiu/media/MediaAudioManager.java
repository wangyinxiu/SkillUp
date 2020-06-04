package com.xiu.media;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.os.Build;
import android.util.SparseArray;


import com.xiu.common.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;


public class MediaAudioManager implements AudioManager.OnAudioFocusChangeListener {

    private static final String TAG = "MediaAudioManager";
    private int stream;
    private boolean focus = false;
    private List<OnAudioFocusChangeListener> listeners;
    private AudioManager audioManager;
    private int maxVolume;
    private int minVolume = 0;

    private MediaAudioManager(Context context,int stream) {
        this.stream = stream;
        audioManager = (AudioManager)context.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        listeners = new ArrayList<>();
        getVolumeBorder();
    }

    public void volumeUp(){
        int curVolume = getTargetVolume(true);
        if(curVolume > maxVolume){
            LogUtil.e(TAG,"volume up error,stream == "+stream);
            return;
        }
        if(hasFocus()){
            setStreamVolume(curVolume);
        }else {
            LogUtil.e(TAG,"volume up ,but not have focus,stream == "+stream);
        }

    }

    public void volumeDown(){
        int curVolume = getTargetVolume(false);
        if(curVolume < minVolume){
            LogUtil.e(TAG,"volume down error,stream == "+stream);
            return;
        }
        if(hasFocus()){
            setStreamVolume(curVolume);
        }else {
            LogUtil.e(TAG,"volume down ,but not have focus,stream == "+stream);
        }
    }

    public boolean hasFocus(){
        return focus;
    }

    public void requestAudioFocus(){
        if(focus){
            notify(hasFocus());
            return;
        }
        LogUtil.i(TAG,"to request audio focus");
        int result;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            result = requestAudioFocusO();
        }else {
            result =audioManager.requestAudioFocus(this, stream,
                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        }
        onAudioFocusChange(result);
        LogUtil.i(TAG,"request result == "+ result);
    }
    @TargetApi(Build.VERSION_CODES.O)
    public int requestAudioFocusO(){
        AudioAttributes attributes = new AudioAttributes
                .Builder()
                .setLegacyStreamType(stream)
                .build();

        AudioFocusRequest request = new AudioFocusRequest
                .Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
                .setAudioAttributes(attributes)
                .build();
        return audioManager.requestAudioFocus(request);
    }


    @Override
    public void onAudioFocusChange(int focusChange) {
        if(focusChange == AudioManager.AUDIOFOCUS_GAIN || focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK){
            focus = true;
        }else {
            focus = false;
        }
        notify(focus);
    }

    public void addOnMediaAudioManagerListener(OnAudioFocusChangeListener listener){
        listeners.add(listener);
    }

    public void removeOnMediaAudioManagerListener(OnAudioFocusChangeListener listener){
        if(listeners.contains(listener)){
            listeners.remove(listener);
        }
    }

    public void clearOnMediaAudioManagerListener(){
        listeners.clear();
    }

    public void release(){
        listeners.clear();
        listeners = null;
        audioManager = null;
    }

    private void notify(boolean focus){
        for(OnAudioFocusChangeListener listener : listeners){
            listener.onFocusChanged(focus);
        }
    }

    private int getTargetVolume(boolean up){
        int curVolume = audioManager.getStreamVolume(stream);
        if(up){
            return ++curVolume;
        }else {
            return --curVolume;
        }
    }

    private void getVolumeBorder(){
        maxVolume = audioManager.getStreamMaxVolume(stream);
    }

    private void setStreamVolume(int volume){
        audioManager.setStreamVolume(stream,volume,AudioManager.FLAG_PLAY_SOUND);
    }

    public interface OnAudioFocusChangeListener {
        void onFocusChanged(boolean focus);
    }

    public static class MediaAudioManagerHolder{

        private static SparseArray<MediaAudioManager> cache = null;

        public static MediaAudioManager getMediaAudioManager(Context context,int stream){
            if(cache == null){
                cache = new SparseArray<>();
            }
            MediaAudioManager manager = cache.get(stream);
            if(manager == null){
                manager = new MediaAudioManager(context,stream);
                cache.put(stream,manager);
            }
            return manager;
        }

        public static void clear(){
            MediaAudioManager manager = null;
            for(int i = 0;i<cache.size();i++){
                manager = cache.valueAt(i);
                manager.release();
            }
            cache.clear();
            cache = null;
        }
    }
}
