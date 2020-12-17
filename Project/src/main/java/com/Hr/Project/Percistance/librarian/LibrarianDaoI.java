package com.Hr.Project.Percistance.librarian;

import com.Hr.Project.Model.Librarian;
import com.Hr.Project.Model.Book;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public interface LibrarianDaoI {
    //////crud
    public boolean create(Librarian librarian);

    public Librarian read( ObjectId id);
    public Librarian read( String email);
    public boolean update(Librarian librarian);
    public boolean delete(ObjectId librarian);
    public List<Librarian> librarianList();
    public ArrayList<Book> bookList();
    public List<Book> mybookList(ObjectId librarianId);
    public boolean checkFound(String email, String password);
   // public boolean issuseBook(String bookName, String librarianName);
    //public boolean returnBook(String bookName, String librarianName);

    public boolean issuseBook(Book bookName, String librarianName);
    public boolean returnBook(ObjectId bookId, ObjectId librarianId);
}
