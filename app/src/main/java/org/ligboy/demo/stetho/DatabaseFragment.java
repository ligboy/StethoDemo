package org.ligboy.demo.stetho;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.AppCompatTextView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.ligboy.demo.stetho.provider.StethoContract;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatabaseFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_DATABASE = 1;

    private ListView mListView;
    private DatabaseAdapter mAdapter;

    public DatabaseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_datebase, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView = (ListView) view.findViewById(R.id.list);

        mAdapter = new DatabaseAdapter(getContext(), null);
        mListView.setAdapter(mAdapter);
        getLoaderManager().initLoader(LOADER_DATABASE, null, this);
        addItem();
    }

    private void addItem() {
        Random random = new Random();
        byte[] randomBytes = new byte[24];
        ContentValues contentValues = new ContentValues(3);
        random.nextBytes(randomBytes);
        contentValues.put(StethoContract.Users.COLUMN_NAME, new String(Base64.encode(randomBytes, Base64.NO_PADDING)));
        random.nextBytes(randomBytes);
        contentValues.put(StethoContract.Users.COLUMN_USERNAME, new String(Base64.encode(randomBytes, Base64.NO_PADDING)));
        random.nextBytes(randomBytes);
        contentValues.put(StethoContract.Users.COLUMN_PASSWORD, new String(Base64.encode(randomBytes, Base64.NO_PADDING)));
        getContext().getContentResolver().insert(StethoContract.Users.CONTENT_URI, contentValues);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_DATABASE:
                return new CursorLoader(getContext(), StethoContract.Users.CONTENT_URI, null, null, null, null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case LOADER_DATABASE:
                mAdapter.swapCursor(data);
                break;
            default:

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case LOADER_DATABASE:
                mAdapter.swapCursor(null);
                break;
            default:

        }
    }

    private static class DatabaseAdapter extends CursorAdapter {

        public DatabaseAdapter(Context context, Cursor c) {
            super(context, c, true);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View view = LayoutInflater.from(context).inflate(R.layout.list_item_database, parent, false);
            new ViewHolder(view);
            return view;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            ViewHolder viewHolder = (ViewHolder) view.getTag();
            viewHolder.idTextView.setText(String.valueOf(getItemId(cursor.getPosition())));
            ViewHolder.bindTextView(viewHolder.nameTextView, cursor, StethoContract.Users.COLUMN_NAME);
            ViewHolder.bindTextView(viewHolder.usernameTextView, cursor, StethoContract.Users.COLUMN_USERNAME);
            ViewHolder.bindTextView(viewHolder.passwordTextView, cursor, StethoContract.Users.COLUMN_PASSWORD);
        }

        private static final class ViewHolder {
            public AppCompatTextView idTextView;
            public AppCompatTextView nameTextView;
            public AppCompatTextView usernameTextView;
            public AppCompatTextView passwordTextView;

            public ViewHolder(View view) {
                idTextView = (AppCompatTextView) view.findViewById(R.id.id);
                nameTextView = (AppCompatTextView) view.findViewById(R.id.name);
                usernameTextView = (AppCompatTextView) view.findViewById(R.id.username);
                passwordTextView = (AppCompatTextView) view.findViewById(R.id.password);
                view.setTag(this);
            }

            public static void bindTextView(TextView textView, Cursor cursor, @NonNull String columnName) {
                int index = cursor.getColumnIndex(columnName);
                if (index > -1) {
                    textView.setText(cursor.getString(index));
                } else {
                    textView.setText(null);
                }
            }
        }
    }
}
