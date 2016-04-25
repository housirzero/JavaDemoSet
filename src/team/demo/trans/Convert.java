package team.demo.trans;

import java.util.regex.Pattern;

import cn.zhugefubin.maptool.ConverterTool;
import cn.zhugefubin.maptool.Point;

/**
 * 转换相关函数
 * @author DELL
 * 
 * wgs84 --GPS系统直接通过卫星定位获得的坐标.(最基础的坐标.)
 * <br>
 * gcj02 --天朝已安全原因为由,要求在中国使用的地图产品使用的都必须是加密后的坐标.这套加密后的坐标就是gcj02 google的中国地图.高德地图. 他们为中国市场的产品都是用这套坐标.
 * <br>
 * bd09ll 百度又在gcj02的技术上将坐标加密就成了 bd09ll坐标.
 *
 */
public class Convert {
	
	/**
	 * 把秒数转换为时间格式 HH:mm：ss
	 */
	public static String secondsToTime( int seconds )
	{
		int second = seconds % 60;
		int minute = (seconds / 60) % 60;
		int hour = seconds / 3600;
		return String.format("%2d:%02d:%02d", hour, minute, second);
//		return hour + ":" + String.format("%02d", minute) + ":" + String.format("%02d", second);
	}
	
	/**
	 * 判断是否为整数 
	 * @param str 传入的字符串 
	 * @return 是整数返回true,否则返回false 
	 */
	public static boolean isInteger(String str)
	{
		if(str == null || str.length() == 0)
			return false;
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}
	
	/**
	 * 将字符串转换为数字类型的字符串(亦即去掉前缀0)
	 * 如不能转换为数字，则返回字符串本身
	 * @param str
	 * @return
	 */
	public static String praseInt(String str)
	{
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		if( pattern.matcher(str).matches() ) // 可以转换
			return Integer.parseInt(str)+"";
		return str; // 不能转换，返回自身
	}

	/* 示例代码
	 * 实现百度坐标（bd09ll）和谷歌坐标（gcj02）互转
	 * 该方法来自于网上的一个C语言版本，我给改写成Java了
	 *  经测试转换后左边偏差大约在10米之内，应该可以满足基本开发需求
	 */
	
	/**
	 * 百度--谷歌（参数：“百度经纬度”）
	 * @return
	 */
	public static LngLat BD2GG( LngLat lngLat )
	{
		ConverterTool ct = new ConverterTool();
		Point p = ct.BD2GG(lngLat.lng, lngLat.lat); // 转换结果用Point接收
		return new LngLat(p.getLongitude(), p.getLatitude());
	}
	
	/**
	 * 谷歌到百度（参数：“谷歌经纬度”）
	 * @return
	 */
	public static LngLat GG2BD( LngLat lngLat )
	{
		// 实例化ConverterTool类，你也可以自己调用cn.zhugefubin.maptool.Converter里的方法
		ConverterTool ct = new ConverterTool();
		Point p = ct.GG2BD(lngLat.lng, lngLat.lat); // 转换结果用Point接收
		return new LngLat(p.getLongitude(), p.getLatitude());
	}
	
	/**
	 * GPS--谷歌（参数：“GPS经纬度”）
	 * @return
	 */
	public static LngLat GPS2GG( LngLat lngLat )
	{
		ConverterTool ct = new ConverterTool();
		Point p = ct.GPS2GG(lngLat.lng, lngLat.lat); // 转换结果用Point接收
		return new LngLat(p.getLongitude(), p.getLatitude());
	}
	
	//02转百度
	public static final double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
	
	/**
	 * 02转百度
	 */
	public static LngLat GCJ02_BD( LngLat lngLat )
	{
		return bd_encrypt(lngLat.lng, lngLat.lat);
	}
		  
	public static LngLat bd_encrypt(double gg_lon, double gg_lat){
		double[] out = new double[2];
		double x = gg_lon;
		double y = gg_lat;
		double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi); 
		out[0] = z * Math.cos(theta) + 0.0065;  
		out[1] = z * Math.sin(theta) + 0.006; 
		return new LngLat(out[0], out[1]);
	}
	
	public static void main(String[] args){
		LngLat gps = new LngLat(116.172853, 39.923962);
//		System.out.println(bd_encrypt(116.172853, 39.923962));
		System.out.println(GPS2GG(gps));
		// http://api.map.baidu.com/ag/coord/convert?from=0&to=4&x=116.172853&y=39.923962
	}
	
}
