package com.classFile;

import java.io.File;
import java.io.IOException;

public class FileRead {
	public static void main(String[] args) throws IOException {
		File file= new File("E:\\","javaFIle1"); //TO create JavaFile in the E:\ directory
		File file1= new File("E:\\","javafIle2"); //TO create JavaFile in the E:\ directory
		file.createNewFile();
		System.out.println(file.canExecute());
		System.out.println(file.canRead());
		System.out.println(file.canWrite());
		System.out.println(file.compareTo(file1));
		File f=null;
		//f.createNewFile(); // to create file
		f = File.createTempFile("ttttt", ".lll",new File("E:\\TheActual_WORKSPACE\\Java_WorkSpace\\Input_Output_Stream")); //to create temp file
		System.out.println(f.getAbsolutePath());
		
		
		
		
	}

}
