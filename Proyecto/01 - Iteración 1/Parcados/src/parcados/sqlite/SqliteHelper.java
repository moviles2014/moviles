package parcados.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqliteHelper extends SQLiteOpenHelper{
	//--------------------------------------------------------------------------------------
	// Constantes
	//--------------------------------------------------------------------------------------

	/**
	 * Columnas de las tablas en común
	 */
	public static final String COLUMN_NOMBRE = "NOMBRE";
	public static final String COLUMN_ZONA_ID = "ID_ZONA";
	
	/**
	 * Nombres de las tablas
	 */
	public static final String TABLE_ZONAS = "ZONAS";
	public static final String TABLE_PARQUEADEROS = "PARQUEADEROS";
	
	/**
	 * Columnas de la tabla de parqueaderos
	 */
	public static final String COLUMN_PARQ_ID = "ID_PARQ";
	public static final String COLUMN_DIRECCION = "DIRECCION";
	public static final String COLUMN_HORARIO = "PARQUEADEROS";
	public static final String COLUMN_CARACTERISTICAS = "CARACTERISTICAS";
	public static final String COLUMN_CUPOS = "CUPOS";
	public static final String COLUMN_PRECIO = "PRECIO";	
	public static final String COLUMN_ULTIMA_ACT = "ULTIMAACT";	

	/**
	 * Nombre y versión de la base de datos
	 */
	private static final String DATABASE_NAME = "parcados.db";
	private static final int DATABASE_VERSION = 1;

	//--------------------------------------------------------------------------------------
	// Constructores
	//--------------------------------------------------------------------------------------
	
	/**
	 * Constructor del helper
	 * @param context el contexto de la aplicación
	 */
	public SqliteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	//--------------------------------------------------------------------------------------
	// Métodos
	//--------------------------------------------------------------------------------------
	
	/**
	 * Crea la base de datos
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table "+ TABLE_PARQUEADEROS + "( "+ COLUMN_PARQ_ID +" integer primary key autoincrement, "+ COLUMN_NOMBRE +" text unique not null, "+ COLUMN_HORARIO +" text not null, "+ COLUMN_CARACTERISTICAS +" text not null, "+ COLUMN_DIRECCION +" text not null, "+ COLUMN_PRECIO +" int, "+ COLUMN_CUPOS +" int, " + COLUMN_ZONA_ID + " integer not null, "+ COLUMN_ULTIMA_ACT  +" text);");
		db.execSQL("create table "+ TABLE_ZONAS + " ( "+ COLUMN_ZONA_ID +" integer primary key autoincrement, "+ COLUMN_NOMBRE +" text unique not null);");
	}

	/**
	 * Upgrade si se requiere sobre la base de datos
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(SqliteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS "+ TABLE_PARQUEADEROS + ";");
		db.execSQL("DROP TABLE IF EXISTS "+ TABLE_ZONAS + ";");
		onCreate(db);
	}

}
