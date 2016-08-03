package com.classFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class CreateNewFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File f=new File("E:\\TheActual_WORKSPACE\\Java_WorkSpace\\Input_Output_Stream","NEW.txt");
		System.out.println(f.getAbsolutePath());
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("File is exist in the directory? " +f.exists());
		f.delete();
		System.out.println("File "+f.getAbsolutePath()+" was deleted");
		System.out.println("File is exist in the directory? " +f.exists());
		System.out.println("Absolute file is "+f.getAbsoluteFile());
		System.out.println("Absolute path is "+f.getAbsolutePath());
		try {
			System.out.println("Canonical file is "+f.getCanonicalFile());
			System.out.println("Canonical path is "+f.getCanonicalPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Get Free Space "+f.getFreeSpace());
		
		int somedata[]={56,230,123,43,11,37,1};
		 
		try (FileOutputStream out = new FileOutputStream(f)){
			for (int i=0; i<somedata.length;i++){
				out.write(somedata[i]);
			
		}			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		
		System.out.println("Last modified "+f.lastModified());
		
	}

}