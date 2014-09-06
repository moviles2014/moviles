package moviles.uniandes.edu.co.mundo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.content.Context;


public class MercaMovil {

	private ArrayList<Producto> productos;
	private ArrayList<ListaCompras> listasDeCompras;

	private static MercaMovil instancia;


	public static MercaMovil darInstancia(Context context)
	{
		if(instancia == null)
		{
			instancia = new MercaMovil(context);
		}
		return instancia;
	}

	public MercaMovil(Context context)
	{
		productos = new ArrayList<Producto>();
		listasDeCompras = new ArrayList<ListaCompras>();
		
		try 
		{			  
			BufferedReader br = new BufferedReader(new InputStreamReader(context.getResources().getAssets().open("productos.txt")));
			String linea = br.readLine();
			while( !linea.equals("**Fin productos**") )
			{
				String nombreProducto = linea.split(";")[0];
				String marcaProducto = linea.split(";")[1];
				String categoria = linea.split(";")[2];
				String fechaCompra = linea.split(";")[3];
				
				GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(fechaCompra.split("/")[2]), Integer.parseInt(fechaCompra.split("/")[1])-1, Integer.parseInt(fechaCompra.split("/")[0]));
				Date fecha = gc.getTime();
				
				Producto p = new Producto(nombreProducto, marcaProducto, categoria, fecha);
				
				int cantPresentaciones = Integer.parseInt(linea.split(";")[4]);
				ArrayList<Presentacion> presentaciones = new ArrayList<Presentacion>();
				for(int i = 0; i< cantPresentaciones; i++)
				{
					linea = br.readLine();
					int tamanio = Integer.parseInt(linea.split(";")[0]);
					String unidad = linea.split(";")[1];
					int cantMinima = Integer.parseInt(linea.split(";")[2]);
					int numEnDespensa = Integer.parseInt(linea.split(";")[3]);
					double precio = Double.parseDouble(linea.split(";")[4]);
					presentaciones.add(new Presentacion(cantMinima, numEnDespensa, tamanio, precio, unidad));
				}
				agregarNuevoProducto(p, presentaciones);
				
				linea = br.readLine();
				linea = br.readLine();
			}
			br.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public ArrayList<Producto> getProductos() {
		return productos;
	}
	public void setProductos(ArrayList<Producto> productos) {
		this.productos = productos;
	}
	public ArrayList<ListaCompras> getListasDeCompras() {
		return listasDeCompras;
	}
	public void setListasDeCompras(ArrayList<ListaCompras> listasDeCompras) {
		this.listasDeCompras = listasDeCompras;
	}

	public ListaCompras darListaPorNombre( String nombreLista )
	{
		for( int i = 0; i < listasDeCompras.size(); i++ )
		{
			if(listasDeCompras.get(i).getNombre().equals(nombreLista))
				return listasDeCompras.get(i);
		}
		return null;	
	}

	public void agregarNuevoProducto( Producto productoNuevo, ArrayList<Presentacion> presentaciones )
	{
		for(int i = 0; i < presentaciones.size(); i++)
			productoNuevo.agregarPresentacion(presentaciones.get(i));
		productos.add(productoNuevo);
	}

	public void agregarPresentacionAProducto(Producto producto, int tamanio, String unidad, int numActualEnDespensa, int cantMinima, double ultimoPrecioDeCompra  )
	{
		Presentacion nueva = new Presentacion(cantMinima, numActualEnDespensa, tamanio, ultimoPrecioDeCompra, unidad);
		producto.agregarPresentacion(nueva);
	}

	public void agregarProductoAListaCompras(String nombreLista, Producto producto, Presentacion presentacion, int cantidad)
	{
		ListaCompras listaAgregar = darListaPorNombre(nombreLista);
		if( listaAgregar != null )
		{
			listaAgregar.agregarProductoAListaCompras(producto, presentacion, cantidad);
		}

	}

	public String[ ] buscarProductosVeteranos( )
	{
		ArrayList<Producto> productosVeteranos = new ArrayList<Producto>();
		Date fechaActual = new Date();
		for(int i = 0; i < productos.size(); i++)
		{
			int difDias = Math.round((fechaActual.getTime() - productos.get(i).getFechaUltimaCompra().getTime())/(1000*60*60*24));
			if( difDias >= 14)
				productosVeteranos.add(productos.get(i));
		}
		String[ ] productos = new String[productosVeteranos.size()];
		for( int i = 0; i < productosVeteranos.size(); i++)
		{
			productos[i] = productosVeteranos.get(i).getNombre();
		}
		return productos;		
	}

	public double calcularCostoLista( String nombreLista )
	{
		ListaCompras lista = darListaPorNombre(nombreLista);
		return lista.calcularCostoLista();
	}

	/**
	 * Puede haber muchos productos con un mismo nombre de distintas marcas. El usuario debe poder consultar las
	 * unidades disponibles de cada marca que haya en la despensa para ese producto. 
	 * @return Una cadena de caracteres especificando las unidades disponibles para cada marca y presentación
	 */
	public String cantidadUnidadesDisponiblesProducto(String nombreProducto)
	{
		String res = "";
		for( int i = 0; i < productos.size(); i++ )
		{
			if( productos.get(i).getNombre().equals(nombreProducto) )
				res += productos.get(i).cantidadUnidadesEnDespensa();
		}
		return res;
	}

	public ListaCompras generarListadeCompras( )
	{
		ArrayList<CompraPrevista> itemsCompra = new ArrayList<CompraPrevista>();
		for( int i = 0; i < productos.size(); i++ )
		{
			ArrayList<Presentacion> actual = productos.get(i).darPresentacionesProximasATerminarse();
			if( actual.size() > 0 )
			{
				for(int j = 0; j<actual.size(); j++)
				{
					CompraPrevista compra = new CompraPrevista(productos.get(i), actual.get(j), actual.get(j).getCantMinima()-actual.get(j).getNumActualEnDespensa()+1);
					itemsCompra.add(compra);
				}
			}
		}

		//Registrar la fecha prevista como una semana después de la fecha en que se genera la lista
		Date fechaPrevista = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaPrevista); 
		calendar.add(Calendar.DAY_OF_MONTH, 7);   
		fechaPrevista = calendar.getTime(); 
		
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String fechaActual = df.format(new Date());

		ListaCompras sugerida = new ListaCompras("Lista "+ Integer.toString(listasDeCompras.size()+1) + " generada el " + fechaActual,false, fechaPrevista, itemsCompra); 
		listasDeCompras.add(sugerida);

		return sugerida;
	}

	public void eliminarProductoDeListaDeCompras(Presentacion presentacionAEliminar, String nombreLista)
	{
		ListaCompras lista = darListaPorNombre(nombreLista);
		lista.eliminarPresentacionDeListDeCompras(presentacionAEliminar);
	}

	public String[] darProductos() 
	{
		String[ ] listaProductos = new String[ productos.size() ];
		for( int i = 0; i < productos.size(); i++ )
		{
			listaProductos[i] = productos.get(i).getNombre();
		}
		return listaProductos;
	}
	
	public Producto darProductoPorNombre( String nombreProducto )
	{
		for( int i = 0; i < productos.size(); i++ )
		{
			if( productos.get(i).getNombre().equals(nombreProducto) )
				return productos.get(i);
		}
		return null;
	}
	
	public String[ ] darPresentacionesProducto( Producto p )
	{
		String[ ] presentaciones = new String[ p.getPresentaciones().size() ];
		
		for( int i = 0; i < p.getPresentaciones().size(); i++ )
		{
			Presentacion presentacion = p.getPresentaciones().get(i);
			presentaciones[i] = presentacion.getTamanio() + " " + presentacion.getUnidad();
		}
		return presentaciones;
	}
	
	public Presentacion darPresentacion( Producto p, int tamanio, String unidad )
	{
		return p.darPresentacionPorTamanioYUnidad(tamanio, unidad);
	}

	public String[] darListas() 
	{
		String[ ] listas = new String[listasDeCompras.size()];
		for( int i = 0; i < listasDeCompras.size(); i++ )
		{
			listas[i] = listasDeCompras.get(i).getNombre();
		}
		return listas;
	}

	public String[] darItemsEnLista(ListaCompras l) 
	{
		String[ ] items = new String[l.getCantidades().size()];
		for( int i = 0; i < l.getCantidades().size(); i++ )
		{
			CompraPrevista compra = l.getCantidades().get(i);
			items[ i ] = compra.getProducto().getNombre() + "\n" + "Presentación: " + compra.getPresentacion().getTamanio() + " " + compra.getPresentacion().getUnidad() + "\n" + "Cantidad: " + compra.getCantidad(); 
		}
		return items;
	}

	public void registrarConsumo(Presentacion presentacion,int cantidad)
	{
		presentacion.setNumActualEnDespensa(presentacion.getNumActualEnDespensa()-cantidad);
	}
	



}
