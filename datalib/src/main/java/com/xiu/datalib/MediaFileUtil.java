package com.xiu.datalib;

import android.media.MediaMetadataRetriever;

import com.xiu.datalib.common.MediaInfo;

public class MediaFileUtil {

    public static MediaInfo disassmenbleFile(String path){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(path);
        String title =
                retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        String album =
                retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
        String artist =
                retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        return new MediaInfo(title,album,artist,path);
    }

}
