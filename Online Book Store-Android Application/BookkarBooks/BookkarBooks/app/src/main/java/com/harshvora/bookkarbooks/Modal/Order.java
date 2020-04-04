package com.harshvora.bookkarbooks.Modal;

/**
 * Created by Harsh on 2/1/2018.
 */

public class Order {
    private String BookId;
    private String BookName;
    private String Quantity;
    private String Price;
    private String Discount;

    public Order() {
    }

    public Order(String bookId, String bookName, String quantity, String price, String discount) {
        BookId = bookId;
        BookName = bookName;
        Quantity = quantity;
        Price = price;
        Discount = discount;
    }

    public String getBookId() {
        return BookId;
    }

    public void setBookId(String bookId) {
        BookId = bookId;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }
}
