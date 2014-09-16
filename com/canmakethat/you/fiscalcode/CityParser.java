package com.canmakethat.you.fiscalcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class CityParser {

	private static InputStream INPUT = CityParser.class
			.getResourceAsStream("dbcf.csv");
	private static BufferedReader READER = new BufferedReader(
			new InputStreamReader(INPUT));
	private static List<City> CITY_DB;

	private static List<City> parse() {
		List<City> cities = new ArrayList<>();
		String line;
		try {
			line = READER.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return cities;
		}
		while (line != null) {
			try {
				StringTokenizer tokenizer = new StringTokenizer(line, ";");
				String name = tokenizer.nextToken();
				String province = tokenizer.nextToken();
				String fiscalCode = tokenizer.nextToken();
				City city = new City(name, province, fiscalCode);
				cities.add(city);
				line = READER.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				return cities;
			}
		}
		return cities;
	}

	/**
	* This method returns the list of city that are into the database.
	* @param refresh A boolean value indicating whether or not to parse again the cvs database.
	* @return A List of City.
	*/
	public static List<City> getCityDb(boolean refresh) {
		if (CITY_DB == null || refresh) {
			CITY_DB = parse();
		}
		return CITY_DB;
	}

}