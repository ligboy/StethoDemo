package org.ligboy.demo.stetho.provider;

import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * @author Ligboy.Liu ligboy@gmail.com.
 */
public class StethoContract {
    public static final String CONTENT_AUTHORITY = "org.ligboy.demo.stetho.provider";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String CONTENT_TYPE_APP_BASE = "user.";

    public static final String CONTENT_TYPE_BASE = "vnd.android.cursor.dir/vnd."
            + CONTENT_TYPE_APP_BASE;

    public static final String CONTENT_ITEM_TYPE_BASE = "vnd.android.cursor.item/vnd."
            + CONTENT_TYPE_APP_BASE;


    public static final String PATH_USERS = "users";
    public static final String USERS_PATH_ID = "id";

    public static final String TABLE_USERS = "users";

    public interface UsersColumns extends BaseColumns {
        String COLUMN_USERNAME = "username";
        String COLUMN_PASSWORD = "password";
        String COLUMN_NAME = "name";
    }

    public static class Users implements UsersColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_USERS).build();

        public static final String CONTENT_TYPE_ID = "user";
        public static final String CONTENT_TYPE = makeContentType(CONTENT_TYPE_ID);
        public static final String CONTENT_ITEM_TYPE = makeContentItemType(CONTENT_TYPE_ID);

        public static Uri buildIdUri(@IntRange(from = 1) int id) {
            return CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
        }

        public static int getUserId(@NonNull Uri uri) {
            String idString = uri.getPathSegments().get(1);
            if (!TextUtils.isEmpty(idString) && TextUtils.isDigitsOnly(idString)) {
                return Integer.valueOf(idString);
            } else {
                return 0;
            }
        }
    }

    public static String makeContentType(String id) {
        if (id != null) {
            return CONTENT_TYPE_BASE + id;
        } else {
            return null;
        }
    }

    public static String makeContentItemType(String id) {
        if (id != null) {
            return CONTENT_ITEM_TYPE_BASE + id;
        } else {
            return null;
        }
    }

    private static final String QUERY_PARAMETER_CALLER_IS_SYNC_ADAPTER = "callerIsSyncAdapter";


    public static boolean isUriCalledFromSyncAdapter(Uri uri) {
        return uri.getBooleanQueryParameter(QUERY_PARAMETER_CALLER_IS_SYNC_ADAPTER, false);
    }
}
