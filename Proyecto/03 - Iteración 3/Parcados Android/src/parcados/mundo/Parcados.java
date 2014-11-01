package parcados.mundo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;


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
	 * ArrayList con las empresas donde se encuentran ubicados los parqueaderos
	 */
	private ArrayList <Empresa> empresas ; 
	
	private ArrayList<String> zonas;

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
		empresas = getAllempresas();
		zonas = new ArrayList<String>();
		llenarZonas();
		sortEverything();
	}

	//--------------------------------------------------------------------------------------
	// Métodos
	//--------------------------------------------------------------------------------------

	/**
	 * Da los parqueaderos dada una Empresa
	 * @param i - el id de la Empresa
	 * @return array con los parqueaderos buscados
	 */
	public ArrayList<Parqueadero> darParqueaderosDeEmpresa ( String nombre ){
		for(int i = 0; i < empresas.size(); i++)
			if (empresas.get(i).darNombre().equals(nombre))
				return empresas.get(i).darParqueaderos();
		return null;
	}


	public void actualizarParqueadero ( String nombre , int precio , int cupos ){ 
		Parqueadero parq = darParqueaderoPorNombre(nombre);
		parq.actualizarCupos(cupos);
		parq.actualizarPrecio(precio);
		dao.actualizarParqueadero(nombre, precio, cupos ) ; 
	}
	/**
	 * Retorna todas las empresas
	 * @return las empresas
	 */
	public ArrayList<Empresa> darEmpresas( ){ 
		return empresas ; 
	}


	/**
	 * Carga las empresas dado un archivo de entrada
	 * @param in el archivo de entrada
	 * @throws IOException si hubo problemas de lectura
	 */
	public void loadEmpresas (InputStream in) throws IOException
	{
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line;
		while ((line = reader.readLine()) != null) {
			Empresa Empresa = new Empresa(line);
			empresas.add(Empresa);
			dao.crearEmpresa(Empresa);
//			DB_Queries.crearEmpresa(line) ; 
		}
		reader.close();
	}

	/**
	 * Retorna todas las empresas en la base de datos
	 * @return las empresas
	 */
	public ArrayList<Empresa> getAllempresas ()  {
		return dao.getAllEmpresas(); 
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
			Parqueadero parq = new Parqueadero(datos[0], datos[2], datos[3], datos[5] , datos[4], -1 , -1, Double.parseDouble(datos[6]), Double.parseDouble(datos[7]) );
//			DB_Queries.crearParqueadero(datos[0], datos[2], datos[3], datos[5] , datos[4], -1 , -1, Double.parseDouble(datos[6]), Double.parseDouble(datos[7]) );
			
			Empresa empresa = empresas.get(Integer.parseInt(datos[1]));
			empresa.agregarParqueadero(parq);
//			DB_Queries.crearRelacion(empresa.darNombre(), parq.darNombre() ) ; 
			
			dao.crearParqueadero(parq, empresa);
			agregarZona(datos[2]);
		}
		reader.close();
		sortEverything();
	}	
	
	public void agregarZona(String zona)
	{
		boolean existe = false;
		for (int i = 0; i < zonas.size(); i++)
			if (zonas.get(i).equals(zona))
				existe = true;
		if (!existe)
			zonas.add(zona);
	}
	
	public Empresa darEmpresa(String nombre)
	{
		if (empresas == null)
			empresas = new ArrayList<Empresa>();
		for (int i = 0; i < empresas.size(); i++)
			if (empresas.get(i).darNombre().equals(nombre))
				return empresas.get(i);
		return null;
	}

	public void loadParEmpresa(Parqueadero parq)
	{
		String nEmpresa = parq.darNombre().split(" ")[0];
		Empresa empr = darEmpresa(nEmpresa);
		if (empr == null)
		{
			empr = new Empresa(nEmpresa);
			empresas.add(empr);			
		}
			
		empr.agregarParqueadero(parq);		
	}

	public void sortEverything()
	{
		Collections.sort(empresas);	
		for (int i = 0; i < empresas.size(); i++)
			empresas.get(i).sortParq();
		Collections.sort(zonas);
	}


	public ArrayList<String> filtrarPorEmpresa()
	{
		ArrayList<String> res = new ArrayList<String>();
		for(int i = 0; i < empresas.size(); i++)
		{
			res.add(empresas.get(i).darNombre());
		}
		return res;
	}
	
	public ArrayList<String> filtrarPorZonas()
	{
		return zonas;
	}

	public ArrayList<Parqueadero> darParqueaderosZona(String zona)
	{
		ArrayList<Parqueadero> res = new ArrayList<Parqueadero>();
		for (int i = 0; i < empresas.size(); i++) 
			res.addAll(empresas.get(i).darParqueaderosZona(zona));
		return res;
	}
	
	public ArrayList<Parqueadero> darTodosLosParqueaderos()
	{
		ArrayList<Parqueadero> res = new ArrayList<Parqueadero>();
		for (int i = 0; i < empresas.size(); i++) 
			res.addAll(empresas.get(i).darParqueaderos());
		return res;
	}
	
	public void llenarZonas()
	{
		for (int i = 0; i < empresas.size(); i++) 
		{
			ArrayList<Parqueadero> parqs = empresas.get(i).darParqueaderos();
			for (int j = 0; j < parqs.size(); j++) {
				agregarZona(parqs.get(j).darZona());				
			}
		}
	}
	
	public Parqueadero darParqueaderoPorNombre(String nombre)
	{
		for (int i = 0; i < empresas.size(); i++) {
			Parqueadero parq = empresas.get(i).darParqueaderoPorNombre(nombre);
			if ( parq != null)
				return parq;			
		}
		return null;
	}
	
	public Parqueadero darParqueaderoPorDireccion(String direccion)
	{
		for (int i = 0; i < empresas.size(); i++) {
			Parqueadero parq = empresas.get(i).darParqueaderoPorDireccion(direccion);
			if ( parq != null)
				return parq;			
		}
		return null;
	}

}
