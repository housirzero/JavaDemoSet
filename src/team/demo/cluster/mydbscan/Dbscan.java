package team.demo.cluster.mydbscan;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import team.demo.cluster.kmeans.ClusterPoint;

/**
 * DBSCAN是一种基于密度的聚类算法<br>
 * 它的基本原理就是给定两个参数，ξ和minp<br>
 * 	其中 ξ可以理解为半径，算法将在这个半径内查找样本<br>
 * 	minp是一个以ξ为半径查找到的样本个数n的限制条件，只要n>=minp，查找到的样本点就是核心样本点<br>
 */
public class Dbscan<E> {
	
	public List<ClusterPoint<E>> points = null; // 要聚类的点
	private int classNum; // 最终划分的类别数
	private int iterTimes; // 迭代次数
	private double eps; // 半径
	private int minPts; // 核心点周围所需样本点阈值

	public int getClassNum() {
		return classNum;
	}

	public int getIterTimes() {
		return iterTimes;
	}
	
	/**
	 * @param list : 要聚类的点
	 * @param classNum : 要划分的类别数
	 */
	public Dbscan(List<E> list, double eps, int minPts){
		this.iterTimes = 0;
		this.eps = eps;
		this.minPts = minPts;
		this.points = new ArrayList<ClusterPoint<E>>();
		for(int i = 0; i < list.size(); i++)
			points.add(new ClusterPoint<E>(list.get(i)));
	}
	
	private static List<LngLat> list = new ArrayList<LngLat>();// 存储原始样本点
	private static List<List<LngLat>> resultList = new ArrayList<List<LngLat>>();// 存储最后的聚类结果
	private static List<List<LngLat>> resultList1 = new ArrayList<List<LngLat>>();// 存储最后的聚类结果

	private static void applyDbscan(List<LngLat> list, int eps, int minp) throws IOException {
		for (int index = 0; index < list.size(); ++index) {
			List<LngLat> tmpLst = new ArrayList<LngLat>();
			LngLat p = list.get(index);
			if (p.isClassed())
				continue;
			tmpLst = Utility.isKeyPoint(list, p, eps, minp);
			if (tmpLst != null) {
				resultList.add(tmpLst);
			}
		}
		int length = resultList.size();
		for (int i = 0; i < length; ++i) {
			for (int j = 0; j < length; ++j) {
				if (i != j) {
					if (Utility.mergeList(resultList.get(i), resultList.get(j))) {
						resultList.get(j).clear();
					}
				}
			}
		}
	}
	
	public static List<Region> cluster( List<LngLat> list, int eps, int minp )
	{
		try {
			// 调用DBSCAN的实现算法
			applyDbscan(list, eps, minp);
			Utility.updateClassId(resultList);
			// 构造新的类别字符串并返回
//			Utility.displayClass(list);
//			Utility.display(resultList);
			Utility.saveToFile(resultList);
			return Utility.getRegions(resultList);
//			return Utility.getClassRes(list);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// 获取文本中的样本点集合
	public static List<LngLat> getPointsList() throws IOException {
		List<LngLat> lst = new ArrayList<LngLat>();
		String txtPath = "src\\points.txt";
		BufferedReader br = new BufferedReader(new FileReader(txtPath));
		String str = "";
		while ((str = br.readLine()) != null && str != "") {
			String[] items = str.split(":")[1].split(",");
			lst.add(new LngLat(items[0],items[1]));
		}
		br.close();
		return lst;
	}

	public static void main(String[] args) {
		System.out.println("调用方式：cluster( List<LngLat> lst, int eps, int minp )");
		try {
			cluster( getPointsList(), 300, 10);
//			Utility.display(resultList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
