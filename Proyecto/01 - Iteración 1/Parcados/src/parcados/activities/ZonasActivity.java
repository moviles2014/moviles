package parcados.activities;

import java.util.ArrayList;
import java.util.List;
import com.example.parcados.R;
import parcados.mundo.Parcados;
import parcados.mundo.Zona;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ZonasActivity extends ListActivity {


	//--------------------------------------------------------------------------------------
	// Métodos
	//--------------------------------------------------------------------------------------
	/**
	 * Método maneja la creación del activity
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_zonas) ; 
		ArrayList<Zona> zonas = Parcados.darInstancia(getApplicationContext()).darZonas() ; 
		getActionBar().setDisplayHomeAsUpEnabled(true) ;
		List<String> lista = new ArrayList<String>() ; 
		for ( int i = 0 ;i < zonas.size() ; i ++ ){
			lista.add(zonas.get(i).darNombre()) ;
		}
		// Se puede usar cualquier objeto y lo que se muestra seria el toString
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
		Intent intent = new Intent(this, ParqueaderosActivity.class) ;
		intent.putExtra("id", Long.toString(id) ) ; 
		startActivity(intent) ;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if ( item.getItemId() == android.R.id.home ){
			finish() ; 
		}
		return super.onOptionsItemSelected(item);
	}

}
