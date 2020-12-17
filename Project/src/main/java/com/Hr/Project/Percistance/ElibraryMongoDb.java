package com.Hr.Project.Percistance;

import com.Hr.Project.Model.Book;
import com.Hr.Project.Model.Librarian;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class ElibraryMongoDb {
    private static ElibraryMongoDb elibraryMongoDb;
    private MongoDatabase mongoDb;

    private MongoCollection<com.Hr.Project.Model.Book> bookCol;
    private MongoCollection<Document> userCol;

    private MongoCollection<Document> adminCol;
    private MongoCollection<Librarian> librarianCol;
    CodecRegistry pojoCodecRegistry;
    private ElibraryMongoDb(){

         pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClient mongoClient= MongoClients.create("mongodb://localhost");
        mongoDb=mongoClient.getDatabase("eLibrarys").withCodecRegistry(pojoCodecRegistry);


    }
    public static ElibraryMongoDb getInstance(){
        if(elibraryMongoDb ==null){
            elibraryMongoDb =new ElibraryMongoDb();

        }else{}
        return elibraryMongoDb;
    }

    public MongoDatabase getMongoDb() {
        return mongoDb;
    }

    public MongoCollection<Document> getBookColDocument() {

        return mongoDb.getCollection("book");
    }

    public MongoCollection<Book> getBookCol() {

        bookCol=mongoDb.getCollection("book", Book.class);
        if(bookCol!=null)
        {
            System.out.println(bookCol);
        }else{System.out.println(" book col is nulll");}
       // bookCol.withCodecRegistry(pojoCodecRegistry);

        return bookCol;
    }
    public MongoCollection<Document> getUserCol() {
        return mongoDb.getCollection("user");
    }


    public MongoCollection<Document> getAdminCol() {
        return mongoDb.getCollection("admin");
    }


    public MongoCollection<Librarian> getLibrarianCol() {
        MongoCollection<Librarian> librarianColection= mongoDb.getCollection("librarian",Librarian.class);
        librarianColection.withCodecRegistry(pojoCodecRegistry);
        return librarianColection;
    }

    public MongoCollection<Document> getLibrarianColDocument() {

        return mongoDb.getCollection("librarian");
    }

}
