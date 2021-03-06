package com;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputSerialization {
//Program will be open file denis.ser in the current folder and create object Employee from bytes 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 
		ObjectInputStream oIn=null;
		
		
		//Code from lines 15 to 23 will get current folder and get absolute path for file with bytes.
		String current=null;
		String filePath=null;
		try {
			current = new java.io.File( "." ).getCanonicalPath();
			filePath=current+"\\denis.ser";
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try(FileInputStream fIn= new FileInputStream(filePath))
		{
			oIn=new ObjectInputStream(fIn);
			Employee bestEmp=(Employee) oIn.readObject();
			
			System.out.println(bestEmp.fName+"\n"+bestEmp.lName+"\n"+bestEmp.salary);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch (IOException e1) {
			e1.printStackTrace();// TODO: handle exception
		}	
		
		
	}

}
