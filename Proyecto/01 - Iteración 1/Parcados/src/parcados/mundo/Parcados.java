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

	}

	//--------------------------------------------------------------------------------------
	// Métodos
	//--------------------------------------------------------------------------------------

	/**
	 * Da los parqueaderos dada una zona
	 * @param i - el id de la zona
	 * @return array con los parqueaderos buscados
	 */
	public ArrayList<Parqueadero> darParqueaderosDeZona ( int i ){
		return zonas.get(i).darParqueaderos() ; 
	}


	public void actualizarParqueadero ( String nombre , int precio , int cupos ){ 
		Parqueadero parq = darParqueadero(nombre);
		parq.actualizarCupos(cupos);
		parq.actualizarPrecio(precio);
		dao.actualizarParqueadero(nombre, precio, cupos ) ; 
	}
	/**
	 * Retorna todas las zonas
	 * @return las zonas
	 */
	public ArrayList<Zona> darZonas ( ){ 
		return zonas ; 
	}


	/**
	 * Carga las zonas dado un archivo de entrada
	 * @param in el archivo de entrada
	 * @throws IOException si hubo problemas de lectura
	 */
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

	/**
	 * Retorna todas las zonas en la base de datos
	 * @return las zonas
	 */
	public ArrayList<Zona> getAllZonas ()  {
		return dao.getAllZonas(); 
	}

	/**
	 * Carga los parqueaderos dado un archivo de entrada
	 * @param in el archivo de entrada
	 * @throws IOException si hubo problemas de lectura
	 */
	public void loadParq (InputStream in) throws IOException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line;
		while ((line = reader.readLine()) != null) {
			String[] datos = line.split(",");
			Parqueadero parq = new Parqueadero(datos[0], datos[1], datos[2], datos[3] , -1 , -1  );
			Zona zona = zonas.get(Integer.parseInt(datos[4]));
			zona.agregarParqueadero(parq);			
			dao.crearParqueadero(parq, zona);
		}
		reader.close();
	}

	/**
	 * Da un parqueadero dado su nombre
	 * @param nombre - el nombre del parqueadero
	 * @return el parqueadero
	 */
	public Parqueadero darParqueadero(String nombre)
	{
		for (int i = 0; i < zonas.size(); i++ )
		{
			int actual = zonas.get(i).darParqueadero(nombre);
			if ( actual != -1)
				return zonas.get(i).darParqueaderos().get(actual);
		}
		return null;
	}

}
