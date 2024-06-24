package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class AttController {

	public AttController() {
		super();
	}
	BufferedWriter bw;
	BufferedReader br;
	
	public void saveData(String urll, String path) throws IOException {
		try {
			URL url = new URL(urll);
			HttpsURLConnection con = (HttpsURLConnection) 
			url.openConnection();
			
			con.setRequestMethod("GET");
			con.setRequestProperty("Accept", "application/json");
			
			if(con.getResponseCode() != 200) {
				throw new RuntimeException("Falha HTTP >>> " + con.getResponseCode());
			}
			
			br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String txt;
			
			while((txt = br.readLine()) != null) {
				sb.append(txt);
			}
			con.disconnect();
			
			bw = new BufferedWriter(new FileWriter(path));
			bw.write(sb.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			br.close();
			bw.close();
		}
		
		System.out.println("Arquivo salvo!");
	}
	
	public void readData(String path) {
		try {
			br = new BufferedReader(new FileReader(path));
			StringBuilder sb = new StringBuilder();
			String txt;
			
			while((txt = br.readLine()) != null) {
				sb.append(txt);
			}
			
			String json = sb.toString();	
			String itemsJson = json.substring(json.indexOf("[")+1, json.lastIndexOf("]"));
			String items[] = itemsJson.split("},");
			
			/*
			for(int i = 0; i < items.length; i++) {
				System.out.println(items[i]);
			}
			 */
			for(String item : items) {
				item = item.replace("{","").replace("}", "");
				String timeStamp = extractValue(item, "timestamp");
				String view = extractValue(item, "views");
				
				timeStamp = timeStamp.substring(0, timeStamp.length()-2);
				
				System.out.println("timestamp: " + timeStamp + " - " + "views: " + view);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private String extractValue(String item, String string) {
		String keyValue = "\"" + string + "\":";
		
		int iniIndex = item.indexOf(keyValue) + keyValue.length();
		int endIndex = item.indexOf(",", iniIndex);
		
		if(endIndex == -1) 
			endIndex = item.length();
		
		return item.substring(iniIndex, endIndex).replace("\"", "");
	}
	
	

}
