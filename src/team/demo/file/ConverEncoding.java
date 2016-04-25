package team.demo.file;
 
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
/**
 * UTF-8：Unicode TransformationFormat-8bit，允许含BOM，但通常不含BOM。
 * 是用以解决国际上字符的一种多字节编码，它对英文使用8位（即一个字节），中文使用24为（三个字节）来编码。
 * UTF-8包含全世界所有国家需要用到的字符，是国际编码，通用性强。
 * UTF-8编码的文字可以在各国支持UTF8字符集的浏览器上显示。如，如果是UTF8编码，则在外国人的英文IE上也能显示中文，他们无需下载IE的中文语言支持包。
 * <br><br>
 * GBK是国家标准GB2312基础上扩容后兼容GB2312的标准。
 * GBK的文字编码是用双字节来表示的，即不论中、英文字符均使用双字节来表示，为了区分中文，将其最高位都设定成1。
 * GBK包含全部中文字符，是国家编码，通用性比UTF8差，不过UTF8占用的数据库比GBK大。
 * <br><br>
 * GBK、GB2312等与UTF8之间都必须通过Unicode编码才能相互转换：
 * GBK、GB2312－－Unicode－－UTF8
 * UTF8－－Unicode－－GBK、GB2312
 */
public class ConverEncoding {
     
    static String CODE = "UTF-8";
    static String FILE_SUFFIX = ".csv";//文件扩展名
//  static String FILE_SUFFIX = ".css";
//  static String FILE_SUFFIX = ".js";
//  static String FILE_SUFFIX = ".htm";
    static String srcDir = "E:\\TransData\\RouteRec\\TripChain";//文件所在目录
    /**
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        File floder = new File(srcDir);
        String filecode = "";
        for (File file : floder.listFiles()) {
        	if(file.isDirectory())
        		continue;
            filecode = codeString(file.getAbsolutePath());
            if (!filecode.equals(CODE)) {
                convert(file.getAbsolutePath(), filecode, file.getAbsolutePath().replace("\\TripChain", "\\NewTripChain"), CODE);
            }
        }
    }
 
    public static void convert(String oldFile, String oldCharset,
            String newFlie, String newCharset) {
        BufferedReader reader;
        File dir = new File(newFlie.substring(0, newFlie.lastIndexOf("\\")));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            System.out.println("the old file is :"+oldFile);
            System.out.println("The oldCharset is : "+oldCharset);
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(
                    oldFile), oldCharset));
            String line = null;
            Writer out = new OutputStreamWriter(new FileOutputStream(newFlie), newCharset);
			int count = 0;
            while ((line = reader.readLine()) != null) {
            	out.write(line + "\r\n");
            	if(++count % 100000 == 0)
            		System.out.println(count/10000 + "W");
            }
            reader.close();
            out.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    public static void fetchFileList(String strPath, List<String> filelist,
            final String regex) {
        File dir = new File(strPath);
        File[] files = dir.listFiles();
        Pattern p = Pattern.compile(regex);
        if (files == null)
            return;
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                fetchFileList(files[i].getAbsolutePath(), filelist, regex);
            } else {
                String strFileName = files[i].getAbsolutePath().toLowerCase();
                Matcher m = p.matcher(strFileName);
                if (m.find()) {
                    filelist.add(strFileName);
                }
            }
        }
    }
 
    /**
     * 判断文件的编码格式
     * 
     * @param fileName
     *            :file
     * @return 文件编码格式
     * @throws Exception
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
}
