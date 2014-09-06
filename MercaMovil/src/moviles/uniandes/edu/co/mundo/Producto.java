package moviles.uniandes.edu.co.mundo;

import java.util.ArrayList;
import java.util.Date;

public class Producto {
	
	public static final String[] ARREGLO_CATEGORIAS = {"Abarrotes", "Aseo Hogar", "Aseo Personal", "Carnes", "Lacteos"};
	
	private String marca;
	private String nombre;	
	private String categoria;
	private Date fechaUltimaCompra;
	private ArrayList<Presentacion> presentaciones;

	public Producto(String nombre, String marca, String categoria, Date fechaUltimaCompra )
	{
		this.categoria = categoria;
		this.fechaUltimaCompra = fechaUltimaCompra;
		this.marca = marca;
		this.nombre = nombre;
		presentaciones = new ArrayList<Presentacion>();
	}
	
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public Date getFechaUltimaCompra() {
		return fechaUltimaCompra;
	}
	public void setFechaUltimaCompra(Date fechaUltimaCompra) {
		this.fechaUltimaCompra = fechaUltimaCompra;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<Presentacion> getPresentaciones() {
		return presentaciones;
	}

	public void setPresentaciones(ArrayList<Presentacion> presentaciones) {
		this.presentaciones = presentaciones;
	}
	
	public String cantidadUnidadesEnDespensa()
	{
		String res = marca + ": ";
		for( int i = 0; i < presentaciones.size(); i++ )
		{
			res += presentaciones.get(i).getNumActualEnDespensa() + " unidades de la presentación en " + presentaciones.get(i).getUnidad() +", ";
		}
		
		int ultimaComa = res.charAt(res.lastIndexOf(","));
		res = res.substring(0, ultimaComa) + ".";
		
		return res;
	}
	
	public boolean agregarPresentacion( Presentacion presentacion )
	{
		for(int i = 0; i < presentaciones.size(); i++)
		{
			if( presentaciones.get(i).getUnidad().equals(presentacion.getUnidad()) && presentaciones.get(i).getTamanio() == presentacion.getTamanio() )
				return false;
		}
		presentaciones.add(presentacion);
		return true;
	}
	
	public ArrayList<Presentacion> darPresentacionesProximasATerminarse()
	{
		ArrayList<Presentacion> res = new ArrayList<Presentacion>();
		for(int i = 0; i < presentaciones.size(); i++)
		{
			if(presentaciones.get(i).getNumActualEnDespensa()<=presentaciones.get(i).getCantMinima())
				res.add(presentaciones.get(i));
		}
		return res;
	}
	
	public Presentacion darPresentacionPorTamanioYUnidad( int tamanio, String unidad )
	{
		for( int i = 0; i < presentaciones.size(); i++ )
		{
			if( presentaciones.get(i).getTamanio() == tamanio && presentaciones.get(i).getUnidad().equals(unidad) )
				return presentaciones.get(i);
		}
		return null;
	}

}
