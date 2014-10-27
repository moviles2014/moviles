package parcados.activities;

import java.util.ArrayList;

import parcados.mundo.Parcados;
import parcados.mundo.Parqueadero;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parcados.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

public class MapActivity extends Activity implements OnInfoWindowClickListener, OnMyLocationButtonClickListener{

	private LocationManager lm;
	private LocationListener locationListener;

	private GoogleMap map;

	private ProgressDialog dialogo;

	private LatLngDistance[] positions;
	private int count;

	private Handler handler = new Handler();
	private Runnable batchAddMarkersRunnable = new Runnable() {

		private static final int ADD_IN_BATCH = 10;
		private static final int DELAY = 1;

		@Override
		public void run() {
			if (count == 0) {
				dialogo.dismiss();
				return;
			}
			for (int i = 0; i < ADD_IN_BATCH && count > 0; i++) {
				count--;
				LatLngDistance position = positions[count];
				positions[count] = null;

				MarkerOptions marker = new MarkerOptions();
				marker.position(position.latLng);			
				marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
				marker.title(position.title);
				marker.snippet("Click para más informacion...");
				map.addMarker(marker);	
			}

			handler.postDelayed(this, DELAY);
		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mapa);
		getActionBar().setDisplayHomeAsUpEnabled(true) ;


		lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 



		locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
			}
			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub

			}

		};

		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);


		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);		


		map.setMyLocationEnabled(true);
		map.getUiSettings().setMyLocationButtonEnabled(true);
		map.getUiSettings().setCompassEnabled(true);
		map.setOnMyLocationButtonClickListener(this);
		map.setOnInfoWindowClickListener(this);


		LatLng actual = new LatLng(4.598056000000001, -74.075833);
		CameraUpdate zoom =CameraUpdateFactory.zoomTo(10);
		CameraUpdate center= CameraUpdateFactory.newLatLng(actual);
		map.moveCamera(center);
		map.animateCamera(zoom);

		ArrayList<Parqueadero> parqs = Parcados.darInstancia(getApplicationContext()).darTodosLosParqueaderos();
		positions = new LatLngDistance[parqs.size()];
		count = positions.length;

		for (int i = 0; i < parqs.size(); i++) {
			Parqueadero act = parqs.get(i);	
			LatLng posact = new LatLng(act.darLatitud(), act.darLongitud());
			positions[i] = new LatLngDistance(posact, act.darNombre());

			//			Parqueadero act = parqs.get(i);	
			//			MarkerOptions marker = new MarkerOptions();
			//
			//			marker.position(new LatLng(act.darLatitud(), act.darLongitud()));			
			//			marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
			//			marker.title(act.darNombre());
			//			marker.snippet("Click para más informacion...");
			//
			//
			//			map.addMarker(marker);				
		}



		if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) && !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
		{
			new AlertDialog.Builder(this)
			.setTitle("Active el GPS para una mejor experiencia")
			.setMessage("¿Desea activarlo?")
			.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) { 
					startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
				}
			})
			.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) { 
					// do nothing
				}
			})
			.setIcon(android.R.drawable.ic_dialog_alert)
			.show();
		}

	}

	@Override
	protected void onStop() {
		lm.removeUpdates(locationListener);
		super.onStop();
	}

	@Override
	protected void onResume() {
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
		dialogo = ProgressDialog.show(this, "Cargando", "Por favor espere...", true);
		handler.post(batchAddMarkersRunnable);
		super.onResume();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		handler.removeCallbacks(batchAddMarkersRunnable);
		super.onPause();

	}


	@Override
	protected void onDestroy() {
		lm.removeUpdates(locationListener);
		super.onDestroy();
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		Intent intent = new Intent(this, DetalleParqueaderoActivity.class) ;
		intent.putExtra("idparq", marker.getTitle() ) ;
		startActivity(intent) ;
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
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 1) {
			switch (requestCode) {
			case 1:
				break;
			}
		}  
	}

	public void mostrarDialogoAlerta(String titulo, String mensaje){
		new AlertDialog.Builder(this)
		.setTitle(titulo)
		.setMessage(mensaje)
		.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) { 
			}
		})
		.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) { 
				// do nothing
			}
		})
		.setIcon(android.R.drawable.ic_dialog_alert)
		.show();
	}
	//	public void waitForLoc()
	//	{
	//		if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
	//		{
	//			Toast toast = Toast.makeText(getApplicationContext(), "Esperando Localización", Toast.LENGTH_LONG);
	//			toast.show();
	//			new Thread(new Runnable() {
	//
	//				@Override
	//				public void run() {
	//
	//					while(lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)==null && lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)==null)
	//					{
	//						try {
	//							Thread.sleep(1000);
	//						} catch (InterruptedException e) {
	//							// TODO Auto-generated catch block
	//							e.printStackTrace();
	//						}
	//					}
	//					runOnUiThread(new Runnable() {
	//
	//						@Override
	//						public void run() {
	//							Location locactual = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	//							if (locactual == null) 
	//							{
	//								locactual = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	//								mostrarDialogoAlerta("El GPS está desactivado o en modo ahorro de energía", "Mostrando localización aproximada");
	//							}
	//							LatLng actual = new LatLng(locactual.getLatitude(), locactual.getLongitude());
	//							
	//							CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
	//							if (marker != null) marker.position(actual);
	//							else
	//							{
	//								marker = (new MarkerOptions().position(actual)).title("Usted está aquí");
	//								map.addMarker(marker);
	//							}
	//
	//							CameraUpdate center= CameraUpdateFactory.newLatLng(actual);
	//							map.moveCamera(center);
	//							map.animateCamera(zoom);
	//						}
	//					});
	//				}
	//			}).start();
	//		}
	//	}

	@Override
	public boolean onMyLocationButtonClick() {
		if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) && !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
		{
			Toast toast = Toast.makeText(getApplicationContext(), "GPS Desactivado", Toast.LENGTH_SHORT);
			toast.show();

		}
		else if (lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)== null && lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) == null)
		{
			Toast toast = Toast.makeText(getApplicationContext(), "Esperando Localización...", Toast.LENGTH_SHORT);
			toast.show();
		}

		return false;
	}

	private static class LatLngDistance {

		private LatLng latLng;
		private String title;

		private LatLngDistance(LatLng latLng, String title) {
			this.latLng = latLng;
			this.title = title;
		}
	}
}
