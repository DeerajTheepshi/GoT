package com.example.android.got.data;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.android.got.data.contractClass.historytable;
import com.example.android.got.data.contractClass.searchTable;

public class provider extends ContentProvider {

    helperClass dbHelper ;
    private static final int FULL_MATCH = 1;
    private static final int SINGLE_MATCH = 2;
    private static final int SEARCH_TABLE = 3;
    private static final int SEARCH_REQ =4;
    private final static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    private final static String SEARCH_URI = "com.example.android.got.contentProvider";

    static {
        matcher.addURI(contractClass.CONTENT_AUTHORTY,contractClass.PATH1,FULL_MATCH);
        matcher.addURI(contractClass.CONTENT_AUTHORTY,contractClass.PATH1+"/#",SINGLE_MATCH);
        matcher.addURI(contractClass.CONTENT_AUTHORTY,contractClass.PATH2,SEARCH_TABLE);
        matcher.addURI(SEARCH_URI,SearchManager.SUGGEST_URI_PATH_QUERY,SEARCH_REQ);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new helperClass(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int match = matcher.match(uri);
        switch (match) {
            case FULL_MATCH:
                cursor = db.query(historytable.TABLE_NAME, projection, selection, selectionArgs, null, null, historytable._ID+ " DESC ");
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            case SINGLE_MATCH:
                selection = historytable._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(historytable.TABLE_NAME, projection, selection, selectionArgs, null, null, historytable._ID+ " DESC ");
                return cursor;
            case SEARCH_REQ:
                selectionArgs = new String[]{"%"+selectionArgs[0]+"%"};
                projection = new String[]{searchTable._ID,searchTable.NAME,searchTable.DATA};
                cursor = db.query(searchTable.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                return cursor;
             default:
                 return null;
        }

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int match = matcher.match(uri);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (match){
            case FULL_MATCH:
                long newID = db.insert(historytable.TABLE_NAME,null,values);
                if(newID==-1){
                    Toast.makeText(getContext(),"Error Inserting Data into Database",Toast.LENGTH_SHORT).show();
                    return null;
                }
                getContext().getContentResolver().notifyChange(uri,null);
                return  ContentUris.withAppendedId(historytable.CONTENT_URI,newID);

            case SEARCH_TABLE:
                long newID1 = db.insert(searchTable.TABLE_NAME,null,values);
                if(newID1==-1) {
                    Toast.makeText(getContext(), "Data Not entered", Toast.LENGTH_SHORT).show();
                    return null;
                }
                getContext().getContentResolver().notifyChange(uri,null);
                return  ContentUris.withAppendedId(searchTable.CONTENT_URI,newID1);
        }
        return null;

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match = matcher.match(uri);
        switch(match){
            case SINGLE_MATCH:
                selection = historytable._ID+"=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return db.update(historytable.TABLE_NAME,values,selection,selectionArgs);
        }
        return 0;
    }
}
