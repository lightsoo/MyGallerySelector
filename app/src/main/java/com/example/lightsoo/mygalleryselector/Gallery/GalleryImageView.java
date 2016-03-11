package com.example.lightsoo.mygalleryselector.Gallery;

import android.content.Context;
import android.graphics.Color;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.lightsoo.mygalleryselector.R;

/**
 * Created by LG on 2016-03-11.
 */
public class GalleryImageView extends RelativeLayout implements Checkable {

    ImageView ivImage, ivChecked;

    public GalleryImageView(Context context) {
        super(context);
        init();
    }

    public void init(){
        inflate(getContext(), R.layout.view_gallery_image_item, this);
        ivImage = (ImageView)findViewById(R.id.gallery_image_item);
        ivChecked = (ImageView)findViewById(R.id.gallery_image_checked);
    }

    boolean isChecked = false;
    private void drawCheck(){
        if(isChecked){
            ivChecked.setVisibility(VISIBLE);
            ivChecked.setBackgroundColor(Color.parseColor("#ffcc11"));
            //           checkView.setImageResource(android.R.drawable.checkbox_on_background);
        } else {
            ivChecked.setVisibility(INVISIBLE);
            ivChecked.setBackgroundColor(Color.TRANSPARENT);
            // checkView.setImageResource(android.R.drawable.checkbox_off_background);
        }
    }

    @Override
    public void setChecked(boolean checked) {
        if(isChecked != checked){
            isChecked = checked;
            drawCheck();
        }
    }

    @Override
    public boolean isChecked() {
        return false;
    }

    @Override
    public void toggle() {
        setChecked(!isChecked);
    }
}
