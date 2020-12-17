package com.Hr.Project.Percistance.Admin;

import com.Hr.Project.Model.Admin;
import com.Hr.Project.Model.Book;
import org.bson.types.ObjectId;

public interface AdminDaoI {
    ///crud
    public boolean create(Admin admin);
    public Admin read(String name, String id);
    public boolean update(Admin admin);
    public boolean delete(Admin admin);
    public boolean checkFound(String email,String password);
    public boolean addBook(Book book);
    public boolean issuseBook(ObjectId bookId, String librarianName);
}
