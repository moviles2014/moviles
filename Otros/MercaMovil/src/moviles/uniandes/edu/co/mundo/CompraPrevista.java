package moviles.uniandes.edu.co.mundo;

public class CompraPrevista {
	
	private Producto producto;
	private Presentacion presentacion;
	private int cantidad;
	private boolean comprado;
	
	public CompraPrevista( Producto producto, Presentacion presentacion, int cantidad )
	{
		this.setProducto(producto);
		this.presentacion = presentacion;
		this.cantidad = cantidad;
		setComprado(false);
	}
	
	public Presentacion getPresentacion() {
		return presentacion;
	}
	public void setPresentacion(Presentacion presentacion) {
		this.presentacion = presentacion;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public boolean isComprado() {
		return comprado;
	}

	public void setComprado(boolean comprado) {
		this.comprado = comprado;
	}
	

}
