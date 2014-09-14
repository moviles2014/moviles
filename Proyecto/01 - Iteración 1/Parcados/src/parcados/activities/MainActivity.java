package parcados.activities;

import java.io.IOException;
import java.io.InputStream;

import parcados.mundo.Parcados;
import parcados.sqlite.DAO;

import com.example.parcados.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

	//--------------------------------------------------------------------------------------
	// Métodos
	//--------------------------------------------------------------------------------------
	/**
	 * Cuando se crea la aplicación
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try {
			Parcados actual = Parcados.darInstancia(getApplicationContext());
			if ( actual.darZonas().isEmpty()){
				InputStream inputStream = getResources().getAssets().open("zonas");			
				actual.loadZonas(inputStream);
				inputStream = getResources().getAssets().open("parqueaderos");			
				actual.loadParq(inputStream);
				inputStream.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(" inicio ");
		
//		startService(new Intent(this, UpdaterServiceManager.class));
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		System.out.println(" resumio ");
	}

	/**
	 * Método que agrega items al action bar si se encuentran
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	/**
	 * Maneja el evento si de si selencciona un item en el action bar
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_about) {
			//FALTA
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Lanza la activity de zonas
	 * @param v - el view
	 */
	public void buscarParqueadero ( View v ) { 
		Intent intent = new Intent(this, ZonasActivity.class) ;
		startActivity(intent) ;
	}

	/**
	 * Lanza la activity para calcular el precio del parqueadero seleccionado
	 * @param v - el view
	 */
	public void abrirCalculadora ( View v ) { 
		Intent intent = new Intent(this, CalculadoraActivity.class) ;
		startActivity(intent) ;
	}
	
	public void irATestActivity ( View  v) 	{ 
		Intent intent = new Intent(this, TestActivity.class) ;
		intent.putExtra("valor", "valor 1" ) ;
		intent.putExtra("valor4", "valor 4" ) ;
		intent.putExtra("meque", 100 ) ; 
		startActivity ( intent) ; 

	}

	//	@Override
	//	public void onConfigurationChanged(Configuration newConfig) {
	//		super.onConfigurationChanged(newConfig);
	//
	//		if ( newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
	//			Toast.makeText(this, "Landscape"  , Toast.LENGTH_SHORT).show() ;
	//		}
	//		else { 
	//			Toast.makeText(this, "Portrait"  , Toast.LENGTH_SHORT).show() ;
	//		}
	//	}


}
