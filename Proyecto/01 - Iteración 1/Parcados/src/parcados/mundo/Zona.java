package parcados.mundo;

import java.util.ArrayList;

public class Zona {

	//--------------------------------------------------------------------------------------
	// Atributos
	//--------------------------------------------------------------------------------------
	/**
	 * Nombre de la zona
	 */
	private String nombre ;
	
	/**
	 * Lista de parqueaderos
	 */
	private ArrayList<Parqueadero> parqueaderos ; 

	//--------------------------------------------------------------------------------------
	// Constructores
	//--------------------------------------------------------------------------------------
	
	/**
	 * Constructor de la zona
	 * @param _nombre - nombre de la zona
	 */
	public Zona( String _nombre ) {
		nombre = _nombre ;
		parqueaderos = new ArrayList<Parqueadero>() ; 
	}

	//--------------------------------------------------------------------------------------
	// Métodos
	//--------------------------------------------------------------------------------------
	
	/**
	 * Retorna los parqueaderos de la zona
	 * @return parqueaderos de la zona
	 */
	public ArrayList<Parqueadero> darParqueaderos()
	{
		return parqueaderos;
	}
	
	/**
	 * Da el nombre de la zona
	 * @return el nombre de la zona
	 */
	public String darNombre()
	{
		return nombre;
	}
	
}
