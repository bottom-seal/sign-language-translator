package com.example.adjustment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.example.adjustment.databinding.ChatroomBinding;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class Chatroom extends AppCompatActivity {
    private LinearLayout conversationLayout;
    private EditText leftMessageInput;
    private EditText rightMessageInput;
    private Button sendButton;
    private SQLiteDatabase db=null;
    private ArrayList<String> conversation;
    private EditText name=null;
    private EditText text=null;
    private ImageButton delete=null;

    private ImageButton gochatroom = null;

    private OperateTablec mytablec =null;
    private OperateTable mytable =null;
    private SQLiteOpenHelper helper=null;
    private String info=null;
    private Button mediapipe_btn;
    private DataBaseHelp dbHelper;
    private DataBaseHelpamo dbHelperamo;
    private ArrayList<String> messages;//0808
    private static final int SPEECH_REQUEST_CODE = 123;
    private ArrayList<String> message;
    private ArrayList<String> position;
    private static final int REQUEST_CODE_SECOND_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);


        helper = new DataBaseHelp(this);
        dbHelperamo = new DataBaseHelpamo(this);
//        mytablec = new OperateTablec(helper.getWritableDatabase());
        mediapipe_btn = (Button) findViewById(R.id.mediapipebutton);

        mediapipe_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Chatroom.this,WebActivity.class);
                /*Uri uri = Uri.parse("https://morgem123.github.io/sign_language.html");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);*/
                startActivityForResult(intent, REQUEST_CODE_SECOND_ACTIVITY);
            }
        });
        this.name=(EditText)super.findViewById(R.id.name);
        this.text=(EditText)super.findViewById(R.id.leftMessageInput);
        this.delete= (ImageButton)  super.findViewById(R.id.delete);
        this.gochatroom= (ImageButton)  super.findViewById(R.id.gochatroom);

        conversationLayout = findViewById(R.id.conversationLayout);
        leftMessageInput = findViewById(R.id.leftMessageInput);
        rightMessageInput = findViewById(R.id.rightMessageInput);
        sendButton = findViewById(R.id.sendButton);

   //     Button addMessageButton = findViewById(R.id.addMessageButton);
        LinearLayout conversationLayout = findViewById(R.id.conversationLayout);

        message = new ArrayList<>();
        position = new ArrayList<>();



        dbHelper = new DataBaseHelp(this);
        Intent it=super.getIntent();
        info=it.getStringExtra("info");
        helper=new DataBaseHelp(this);
        helper.getWritableDatabase();
        dbHelperamo.getWritableDatabase();
        mytable=new OperateTable(helper.getWritableDatabase());
        mytablec=new OperateTablec(dbHelperamo.getWritableDatabase());
        message.addAll(mytablec.selectMessage(info));
        position.addAll(mytablec.selectPosition(info));
//        message.
        tip t=mytable.t(info);
//        tipc tc = mytablec.t(info);
        name.setText(t.getName());
        text.setText(t.getText());


        Intent intentidk = getIntent();
        String message1 = intentidk.getStringExtra("initialText");
        leftMessageInput.setText(message1);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog myAlertDialog = new AlertDialog.Builder(Chatroom.this)
                        .setTitle("提示" )
                        .setMessage("確定刪除【"+name.getText()+"】聊天室嗎？" )
                        .setPositiveButton("是" , new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                mytable.delete(info);
                                Toast.makeText(getApplicationContext(),"刪除成功",Toast.LENGTH_SHORT).show();
                                Chatroom.this.finish();
                            }
                        })
                        .setNegativeButton("否" , null)
                        .show();
            }
        });

        gochatroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Chathome.class);
                startActivity(intent);
            }
        });

        conversation = new ArrayList<>();
        for(int i=0;i<message.size();i++){
            conversation.add(position.get(i) + " " + message.get(i));
        }
        updateConversationLayout();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String leftMessage = leftMessageInput.getText().toString().trim();
                String rightMessage = rightMessageInput.getText().toString().trim();
//               if (info != null && rightMessage != null) {
//                    mytablec.insert(info, rightMessage);
//                }
                if (!leftMessage.isEmpty()) {
                    conversation.add("Left: " + leftMessage);
                    mytablec.insert(info, leftMessage,"Left:");//1 is the left hand side
                    leftMessageInput.setText("");
                }
                else
                if (!rightMessage.isEmpty()) {
                    conversation.add("Right: " + rightMessage);
                    mytablec.insert(info, rightMessage,"Right:");//2 is the right hand side
                    rightMessageInput.setText("");
                }

                updateConversationLayout();
            }
        });


   /*     addMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conversation.add("Left: 可以幫我嗎");
                updateConversationLayout();
            }
        });*/

        Button speechToTextButton = findViewById(R.id.speechToTextButton);
        speechToTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 啟動語音辨識 Intent
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "說些什麼...");

                startActivityForResult(intent, SPEECH_REQUEST_CODE);
            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==  REQUEST_CODE_SECOND_ACTIVITY && resultCode == Activity.RESULT_OK) {
            String textReceived = data.getStringExtra("textToPass");
            // 在EditText中显示接收到的文本
            leftMessageInput.setText(textReceived);
        }

        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result != null && !result.isEmpty()) {
                String spokenText = result.get(0);
                rightMessageInput.setText(spokenText);
            }
        }
    }



    private void updateConversationLayout() {
        conversationLayout.removeAllViews();
        for (String message : conversation) {
            View messageView = getMessageView(message);
            conversationLayout.addView(messageView);
        }
    }

    private View getMessageView(String message) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View messageView;

        if (message.startsWith("Left:")) {
            messageView = inflater.inflate(R.layout.left_message_layout, null);
        } else {
            messageView = inflater.inflate(R.layout.right_message_layout, null);
        }

        TextView messageTextView = messageView.findViewById(R.id.messageTextView);
        messageTextView.setText(message.substring(message.indexOf(":") + 1).trim());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        if (message.startsWith("Left:")) {
            layoutParams.gravity = Gravity.START;
        } else {
            layoutParams.gravity = Gravity.END;
        }

        messageView.setLayoutParams(layoutParams);

        return messageView;
    }
}