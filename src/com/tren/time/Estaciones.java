package com.tren.time;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.tren.time.R.id;

public class Estaciones extends Activity {
		private static String DB_NAME = "DB_URQUIZA";
		private static String C_TABLA_ESTACIONES = "ESTACIONES";
		private static String C_TABLA_HORARIOS = "Horarios_Urquiza";	
		private static String C_COLUMNA_ID = "_id";
		private static String C_COLUMNA_NOMBRE_ESTACION = "estacion_nombre";
		
		private static String[] C_ARRAY_COLUMNAS_HORARIOS = {C_COLUMNA_ID, "e1","e2","e3","e4","e5","e6","e7","e8","e9","e10","e11",
														"e12","e13","e14","e15","e16","e17","e18","e19","e20","e21","e22","e23"};
		private static String[] C_ARRAY_COLUMNAS_ESTACIONES = {C_COLUMNA_ID, C_COLUMNA_NOMBRE_ESTACION};
		
	 	private TrenTimeHelper db;
	    private Spinner cbEstacionSalida;
	    private Spinner cbEstacionLLegada;
	    private TimePicker timePicker;
	    private Button btnBuscar;
	    private Cursor cursor;
	    private SQLiteDatabase DataBase;
	    
	    String[] estacionesTodas;

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	    	requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.activity_estaciones);

	        //Obtenemos la Hora y El minuto actual
	        int horaActual = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	        int minutoActual = Calendar.getInstance().get(Calendar.MINUTE);

	        //instanciamos los controles de la pantalla
	        cbEstacionLLegada = (Spinner) findViewById(R.id.spLLegada);
	        cbEstacionSalida = (Spinner) findViewById(R.id.spSalida);
	        timePicker = (TimePicker) findViewById(R.id.timePicker1);
	        btnBuscar = (Button) findViewById(id.btnBuscar);

	        //configuramos el time picker
	        timePicker.setIs24HourView(true);
	       // timePicker.setDescendantFocusability(timePicker.FOCUS_BLOCK_DESCENDANTS);
	        timePicker.setCurrentHour(horaActual);
	        timePicker.setCurrentMinute(minutoActual);
	        
	       
	        
	        // Instanciamos la db
	        
	        db = new TrenTimeHelper(this,DB_NAME) ;
	       DataBase = db.openDataBase();

	        // Obtenemos todas las estaciones de la tabla ESTACIONES
	        cursor = DataBase.query(C_TABLA_ESTACIONES,C_ARRAY_COLUMNAS_ESTACIONES, "", null, null,null, null);
	       	
	       	// Obtenemos la lista de estaciones
	         estacionesTodas = ArrayEstaciones(cursor);
	       	
	       // Hacemos el Adapter con el cursor LLenamos todos los Spiner.
	        MyCustomAdapter adapterEstaciones;
	        adapterEstaciones = new MyCustomAdapter(this, R.layout.custom_spinner, estacionesTodas );
	       // adapterEstaciones = new SimpleCursorAdapter(this,android.R.layout.simple_spinner_item,cursor,new String[] {C_COLUMNA_NOMBRE_ESTACION}, new int[] {android.R.id.text1});
	        adapterEstaciones.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	       //seteamos el adapter	        
	        cbEstacionSalida.setAdapter(adapterEstaciones);
	        cbEstacionSalida.setSelection(22);
	        cbEstacionLLegada.setAdapter(adapterEstaciones);
	        
	        //evento OnCLick del boton
			 btnBuscar.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	            	 launchMuestraHorario(v);
	             }
			 });
			 
			 

//	        if (savedInstanceState == null) {
//	            getSupportFragmentManager().beginTransaction()
//	                    .add(R.id.container, new PlaceholderFragment())
//	                    .commit();
//	        }
	    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.estaciones, menu);
		return true;
	}
	
	public void launchMuestraHorario(View v){
		Intent i = new Intent(this, MuestraHorario.class);
		
		// obtenemos la estacion seleccionada
		cursor.moveToPosition(cbEstacionSalida.getSelectedItemPosition());
		String estacion = cursor.getString(cursor.getColumnIndex(C_COLUMNA_NOMBRE_ESTACION)) ;
		i.putExtra("EstacionLLegada",estacion);
		
		// Obtenemos la hora entera para el query y la pasamos para la otra activity
		int horaExacta = armarHoraEntera();	
		i.putExtra("horaSeleccionada",horaExacta);
		//pasamos el numero de estacion
		i.putExtra("numEstacion",cbEstacionSalida.getSelectedItemPosition() + 1);
		//pasamos el indice del sentido
		i.putExtra("sentido", cbEstacionSalida.getSelectedItemPosition() -cbEstacionLLegada.getSelectedItemPosition());
		
		//starteamos la atvt
		startActivity(i);
	}
	
	public int armarHoraEntera(){
		int hora = timePicker.getCurrentHour();
		int minuto = timePicker.getCurrentMinute();
		int horaExacta = 0;
		
		horaExacta = hora*100 + minuto;
		
		return horaExacta;
	}

	public String[] ArrayEstaciones(Cursor c){
		
		ArrayList<String> mArrayList = new ArrayList<String>();
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
		    // The Cursor is now set to the right position
		    mArrayList.add(c.getString(c.getColumnIndex(C_COLUMNA_NOMBRE_ESTACION)));
		}
		String[] arrayFinal = mArrayList.toArray(new String[mArrayList.size()]);
		return arrayFinal;
	}

	////////////////////////////////
	
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
		   View row=inflater.inflate(R.layout.custom_spinner, parent, false);
		   TextView label=(TextView)row.findViewById(R.id.weekofday);
		   label.setText(estacionesTodas[position]);

		   return row;
		  } 
		    }

}


