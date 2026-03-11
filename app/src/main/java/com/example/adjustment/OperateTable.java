package com.example.adjustment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperateTable {
    private static final String TABLENAME ="tip1";
    private SQLiteDatabase db=null;
    public OperateTable(SQLiteDatabase db)
    {
        this.db=db;
    }
    public void insert(String name,String text)
    {
        String sql="INSERT INTO "+TABLENAME+" (name,text) VALUES ('"+name+"','"+text+"')";
        this.db.execSQL(sql);


    }
    public void delete(String id)
    {
        String sql="DELETE FROM "+TABLENAME+" WHERE id='"+id+"'";
        this.db.execSQL(sql);


    }
    public void updata(String id,String name,String text)
    {
        String sql="UPDATE "+TABLENAME+" SET name ='"+name+"',text='"+text+"' WHERE id='"+id+"'";
        this.db.execSQL(sql);
    }
    public List<Map<String,Object>> getdata()
    {List<Map<String,Object>>list=new ArrayList<Map<String,Object>>();
        Map<String,Object> map=new HashMap<String,Object>();

        String sql="SELECT id,name,text FROM "+TABLENAME;
        Cursor result =this.db.rawQuery(sql,null);
        for(result.moveToFirst();!result.isAfterLast();result.moveToNext())
        {
            map=new HashMap<String,Object>();
            map.put("id",result.getInt(0));
            map.put("tt",result.getString(1));
            list.add(map);
        }
        return  list;}
    public tip t(String id)
    {
        tip t=new tip();

        String sql="SELECT name,text FROM "+TABLENAME+" WHERE id ='"+id+"'";
        Cursor result =this.db.rawQuery(sql,null);
        result.moveToFirst();
        t.setName(result.getString(0));
        t.setText(result.getString(1));
        return t;
    }
}
