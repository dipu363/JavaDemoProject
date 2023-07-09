package sa.com.medisys;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadDirectory {

	public String getDirectory() {

		String value = null;
		File file = new File("C:/source/absolutepath.txt");
		
		try {
			@SuppressWarnings("resource")
			Scanner scnner = new Scanner(file);
			while (scnner.hasNext()) {
				value = scnner.next();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//System.out.println(value);
		return value;
	}

}
