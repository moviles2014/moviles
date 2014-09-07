package com.example.parcados;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.layout) ;
		
		for ( int i =  0 ; i < 0 ; i ++ ) { 
			Button button = new Button(this) ; 
			button.setText("otro s" + i  ) ; 
			layout.addView(button) ;
		}
		Toast msg = Toast.makeText(this	, "hello covas", 3)  ; 
		msg.show() ; 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		Toast.makeText(this, "escogiste " + item.getTitle(), 3).show() ;
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void irATestActivity ( View  v) 	{ 
		 Intent intent = new Intent(this, TestActivity.class) ;
		 startActivity(intent) ;
	}
	
	
	
}
