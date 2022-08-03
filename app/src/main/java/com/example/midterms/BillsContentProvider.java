package com.example.midterms;


import static com.example.midterms.Bill.*;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

// TODO implement methods of ContentProvider
//  Tip: Copy and paste the NotesContentProvider here and change "notes" to "bills", etc

public class BillsContentProvider extends ContentProvider {
    public static final Uri CONTENT_URI =
            Uri.parse("content://com.example.notesserato.notesprovider/notes");
    public static final int ALL_ROWS = 1;
    public static final int SINGLE_ROW = 2;
    private BillsOpenHelper helper;
    private static final UriMatcher matcher;

    static {
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI("com.example.notesserato.notesprovider", "notes", ALL_ROWS);
        matcher.addURI("com.example.notesserato.notesprovider", "notes/#", SINGLE_ROW);
    }

    @Override
    public boolean onCreate() {
        helper = new BillsOpenHelper(getContext(),BillsOpenHelper.DATABASE_NAME,
                null,BillsOpenHelper.DATABASE_VERSION);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        SQLiteDatabase db = helper.getWritableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        switch (matcher.match(uri)){
            case SINGLE_ROW:
                String rowID = uri.getPathSegments().get(1);
                builder.appendWhere(KEY_ID + "=" + rowID);
            default:
                break;
        }
        builder.setTables(BillsOpenHelper.DATABASE_TABLE);
        Cursor cursor = builder.query(db, strings, s, strings1,null,null, s1);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (matcher.match(uri)) {
            case SINGLE_ROW:
                return "vnd.android.cursor.item/vnd.example.notes";
            case ALL_ROWS:
                return "vnd.android.cursor.dir/vnd.example.notes";
            default:
                throw new IllegalArgumentException("Unsupported URI" + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String nullColumnHack = null;
        long id = db.insert(BillsOpenHelper.DATABASE_TABLE, nullColumnHack, contentValues);
        if (id > -1){
            Uri inserted = ContentUris.withAppendedId(CONTENT_URI,id);
            getContext().getContentResolver().notifyChange(inserted,null);
            return inserted;
        }
        else {
            return null;
        }
    }
    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase db = helper.getWritableDatabase();
        switch (matcher.match(uri)){
            case SINGLE_ROW:
                String rowID = uri.getPathSegments().get(1);
                String row = KEY_ID + "=" + rowID;
                if (!TextUtils.isEmpty(s)){
                    row += " AND ( " + s + ")";
                }
                s = row;
            default:
                break;
        }
        if (s == null) {
            s = "1";
        }
        int deleteCount = db.delete(BillsOpenHelper.DATABASE_TABLE, s, strings);
        getContext().getContentResolver().notifyChange(uri,null);
        return deleteCount;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase db = helper.getWritableDatabase();
        switch (matcher.match(uri)){
            case SINGLE_ROW:
                String rowID = uri.getPathSegments().get(1);
                String row = KEY_ID + "=" + rowID;
                if (!TextUtils.isEmpty(s)){
                    row += " AND ( " + s + ")";
                }
                s = row;
            default:
                break;
        }
        int updateCount = db.update(BillsOpenHelper.DATABASE_TABLE, contentValues, s, strings);
        getContext().getContentResolver().notifyChange(uri,null);
        return updateCount;
    }
}
