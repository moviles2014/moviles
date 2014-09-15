package parcados.activities;

import java.util.ArrayList;
import java.util.List;
import com.parcados.R;
import parcados.mundo.Parcados;
import parcados.mundo.Zona;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ZonasActivity extends ListActivity {


	//--------------------------------------------------------------------------------------
	// M�todos
	//--------------------------------------------------------------------------------------
	/**
	 * M�todo maneja la creaci�n del activity
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
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	/**
	 * Maneja la selecci�n de un elemento de la lista
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(this, ParqueaderosActivity.class) ;
		intent.putExtra("id", Long.toString(id) ) ; 
		startActivity(intent) ;
	}

}
