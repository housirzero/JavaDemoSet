package team.demo.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteFile {

	public static void append(String filePath) {
		try {
			String data = " This content will append to the end of the file";

			File file = new File(filePath);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// true = append file
			FileWriter fileWritter = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fileWritter);
			bw.write(data);
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void write(String filePath) {
		try {
			String content = "This is the content to write into file";
			File file = new File(filePath);

			// if file doesnt exists, then create it
			if (!file.exists())
				file.createNewFile();

			BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
			bw.write(content);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {

	}

}
