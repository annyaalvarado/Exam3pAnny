package com.example.exam3panny;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exam3panny.configuracion.Adaptador;
import com.example.exam3panny.configuracion.SQLiteConexion;
import com.example.exam3panny.configuracion.bdTransaccione;
import com.example.exam3panny.configuracion.medica;

import java.util.ArrayList;

public class ActivityLista extends AppCompatActivity {

    SQLiteConexion conexion;
    ListView listViewContacto;

    ArrayList<medica> listMedicos, listRes;

    ArrayList<String> listaStringContactos;

    Button btnAtras, btnEliminarContacto, btnMostrarImagen;

    medica SeleccionDatos;



    //    ArrayAdapter adapter;
    Adaptador adapter, adc;

    int idBusqueda;

    TextView tv;


    AlertDialog.Builder builder;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);



        SeleccionDatos = null;

        listarListViewMed();


        btnAtras = (Button) findViewById(R.id.btnAtrasMostrar);
        btnEliminarContacto = (Button) findViewById(R.id.btnEliminarMed);
        btnMostrarImagen = (Button) findViewById(R.id.btnMostrarImagen);


        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnEliminarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(SeleccionDatos != null){
                    builder = new AlertDialog.Builder(ActivityLista.this);

                    builder.setMessage("¿ESTAS SEGURO QUE DESEAS ELIMINAR ESTE REGISTRO?").setTitle("Alerta");

                    builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            eliminarMed();

                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    dialog = builder.create();
                    dialog.show();
                }else{
                    mostrarMensaje("Alerta", "FAVOR SELECCIONE UN REGISTRO");
                }


            }
        });

        btnMostrarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SeleccionDatos != null){
                    mostrarImagen();
                }else {
                    mostrarMensaje("Alerta", "FAVOR SELECCIONE UN REGISTRO");
                }
            }
        });



        listViewContacto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private long lastTouchTime = 0;
            private long currentTouchTime = 0;

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lastTouchTime = currentTouchTime;
                currentTouchTime = System.currentTimeMillis();

//                contactoSeleccionado = listContactos.get(i);


                tv = (TextView) view.findViewById(R.id.itemObjetId);
                idBusqueda = Integer.parseInt(tv.getText().toString());


                adc =(Adaptador) adapterView.getAdapter();

                listRes = adc.getFilterlist();

                for(int j=0;j<listRes.size();j++){
                    if(listRes.get(j).getId() == idBusqueda){
                        SeleccionDatos = listRes.get(j);
                        break;
                    }
                }

            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();


        obtenerListaMed();
        llenarListViewMed();

        SeleccionDatos = null;
    }

    private void listarListViewMed() {
        conexion = new SQLiteConexion(this, bdTransaccione.NAME_DATABASE, null, 1);
        listViewContacto = (ListView) findViewById(R.id.listViewContactos);
        //listViewContacto.setSelector(R.color.blue_200);

        obtenerListaMed();

//        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaStringContactos);
        adapter = new Adaptador(ActivityLista.this, listMedicos);

        listViewContacto.setAdapter(adapter);
    }

    private void obtenerListaMed() {
        SQLiteDatabase db = conexion.getReadableDatabase();

        medica tempCont = null;


        listMedicos = new ArrayList<>();

        Cursor cursor = db.rawQuery(bdTransaccione.SELECT_TABLE_Medicamentos, null);


        while (cursor.moveToNext()){

            tempCont = new medica();

            tempCont.setId(cursor.getInt(0));
            tempCont.setDescripcion(cursor.getString(1));
            tempCont.setCantidad(cursor.getString(2));
            tempCont.setTiempo(cursor.getString(3));
            tempCont.setPeriocidad(cursor.getString(4));
            tempCont.setImagen(cursor.getString(5));

            listMedicos.add(tempCont);

        }

        cursor.close();

        llenarListStringMed();

    }

    private void llenarListStringMed() {
        listaStringContactos = new ArrayList<>();

        for(medica c: listMedicos){

            listaStringContactos.add(c.toString());
        }
    }

    private void eliminarMed() {
        conexion = new SQLiteConexion(this, bdTransaccione.NAME_DATABASE, null, 1);
        SQLiteDatabase database = conexion.getWritableDatabase();


        int result = database.delete(bdTransaccione.TABLA_Medicamentos, bdTransaccione.ID+"=?",
                new String[]{SeleccionDatos.getId()+""});

        if(result>0){
            obtenerListaMed();
            llenarListViewMed();

            SeleccionDatos = null;
            Toast.makeText(getApplicationContext(), "Eliminado correctamente", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(), "Error:No se pudo eliminar", Toast.LENGTH_LONG).show();
        }
    }

    private void llenarListViewMed() {
        adapter = null;
//        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaStringContactos);

        adapter = new Adaptador(ActivityLista.this, listMedicos);
        listViewContacto.setAdapter(adapter);
    }

    private void mostrarMensaje(String titulo, String mensaje) {
        builder = new AlertDialog.Builder(ActivityLista.this);

        builder.setMessage(mensaje).setTitle(titulo);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dialog = builder.create();
        dialog.show();

    }

    public void mostrarImagen(){

        if(isTextEmpty(SeleccionDatos.getImagen())){
            mostrarMensaje("Alerta", "FAVOR AGREGAR IMAGEN");
            return;
        }

        builder = new AlertDialog.Builder(ActivityLista.this);

        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.imagen, null);

        builder.setView(view);

        dialog = builder.create();

        dialog.show();

        TextView text =(TextView) view.findViewById(R.id.textViewDialogPersonalizado);
        text.setText(SeleccionDatos.getDescripcion());

        ImageView imagen = (ImageView) view.findViewById(R.id.imageViewDialog);

//        Bitmap image = BitmapFactory.decodeFile(contactoSeleccionado.getImagen());
//        imagen.setImageBitmap(image);

        Uri uri = Uri.parse(SeleccionDatos.getImagen());

        imagen.setImageURI(uri);

        Button btnCerrar = (Button) view.findViewById(R.id.buttonDialog);

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }
    //Si el texto esta vacio
    private static boolean isTextEmpty(String text){
        return (text.length()==0)?true:false;
    }


}