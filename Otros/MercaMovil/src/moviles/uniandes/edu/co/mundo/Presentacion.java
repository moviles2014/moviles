package moviles.uniandes.edu.co.mundo;

public class Presentacion {
	
	public static final String[] ARREGLO_UNIDADES = {"Kg","Litro", "Paquete", "Botella"};
	
	private int cantMinima;
	private int numActualEnDespensa;
	private int tamanio;
	private double ultimoPrecioDeCompra;
	private String unidad;
	
	
	public Presentacion( int cantMinima, int numActualEnDespensa, int tamanio, double ultimoPrecioDeCompra, String unidad )
	{
		this.cantMinima = cantMinima;
		this.numActualEnDespensa = numActualEnDespensa;
		this.tamanio = tamanio;
		this.ultimoPrecioDeCompra = ultimoPrecioDeCompra;
		this.unidad = unidad;
	}
	
	public int getCantMinima() {
		return cantMinima;
	}
	public void setCantMinima(int cantMinima) {
		this.cantMinima = cantMinima;
	}
	public int getNumActualEnDespensa() {
		return numActualEnDespensa;
	}
	public void setNumActualEnDespensa(int numActualEnDespensa) {
		this.numActualEnDespensa = numActualEnDespensa;
	}
	public int getTamanio() {
		return tamanio;
	}
	public void setTamanio(int tamanio) {
		this.tamanio = tamanio;
	}
	public double getUltimoPrecioDeCompra() {
		return ultimoPrecioDeCompra;
	}
	public void setUltimoPrecioDeCompra(double ultimoPrecioDeCompra) {
		this.ultimoPrecioDeCompra = ultimoPrecioDeCompra;
	}
	public String getUnidad() {
		return unidad;
	}
	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

}
