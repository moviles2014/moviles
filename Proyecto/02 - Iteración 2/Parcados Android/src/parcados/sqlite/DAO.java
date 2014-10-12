package parcados.sqlite;

import java.util.ArrayList;

import parcados.mundo.Empresa;
import parcados.mundo.Parqueadero;
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
	private String[] allColumnsParq = { SqliteHelper.COLUMN_PARQ_ID, SqliteHelper.COLUMN_NOMBRE, SqliteHelper.COLUMN_ZONA, SqliteHelper.COLUMN_HORARIO, SqliteHelper.COLUMN_CARACTERISTICAS, SqliteHelper.COLUMN_DIRECCION, 
			SqliteHelper.COLUMN_PRECIO, SqliteHelper.COLUMN_CUPOS,  SqliteHelper.COLUMN_EMPRESA_ID, SqliteHelper.COLUMN_LATITUD, SqliteHelper.COLUMN_LONGITUD, SqliteHelper.COLUMN_ULTIMA_ACT};
	
	/**
	 * Columnas de la tabla zona
	 */
	private String[] allColumnsEmpresa = { SqliteHelper.COLUMN_EMPRESA_ID, SqliteHelper.COLUMN_NOMBRE	};

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

	/**
	 * Crea un parqueadero
	 * @param parq el parqueadero
	 * @param zona la zona del parqueadero
	 */
	public void crearParqueadero( Parqueadero parq, Empresa empresa )
	{
		ContentValues values = new ContentValues();
		values.put(SqliteHelper.COLUMN_NOMBRE, parq.darNombre());	
		values.put(SqliteHelper.COLUMN_ZONA, parq.darZona());
		values.put(SqliteHelper.COLUMN_HORARIO, parq.darHorario());
		values.put(SqliteHelper.COLUMN_CARACTERISTICAS, parq.darCaracteristicas());
		values.put(SqliteHelper.COLUMN_DIRECCION, parq.darDireccion());
		values.put(SqliteHelper.COLUMN_PRECIO, parq.darPrecio());
		values.put(SqliteHelper.COLUMN_CUPOS, parq.darCupos());		
		values.put(SqliteHelper.COLUMN_EMPRESA_ID, darIdEmpresa(empresa));
		values.put(SqliteHelper.COLUMN_LATITUD, parq.darLatitud());
		values.put(SqliteHelper.COLUMN_LONGITUD, parq.darLongitud());
		db.insert(SqliteHelper.TABLE_PARQUEADEROS, null, values);	
	}

	/**
	 * Actualiza precio y cupo de un parqueadero dado su nombre
	 * @param nombre el nombre del parqueadero
	 * @param precio el precio
	 * @param cupos los cupos
	 */
	public void actualizarParqueadero ( String nombre ,  int precio , int cupos ){
		ContentValues values = new ContentValues();
		values.put(SqliteHelper.COLUMN_PRECIO, precio );
		values.put(SqliteHelper.COLUMN_CUPOS, cupos );
		db.update(SqliteHelper.TABLE_PARQUEADEROS, values, "NOMBRE='"+nombre+"'" , null ) ;
	}

	/**
	 * Crea una zona en la base de datos
	 * @param zona la zona
	 */
	public void crearEmpresa( Empresa empresa )
	{
		ContentValues values = new ContentValues();
		values.put(SqliteHelper.COLUMN_NOMBRE, empresa.darNombre());
		db.insert(SqliteHelper.TABLE_EMPRESAS, null, values);		
	}

	/**
	 * Da el id de una zona
	 * @param zona la zona que se busca el id
	 * @return el id de la zona
	 */
	public long darIdEmpresa(Empresa empresa)
	{
		Cursor cursor = db.query(SqliteHelper.TABLE_EMPRESAS, allColumnsEmpresa, SqliteHelper.COLUMN_NOMBRE + " = '" + empresa.darNombre() + "'", null, null, null, null);
		cursor.moveToFirst();
		long res = cursor.getLong(0);
		cursor.close();
		return res;
	}


	/**
	 * Da todos los parqueaderos de una zona
	 * @param idzona la zona que se busca
	 * @return un arraylist con los parqueaderos
	 */
	public ArrayList<Parqueadero> getAllParqueaderosEmpresa(long idempr) {
		ArrayList<Parqueadero> parqueaderos = new ArrayList<Parqueadero> ();
		Cursor cursor = db.query(SqliteHelper.TABLE_PARQUEADEROS,
				allColumnsParq, SqliteHelper.COLUMN_EMPRESA_ID + " = " + idempr, null, null, null, null);
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



	/**
	 * Retorna todas las empresas
	 * @return las empresas
	 */
	public ArrayList<Empresa> getAllEmpresas() {
		ArrayList<Empresa> empresas = new ArrayList<Empresa> ();
		Cursor cursor = db.query(SqliteHelper.TABLE_EMPRESAS,
				allColumnsEmpresa, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Empresa empr = cursorToEmpresa(cursor);
			empr.setParqueaderos(getAllParqueaderosEmpresa(cursor.getLong(0)));
			empresas.add(empr);
			cursor.moveToNext();	
		}
		cursor.close();
		return empresas;
	}

	/**
	 * Convierte un cursor a empresa
	 * @param cursor el cursor de la empresa
	 * @return la empresa
	 */
	public Empresa cursorToEmpresa(Cursor cursor)
	{
		return (new Empresa(cursor.getString(1)));
	}

	/**
	 * Convierte un cursor a parqueadero
	 * @param cursor el cursor del parqueadero
	 * @return el parqueadero
	 */
	public Parqueadero cursorToParqueadero(Cursor cursor)
	{
		return (new Parqueadero(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4) , cursor.getString(5), cursor.getInt(6) , cursor.getInt(7), cursor.getDouble(9), cursor.getDouble(10) ));
	}

	
	/**
	 * Da todos los parqueaderos 
	 * @return un arraylist con los parqueaderos
	 */
	public ArrayList<Parqueadero> getAllParqueaderos() {
		ArrayList<Parqueadero> parqueaderos = new ArrayList<Parqueadero> ();
		Cursor cursor = db.query(SqliteHelper.TABLE_PARQUEADEROS,
				allColumnsParq, null, null, null, null, null);
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

}
