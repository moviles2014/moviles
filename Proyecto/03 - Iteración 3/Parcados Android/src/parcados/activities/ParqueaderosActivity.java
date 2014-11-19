package parcados.activities;

import java.util.ArrayList;
import java.util.List;
import com.parcados.R;
import parcados.mundo.Parcados;
import parcados.mundo.Parqueadero;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ParqueaderosActivity extends DrawerActivity implements OnItemClickListener {


	//--------------------------------------------------------------------------------------
	// Atributos
	//--------------------------------------------------------------------------------------

	/**
	 * Id de la zona
	 */
	private String idbusq;

	private ListView mListView;

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
		getActionBar().setTitle("Parqueaderos");
		mListView = (ListView) findViewById(R.id.listParqueaderosActivity);
		mListView.setOnItemClickListener(this);

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
			mListView.setAdapter(adapter) ; 
		}
		else if (tipo.equals(BusquedaParqueaderosActivity.ZONAS))
		{
			ArrayList<Parqueadero> parqueaderos = Parcados.darInstancia(getApplicationContext()).darParqueaderosZona(idbusq) ; 
			List<String> lista = new ArrayList<String>() ;
			for ( int i = 0 ;i < parqueaderos.size() ; i ++ ){
				lista.add(parqueaderos.get(i).darNombre()) ;
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , lista) ; 
			mListView.setAdapter(adapter) ; 
		}
		final Typeface mFont = Typeface.createFromAsset(getAssets(),
		"fonts/Oxygen-Regular.ttf"); 
		final ViewGroup mContainer = (ViewGroup) findViewById(
		android.R.id.content).getRootView();
		MyApplication.setAppFont(mContainer, mFont ,true );

	}

	/**
	 * Cuando se resume la activity
	 */
	@Override
	protected void onResume() {
		super.onResume();
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

	/**
	 * Maneja la selección de un elemento de la lista
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(this, DetalleParqueaderoActivity.class) ;
		intent.putExtra("idparq", mListView.getItemAtPosition((int) id).toString() ) ;
		startActivity(intent) ;

	}

}
