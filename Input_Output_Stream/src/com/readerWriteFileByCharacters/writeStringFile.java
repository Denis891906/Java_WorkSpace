package com.readerWriteFileByCharacters;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class writeStringFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String text="sudo sh -c echo  ";
		
		//FileOutputStream file=null;
		OutputStreamWriter out=null;
		//Writer out; //For buffer
		
		try(FileOutputStream file=new FileOutputStream("otput.txt")){
			//Open file and close automaticaly
			out=new OutputStreamWriter(file, "UTF8");
			//Writer out=new BufferedWriter(new OutputStreamWriter(file,"UTF8")); //write with buffer
			out.write(text);
			
			
			
		}catch(IOException e){
			System.out.print(e.toString());
			
		}/*finally{
			if (file!=null){
				try {
					out.close();
					file.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/	
			}
	
	

	}


