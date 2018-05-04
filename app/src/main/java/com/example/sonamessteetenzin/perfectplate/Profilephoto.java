package com.example.sonamessteetenzin.perfectplate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profilephoto extends AppCompatActivity implements View.OnClickListener {

    private EditText Img_title;
    private Button BnChoose, BnUpload;
    private ImageView Img;
    private static final int IMG_REQUEST=777;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilephoto);
        Img_title =(EditText)findViewById(R.id.img_title);
        BnChoose = (Button)findViewById(R.id.chooseBn);
        BnUpload = (Button)findViewById(R.id.uploadBn);
        Img=(ImageView)findViewById(R.id.imageView);
        BnChoose.setOnClickListener(this);
        BnUpload.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.chooseBn:
                selectImage();
                break;

            case R.id.uploadBn:
                break;

        }

    }

    private void uploadImage()
    {
        String Image= imageToString();
        String Title = Img_title.getText().toString();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ImageClass>call= apiInterface.uploadImage(Title,Image);

        call.enqueue(new Callback<ImageClass>() {
            @Override
            public void onResponse(Call<ImageClass> call, Response<ImageClass> response) {
                ImageClass imageClass = response.body();
                Toast.makeText(Profilephoto.this, "Server Response:"+imageClass.getResponse(), Toast.LENGTH_SHORT).show();
                Img.setVisibility(View.GONE);
                Img_title.setVisibility(View.GONE);
                BnChoose.setEnabled(true);
                BnUpload.setEnabled(false);
                Img_title.setText("");


            }

            @Override
            public void onFailure(Call<ImageClass> call, Throwable t) {

            }
        });

    }
    private void selectImage()
    {
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMG_REQUEST && resultCode==RESULT_OK && data !=null)
        {
            Uri path= data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                Img.setImageBitmap(bitmap);
                Img.setVisibility(View.VISIBLE);
                Img_title.setVisibility(View.VISIBLE);
                BnChoose.setEnabled(false);
                BnUpload.setEnabled(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private String imageToString()
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }
}
