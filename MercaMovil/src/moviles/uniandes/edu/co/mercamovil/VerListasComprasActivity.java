package moviles.uniandes.edu.co.mercamovil;

import moviles.uniandes.edu.co.mundo.MercaMovil;
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

public class VerListasComprasActivity extends Activity 
{
	private MercaMovil instancia;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista);
		instancia = MercaMovil.darInstancia(getApplicationContext());
		
		TextView titulo = (TextView)findViewById(R.id.txtTitulo);
		titulo.setText("Listas de Compra");
		
		ListView listasCompras = (ListView)findViewById(R.id.lista);
		String[ ] listas = instancia.darListas( );
		ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1,listas);
		listasCompras.setAdapter(adapter);
		
		//TODO Mostrar el detalle de la lista de compras seleccionada

	}

}
