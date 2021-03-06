package com;

import java.awt.Transparency;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
//Program will be save object Employee to file denis.ser in the current folder
public class ObjectOutputSerialization {
	public static void main(String args[]){
		Employee emp=new Employee();
		emp.lName="Lebedev";
		emp.fName="Denis";
		emp.salary=1000;
		
	
	
	FileOutputStream fOut=null;
	ObjectOutputStream oOut=null;
	
	//find path to the current directory from lines 19 to 27
	String current=null;
	try {
		current = new java.io.File( "." ).getCanonicalPath();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//System.out.println(current);
	
	try{
		// Open file for write
		fOut= new FileOutputStream(current+"\\denis.ser");
		//Open objectOutputStream for sending file
		oOut=new ObjectOutputStream(fOut);
		// Write object to the stream
		oOut.writeObject(emp);
		
	}catch(IOException e){
		e.printStackTrace();
		
	}

	}
}
