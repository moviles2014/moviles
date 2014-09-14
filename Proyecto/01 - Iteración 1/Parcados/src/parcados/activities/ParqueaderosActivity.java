package parcados.activities;

import java.util.ArrayList;
import java.util.List;

import com.example.parcados.R;

import parcados.mundo.Parcados;
import parcados.mundo.Parqueadero;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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
		getActionBar().setDisplayHomeAsUpEnabled(true) ;
		Intent intent = getIntent() ; 
		idzona  = Integer.parseInt(intent.getStringExtra("id"))  ; 

		ArrayList<Parqueadero> parqueaderos = Parcados.darInstancia(getApplicationContext()).darParqueaderosDeZona(idzona) ; 
		List<String> lista = new ArrayList<String>() ;
		for ( int i = 0 ;i < parqueaderos.size() ; i ++ ){
			lista.add(parqueaderos.get(i).darNombre()) ;
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , lista) ; 
		setListAdapter(adapter) ; 
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if ( item.getItemId() == android.R.id.home ){
			finish() ; 
		}
		return super.onOptionsItemSelected(item);
	
	}
	
	/**
	 * Maneja la selección de un elemento de la lista
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(this, DetalleParqueaderoActivity.class) ;
		intent.putExtra("idparq", Long.toString(position) ) ;
		intent.putExtra("idzona", Integer.toString(idzona) ) ;
		startActivity(intent) ;
		finish() ; 
	}
	

}
