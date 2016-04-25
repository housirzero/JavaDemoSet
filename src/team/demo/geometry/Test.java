package team.demo.geometry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

public class Test {
	public static void main(String[] args) {
		Date start = new Date();
		run();
		Date end = new Date();
		System.out
				.println("run time : " + (end.getTime() - start.getTime()) / 1000.0 + " s.");
	}

	public static void run() {
		double[][] point = new double[][]{{3,1}, {2,3}, {4,4}, {1,4}, {3,2}, {0,0}, {2,0}};
		List<LngLat> points = new ArrayList<LngLat>();
		for(int i = 0; i < point.length; i++)
			points.add(new LngLat(point[i][0], point[i][1]));
		ConvexHull<LngLat> test = new ConvexHull<LngLat>() {
			
			@Override
			public double getY(LngLat e) {
				return e.lat;
			}
			
			@Override
			public double getX(LngLat e) {
				return e.lng;
			}
			
			@Override
			public double distance(LngLat e1, LngLat e2) {
				double dx = e1.lng - e2.lng;
				double dy = e1.lat - e2.lat;
				return dx * dx + dy * dy;
			}
		};
		
		Stack<LngLat> S = test.graham(points);
		for(int i = 0; i < S.size(); i++)
			System.out.println(S.get(i));
	}
}

