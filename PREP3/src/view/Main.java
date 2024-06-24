package view;

import java.io.IOException;

import controller.AttController;

public class Main {

	public static void main(String[] args) {
		AttController atCont = new AttController();
		String os = System.getProperty("os.name").toLowerCase();
		String url = "https://wikimedia.org/api/rest_v1/metrics/pageviews/per-article/en.wikipedia/all-access/all-agents/Tiger_King/daily/20210901/20210930";
		String filePath = os.contains("win") ? "C:/TEMP/wiki.json" : "C:/tmp/wiki.json";
		
		try {
			atCont.saveData(url, filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		atCont.readData(filePath);
	}

}
