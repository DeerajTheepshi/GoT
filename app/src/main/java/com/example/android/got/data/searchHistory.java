package com.example.android.got.data;

import android.content.SearchRecentSuggestionsProvider;

public class searchHistory extends SearchRecentSuggestionsProvider {
    public final static String CONTENT_URI = "com.example.android.got.searchHistoryProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;
    public searchHistory() {
        setupSuggestions(CONTENT_URI,MODE);
    }
}
