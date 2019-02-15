package himnario.hunter.com.himnario;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    private EditText et1 ;
    private ListView lt1;
    private ConexionSQLite CSQL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1 = findViewById(R.id.editText);
        lt1 =findViewById(R.id.Titulos);

        //Creacion de la base de datos con las canciones
        CSQL= new ConexionSQLite(this,"Himnario",null,1);

        InsertarDatos();

        LeerDatos("");
    }

    public void Buscar(View view){
        String sql = et1.getText().toString();
        LeerDatos(sql);
    }
    private void ListarCancion(final List Canciones){
        lt1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Titulo= (String) Canciones.get(position);
                EnviarTitulo(Titulo);
            }
        });
    }

    private void EnviarTitulo(String cadena){
        Intent intent;
        intent= new Intent(MainActivity.this,HImno.class);
        intent.putExtra("ID",cadena.substring(0,1));
        if(intent!=null){
            startActivity(intent);
        }
        Toast.makeText(this,""+cadena.substring(0,1),Toast.LENGTH_LONG).show();
    }

    private void InsertarDatos(){
        SQLiteDatabase db= CSQL.getReadableDatabase();
        Cursor c = db.rawQuery("select id from himnos",null);
        int a=0;
        while(c.moveToNext()){
            a=c.getInt(0);
        }
        if(a==0) {
            SQLiteDatabase bd = CSQL.getWritableDatabase();

            bd.execSQL("Insert into himnos(id,nombre)values('1','Primera Cancion')");
            bd.execSQL("Insert into himnos(id,nombre)values('2','Segunda Cancion')");
            bd.execSQL("Insert into himnos(id,nombre)values('3','Tercera Cancion')");
            bd.execSQL("Insert into letras(id,himno)values('1','Tercera Cancion prueba')");
            bd.execSQL("Insert into letras(id,himno)values('2','Tercera Cancion prueba ')");
            bd.execSQL("Insert into letras(id,himno)values('3','Tercera Cancion prueba ')");
        }

    }
    private void LeerDatos(String condicion){
        ArrayAdapter<String> adapter;
        List<String> Canciones;
        Canciones = new ArrayList<>();

        SQLiteDatabase bd = CSQL.getReadableDatabase();
        String sql ="Select id,nombre from himnos  Order BY id";
        if(!condicion.isEmpty()){
            sql ="Select id,nombre from himnos where (id='"+condicion+"' OR nombre Like '%"+condicion+"%') Order BY id";
        }
        Cursor leer=bd.rawQuery(sql,null);
        int c=0;
        while(leer.moveToNext()){
            Canciones.add(leer.getString(0)+" : "+leer.getString(1));
            c++;

        }
        if(c==0){
            Canciones.add("No hay resultados...");
        }
        adapter = new ArrayAdapter<String>(this, R.layout.himnos, Canciones);
        lt1.setAdapter(adapter);
        ListarCancion(Canciones);

    }

}
