package com.enduringstack.retrofitdemo7.activity;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;

import java.util.List;

import com.enduringstack.retrofitdemo7.R;
import com.enduringstack.retrofitdemo7.Utils.DbHelper;
import com.enduringstack.retrofitdemo7.Utils.RequestUtils;
import com.enduringstack.retrofitdemo7.model.Repository;
import com.enduringstack.retrofitdemo7.service.RepositoryService;
import com.enduringstack.retrofitdemo7.service.ServiceFactory;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends RepoListActivity {

    private Call<Repository[]> request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fetchRepos();
    }

    @Override
    int getContentView() {
        return R.layout.activity_main;
    }

    private void fetchRepos() {
        if (haveNetworkConnection()) {
            RepositoryService service = ServiceFactory.createRetrofitService(RepositoryService.class, RepositoryService.SERVICE_ENDPOINT);

            String headerValue = RequestUtils.getAuthorizationHeader(PreferenceManager.getDefaultSharedPreferences(MainActivity.this));

            showProgress(getString(R.string.fetching_your_repos));

            request = service.getRepos(headerValue);
            request.enqueue(new Callback<Repository[]>() {
                @Override
                public void onResponse(Response<Repository[]> response, Retrofit retrofit) {
                    hideProgress();

                    if (response.code() == 200) {
                        DbHelper.getInstance(MainActivity.this).delete(Repository.TABLE_NAME, null, null);
                        for (Repository repo : response.body()) {
                            addItem(repo);

                            DbHelper.getInstance(MainActivity.this).insert(Repository.TABLE_NAME, Repository.convertModelToInsert(repo));
                        }
                    }

                    setSubTitle(getString(R.string.found_count, response.body() != null ? response.body().length : 0));
                }

                @Override
                public void onFailure(Throwable t) {
                    hideProgress();
                }
            });
        } else {
            Cursor cur = DbHelper.getInstance(MainActivity.this).select(Repository.TABLE_NAME, null, null, null, null);
            List<Repository> list = Repository.convertCursorToModelList(cur);

            for (Repository repo : list) {
                addItem(repo);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean retVal = false;

        if (haveNetworkConnection()) {
            getMenuInflater().inflate(R.menu.menu_main, menu);

            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
            SearchableInfo info = searchManager.getSearchableInfo(new ComponentName(this, SearchResultsActivity.class));
            searchView.setSearchableInfo(info);

            retVal = true;
        }

        return retVal;
    }

    @Override
    void cancelRequest() {
        if (request != null) {
            request.cancel();
        }
    }
}
