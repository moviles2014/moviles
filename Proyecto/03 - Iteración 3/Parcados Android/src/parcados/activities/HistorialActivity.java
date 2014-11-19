package parcados.activities;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class HistorialActivity extends DrawerActivity  implements OnItemSelectedListener, OnItemClickListener , OnItemLongClickListener  {
	
	private ListView mListView ;
	private MyAdapterH myAdapter  ;
	public static int pos ; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historial) ;
		getActionBar().setDisplayHomeAsUpEnabled(true) ;
		getActionBar().setTitle("Historial");
		
		mListView = (ListView) findViewById(R.id.mi_historial);
		mListView.setOnItemClickListener(this);
		mListView.setOnItemLongClickListener(this) ; 
		
		myAdapter = new MyAdapterH(this) ;
		mListView.setAdapter(myAdapter) ;
		
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
	        	
	        	myAdapter.remove(HistorialActivity.pos);
	    		myAdapter.notifyDataSetChanged();
	    		
	        }

	    })
	    .setNegativeButton("No", null)
	    .show();
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
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
	private ArrayList<String> parqueaderos ;
	private ArrayList<String> fecha ;
	
	public MyAdapterH (Context context){ 
		parqueaderos = new ArrayList<String>() ;
		fecha = new ArrayList<String>() ; 
		parqueaderos.add (  "Parqueolito calle 54" ) ; 
		parqueaderos.add ( "Parking R us" ) ;
		fecha.add (  "12/01/2014 - 8:00 am" ) ; 
		fecha. add (  "12/02/2014 - 10:00 am"  ) ; 
		this.context = context ; 
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return parqueaderos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return parqueaderos.get(position) ; 
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
		parqueaderos.remove(parqueaderos.get(position));;
	    fecha.remove(fecha.get(position));
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
		titleTextView.setText(parqueaderos.get (position) ) ;
		
		TextView cupo =  (TextView) row.findViewById(R.id.fecha_reserva) ;
		cupo.setTypeface(tf2) ;
		cupo.setText(fecha.get (position)) ;
		
		return row;
	} 
	
}

