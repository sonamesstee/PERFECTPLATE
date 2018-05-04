package com.example.sonamessteetenzin.perfectplate;

/**
 * Created by Sonam ESSTEE Tenzin on 4/21/2018.
 */

public class Album {
    private String id, title;

    public Album(String id, String title, String path){}
    public Album(String id, String title)
    {
        this.setId(id);
        this.setTitle(title);

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
