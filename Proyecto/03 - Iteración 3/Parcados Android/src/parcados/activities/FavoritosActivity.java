package parcados.activities;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
 
import parcados.mundo.Parcados;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;

import com.parcados.R;

import db_remote.DB_Queries;
import db_remote.RespuestaFavs;

public class FavoritosActivity extends DrawerActivity implements OnItemSelectedListener, OnItemClickListener , OnItemLongClickListener  {
	
	private ListView mListView;
	private MyAdapter myAdapter ; 
	public static  ArrayList<String> favoritos ;
	public static ArrayList<String> precios ;
	public static  ArrayList<String> cupos ;
	public static  ArrayList<String> parqueaderos ;
	
	public static Object cola ; 
	
	public static int pos ; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_favoritos) ;
		getActionBar().setDisplayHomeAsUpEnabled(true) ;
		getActionBar().setTitle("Parqueaderos Favoritos");
		
		final Typeface mFont = Typeface.createFromAsset(getAssets(),
				"fonts/Oxygen-Regular.ttf"); 
				final ViewGroup mContainer = (ViewGroup) findViewById(
				android.R.id.content).getRootView();
				MyApplication.setAppFont(mContainer, mFont ,true );
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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		parqueaderos = new ArrayList<String>() ;
		precios = new ArrayList<String>() ;
		cupos = new ArrayList<String>() ;
		favoritos = new ArrayList<String>() ;
		cola = new Object() ; 
		
		
		final AlertDialog dialog2 = new AlertDialog.Builder(this).setTitle("Parcados no se pudo conectar").setMessage("Asegúrese de tener una conexión a internet").setIcon(android.R.drawable.ic_dialog_alert).show();
		final ProgressDialog dialog = ProgressDialog.show(this, "Consultando parqueadero", "Por favor espere...", true);
		
		try {

			new Thread(new Runnable() {
				@Override
				public void run() {
					try
					{
						ArrayList<RespuestaFavs> res =  DB_Queries.consultarFavoritos() ;
						System.out.println( "size de res "  + res.size () );
						for ( int i = 0 ;i < res.size() ; i ++ ) { 
							parqueaderos.add ( res.get(i).parqueadero) ;
							if ( res.get(i).cupos.equals("-1") )
								cupos.add( "no data") ;
							else 
								cupos.add ( res.get(i).cupos + " cupos") ;
							if ( res.get(i).precio.equals("-1"))
								precios.add ( "no data") ; 
							else 
								precios.add ( "$"+ res.get(i).precio + "/min") ;
							favoritos.add ( res.get(i).favorito)  ; 
						}
						
						synchronized (FavoritosActivity.cola) {
							FavoritosActivity.cola.notifyAll() ;	
						}
//						 
						int i = 0;
						while ( i < 10 && DB_Queries.inRequest)
						{
							i++;
							Thread.sleep(1000);
							System.out.println( i );
						}
						
						dialog.dismiss();	
						dialog2.dismiss() ;
						
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								synchronized (FavoritosActivity.cola) {
									FavoritosActivity.cola.notifyAll() ;	
								}
								if (DB_Queries.inRequest)
								{
									new AlertDialog.Builder(FavoritosActivity.this).setTitle("Parcados Time Out")
									.setMessage("No se pudo consultar el parqueadero") 
									.setIcon(android.R.drawable.ic_dialog_alert)
									.show();
								}
								else
								{
//									setCuposYPrecio();
								}										
							}
						});
					} catch (Exception e) {
						synchronized (FavoritosActivity.cola) {
							FavoritosActivity.cola.notifyAll() ;	
						}
						dialog.dismiss();
						
						System.out.println( "parcados no se pudo conectar ");
						try {
							Thread.sleep(3000) ;
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} 
						dialog2.dismiss() ; 
					}		

				}
			}).start();

		} catch (Exception e) {
			synchronized (FavoritosActivity.cola) {
				FavoritosActivity.cola.notifyAll() ;	
			}
			new AlertDialog.Builder(this)
			.setTitle("Parcados")
			.setMessage("Parcados no se pudo conectar") 
			.setIcon(android.R.drawable.ic_dialog_alert)
			.show();
		}
		
		
		
		synchronized (cola) {
			try {
				cola.wait() ;
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} 
		}
		
		mListView = (ListView) findViewById(R.id.lista_favs);
		mListView.setOnItemClickListener(this);
		mListView.setOnItemLongClickListener(this) ; 
		myAdapter = new MyAdapter(this) ;
		mListView.setAdapter(myAdapter) ;
		
		
		if ( parqueaderos.size() == 0 ) { 
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("No tiene parqueaderos favoritos!")
			       .setCancelable(false)
			       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                //do things
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();
		}
			
		
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		  pos = position  ; 
		 
		  new AlertDialog.Builder(this)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle("Eliminar Favorito")
	        .setMessage("¿Está seguro que desea eliminar el elemento seleccionado?")
	        .setPositiveButton("Si", new DialogInterface.OnClickListener()
	    {
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	        	
	    		try {

	    			new Thread(new Runnable() {
	    				@Override
	    				public void run() {
	    					try
	    					{
	    						DB_Queries.eliminarFavorito(favoritos.get(pos)) ;
	    						
	    						
	    						synchronized (FavoritosActivity.cola) {
	    							FavoritosActivity.cola.notifyAll() ;	
	    						}
//	    						 
	    						int i = 0;
	    						while ( i < 10 && DB_Queries.inRequest)
	    						{
	    							i++;
	    							Thread.sleep(1000);
	    							System.out.println( i );
	    						}
	    						
	    						runOnUiThread(new Runnable() {

	    							@Override
	    							public void run() {
	    								synchronized (FavoritosActivity.cola) {
	    									FavoritosActivity.cola.notifyAll() ;	
	    								}
	    								if (DB_Queries.inRequest)
	    								{
	    									new AlertDialog.Builder(FavoritosActivity.this).setTitle("Parcados Time Out")
	    									.setMessage("No se pudo consultar el parqueadero") 
	    									.setIcon(android.R.drawable.ic_dialog_alert)
	    									.show();
	    								}
	    								else
	    								{
//	    									setCuposYPrecio();
	    								}										
	    							}
	    						});
	    					} catch (Exception e) {
	    						synchronized (FavoritosActivity.cola) {
	    							FavoritosActivity.cola.notifyAll() ;	
	    						}
	    						
	    						System.out.println( "parcados no se pudo conectar ");
	    						try {
	    							Thread.sleep(3000) ;
	    						} catch (InterruptedException e1) {
	    							// TODO Auto-generated catch block
	    							e1.printStackTrace();
	    						} 
	    					}		

	    				}
	    			}).start();

	    		} catch (Exception e) {
	    		}
	    		
	    		
	    		
	    		synchronized (cola) {
	    			try {
	    				cola.wait() ;
	    			} catch (InterruptedException e2) {
	    				// TODO Auto-generated catch block
	    				e2.printStackTrace();
	    			} 
	    		}
	        	
	        	myAdapter.remove(FavoritosActivity.pos);
	    		myAdapter.notifyDataSetChanged();
	    		
	        }

	    })
	    .setNegativeButton("No", null)
	    .show();
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(this, DetalleParqueaderoActivity.class) ;
		intent.putExtra("idparq", parqueaderos.get(position) ) ;  
		startActivity(intent) ;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		System.out.println( " selected algo ");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	
}

class MyAdapter extends BaseAdapter {
	private Context context ; 

	
	public String getParqueaderoAtPosition  ( int position ) { 
		return FavoritosActivity.parqueaderos.get(position) ; 
	}
	
	public MyAdapter (Context context){ 

		this.context = context ; 
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return FavoritosActivity.favoritos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return FavoritosActivity.favoritos.get(position) ; 
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public void cambiarEstadoCalculadora ( View v ) { 
		System.out.println( " colo ");
	}

	public void remove(int position){
		FavoritosActivity.favoritos.remove(FavoritosActivity.favoritos.get(position));;
		FavoritosActivity.precios.remove(FavoritosActivity.precios.get(position));
		FavoritosActivity.parqueaderos.remove(FavoritosActivity.parqueaderos.get(position)) ; 
		FavoritosActivity.cupos.remove(FavoritosActivity.cupos.get(position));
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// primera vez que se crea
		Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/Oxygen-Regular.ttf");
		Typeface tf2 = Typeface.createFromAsset(context.getAssets(), "fonts/Oxygen-Regular.ttf");

		View row = null ; 
		if ( convertView == null)  {
			LayoutInflater inflater = (LayoutInflater) 
					context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.favoritos_row, parent , false) ; 
		}
		else { 
			row = convertView ; 
		}
		TextView titleTextView =  (TextView) row.findViewById(R.id.precioFav) ;
		titleTextView.setTypeface(tf) ;
		titleTextView.setText(FavoritosActivity.precios.get (position) ) ;
		
		TextView cupo =  (TextView) row.findViewById(R.id.cuposFav) ;
		cupo.setTypeface(tf2) ;
		cupo.setText(FavoritosActivity.cupos.get (position)) ;
		
		TextView precio =  (TextView) row.findViewById(R.id.textView121) ;
		precio.setTypeface(tf2) ;
		precio.setText(FavoritosActivity.favoritos.get (position)) ;
		return row;
	} 
	
}

