package com.example.randimarie.namequiz;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class NewEntryActivity extends AppCompatActivity {

    public static final String NEW_PERSON = "PERSON";
    public static final String NEWPERSON_URI = "URI";

    ImageView photoView;
    Uri currentUri;
    String currentPhotoPath;

    EditText personName;

    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);
        permissionRequests();

        personName = findViewById(R.id.editText);

        db = FirebaseFirestore.getInstance();

        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        photoView = findViewById(R.id.imageView);
    }

    public void openCamera(View view){
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePicture.resolveActivity(getPackageManager()) != null){

            File photofile = null;
            try {
                photofile = createImageFile();
            } catch (IOException e){

            }

            if(photofile !=null){

                Uri photoURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, photofile);

                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePicture, 1);
            }
        }

    }

    public void goToGallery(View view){
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 2);
        }
    }


    private File createImageFile() throws IOException{
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "img" + timeStamp + "_";

        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".jpg", storageDir);

        // Save a file: path for use with ACTION_VIEW intents
        String mCurrentPhotoPath;
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void permissionRequests(){
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 4);
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.MANAGE_DOCUMENTS}, 3);
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 1);
    }


    public void addNewEntry(View view){
        String image = currentPhotoPath;


        String name = personName.getText().toString();
        CollectionReference dbProducts = db.collection("persons");

        Person person = new Person(name, image);

        dbProducts.add(person)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(NewEntryActivity.this, "Person Added", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NewEntryActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Uri selectedImage = data.getData();
        currentUri = selectedImage;
        currentPhotoPath = "content://media" + selectedImage.getPath();
        photoView.setImageURI(selectedImage);
        photoView.getLayoutParams().height = 500;
        photoView.getLayoutParams().width = 500;

    }



}
