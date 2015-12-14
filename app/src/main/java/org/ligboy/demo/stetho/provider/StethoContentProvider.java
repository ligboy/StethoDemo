package org.ligboy.demo.stetho.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

public class StethoContentProvider extends ContentProvider {

    private static final int USER = 100;
    private static final int USER_ID = 101;

    private StethoSqliteHelper mSqliteHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static
    {
        sUriMatcher.addURI(StethoContract.CONTENT_AUTHORITY, "users", USER);
        sUriMatcher.addURI(StethoContract.CONTENT_AUTHORITY, "users/#", USER_ID);

    }
    public StethoContentProvider() {
    }

    @Override
    public boolean onCreate() {
        mSqliteHelper = new StethoSqliteHelper(getContext());
        return false;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        int match = sUriMatcher.match(uri);
        switch (match) {
            case USER:
                return StethoContract.Users.CONTENT_TYPE;
            case USER_ID:
                return StethoContract.Users.CONTENT_ITEM_TYPE;
            default:
                return null;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int result = 0;
        int match = sUriMatcher.match(uri);
        final SQLiteDatabase database = mSqliteHelper.getWritableDatabase();
        switch (match) {
            case USER:
                result = database.delete(StethoContract.TABLE_USERS, selection, selectionArgs);
                break;
            case USER_ID:
                int userId = StethoContract.Users.getUserId(uri);
                if (userId > 0) {
                    result = database.delete(StethoContract.TABLE_USERS, "_id=?", new String[] {String.valueOf(userId)});
                }
                break;
        }
        if (result > 0) {
            notifyChange(uri);
        }
        return result;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri resultUri = null;
        int match = sUriMatcher.match(uri);
        final SQLiteDatabase database = mSqliteHelper.getWritableDatabase();
        switch (match) {
            case USER:
                long insert = database.insert(StethoContract.TABLE_USERS, null, values);
                if (insert >= 0) {
                    resultUri = StethoContract.Users.buildIdUri((int) insert);
                    notifyChange(uri);
                }
                break;

        }
        return resultUri;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        int match = sUriMatcher.match(uri);
        final SQLiteDatabase database = mSqliteHelper.getReadableDatabase();
        switch (match) {
            case USER:
                cursor = database.query(StethoContract.TABLE_USERS, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case USER_ID:
                int profileId = StethoContract.Users.getUserId(uri);
                if (profileId > 0) {
                    cursor = database.query(StethoContract.TABLE_USERS, projection, "_id=?", new String[]{String.valueOf(profileId)}, null, null, sortOrder);
                }
                break;
        }
        if (cursor != null) {
            //noinspection ConstantConditions
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int result = 0;
        int match = sUriMatcher.match(uri);
        final SQLiteDatabase database = mSqliteHelper.getWritableDatabase();
        switch (match) {
            case USER:
                result = database.update(StethoContract.TABLE_USERS, values, selection, selectionArgs);
                break;
            case USER_ID:
                int profileId = StethoContract.Users.getUserId(uri);
                if (profileId > 0) {
                    result = database.update(StethoContract.TABLE_USERS, values, "_id=?",  new String[]{String.valueOf(profileId)});
                }
                break;
        }
        if (result > 0) {
            notifyChange(uri);
        }
        return result;
    }

    /**
     * Notifies the system that the given {@code uri} data has changed.
     * <p/>
     * We only notify changes if the uri wasn't called by the sync adapter, to avoid issuing a large
     * amount of notifications while doing a sync.
     * @param uri
     */
    private void notifyChange(Uri uri) {
        if (!StethoContract.isUriCalledFromSyncAdapter(uri)) {
            Context context = getContext();
            //noinspection ConstantConditions
            context.getContentResolver().notifyChange(uri, null);
        }
    }
}
