package com.example.adjustment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DBHelp extends SQLiteOpenHelper{
    String destPath;
    String destName;
    Context context;

    String dataCol = "dataName";
   /* public DBHelp(Context context){
        super(context,"sample.db",null,1);
        this.context = context;
        this.destPath = "/data/data/" + context.getPackageName() + "/databases";
        this.destName = destPath + "sample";
    }
    public void copydb() throws IOException {
        //destPath = "/data/data/" + context.getPackageName() + "/databases";
        //destName = destPath + "sample";
        File fPath = new File(destPath);
        File fPathWithName = new File(destName);
        if(!fPath.exists()){
            fPath.mkdirs();
            rawcopy(context.getAssets().open("sample"),new FileOutputStream(destName));
        }else
        {
            if(!fPathWithName.exists())
                rawcopy(context.getAssets().open("sample"),new FileOutputStream(destName));
        }
    }

    private static void rawcopy(InputStream sample, FileOutputStream fileOutputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = sample.read(buffer)) > 0){
            fileOutputStream.write(buffer, 0, len);
        }
        sample.close();
        fileOutputStream.close();
    }
*/

    //String dbName;
    //Context context;
    //String dbPath;
    @SuppressLint("SdCardPath")
    public DBHelp(Context mcontext, String name, int version){
        super(mcontext,name,null,version);


        this.context = mcontext;
        this.destPath = "/data/data/" + context.getPackageName() + "/databases";
        this.destName = name;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void CheckDB(){
        SQLiteDatabase checkDB = null;
        try {
            String filePath = destPath + destName;
            checkDB = SQLiteDatabase.openDatabase(filePath,null,0);
        }catch (Exception e){}
        if(checkDB != null)
        {
            Log.d("checkDB","Database already exists");
            checkDB.close();
        }
        else {
            CreateDatabase();
        }
    }
    public void CreateDatabase(){
        this.getReadableDatabase();

        try {
            InputStream is = context.getAssets().open(destName);
            OutputStream os = new FileOutputStream(destPath + "/"  + destName);

            byte[] buffer = new byte[1024];
            int len;
            while((len = is.read(buffer))>0){
                os.write(buffer,0,len);
            }
            os.flush();
            is.close();
            os.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d("CopyDB","Database copied");

    }
    public void OpenDatabase(){
        String filePath = destPath + destName;
        SQLiteDatabase.openDatabase(filePath,null,0);
    }
    public ArrayList<String> getAAns(String query){
        ArrayList<String> engList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(
                "dictionary",
                new String[]{dataCol},
                dataCol + " LIKE ?",
                new String[]{query + "%"},
                null,
                null,
                dataCol
        );
        //Cursor cursor = sqLiteDatabase.rawQuery("select dataName from dictionary where dataName like " + query + "%",null);
        int index = cursor.getColumnIndex(dataCol);
        while (cursor.moveToNext()){
            engList.add(cursor.getString(index));
        }
        sqLiteDatabase.close();
        cursor.close();
        return engList;
    }
    @SuppressLint("Range")
    public String GetExample(String word){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String ans = null;
        Cursor cursor = sqLiteDatabase.rawQuery("select * from dictionary where dataName = '" + word + "'",null);
        while (cursor.moveToNext()){
            ans = cursor.getString(cursor.getColumnIndex("dataExample"));
            break;
        }
        return ans;
    }
    @SuppressLint("Range")
    public String GetUrl(String word){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String ans = null;
        Cursor cursor = sqLiteDatabase.rawQuery("select * from dictionary where dataName = '" + word + "'",null);
        while (cursor.moveToNext()){
            ans = cursor.getString(cursor.getColumnIndex("url"));
            break;
        }
        return ans;
    }
    @SuppressLint("Range")
    public String GetId(int word){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String ans = null;
        Cursor cursor = sqLiteDatabase.rawQuery("select * from dictionary where id = '" + word + "'",null);
        while (cursor.moveToNext()){
            ans = cursor.getString(cursor.getColumnIndex("url"));
            break;
        }
        return ans;
    }
    @SuppressLint("Range")
    public String GetWord(int word){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String ans = null;
        Cursor cursor = sqLiteDatabase.rawQuery("select * from dictionary where id = '" + word + "'",null);
        while (cursor.moveToNext()){
            ans = cursor.getString(cursor.getColumnIndex("dataName"));
            break;
        }
        return ans;
    }
    @SuppressLint("Range")
    public String GetIdUrl(int word){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String ans = null;
        Cursor cursor = sqLiteDatabase.rawQuery("select * from dictionary where id = '" + word + "'",null);
        while (cursor.moveToNext()){
            ans = cursor.getString(cursor.getColumnIndex("url"));
            break;
        }
        return ans;
    }

    @SuppressLint("Range")
    public String GetIdItem(int word){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String ans = null;
        Cursor cursor = sqLiteDatabase.rawQuery("select * from dictionary where id = '" + word + "'",null);
        while (cursor.moveToNext()){
            ans = cursor.getString(cursor.getColumnIndex("dataName"));
            break;
        }
        return ans;
    }
    public void getAns(String query){
        ArrayList<String> engList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        /*Cursor cursor = sqLiteDatabase.query(
                "dictionary",
                new String[]{dataCol},
                dataCol + " LIKE ?",
                new String[]{query + "%"},
                null,
                null,
                dataCol
        );*/
        Cursor cursor = sqLiteDatabase.rawQuery("select dataExample from dictionary where dataName like ?",new String[]{"%手%"});
        int index = cursor.getColumnIndex(dataCol);
        while (cursor.moveToNext()){
            engList.add(cursor.getString(index));
        }
        sqLiteDatabase.close();
        cursor.close();
        //return engList;
    }

    /*@Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }*/
}
