package modelos;

import java.util.ArrayList;

import android.content.Context;

public class Data {
	
	private static Data instancia;
	
	private ArrayList <Zona> zonas ; 
	
	public static Data darInstancia(Context context)
	{
		if(instancia == null)
		{
			instancia = new Data(context);
		}
		return instancia;
	}
	
	public Data( Context context ) {
		zonas = new ArrayList<Zona>() ;  
		zonas.add( new Zona("sur")) ;
		zonas.add( new Zona("norte")) ;
		zonas.add( new Zona("firme")) ; 
	}
	public int getNum ( ) { 
		return 3 ; 
	}
	
	public ArrayList<Parqueadero> darParqueaderosDeZona ( int i ){
		return zonas.get(i).parqueaderos ; 
	}

	public ArrayList<Zona> darZonas ( ){ 
		return zonas ; 
	}
	
	
}
