package com.tren.time;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tren.time.R.id;

public class MuestraHorario extends Activity {
	// Declaramos las variables
	private TrenTimeHelper DataBaseHelper;
	private SQLiteDatabase DataBase;
	private String sentidoLetra, sentidoAMostrar;
	private Cursor cursorResult;
	private String estacionNombre, estacionNumero;
	private String horaEnteraString;
	private int sentidoInt ;

 	private ArrayList<String> horariosCadenas = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_muestra_horario);
		
		TextView tvNombreEstacion = (TextView) findViewById(id.nombreDeEstacion);
		TextView tvSentidoDelTren =  (TextView) findViewById(id.sentido) ;
		
		Bundle itemsRecibidos = getIntent().getExtras();
		String estacionNombre = itemsRecibidos.getString("EstacionLLegada");
		String horaEnteraString = String.valueOf(itemsRecibidos.getInt("horaSeleccionada"));
		String estacionNumero = "E"+String.valueOf(itemsRecibidos.getInt("numEstacion"));
		int sentidoInt = itemsRecibidos.getInt("sentido");
		String sentido, sentidoAMostrar;
		
		// si es menor a 0 es que Vuelta si es mayor a 0 es Ida
		if (sentidoInt >= 0){
			sentido ="V";
			sentidoAMostrar ="Con Destino a Federico Lacroze";
		}
		else{
			sentido ="I";
			sentidoAMostrar = "Con Destino a Lemos";
		}
		
		
		tvSentidoDelTren.setText(sentidoAMostrar);
		
		// obtenemos una instancia de nuestra base
        DataBaseHelper = new TrenTimeHelper(this,DBStrings.DB_NAME) ;
        // la abrimos
        DataBase = DataBaseHelper.openDataBase();
       
       
        // cambiamos el nombre de la estacion de llegada en el activity
        tvNombreEstacion.setText(estacionNombre);
        
//        // Obtenemos todas las estaciones de la tabla ESTACIONES
//        cursorResult = DataBase.query(DBStrings.C_TABLA_HORARIOS,DBStrings.C_ARRAY_COLUMNAS_HORARIOS, 
//					estacionNumero +" >= "+ horaEnteraString +" AND "+ DBStrings.C_COLUMNA_SENTIDO +" = "+ "'"+sentido+"'" 
//					,null,null,null,null,"3");	
//       	
//       	// nos paramos en el primero y sacamos el horario
//       	if (cursor!= null && cursor.getCount()>0){
//       		cursor.moveToFirst();
//       		horariosCadenas.add("Pasa: " + transformarAHoraLinda(cursor.getString(cursor.getColumnIndex(estacionNumero))));
//       		
//       		if(!cursor.isLast()){
//	       	cursor.moveToNext();
//	       	horariosCadenas.add("Pasa: " + transformarAHoraLinda(cursor.getString(cursor.getColumnIndex(estacionNumero))));
//       		}
//       		
//       		if(!cursor.isLast()){
//	       	cursor.moveToNext();
//	       	horariosCadenas.add("Pasa: " + transformarAHoraLinda(cursor.getString(cursor.getColumnIndex(estacionNumero))));
//       		}
//       	}
       	while(){
       		
       	}
        
       	String[] arrayHorariosSeleccionado = horariosCadenas.toArray(new String[horariosCadenas.size()]);
       	CustomList adapter = new CustomList(this, arrayHorariosSeleccionado);
       	ListView list = (ListView)findViewById(id.listResultados);
       	list.setAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.muestra_horario, menu);
		return true;
	}

//	SELECT * FROM 
//  ((SELECT * FROM tipo_elemento_estructur al tee1 WHERE tee1.id > 8 ORDER BY tee1.id)
//  UNION
//  (SELECT * FROM tipo_elemento_estructural tee2 ORDER BY tee2.id LIMIT 3)) asd
//LIMIT 3;
	
	public Cursor ObtenerLosSiguientesHorarios(){
		Cursor l_cursor = DataBase.rawQuery("SELECT * FROM "+ DBStrings.C_TABLA_URQUIZA,null);	
		return l_cursor;
	}
	
	private String transformarAHoraLinda(String horaMinuto){
		String min= horaMinuto.substring(horaMinuto.length() - 2);
		String hora= horaMinuto.substring(0,horaMinuto.length() - 2);
		
		return hora +":" + min + " hs";
	
	}
	
	//////////////

	public class CustomList extends ArrayAdapter<String>{
	 
	private final Activity context;
	private final String[] horariosString;
	
	public CustomList(Activity context,String[] horariosString) {
		super(context, R.layout.custom_lista, horariosString);
		this.context = context;
		this.horariosString = horariosString;
	 
	}
	
	private void ObtenerBundles(){
		
		Bundle itemsRecibidos = getIntent().getExtras();
		String estacionLLegadaNombre = itemsRecibidos.getString("EstacionLLegada");
		String horaSeleccionada = String.valueOf(itemsRecibidos.getInt("horaSeleccionada"));
		String estacionLLegadaColumna = "E"+String.valueOf(itemsRecibidos.getInt("numEstacion"));
		int sentido = itemsRecibidos.getInt("sentido");
		
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.custom_lista, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.txtLista);
		 
		ImageView imageView = (ImageView) rowView.findViewById(R.id.imgLista);
		txtTitle.setText(horariosString[position]);
		 
		imageView.setImageResource(R.drawable.logitoazul);
		return rowView;
	}
	}
	

}
