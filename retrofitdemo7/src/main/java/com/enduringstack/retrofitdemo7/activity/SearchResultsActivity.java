package com.enduringstack.retrofitdemo7.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.SearchRecentSuggestions;

import com.enduringstack.retrofitdemo7.R;
import com.enduringstack.retrofitdemo7.Utils.RequestUtils;
import com.enduringstack.retrofitdemo7.content_provider.SuggestionsContentProvider;
import com.enduringstack.retrofitdemo7.model.Repository;
import com.enduringstack.retrofitdemo7.model.SearchReposResponse;
import com.enduringstack.retrofitdemo7.service.SearchReposService;
import com.enduringstack.retrofitdemo7.service.ServiceFactory;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class SearchResultsActivity extends RepoListActivity {

    Call<SearchReposResponse> request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handleIntent(getIntent());
    }

    @Override
    int getContentView() {
        return R.layout.activity_search_results;
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            SearchRecentSuggestions suggestions =
                    new SearchRecentSuggestions(this,
                            SuggestionsContentProvider.AUTHORITY,
                            SuggestionsContentProvider.MODE);
            suggestions.saveRecentQuery(query, null);

            setTitle(getString(R.string.search_results_query, query));

            fetchRepos(query);
        }
    }

    private void fetchRepos(String query) {
        if (haveNetworkConnection()) {
            SearchReposService service = ServiceFactory.createRetrofitService(SearchReposService.class, SearchReposService.SERVICE_ENDPOINT);
            String headerValue = RequestUtils.getAuthorizationHeader(PreferenceManager.getDefaultSharedPreferences(this));

            showProgress(getString(R.string.searching));
            request = service.getQuery(query, headerValue);
            request.enqueue(new Callback<SearchReposResponse>() {
                @Override
                public void onResponse(Response<SearchReposResponse> response, Retrofit retrofit) {
                    hideProgress();

                    int totalCount = 0;
                    int showingCount = 0;

                    if (response.code() == 200) {
                        for (Repository repo : response.body().getItems()) {
                            addItem(repo);
                        }

                        totalCount = response.body().getTotal_count();
                        showingCount = response.body().getItems().length;
                    }

                    setSubTitle(String.format("Found: %s | Showing: %s", totalCount, showingCount));
                }


                @Override
                public void onFailure(Throwable t) {
                    hideProgress();
                }
            });
        }
    }

    @Override
    void cancelRequest() {
        if (request != null) {
            request.cancel();
        }
    }
}
