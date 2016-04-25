package team.demo.cluster.mydbscan;

import cn.zhugefubin.maptool.ConverterTool;
import cn.zhugefubin.maptool.Point;

/**
 * 转换相关函数
 * @author DELL
 *
 */
public class Convert {
	
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
	
}
