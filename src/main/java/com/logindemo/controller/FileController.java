package com.logindemo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.channels.ScatteringByteChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.logindemo.comon.FileProp;
import com.logindemo.comon.PropertyConfigerator;
import com.logindemo.utils.FileDownloader;


@Controller
@RequestMapping(value = "/file")
public class FileController {
	 protected static final Logger logger = LoggerFactory.getLogger(FileController.class);
	//private static String root = "D:\\\\";
	private static String separator = java.io.File.separator;
	private static String separator2 = File.separator;
	private static String separator3 = System.getProperty("file.separator");
	@RequestMapping(value = "/index")
    public String index(ModelMap modelMap) {
		return "file";
    }
	
	@RequestMapping(value = "/list/**")
	@ResponseBody
    public Map<String, Object> list3(HttpServletRequest request) {
		String root = new PropertyConfigerator().doConfigure().getProperty("root");
		//String root = new FileProp().getRoot();
		logger.info(root);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String requestURL = request.getRequestURL().toString();
		logger.info(requestURL);
		List<Map> result = new ArrayList<Map>();
		int i = requestURL.lastIndexOf("/list/");
		 String relativePath = "";
		 logger.info(String.valueOf(requestURL.split("/list/").length));
		 String relativeURL = "";
		if(requestURL.split("/list/").length > 1){
			relativePath = requestURL.split("/list/")[1];
			 logger.info("相对URL"+relativePath);
		    //判断是否返回上一级
		    String[] moduleNameList = relativePath.split("/");
		   logger.info(JSON.toJSONString(moduleNameList));
		    if(relativePath.indexOf("$$") > -1){
		    	logger.info("sss");
		    	String[] moduleNameList1 = new String[moduleNameList.length -2];
		    	System.arraycopy(moduleNameList, 0, moduleNameList1,0, moduleNameList.length -2);
		    	moduleNameList = moduleNameList1;
		    }
		   
		    logger.info("截取的URL方法:"+JSON.toJSONString(moduleNameList));
		    relativePath = StringUtils.join(moduleNameList, separator);
		    relativeURL = StringUtils.join(moduleNameList, "/")+"/";
		}
		//针对linux 
		if()
		String path = root+relativePath;//路径
		Map<String, List> map = FileDownloader.getFileOrDir(path);
		List<String> dirList = map.get("dir");
		Map beforeMap = new HashMap();
		beforeMap.put("name","$$");
		beforeMap.put("isDir",true);
		result.add(beforeMap);
		for (String str : dirList) {
			//进行路径切割
			String[] sList = str.split("\\\\");//windows            
	            if(sList.length == 1){sList = str.split(separator);}//linux
			Map sMap = new HashMap();
			sMap.put("name",sList[sList.length- 1]);
			sMap.put("isDir",true);
			result.add(sMap);
		}
		List<String> fileList = map.get("file");
		for (String str : fileList) {
				Map sMap = new HashMap();
				sMap.put("name",str);
				sMap.put("isDir",false);
				result.add(sMap);
		}
		resultMap.put("list", result);
		resultMap.put("path", relativeURL);
		return resultMap;
    }
	
	
	@RequestMapping(value = "/upload",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
    public String upload(ModelMap modelMap,@RequestParam("file") MultipartFile file,@RequestParam("path")String path) {
		try {
			String root = new PropertyConfigerator().doConfigure().getProperty("root");
			FileDownloader.saveFile(root+separator+path, file.getOriginalFilename(), file.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(file.getContentType());
		System.out.println(file.getOriginalFilename());
		return "file";
    }
}
