package team.demo.trans;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.zhugefubin.maptool.ConverterTool;
import cn.zhugefubin.maptool.Point;

/**
 * 获取地铁站GPS信息
 * 
 * @author DELL
 * 
 */
public class GetStationGps
{

	/**
	 * 获取GPS google Gps
	 */
	public static LngLat getStationGps(String addr)
	{
		// addr = "北京市1号线苹果园站";
		String url = "http://maps.googleapis.com/maps/api/geocode/json?address=" + addr + "&sensor=true";
		// System.out.println("开始请求"+url);
		JSONObject jsonObject = NetDeal.getJsonObject(url);

		double lat = 0.0;
		double lng = 0.0;
		try
		{
			if (jsonObject == null || !jsonObject.optString("status").equals("OK"))
			{
				System.err.println("Json 获取失败：" + url);
				return null;
			}
			JSONArray results = jsonObject.getJSONArray("results");
			JSONObject result = (JSONObject) results.get(0);
			JSONObject geometry = (JSONObject) result.getJSONObject("geometry");
			JSONObject location = (JSONObject) geometry.getJSONObject("location");
			lng = location.optDouble("lng");
			lat = location.optDouble("lat");
			return new LngLat(lng, lat);

		} catch (JSONException e1)
		{
			System.err.println(url + " 获取失败");
		}
		return null;
	}

	public static void main(String[] args) throws IOException
	{
		// LngLat lngLat = getStationGps("北京市1号线苹果园站");
		// BufferedReader reader = new BufferedReader(new
		// FileReader(FilePath.subwayStation));
		String filePath = "C:\\Users\\DELL\\Desktop\\显示屏位置.txt";
		// BufferedReader reader = new BufferedReader(new FileReader(filePath));
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
		// C:\\Users\\DELL\\Desktop\\显示屏位置.txt
		BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\DELL\\Desktop\\ScreenGPSAfterTrans20141202.txt"));
		String line = null;
		// line = reader.readLine();
		int count = 0;
		ConverterTool ct = new ConverterTool();
		while ((line = reader.readLine()) != null)
		{
			// 1,1,1号线,103,苹果园,,1,1号线,103,苹果园,150995203
			String addr = "北京市" + line;
			LngLat lngLat = getStationGps(addr);

			String writeLine = "";
			double lon = 0, lat = 0;
			if (lngLat != null)
			{
				Point p = ct.GG2BD(lngLat.lng, lngLat.lat);
				lon = p.getLongitude();
				lat = p.getLatitude();
			}
			writeLine = String.format("{\"name\":\"%s\",\"lon\":%f,\"lat\":%f},", line, lon, lat);

			writeLine = new String(writeLine.getBytes("UTF-8"), "UTF-8");
			writer.write(writeLine);
			System.err.println(count + "," + writeLine);
			writer.flush();
		}
		reader.close();
		writer.close();
	}
}
