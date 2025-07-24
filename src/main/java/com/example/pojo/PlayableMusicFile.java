package com.example.pojo;

import android.net.Uri;

public class PlayableMusicFile {
    private String displayName;
    private String path;
    private long duration;
    private Uri uri;

    public PlayableMusicFile(String displayName, String path, long duration, Uri uri) {
        this.displayName = displayName;
        this.path = path;
        this.duration = duration;
        this.uri = uri;
    }

    // Getters
    public String getDisplayName() { return displayName; }
    public String getPath() { return path; }
    public long getDuration() { return duration; }
    public Uri getUri() { return uri; }
}
