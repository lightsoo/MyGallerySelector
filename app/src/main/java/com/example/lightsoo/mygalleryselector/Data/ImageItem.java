package com.example.lightsoo.mygalleryselector.Data;

import java.io.Serializable;


public class ImageItem implements Serializable{
    String title;
    String imageURL;

    public ImageItem(){}

    public void setTitle(String title){
        this.title = title;
    }

    public void setImage(String image){
        this.imageURL = image;
    }

    public String getTitle(){
        return title;
    }

    public String getImage(){
        return imageURL;
    }

}
