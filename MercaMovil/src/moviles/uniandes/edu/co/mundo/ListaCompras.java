package moviles.uniandes.edu.co.mundo;

import java.util.ArrayList;
import java.util.Date;

public class ListaCompras {
	
	private String nombre;
	private boolean compraRealizada;
	private Date fechaPrevistaProximaCompra;
	private ArrayList<CompraPrevista> itemListaCompra;
	
	public ListaCompras(String nombre, boolean compraRealizada, Date fechaPrevistaProximaCompra, ArrayList<CompraPrevista> itemListaCompra)
	{
		this.nombre = nombre;
		this.compraRealizada = compraRealizada;
		this.fechaPrevistaProximaCompra = fechaPrevistaProximaCompra;
		this.itemListaCompra = itemListaCompra;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public boolean isCompraRealizada() {
		return compraRealizada;
	}
	public void setCompraRealizada(boolean compraRealizada) {
		this.compraRealizada = compraRealizada;
	}
	public Date getFechaPrevistaProximaCompra() {
		return fechaPrevistaProximaCompra;
	}
	public void setFechaPrevistaProximaCompra(Date fechaPrevistaProximaCompra) {
		this.fechaPrevistaProximaCompra = fechaPrevistaProximaCompra;
	}

	public ArrayList<CompraPrevista> getCantidades() {
		return itemListaCompra;
	}

	public void setCantidades(ArrayList<CompraPrevista> cantidades) {
		this.itemListaCompra = cantidades;
	}
	
	public double calcularCostoLista()
	{
		double costo = 0;
		for(int i = 0; i<itemListaCompra.size(); i++)
		{
			CompraPrevista compra = itemListaCompra.get(i);
			costo += compra.getPresentacion().getUltimoPrecioDeCompra()*compra.getCantidad();
		}
		return costo;
	}

	public void agregarProductoAListaCompras(Producto producto, Presentacion presentacionProducto, int cantidad) 
	{
		itemListaCompra.add(new CompraPrevista(producto, presentacionProducto, cantidad));
	}
	
	public void eliminarPresentacionDeListDeCompras(Presentacion presentacionAEliminar)
	{
		for( int i = 0; i<itemListaCompra.size(); i++ )
		{
			if(itemListaCompra.get(i).getPresentacion().equals(presentacionAEliminar))
				itemListaCompra.remove(i);
		}
	}

}
