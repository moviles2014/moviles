package parcados.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqliteHelper extends SQLiteOpenHelper{

	public static final String COLUMN_NOMBRE = "NOMBRE";
	public static final String COLUMN_PARQ_ID = "ID_PARQ";
	public static final String COLUMN_ZONA_ID = "ID_ZONA";
	
	public static final String TABLE_ZONAS = "ZONAS";
	public static final String TABLE_PARQUEADEROS = "PARQUEADEROS";
	
	public static final String COLUMN_DIRECCION = "DIRECCION";
	public static final String COLUMN_HORARIO = "PARQUEADEROS";
	public static final String COLUMN_CARACTERISTICAS = "CARACTERISTICAS";
	public static final String COLUMN_CUPOS = "CUPOS";
	public static final String COLUMN_PRECIO = "PRECIO";	
	public static final String COLUMN_ULTIMA_ACT = "ULTIMAACT";	

	private static final String DATABASE_NAME = "parcados.db";
	private static final int DATABASE_VERSION = 1;


	public SqliteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table "+ TABLE_PARQUEADEROS + "( "+ COLUMN_PARQ_ID +" integer primary key autoincrement, "+ COLUMN_NOMBRE +" text unique not null, "+ COLUMN_DIRECCION +" text not null, "+ COLUMN_HORARIO +" text not null, "+ COLUMN_CARACTERISTICAS +" text not null, "+ COLUMN_CUPOS +" int, "+ COLUMN_PRECIO +" int, " + COLUMN_ZONA_ID + " integer not null, "+ COLUMN_ULTIMA_ACT  +" text);");
		db.execSQL("create table "+ TABLE_ZONAS + " ( "+ COLUMN_ZONA_ID +" integer primary key autoincrement, "+ COLUMN_NOMBRE +" text unique not null);");
	}

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
