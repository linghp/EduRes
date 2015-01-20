package com.cqvip.edures;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.cqvip.edures.base.C;
import com.cqvip.edures.db.DaoMaster;
import com.cqvip.edures.db.DaoMaster.DevOpenHelper;
import com.cqvip.edures.db.DaoSession;

public class MyApplication extends Application{
    public DaoMaster daoMaster;
    public DaoSession daoSession;
    public SQLiteDatabase db;
    
    @Override
    public void onCreate() {
    	// TODO Auto-generated method stub
    	super.onCreate();
        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this.getApplicationContext(), C.DBNAME, null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }
}
