package com.example.sonamessteetenzin.perfectplate;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileCustomer extends AppCompatActivity implements View.OnClickListener {

    private ImageView profileImageView;
    private Button pickImage;
    ImageView photoUpload;

    private static final int SELECT_PHOTO = 1;
    private static final int CAPTURE_PHOTO = 2;

    private boolean hasImagechanged = false;
    Bitmap thumbnail;
    private ProgressBar progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();
    ThemedSpinnerAdapter.Helper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_customer);

        profileImageView = (CircleImageView) findViewById(R.id.ccleimgview);
        pickImage = (Button) findViewById(R.id.btnedit);
        pickImage.setOnClickListener(this);

        photoUpload = (ImageView) findViewById(R.id.PhotoUpload);

        photoUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ProfileCustomer.this, Registration.class));

            }
        });

        if (ContextCompat.checkSelfPermission(ProfileCustomer.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            profileImageView.setEnabled(false);

        } else {
            profileImageView.setEnabled(true);

        }
    }

    @Override
    public void onClick(final View view) {

        switch (view.getId()) {
            case R.id.btnedit:
                MaterialDialog.ListCallback dialog;
                new MaterialDialog.Builder(this)
                        .title("set your image")
                        .items(R.array.uploadImages)
                        .itemsIds(R.array.itemIds)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                switch (which) {
                                    case 0:
                                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                                        photoPickerIntent.setType("image/*");
                                        startActivityForResult(photoPickerIntent, SELECT_PHOTO);

                                        break;

                                    case 1:
                                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        startActivityForResult(intent, CAPTURE_PHOTO);
                                        break;

                                    case 2:
                                        profileImageView.setImageResource(R.drawable.ic_account_circle_black_24dp);
                                        break;
                                }
                            }
                        })
                        .show();
                break;
        }

    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantresults) {
        if (requestCode == 0) {
            if (grantresults.length > 0 && grantresults[0] == PackageManager.PERMISSION_GRANTED
                    && grantresults[1] == PackageManager.PERMISSION_GRANTED) {
                profileImageView.setEnabled(true);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PHOTO) {
            if (resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    profileImageView.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        } else if (requestCode == CAPTURE_PHOTO) {
            if (resultCode == RESULT_OK) {
                onCaptureImageResult(data);
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void onCaptureImageResult(Intent data) {
        thumbnail = (Bitmap) data.getExtras().get("data");
        profileImageView.setMaxWidth(200);
        profileImageView.setImageBitmap(thumbnail);
    }
}
