package moviles.uniandes.edu.co.mercamovil;

import java.util.List;

import moviles.uniandes.edu.co.mundo.ListaCompras;
import moviles.uniandes.edu.co.mundo.MercaMovil;

import classattendance.mundo.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
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
	
	public void consultarPorcentajeBateria(View v)
	{
		IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		Intent batteryStatus = getApplicationContext().registerReceiver(null, ifilter);
		int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
		boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
		boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
		
		int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

		float batteryPct = level / (float)scale;
		
		String bateria = "El nivel de Batería es del: " + batteryPct*100 + "%";
		if(usbCharge)
			bateria += " y se encuentra cargando por medio de USB";
		else if (acCharge)
			bateria += " y se encuentra cargando por medio de AC";
		else
			bateria += " y el dispositivo se encuentra desconectado";	
		showDialog("Nivel de Batería", bateria);
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
