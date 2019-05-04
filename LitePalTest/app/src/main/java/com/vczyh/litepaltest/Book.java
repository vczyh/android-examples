package com.vczyh.litepaltest;

import org.litepal.crud.LitePalSupport;

// 生成表不需要继承LitePalSupport
// CRUD需要继承LitePalSupport
public class Book extends LitePalSupport {

    private int id;

    private String name;

    private String author;

    private int pages;

    private double price;

    private String press;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }
}
