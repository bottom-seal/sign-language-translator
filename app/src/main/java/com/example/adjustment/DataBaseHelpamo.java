package com.example.adjustment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelpamo extends SQLiteOpenHelper {
    private static final String DATABASENAME ="test";
    private static final int DATABASEVERSION =1;
    private static final String TABLENAME="test";
    public DataBaseHelpamo(Context context)
    {
        super(context,DATABASENAME,null,DATABASEVERSION);
    }
    public void onCreate(SQLiteDatabase db)
    {
        String sql="CREATE TABLE "+TABLENAME+"("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "name   TEXT,"+
                "message  TEXT,"+
                "position TEXT)";
        db.execSQL(sql);
    }
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
    {
        String sql="DROP TABLE IF EXISTS "+TABLENAME;
        db.execSQL(sql);
        this.onCreate(db);
    }
}
