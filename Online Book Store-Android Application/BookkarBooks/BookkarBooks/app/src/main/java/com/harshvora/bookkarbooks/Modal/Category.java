package com.harshvora.bookkarbooks.Modal;

/**
 * Created by Harsh on 1/29/2018.
 */

public class Category {
    private String Bookname;
    private String Authorname;
    private String Publicationname;
    private String Price;
    private String Ratings;
    private String Image;
    private String BookId;

    public Category() {


    }

    public Category(String bookname, String authorname, String publicationname, String price, String ratings, String image) {
        Bookname = bookname;
        Authorname = authorname;
        Publicationname = publicationname;
        Price = price;
        Ratings = ratings;
        Image = image;
    }

    public String getBookId() {
        return BookId;
    }

    public void setBookId(String bookId) {
        BookId = bookId;
    }

    public String getBookname() {
        return Bookname;
    }

    public void setBookname(String bookname) {
        Bookname = bookname;
    }

    public String getAuthorname() {
        return Authorname;
    }

    public void setAuthorname(String authorname) {
        Authorname = authorname;
    }

    public String getPublicationname() {
        return Publicationname;
    }

    public void setPublicationname(String publicationname) {
        Publicationname = publicationname;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getRatings() {
        return Ratings;
    }

    public void setRatings(String ratings) {
        Ratings = ratings;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}


