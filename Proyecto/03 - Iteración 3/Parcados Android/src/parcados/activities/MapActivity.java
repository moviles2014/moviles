package parcados.activities;

import java.util.ArrayList;
import java.util.List;

import parcados.mundo.Parcados;
import parcados.mundo.Parqueadero;
import parcados.sensors.Compass;

import com.androidmapsextensions.ClusteringSettings;
import com.androidmapsextensions.GoogleMap;
import com.androidmapsextensions.MapFragment;
import com.androidmapsextensions.Marker;
import com.androidmapsextensions.MarkerOptions;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.parcados.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.GeomagneticField;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

public class MapActivity extends DrawerActivity implements com.androidmapsextensions.GoogleMap.OnInfoWindowClickListener, com.androidmapsextensions.GoogleMap.OnMyLocationButtonClickListener{

	private LocationManager lm;
	private LocationListener locationListener;

	private GoogleMap map;

	private GeomagneticField geoField;	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mapa);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getActionBar().setDisplayHomeAsUpEnabled(true) ;
		getActionBar().setTitle("Mapa");

		Compass.startCompass((SensorManager) getSystemService(Context.SENSOR_SERVICE));


		lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 



		locationListener = new LocationListener() {

			private ArrayList<String> asked = new ArrayList<String>();

			public void onLocationChanged(Location location) {
				if (location == null)
					return;

				float heading = Compass.getAzimuth();
				float bearing = location.getBearing();

				geoField = new GeomagneticField(
						Double.valueOf(location.getLatitude()).floatValue(),
						Double.valueOf(location.getLongitude()).floatValue(),
						Double.valueOf(location.getAltitude()).floatValue(),
						System.currentTimeMillis()
						);

				heading -= geoField.getDeclination();


				heading = bearing - heading;

				if (heading < 0) {
					heading = heading + 360;
				}

				updateCamera(heading, location.getLatitude(), location.getLongitude());
				//
				//				MarkerOptions marker = new MarkerOptions();
				//
				//				marker.position(new LatLng(location.getLatitude(), location.getLongitude()));			
				//				marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
				//				marker.title("Impark Carrera 35");
				//				marker.snippet("Click para más informacion...");
				//				map.addMarker(marker);	

				List<Marker> markers = map.getDisplayedMarkers();
				for (int i = 0; i < markers.size(); i++)
				{
					Marker actual = markers.get(i);
					if (actual.getTitle() != null)
					{
						Location loc = new Location("actual");
						loc.setLatitude(actual.getPosition().latitude);
						loc.setLongitude(actual.getPosition().longitude);
						if (location.distanceTo(loc) < 20)
						{
							if (!asked.contains(actual.getTitle()))
							{
								asked.add(actual.getTitle());
								dialogoIngresoParqueadero(actual.getTitle());
							}
						}

					}
				}
			}

			public void updateCamera(float bearing, double latitude, double longitude) {
				CameraPosition currentPlace = new CameraPosition.Builder()
				.target(new LatLng(latitude, longitude))
				.bearing(bearing).zoom(18f).build();
				map.moveCamera(CameraUpdateFactory.newCameraPosition(currentPlace));
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

		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 300, 10, locationListener);


		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getExtendedMap();

		map.setClustering(new ClusteringSettings()
		.enabled(true)
		.addMarkersDynamically(true)
		.clusterSize(96)
				);

		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);		
		map.setTrafficEnabled(true);


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

		for (int i = 0; i < parqs.size(); i++) {
			Parqueadero act = parqs.get(i);	
			MarkerOptions marker = new MarkerOptions();

			marker.position(new LatLng(act.darLatitud(), act.darLongitud()));			
			marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
			marker.title(act.darNombre());
			marker.snippet("Click para más informacion...");

			map.addMarker(marker);				
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
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 300, 10, locationListener);
		Compass.startCompass((SensorManager) getSystemService(Context.SENSOR_SERVICE));
		super.onResume();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		lm.removeUpdates(locationListener);
		Compass.pauseCompass();
		super.onPause();

	}


	@Override
	protected void onDestroy() {
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		lm.removeUpdates(locationListener);
		Compass.pauseCompass();
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
		else if (map.getMyLocation() == null)
		{
			Toast toast = Toast.makeText(getApplicationContext(), "Esperando Localización...", Toast.LENGTH_SHORT);
			toast.show();
		}
		return false;
	}

	public void dialogoIngresoParqueadero(final String nombre){
		new AlertDialog.Builder(this)
		.setTitle("Ingreso a parqueadero")
		.setMessage("¿Ingresó al paqueadero: " + nombre + "?")
		.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) { 
				Parqueadero parq = Parcados.darInstancia(getApplicationContext()).darParqueaderoPorNombre(nombre);
				seleccionarParqueadero(parq.darPrecio(), nombre);
			}
		})
		.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) { 


			}
		})
		.setIcon(android.R.drawable.ic_dialog_map)
		.show();
	}

	public void seleccionarParqueadero (int precio, String nombre ) {
		Intent intent = new Intent(this, CalculadoraActivity.class) ;
		intent.putExtra("precio",  precio) ;
		intent.putExtra("NombreParqueadero", nombre ) ;
		startActivity(intent) ; 
	} 

}
