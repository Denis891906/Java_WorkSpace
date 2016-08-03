package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ReadingGoogleCom {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String nextLine;
		URL url=null;
		URLConnection urlConn=null;
		
		InputStreamReader inStream=null;
		BufferedReader buff=null;
		
		try{
			url=new URL("http://www.google.com");
			urlConn = url.openConnection();
			
			inStream= new InputStreamReader(urlConn.getInputStream(),"UTF8");
			
			buff=new BufferedReader(inStream);
			
			while(true){
				nextLine=buff.readLine();
				if (nextLine!=null){
					System.out.println(nextLine);
				}else{
					break;
				}
			}
			
		}catch(MalformedURLException e){
			System.out.println("Please chrck the URL:" + e.toString());
			}catch (IOException e1) {
				// TODO: handle exception
				System.out.println("Can't read from Intet:"+e1.toString() );
			}finally {
				if (inStream!=null){
					try{
						inStream.close();
						buff.close();
					}catch(IOException e1){
						e1.printStackTrace();
					}
				}
			}
		
		
	}

}