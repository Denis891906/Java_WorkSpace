package com.classFile;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.Path;

public class ListOfFiles {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File f=null;
		int lenght=f.listRoots().length;
		File list[]=f.listRoots();
		for (int i=0; i<f.listRoots().length; i++){
			//Get list of the files in the folder
			System.out.println(list[i]);
						
		}
		File driveC[]=list[3].listFiles();
		for (int j=0; j<driveC.length;j++){
			System.out.println(driveC[j]);
		}
		
		
	}

}