package com.Hr.Project.TemplateController;

import com.Hr.Project.CurrentUser;
import com.Hr.Project.Model.*;
import com.Hr.Project.RegularExpertion;
import com.Hr.Project.Percistance.Admin.AdminDaoI;
import com.Hr.Project.Percistance.Admin.AdminDaoMongoDb;
import com.Hr.Project.Percistance.librarian.LibrarianDaoI;
import com.Hr.Project.Percistance.librarian.LibrarianDaoMongoDb;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TemplateController {
    @RequestMapping("/")
    public String index(Model model) {
        LoginData loginData = new LoginData();
        model.addAttribute("loginData", loginData);
        return "index";
    }

    @RequestMapping("/adminSection")
    public String adminSection(@ModelAttribute("loginData") LoginData loginData, Model model) {

      /*  AdminDaoI adminDaoI = new AdminDaoMongoDb();
        if (adminDaoI.checkFound(loginData.getEmail(), loginData.getPassword())) {
            CurrentUser.curentAdminUser=loginData.getEmail();
            CurrentUser.curentLibrarianUser=null;
            return "admin/adminSection";
        } else {
            model.addAttribute("txterror", "not found email or password   (adminSection)");
            return "/error";
        }*/
        if (loginData.getEmail().contentEquals("iran@chmail.ir")&
                loginData.getPassword().contentEquals("1111")) {
            CurrentUser.curentAdminUser=loginData.getEmail();
            CurrentUser.curentLibrarianUser=null;
            return "admin/adminSection";
        } else {
            model.addAttribute("txterror", "not found email or password   (adminSection)");
            return "/error";
        }


    }

    @RequestMapping("/librarianSection")
    public String librarianSection(@ModelAttribute("loginData") LoginData loginData,Model model) {
        if (RegularExpertion.checkPassword(loginData.getPassword())
                & RegularExpertion.checkEmail(loginData.getEmail())) {
                   System.out.println("******"+loginData.getEmail()+loginData.getUserType()+loginData.getPassword());
            LibrarianDaoI librarianDaoI = new LibrarianDaoMongoDb();
          boolean temp=  librarianDaoI.checkFound(loginData.getEmail(), loginData.getPassword());
            if (temp) {

                CurrentUser.curentAdminUser=null;
                CurrentUser.curentLibrarianUser=loginData.getEmail();
                return "librarian/librarianSection";
            } else {
                model.addAttribute("txterror", "not found email or password (librarianSection)");
                return "/error";
            }


        } else {

            model.addAttribute("txterror", "email or passwordnot regular.(librarianSection)");
            return "/error";
        }
    }

    @RequestMapping("/adminSection/addLibrarian")
    public String addLibrarian(Model model) {
        User librarian = new Librarian();
        model.addAttribute("librarianData", librarian);
        return "admin/adminSection/addLibrarian";
    }

    @RequestMapping("/adminSection/addLibrarian/register")
    public String librarianregiste(@ModelAttribute("librarianData") Librarian librarian, Model model) {
        if (RegularExpertion.checkPassword(librarian.getPassword())
                & RegularExpertion.checkEmail(librarian.getEmail())) {
                      LibrarianDaoI librarianDaoI = new LibrarianDaoMongoDb();
            if (librarianDaoI.create(librarian)) {
                return "admin/adminSection";
            } else {
                model.addAttribute("txterror", "can not insert data into DataBase.");
                return "/error";
            }
        } else {
            model.addAttribute("txterror", "please insert regular expertion.");
            return "/error";
        }

    }

    @RequestMapping("/adminSection/viewLibrarians")
    public String view(Model model) {
        List<Librarian> librarians=new ArrayList<>();
        LibrarianDaoI librarianDaoI=new LibrarianDaoMongoDb();
        librarians=librarianDaoI.librarianList();
        model.addAttribute("librarians",librarians);
       // System.out.println(librarians.get(0).getName());
        return "admin/adminSection/viewLibrarians";
    }

    @RequestMapping("/adminSection/editLibrarian")
    public String edit() {

        return "admin/adminSection/editLibrarian";
    }

    @RequestMapping("/adminSection/deleteLibrarian")
    public String delete(@RequestParam("id") ObjectId librarianId, Model model) {
        System.out.println(librarianId);
        Librarian librarian=new Librarian();
        LibrarianDaoI librarianDaoI=new LibrarianDaoMongoDb();

        if(librarianDaoI.delete(librarianId)){
            model.addAttribute("deleteResult","librarian delete is success.");
        }else{
            model.addAttribute("deleteResult","librarian delete is unSuccess.");
        }


        return "admin/adminSection/deleteLibrarian";
        //return "admin/adminSection/viewLibrarians";
    }

    @RequestMapping("/adminSection/LibrarianIssuseBooks")
    public String LibrarianIssuseBooks(@RequestParam("id") ObjectId librarianId, Model model) {

        Librarian librarian=new Librarian();
        LibrarianDaoI librarianDaoI=new LibrarianDaoMongoDb();
        String librarianName=librarianDaoI.read(librarianId).getName();
        model.addAttribute("librarianName",librarianName);
        List<Book> books=librarianDaoI.mybookList(librarianId);
        if(books!=null){
            model.addAttribute("books",books);
            System.out.println(books.get(0).getName());
        }else{
            model.addAttribute("librarianName",librarianName);
            model.addAttribute("empty",true);
            model.addAttribute("books",books);
        }


        return "admin/adminSection/librarianIssuseBooks";
        //return "admin/adminSection/viewLibrarians";
    }
    /////////////////////////////////mange book
    /////////////////////////////
    /////////////////////////////
    @RequestMapping("/adminSection/addBook")
    public String addBook(Model model) {
        Book book = new Book();
        model.addAttribute("bookData", book);
        return "admin/adminSection/addBook";
    }

    @RequestMapping("/adminSection/addBook/register")
    public String bookRegister(@ModelAttribute("bookData") Book book, Model model) {

            AdminDaoI adminDaoI = new AdminDaoMongoDb();
            if (adminDaoI.addBook(book)) {
                return "admin/adminSection";
            } else {
                model.addAttribute("txterror", "can not isert data into DataBase.");
                return "/error";
            }


    }

    @RequestMapping("/librarianSection/issuseNewBook")
    public String essuseNewBook( Model model) {
         LibrarianDaoI librarianDaoI=new LibrarianDaoMongoDb();
         ArrayList<Book> books=new ArrayList<>();
         books=librarianDaoI.bookList();
         model.addAttribute("books",books);

       return "/librarian/librarianSection/issuseBook";

    }
    @RequestMapping("/librarianSection/issuseBook")
    public String essuseBook(@RequestParam("id") ObjectId bookId, Model model)
    {

        AdminDaoI adminDaoI=new AdminDaoMongoDb();
        if(adminDaoI.issuseBook(bookId,CurrentUser.curentLibrarianUser)){
            return "librarian/librarianSection";
        }else{
            model.addAttribute("txterror","issuse Book error  ");
            return "error";
        }
    }

    ////////////////////////////////return page And return book

    @RequestMapping("/librarianSection/returnBook")
    public String returnBook( Model model) {

        LibrarianDaoI librarianDaoI=new LibrarianDaoMongoDb();
        List<Book> books=new ArrayList<>();
        System.out.println(CurrentUser.curentLibrarianUser+librarianDaoI.read(CurrentUser.curentLibrarianUser).getId()+"*************************");
        ObjectId librarianId=librarianDaoI.read(CurrentUser.curentLibrarianUser).getId();
        books=librarianDaoI.mybookList(librarianId);

        model.addAttribute("books",books);
        model.addAttribute("librarianId",librarianId );

        return "/librarian/librarianSection/returnBook";
        //return "admin/adminSection/viewLibrarians";
    }

    @RequestMapping("/librarianSection/returnb")
    public String returnb(@RequestParam("id") ObjectId bookId,@RequestParam("librarianId") ObjectId librarianId, Model model)
    {

        LibrarianDaoI librarianDaoI=new LibrarianDaoMongoDb();
        if(librarianDaoI.returnBook(bookId,librarianId)){
            return "librarian/librarianSection";
        }else{
            model.addAttribute("txterror","return book error  ");
            return "error";
        }
    }


}
