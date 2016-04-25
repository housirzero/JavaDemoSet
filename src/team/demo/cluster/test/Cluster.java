package team.demo.cluster.test;

import java.util.Date;
import java.util.List;

import team.demo.file.ReadFile;

public class Cluster {
	public static void main(String[] args) {
		Date start = new Date();
		run();
		Date end = new Date();
		System.out
				.println("run time : " + (end.getTime() - start.getTime()) / 1000.0 + " s.");
	}

	public static void run() {
		String busLineFile = "E:/TransData/BaseInformation/BusStation2015v2.csv";
		// 1(老山公交场站-四惠枢纽站),老山公交场站,1,116.226922,39.913583,1,894,
		List<String> busLines = ReadFile.read(busLineFile);
		
//		genLineJson(busLines, "lines" );
		
		String subLineFile = "E:/TransData/BaseInformation/SubwayStation2015.csv";
		// 1号线,苹果园,103,116.172853,39.923962,150995203,897
		List<String> subLines = ReadFile.read(subLineFile);
	}
	
	
}

