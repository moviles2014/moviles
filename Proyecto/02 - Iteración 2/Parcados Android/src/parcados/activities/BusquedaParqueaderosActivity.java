package parcados.activities;

import java.util.ArrayList;
import java.util.Collections;

import com.parcados.R;
import parcados.mundo.Parcados;
import parcados.mundo.Parqueadero;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class BusquedaParqueaderosActivity extends ListActivity implements OnItemSelectedListener {

	public final static String ZONAS = "Zonas";
	public final static String EMPRESA = "Empresa";
	public final static String DIRECCION = "Dirección";
	public final static String LOCALIZACION = "Localización";


	private Spinner spinner;
	
	private ProgressDialog dialogo;

	//--------------------------------------------------------------------------------------
	// Métodos
	//--------------------------------------------------------------------------------------
	/**
	 * Método maneja la creación del activity
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_busqueda_parqueaderos) ; 
		getActionBar().setDisplayHomeAsUpEnabled(true) ;

		spinner = (Spinner) findViewById(R.id.spinner_busqueda);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.busquedas_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		// Listener
		spinner.setOnItemSelectedListener(this);


	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		
		if (spinner.getSelectedItem().toString().equals(LOCALIZACION))
			dialogo.dismiss();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (spinner.getSelectedItem().toString().equals(LOCALIZACION))
		{
			spinner.setSelection(0, true);
			// Se puede usar cualquier objeto y lo que se muestra seria el toString
			ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , Parcados.darInstancia(getApplicationContext()).filtrarPorZonas()) ; 
			setListAdapter(adapter2) ; 
		}
	}

	/**
	 * Maneja la selección de un elemento de la lista
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		if (spinner.getSelectedItem().toString().equals(ZONAS))
		{
			Intent intent = new Intent(this, ParqueaderosActivity.class) ;
			intent.putExtra("id", l.getItemAtPosition((int) id).toString() ) ; 
			intent.putExtra("tipo", ZONAS);
			startActivity(intent) ;
		}
		else if (spinner.getSelectedItem().toString().equals(EMPRESA))
		{
			Intent intent = new Intent(this, ParqueaderosActivity.class) ;
			intent.putExtra("id", l.getItemAtPosition((int) id).toString() ) ; 
			intent.putExtra("tipo", EMPRESA);
			startActivity(intent) ;
		}
		else if (spinner.getSelectedItem().toString().equals(DIRECCION))
		{
			Intent intent = new Intent(this, DetalleParqueaderoActivity.class) ;
			intent.putExtra("idparq", Parcados.darInstancia(getApplicationContext()).darParqueaderoPorDireccion(l.getItemAtPosition((int) id).toString()).darNombre() ) ; 
			startActivity(intent) ;
		}

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

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {

		if(parent.getItemAtPosition(position).toString().equals(ZONAS))
		{
			// Se puede usar cualquier objeto y lo que se muestra seria el toString
			ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , Parcados.darInstancia(getApplicationContext()).filtrarPorZonas()) ; 
			setListAdapter(adapter2) ; 
		}
		else if(parent.getItemAtPosition(position).toString().equals(EMPRESA))
		{
			// Se puede usar cualquier objeto y lo que se muestra seria el toString
			ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , Parcados.darInstancia(getApplicationContext()).filtrarPorEmpresa()) ; 
			setListAdapter(adapter2) ; 
		}
		else if(parent.getItemAtPosition(position).toString().equals(DIRECCION))
		{
			ArrayList<Parqueadero> parqs = Parcados.darInstancia(getApplicationContext()).darTodosLosParqueaderos();
			ArrayList<String> lista = new ArrayList<String>();
			for (int i = 0; i < parqs.size(); i++) {
				lista.add(parqs.get(i).darDireccion());
			}
			Collections.sort(lista);
			// Se puede usar cualquier objeto y lo que se muestra seria el toString
			ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , lista) ; 
			setListAdapter(adapter2) ; 
		}
		else if(parent.getItemAtPosition(position).toString().equals(LOCALIZACION))
		{
			dialogo = ProgressDialog.show(this, "Cargando", "Por favor espere...", true);
			Intent intent = new Intent(this, MapActivity.class) ;			
			startActivity(intent) ;

		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

}
