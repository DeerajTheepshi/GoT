package com.example.android.got.data;

import android.app.SearchManager;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import java.util.HashMap;

public class contractClass implements BaseColumns {
    public  final static String CONTENT_AUTHORTY = "com.example.android.got.contentProvider";
    public final static Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORTY);
    public final static String PATH1 = "history";
    public final static String PATH2 = "search";

    public final static class historytable implements BaseColumns{
        public final static Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH1);

        public final static String TABLE_NAME = "history";
        public final static String _ID = BaseColumns._ID;
        public final static String C_NAME = "Name";
        public final static String C_SPOUSE = "Spouse";
        public final static String C_HOUSE = "House";
        public final static String C_CUL = "Culture";
        public final static String C_TITLES = "Titles";
        public final static String code = "code";
        public final static String C_IMAGE = "Image";
    }

    public final static class searchTable implements BaseColumns{
        public final static Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH2);
        public final static String TABLE_NAME = "search";
        public final static String _ID = BaseColumns._ID;
        public final static String NAME = SearchManager.SUGGEST_COLUMN_TEXT_1;
        public final static String DATA = SearchManager.SUGGEST_COLUMN_INTENT_DATA;
        public HashMap<String,String> map = new HashMap<String, String>();
    }
}
