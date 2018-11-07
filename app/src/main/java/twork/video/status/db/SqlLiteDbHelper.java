package twork.video.status.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import twork.video.status.object.status.CatagoryList;

public class SqlLiteDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "allstatus.sqlite";
    private static String DATABASE_PATH = null;
    private static final int DATABASE_VERSION = 1;
    Context ctx;
    private SQLiteDatabase db;

    public SqlLiteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.ctx = context;
        DATABASE_PATH = "/data/data/" + this.ctx.getPackageName() + "/databases/";
    }

    public void CopyDataBaseFromAsset() throws IOException {
        InputStream in = this.ctx.getAssets().open(DATABASE_NAME);
        Log.e("sample", "Starting copying");
        String outputFileName = DATABASE_PATH + DATABASE_NAME;
        new File("/data/data/" + this.ctx.getPackageName() + "/databases").mkdir();
        OutputStream out = new FileOutputStream(outputFileName);
        byte[] buffer = new byte[1024];
        while (true) {
            int length = in.read(buffer);
            if (length > 0) {
                out.write(buffer, 0, length);
            } else {
                Log.e("sample", "Completed");
                out.flush();
                out.close();
                in.close();
                return;
            }
        }
    }

    public void openDataBase() throws SQLException {
        this.db = SQLiteDatabase.openDatabase(DATABASE_PATH + DATABASE_NAME, null, 268435472);
    }

    public void onCreate(SQLiteDatabase db) {
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public ArrayList<CatagoryList> GetCategory(String tablename) {
        ArrayList<CatagoryList> categoryList = new ArrayList();
        Cursor cursor = getReadableDatabase().rawQuery("select * from " + tablename + ";", null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                CatagoryList categorydata = new CatagoryList();
                categorydata.setCategoryid(cursor.getInt(0));
                categorydata.setCategory(cursor.getString(1));
                categoryList.add(categorydata);
                cursor.moveToNext();
            }
        }
        this.db.close();
        return categoryList;
    }

    public ArrayList<String> GetMessageLIMIT(String as, String tablename) {
        Cursor cursor;
        ArrayList<String> categoryList = new ArrayList();
        if (tablename.equalsIgnoreCase("punjabi_status")) {
            cursor = getReadableDatabase().rawQuery("select message from " + tablename + " WHERE cat_id =" + as + ";", null);
        } else {
            cursor = getReadableDatabase().rawQuery("select status from " + tablename + " WHERE cat_id =" + as + ";", null);
        }
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                categoryList.add(cursor.getString(0));
                cursor.moveToNext();
            }
        }
        this.db.close();
        return categoryList;
    }
}
