package org.tain.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Deprecated
public class FileInfo {

	private String filePath;
	
	public FileInfo(String filePath) {
		this.filePath = filePath;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(this.filePath));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) try { br.close(); } catch (Exception e) {}
		}
		return sb.toString();
	}
}
