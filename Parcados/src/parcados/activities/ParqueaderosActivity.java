package parcados.activities;

import java.util.ArrayList;
import java.util.List;

import com.example.parcados.R;

import parcados.mundo.Parcados;
import parcados.mundo.Parqueadero;
import parcados.mundo.Zona;


import android.R.integer;
import android.R.integer;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ParqueaderosActivity extends ListActivity {


	//--------------------------------------------------------------------------------------
	// Atributos
	//--------------------------------------------------------------------------------------

	/**
	 * Id de la zona
	 */
	private int idzona;


	//--------------------------------------------------------------------------------------
	// Métodos
	//--------------------------------------------------------------------------------------

	/**
	 * Maneja la creación del activity
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parqueaderos) ;

		Intent intent = getIntent() ; 
		int id  = Integer.parseInt(intent.getStringExtra("id"))  ; 

		ArrayList<Parqueadero> parqueaderos = Parcados.darInstancia(getApplicationContext()).darParqueaderosDeZona(id) ; 
		List<String> lista = new ArrayList<String>() ;
		for ( int i = 0 ;i < parqueaderos.size() ; i ++ ){
			lista.add(parqueaderos.get(i).darNombre()) ;
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String >(this, android.R.layout.simple_list_item_1 , lista) ; 
		setListAdapter(adapter) ; 
	}

	/**
	 * Maneja la selección de un elemento de la lista
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(this, DetalleParqueaderoActivity.class) ;
		intent.putExtra("idparq", position ) ;
		intent.putExtra("idzona", idzona ) ;
		startActivity(intent) ;
	}
}
