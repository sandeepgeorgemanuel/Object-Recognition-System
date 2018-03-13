package com.example.sandeepgm.objectrecognitionsystem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibm.watson.developer_cloud.android.library.camera.CameraHelper;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassResult;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImage;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifierResult;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.Classifiers;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;

import java.io.File;
import java.io.FileNotFoundException;

public class ScanActivity extends AppCompatActivity {
    private static final String TAG = "ScanActivity";

    ImageView picture;
    TextView obname;
    Button capture;

    private VisualRecognition vrClient;
    private CameraHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        vrClient = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_20, getString(R.string.api_key));
        helper = new CameraHelper(this);


        capture = findViewById(R.id.bt_capture);

        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                helper.dispatchTakePictureIntent();
                //Intent c = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //startActivityForResult(c, 1);
            }
        });
    }
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode,resultCode,data);
            if(requestCode == CameraHelper.REQUEST_IMAGE_CAPTURE){
                final Bitmap photo = helper.getBitmap(resultCode);
                final File photoFile = helper.getFile(resultCode);
                picture=findViewById(R.id.im_image);
                picture.setImageBitmap(photo);

                sendimage(photoFile);


                /*try {
                    ClassifiedImages pic=vrClient.classify(new ClassifyOptions.Builder().imagesFile(photoFile).build()).execute();


                    Log.i(TAG, "onActivityResult: suceess");



                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }*/


            }
    }

    private void sendimage(final File photoFile) {


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    ClassifiedImages pic=vrClient.classify(new ClassifyOptions.Builder().imagesFile(photoFile).build()).execute();


                    ClassifiedImage result=pic.getImages().get(0);
                    final ClassifierResult res=result.getClassifiers().get(0);

                    final StringBuffer name=new StringBuffer();
                    for (ClassResult ob: res.getClasses()){
                        if (ob.getScore()> 0.7f) {
                            name.append("<").append(ob.getClassName()).append("> ");
                            Log.i(TAG, "onActivityResult: suceess" +name);
                            dis(name);
                        }

                    }

                    Log.i(TAG, "onActivityResult: suceess" +res);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    private void dis(final StringBuffer name){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                obname = findViewById(R.id.txt_object);
                obname.setText("" +name);

            }
        });
    }

}


