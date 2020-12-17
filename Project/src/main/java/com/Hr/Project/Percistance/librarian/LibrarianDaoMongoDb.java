package com.Hr.Project.Percistance.librarian;

import com.Hr.Project.Model.Book;
import com.Hr.Project.Model.Librarian;
import com.Hr.Project.Percistance.ElibraryMongoDb;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;


import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.inc;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class LibrarianDaoMongoDb implements LibrarianDaoI{
    ElibraryMongoDb elibraryMongoDb=ElibraryMongoDb.getInstance();
    //MongoCollection mcol=elibraryMongoDb.getLibrarianCol();
    MongoCollection<Librarian> mcol=elibraryMongoDb.getLibrarianCol();
    MongoCollection mcolBook=elibraryMongoDb.getBookCol();
    private CodecRegistry pojoCodecRegistery;

    @Override
    public boolean create(Librarian librarian) {


        boolean g=false;
        try {
           g= mcol.insertOne(librarian).wasAcknowledged();
        }catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("exception");
        }
        return g;

    }


    @Override
    public Librarian read(ObjectId librarianId) {

        return mcol.find(eq("_id",librarianId)).first();
    }

    @Override
    public Librarian read(String email) {
        return mcol.find(eq("email",email)).first();
    }

    @Override
    public boolean update(Librarian librarian) {
        return false;
    }

    @Override
    public boolean delete(ObjectId librarianId) {

        if(mcol.deleteOne(eq("_id",librarianId)).wasAcknowledged()){return true;}else{return false;}

    }


   @Override
   public List<Librarian> librarianList() {
       MongoCursor<Librarian> cursor = mcol.find().cursor();
       List<Librarian> librarians=new ArrayList<>();
       try {
           while (cursor.hasNext()) {
               librarians.add(cursor.next());
           }
       } finally {
           cursor.close();
       }
       return librarians;
   }


    @Override
    public List<Book> mybookList(ObjectId librarianId) {
        ArrayList<Book> books=new ArrayList<>();
        Librarian librarian = mcol.find(eq("_id",librarianId)).first();

        return librarian.getIssueBooks();    }

    @Override
    public ArrayList<Book> bookList() {
        MongoCursor<Book> cursor = mcolBook.find().iterator();
        ArrayList<Book> books=new ArrayList<>();

        try {
            while (cursor.hasNext()) {
                books.add(cursor.next());
            }
        } finally {
            cursor.close();
        }
        return books;

    }




    @Override
    public boolean checkFound(String email, String password) {



       Librarian librarian=mcol.find(and(eq("email",email),eq("password",password))).first();

        if(librarian!=null){

            return true;

        }else{return false;}

    }

    @Override
    public boolean issuseBook(Book bookName, String librarianName) {
        return false;
    }

    @Override
    public boolean returnBook(ObjectId bookId, ObjectId librarianId) {
        boolean temp=false;
               Librarian librarian=  mcol.find(eq("_id",librarianId)).first();
        List<Book> list = librarian.getIssueBooks();
        Book b;

        for(int i=0;i<list.size();i++)
        {
            b=list.get(i);
            if(b.getId().equals(bookId)){
                   list.remove(i);
            }else{

            }
        }
        mcol.deleteOne(eq("_id",librarianId));
        librarian.setIssueBooks(list);
        if(mcol.insertOne(librarian).wasAcknowledged()){
             temp=mcolBook.updateOne(eq("_id",bookId),inc("quantity",1)).wasAcknowledged();
        }else {}
        return temp;
    }

}
