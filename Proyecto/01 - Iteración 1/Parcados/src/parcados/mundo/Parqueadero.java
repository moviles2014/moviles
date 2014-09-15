package parcados.mundo;

public class Parqueadero {

	//--------------------------------------------------------------------------------------
	// Atributos
	//--------------------------------------------------------------------------------------

	/**
	 * Cupos del parqueadero
	 */
	private int cupos ; 

	/**
	 * Precio del parqueadero
	 */
	private int precio  ;
	
	/**
	 * Horario del parqueadero
	 */
	private String horario ; 
	
	/**
	 * Caracter�sticas del parqueadero
	 */
	private String caracteristicas ; 
	
	/**
	 * Direcci�n del parqueadero
	 */
	private String direccion ; 
	
	/**
	 * Nombre del parqueadero
	 */
	private String nombre ; 
	
	/**
	 * �ltima actualizaci�n de los datos del parqueadero
	 */
	private String ultimaact;


	//--------------------------------------------------------------------------------------
	// Constructor
	//--------------------------------------------------------------------------------------
	
	/**
	 * M�todo constructor del parqueadero
	 * @param _nombre el nombre
	 * @param _horario elhorario
	 * @param _caracteristicas las caracter�sticas
	 * @param _direccion la direcci�n
	 * @param _precio el precio
	 * @param _cupos los cupos
	 */
	public Parqueadero( String _nombre , String _horario , String _caracteristicas , String _direccion , int _precio , int _cupos ) {
		precio = _precio  ; 
		cupos = _cupos ;
		nombre = _nombre ; 
		horario = _horario ; 
		caracteristicas = _caracteristicas ; 
		direccion = _direccion ; 
	}

	//--------------------------------------------------------------------------------------
	// M�todos
	//--------------------------------------------------------------------------------------

	/**
	 * Da el horario del parqueadero
	 * @return el horario del parqueadero
	 */
	public String darHorario()
	{
		return horario;
	}
	/**
	 * Da el nombre del parqueadero
	 * @return el nombre del parqueadero
	 */
	public String darNombre()
	{
		return nombre;
	}
	/**
	 * Da las caracteristicas de parqueadero
	 * @return las caracteristicas del parqueadero
	 */
	public String darCaracteristicas()
	{
		return caracteristicas;
	}
	/**
	 * Da la direcci�n del parqueadero
	 * @return la direcci�n del parqueadero
	 */
	public String darDireccion()
	{
		return direccion;
	}
	/**
	 * Da los cupos del parqueadero
	 * @return los cupos del parqueadero
	 */
	public int darCupos()
	{
		return cupos;
	}
	/**
	 * Da el precio del parqueadero
	 * @return el precio del parqueadero
	 */
	public int darPrecio()
	{
		return precio;
	}

	/**
	 * Da la �ltima actualizaci�n
	 * @return la �ltima actualizaci�n
	 */
	public String darUltimaAct()
	{
		return ultimaact;
	}

	/**
	 * Actualiza los cupos del parqueadero dado el par�metro
	 * @param _cupos cupos del parqueadero
	 */	
	public void actualizarCupos( int _cupos )
	{
		cupos = _cupos;
	}

	/**
	 * Actualiza el precio del parqueadero dado el par�metro
	 * @param _precio - precio del parqueadero
	 */
	public void actualizarPrecio( int _precio )
	{
		precio = _precio;
	}

	/**
	 * Pone la fecha de �ltima actualizaci�n
	 * @param _ultimaact - fecha de �ltima actualizaci�n
	 */
	public void setUltimaAct( String _ultimaact )
	{
		ultimaact = _ultimaact;
	}
}
