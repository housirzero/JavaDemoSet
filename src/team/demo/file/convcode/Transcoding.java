package team.demo.file.convcode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Transcoding {

	private BytesEncodingDetect encode = new BytesEncodingDetect();
	private String projectPath = System.getProperty("user.dir");// 获取项目目录
	private File tempPath = null;// 转码备份区，用来备份，注意：每次使用前会自动清空
	private String suf = null;
	private String newCharset = null;

	public Transcoding() {
		// 针对windows目录，linux请自行修改
		String proPath = projectPath.substring(projectPath.lastIndexOf("\\") + 1);
		String temp_path = "D:/备份/" + proPath;
		tempPath = new File(temp_path);// 转码备份区，可自行修改
		if (!tempPath.exists()) {
			tempPath.mkdirs();
		} else {
			delete(tempPath);// 注意：每次使用前会自动清空
		}
	}

	/**
	 * 把当前项目复制一份到转码备份区
	 */
	private void backUp(File srcPath, File dstPath) {
		if (srcPath.isDirectory()) {
			if (!dstPath.exists()) {
				dstPath.mkdirs();
			}
			String files[] = srcPath.list();
			for (int i = 0; i < files.length; i++) {
				backUp(new File(srcPath, files[i]), new File(dstPath, files[i]));
			}
		} else {
			if (!srcPath.exists()) {
				System.out.println("File or directory does not exist.");
				System.exit(0);
			} else {
				try {
					InputStream in = new FileInputStream(srcPath);
					OutputStream out = new FileOutputStream(dstPath);
					byte[] b = new byte[1024];
					int len = 0;
					while ((len = in.read(b)) != -1) {
						out.write(b, 0, len);
					}
					in.close();
					out.close();
				} catch (Exception e) {
					e.printStackTrace();// 一旦出错，根据信息自己找原因吧
					System.out.println(srcPath.getAbsolutePath() + " " + dstPath.getAbsolutePath());
				}
			}
		}
	}

	/**
	 * 用转码备份区文件转码后覆盖项目
	 */
	private void convert(File srcPath, File dstPath) {
		if (srcPath.isDirectory()) {
			if (!dstPath.exists()) {
				dstPath.mkdirs();
			}
			String files[] = srcPath.list();
			for (int i = 0; i < files.length; i++) {
				convert(new File(srcPath, files[i]), new File(dstPath, files[i]));
			}
		} else {
			if (!srcPath.exists()) {
				System.out.println("File or directory does not exist.");
				System.exit(0);
			} else {
				//只转码指定后缀的文件
				if (!srcPath.getName().toLowerCase().endsWith(suf)) {
					return;
				}
				try {
					// 获取文件编码
					String charset = BytesEncodingDetect.javaname[encode.detectEncoding(srcPath)];
					//编码相同，无需转码
					if (charset.equalsIgnoreCase(newCharset)) {
						return;
					}
					InputStream in = new FileInputStream(srcPath);
					OutputStream out = new FileOutputStream(dstPath, false);
					BufferedReader br = new BufferedReader(new InputStreamReader(in, charset));
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out, newCharset));
					while (br.ready()) {
						bw.append(br.readLine());
						bw.newLine();
					}
					br.close();
					bw.close();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(srcPath.getAbsolutePath() + " " + dstPath.getAbsolutePath());
				}
			}
		}
	}

	/**
	 * 删除目录
	 */
	private void delete(File file) {
		if (file.isDirectory()) {
			File[] list = file.listFiles();
			for (File f : list) {
				delete(f);
			}
			file.delete();
		} else {
			file.delete();
		}
	}

	/**
	 * 对当前项目进行转码，会把当前项目覆盖
	 * suf：规定要转码的文件后缀；charset：规定要转的编码
	 */
	public void encoding(String suf, String charset) {
		this.suf = suf;
		newCharset = charset;
		backUp(new File(projectPath), tempPath);
		convert(tempPath, new File(projectPath));
		System.out.println("encoding successfully!");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Transcoding().encoding("java", "utf-8");

	}

}
