package com.logindemo.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.jdt.internal.compiler.classfmt.MethodInfoWithAnnotations;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.alibaba.fastjson.JSON;
import com.logindemo.controller.FileController;

public class FileDownloader {
	static Logger logger = Logger.getLogger(FileController.class);
	
	/**
	 */
	public static Map<String, List> getFileOrDir(String path){
			try {
				path = URLDecoder.decode(path,"utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}
		logger.info("路径:"+path);
		File fileDirectory = new File(path);
		Map<String, List> listMap = new HashMap<String, List>();
		List<String> fileList = new ArrayList<String>();
		List<String> dirList = new ArrayList<String>();
		if(fileDirectory.listFiles().length > 0){
			for (File temp : fileDirectory.listFiles()) {
				if (temp.isDirectory()) {
					dirList.add(temp.toString());
				}
			}
		}
		
		listMap.put("dir", dirList);
		File file = new File(path);
		File[] aa = file.listFiles();
		for (int i = 0; i < aa.length; i++) {
			if (aa[i].isFile()) {
				fileList.add(aa[i].getName());
			}
		}
		listMap.put("file", fileList);
		logger.info(JSON.toJSONString(listMap));
		return listMap;
	}
	

	public static boolean saveFile(String path, String originalFilename, byte[] bytes) {
		// TODO Auto-generated method stub
		boolean result = true;
		FileOutputStream fops = null;
		try { 
			 File file = new File(path+originalFilename);
			fops = new FileOutputStream(file); 
	        fops.write(bytes, 0, bytes.length); 
	     
	    } catch (Exception e) { 
	    	result = false;
	      e.printStackTrace(); 
	    } finally {
	    	 try {
	    		 if(fops != null){
	    			 fops.flush();
	 				fops.close();  
	    		 }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		return result;
	}
}
