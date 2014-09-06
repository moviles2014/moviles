package moviles.uniandes.edu.co.mercamovil;

import java.text.SimpleDateFormat;

import moviles.uniandes.edu.co.mundo.MercaMovil;
import moviles.uniandes.edu.co.mundo.Producto;
import classattendance.mundo.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class DetalleProductoActivity extends Activity 
{
	private MercaMovil instancia;
	private String nombreProducto;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle_producto);
		instancia = MercaMovil.darInstancia(getApplicationContext());
		
		Intent intent = getIntent();
		nombreProducto = intent.getStringExtra("nombreProducto");
		Producto p = instancia.darProductoPorNombre(nombreProducto);
		
		TextView nombre = (TextView)findViewById(R.id.txtNombreProducto);
		nombre.setText(nombreProducto);
		TextView marca = (TextView)findViewById(R.id.txtMarcaProducto);
		marca.setText(p.getMarca());
		TextView categoria = (TextView)findViewById(R.id.txtCategoria);
		categoria.setText(p.getCategoria());
		TextView fecha = (TextView)findViewById(R.id.txtFechaUltimaCompra);
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		fecha.setText(df.format(p.getFechaUltimaCompra()));
		
		ListView listaPresentaciones = (ListView)findViewById(R.id.lstPresentacionesProducto);
		String[ ] presentaciones = instancia.darPresentacionesProducto( p );
		ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1,presentaciones);
		listaPresentaciones.setAdapter(adapter);
		listaPresentaciones.setOnItemClickListener(new OnItemClickListener() {
			@SuppressWarnings("rawtypes")
			public void onItemClick(AdapterView parent, View view,
					int position, long id) {
				String presentacion = ((TextView) view).getText().toString();
				Intent intent = new Intent(getApplicationContext(), DetallePresentacionActivity.class);
				intent.putExtra("presentacion", presentacion);
				intent.putExtra("nombreProducto", nombreProducto);
				startActivity(intent);
			}
		});

	}

}
