package co.id.fifgroup.ssoa.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SSOACommons {

	public static void deleteFile(String fullPath){
    	File afile = new File(fullPath);

		//delete the original file
		afile.delete();

	}
	
	public static void moveToRealPath(String tmpFullPath, String destinationFullPath)
    {

    	InputStream inStream = null;
	    OutputStream outStream = null;

    	try{

    	    File afile =new File(tmpFullPath);
    	    File bfile =new File(destinationFullPath);

    	    inStream = new FileInputStream(afile);
    	    outStream = new FileOutputStream(bfile);

    	    byte[] buffer = new byte[1024];

    	    int length;
    	    //copy the file content in bytes
    	    while ((length = inStream.read(buffer)) > 0){

    	    	outStream.write(buffer, 0, length);

    	    }

    	    inStream.close();
    	    outStream.close();

    	    //delete the original file
    	    afile.delete();


    	}catch(IOException e){
    	    e.printStackTrace();
    	}
    }

	}