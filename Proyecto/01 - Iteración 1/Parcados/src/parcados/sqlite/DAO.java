package parcados.sqlite;

import java.util.ArrayList;

import parcados.mundo.Parqueadero;
import parcados.mundo.Zona;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DAO {

	//--------------------------------------------------------------------------------------
	// Atributos
	//--------------------------------------------------------------------------------------

	/**
	 * Database
	 */
	private SQLiteDatabase db;

	/**
	 * Helper
	 */
	private SqliteHelper dbHelper;

	/**
	 * Columnas de la tabla parqueaderos
	 */
	private String[] allColumnsParq = { SqliteHelper.COLUMN_PARQ_ID, SqliteHelper.COLUMN_NOMBRE, SqliteHelper.COLUMN_HORARIO, SqliteHelper.COLUMN_CARACTERISTICAS, SqliteHelper.COLUMN_DIRECCION, 
			 SqliteHelper.COLUMN_PRECIO, SqliteHelper.COLUMN_CUPOS, SqliteHelper.COLUMN_ZONA_ID, SqliteHelper.COLUMN_ULTIMA_ACT};

	/**
	 * Columnas de la tabla zona
	 */
	private String[] allColumnsZona = { SqliteHelper.COLUMN_ZONA_ID, SqliteHelper.COLUMN_NOMBRE	};

	//--------------------------------------------------------------------------------------
	// Constructores
	//--------------------------------------------------------------------------------------

	/**
	 * Método constructor
	 * @param context contexto de la aplicación
	 */
	public DAO(Context context)
	{
		dbHelper = new SqliteHelper(context);
	}	

	//--------------------------------------------------------------------------------------
	// Métodos
	//--------------------------------------------------------------------------------------

	/**
	 * Abre la conexión
	 * @throws SQLException - si hay problemas al abrir la conexión
	 */
	public void open() throws SQLException {
		db = dbHelper.getWritableDatabase();
	}

	/**
	 * Cierra la conexión
	 */
	public void close() {
		dbHelper.close();
	}

	public void crearParqueadero( Parqueadero parq, Zona zona )
	{
		ContentValues values = new ContentValues();
		values.put(SqliteHelper.COLUMN_NOMBRE, parq.darNombre());
		values.put(SqliteHelper.COLUMN_HORARIO, parq.darHorario());
		values.put(SqliteHelper.COLUMN_CARACTERISTICAS, parq.darCaracteristicas());
		values.put(SqliteHelper.COLUMN_DIRECCION, parq.darDireccion());
		values.put(SqliteHelper.COLUMN_PRECIO, parq.darPrecio());
		values.put(SqliteHelper.COLUMN_CUPOS, parq.darCupos());
		values.put(SqliteHelper.COLUMN_ZONA_ID, darIdZona(zona));
		db.insert(SqliteHelper.TABLE_PARQUEADEROS, null, values);	
	}
	
	public void actualizarParqueadero ( String nombre ,  int precio , int cupos ){
		ContentValues values = new ContentValues();
		values.put(SqliteHelper.COLUMN_PRECIO, precio );
		values.put(SqliteHelper.COLUMN_CUPOS, cupos );
		db.update(SqliteHelper.TABLE_PARQUEADEROS, values, "NOMBRE='"+nombre+"'" , null ) ;
	}
	
	
	
	public int  darPrecioParqueaderoPorNombre ( String nombre ) {
		final Cursor cursor = db.rawQuery("SELECT PRECIO FROM PARQUEADEROS where NOMBRE = '"+nombre+"';", null);
		int precio = -2 ;  
		if (cursor != null) {
		    try {
		        if (cursor.moveToFirst()) {
		            precio = cursor.getInt(0);
		        }
		    } finally {
		        cursor.close();
		    }
		}
		return precio ; 
	}

	public void crearZona( Zona zona )
	{
		ContentValues values = new ContentValues();
		values.put(SqliteHelper.COLUMN_NOMBRE, zona.darNombre());
		db.insert(SqliteHelper.TABLE_ZONAS, null, values);		
	}


	public long darIdZona(Zona zona)
	{
		Cursor cursor = db.query(SqliteHelper.TABLE_ZONAS, allColumnsZona, SqliteHelper.COLUMN_NOMBRE + " = '" + zona.darNombre() + "'", null, null, null, null);
		cursor.moveToFirst();
		long res = cursor.getLong(0);
		cursor.close();
		return res;
	}

	public ArrayList<Parqueadero> getAllParqueaderosZona(long idzona) {
		ArrayList<Parqueadero> parqueaderos = new ArrayList<Parqueadero> ();
		Cursor cursor = db.query(SqliteHelper.TABLE_PARQUEADEROS,
				allColumnsParq, SqliteHelper.COLUMN_ZONA_ID + " = " + idzona, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Parqueadero parq = cursorToParqueadero(cursor);
			parqueaderos.add(parq);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return parqueaderos;
	}



	public ArrayList<Zona> getAllZonas() {
		ArrayList<Zona> zonas = new ArrayList<Zona> ();

		Cursor cursor = db.query(SqliteHelper.TABLE_ZONAS,
				allColumnsZona, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Zona zona = cursorToZona(cursor);
			zona.setParqueaderos(getAllParqueaderosZona(cursor.getLong(0)));
			zonas.add(zona);
			cursor.moveToNext();	
		}
		cursor.close();
		return zonas;
	}

	public Zona cursorToZona(Cursor cursor)
	{
		return (new Zona(cursor.getString(1)));
	}

	public Parqueadero cursorToParqueadero(Cursor cursor)
	{
		return (new Parqueadero(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4) , cursor.getInt(5) , cursor.getInt(6) ));
	}


}
