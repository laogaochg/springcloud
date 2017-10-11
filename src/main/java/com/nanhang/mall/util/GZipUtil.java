package com.nanhang.mall.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public final class GZipUtil {    
    /** * Do a gzip operation. */   
	public static byte[] gzip(byte[] data) throws IOException {    
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipEntry ze = new ZipEntry("servletservice");
		ZipOutputStream zos = new ZipOutputStream(baos);
		zos.putNextEntry(ze);
		zos.write(data, 0, data.length);
		zos.close();
		byte[] zipBytes = baos.toByteArray();
		return zipBytes;  
    } 
    
    public static byte[] unzip(byte[] zipBytes) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(zipBytes);
		ZipInputStream zis = new ZipInputStream(bais);
		zis.getNextEntry();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final int BUFSIZ = 4096;
		byte inbuf[] = new byte[BUFSIZ];
		int n;
        try{
        	while ((n = zis.read(inbuf, 0, BUFSIZ)) != -1) {
        		baos.write(inbuf, 0, n);
        	}
        } catch(Exception ex){   	
        	ex.printStackTrace();
        }

		byte[] data = baos.toByteArray();
		zis.close();
		return data;
	}
    
    public static void main(String[] args) throws IOException {
		//for(int i =0 ;i<1000;i++){
			String a = "<?xml version = '1.0' encoding = 'utf-8'?><page><MESSAGE>校验Token失败</MESSAGE></page>";	
			byte[] abyte = a.getBytes("UTF-8");
			byte[] testbyte1 = null;
			testbyte1 = gzip(abyte);	
			System.out.println(testbyte1);
			String result=new String(testbyte1,"UTF-8");
			System.out.println(result);
			System.out.println(result.getBytes("UTF-8"));
			byte[] testbyte2=unzip(result.getBytes());
			System.out.println(new String(testbyte2,"UTF-8"));
			
			
		//}

	}
} 
