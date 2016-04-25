package team.demo.use;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LittleAlchemist {

	public static void main(String[] args) {
		System.err.println(1/3 == 2/6);
		Set<String> exist = new HashSet<String>();
		List<String> exp = new ArrayList<String>();

		String file = "C:/Users/DELL/Desktop/little alchemist.txt";
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(file+".txt")));

			String line = null;
			List<String> lines = new ArrayList<String>();
			while( (line=br.readLine()) != null )
			{
				line = line.trim();
				if(line.contains("="))
				{
					String[] items = line.split("=");
					for(int i = 1; i < items.length; i++) // algae=water+plant=plant+ocean
						lines.add(items[0] + "=" + items[i]);
				}
				else
					exist.add(line); // "water", "fire", "earth", "air"
			}
			
			int size = lines.size();
			do {
				for(int i = 0; i < lines.size(); i++){
//					String[] items = lines.get(i).split("=");
					line = lines.get(i);
					String[] elements = line.split("=")[1].split("[+]");
					if(exist.contains(elements[0]) && exist.contains(elements[1]))
					{
						exist.add(line.split("=")[0]);
						exp.add(line);
						lines.remove(i);
						i--;
					}
				}
//				System.out.println(exp.size() + "," + lines.size());
			} while (exp.size() < size);
			
			for(int i = 0; i < exp.size(); i++){
				line = exp.get(i);
				bw.write(line + "\r\n");
			}
			bw.close();
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
