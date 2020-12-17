package com.Hr.Project.Percistance.Book;

import java.awt.print.Book;

public interface BookDaoI {
    //crud
    public boolean create(Book book);
    public Book read(String name,String id);
    public boolean update(Book book);
    //public boolean issuseBook(String name);
    public boolean delete(Book book);
}
