package parcados.activities;

import parcados.services.BackgroundService;
import com.parcados.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends DrawerActivity {



	//--------------------------------------------------------------------------------------
	// Métodos
	//--------------------------------------------------------------------------------------
	/**
	 * Cuando se crea la aplicación
	 */
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_con);	
		getActionBar().setTitle("Parcados");
		if ( !BackgroundService.running )
			MyApplication.getAppContext().startService(new Intent(MyApplication.getAppContext(), BackgroundService.class));
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	/**
	 * Método que agrega items al action bar si se encuentran
	 */
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}


	/**
	 * Maneja el evento si de si selencciona un item en el action bar
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Lanza la activity de zonas
	 * @param v - el view
	 */
	public void buscarParqueadero ( View v ) { 
		super.buscarParqueadero(v);

	}

	/**
	 * Lanza la activity para calcular el precio del parqueadero seleccionado
	 * @param v - el view
	 */
	public void abrirCalculadora ( View v ) {
		super.abrirCalculadora(v);
	}
}
