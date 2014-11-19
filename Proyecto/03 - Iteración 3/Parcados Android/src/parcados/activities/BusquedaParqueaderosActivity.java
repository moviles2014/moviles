package parcados.activities;

import java.util.ArrayList;
import java.util.Collections;

import com.parcados.R;
import parcados.mundo.Parcados;
import parcados.mundo.Parqueadero;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class BusquedaParqueaderosActivity extends DrawerActivity implements OnItemSelectedListener, OnItemClickListener {

	public final static String ZONAS = "Zonas";
	public final static String EMPRESA = "Empresa";
	public final static String DIRECCION = "Dirección";
	public final static String LOCALIZACION = "Localización";

	private ListView mListView;

	private ArrayList<String> listaActual;

	private Spinner spinner;

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
		getActionBar().setTitle("Buscar Parqueadero");
		mListView = (ListView) findViewById(R.id.listBusquedaParqueaderos);
		mListView.setOnItemClickListener(this);
		

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


		((EditText)findViewById(R.id.textoBusqueda)).addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				updateList();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		listaActual = new ArrayList<String>();
		
		final Typeface mFont = Typeface.createFromAsset(getAssets(),
		"fonts/Oxygen-Regular.ttf"); 
		final ViewGroup mContainer = (ViewGroup) findViewById(
		android.R.id.content).getRootView();
		MyApplication.setAppFont(mContainer, mFont ,true );
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (spinner.getSelectedItem().toString().equals(LOCALIZACION))
		{
			spinner.setSelection(0, true);
			// Se puede usar cualquier objeto y lo que se muestra seria el toString
			listaActual = Parcados.darInstancia(getApplicationContext()).filtrarPorZonas();
			ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , listaActual) ; 
			mListView.setAdapter(adapter2) ; 
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
			listaActual = Parcados.darInstancia(getApplicationContext()).filtrarPorZonas();
			ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , listaActual) ; 
			mListView.setAdapter(adapter2) ; 
		}
		else if(parent.getItemAtPosition(position).toString().equals(EMPRESA))
		{
			// Se puede usar cualquier objeto y lo que se muestra seria el toString
			listaActual = Parcados.darInstancia(getApplicationContext()).filtrarPorEmpresa();
			ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , listaActual) ; 
			mListView.setAdapter(adapter2) ; 
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
			listaActual = lista;
			ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , listaActual) ; 
			mListView.setAdapter(adapter2) ; 
		}
		else if(parent.getItemAtPosition(position).toString().equals(LOCALIZACION))
		{
			super.abrirMapa(view);

		}

	}



	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

	@SuppressLint("DefaultLocale") public void updateList()
	{
		String edit = ((EditText) findViewById(R.id.textoBusqueda)).getText().toString();

		if (edit != null){


			ArrayList<String> nuevo = new ArrayList<String>();
			for (int i = 0; i < listaActual.size(); i++) {
				String array = listaActual.get(i).toString();
				if (array.toLowerCase().contains(edit.toLowerCase()))
					nuevo.add(array);
			}		
			ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , nuevo) ; 
			mListView.setAdapter(adapter2) ; 
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (spinner.getSelectedItem().toString().equals(ZONAS))
		{
			Intent intent = new Intent(this, ParqueaderosActivity.class) ;
			intent.putExtra("id", mListView.getItemAtPosition((int) id).toString() ) ; 
			intent.putExtra("tipo", ZONAS);
			startActivity(intent) ;
		}
		else if (spinner.getSelectedItem().toString().equals(EMPRESA))
		{
			Intent intent = new Intent(this, ParqueaderosActivity.class) ;
			intent.putExtra("id", mListView.getItemAtPosition((int) id).toString() ) ; 
			intent.putExtra("tipo", EMPRESA);
			startActivity(intent) ;
		}
		else if (spinner.getSelectedItem().toString().equals(DIRECCION))
		{
			Intent intent = new Intent(this, DetalleParqueaderoActivity.class) ;
			intent.putExtra("idparq", Parcados.darInstancia(getApplicationContext()).darParqueaderoPorDireccion(mListView.getItemAtPosition((int) id).toString()).darNombre() ) ; 
			startActivity(intent) ;
		}
		
	}



}
