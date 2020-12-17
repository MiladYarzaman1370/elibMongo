package com.Hr.Project.Percistance.Admin;

import com.Hr.Project.Model.Admin;
import com.Hr.Project.Model.Book;
import com.Hr.Project.Model.Librarian;
import com.Hr.Project.Percistance.ElibraryMongoDb;
import com.google.gson.*;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;


import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.inc;

public class AdminDaoMongoDb implements AdminDaoI {
    ElibraryMongoDb elibraryMongoDb = ElibraryMongoDb.getInstance();
    private MongoCollection<Document> mcol = elibraryMongoDb.getAdminCol();
    private MongoCollection<Book> mcolBook = elibraryMongoDb.getBookCol();
    private MongoCollection<Librarian> mcolLibrarian = elibraryMongoDb.getLibrarianCol();

    @Override
    public boolean addBook(Book book) {

        if (mcolBook.insertOne(book).wasAcknowledged()) {

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean issuseBook(ObjectId bookId, String email) {
        boolean t=false;
        Book dcBook = mcolBook.find(eq("_id", bookId)).first();
        mcolBook.updateOne(eq("_id", bookId), (inc("quantity", -1)));
           System.out.println(email+"issuseBook");
        Librarian librarian = mcolLibrarian.find(eq("email", email)).first();
        if(librarian!=null){
            if(librarian.getIssueBooks()==null){
                List<Book> booksTemp=new ArrayList<>();
                booksTemp.add(dcBook);
                librarian.setIssueBooks(booksTemp);
                mcolLibrarian.deleteOne(eq("email",email));
                t=mcolLibrarian.insertOne(librarian).wasAcknowledged();
            }else{

                librarian.getIssueBooks().add(dcBook);
                mcolLibrarian.deleteOne(eq("email",email));

                t=mcolLibrarian.insertOne(librarian).wasAcknowledged();
            }
        }else{}

        return t;
    }


    @Override
    public boolean create(Admin admin) {
        return false;
    }

    @Override
    public Admin read(String name, String id) {
        return null;
    }

    @Override
    public boolean update(Admin admin) {
        return false;
    }

    @Override
    public boolean delete(Admin admin) {
        return false;
    }

    @Override
    public boolean checkFound(String email, String password) {


        Document dc = mcol.find(and(eq("email", email), eq("password", password))).first();
        if (dc != null) {

            return true;

        } else {
            return false;
        }

    }

    public static List<Object> parseJson(String sb) {
        List<Object> list = new ArrayList<>();
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = new JsonObject();
        JsonElement jsonEle = jsonParser.parse(sb);
        if (jsonEle instanceof JsonObject) {
            JsonObject jobject = jsonEle.getAsJsonObject();
            System.out.println("isssssssssssssobject");
        } else if (jsonEle instanceof JsonArray) {
            JsonArray jarray = jsonEle.getAsJsonArray();
            System.out.println("isssssssssssssobject  Array");
        } else {
            System.out.println("%^&$#%^&&" + jsonEle.getAsString());
        }
        JsonObject jArray = new JsonObject();
        try {
            jArray = jsonEle.getAsJsonObject();
        } catch (Exception e) {
        }

        String s = jsonEle.toString();
        System.out.println(sb + s + "jmmmmmmmmmmmmmmmmmmmmmmmmmm");
        for (int i = 0; i < jArray.size(); i++) {
            jsonObject = jArray.getAsJsonObject();
            System.out.println(jsonObject.toString() + "99999999");
            if (jsonObject != null)
                list.add(jsonObject.get("bookName").getAsString());
        }
        return list;
    }





}
