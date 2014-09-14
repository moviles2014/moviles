package parcados.activities;

import parcados.mundo.Parcados;
import parcados.mundo.Parqueadero;

import com.parcados.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class DetalleParqueaderoActivity extends Activity {

	private Parqueadero actual;
	
	
	private TextView tx3 ; 
	/**
	 * Cuando se crea la aplicación
	 */
	
	TextView tx1 ;
	String nombreParqueadero ; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle_parqueadero) ;
		getActionBar().setDisplayHomeAsUpEnabled(true) ;
		Intent intent = getIntent(); 		
		String idparq = intent.getStringExtra("idparq");
		String idzona = intent.getStringExtra("idzona");	
		System.out.println(idzona);
		actual = Parcados.darInstancia(getApplicationContext()).darParqueaderosDeZona(Integer.parseInt(idzona)).get(Integer.parseInt(idparq));

		tx1 = (TextView) findViewById(R.id.textView1) ;
		tx1.setText(actual.darNombre()) ;
		nombreParqueadero = actual.darNombre() ;
		TextView tx2 = (TextView) findViewById(R.id.textView2) ;
		tx2.setText(Integer.toString(actual.darCupos())) ; 
		tx3 = (TextView) findViewById(R.id.textView3) ;
		tx3.setText(Integer.toString(actual.darPrecio())) ; 
		TextView tx4 = (TextView) findViewById(R.id.textView4) ;
		tx4.setText(actual.darHorario()) ; 
		TextView tx5 = (TextView) findViewById(R.id.textView5) ; 
		tx5.setText(actual.darCaracteristicas()) ; 
		TextView tx6 = (TextView) findViewById(R.id.textView6) ;
		tx6.setText(actual.darDireccion()) ; 

	}
	
	/**
	 * Método que agrega items al action bar si se encuentran
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.parqueadero_detalle, menu);
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
		
		if ( item.getItemId() == android.R.id.home ){
			finish() ; 
		}
		
		int id = item.getItemId();
		System.out.println( ""+ id );
		if (id == R.id.consultar_cupos) {
			//FALTA
			return true;
		}
		else if (id == R.id.reiniciar_precio) {
			Parcados.darInstancia(getApplicationContext()).actualizarPrecioParqueadero(nombreParqueadero, -1) ; 
			finish() ; 
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/*
	 * selecciona el parqueadero actual para considerarlo en la calculadora
	 */
	public void seleccionarParqueadero ( View v ) {
		Intent intent = new Intent(this, CalculadoraActivity.class) ;
		intent.putExtra("precio", Integer.parseInt( tx3.getText().toString())) ;
		intent.putExtra("NombreParqueadero", nombreParqueadero ) ;
		startActivity(intent) ; 
	} 

}
