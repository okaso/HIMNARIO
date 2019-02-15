package himnario.hunter.com.himnario;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConexionSQLite extends SQLiteOpenHelper {
    public ConexionSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table himnos(" +
                "id TEXT primary key," +
                "nombre TEXT)");
        db.execSQL("Create Table letras(" +
                "id TEXT ," +
                " himno TEXT," +
                "foreign key(id)references himnos(id))");
        db.execSQL("Create Table favoritos(id TEXT ,foreign key (id)references himnos(id))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE EXISTS  himnos");
        db.execSQL("DROP TABLE EXISTS  letras");
        db.execSQL("DROP TABLE EXISTS  favoritos");


        onCreate(db);
    }
}
