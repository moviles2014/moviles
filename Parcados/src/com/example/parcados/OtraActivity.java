package com.example.parcados;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class OtraActivity extends ListActivity {
	
	public OtraActivity yo ;
	private String respuesta ;
	List<String> lista = new ArrayList<String>() ; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		yo = this ; 
		setContentView(R.layout.activity_otra) ; 
		Intent intent  = getIntent() ; 
		
		String msg = intent.getStringExtra( "text") ; 
		
		// para ersponder despues 
		respuesta = msg ; 
		
		TextView tv = (TextView) findViewById(R.id.textView2) ; 
		tv.setText(msg) ;
		
		
		lista.add("colo") ; 
		lista.add("coletieri" ) ; 
		lista.add("otra vaina" ) ;
		
		// se puede usar cualquier objeto y lo que se muestra seria el toString
		ArrayAdapter<String> adapter = new ArrayAdapter<String >(this, android.R.layout.simple_list_item_1 , lista) ; 
		setListAdapter(adapter) ; 
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		System.out.println( " se presiono " + lista.get(position));
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}
	
	public void goBack ( View v ) {
		// para responderle al activity que llama
		respuesta += " algo " ; 
		Intent intent = new Intent () ; 
		intent.putExtra("respuesta" , respuesta  ) ; 
		setResult(RESULT_OK, intent ) ; 
		finish() ;
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuItem item = menu.add(R.string.action_settings) ;
		item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				finish() ; 
				Intent intent = new Intent( OtraActivity.this, TestActivity.class) ;
				 startActivity(intent) ;
				return false;
			}
		}) ; 
		return super.onCreateOptionsMenu(menu);
	}
}
