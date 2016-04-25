package team.demo.use;

import java.io.File;

import team.demo.file.FileOption;

public class Main {

	
	public static void main(String[] args) {
//		String busCardFloder = "I:\\程序及数据\\2014数据（程序BusData生成）\\Mid\\Card";
//		String gpsFloder = "I:\\程序及数据\\2014数据（程序BusData生成）\\Mid\\GPS";

//		File folder = new File("E:/TransData/RouteRec/TripChain");
//		File folder = new File("I:/20150420-0520/TransData/DailyData/BUS");
////		File folder = new File("I:/20150420-0520/TransData/DailyData/AFC");
//		File[] files = folder.listFiles();
//		for( File file : files)
//		{
//			if(file.isFile() && file.getName().endsWith("TripChain3.csv"))
//			{
//				int count = FileOption.getFileLines(file);
//				System.out.println(file.getName() + "," + count);
//			}
//		}
		
//		folder = new File(gpsFloder);
//		files = folder.listFiles();
//		for( File file : files)
//		{
//			if(file.isFile())
//			{
//				int count = FileOption.getFileLines(file);
//				System.out.println(file.getName() + "," + count);
//			}
//		}
		
		String[] items = "a,b,,d,e,f,,df".split(",");
		System.out.println(items.length);

	}

}
