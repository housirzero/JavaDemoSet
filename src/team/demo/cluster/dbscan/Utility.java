package team.demo.cluster.dbscan;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import team.demo.sys.redis.TestRedis;

/**
 * 工具类，为算法的实现服务
 */
public final class Utility {
	// 计算两点之间的距离
	public static double getDistance(LngLat p, LngLat q) {
		return Distance.lngLatDistance(p, q);
	}

	// 检测p点是不是核心点，tmpLst存储核心点的直达点
	public static List<LngLat> isKeyPoint(List<LngLat> lst, LngLat p, int eps,
			int minp) {
		int count = 0;
		List<LngLat> tmpLst = new ArrayList<LngLat>();
		for (Iterator<LngLat> it = lst.iterator(); it.hasNext();) {
			LngLat q = it.next();
			if (getDistance(p, q) <= eps) {
				count += q.getPassFlow();
				if (!tmpLst.contains(q)) {
					tmpLst.add(q);
				}
			}
		}
		if (count >= minp) {
			p.setKey(true);
			return tmpLst;
		}
		return null;
	}

	// 合并两个链表，前提是b中的核心点包含在a中
	public static boolean mergeList(List<LngLat> a, List<LngLat> b) {
		boolean merge = false;
		if (a == null || b == null) {
			return false;
		}
		for (int index = 0; index < b.size(); ++index) {
			LngLat p = b.get(index);
			if (p.isKey() && a.contains(p)) {
				merge = true;
				break;
			}
		}
		if (merge) {
			for (int index = 0; index < b.size(); ++index) {
				if (!a.contains(b.get(index))) {
					a.add(b.get(index));
				}
			}
		}
		return merge;
	}

	// 更新聚类的类别号
	public static void updateClassId(List<List<LngLat>> resultList)
	{
		int index = 1;
		if(resultList.size() == 0)
			System.out.println("resultList.size() == 0");
		for (Iterator<List<LngLat>> it = resultList.iterator(); it.hasNext();) {
			List<LngLat> lst = it.next();
			if (lst.isEmpty()) {
				continue;
			}
			for (Iterator<LngLat> it1 = lst.iterator(); it1.hasNext();) {
				LngLat p = it1.next();
				p.setClassId(index);
			}
			index++;
		}
	}
	

	// 显示聚类的结果
	public static void displayClass(List<LngLat> list) {
		for (Iterator<LngLat> it = list.iterator(); it.hasNext();) {
			LngLat pos = it.next();
			System.out.println(pos.classId + " : " + pos.toString());
		}
	}	
	
	// 返回分类结果
	public static List<Integer> getClassRes(List<LngLat> list) {
		List<Integer> res = new ArrayList<Integer>();
		for (Iterator<LngLat> it = list.iterator(); it.hasNext();) {
			LngLat pos = it.next();
//			System.out.println(pos.classId + " : " + pos.toString());
			res.add(pos.classId);
		}
		return res;
	}
	
	// 显示聚类的结果
	public static void display(List<List<LngLat>> resultList) {
		int index = 1;
		for (Iterator<List<LngLat>> it = resultList.iterator(); it.hasNext();) {
			List<LngLat> lst = it.next();
			if (lst.isEmpty()) {
				continue;
			}
//			System.out.println("-----第" + index + "个聚类-----");
			for (Iterator<LngLat> it1 = lst.iterator(); it1.hasNext();) {
				LngLat p = it1.next();
//				System.out.println(p);
				LngLat baidu = Convert.GG2BD(p);
				System.out.println(String.format("{\"lng\":%f,\"lat\":%f,\"count\":%d},",
						baidu.lng, baidu.lat, p.passFlow));
			}
			index++;
		}
		System.out.println("一共 " + (index-1) + " 类！");
	}
	
	// 保存聚类的结果
	public static void saveToFile(List<List<LngLat>> resultList) {
		int index = 1;
		BufferedWriter bw = null;
		try {
//			bw = new BufferedWriter(new FileWriter(new File("E:\\TransData\\RouteRec\\TripChain\\20150104_clusterResult(19点GPS点).csv")));
			bw = new BufferedWriter(new FileWriter(new File("C:\\Users\\DELL\\Desktop\\ace-v1.2--bs-v3.0.0\\data\\data_8-9")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {

			bw.write("var points =[\r\n");
			for (Iterator<List<LngLat>> it = resultList.iterator(); it.hasNext();) {
				List<LngLat> lst = it.next();
				if (lst.isEmpty()) {
					continue;
				}
				int count = 0;
				for (Iterator<LngLat> it1 = lst.iterator(); it1.hasNext();) {
					LngLat p = it1.next();
					LngLat baidu = Convert.GG2BD(p);
					count += p.passFlow;
					bw.write(String.format("{\"lng\":%f,\"lat\":%f,\"count\":%d},\r\n",
							baidu.lng, baidu.lat, p.passFlow));
				}
//				bw.write(index + " : " + count + "\r\n");
				index++;
			}
//			bw.write("一共 " + (index-1) + " 类！\r\n");
			bw.write("];");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<Region> getRegions(List<List<LngLat>> resultList) {
		if(resultList == null || resultList.size() == 0)
			return null;
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH");
		String time = sdf.format(now);
		TestRedis redis = new TestRedis("10.2.9.42", 6379);
		String allRegions = "regions";
//		//redis.jedis.del(allRegions);
		int classId = 1;
		List<Region> regions = new ArrayList<Region>();
		for (Iterator<List<LngLat>> it = resultList.iterator(); it.hasNext();) {
			List<LngLat> lst = it.next();
			if (lst.isEmpty()) {
				continue;
			}
			double lngs = 0;
			double lats = 0;
			int passFlow = 0;
			double minLng = 180, maxLng = 0;
			double minLat = 90, maxLat = 0;

			String pointsKey = "points_" + time + "_" + classId;
			redis.jedis.del(pointsKey);
			for (Iterator<LngLat> it1 = lst.iterator(); it1.hasNext();) {
				LngLat p = it1.next();
				
				LngLat baidu = Convert.GG2BD(p);
				redis.jedis.rpush(pointsKey, String.format("{'lng':%.6f,'lat':%.6f,'count':%d},",
						baidu.lng, baidu.lat, p.passFlow));
				
				passFlow += p.passFlow;
				lngs += p.lng * p.passFlow;
				lats += p.lat * p.passFlow;

				if(minLng > p.lng)
					minLng = p.lng;
				if(maxLng < p.lng)
					maxLng = p.lng;
				if(minLat > p.lat)
					minLat = p.lat;
				if(maxLat < p.lat)
					maxLat = p.lat;
			}
			double r = Distance.lngLatDistance(minLng, minLat, maxLng, maxLat) / 2;
			Region region = new Region(new Position(lngs/passFlow, lats/passFlow), r, passFlow);
			regions.add(region);
			// 寻找热点区域的描述
			String td_class = ""; // <td> 标签的类属性
			String describe = "";
			int index = getPlaceIndex(region);
			if(index != -1)
			{
				td_class = _class[(int)(Math.random()*4)];
				describe = places[index];
			}
			
			String regionKey = "region_" + time + "_" + classId;
			redis.jedis.rpush(allRegions, regionKey);
			// td_class#lng,lat#describe#passFlow#r#pointsKey#time
			redis.jedis.set(regionKey, String.format("%s#%.6f,%.6f#%s#%d#%.2f#%s#%s", 
					td_class, region.center.lng, region.center.lat, describe, region.passFlow, region.r, pointsKey, time));
			classId++;
		}
		return regions;
	}
	
	static String[] _class = new String[]{"warning", "danger", "active", "success"};
	static String[] places = new String[]{"天通苑", "西二旗", "三元桥", "东直门",
		"六里桥", "复兴门", "西直门", "积水潭",
		"王府井", "西单", "四惠", "国贸"};
	static double[][] placeGPS = new double[][]{
		{116.406899, 40.074076}, {116.300435, 40.051356}, {116.450286, 39.959598}, {116.428558, 39.940909},
		{116.296867, 39.878738}, {116.35047, 39.905909}, {116.349366, 39.939597}, {116.366501, 39.947357},
		{116.405447, 39.906751}, {116.369542, 39.905857}, {116.495698, 39.907498}, {116.455693, 39.907121}
	};
	
	public static int getPlaceIndex(Region region) {
		double minDis = 200; // 超过200米不算
		int index = -1;
		for(int i = 0; i < places.length; i++)
		{
			double dis = Distance.lngLatDistance(region.center.lng, region.center.lat, placeGPS[i][0], placeGPS[i][1]);
			if( dis < minDis )
			{
				minDis = dis;
				index = i;
			}
		}
		return index;
	}
}
