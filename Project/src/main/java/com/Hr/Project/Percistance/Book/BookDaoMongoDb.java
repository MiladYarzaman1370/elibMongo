package com.Hr.Project.Percistance.Book;

import com.Hr.Project.Percistance.ElibraryMongoDb;
import com.mongodb.client.MongoCollection;

import java.awt.print.Book;

public class BookDaoMongoDb implements BookDaoI{
    ElibraryMongoDb elibraryMongoDb=ElibraryMongoDb.getInstance();
    MongoCollection<com.Hr.Project.Model.Book> mCol=elibraryMongoDb.getBookCol();
    @Override
    public boolean create(Book book) {
        return false;
    }

    @Override
    public Book read(String name, String id) {
        return null;
    }

    @Override
    public boolean update(Book book) {
        return false;
    }

    @Override
    public boolean delete(Book book) {
        return false;
    }
}
