package parcados.activities;

import java.io.IOException;
import java.io.InputStream;

import parcados.mundo.Parcados;
import com.parcados.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

public class DrawerActivity extends Activity {

	private String[] listaOpciones;
	private ListView mDrawerList;

	private final static int MIS_RESERVAS = 0;
	private final static int BUSCAR_PARQUEADERO = 1;
	private final static int CALCULADORA = 2;
	private final static int MAPA = 3;
	private final static int HISTORIAL = 4;
	private final static int FAVORITOS = 5;


	protected DrawerLayout fullLayout;
	protected FrameLayout actContent;

	@SuppressLint("InflateParams") @Override
	public void setContentView(final int layoutResID) {
		fullLayout= (android.support.v4.widget.DrawerLayout) getLayoutInflater().inflate(R.layout.activity_main, null); // Your base layout here       
		actContent= (FrameLayout) fullLayout.findViewById(R.id.content_frame);
		getLayoutInflater().inflate(layoutResID, actContent, true); // Setting the content of layout your provided to the act_content frame
		super.setContentView(fullLayout);        

		listaOpciones = getResources().getStringArray(R.array.opciones_array);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		// Set the adapter for the list view
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, listaOpciones));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
	}

	private class DrawerItemClickListener implements ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position, view);

		}
	}

	private void selectItem(int position, View view){
		if (position == MIS_RESERVAS)
		{
			abrirReservas(view) ;
		}
		else if (position == BUSCAR_PARQUEADERO)
		{
			buscarParqueadero(view);
		}
		else if (position == CALCULADORA)
		{
			abrirCalculadora(view);
		}
		else if (position == MAPA)
		{
			abrirMapa(view);
		}	
		else if (position == FAVORITOS)
		{
			abrirFavoritos(view);
		}	
		else if (position == HISTORIAL)
		{
			abrirHistorial(view);
		}	
	}

	public void buscarParqueadero ( View view )
	{
		final Intent intent = new Intent(this, BusquedaParqueaderosActivity.class) ;


		final ProgressDialog dialog = ProgressDialog.show(this, "Cargando Información", "Por favor espere...", true);

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Parcados actual = Parcados.darInstancia(getApplicationContext());
					if ( actual.darEmpresas().isEmpty()){
						InputStream inputStream = getResources().getAssets().open("empresas");			
						actual.loadEmpresas(inputStream);
						inputStream = getResources().getAssets().open("parqueaderos");			
						actual.loadParq(inputStream);
						inputStream.close();	

					}					

					dialog.dismiss();
					startActivity(intent) ;
				} catch (IOException e) {

					e.printStackTrace();
				}		

			}
		}).start();
	}

	public void abrirCalculadora ( View view ) {
		Intent intent = new Intent(this, CalculadoraActivity.class) ;
		startActivity(intent) ;
	}

	public void abrirMapa( View view )
	{
		Intent intent = new Intent(this, MapActivity.class) ;			
		startActivity(intent) ;
	}
	
	public void abrirHistorial( View view )
	{
		Intent intent = new Intent(this, HistorialActivity.class) ;			
		startActivity(intent) ;
	}
	
	public void abrirFavoritos( View view )
	{
		Intent intent = new Intent(this, FavoritosActivity.class) ;			
		startActivity(intent) ;
		System.out.println(" entro abrir favs ");
	}
	public void abrirReservas( View view )
	{
		Intent intent = new Intent(this, MisReservasActivity.class) ;			
		startActivity(intent) ;
	}
	
	
}
