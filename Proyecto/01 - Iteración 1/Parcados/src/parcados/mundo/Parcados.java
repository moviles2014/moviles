package parcados.mundo;

import java.util.ArrayList;

import android.content.Context;

public class Parcados {

	//--------------------------------------------------------------------------------------
	// Atributos
	//--------------------------------------------------------------------------------------

	/**
	 * Instancia de la clase
	 */
	private static Parcados instancia;

	/**
	 * ArrayList con las zonas donde se encuentran ubicados los parqueaderos
	 */
	private ArrayList <Zona> zonas ; 

	//--------------------------------------------------------------------------------------
	// Constructores
	//--------------------------------------------------------------------------------------

	/**
	 * Patr�n singleton, da la instancia del mundo
	 * @param context - contexto de la aplicaci�n
	 * @return la instancia de la aplicaci�n
	 */
	public static Parcados darInstancia(Context context)
	{
		if(instancia == null)
		{
			instancia = new Parcados(context);
		}
		return instancia;
	}

	/**
	 * Constructor de la clase principal de la aplicaci�n
	 * @param context - contexto global de la aplicaci�n
	 */
	public Parcados( Context context ) {
		zonas = new ArrayList<Zona>() ;  
	}

	//--------------------------------------------------------------------------------------
	// M�todos
	//--------------------------------------------------------------------------------------

	/**
	 * Da los parqueaderos dada una zona
	 * @param i - el id de la zona
	 * @return array con los parqueaderos buscados
	 */
	public ArrayList<Parqueadero> darParqueaderosDeZona ( int i ){
		return zonas.get(i).darParqueaderos() ; 
	}

	/**
	 * Retorna todas las zonas
	 * @return las zonas
	 */
	public ArrayList<Zona> darZonas ( ){ 
		return zonas ; 
	}


}
