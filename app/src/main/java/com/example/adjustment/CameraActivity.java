package com.example.adjustment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

public class CameraActivity extends AppCompatActivity {
    public static final String Extra_massage = "com.example.adjustment";
    Button opencam,finishcam;
    VideoView video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        opencam =(Button)findViewById(R.id.opencam);
        video = (VideoView)findViewById(R.id.videotest);
        finishcam = (Button)findViewById(R.id.finishcam);

       /* finishcam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Chatroom.class);
                intent.putExtra("我手機不見可以幫我找嗎",leftMessageInput);
                startActivity(intent);
            }
        });*/
    }


    public void videoplay(View v) {
        String videopath = "android.resource://com.example.adjustment/" + R.raw.video1;
        Uri uri=Uri.parse(videopath);
        video.setVideoURI(uri);
        video.start();
    }

    public void sendmessage(View view){
        Intent intent = new Intent(CameraActivity.this,Chatroom.class);
        String textToPass = "另外我想申請會員卡";
        intent.putExtra("textToPass", textToPass);
        Toast.makeText(CameraActivity.this, "另外我想申請會員卡", Toast.LENGTH_SHORT).show();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}