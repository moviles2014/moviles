package parcados.activities;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
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

public class MisReservasActivity extends DrawerActivity implements OnItemSelectedListener, OnItemClickListener , OnItemLongClickListener  { 

	private ListView mListView ;
	private MyAdapterR myAdapter  ;
	public static int pos ; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mis_reservas) ;
		getActionBar().setDisplayHomeAsUpEnabled(true) ;
		getActionBar().setTitle("Mis Reservas");
		
		mListView = (ListView) findViewById(R.id.mis_reservas);
		mListView.setOnItemClickListener(this);
		mListView.setOnItemLongClickListener(this) ; 
		
		myAdapter = new MyAdapterR(this) ;
		mListView.setAdapter(myAdapter) ;
		
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		  pos = position  ; 
		 
		  new AlertDialog.Builder(this)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle("Cancelar Reserva")
	        .setMessage("�Est� seguro que desea cancelar la reserva?")
	        .setPositiveButton("Si", new DialogInterface.OnClickListener()
	    {
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	        	
	        	myAdapter.remove(MisReservasActivity.pos);
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

class MyAdapterR extends BaseAdapter {
	private Context context ; 
	private ArrayList<String> parqueaderos ;
	private ArrayList<String> fecha ;
	
	public MyAdapterR (Context context){ 
		parqueaderos = new ArrayList<String>() ;
		fecha = new ArrayList<String>() ; 
		parqueaderos.add (  "Parqueolito calle 54" ) ; 
		parqueaderos.add ( "Parking R us" ) ;
		parqueaderos.add ( "Turrocaaqui" ) ; 
		fecha.add (  "12/01/2014 - 8:00 am" ) ; 
		fecha. add (  "12/02/2014 - 10:00 am"  ) ; 
		fecha.add (  "12/03/2014 - 3:30 pm" ) ;
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

