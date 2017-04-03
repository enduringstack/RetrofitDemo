package com.enduringstack.retrofitdemo7.content_provider;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by sebastianmazur on 18.12.15.
 */
public class SuggestionsContentProvider extends SearchRecentSuggestionsProvider {

    public static final String AUTHORITY = "pl.sebastianmazur.materialhub.suggestions";

    public static final int MODE = DATABASE_MODE_QUERIES;

    public SuggestionsContentProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
