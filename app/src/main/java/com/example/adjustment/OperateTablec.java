package com.example.adjustment;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperateTablec {
    private static final String TABLENAME ="test";
    private SQLiteDatabase db=null;
    public OperateTablec(SQLiteDatabase db)
    {
        this.db=db;
    }
    public void insert(String name,String text,String position)
    {
        String sql="INSERT INTO "+TABLENAME+" (name,message,position) VALUES ('"+name+"','"+text+"','"+position+"')";
        this.db.execSQL(sql);

    }
    public void updata(String id,String name,String text)
    {
        String sql="UPDATE "+TABLENAME+" SET name ='"+name+"',position='"+text+"' WHERE name='"+id+"'";
        this.db.execSQL(sql);
    }
    public void delete(String id)
    {
        String sql="DELETE FROM "+TABLENAME+" WHERE id='"+id+"'";
        this.db.execSQL(sql);
    }
    public ArrayList<String> selectMessage(String id){
        ArrayList<String> list = new ArrayList<>();
        String sql="SELECT message FROM "+TABLENAME+" WHERE name ='"+id+"'";
        Cursor result =this.db.rawQuery(sql,null);
        int index = result.getColumnIndex("message");
        result.moveToFirst();
        while(result.moveToNext()){
            list.add(result.getString(index));
        }
        return list;
    }
    public ArrayList<String> selectPosition(String id){
        ArrayList<String> list = new ArrayList<>();
        String sql="SELECT position FROM "+TABLENAME+" WHERE name ='"+id+"'";
        Cursor result =this.db.rawQuery(sql,null);
        int index = result.getColumnIndex("position");
        result.moveToFirst();
        while(result.moveToNext()){
            list.add(result.getString(index));
        }
        return list;
    }
    public tipc t(String id)
    {
        tipc t=new tipc();

        String sql="SELECT message,position FROM "+TABLENAME+" WHERE name ='"+id+"'";
        Cursor result =this.db.rawQuery(sql,null);
        result.moveToFirst();
        t.setName(result.getString(0));
        t.setText(result.getString(1));
        return t;
    }
}
