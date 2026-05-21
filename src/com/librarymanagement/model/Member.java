package com.librarymanagement.model;

public class Member {

    private static int personId;
    private int id;
    private String name;
    private String email;
    private int borrowedBooksCount;

    public Member(String name, String email){
        this.id = ++personId;
        this.name= name;
        this.email = email;
        this.borrowedBooksCount = 0;
    }

    public int getPersonId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String setEmail() {
        return email;
    }

    public void incrementBorrowedBooks() {borrowedBooksCount++;}
    public void decrementBorrowedBooks() {borrowedBooksCount--;}

    public String toString() { return id + " - " + name + " - " + email; }
}
