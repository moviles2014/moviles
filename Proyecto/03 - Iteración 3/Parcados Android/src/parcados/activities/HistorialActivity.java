package parcados.activities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

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
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import com.parcados.R;

import db_remote.DB_Queries;
import db_remote.RespuestaFavs;
import db_remote.RespuestaReservas;

public class HistorialActivity extends DrawerActivity implements OnItemSelectedListener, OnItemClickListener , OnItemLongClickListener  { 

	private ListView mListView ;
	private MyAdapterH myAdapter  ;
	
	private static Object cola ;
	
	public static ArrayList<String> parqueaderos ; 
	public static ArrayList<String> fecha  ;  
	public static ArrayList<String> fechaLong  ; 
	public static int pos ; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historial) ;
		getActionBar().setDisplayHomeAsUpEnabled(true) ;
		getActionBar().setTitle("Mis Reservas");
		
		final Typeface mFont = Typeface.createFromAsset(getAssets(),
				"fonts/Oxygen-Regular.ttf"); 
				final ViewGroup mContainer = (ViewGroup) findViewById(
				android.R.id.content).getRootView();
				MyApplication.setAppFont(mContainer, mFont ,true );
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		parqueaderos  = new ArrayList<String>() ;
		fecha = new ArrayList<String>() ;
		fechaLong = new ArrayList<String>() ;
		cola = new Object() ; 
		
		
		
		final AlertDialog dialog2 = new AlertDialog.Builder(this).setTitle("Parcados no se pudo conectar").setMessage("Asegúrese de tener una conexión a internet").setIcon(android.R.drawable.ic_dialog_alert).show();
		final ProgressDialog dialog = ProgressDialog.show(this, "Consultando Historial de Reservas", "Por favor espere...", true);
		
		try {

			new Thread(new Runnable() {
				@Override
				public void run() {
					try
					{
						ArrayList<RespuestaReservas> res =  DB_Queries.consultarReservas() ;
						System.out.println( "size de res "  + res.size () );
						for ( int i = 0 ;i < res.size() ; i ++ ) { 
							
//							
							long time = Long.parseLong(res.get(i).fecha) ; 
							if ( time <= System.currentTimeMillis() ) { 
								parqueaderos.add ( res.get(i).parqueadero) ;
						    	Calendar cal = Calendar.getInstance(); // locale-specific
						    	Date d2 = new Date ( time ) ;
						    	cal.setTime (d2) ; 
						    	
						    	String fechaF = ""+cal.get (cal.YEAR) + "-" + (cal.get( cal.MONTH )+1)  +  "-" + 
						    			 cal.get( cal.DAY_OF_MONTH) + "  " +  cal.get( cal.HOUR_OF_DAY )  + ":" + cal.get(cal.MINUTE) ;
								fecha.add ( fechaF)  ;
								fechaLong.add(res.get(i).fecha) ;
							}
						}
						
						synchronized (HistorialActivity.cola) {
							HistorialActivity.cola.notifyAll() ;	
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
								synchronized (HistorialActivity.cola) {
									HistorialActivity.cola.notifyAll() ;	
								}
								if (DB_Queries.inRequest)
								{
									new AlertDialog.Builder(HistorialActivity.this).setTitle("Parcados Time Out")
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
						synchronized (HistorialActivity.cola) {
							HistorialActivity.cola.notifyAll() ;	
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
			synchronized (HistorialActivity.cola) {
				HistorialActivity.cola.notifyAll() ;	
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
		
		
		if ( parqueaderos.size() == 0 ) { 
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("No tiene registros!")
			       .setCancelable(false)
			       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                //do things
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();
		}
		
		mListView = (ListView) findViewById(R.id.mi_historial);
		mListView.setOnItemClickListener(this);
		mListView.setOnItemLongClickListener(this) ; 
		
		myAdapter = new MyAdapterH(this) ;
		mListView.setAdapter(myAdapter) ;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		  pos = position  ; 
			 
		  new AlertDialog.Builder(this)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle("Borrar Registro")
	        .setMessage("¿Está seguro que desea borrar el registro?")
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
	    						DB_Queries.eliminarReserva(parqueaderos.get(pos), fechaLong.get(pos)) ;
	    						
	    						
	    						synchronized (HistorialActivity.cola) {
	    							HistorialActivity.cola.notifyAll() ;	
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
	    								synchronized (HistorialActivity.cola) {
	    									HistorialActivity.cola.notifyAll() ;	
	    								}
	    								if (DB_Queries.inRequest)
	    								{
	    									new AlertDialog.Builder(HistorialActivity.this).setTitle("Parcados Time Out")
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
	    						synchronized (HistorialActivity.cola) {
	    							HistorialActivity.cola.notifyAll() ;	
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
	        	
	        	myAdapter.remove(HistorialActivity.pos);
	    		myAdapter.notifyDataSetChanged();
	    		
	        }

	    })
	    .setNegativeButton("No", null)
	    .show();
		return true;
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
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, DetalleParqueaderoActivity.class) ;
		intent.putExtra("idparq", parqueaderos.get(position) ) ;  
		startActivity(intent) ;
	}
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	
}

class MyAdapterH extends BaseAdapter {
	private Context context ; 
	
	public MyAdapterH(Context context){ 
		
		this.context = context ; 
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return HistorialActivity.parqueaderos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return HistorialActivity.parqueaderos.get(position) ; 
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
		HistorialActivity.parqueaderos.remove(HistorialActivity.parqueaderos.get(position));;
		HistorialActivity.fecha.remove(HistorialActivity.fecha.get(position));
		HistorialActivity.fechaLong.remove(HistorialActivity.fechaLong.get(position));
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// primera vez que se crea
		Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/Oxygen-Bold.ttf");
		Typeface tf2 = Typeface.createFromAsset(context.getAssets(), "fonts/Oxygen-Regular.ttf");

		View row = null ; 
		if ( convertView == null)  {
			LayoutInflater inflater = (LayoutInflater) 
					context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.reservas_row, parent , false) ; 
		}
		else { 
			row = convertView ; 
		}
		TextView titleTextView =  (TextView) row.findViewById(R.id.nombre_parqueadero_reservas) ;
		titleTextView.setTypeface(tf) ;
		titleTextView.setText(HistorialActivity.parqueaderos.get (position) ) ;
		
		TextView cupo =  (TextView) row.findViewById(R.id.fecha_reserva) ;
		cupo.setTypeface(tf2) ;
		cupo.setText(HistorialActivity.fecha.get (position)) ;
		
		return row;
	} 
	
}

