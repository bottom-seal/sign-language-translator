package com.example.adjustment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelp extends SQLiteOpenHelper {
    private static final String DATABASENAME ="tip1";
    private static final int DATABASEVERSION =1;
    private static final String TABLENAME="tip1";
    public DataBaseHelp(Context context)
    {
        super(context,DATABASENAME,null,DATABASEVERSION);
    }
    public void onCreate(SQLiteDatabase db)
    {
        String sql="CREATE TABLE "+TABLENAME+"("+
                "id   INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "name  VARCHAR(50) NOT NULL,"+
                "text  VARCHAR(50) NOT NULL)";
        db.execSQL(sql);
    }
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
    {
        String sql="DROP TABLE IF EXISTS "+TABLENAME;
        db.execSQL(sql);
        this.onCreate(db);

    }
}
