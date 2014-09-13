package modelos;

public class Parqueadero {
	public int cupos ; 
	public int precio  ;
	public String horario ; 
	public String caracteristicas ; 
	public String direccion ; 
	public String nombre ; 
	public Parqueadero( String _nombre , String _horario , String _caracteristicas , String _direccion ) {
		precio = -1 ; 
		cupos = -1 ;
		nombre = _nombre ; 
		horario = _horario ; 
		caracteristicas = _caracteristicas ; 
		direccion = _direccion ; 
	}
}
