package parcados.mundo;

import java.util.ArrayList;
import java.util.Collections;

public class Empresa implements Comparable<Empresa>{
	//--------------------------------------------------------------------------------------
	// Atributos
	//--------------------------------------------------------------------------------------
	/**
	 * Nombre de la Empresa
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
	 * Constructor de la Empresa
	 * @param _nombre - nombre de la Empresa
	 */
	public Empresa( String _nombre ) {
		nombre = _nombre ;
		parqueaderos = new ArrayList<Parqueadero>() ; 
	}

	//--------------------------------------------------------------------------------------
	// Métodos
	//--------------------------------------------------------------------------------------

	/**
	 * Retorna los parqueaderos de la Empresa
	 * @return parqueaderos de la Empresa
	 */
	public ArrayList<Parqueadero> darParqueaderos()
	{
		return parqueaderos;
	}

	/**
	 * Da el nombre de la Empresa
	 * @return el nombre de la Empresa
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
		if (!existeParq(_parq))
			parqueaderos.add(_parq);
	}

	/**
	 * Da un parqueadero dado su nombre
	 * @param nombre el nombre del parqueadero buscado
	 * @return la posición del parqueadero
	 */
	public Parqueadero darParqueaderoPorNombre(String nombre)
	{
		for (int i = 0; i < parqueaderos.size(); i++ )
			if (parqueaderos.get(i).darNombre().equals(nombre))
				return parqueaderos.get(i);
		return null;
	}
	
	public Parqueadero darParqueaderoPorDireccion(String direccion)
	{
		for (int i = 0; i < parqueaderos.size(); i++ )
			if (parqueaderos.get(i).darDireccion().equals(direccion))
				return parqueaderos.get(i);
		return null;
	}

	public boolean existeParq(Parqueadero parq)
	{ 
		for (int i = 0; i < parqueaderos.size(); i++)
			if (parqueaderos.get(i).darNombre().equalsIgnoreCase(parq.darNombre()))
				return true;
		return false;
	}
	
	public ArrayList<Parqueadero> darParqueaderosZona(String zona)
	{
		ArrayList<Parqueadero> res = new ArrayList<Parqueadero>();
		for (int i = 0; i < parqueaderos.size(); i++)
			if(parqueaderos.get(i).darZona().equals(zona))
				res.add(parqueaderos.get(i));
		return res;
	}

	public void sortParq()
	{
		Collections.sort(parqueaderos);
	}	


	@Override
	public int compareTo(Empresa another) {
		// TODO Auto-generated method stub
		return nombre.compareTo(another.darNombre());
	}
}
