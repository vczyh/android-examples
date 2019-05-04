package com.vczyh.litepaltest;

import org.litepal.crud.LitePalSupport;

public class Category extends LitePalSupport {

    private int id;

    private String category_name;

    private int category_code;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getCategory_code() {
        return category_code;
    }

    public void setCategory_code(int category_code) {
        this.category_code = category_code;
    }
}
