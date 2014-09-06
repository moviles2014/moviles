package moviles.uniandes.edu.co.mercamovil;

import java.text.SimpleDateFormat;

import moviles.uniandes.edu.co.mundo.ListaCompras;
import moviles.uniandes.edu.co.mundo.MercaMovil;
import classattendance.mundo.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DetalleListaActivity extends Activity 
{
	/**
	 * Constante que permite identificar la acción de seleccionar un contacto
	 */
	private final static int ESCOGER_CONTACTO = 1;
	
	/**
	 * Constante para crear un dialogo de error
	 */
	private final static int DIALOGO_ERROR = 2;

	/**
	 * Constante para crear un dialogo de envió exitoso
	 */
	private final static int DIALOGO_ENVIO_OK = 3;

	/**
	 * Constante para crear un dialogo de error de datos
	 */
	private final static int DIALOGO_ERROR_DATOS = 4;

	/**
	 * Nombre del contacto seleccionado
	 */
	private String nombreContacto;

	/**
	 * Numero telefónico del contacto seleccionado
	 */
	private String numeroTelefonico;

	/**
	 * Referencia al mundo
	 */
	private MercaMovil instancia;

	/**
	 * Nombre de la lista
	 */
	private String nombreLista;

	/**
	 * Campo de texto donde se guarda el contacto seleccionado
	 */
	private TextView compartidaCon;

	/**
	 * Arreglo con los items de la lista de compras
	 */
	private String[ ] items;
	
	/**
	 * List view con los items de compra
	 */
	private ListView listaItems;
	
	/**
	 * Lista de compras de la que se muestra el detalle
	 */
	private ListaCompras listaCompras;
	
	/**
	 * TextView con el costo de la lista
	 */
	private TextView costo;
	
	//TODO Cree un atributo para cada elemento gráfico que compone el layout

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		
		//TODO Inicializar el atributo instancia para obtener la información del mundo
		
		//TODO Recuperar la información enviada por medio del intent (Ayuda: Revise cómo se inicia el Intent desde VerListasComprasActivity) 

		//TODO Inicializar el atributo listaCompras (Ayuda: Utilice el método darListaPorNombre de la clase MercaMovil)
		
		//TODO Inicializar los TextView con la información de la lista de compras
		
		//TODO Inicializar el atributo items con la información de los productos de la lista (Ayuda: Utilice el método darItemsEnLista de la clase MercaMovil)
		
		//TODO Inicializar el ListView y llenar el adapter con la información de los items
		
		compartidaCon = (TextView)findViewById(R.id.txtCompartidaCon);

	}
	
	@Override
	protected void onResume( )
	{
		super.onResume();
		costo.setText(Double.toString(listaCompras.calcularCostoLista()));
		items = instancia.darItemsEnLista( listaCompras );
		ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1,items);
		listaItems.setAdapter(adapter);
	}

	/**
	 * Muestra la actividad para agregar un producto a la lista
	 */
	public void agregarProducto(View v)
	{
		Intent intent = new Intent(getApplicationContext(), AgregarProductoActivity.class);
		intent.putExtra("nombreLista", nombreLista);
		startActivity(intent);
	}

	/**
	 * Muestra la actividad de selección de contactos invocando las funciones del dispositivo
	 */
	public void seleccionarContacto(View v)
	{
		startActivityForResult(new Intent(Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI),ESCOGER_CONTACTO);
	}

	public void onActivityResult(int reqCode, int resultCode, Intent data) {
		nombreContacto = "";
		if(resultCode == RESULT_OK){
			if(reqCode == ESCOGER_CONTACTO){
				Uri uriContacto = data.getData();
				if(uriContacto != null ){						
					try {
						String[] cols = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
						Cursor cursor =  managedQuery(uriContacto, cols, null, null, null);
						cursor.moveToFirst();
						nombreContacto = cursor.getString(0);

						Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
						String[] columnas = {ContactsContract.CommonDataKinds.Phone.NUMBER};
						String seleccion = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "='" + nombreContacto + "'";
						Cursor c = managedQuery(phoneUri,columnas,seleccion,null, null );
						if(c.moveToFirst()){
							numeroTelefonico = c.getString(0);
						}

					} catch (Exception e) {
						numeroTelefonico = e.getMessage();
						showDialog(DIALOGO_ERROR);
					}
					compartidaCon.setText(nombreContacto);
				}
			}
		}

	}

	/**
	 * Envía un mensaje de texto al contacto seleccionado con los items de la lista de compras
	 */
	public void compartirLista(View v)
	{
		if(numeroTelefonico != null ){
			try{
				String mensaje = "Recuerda comprar la lista " + nombreLista + " que tiene los siguientes items: "; 
				for( int i = 0; i < items.length; i++ )
				{
					if( i < (items.length - 1) )
					{
						String cadena = items[i].replace("\n", " ");
						mensaje += cadena + ",";
					}
					else
					{
						String cadena = items[i].replace("\n", " ");
						mensaje += cadena;
					}
				}
				SmsManager manejador = SmsManager.getDefault();
				try {
					manejador.sendTextMessage(numeroTelefonico, null, mensaje, null, null);
					Log.i("SMS", "Se envió el mensaje correctamente");
				} catch (Exception e) {
					Log.i("SMS", "No se envió el mensaje, excepción: "+e.getMessage());
				}
				showDialog("Mensaje enviado","El mensaje se ha enviado a " + nombreContacto);

			}catch(Exception e){			
				showDialog("Faltan platos de la comida ", "Una comida solo se puede formar con un plato de cada uno de los tipos mostrados.");
			}


		}else{
			showDialog("Error al enviar", "Debe seleccionar un contacto para compartir la lista");
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
	
	protected Dialog onCreateDialog(int id) {
    	switch (id) {
		case DIALOGO_ERROR:
			return crearDialogo("No fue posible recuperar la información del contacto.");
		case DIALOGO_ENVIO_OK:
			return crearDialogo("Se ha enviado el mensaje a " + nombreContacto);
		case DIALOGO_ERROR_DATOS:
			return crearDialogo("No ha seleccionado un contacto o este no tiene un número telefónico.");
    	}
		 
		return null;
    }
	
	/**
	 * Crea un dialogo con el mensaje que llega por parámetro
	 * @param mensaje el mensaje que se desea desplegar
	 * @return el dialogo con el mensaje a mostrar
	 */
	private Dialog crearDialogo(String mensaje){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(mensaje);
		builder.setCancelable(false);
		builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		                finish();
		           }
		       });
		AlertDialog alert = builder.create();
		return alert;
	}


}
