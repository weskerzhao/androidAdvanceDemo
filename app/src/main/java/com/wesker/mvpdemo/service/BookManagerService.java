package com.wesker.mvpdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.wesker.mvpdemo.aidl.Book;
import com.wesker.mvpdemo.aidl.IBookManager;
import com.wesker.mvpdemo.aidl.IOnNewBookArrivedListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookManagerService extends Service {
    private static final String TAG = "BMS";

    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();

    private CopyOnWriteArrayList<IOnNewBookArrivedListener> mIOnNewBookArrivedListenerList = new CopyOnWriteArrayList<>();

    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);

    private Binder mBinder = new IBookManager.Stub(){

        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            if(!mIOnNewBookArrivedListenerList.contains(listener)){
                mIOnNewBookArrivedListenerList.add(listener);
                Log.i(TAG,"register listener successed.");
            }else{
                Log.i(TAG,"already exists.");
            }
            Log.i(TAG,"registerListener.size:" + mIOnNewBookArrivedListenerList.size());
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            if(mIOnNewBookArrivedListenerList.contains(listener)) {
                mIOnNewBookArrivedListenerList.remove(listener);
                Log.i(TAG,"unregister listener successed.");
            }else{
                Log.i(TAG,"not found,can not unregister.");
            }
            Log.i(TAG,"registerListener.size:" + mIOnNewBookArrivedListenerList.size());
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1,"Android"));
        mBookList.add(new Book(2,"Ios"));
        new Thread(new ServiceWorker()).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsServiceDestoryed.set(true);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private void onNewBookArrived(Book book) throws RemoteException {
        mBookList.add(book);
        Log.i(TAG,"onNewBookArrived,notify listener:" + mIOnNewBookArrivedListenerList.size());
        for (IOnNewBookArrivedListener listener : mIOnNewBookArrivedListenerList) {
            Log.i(TAG,"onNewBookArrived,notify listener:" + listener);
            listener.OnNewBookArrived(book);

        }
    }


    private class ServiceWorker implements Runnable{

        @Override
        public void run() {
            while(!mIsServiceDestoryed.get()){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookId = mBookList.size() + 1;
                Book newBook = new Book(bookId, "new book# " + bookId);

                try {
                    onNewBookArrived(newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
