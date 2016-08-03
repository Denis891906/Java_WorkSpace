package com.readerWriteFileByCharacters;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import com.readerWriteFileByByte.Read_bufferedInputStream;

public class readByCharacter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		StringBuffer buffer=new StringBuffer();
		//FileInputStream file=null;
		try (FileInputStream file=new FileInputStream("e:\\TheActual_WORKSPACE\\Java_WorkSpace\\Input_Output_Stream\\src\\com\\readerWriteFileByByte\\file.txt")){
			//Open file and close automaticaly
			
			
			InputStreamReader inputStreamReader=new InputStreamReader(file,"UTF8"); // To open text file with UTF8 coding
			
			Reader reader=new BufferedReader(inputStreamReader); //For buffered mode
			int ch;
			while ((ch=reader.read())>-1){
				//Add character to the buffer variable
				buffer.append((char)ch);
				
			}
			String result=buffer.toString();
			//print the result
			System.out.println(result);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Couldn't read string file" +e.toString());
		}/*finally{
			if (file!=null){
				
				try {
					file.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}*/
	}

}