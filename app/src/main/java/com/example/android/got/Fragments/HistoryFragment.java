package com.example.android.got.Fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.app.SearchManager;
import android.content.ContentUris;
import android.content.Context;
import android.support.v4.content.CursorLoader;
import android.content.Intent;
import android.support.v4.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.android.got.MainActivity;
import com.example.android.got.R;
import com.example.android.got.data.contractClass;
import com.example.android.got.data.customAdapter;
import com.example.android.got.infoPage;

public class HistoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    ListView homelist;
    customAdapter adapter;

    public HistoryFragment() {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.hom_menu,menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(false);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.activity_main,container,false);

        homelist = (ListView) root.findViewById(R.id.history);
        final ProgressBar loader = (ProgressBar) root.findViewById(R.id.progress2);
        loader.setVisibility(View.GONE);

        adapter = new customAdapter(getActivity(), null);

        homelist.setAdapter(adapter);

        homelist.setEmptyView(root.findViewById(R.id.emptyView));

        getLoaderManager().initLoader(0,null,this);

        homelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri dataUri = ContentUris.withAppendedId(contractClass.historytable.CONTENT_URI,id);
                Intent intent = new Intent(getActivity(),infoPage.class);
                intent.putExtra("Data",dataUri);
                startActivity(intent);
            }
        });

        return root;

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = new String[]{contractClass.historytable._ID, contractClass.historytable.C_NAME, contractClass.historytable.C_IMAGE};
        return new CursorLoader(getActivity(), contractClass.historytable.CONTENT_URI,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {adapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
