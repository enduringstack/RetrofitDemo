package com.enduringstack.retrofitdemo7.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.enduringstack.retrofitdemo7.R;
import com.enduringstack.retrofitdemo7.async_task.DownloadAvatarAsyncTask;
import com.enduringstack.retrofitdemo7.listener.AvatarDownloadListener;
import com.enduringstack.retrofitdemo7.model.Repository;
import com.enduringstack.retrofitdemo7.model.Subscription;
import com.enduringstack.retrofitdemo7.service.RepositoryStarringService;
import com.enduringstack.retrofitdemo7.service.RepositoryWatchingService;
import com.enduringstack.retrofitdemo7.service.ServiceFactory;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by sebastianmazur on 19.12.15.
 */
public class RepositoryDetailsViewHandler {
    private Logger logger = Logger.getLogger(RepositoryDetailsViewHandler.class.toString());

    private ProgressBar progressBar;
    private Context context;
    private TextView name;
    private TextView desc;
    private ImageView avatar;
    private ImageView stars;
    private ImageView watchers;
    private TextView starsText;
    private TextView watchText;

    private Repository repository;

    private boolean isStarred;
    private boolean isWatched;

    private View bottomSheet;

    private DownloadAvatarAsyncTask avatarAsyncTask;
    private List<Call<Void>> callsList = new ArrayList<Call<Void>>();

    public RepositoryDetailsViewHandler(View bottomSheet, Context context) {
        this.context = context;
        this.bottomSheet = bottomSheet;

        progressBar = (ProgressBar) bottomSheet.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        name = (TextView) bottomSheet.findViewById(R.id.repo_name_tv);
        desc = (TextView) bottomSheet.findViewById(R.id.repo_desc_tv);
        avatar = (ImageView) bottomSheet.findViewById(R.id.repo_avatar_iv);
        stars = (ImageView) bottomSheet.findViewById(R.id.repo_stars_iv);
        stars.setEnabled(false);
        watchers = (ImageView) bottomSheet.findViewById(R.id.repo_watchers_iv);
        watchers.setEnabled(false);
        starsText = (TextView) bottomSheet.findViewById(R.id.repo_stars_tv);
        watchText = (TextView) bottomSheet.findViewById(R.id.repo_watchers_tv);

        watchers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleWatchStatus();
            }
        });
        stars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleStarStatus();
            }
        });
    }

    /**
     * Reload repository details view
     *
     * @param repo Repository object
     */
    public void reload(Repository repo) {
        repository = repo;
        name.setText(repo.getName());
        desc.setText(repo.getDescription());

        if (Utils.haveNetworkConnection(context)) {
            try {
                avatarAsyncTask = new DownloadAvatarAsyncTask(new AvatarDownloadListener() {
                    @Override
                    public void avatarDownloadStarted() {

                    }

                    @Override
                    public void avatarDownloadSucceeded(Bitmap file) {
                        avatar.setImageBitmap(file);
                        avatarAsyncTask = null;
                    }

                    @Override
                    public void avatarDownloadError() {
                        avatarAsyncTask = null;
                    }
                });
                avatarAsyncTask.execute(repo.getOwner().getAvatar_url());
            } catch (Exception ex) {
                logger.log(Level.WARNING, ex.getMessage());
            }
        }

        loadStarred();
        loadWatched();
    }

    private String getAuthHeaderValue() {
        return RequestUtils.getAuthorizationHeader(PreferenceManager.getDefaultSharedPreferences(context));
    }

    private void loadStarred() {
        RepositoryStarringService starringService = ServiceFactory.createRetrofitService(RepositoryStarringService.class, RepositoryStarringService.SERVICE_ENDPOINT);
        Call<Void> call = starringService.isStarred(repository.getOwner().getLogin(), repository.getName(), getAuthHeaderValue());
        callsList.add(call);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Response<Void> response, Retrofit retrofit) {
                if (response.code() == 204) {
                    isStarred = true;
                    stars.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_black));
                    starsText.setText(R.string.starred_by_you);
                }

                stars.setEnabled(true);
            }

            @Override
            public void onFailure(Throwable t) {
                logger.log(Level.WARNING, t.getMessage());
                starsText.setText(R.string.unknown_status);
            }
        });
    }

    private void toggleStarStatus() {
        setStarsState(false);
        RepositoryStarringService starringService = ServiceFactory.createRetrofitService(RepositoryStarringService.class, RepositoryStarringService.SERVICE_ENDPOINT);
        Call<Void> call;
        if (isStarred) {
            call = starringService.unstarRepo(repository.getOwner().getLogin(), repository.getName(), getAuthHeaderValue());
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Response<Void> response, Retrofit retrofit) {
                    setStarsState(true);
                    if (response.code() == 204) {
                        isStarred = false;
                        stars.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_border_black));
                        starsText.setText(R.string.not_starred_by_you);

                        Snackbar.make(bottomSheet, context.getString(R.string.successfully_unstarred_repo), Snackbar.LENGTH_SHORT)
                                .show();
                    } else {
                        Snackbar.make(bottomSheet, context.getString(R.string.error_unstarring), Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    setStarsState(true);

                }
            });
        } else {
            call = starringService.starRepo(repository.getOwner().getLogin(), repository.getName(), getAuthHeaderValue());
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Response<Void> response, Retrofit retrofit) {
                    setStarsState(true);
                    if (response.code() == 204) {
                        isStarred = true;
                        stars.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_black));
                        starsText.setText(R.string.starred_by_you);

                        Snackbar.make(bottomSheet, context.getString(R.string.successfully_starred), Snackbar.LENGTH_SHORT)
                                .show();
                    } else {
                        Snackbar.make(bottomSheet, context.getString(R.string.error_starring), Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    setStarsState(true);
                    Snackbar.make(bottomSheet, context.getString(R.string.error_starring), Snackbar.LENGTH_SHORT)
                            .show();
                }
            });
        }

        callsList.add(call);
    }

    private void loadWatched() {
        RepositoryWatchingService watchingService = ServiceFactory.createRetrofitService(RepositoryWatchingService.class, RepositoryWatchingService.SERVICE_ENDPOINT);

        Call<Void> call = watchingService.isWatched(repository.getOwner().getLogin(), repository.getName(), getAuthHeaderValue());
        callsList.add(call);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Response<Void> response, Retrofit retrofit) {
                if (response.code() == 200) {
                    isWatched = true;
                    watchers.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_watching_black));
                    watchText.setText(R.string.watched_by_you);
                }

                watchers.setEnabled(true);
            }

            @Override
            public void onFailure(Throwable t) {
                logger.log(Level.WARNING, t.getMessage());
                watchText.setText(R.string.unknown_status);
            }
        });
    }

    private void toggleWatchStatus() {
        setWatchersState(false);
        RepositoryWatchingService watchingService = ServiceFactory.createRetrofitService(RepositoryWatchingService.class, RepositoryWatchingService.SERVICE_ENDPOINT);

        Call<Void> call;
        if (isWatched) {
            call = watchingService.unwatchRepo(repository.getOwner().getLogin(), repository.getName(), getAuthHeaderValue());
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Response<Void> response, Retrofit retrofit) {
                    if (response.code() == 204) {
                        isWatched = false;
                        watchers.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_not_watching_black));
                        watchText.setText(R.string.not_watched_by_you);

                        Snackbar.make(bottomSheet, context.getString(R.string.successfully_unsubscribed), Snackbar.LENGTH_SHORT)
                                .show();
                    } else {
                        Snackbar.make(bottomSheet, context.getString(R.string.error_unsubscribing), Snackbar.LENGTH_SHORT)
                                .show();
                    }

                    setWatchersState(true);
                }

                @Override
                public void onFailure(Throwable t) {
                    setWatchersState(true);
                    Snackbar.make(bottomSheet, context.getString(R.string.error_unsubscribing), Snackbar.LENGTH_SHORT)
                            .show();
                }
            });
        } else {
            call = watchingService.watchRepo(repository.getOwner().getLogin(), repository.getName(), getAuthHeaderValue(), new Subscription().setIgnored(false).setSubscribed(true));
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Response<Void> response, Retrofit retrofit) {
                    if (response.code() == 200) {
                        isWatched = true;
                        watchers.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_watching_black));
                        watchText.setText(R.string.watched_by_you);

                        Snackbar.make(bottomSheet, context.getString(R.string.successfully_subscribed), Snackbar.LENGTH_SHORT)
                                .show();
                    } else {
                        Snackbar.make(bottomSheet, context.getString(R.string.error_subscribing), Snackbar.LENGTH_SHORT)
                                .show();
                    }

                    setWatchersState(true);
                }


                @Override
                public void onFailure(Throwable t) {
                    setWatchersState(true);
                    Snackbar.make(bottomSheet, context.getString(R.string.error_subscribing), Snackbar.LENGTH_SHORT)
                            .show();
                }
            });
        }

        callsList.add(call);
    }

    private void setWatchersState(boolean state) {
        watchers.setEnabled(state);
    }

    private void setStarsState(boolean state) {
        stars.setEnabled(state);
    }

    private void cancelRequests() {
        if (avatarAsyncTask != null) {
            avatarAsyncTask.cancel(true);
            avatarAsyncTask = null;
        }

        for (Call<Void> call : callsList) {
            if (call != null) {
                call.cancel();
            }
        }

        callsList.clear();
    }

    public void reset() {
        cancelRequests();
        avatar.setImageDrawable(context.getResources().getDrawable(android.R.drawable.ic_menu_gallery));
        stars.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_border_black));
        watchers.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_not_watching_black));
        isWatched = false;
        isStarred = false;
        repository = null;
    }
}
