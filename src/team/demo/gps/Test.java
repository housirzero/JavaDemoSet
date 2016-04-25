package team.demo.gps;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import team.demo.cluster.dbscan.Dbscan;
import team.demo.file.ReadFile;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		String busLineFile = "E:/TransData/BaseInformation/BusStation2015v2.csv";
//		// 1(老山公交场站-四惠枢纽站),老山公交场站,1,116.226922,39.913583,1,894,
//		List<String> busLines = ReadFile.read(busLineFile);
//		
////		genLineJson(busLines, "lines" );
//		
//		
//		String subLineFile = "E:/TransData/BaseInformation/SubwayStation2015.csv";
//		// 1号线,苹果园,103,116.172853,39.923962,150995203,897
//		List<String> subLines = ReadFile.read(subLineFile);
//		
////		busLines.addAll(subLines);
////		genLineJson(busLines, "lines" );
//		genLineJson(ReadFile.read("E:/TransData/BaseInformation/RoadPoints/road_points.csv"), "lines", "C:/Users/DELL/Desktop/road.json");
		genLineJson(ReadFile.read("E:/TransData/BaseInformation/RoadPoints/poliline_points.csv"), "lines", "C:/Users/DELL/Desktop/poliline.json");

	}

	public static void genLineJson(List<String> busLines, String jsonVarName, String outfile) {
		
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(outfile));
		
			String lineName = "";
//			System.out.print("var " + jsonVarName + " = [\r\n[");
			bw.write("var " + jsonVarName + " = [\r\n[");
			lineName = busLines.get(0).split(",")[0].split("[(]")[0];
			for(int i = 1; i < busLines.size(); i++){
				// 1(老山公交场站-四惠枢纽站),老山公交场站,1,116.226922,39.913583,1,894,
				String[] items = busLines.get(i).split(",");
				
				if(!lineName.equals(items[0].split("[(]")[0]))
				{
	//				System.out.print("],\r\n[");
					bw.write("],\r\n[");
					lineName = items[0].split("[(]")[0];
				}
				double lat = Double.parseDouble(items[4]);
				double lon = Double.parseDouble(items[3]);
//				Gps bd = GPSConveter.wgs84_To_Bd09(lat, lon);
				Gps bd = GPSConveter.gcj02_To_Bd09(lat, lon);
//				Gps bd = new Gps(lat, lon);
	//			System.out.print(String.format("[%.6f, %.6f],", bd.getWgLon(), bd.getWgLat()));
				bw.write(String.format("[%.6f, %.6f],", bd.getWgLon(), bd.getWgLat()));
			}

//			System.out.print("]\r\n];\r\n");
			bw.write("]\r\n];\r\n");
			
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
