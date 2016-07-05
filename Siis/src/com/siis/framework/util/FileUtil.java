package com.casewaresa.framework.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Vector;

public class FileUtil {
	private InputStreamReader inputFile = null;
	private Vector<String> lines = new Vector<String>();
	
	public FileUtil(String file, Charset charset) throws Exception{
		
		if(charset == null)
			charset =  Charset.forName("UTF-8");
		
		URL resource = getClass().getClassLoader().getResource("./" + file);         
		this.inputFile = new InputStreamReader(resource.openStream(), charset);		
	}
	
	public void readFile() throws Exception{
		String line;
		
		BufferedReader reader = new BufferedReader(this.inputFile);
        while((line = reader.readLine())!=null){
        	this.lines.add(line);
        }
	}

	public void setProperty(String property, String value){
		System.out.println("Property : " + property);
		System.out.println("Value    : " + value);
		
		for (int i = 0; i < lines.size(); i++) {
			if(!lines.elementAt(i).startsWith("#") && lines.elementAt(i).startsWith(property))
				lines.add(i, property + "=" + value);
		}		
	}
	
	public String getProperty(String property){
		String value = null;
		
		System.out.println("Property : " + property);
		for (int i = 0; i < lines.size(); i++) {
			if(!lines.elementAt(i).startsWith("#") && lines.elementAt(i).startsWith(property)){
				String line = lines.elementAt(i);
				
				System.out.println("Line [" + i + "]: " + line);
				System.out.println("Line Size: " + line.length());
				
				//value = line.substring(property.length() + 1);
			}
		}		
		
		return value;
	}
	
	public void readLines(){
		for (int i = 0; i < lines.size(); i++) {
			if(!lines.elementAt(i).startsWith("#"))
				System.out.println(lines.elementAt(i));
		}
	}

}