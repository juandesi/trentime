package com.tren.time;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.tren.time.R.id;

public class MainActivity extends Activity implements OnClickListener {

	private static String DB_NAME = "DB_URQUIZA";
	private static String C_COLUMNA_ID = "_id";
	private static String C_COLUMNA_NOMBRE_LINEA = "linea_nombre";
	private static String[] C_ARRAY_COLUMNAS_LINEAS = {C_COLUMNA_ID, C_COLUMNA_NOMBRE_LINEA};
	
 	private TrenTimeHelper db;
    private Cursor cursor;
    private SQLiteDatabase DataBase;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		// instanciamos todos los controles
		Button boton = (Button) findViewById(id.btnBuscar);
		Spinner cbLinea = (Spinner)findViewById(id.spinner1);
		
		//listener del boton 
		 boton.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
            	 selectEstacionesHorarios(v);
             }
		 });
		 
		 // instanciamos la db y la abrimos
		 db = new TrenTimeHelper(this,DB_NAME) ;
	     DataBase = db.openDataBase();
	     
	     // obtenemos todas los registros de la tabla
	     cursor = DataBase.query("lineas", C_ARRAY_COLUMNAS_LINEAS, "", null, null, null, null);
	     // creamos el adapter y lo llenamos con toas las lineas que tenemos en el array
	     MyCustomAdapter adapterLinea = new MyCustomAdapter(this,R.layout.custom_spinner_linea, ArrayLineas(cursor));
	     cbLinea.setAdapter(adapterLinea);
	     
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	// nos lleva a la siguienta activity
	public void selectEstacionesHorarios(View view){
		Intent i = new Intent(this,Estaciones.class);
		this.startActivity(i);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	// no devuelve un arrayString con los nombres de la estaciones, pasandole un cursor con registros de la tabla LINEAS
	public String[] ArrayLineas(Cursor c){
		
		ArrayList<String> mArrayList = new ArrayList<String>();
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
		    // The Cursor is now set to the right position
		    mArrayList.add(c.getString(c.getColumnIndex(C_COLUMNA_NOMBRE_LINEA)));
		}
		String[] arrayFinal = mArrayList.toArray(new String[mArrayList.size()]);
		return arrayFinal;
	}
	
	public class MyCustomAdapter extends ArrayAdapter<String>{

		  public MyCustomAdapter(Context context, int textViewResourceId,
		    String[] objects) {
		   super(context, textViewResourceId, objects);
		   // TODO Auto-generated constructor stub
		  }

		  @Override
		  public View getDropDownView(int position, View convertView,
		    ViewGroup parent) {
		   // TODO Auto-generated method stub
		   return getCustomView(position, convertView, parent);
		  }

		  @Override
		  public View getView(int position, View convertView, ViewGroup parent) {
		   // TODO Auto-generated method stub
		   return getCustomView(position, convertView, parent);
		  }

		  public View getCustomView(int position, View convertView, ViewGroup parent) {
		   // TODO Auto-generated method stub
		   //return super.getView(position, convertView, parent);

		   LayoutInflater inflater=getLayoutInflater();
		   View row=inflater.inflate(R.layout.custom_spinner_linea, parent, false);
		   TextView label=(TextView)row.findViewById(R.id.textView);
		   String[] lineas = ArrayLineas(cursor);
		   label.setText(lineas[position]);
		   return row;
		  } 
    }

}
