package team.demo.cluster.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StatPassVolume {

	public static void main(String[] args) {
		String recordsFile = "E:/TransData/RouteRec/TripChain/20150104_TripChain3.csv";
		String passVolumeFile = recordsFile.replace(".csv", "_volume.csv");
//		statLinePassVolume(recordsFile, passVolumeFile);
	}
	
	
	/**
	 * 统计每条线路的客流量
	 */
	public static Map<String, Integer> statLinePassVolume( String readFile, String saveFile ) 
	{
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			br = new BufferedReader(new FileReader(readFile));
			bw = new BufferedWriter(new FileWriter(readFile.replace(".csv", "_volume.csv")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Map<String, Integer> passVolume = new HashMap<String, Integer>();
		
		try {
			String line = null;
			int onLineIndex = 'G' - 'A';
			int offLineIndex = 'N' - 'A';
			
			// W,P都是先计算次数，最后再算百分比
			while ((line = br.readLine()) != null) {
				String[] items = line.split(",");
			
				String onLine = items[onLineIndex];
				String offLine = items[offLineIndex];
				
				if(!passVolume.containsKey(onLine))
					passVolume.put(onLine, 1);
				else
					passVolume.put(onLine, passVolume.get(onLine)+1);
				
				if(!passVolume.containsKey(offLine))
					passVolume.put(offLine, 1);
				else
					passVolume.put(offLine, passVolume.get(offLine)+1);
			}
			br.close();
			
			for(String key : passVolume.keySet())
			{
				bw.write(key + "," + passVolume.get(key) + "\r\n");
			}
			bw.close();
			return passVolume;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
