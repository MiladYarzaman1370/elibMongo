package com.Hr.Project.Model;


import org.bson.types.ObjectId;

import java.util.List;

public class Librarian extends User{
    private List<Book> issueBooks;
    public Librarian(ObjectId id, String name, String email, String password,
                     String mobileNumber, String type, List<Book> issueBooks) {
        super(id, name, email, password, mobileNumber, type);
        this.issueBooks=issueBooks;
        this.type="librarian";
    }

    public Librarian() {
            }

    public List<Book> getIssueBooks() {
        return issueBooks;
    }

    public void setIssueBooks(List<Book> issueBooks) {
        this.issueBooks = issueBooks;
    }
    ///////////////////////////////////////////////
}
