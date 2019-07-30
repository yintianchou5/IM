package org.dzq.im.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileUtils {
	public static void main(String[] args) throws Exception {
		readerMessage();
		writerMessage("ssssssss");
	}
	public static void writerMessage(String str) throws Exception{
		FileOutputStream fos=new FileOutputStream("e:\\111.txt",true);
		OutputStreamWriter osw=new OutputStreamWriter(fos, "GBK");
		BufferedWriter out=new BufferedWriter(osw);
		System.out.println(str);
		out.write(str);
		out.newLine();
		out.flush();
		out.close();
	}
public static String readerMessage() throws Exception{
		FileInputStream fis=new FileInputStream("e:\\111.txt");
		InputStreamReader isr=new InputStreamReader(fis,"GBK");
		BufferedReader in =new BufferedReader(isr);
		
		String str=null;
		while((str=in.readLine())!=null){
			System.out.println(str);
		}
		in.close();
		return str;
	}
}
