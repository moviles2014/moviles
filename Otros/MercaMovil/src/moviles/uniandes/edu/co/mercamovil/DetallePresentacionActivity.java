package moviles.uniandes.edu.co.mercamovil;

import moviles.uniandes.edu.co.mundo.MercaMovil;
import moviles.uniandes.edu.co.mundo.Presentacion;
import classattendance.mundo.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetallePresentacionActivity extends Activity 
{
	private MercaMovil instancia;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle_presentacion);
		instancia = MercaMovil.darInstancia(getApplicationContext());
		
		Intent intent = getIntent();
		
		String nombreProducto = intent.getStringExtra("nombreProducto");
		int tamanioPresentacion = Integer.parseInt(intent.getStringExtra("presentacion").split(" ")[0]);
		String unidadPresentacion = intent.getStringExtra("presentacion").split(" ")[1];
		
		Presentacion presentacion = instancia.darPresentacion(instancia.darProductoPorNombre(nombreProducto), tamanioPresentacion, unidadPresentacion);

		TextView txtTituloPresentacion = (TextView)findViewById(R.id.txtTituloPresentacion);
		txtTituloPresentacion.setText("Presentaciones del producto " + nombreProducto);
		TextView txtTamanioPresentacion = (TextView)findViewById(R.id.txtTamanioPresentacion);
		txtTamanioPresentacion.setText(Integer.toString(tamanioPresentacion));
		TextView txtUnidadPresentacion = (TextView)findViewById(R.id.txtUnidadPresentacion);
		txtUnidadPresentacion.setText(unidadPresentacion);
		TextView txtCantidadMinimaPresentacion = (TextView)findViewById(R.id.txtCantidadMinimaPresentacion);
		txtCantidadMinimaPresentacion.setText(Integer.toString(presentacion.getCantMinima()));
		TextView txtNumActualEnDespensa = (TextView)findViewById(R.id.txtNumActualEnDespensa);
		txtNumActualEnDespensa.setText(Integer.toString(presentacion.getNumActualEnDespensa()));
		TextView txtUltimoPrecioCompra = (TextView)findViewById(R.id.txtUltimoPrecioCompra);
		txtUltimoPrecioCompra.setText(Double.toString(presentacion.getUltimoPrecioDeCompra()));
		
	}

}
