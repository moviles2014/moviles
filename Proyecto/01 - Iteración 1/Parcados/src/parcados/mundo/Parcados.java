package parcados.mundo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import parcados.sqlite.DAO;
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

	/**
	 * Manejador de base de datos
	 */
	private DAO dao;
	
	/**
	 * Guarda el precio del parqueadero seleccionado por el usuario para
	 * luego calcular el precio del parqueadero segun el tiempo transcurrido
	 */
	private int precioParqueadero ; 

	//--------------------------------------------------------------------------------------
	// Constructores
	//--------------------------------------------------------------------------------------

	

	/**
	 * Patrón singleton, da la instancia del mundo
	 * @param context - contexto de la aplicación
	 * @return la instancia de la aplicación
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
	 * Constructor de la clase principal de la aplicación
	 * @param context - contexto global de la aplicación
	 */
	public Parcados( Context context ) {
		dao = new DAO(context);
		dao.open();
		zonas = dao.getAllZonas();
		precioParqueadero = -1 ; 
		System.out.println(zonas);
		System.out.println(zonas.size());
	}

	//--------------------------------------------------------------------------------------
	// Métodos
	//--------------------------------------------------------------------------------------

	/**
	 * Da el precio del parqueadero seleccionado por el usuario
	 * @return precio del parqueadero
	 */
	public int getPrecioParqueadero() {
		return precioParqueadero;
	}

	/**
	 * guarda el precio del parqueadero seleccionado por el usuario
	 * @param precioParqueadero - precio del parqueadero
	 */
	public void setPrecioParqueadero(int precioParqueadero) {
		this.precioParqueadero = precioParqueadero;
	}
	
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


	public void loadZonas (InputStream in) throws IOException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line;
		while ((line = reader.readLine()) != null) {
			Zona zona = new Zona(line);
			zonas.add(zona);
			dao.crearZona(zona);
		}
		reader.close();
	}

	public void loadParq (InputStream in) throws IOException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line;
		while ((line = reader.readLine()) != null) {
			String[] datos = line.split(",");
			Parqueadero parq = new Parqueadero(datos[0], datos[1], datos[2], datos[3]);
			Zona zona = zonas.get(Integer.parseInt(datos[4]));
			System.out.println(zona);
			zona.agregarParqueadero(parq);			
			dao.crearParqueadero(parq, zona);
		}
		reader.close();
	}
}
