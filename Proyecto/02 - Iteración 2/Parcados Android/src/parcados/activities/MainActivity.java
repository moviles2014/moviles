package parcados.activities;

import java.io.IOException;
import java.io.InputStream;
import parcados.mundo.Parcados;
import parcados.services.BackgroundService;
import com.parcados.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

	//--------------------------------------------------------------------------------------
	// M�todos
	//--------------------------------------------------------------------------------------
	/**
	 * Cuando se crea la aplicaci�n
	 */
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if ( !BackgroundService.running )
			MyApplication.getAppContext().startService(new Intent(MyApplication.getAppContext(), BackgroundService.class));
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	/**
	 * M�todo que agrega items al action bar si se encuentran
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


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
		final Intent intent = new Intent(this, BusquedaParqueaderosActivity.class) ;


		final ProgressDialog dialog = ProgressDialog.show(this, "Cargando Informaci�n", "Por favor espere...", true);

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Parcados actual = Parcados.darInstancia(getApplicationContext());
					if ( actual.darEmpresas().isEmpty()){
						InputStream inputStream = getResources().getAssets().open("empresas");			
						actual.loadEmpresas(inputStream);
						inputStream = getResources().getAssets().open("parqueaderos");			
						actual.loadParq(inputStream);
						inputStream.close();	

					}					

					dialog.dismiss();
					startActivity(intent) ;
				} catch (IOException e) {

					e.printStackTrace();
				}		

			}
		}).start();

	}

	/**
	 * Lanza la activity para calcular el precio del parqueadero seleccionado
	 * @param v - el view
	 */
	public void abrirCalculadora ( View v ) {
		Intent intent = new Intent(this, CalculadoraActivity.class) ;
		startActivity(intent) ;

	}


}
