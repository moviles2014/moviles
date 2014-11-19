package parcados.activities;

import java.util.ArrayList;

import parcados.mundo.Parcados;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
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

public class FavoritosActivity extends DrawerActivity implements OnItemSelectedListener, OnItemClickListener , OnItemLongClickListener  {
	
	private ListView mListView;
	private MyAdapter myAdapter ; 
	public static int pos ; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favoritos) ;
		getActionBar().setDisplayHomeAsUpEnabled(true) ;
		getActionBar().setTitle("Parqueaderos Favoritos");
		
		mListView = (ListView) findViewById(R.id.lista_favs);
		mListView.setOnItemClickListener(this);
		mListView.setOnItemLongClickListener(this) ; 
		
		myAdapter = new MyAdapter(this) ;
		mListView.setAdapter(myAdapter) ;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		  pos = position  ; 
		 
		  new AlertDialog.Builder(this)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle("Eliminar Parqueadero")
	        .setMessage("¿Está seguro que desea eliminar el parqueadero seleccionado?")
	        .setPositiveButton("Si", new DialogInterface.OnClickListener()
	    {
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	        	
	        	
	        	myAdapter.remove(FavoritosActivity.pos);
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
	private ArrayList<String> favoritos ;
	private ArrayList<String> precios ;
	private ArrayList<String> cupos ;
	
	public MyAdapter (Context context){ 
		favoritos = new ArrayList<String>() ;
		precios = new ArrayList<String>() ; 
		cupos = new ArrayList<String>() ; 
		favoritos.add (  "Por mi casa" ) ; 
		favoritos.add ( "en la U" ) ;
		favoritos.add ( "en el metro" ) ; 
		precios.add (  "$65/min" ) ; 
		precios. add (  "$10000/min"  ) ; 
		precios.add (  "$800/min" ) ;
		cupos.add( "43 cupos ") ;
		cupos.add( "62 cupos ") ; 
		cupos.add( "14 cupos ") ; 
		this.context = context ; 
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return favoritos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return favoritos.get(position) ; 
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
	    favoritos.remove(favoritos.get(position));;
	    precios.remove(precios.get(position));
	    cupos.remove(cupos.get(position));
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
		TextView titleTextView =  (TextView) row.findViewById(R.id.textView121) ;
		titleTextView.setTypeface(tf) ;
		titleTextView.setText(favoritos.get (position) ) ;
		
		TextView cupo =  (TextView) row.findViewById(R.id.cuposFav) ;
		cupo.setTypeface(tf2) ;
		cupo.setText(cupos.get (position)) ;
		
		TextView precio =  (TextView) row.findViewById(R.id.precioFav) ;
		precio.setTypeface(tf2) ;
		precio.setText(precios.get (position)) ;
		return row;
	} 
	
}
