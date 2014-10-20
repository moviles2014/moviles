package Generador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class Generador {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		//public Parqueadero( String _nombre , String _zona, String _horario , String _caracteristicas , String _direccion , int _precio , int _cupos, double _latitud, double _longitud ) {
		//db.execSQL("create table "+ TABLE_PARQUEADEROS + "( "+ COLUMN_PARQ_ID +" integer primary key autoincrement, "+ COLUMN_NOMBRE +" text unique not null, "+ COLUMN_ZONA + " text not null, "+COLUMN_HORARIO +" text not null, "+ COLUMN_CARACTERISTICAS +" text not null, "+ COLUMN_DIRECCION +" text not null, "+ COLUMN_PRECIO +" int, "+ COLUMN_CUPOS +" int, " + COLUMN_EMPRESA_ID + " integer not null, " + COLUMN_LATITUD + " real not null, " + COLUMN_LONGITUD + " real not null, "+ COLUMN_ULTIMA_ACT  +" text);");
		String[] empresas = {"Parqueaquí", "Parqueolito", "City Parking", "El Chuzo", "Parking", "Aparcar", "Tuparkin", "Parqueparranda", "Europark", "Impark", "IPark", "Park N Fly", "Corpaul", "Proinnco", "Central Parking System", "El Mulero"};
		String[] horarios = {"L-V 9:00 a 5:00pm", "L-S 9:00am a 2:00pm", "L-V 7:00am a 8:00pm", "L-D 8:00am a 8:00pm", "L-S 9:00am a 5:00pm", "L-D 8:00am a 6:00pm"};

		PrintWriter empr = new PrintWriter("data/empresas", "UTF-8");
		for (int i = 0; i < empresas.length; i++) {
			empr.println(empresas[i]);
		}
		empr.close();


		PrintWriter writer = new PrintWriter("data/parqueaderos", "UTF-8");



		for (int i = 0; i < 420; i++) {
			String cadena = "";
			String direccion = "";
			if ( Math.random() > 0.5)
				direccion += "Calle " + (int)(Math.random()*220+1) + " No. " + (int)(Math.random()*180+1) + "-" + (int)(Math.random()*90+1);
			else
				direccion += "Carrera " + (int)(Math.random()*180+1)+ " No. " + (int)(Math.random()*220+1) + "-" + (int)(Math.random()*90+1);
			if ( Math.random() > 0.6)
				direccion += " Sur";
			int idempresa = ((int)(Math.random()*empresas.length));
			cadena += empresas[idempresa] + " " + direccion.split("No.")[0].trim() + "," + idempresa + "," + "zona"+","
					+horarios[((int)(Math.random()*horarios.length))] + "," + direccion + ",";
			String caracteristicas = "";
			if(Math.random() > 0.3)
				caracteristicas += "Cubierto";
			else
				caracteristicas += "Descubierto";
			cadena+=caracteristicas+",";

			try {
				JSONObject obj2 = new JSONObject(sendGet(direccion));
				JSONObject location = obj2.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
				JSONObject locality = obj2.getJSONArray("results").getJSONObject(0).getJSONArray("address_components").getJSONObject(2);
				double lat = location.getDouble("lat");
				double lng = location.getDouble("lng");
				cadena += lat + "," + lng;
				String localidad = new String(locality.getString("long_name").getBytes("ISO-8859-1"), "UTF-8");
				cadena = cadena.replace("zona", localidad);
				if(!localidad.equalsIgnoreCase("Bogota") && !localidad.contains("Carrera") && !localidad.contains("Calle"))
					writer.println(cadena);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//				e.printStackTrace();
			}
			System.out.println(cadena);
		}



		writer.close();

	}
	private final static String USER_AGENT = "Mozilla/5.0";	
	// HTTP GET request
	private static String sendGet(String direccion) throws Exception {




		String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + direccion.replaceAll(" ", "+") + "bogota";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		return response.toString();		


	}
}
