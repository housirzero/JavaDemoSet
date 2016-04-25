package team.demo.excel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class Excel
{

	public static void main(String[] args)
	{
		jxl.Workbook readwb = null;
		try{
			// 构建Workbook对象, 只读Workbook对象
			// 直接从本地文件创建Workbook
			InputStream instream = new FileInputStream("E:\\TransData\\公交线路站点明细表.xls");
			readwb = Workbook.getWorkbook(instream);

			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
					"E:\\TransData\\公交线路站点明细表.csv")));
			for( int i = 0; i < readwb.getNumberOfSheets(); i++ )
			{
				readSheet(readwb, i, bw);
			}
			bw.close();
			/***************************
			// 利用已经创建的Excel工作薄,创建新的可写入的Excel工作薄
			jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(new File("E:\\TransData\\公交线路站点明细表1.xls"), readwb);
			// 读取第一张工作表
			jxl.write.WritableSheet ws = wwb.getSheet(0);

			// 获得第一个单元格对象
			jxl.write.WritableCell wc = ws.getWritableCell(0, 0);

			// 判断单元格的类型, 做出相应的转化
			if (wc.getType() == CellType.LABEL)
			{
				Label l = (Label) wc;
				l.setString("新姓名");
			}

			// 写入Excel对象
			wwb.write();
			wwb.close();
			***************************************/
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			readwb.close();
		}
	}

	/**
	 * 读取第 index 张 sheet 表
	 * @param bw 
	 */
	public static void readSheet(jxl.Workbook readwb, int index, BufferedWriter bw) {
		// Sheet的下标是从0开始
		// 获取第一张Sheet表
		Sheet readsheet = readwb.getSheet(index);
		// 获取Sheet表中所包含的总列数
		int rsColumns = readsheet.getColumns();

		// 获取Sheet表中所包含的总行数
		int rsRows = readsheet.getRows();
		
		int iBegin = 0;
		if(readsheet.getCell(0,0).getContents().equals("线路"))
		{
			System.out.println("1," + readsheet.getName());
			iBegin = 1;
		}
		else
		{
			System.err.println("0," + readsheet.getName());
			return;
		}

		String line = "";
		String realLine = "";
		boolean over = false;
		
		String writeLine = "";
		// 获取指定单元格的对象引用
		for (int i = iBegin; i < rsRows; i++)
		{
			over = false;
			line = readsheet.getCell(0, i).getContents();
			if(line.trim().length() > 0)
			{
				realLine = line.trim();
				realLine = realLine.replace("（", "(");
				realLine = realLine.replace("）", ")");
				realLine = realLine.replace("至", "-");
				String[] items = realLine.split("[(]");
				if(items.length < 2){
					System.out.println(realLine);
				}
				else {
					realLine = items[0].replace("路", "") + "(" + items[1];
				}
			}
			writeLine = realLine;
			for (int j = 1; j < rsColumns; j++)
			{
				Cell cell = readsheet.getCell(j, i);
				if(cell.getContents().trim().length() == 0) // 之后都是空行
				{
					over = true;
					break;
				}
				writeLine += "," + cell.getContents();
			}
			if(over)
				break;
			if(!writeLine.equals(realLine))
				try {
					bw.write(writeLine + "\r\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	
}
