package com.xiu.photopicker.activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.functions.Consumer;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xiu.common.CommonConfig;
import com.xiu.common.utils.LogUtil;
import com.xiu.datalib.image.Image;
import com.xiu.photopicker.R;
import com.xiu.photopicker.adapter.AlbumSelectedAdapter;
import com.xiu.photopicker.mvp_view.AlbumPickerView;
import com.xiu.photopicker.presenter.AlbumPickerPresenter;
import com.xiu.ui.base.BaseActivity;
import com.xiu.ui.mvp.MvpActivity;

import java.util.ArrayList;
import java.util.List;

public class AlbumPickerActivity extends MvpActivity<AlbumPickerView,
        AlbumPickerPresenter> implements AlbumPickerView {

    private static final String TAG = "AlbumPickerActivity";

    public static final int REQUEST_PHOTO_PICKER = CommonConfig.REQUEST.REQUEST_ALBUM_PICKER;
    public static final String RESULT_SELECTED_IMAGE = "result_selected_image";

    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private AlbumSelectedAdapter adapter;

    private int max = 0;
    private int current = 0;
    private static final String ARG_MAX_COUNT = "arg_max_count";
    private static final String ARG_CURRENT_COUNT = "arg_current_count";


    public static void newSingleInstnce(BaseActivity activity){

    }

    public static void newInstance(BaseActivity activity) {
        newInstance(activity, 0, 9);
    }

    public static void newInstance(final BaseActivity activity, int current,
                                   int max) {
        new RxPermissions(activity).request(PERMISSIONS).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean permission) throws Exception {
                LogUtil.i(TAG, "permission == " + permission);
                if (permission) {
                    Intent intent = new Intent(activity, AlbumPickerActivity.class);
                    intent.putExtra(ARG_MAX_COUNT, max);
                    intent.putExtra(ARG_CURRENT_COUNT, current);
                    activity.startActivityForResult(intent, REQUEST_PHOTO_PICKER);
                }
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        max = getIntent().getIntExtra(ARG_MAX_COUNT, 0);
        current = getIntent().getIntExtra(ARG_CURRENT_COUNT, 0);
        if (max <= 0 || max > 9) {
            Toast.makeText(getContext(), "最大选取数量错误", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if (current < 0 || current > max) {
            Toast.makeText(getContext(), "当前选取数量错误", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setContentView(R.layout.activity_photo_picker);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        setTitle(getResources().getString(R.string.activity_photo_picker));
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        int width = getResources().getDisplayMetrics().widthPixels / 3;
        adapter = new AlbumSelectedAdapter(getContext(), width, max);
        adapter.setOnItemClickLister((id, position, data) -> {
            current = adapter.getSelectedCount();
            invalidateOptionsMenu();
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.loadImage(this);
    }

    @Override
    public void onStartLoad() {
        //TODO show loading dialog
    }

    @Override
    public void onLoaded(List<Image> data) {
        adapter.addData(data);
    }

    @Override
    public void onLoadEmpty() {
        //TODO show empty view
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_text, menu);
        getMenuInflater().inflate(R.menu.menu_complete, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.getItem(0);
        item.setTitle("(" + current + "/" + max + ")");
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_complete){
            int count = adapter.getSelectedCount();
            if(count > 0){
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra(RESULT_SELECTED_IMAGE,
                        adapter.getSelected());
                setResult(RESULT_OK,intent);
            }
            finish();

        }
        return true;
    }

    @NonNull
    @Override
    public AlbumPickerPresenter createPresenter() {
        return new AlbumPickerPresenter();
    }

    public static boolean hasData(int requestCode,int resultCode){
        return requestCode == REQUEST_PHOTO_PICKER && resultCode == RESULT_OK;
    }

    public static ArrayList<Image> getImageData(Intent intent){
        return intent.getParcelableArrayListExtra(RESULT_SELECTED_IMAGE);
    }
}
