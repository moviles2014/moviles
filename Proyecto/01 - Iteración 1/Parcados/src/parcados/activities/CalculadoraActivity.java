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
import android.widget.Button;

public class CalculadoraActivity extends Activity {
	
	/*
	 * boton para cambiar el estado de la calculadora
	 */
	private Button btn  ; 
	/*
	 * Maneja la creación de la activity 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculadora);
		getActionBar().setDisplayHomeAsUpEnabled(true) ;
		btn = (Button) findViewById(R.id.button2) ; 
		btn.setText( R.string.inciarTiempo ) ; 
		if ( Parcados.darInstancia(getApplicationContext()).isInicioCalculadora() ) 
			btn.setText(R.string.finalizarTiempo) ;
	}
	
	/**
	 * Método que agrega items al action bar si se encuentran
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calculadora, menu);
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
		if (id == R.id.ingresar_precio) {
			System.out.println( " ingresar precio");
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/*
	 * empieza a calcular el precio del parqueadero seleccionado según
	 * el tiempo que transcurre
	 */
	public void cambiarEstadoCalculadora ( View v ) { 
		
		Parcados.darInstancia(getApplicationContext()).toggleEstadoCalculadora() ; 
		
		if ( Parcados.darInstancia(getApplicationContext()).isInicioCalculadora() ) {
			btn.setText(R.string.finalizarTiempo) ;
			iniciarCalculadora() ; 
		}
		else { 
			btn.setText( R.string.inciarTiempo ) ; 
			finalizarCalculadora() ; 
		}
	}
		
	
	/*
	 * inicia registro del precio actual considerando el tiempo trasncurrido en parqueadero
	 */
	public void iniciarCalculadora (){
		System.out.println("iniciar calculadora");
	}
	
	/*
	 * restablece el estado de la calculadora para que el usuario pueda iniciar 
	 * calculos en otros parqueaderos
	 */
	public void finalizarCalculadora(){
		System.out.println("finalizar calculadora");
	}

	/*
	 * abre ventana modal para que el usuario ingrese la hora de inicio
	 */
	public void ingresarPrecio (View v){
		System.out.println("ingresar precio");
	}
}
