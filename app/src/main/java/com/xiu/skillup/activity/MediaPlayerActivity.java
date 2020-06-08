package com.xiu.skillup.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.widget.SeekBar;

import com.xiu.common.utils.LogUtil;
import com.xiu.datalib.common.MediaInfo;
import com.xiu.skillup.R;
import com.xiu.skillup.adapter.MediaGalleryAdapter;
import com.xiu.skillup.adapter.SingleTextAdapter;
import com.xiu.skillup.mvp_view.MediaPlayerView;
import com.xiu.skillup.presenter.MediaPlayerPresenter;
import com.xiu.ui.mvp.MvpActivity;
import com.xiu.ui.utils.StackBlurManager;
import com.xiu.ui.view.XButton;
import com.xiu.ui.view.recycler.XHolder;
import com.xiu.ui.view.recycler.gallery.GalleryRecyclerView;
import com.xiu.ui.view.recycler.gallery.GalleryScaleHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

public class MediaPlayerActivity extends MvpActivity<MediaPlayerView, MediaPlayerPresenter>
        implements MediaPlayerView {

    private static final String TAG = "MediaPlayerActivity";

    private static final String ARG_POSITION = "arg_position";
    private static final String ARG_DATA = "arg_data";

    private MediaGalleryAdapter galleryAdapter;
    private GalleryScaleHelper galleryScaleHelper;

    private SingleTextAdapter<MediaInfo> nameAdapter;
    private LinearSnapHelper nameSnapHelper;
    private RecyclerView nameRecyclerView;
    private AppCompatSeekBar seekBar;
    public static void newInstance(Context context, int position,
                                   List<MediaInfo> data) {
        Intent intent = new Intent(context, MediaPlayerActivity.class);
        intent.putExtra(ARG_POSITION, position);
        intent.putParcelableArrayListExtra(ARG_DATA, (ArrayList) data);
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
        int position = getIntent().getIntExtra(ARG_POSITION, 0);
        List<MediaInfo> data =
                (ArrayList) getIntent().getParcelableArrayListExtra(ARG_DATA);
        seekBar = findViewById(R.id.seek);
        getPresenter().setDataSource(position, data);
        getPresenter().playOrPause(getContext());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                LogUtil.i(TAG,"stop seek progress == "+ progress);
                getPresenter().seekTo(progress);
            }
        });
    }


    @Override
    public void onItButton() {
        findViewById(R.id.btn_play).setOnClickListener(v -> getPresenter().playOrPause(getContext()));
        findViewById(R.id.btn_last).setOnClickListener(v -> getPresenter().playLast(getContext()));
        findViewById(R.id.btn_next).setOnClickListener(v -> getPresenter().playNext(getContext()));
    }

    @Override
    public void onInitGallery(int position, List<MediaInfo> data) {
        GalleryRecyclerView recyclerView = findViewById(R.id.recycler_gallery);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        galleryAdapter = new MediaGalleryAdapter(getContext());
        galleryAdapter.addData(data);
        LogUtil.i(TAG, "data size == " + data.size());
        recyclerView.setAdapter(galleryAdapter);
        galleryScaleHelper = new GalleryScaleHelper();
        galleryScaleHelper.setStartPosition(position, galleryAdapter.getData().size());
        galleryScaleHelper.attachToRecyclerView(recyclerView);
        recyclerView.addOnPageChangeListener(position1 -> {
            int realPosition = galleryAdapter.getRealPosition(position1);
            getPresenter().play(getContext(), realPosition);
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(galleryAdapter.getData().get(realPosition).getPath());
            byte[] bitmapData = retriever.getEmbeddedPicture();
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapData, 0,
                    bitmapData.length);
            if (bitmap != null) {
                bitmap = new StackBlurManager(bitmap).process(20);
                findImageViewById(R.id.image_background).setImageBitmap(bitmap);
            }
            nameAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onInitName(int position, List<MediaInfo> data) {
        nameRecyclerView = findViewById(R.id.recycler_list);
        nameRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        nameAdapter = new SingleTextAdapter<MediaInfo>(getContext()) {

            @Override
            public int[] setOnClick() {
                return new int[]{R.id.item_text};
            }

            @Override
            public void onBindViewHolder(@NonNull XHolder holder, int position, MediaInfo data) {
                super.onBindViewHolder(holder, position, data);
                XButton button = holder.findButtonById(R.id.item_text);
                button.setTextSafe(data.getTitle());
                if (getPresenter().getPosition() == position) {
                    button.setTextColor(getResources().getColor(R.color.black));
                } else {
                    button.setTextColor(getResources().getColor(R.color.gray));
                }
            }

            @Override
            public boolean onClick(int id, int position, MediaInfo data) {
                getPresenter().play(getContext(), position);
                galleryScaleHelper.setPosition(position, getItemCount());
                return false;
            }
        };
        nameAdapter.addData(data);
        nameRecyclerView.setAdapter(nameAdapter);
        nameSnapHelper = new LinearSnapHelper();
        nameSnapHelper.attachToRecyclerView(nameRecyclerView);
    }



    @Override
    public void onPageChanged(int position) {
        LogUtil.i(TAG,"onPageChanged == "+position);
        galleryScaleHelper.setPosition(position, nameAdapter.getItemCount());
        nameAdapter.notifyDataSetChanged();
        nameRecyclerView.smoothScrollToPosition(position);
    }

    @Override
    public void onChangePlayButtonText(String text) {
        findButtonViewById(R.id.btn_play).setTextSafe(text);
    }

    @Override
    public void onSeekChanged(int position, int duration) {
        boolean enable = !(position == 0 && duration == 0);
        if(enable){
            seekBar.setMax(duration);
            seekBar.setProgress(position);
        }
    }

    @NonNull
    @Override
    public MediaPlayerPresenter createPresenter() {
        return new MediaPlayerPresenter();
    }

    @Override
    protected void onDestroy() {
        getPresenter().unBindMediaService(getContext());
        super.onDestroy();

    }
}
