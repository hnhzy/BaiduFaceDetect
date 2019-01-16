package com.hzy.baidufacedetect;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private final int PICK_IMAGE = 1;
    private MyThread mMyThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClicked(View view) {
        /**
         * 此处为相册图片代码
         */
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        /**
         * 此处为拍照图片代码
         */
//        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, PICK_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 此处为相册图片代码
         */
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData
                () != null) {
            Uri uri = data.getData();
            try {
                //将图片显示到ImageView
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ImageView imageView = findViewById(R.id.imageView1);
                imageView.setImageBitmap(bitmap);

                //压缩图片并将bitmap转为byte[]
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
                byte[] bt = bos.toByteArray();
                mMyThread=new MyThread(bt);
                mMyThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 此处为拍照图片代码
         */
//        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data
//                .getExtras() != null) {
//            Bundle bundle = data.getExtras();
//            Bitmap bitmap = (Bitmap) bundle.get("data");
//            ImageView imageView = findViewById(R.id.imageView1);
//            imageView.setImageBitmap(bitmap);
//            //压缩图片并将bitmap转为byte[]
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
//            byte[] bt = bos.toByteArray();
//            mMyThread=new MyThread(bt);
//            mMyThread.start();
//        }
    }

    //
    private static class MyThread extends Thread {

        private byte[] bt;

        public MyThread(byte[] bt) {
            this.bt = bt;
        }

        @Override
        public void run() {
            FaceDetect.detect(bt);//人脸检测
        }
    }
}
