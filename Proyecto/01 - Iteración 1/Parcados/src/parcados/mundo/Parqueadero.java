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
	 * Características del parqueadero
	 */
	private String caracteristicas ; 
	
	/**
	 * Dirección del parqueadero
	 */
	private String direccion ; 
	
	/**
	 * Nombre del parqueadero
	 */
	private String nombre ; 
	
	/**
	 * Última actualización de los datos del parqueadero
	 */
	private String ultimaact;


	//--------------------------------------------------------------------------------------
	// Constructor
	//--------------------------------------------------------------------------------------
	
	/**
	 * Método constructor del parqueadero
	 * @param _nombre el nombre
	 * @param _horario elhorario
	 * @param _caracteristicas las características
	 * @param _direccion la dirección
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
	// Métodos
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
	 * Da la dirección del parqueadero
	 * @return la dirección del parqueadero
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
	 * Da la última actualización
	 * @return la última actualización
	 */
	public String darUltimaAct()
	{
		return ultimaact;
	}

	/**
	 * Actualiza los cupos del parqueadero dado el parámetro
	 * @param _cupos cupos del parqueadero
	 */	
	public void actualizarCupos( int _cupos )
	{
		cupos = _cupos;
	}

	/**
	 * Actualiza el precio del parqueadero dado el parámetro
	 * @param _precio - precio del parqueadero
	 */
	public void actualizarPrecio( int _precio )
	{
		precio = _precio;
	}

	/**
	 * Pone la fecha de última actualización
	 * @param _ultimaact - fecha de última actualización
	 */
	public void setUltimaAct( String _ultimaact )
	{
		ultimaact = _ultimaact;
	}
}
