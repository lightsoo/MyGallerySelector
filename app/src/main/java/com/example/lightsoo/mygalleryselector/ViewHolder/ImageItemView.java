package com.example.lightsoo.mygalleryselector.ViewHolder;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lightsoo.mygalleryselector.Data.ImageItem;
import com.example.lightsoo.mygalleryselector.R;

/**
 * Created by LG on 2016-03-12.
 */
public class ImageItemView extends FrameLayout {
    ImageView imageView;
    TextView titleView;

    public ImageItemView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_image_item, this);
        imageView = (ImageView)findViewById(R.id.image_src);
//        titleView = (TextView)findViewById(R.id.image_title);
    }

    public void setImageItem(ImageItem item){
        titleView.setText(item.getTitle());
        Glide.with(getContext())
                .load(item.getImage())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView);
    }

    public void setImageItem(String item){
        Glide.with(getContext())
                .load(item)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView);
    }



}
