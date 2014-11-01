package parcados.activities;

import java.util.ArrayList;
import java.util.List;
import com.parcados.R;
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
	private String idbusq;


	//--------------------------------------------------------------------------------------
	// Métodos
	//--------------------------------------------------------------------------------------

	/**
	 * Maneja la creación del activity
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parqueaderos) ;
		getActionBar().setDisplayHomeAsUpEnabled(true) ;
		Intent intent = getIntent() ; 
		idbusq  = intent.getStringExtra("id")  ; 
		String tipo = intent.getStringExtra("tipo")  ; 
		
		if (tipo.equals(BusquedaParqueaderosActivity.EMPRESA))
		{
			ArrayList<Parqueadero> parqueaderos = Parcados.darInstancia(getApplicationContext()).darParqueaderosDeEmpresa(idbusq) ; 
			List<String> lista = new ArrayList<String>() ;
			for ( int i = 0 ;i < parqueaderos.size() ; i ++ ){
				lista.add(parqueaderos.get(i).darNombre()) ;
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , lista) ; 
			setListAdapter(adapter) ; 
		}
		else if (tipo.equals(BusquedaParqueaderosActivity.ZONAS))
		{
			ArrayList<Parqueadero> parqueaderos = Parcados.darInstancia(getApplicationContext()).darParqueaderosZona(idbusq) ; 
			List<String> lista = new ArrayList<String>() ;
			for ( int i = 0 ;i < parqueaderos.size() ; i ++ ){
				lista.add(parqueaderos.get(i).darNombre()) ;
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , lista) ; 
			setListAdapter(adapter) ; 
		}
		
	}

	/**
	 * Cuando se resume la activity
	 */
	@Override
	protected void onResume() {
		super.onResume();
	}

	/**
	 * Maneja la selección de un elemento de la lista
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(this, DetalleParqueaderoActivity.class) ;
		intent.putExtra("idparq", l.getItemAtPosition((int) id).toString() ) ;
		startActivity(intent) ;
	}


	/**
	 * Maneja el evento si de si selencciona un item en el action bar
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		if ( item.getItemId() == android.R.id.home ){
			finish() ; 
		}
		return super.onOptionsItemSelected(item);
	}

}
