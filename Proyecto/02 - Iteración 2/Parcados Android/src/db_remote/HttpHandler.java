package db_remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Base64;

public class HttpHandler {


	public static String cypher_query(String query) throws JSONException, ClientProtocolException, IOException {
		InputStream inputStream = null;
		String result = "";
		
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(
					"http://parcados.sb02.stations.graphenedb.com:24789/db/data/cypher");

			String json = "";
			JSONObject jsonObject = new JSONObject();
			jsonObject.accumulate("query", query);
			json = jsonObject.toString();
			StringEntity se = new StringEntity(json);
			httpPost.setEntity(se);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			String authorizationString = "Basic "
					+ Base64.encodeToString(("parcados" + ":"
							+ "swbc6fDb7EvkkUFteYuA").getBytes(),
							Base64.NO_WRAP); // <=== Do not add '\n'
			httpPost.setHeader("Authorization", authorizationString);

			HttpResponse httpResponse = httpclient.execute(httpPost);

			inputStream = httpResponse.getEntity().getContent();
			if (inputStream != null) {
				result = convertInputStreamToString(inputStream);

//				JSONObject obj = new JSONObject(result);
//				System.out.println(obj.getJSONArray("data").getJSONArray(3)
//						.getJSONArray(1).get(0));
			} else
				result = "Did not work!";


		
		return result;
	}

	private static String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;

	}

}