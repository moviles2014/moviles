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
	// M�todos
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
	
	/**
	 * Asigna los parqueaderos dado un arreglo
	 * @param _parqs el arreglo de parqueaderos
	 */
	public void setParqueaderos( ArrayList<Parqueadero> _parqs)
	{
		parqueaderos = _parqs;
	}
	
	/**
	 * Agrega un parqueadero al arraylist de parqueaderos
	 * @param _parq - el parqueadero
	 */
	public void agregarParqueadero(Parqueadero _parq)
	{
		parqueaderos.add(_parq);
	}
	
	/**
	 * Da un parqueadero dado su nombre
	 * @param nombre el nombre del parqueadero buscado
	 * @return la posici�n del parqueadero
	 */
	public int darParqueadero(String nombre)
	{
		for (int i = 0; i < parqueaderos.size(); i++ )
			if (parqueaderos.get(i).darNombre().equals(nombre))
				return i;
		return -1;
	}
	
}
