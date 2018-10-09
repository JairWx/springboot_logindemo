package com.logindemo.comon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan(basePackages = { "com.logindemo.*" })
@PropertySource("classpath:file.properties")
public class FileProp {
	@Value("${root}")
	private String root;
	
	@Autowired
	private Environment env;
	
	public String getRoot() {
		String mongodbUrl = env.getProperty("root");
		System.out.println(mongodbUrl);
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}
	
	
}
