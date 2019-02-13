// IOnNewBookArrivedListener.aidl
package com.wesker.mvpdemo.aidl;

import com.wesker.mvpdemo.aidl.Book;

interface IOnNewBookArrivedListener {
    void OnNewBookArrived(in Book book);
}
