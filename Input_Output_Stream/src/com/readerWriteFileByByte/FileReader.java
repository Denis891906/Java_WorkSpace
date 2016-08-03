package com.readerWriteFileByByte;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileReader {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileInputStream myFile=null;
		try{
			myFile = new FileInputStream("e:\\TheActual_WORKSPACE\\Java_WorkSpace\\Input_Output_Stream\\src\\com\\readerWriteFileByByte\\file.txt");
			boolean eof=false;
			while (!eof){
				int byteValue=myFile.read();
				System.out.println(byteValue);
				if (byteValue==-1){
					eof=true;
									}
			}
			
		}catch(IOException e){
			System.out.println("Couldn't read a file:"+e.toString());
			
			
		}finally{
			if(myFile!=null){
				try {
					myFile.close();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		}
		
	}

}