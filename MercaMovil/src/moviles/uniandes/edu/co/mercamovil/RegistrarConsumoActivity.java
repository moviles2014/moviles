package moviles.uniandes.edu.co.mercamovil;

import moviles.uniandes.edu.co.mundo.MercaMovil;
import moviles.uniandes.edu.co.mundo.Presentacion;
import moviles.uniandes.edu.co.mundo.Producto;
import classattendance.mundo.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegistrarConsumoActivity extends Activity 
{
	private MercaMovil instancia;
	private String nombreLista;
	private Spinner spinnerProductos;
	private Spinner spinnerPresentaciones;
	private EditText cantidad;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registrar_consumo);
		instancia = MercaMovil.darInstancia(getApplicationContext());
		
		cantidad = (EditText)findViewById(R.id.editTextCantidadReg);
		spinnerProductos = (Spinner)findViewById(R.id.spinnerProductosReg);
		spinnerPresentaciones = (Spinner)findViewById(R.id.spinnerPresentacionesReg);
		
		String[ ] productos = instancia.darProductos();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,productos);
		spinnerProductos.setAdapter(adapter);
		spinnerProductos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		        String nombreProducto = spinnerProductos.getItemAtPosition(position).toString();
		        String[ ] presentaciones = instancia.darPresentacionesProducto(instancia.darProductoPorNombre(nombreProducto));
				ArrayAdapter<String> adapterPresentaciones = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,presentaciones);
				spinnerPresentaciones.setAdapter( adapterPresentaciones );
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        
		    }

		});
		
	}
	
	public void registrarConsumo(View v)
	{
		if( !cantidad.getText().toString().equals("") && spinnerPresentaciones.getSelectedItem() != null && spinnerProductos.getSelectedItem() != null )
		{
			Producto producto = instancia.darProductoPorNombre(spinnerProductos.getSelectedItem().toString());
			Presentacion presentacion = instancia.darPresentacion(producto, Integer.parseInt(spinnerPresentaciones.getSelectedItem().toString().split(" ")[0]), spinnerPresentaciones.getSelectedItem().toString().split(" ")[1]);
			if( Integer.parseInt(cantidad.getText().toString())<=presentacion.getNumActualEnDespensa() )
			{
				instancia.registrarConsumo( presentacion,Integer.parseInt(cantidad.getText().toString()) );
				showDialog("Consumo registrado", "Se ha registrado el consumo del producto");
			}
			else
			{
				showDialog("Cantidad incorrecta", "No puede consumir una mayor cantidad de la que tiene disponible este producto");
			}
				
		}
		else
		{
			showDialog("Valores vacíos", "Debe ingresar valores válidos para adicionar el producto");
		}
			
	}
	
	private void showDialog(String title, String message) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle(title);
		alertDialog.setCancelable(false);
		alertDialog.setMessage(message);
		alertDialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				
			}
		});
		AlertDialog dialog= alertDialog.create();
		dialog.show();
		
	}
}
