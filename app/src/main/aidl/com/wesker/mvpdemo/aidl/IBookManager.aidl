// IBookManager.aidl
package com.wesker.mvpdemo.aidl;

import com.wesker.mvpdemo.aidl.Book;
import com.wesker.mvpdemo.aidl.IOnNewBookArrivedListener;
interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);
}
