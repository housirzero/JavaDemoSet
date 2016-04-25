package team.demo.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * 自定义划分接口类<br>
 * 将BUS/AFC/GPS信息按指定键值划分为不同文件<br>
 * inputPath中不要有无关的文件，若有的话需要在增加一个filter接口<br>
 * 
 * 用法：<br> 
 * 	new DivideByKey(inputPath, outputPath) {<br> 
 * 		@Override<br> 
 * 		public String getKey(String line) {<br> 
 * 			return null;<br> 
 * 		}<br> 
 * 	}.split();<br> 
 * @author housir
 * @since 2015.6.11
 */
public abstract class DivideByKey {

	/**
	 * 输入路径（文件路径或文件夹路径）
	 */
	private String inputPath;
	/**
	 * 输出路径(文件夹路径)
	 */
	private String outputPath;
	
	/**
	 * 处理文件夹时，可能会有多层文件夹的嵌套，此时就需要递归调用splitFloder函数，所以使用这个全局变量
	 */
	private HashMap<String, BufferedWriter> writerMap = new HashMap<String, BufferedWriter>();
	
	
	/**
	 * @param inputPath ： 输入路径（文件路径或文件夹路径）
	 * @param outputPath ： 输出路径(文件夹路径，不包括最后的"\\")
	 */
	public DivideByKey(String inputPath, String outputPath) {
		super();
		this.inputPath = inputPath;
		this.outputPath = outputPath;
	}

	/**
	 * 每一行的数据转为分类键值的规则
	 * 以转换后的数据为分类标准
	 */
	public abstract String getKey(String line);

	public void split()
	{
		if(inputPath == null || outputPath == null)
		{
			System.err.println("inputPath == null || outputPath == null.");
			return ;
		}
		File file = new File(inputPath);
		if(!file.exists())
		{
			System.err.println("路径 " + inputPath + " 不存在.");
			return;
		}

		if(file.isFile())
			splitFile(file);
		else if(file.isDirectory())
			splitFloder(file);
		
		postdeal();
	}
	
	/**
	 * 最后的处理：文件流的flush()/close()
	 * <br>
	 * Map的清空
	 */
	private void postdeal()
	{
		try {
			for (Entry<String, BufferedWriter> entry : writerMap.entrySet())
				entry.getValue().close(); // 关闭前会执行flush()，将缓存写入文件；不加这句可能会导致输出文件内容不完整
		} catch (IOException e) {
			e.printStackTrace();
		}
		writerMap.clear();
	}
	
	/**
	 * 划分单个文件
	 */
	private void splitFile(File file)
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			br.readLine(); // AFC 和 BUS 刚导出来的文件第一行为表头
			String line = null;
			while( (line=br.readLine()) != null )
			{
				String key = getKey(line);
				if(!writerMap.containsKey(key))
				{
					String writeFilePath = outputPath + "\\" + key + ".csv";
					writerMap.put(key,new BufferedWriter(new FileWriter(new File(writeFilePath))));
				}
				BufferedWriter bw = writerMap.get(key);
				bw.write(line + "\r\n");
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 划分文件夹内所有文件
	 * @param file 
	 */
	private void splitFloder(File floder)
	{
		for(File file : floder.listFiles())
		{
			if(file.isFile())
				splitFile(file);
			else if(file.isDirectory())
				splitFloder(file);
		}
	}

}
