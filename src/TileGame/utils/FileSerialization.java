package TileGame.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

public class FileSerialization implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 8642862488785763487L;
	
//	public static void save(String filename, Serializable Text){
//		// Creates output to file
//		FileOutputStream fos = null;
//		try{
//			
//			fos = new FileOutputStream("res/worlds/" + filename);
//			
//			ObjectOutputStream oos = new ObjectOutputStream(fos);
//			oos.writeObject(Text);
//			oos.flush();
//			oos.close();
//		}catch (IOException e){
//			e.printStackTrace();
//		}
//	}
	

	public static void writeFile(String path, String outPutMessage){

		// Creates output to file
		File outPutFile;
		BufferedWriter outPutWriter;
		try{
			outPutFile = new File(path);
			
			outPutWriter = new BufferedWriter(new FileWriter(outPutFile));
			outPutWriter.write(outPutMessage);
			outPutWriter.close();
		}catch (IOException e){
			e.printStackTrace();
		}
	}
}
