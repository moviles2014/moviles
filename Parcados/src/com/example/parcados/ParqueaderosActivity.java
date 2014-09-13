package com.example.parcados;

import java.util.ArrayList;
import java.util.List;

import modelos.Data;
import modelos.Parqueadero;
import modelos.Zona;

import android.R.integer;
import android.R.integer;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ParqueaderosActivity extends ListActivity {
	List<String> lista = new ArrayList<String>() ;
	ArrayList<Parqueadero> parqueaderos ; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parqueaderos) ;
		
		Intent intent = getIntent() ; 
		int id  = Integer.parseInt(intent.getStringExtra("id"))  ; 
		
		parqueaderos = Data.darInstancia(getApplicationContext()).darParqueaderosDeZona(id) ; 
		
		for ( int i = 0 ;i < parqueaderos.size() ; i ++ ){
			lista.add(parqueaderos.get(i).nombre) ;
//			System.out.println( zonas.get(i).nombre);
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String >(this, android.R.layout.simple_list_item_1 , lista) ; 
		setListAdapter(adapter) ; 
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		 Intent intent = new Intent(this, DetalleParqueaderoActivity.class) ;
//		 intent.putExtra("text", "texto de main" ) ; 
//		 startActivityForResult(intent, REQUEST_CODE) ;
		 Parqueadero p = parqueaderos.get(position) ; 
		 intent.putExtra("nombre", p.nombre ) ;
		 intent.putExtra("cupos", "-1" ) ;
		 intent.putExtra("precio", "-1" ) ;
		 intent.putExtra("horario", p.horario ) ;
		 intent.putExtra("caracteristicas", p.caracteristicas ) ;
		 intent.putExtra("direccion", p.direccion ) ;
		 
		 
		 startActivity(intent) ;
//		System.out.println( " se presiono " + lista.get(position));
	}
}
