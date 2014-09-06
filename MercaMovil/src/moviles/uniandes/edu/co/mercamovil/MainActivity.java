package moviles.uniandes.edu.co.mercamovil;

import java.util.List;

import moviles.uniandes.edu.co.mundo.ListaCompras;
import moviles.uniandes.edu.co.mundo.MercaMovil;

import classattendance.mundo.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
	
	private MercaMovil instancia;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		instancia = MercaMovil.darInstancia(getApplicationContext());
	}
	
	public void verProductos(View v)
	{
		Intent intent = new Intent(getApplicationContext(), VerProductosActivity.class);
		startActivity(intent);
	}
	
	public void verProductosVeteranos(View v)
	{
		Intent intent = new Intent(getApplicationContext(), VerProductosVeteranosActivity.class);
		startActivity(intent);
	}
	
	public void verListasCompras(View v)
	{
		Intent intent = new Intent(getApplicationContext(), VerListasComprasActivity.class);
		startActivity(intent);
	}
	
	public void generarLista(View v)
	{
		ListaCompras generada = instancia.generarListadeCompras();
		Intent intent = new Intent(getApplicationContext(), DetalleListaActivity.class);
		intent.putExtra("nombreLista", generada.getNombre());
		startActivity(intent);
		//ToReview
	}
	
	public void registrarConsumoProducto(View v)
	{
		Intent intent = new Intent(getApplicationContext(), RegistrarConsumoActivity.class);
		startActivity(intent);
	}
	
}
