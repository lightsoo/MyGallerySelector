package com.example.lightsoo.mygalleryselector.Gallery;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lightsoo.mygalleryselector.Data.ImageItem;
import com.example.lightsoo.mygalleryselector.MainActivity;
import com.example.lightsoo.mygalleryselector.R;

import java.util.ArrayList;
import java.util.List;

public class GalleryImageActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    public static final int REQUEST_CODE_GALLERY = 0;
    //갤러리 내장 DB에서 이미지를 가져오기위한 쿼리 세팅
    //Select하고자 하는 컬럼 명(이미지 id, 경로명)
    String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};
    String selection = null;
    String[] selectionArgs = null;
    String sortOrder = MediaStore.Images.Media.DATE_ADDED + " DESC";

    int dataColumnIndex = -1;
    SimpleCursorAdapter mAdapter;
    GridView gridView;
    Button btn_ok;

    //최대 선택갯수
    public static final int IMAGE_CHECK_THRESHOLD = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_gallery);
        setSupportActionBar(toolbar);
        //이걸로 기존에 뜨는 Title을 안보이게 한다.
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //백키가 나온다.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
        //DB의 데이타를
        String[] from = {MediaStore.Images.Media.DATA};
        //gallery_image_item에다가 출력하려는 거야
        int[] to = {R.id.gallery_image_item};
        //직접 새로운 레이아웃을 만들었어
        mAdapter = new SimpleCursorAdapter(this, R.layout.view_gallery_image_item_layout, null, from, to, 0);
        mAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (columnIndex == dataColumnIndex) {
                    ImageView iv = (ImageView) view;
                    String path = cursor.getString(columnIndex);
                    String uri = "file://" + path;
                    Glide.with(getApplicationContext())
                            .load(uri)
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(iv);
//                    ImageLoader.getInstance().displayImage(uri.toString(), iv);
                    return true;
                }
                return false;
            }
        });
        gridView.setAdapter(mAdapter);
        gridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
        getSupportLoaderManager().initLoader(0, null, this);

        //사진 선택갯수
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //리스트뷰에서 체크된걸 확인하는 객체야
                SparseBooleanArray selection = gridView.getCheckedItemPositions();
                int count = 0;
                for (int index = 0; index < selection.size(); index++) {
                    int pos = selection.keyAt(index);
                    //get(pos) 사진이 선택된거야
                    if (selection.get(pos)) {
                        count++;
                    }
                }
                //코드로 설정 다 한후에 그림이 그려진다.
                //view의 setChecked가 먼저 불려진다. 할지라도 여기서 한번더 설정이 된 후에
                //그 값을 가지고 onresume다음에 draw되는 시점에 그려진다.!
                if (count > IMAGE_CHECK_THRESHOLD) {
                    gridView.setItemChecked(position, false);
                    Toast.makeText(GalleryImageActivity.this, "사진은 최대 10장까지 등록할 수 있습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pictureAdd();
            }
        });
    }

    public void init(){
        gridView = (GridView)findViewById(R.id.gallery_image_grid);
        btn_ok  =  (Button)findViewById(R.id.btn_ok);
    }

    public void pictureAdd(){
        SparseBooleanArray array = gridView.getCheckedItemPositions();
/*
        //여기서 객체를 넣은다음에 리턴해보자
        List<ImageItem> items = new ArrayList<ImageItem>();

        for (int index = 0; index < array.size(); index++){
            int position = array.keyAt(index);
            if(array.get(position)){
                //어떻게 이렇게 커서로 변환이 가능한걸까?
                Cursor c = (Cursor)gridView.getItemAtPosition(position);
                String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
//                items.setTitle();
//                items.setImage(path);
            }
        }
*/
        List<String> pathList = new ArrayList<String>();
        for (int index = 0; index < array.size(); index++){
            int position = array.keyAt(index);
            if(array.get(position)){
                //어떻게 이렇게 커서로 변환이 가능한걸까?
                Cursor c = (Cursor)gridView.getItemAtPosition(position);
                String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
                pathList.add(path);
            }
        }

        Intent intent = new Intent(GalleryImageActivity.this, MainActivity.class);
        String[] paths = pathList.toArray(new String[pathList.size()]);
        intent.putExtra(MainActivity.INTENT_IMG_PATHS, paths);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        return new CursorLoader(this, uri, projection, selection, selectionArgs, sortOrder);
    }

    //Loader 작업이 끝난후 결과 데이터를 어댑터에 저장한다.
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        dataColumnIndex = data.getColumnIndex(MediaStore.Images.Media.DATA);
        mAdapter.swapCursor(data);
    }

    //Loader가 리셋되면 기존의 데이터를 해제한다.
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

}
