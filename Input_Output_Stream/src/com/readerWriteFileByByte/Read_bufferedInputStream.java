package com.readerWriteFileByByte;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Read_bufferedInputStream {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileInputStream myFile=null;
		BufferedInputStream buffRead=null;
		try{
			myFile = new FileInputStream("e:\\TheActual_WORKSPACE\\Java_WorkSpace\\Input_Output_Stream\\src\\com\\readerFile\\file.txt");
			buffRead=new BufferedInputStream(myFile);
			boolean eof=false;
			while (!eof){
				int byteValue=buffRead.read();
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
					buffRead.close();
					myFile.close();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		}
	}
}

