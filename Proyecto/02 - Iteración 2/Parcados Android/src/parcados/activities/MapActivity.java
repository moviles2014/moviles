package parcados.activities;

import java.util.ArrayList;

import parcados.mundo.Parcados;
import parcados.mundo.Parqueadero;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
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
import android.view.MenuItem;

public class MapActivity extends Activity implements OnInfoWindowClickListener{

	private LocationManager lm;
	private LocationListener locationListener;

	private GoogleMap map;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mapa);
		getActionBar().setDisplayHomeAsUpEnabled(true) ;


		lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 

		if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
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
		Location locactual = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);


		waitForLoc();



		// mapa
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		CameraUpdate zoom;
		LatLng actual;
		if(locactual == null)
		{
			actual = new LatLng(4.598056000000001, -74.075833);
			zoom=CameraUpdateFactory.zoomTo(10);
			CameraUpdate center= CameraUpdateFactory.newLatLng(actual);
			map.moveCamera(center);
			map.animateCamera(zoom);
		}
		//		else
		//		{
		//			actual = new LatLng(locactual.getLatitude(), locactual.getLongitude());
		//			zoom=CameraUpdateFactory.zoomTo(15);
		//			map.addMarker((new MarkerOptions().position(actual)).title("Usted está aquí"));
		//		}	










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

		map.setOnInfoWindowClickListener(this);
	}

	@Override
	protected void onStop() {
		lm.removeUpdates(locationListener);
		super.onStop();
	}

	@Override
	protected void onResume() {
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
		waitForLoc();
		super.onResume();
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

	public void waitForLoc()
	{
		if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
		{
			final ProgressDialog dialogo = ProgressDialog.show(this, "Esperando Localización", "Por favor espere...", true);

			new Thread(new Runnable() {

				@Override
				public void run() {

					while(lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)==null)
					{
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Location locactual = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							LatLng actual = new LatLng(locactual.getLatitude(), locactual.getLongitude());
							CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
							map.addMarker((new MarkerOptions().position(actual)).title("Usted está aquí"));

							CameraUpdate center= CameraUpdateFactory.newLatLng(actual);
							map.moveCamera(center);
							map.animateCamera(zoom);

							dialogo.dismiss();	
						}
					});
				}
			}).start();
		}
	}
}
