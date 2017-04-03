package com.enduringstack.retrofitdemo7.listener;

import android.graphics.Bitmap;

/**
 * Created by sebastianmazur on 19.12.15.
 */
public interface AvatarDownloadListener {
    void avatarDownloadStarted();

    void avatarDownloadSucceeded(Bitmap file);

    void avatarDownloadError();
}
