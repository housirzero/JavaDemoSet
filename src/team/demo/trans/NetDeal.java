package team.demo.trans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.client.methods.HttpPost;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("deprecation")
public class NetDeal {

	/**
	 * 解析Json
	 */
	public static void analyzeJson(final String url) {
		if (url == null || url.length() <= 0)
			return;
		HttpClient client = new DefaultHttpClient();
		StringBuilder builder = new StringBuilder();
		JSONArray jsonArray = null;
		JSONObject json = null;
		HttpGet post = new HttpGet(url);
		// HttpPost post = new HttpPost(url);
		HttpResponse response = null;
		try {
			response = client.execute(post);
		} catch (ClientProtocolException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
			return;
		}

		if (response.getStatusLine().getStatusCode() == 200) // 响应成功
		{
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(response.getEntity().getContent()));
				String s = reader.readLine();
				for (; s != null; s = reader.readLine()) {
					builder.append(s);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			String JsonStr = builder.toString();
			if (JsonStr.length() <= 0) // 沒有获取Json数据
				return;

			try {
				json = new JSONObject(JsonStr);
				jsonArray = json.getJSONArray("data");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonData = (JSONObject) jsonArray.get(i);
					String value = json.optString("key");
					// Add Your Codes
				}

			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
		return;
	}
	
	/**
	 * 获取JSONObject
	 * @param url
	 * @return
	 */
	public static JSONObject getJsonObject(String url)
	{
		if (url == null || url.length() <= 0)
			return null;
		HttpClient client = new DefaultHttpClient();
		StringBuilder builder = new StringBuilder();
		JSONObject jsonObject = null;
		HttpGet post = new HttpGet(url);
		// HttpPost post = new HttpPost(url);
		HttpResponse response = null;
		try {
			response = client.execute(post);
		} catch (ClientProtocolException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
			return null;
		}

		if (response.getStatusLine().getStatusCode() == 200) // 响应成功
		{
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(response.getEntity().getContent()));
				String s = reader.readLine();
				for (; s != null; s = reader.readLine()) {
					builder.append(s);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			String JsonStr = builder.toString();
			if (JsonStr.length() <= 0) // 沒有获取Json数据
				return null;
			
			try {
				jsonObject = new JSONObject(JsonStr);
			} catch (JSONException e) {
				return null;
			}
			return jsonObject;
		}
		return null;
	}

}
