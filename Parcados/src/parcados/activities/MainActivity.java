package parcados.activities;

import com.example.parcados.R;

import parcados.mundo.Parcados;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputFilter.LengthFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


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
