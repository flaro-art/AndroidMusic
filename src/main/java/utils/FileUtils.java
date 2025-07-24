package utils;

import static android.util.Log.e;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.pojo.PlayableMusicFile;


import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static List<PlayableMusicFile> loadMusicWithMediaStore(Context context) {
        List<PlayableMusicFile> musicFiles = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA
        };
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(uri, projection, null, null, null);
        Log.d("MusicFile", String.valueOf(cursor.getCount()));
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Log.d("MusicFile", "开始读取文件");
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                long duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                Uri musicUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
                Log.d("MusicFile", "文件ID:"+id+"时间："+duration+"文件名: " + name + " | 路径: " + path);
                PlayableMusicFile playableMusicFile = new PlayableMusicFile(name, path, duration, musicUri);
                musicFiles.add(playableMusicFile);
            }
        } else {
            Log.d("MusicFile", "未找到音频文件");
        }
        if (cursor != null) {
            cursor.close();
        }
        return musicFiles;
    }
}
