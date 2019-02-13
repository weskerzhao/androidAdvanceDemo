package com.wesker.mvpdemo.activity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.wesker.mvpdemo.R;
import com.wesker.mvpdemo.aidl.Book;
import com.wesker.mvpdemo.aidl.IBookManager;
import com.wesker.mvpdemo.aidl.IOnNewBookArrivedListener;
import com.wesker.mvpdemo.service.BookManagerService;

import java.util.List;

public class BookManagerActivity extends AppCompatActivity {


    private static final String TAG = "BookManagerActivity";
    private static final int MESSAGE_NEW_BOOK_ARRIVED = 1;
    private IBookManager mRemoteBookManager;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_NEW_BOOK_ARRIVED:
                    Log.d(TAG, "receive new book :" + msg.obj);
                    break;
                default:
                    super.handleMessage(msg);

            }
        }
    };

    private IOnNewBookArrivedListener listener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void OnNewBookArrived(Book book) throws RemoteException {
            Message msg = Message.obtain(null, MESSAGE_NEW_BOOK_ARRIVED);
            msg.obj = book;
            mHandler.sendMessage(msg);
        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mRemoteBookManager = IBookManager.Stub.asInterface(service);

            try {
                List<Book> bookList = mRemoteBookManager.getBookList();
                Log.i(TAG, "query book list: " + bookList.toString());
                Book book = new Book(3, "Android开发艺术探索");
                mRemoteBookManager.addBook(book);
                Log.i(TAG, "add book: " + book.toString());
                List<Book> bookNewList = mRemoteBookManager.getBookList();
                Log.i(TAG, "query book new list: " + bookNewList.toString());
                mRemoteBookManager.registerListener(listener);
                listener.OnNewBookArrived(book);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mRemoteBookManager = null;
            Log.e(TAG, "binder died");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);
        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onDestroy() {
        if(mRemoteBookManager != null && mRemoteBookManager.asBinder().isBinderAlive()){
            Log.i(TAG,"ungister listener:" + listener);
            try {
                mRemoteBookManager.unregisterListener(listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mConnection);
        super.onDestroy();
    }
}
