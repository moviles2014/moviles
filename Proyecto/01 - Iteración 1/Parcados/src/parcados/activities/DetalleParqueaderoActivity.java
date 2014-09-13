package parcados.activities;

import parcados.mundo.Parcados;
import parcados.mundo.Parqueadero;

import com.example.parcados.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetalleParqueaderoActivity extends Activity {

	private Parqueadero actual;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle_parqueadero) ; 		
		Intent intent = getIntent(); 		
		String idparq = intent.getStringExtra("idparq");
		String idzona = intent.getStringExtra("idzona");	
		System.out.println(idzona);
		actual = Parcados.darInstancia(getApplicationContext()).darParqueaderosDeZona(Integer.parseInt(idzona)).get(Integer.parseInt(idparq));

		TextView tx1 = (TextView) findViewById(R.id.textView1) ;
		tx1.setText(actual.darNombre()) ; 
		TextView tx2 = (TextView) findViewById(R.id.textView2) ;
		tx2.setText(Integer.toString(actual.darCupos())) ; 
		TextView tx3 = (TextView) findViewById(R.id.textView3) ;
		tx3.setText(Integer.toString(actual.darPrecio())) ; 
		TextView tx4 = (TextView) findViewById(R.id.textView4) ;
		tx4.setText(actual.darHorario()) ; 
		TextView tx5 = (TextView) findViewById(R.id.textView5) ; 
		tx5.setText(actual.darCaracteristicas()) ; 
		TextView tx6 = (TextView) findViewById(R.id.textView6) ;
		tx6.setText(actual.darDireccion()) ; 

	}
}
