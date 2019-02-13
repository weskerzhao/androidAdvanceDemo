// IBookManager.aidl
package com.wesker.mvpdemo.aidl;

import com.wesker.mvpdemo.aidl.Book;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}
