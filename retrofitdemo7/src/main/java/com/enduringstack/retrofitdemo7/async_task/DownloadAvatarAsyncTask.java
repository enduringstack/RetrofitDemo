package com.enduringstack.retrofitdemo7.async_task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.enduringstack.retrofitdemo7.listener.AvatarDownloadListener;

/**
 * Created by sebastianmazur on 19.12.15.
 */
public class DownloadAvatarAsyncTask extends AsyncTask<String, Void, Bitmap> {

    Logger logger = Logger.getLogger(DownloadAvatarAsyncTask.class.toString());

    private AvatarDownloadListener handler;

    /**
     * Download image from specified URL.
     * Image is returned as Bitmap
     *
     * @param handler AvatarDownloadListener to notify
     * @throws Exception if provided listener is null
     */
    public DownloadAvatarAsyncTask(AvatarDownloadListener handler) throws Exception {
        if (handler == null) {
            throw new Exception("Handler can't be null");
        }

        this.handler = handler;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap retVal = null;

        String url = params.length == 1 ? params[0] : "";

        if (!url.isEmpty()) {
            try {
                URI uri = new URI(url);
                HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                retVal = BitmapFactory.decodeStream(input);
                input.close();
                connection.disconnect();
            } catch (Exception ex) {
                logger.log(Level.SEVERE, ex.getMessage());
            }
        } else {
            logger.log(Level.SEVERE, "AVATAR URL IS NULL");
        }

        return retVal;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {
            handler.avatarDownloadSucceeded(bitmap);
        } else {
            handler.avatarDownloadError();
        }
    }
}
