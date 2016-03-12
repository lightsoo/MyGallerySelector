package com.example.lightsoo.mygalleryselector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.lightsoo.mygalleryselector.Gallery.GalleryImageActivity;

public class MainActivity extends AppCompatActivity {

    public static final String INTENT_IMG_PATHS = "intent_img_paths";
    Button btn_gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GalleryImageActivity.class);
                startActivityForResult(intent, GalleryImageActivity.REQUEST_CODE_GALLERY);
            }
        });
    }

    public void init(){
        btn_gallery = (Button)findViewById(R.id.btn_gallery);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GalleryImageActivity.REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK){
            String[] paths = data.getStringArrayExtra(INTENT_IMG_PATHS);
            if(paths != null) {
                for(int i = 0; i < paths.length; i++){
                    //여기에 리스트뷰로 하자,
//                    mAdapter.add(paths[i]);
                }
            }
        }

    }
}
