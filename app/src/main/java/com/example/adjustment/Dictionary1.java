package com.example.adjustment;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class Dictionary1 extends AppCompatActivity {

    //private AutoCompleteTextView  autoCompleteTextView;
    private ImageView btn_img;
    private ImageView closeBtn;
    DBHelp db;
    ArrayList<String> newList;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary1);
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        db = new DBHelp(this,"sample.db",1);
        /*try {
            db.copydb();
            db.OpenDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        try {
            db.CheckDB();
            db.OpenDatabase();
        }catch (Exception e){}
        newList = new ArrayList<>();
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0){
                    newList.clear();
                    newList.addAll(db.getAAns(s.toString()));
                    autoCompleteTextView.setAdapter(new ArrayAdapter<String>(Dictionary1.this, android.R.layout.simple_list_item_1, newList));
                    autoCompleteTextView.setThreshold(1);
                    /*String[] countries = getResources().getStringArray(R.array.countries);
                    ArrayAdapter<String> arrAdpt = new ArrayAdapter<String>(MainActivity.this
                            , android.R.layout.simple_list_item_1, countries);
                    autoCompleteTextView.setAdapter(arrAdpt);*/
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String word = newList.get(position);
                getExample(word);
            }
        });
        //closeBtn = findViewById(R.id.closeBtn);
        /*btn_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCompleteTextView.showContextMenu();
            }
        });*/
        /*closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCompleteTextView.setText("");

            }
        });*/
        /*db = new DBHelp(this,"sample.db",1);
        try {
            db.CheckDB();
            db.OpenDatabase();
        }catch (Exception e){}*/
       /* String[] countries = getResources().getStringArray(R.array.countries);
        ArrayAdapter<String> arrAdpt = new ArrayAdapter<String>(this
        , android.R.layout.simple_list_item_1, countries);
        autoCompleteTextView.setAdapter(arrAdpt);
        autoCompleteTextView.setThreshold(1);*/
    }
    public void getExample(String word) {
        String ans = db.GetExample(word);
        String url = db.GetUrl(word);
        //Toast.makeText(this, url, Toast.LENGTH_LONG).show();

        Intent ii = new Intent(this,DictionarySearch.class);
        ii.putExtra("name",word);
        ii.putExtra("example",ans);
        ii.putExtra("url",url);
        startActivity(ii);
    }
}