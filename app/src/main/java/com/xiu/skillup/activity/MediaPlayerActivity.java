package com.xiu.skillup.activity;

import androidx.annotation.NonNull;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.xiu.datalib.common.MediaInfo;
import com.xiu.skillup.R;
import com.xiu.skillup.mvp_view.MediaPlayerView;
import com.xiu.skillup.presenter.MediaPlayerPresenter;
import com.xiu.ui.mvp.MvpActivity;

import java.util.ArrayList;
import java.util.List;

public class MediaPlayerActivity extends MvpActivity<MediaPlayerView, MediaPlayerPresenter>
    implements MediaPlayerView{

    private static final String ARG_POSITION = "arg_position";
    private static final String ARG_DATA = "arg_data";

    public static void newInstance(Context context, int position,
                                   List<MediaInfo> data){
        Intent intent = new Intent(context,MediaPlayerActivity.class);
        intent.putExtra(ARG_POSITION,position);
        intent.putParcelableArrayListExtra(ARG_DATA,(ArrayList) data);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        setTitle("音乐播放");
        findViewById(R.id.btn_play).setOnClickListener(v -> getPresenter().playOrPause(getContext()));
        findViewById(R.id.btn_last).setOnClickListener(v -> getPresenter().playLast(getContext()));
        findViewById(R.id.btn_next).setOnClickListener(v -> getPresenter().playNext(getContext()));
        int position = getIntent().getIntExtra(ARG_POSITION,0);
        List<MediaInfo> data =
                (ArrayList)getIntent().getParcelableArrayListExtra(ARG_DATA);
        getPresenter().setDataSource(position,data);
        getPresenter().playOrPause(getContext());
    }

    @Override
    public void onChangePlayButtonText(String text) {
        findButtonViewById(R.id.btn_play).setTextSafe(text);
    }

    @NonNull
    @Override
    public MediaPlayerPresenter createPresenter() {
        return new MediaPlayerPresenter();
    }
}
