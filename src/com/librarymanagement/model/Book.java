package com.librarymanagement.model;

import com.librarymanagement.enums.BookCategory;

public class Book {

    private static int nextId;
    private int id;
    private String title;
    private String author;
    private BookCategory category;
    private boolean available;

    public Book(String title, String author, BookCategory category){
        this.id = ++nextId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.available = true;
    }

    public int getId() {
        return id; }
    public String getTitle() {
        return title; }
    public String getAuthor() {
        return author; }
    public BookCategory getCategory() {
        return category; }
    public boolean isAvailable() {
        return available; }

    public void markAsBorrowed() {
        this.available = false;
    }
    public void markAsReturned() {
        this.available = true;
    }

    @Override
    public String toString() {
        return id + " - " + title + " - " + author + " - " + category + " - " + category;
    }
}
