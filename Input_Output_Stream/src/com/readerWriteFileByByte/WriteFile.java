package com.readerWriteFileByByte;

import java.io.FileOutputStream;
import java.io.IOException;

public class WriteFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int somedata[]={56,230,123,43,11,37,1};
		
		
		try (FileOutputStream myFile=new FileOutputStream("myfile")){
			
			for (int i=0; i<somedata.length; i++){
				myFile.write(somedata[i]);
			}
			
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println("Couldn't write to file: "+e.toString());
		}

	}

}