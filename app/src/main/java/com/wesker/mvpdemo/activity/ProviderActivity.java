package com.wesker.mvpdemo.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.wesker.bean.User;
import com.wesker.contentprovider.BookProvider;
import com.wesker.mvpdemo.R;
import com.wesker.mvpdemo.aidl.Book;

public class ProviderActivity extends AppCompatActivity {
    private static final String TAG = "ProviderActivity";
    private static final String baseUri = "content://" + BookProvider.AUTHORITY + "/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        Uri bookUri = Uri.parse(baseUri + "book");
        ContentValues values = new ContentValues();
        values.put("_id", 6);
        values.put("name", "程序设计的艺术");
        getContentResolver().insert(bookUri, values);
        Cursor bookCursor = getContentResolver().query(bookUri, new String[]{"_id", "name"}, null, null, null);
        while (bookCursor.moveToNext()) {
            Book book = new Book();
            book.setId(bookCursor.getInt(0));
            book.setName(bookCursor.getString(1));
            Log.d(TAG, "query book:" + book.toString());
        }
        bookCursor.close();


        Uri userUri = Uri.parse(baseUri + "user");
        Cursor userCursor = getContentResolver().query(userUri, new String[]{"_id", "name", "sex"}, null, null, null);
        while (userCursor.moveToNext()) {
            User user = new User();
            user.set_id(userCursor.getInt(0));
            user.setName(userCursor.getString(1));
            user.setSex(userCursor.getInt(2));
            Log.d(TAG, "query book:" + user.toString());
        }
        userCursor.close();

    }
}
