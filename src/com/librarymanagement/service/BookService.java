package com.librarymanagement.service;

import java.util.ArrayList;
import com.librarymanagement.model.Book;

public class BookService {

    private ArrayList<Book> books;

    public BookService() {
        this.books = new ArrayList<>();
    }

    public void registerBook(Book book) {
        books.add(book);
    }

    public Book findBookById(int id){
        for (Book b : books){
            if (b.getId() == id){
                return b;
            }
        }
        return null;
    }

    public boolean removeBook(int id) {
        Book book = findBookById(id);
        if (book != null) {
            books.remove(book);
            return true;
        }
        return false;
    }

    public ArrayList<Book> listBooks(){
        return books;
    }
}
