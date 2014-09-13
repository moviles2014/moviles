package parcados.activities;

import java.io.IOException;
import java.io.InputStream;

import parcados.mundo.Parcados;
import parcados.sqlite.DAO;

import com.example.parcados.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class CalculadoraActivity extends Activity {
	
	/*
	 * Maneja la creación de la activity 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculadora);
	}
}
