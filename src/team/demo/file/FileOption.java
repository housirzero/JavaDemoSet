package team.demo.file;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileOption {

	/**
	 * 创建filePath需要的文件夹
	 */
	public static void mkDir( String filePath )
	{
		File file = new File(filePath);
		File parentFolder = file.getParentFile();
		if(!parentFolder.exists())
			parentFolder.mkdirs();
	}

	/**
	 * 计算一个文件的行数
	 * @param filePath
	 */
	public static int getFileLines(String filePath)
	{
		int lines = 0;
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
			while (reader.readLine() != null)
				++lines;
//			System.out.println("文件" + filePath+ "行数：" + lines);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return lines;
	}
	
	/**
	 * 计算一个文件的行数
	 * @param File file
	 */
	public static int getFileLines(File file)
	{
		int lines = 0;
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			while (reader.readLine() != null)
				++lines;
//			System.out.println("文件" + filePath+ "行数：" + lines);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return lines;
	}
	
	   /**
     * 判断文件的编码格式
     * 
     * @param fileName
     *            :file
     * @return 文件编码格式
     */
    public static String codeString(String fileName) throws Exception {
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
                fileName));
        int p = (bin.read() << 8) + bin.read();
        String code = null;
 
        switch (p) {
        case 0xefbb:
            code = "UTF-8";
            break;
        case 0xfffe:
            code = "Unicode";
            break;
        case 0xfeff:
            code = "UTF-16BE";
            break;
        default:
            code = "GBK";
        }
 
        return code;
    }
    
    /**
     * 从resdFilePath中读取n行并写入saveFilePath文件中
     * 主要用于看大型的文件（不能用一般的文本编辑器打开）
     * @param resdFilePath
     * @param saveFilePath
     * @param n
     */
    public static void readNLines( String resdFilePath, String saveFilePath, int n  )
    {
    	int lineCount = 0;
		String line;
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(resdFilePath), "GBK"));
			BufferedWriter writer = new BufferedWriter(new FileWriter(saveFilePath));
			while ( ++lineCount < n && ( line = reader.readLine() ) != null)
			{
				writer.write(line+"\r\n");
//				System.out.println(line);
//				System.out.println(lineCount);
			}
			System.out.println("写入文件" + resdFilePath+ "行数：" + lineCount);
			reader.close();
			writer.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
    }
    
    public static void main(String[] args)
    {
//    	readNLines("I:\\20150420-0520\\一卡通\\BUS_2015.04.20-0520.csv", "I:\\20150420-0520\\一卡通\\BUS_Sample.csv", 50);
    	readNLines("L:/ORACLE/DATA", "L:/ORACLE/DATA_sample.csv", 50);
    }
    
}
