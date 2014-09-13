package modelos;

import java.util.ArrayList;

public class Zona {
	public int id ;
	public String nombre ; 
	public ArrayList<Parqueadero> parqueaderos ; 
	public Zona( String _nombre ) {
		nombre = _nombre ;
		parqueaderos = new ArrayList<Parqueadero>() ; 
		parqueaderos.add(new Parqueadero(nombre +  " parking 1"  , nombre + " L-V", nombre + " firme", nombre + " por ahi" )) ;
		parqueaderos.add(new Parqueadero(nombre +  " parking 2",nombre + " M-V", nombre + " mono", nombre +  " esnaqui de la via tacuaren" )) ; 
		parqueaderos.add(new Parqueadero(nombre +  " parking 3",nombre + " J-V", nombre + " monkey", nombre +   " a la tavuel de la esnaqui" )) ; 
		parqueaderos.add(new Parqueadero(nombre +  " parking 4",nombre + " L-S", nombre +  " mefir", nombre +  " despues del semaforo" )) ; 
		parqueaderos.add(new Parqueadero(nombre +  " parking 5 ",nombre + " L-D", nombre + " elegante", nombre + " no como quien va si no como quien viene" )) ; 
		parqueaderos.add(new Parqueadero(nombre +  " parking 6",nombre + " J-V", nombre +  " caribeño", nombre +  " por allá" )) ; 
		parqueaderos.add(new Parqueadero(nombre +  " parking 7 ",nombre + " L-V", nombre +  " tropical", nombre +  " por aquí" )) ; 
	}
}
