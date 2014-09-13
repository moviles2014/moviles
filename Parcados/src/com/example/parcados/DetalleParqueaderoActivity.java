package com.example.parcados;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetalleParqueaderoActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle_parqueadero) ; 
		
		Intent intent = getIntent()  ; 
		
		
		TextView tx1 = (TextView) findViewById(R.id.textView1) ;
		tx1.setText(intent.getStringExtra("nombre")) ; 
		TextView tx2 = (TextView) findViewById(R.id.textView2) ;
		tx2.setText(intent.getStringExtra("cupos")) ; 
		TextView tx3 = (TextView) findViewById(R.id.textView3) ;
		tx3.setText(intent.getStringExtra("precio")) ; 
		TextView tx4 = (TextView) findViewById(R.id.textView4) ;
		tx4.setText(intent.getStringExtra("horario")) ; 
		TextView tx5 = (TextView) findViewById(R.id.textView5) ; 
		tx5.setText(intent.getStringExtra("caracteristicas")) ; 
		TextView tx6 = (TextView) findViewById(R.id.textView6) ;
		tx6.setText(intent.getStringExtra("direccion")) ; 
		
	}
}
