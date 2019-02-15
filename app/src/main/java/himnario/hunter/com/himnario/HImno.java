package himnario.hunter.com.himnario;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class HImno extends AppCompatActivity {

    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_himno);
        ConexionSQLite SQL=new ConexionSQLite(this,"Himnario",null,1);
        SQLiteDatabase database=SQL.getReadableDatabase();
        SQLiteDatabase database1= SQL.getWritableDatabase();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String id = getIntent().getStringExtra("ID");
        tv1= findViewById(R.id.textView);
        Cursor leer=database.rawQuery("Select himno from letras where id='"+id+"'",null);
        while(leer.moveToNext()){
            tv1.setText(leer.getString(0));
        }
        setTitle(id);
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.ventanas,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id= item.getItemId();
        if(id == R.id.favorito) {
            item.setIcon(R.drawable.favorito1);
        }
        return super.onOptionsItemSelected(item);
    }

}
