package com.canmakethat.you.fiscalcode;

import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FiscalCodeCalculator {
	

	/**
	* This method calculates an Italian fiscal code starting from the initial and necessary parameters.
	* If the string you pass as a place of birth does not correspond to any valid city or country an exception is thrown.
	* @param name The name of the person you want to calculate the fiscal code.
	* @param surname The surname of the person you want to calculate the fiscal code.
	* @param sex A char indicating the sex of the person you want to calculate the fiscal code.
	* @param birth A Date object indicating the date of birth of the person you want to calculate the fiscal code.
	* @param placeOfBirth A String object indicating the place of birth of the person you want to calculate the fiscal code.
	* @return A String object representing the fiscal code calculated.
	*/
	public String calculateFC(String name, String surname, char sex,
			Date birth, String placeOfBirth) throws NotSuchCityException {
		List<City> cities = CityParser.getCityDb(false);
		int index = 0;
		for (City city : cities) {
			if (city.getName().equalsIgnoreCase(placeOfBirth))
				break;
			index++;
		}
		if (index == cities.size()) {
			throw new NotSuchCityException();
		} else {
			City city = cities.get(index);
			return calculateFC(name, surname, sex, birth, city);
		}
	}

	/**
	* This method actually calculates an Italian fiscal code starting from the initial and necessary parameters.
	* @param name The name of the person you want to calculate the fiscal code.
	* @param surname The surname of the person you want to calculate the fiscal code.
	* @param sex A char indicating the sex of the person you want to calculate the fiscal code.
	* @param birth A Date object indicating the date of birth of the person you want to calculate the fiscal code.
	* @param placeOfBirth A City object indicating the place of birth of the person you want to calculate the fiscal code.
	* @return A String object representing the fiscal code calculated.
	*/
	public String calculateFC(String name, String surname, char sex,
			Date birth, City placeOfBirth) {
		String fiscalCode = "";
		String fcSurname = surname.replace(" ", "").toUpperCase();
		String fcName = name.replace(" ", "").toUpperCase();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String fcBirthDate = sdf.format(birth);

		String consonants = consonants(fcSurname);
		String vowels = vowels(fcSurname);
		int consonantsLength = consonants.length();
		switch (consonantsLength) {
		case 0:
			if (vowels.length() > 2)
				fiscalCode += vowels.substring(0, 3);
			else if (vowels.length() == 2)
				fiscalCode += vowels + "x";
			else if (vowels.length() == 1)
				fiscalCode += vowels + "xx";
			else
				fiscalCode += "xxx";
			break;
		case 1:
			if (vowels.length() == 1)
				fiscalCode += consonants + vowels + "x";
			else
				fiscalCode += consonants + vowels.substring(0, 2);
			break;
		case 2:
			if (vowels.length() > 0)
				fiscalCode += consonants + vowels.substring(0, 1);
			else
				fiscalCode += consonants + "x";
			break;
		default:
			fiscalCode += consonants.substring(0, 3);
			break;
		}

		// NAME
		consonants = consonants(fcName);
		vowels = vowels(fcName);
		consonantsLength = consonants.length();
		switch (consonantsLength) {
		case 0:
			if (vowels.length() > 2)
				fiscalCode += vowels.substring(0, 3);
			else if (vowels.length() == 2)
				fiscalCode += vowels + "x";
			else if (vowels.length() == 1)
				fiscalCode += vowels + "xx";
			else
				fiscalCode += "xxx";
			break;
		case 1:
			if (vowels.length() == 1)
				fiscalCode += consonants + vowels + "x";
			else
				fiscalCode += consonants + vowels.substring(0, 2);
			break;
		case 2:
			if (vowels.length() > 0)
				fiscalCode += consonants + vowels.substring(0, 1);
			else
				fiscalCode += consonants + "x";
			break;
		case 3:
			fiscalCode += consonants;
			break;
		default:
			fiscalCode += consonants.substring(0, 1) + consonants.substring(2, 4);
			break;
		}

		
		/* Year */
		fiscalCode += fcBirthDate.substring(8, 10);
		/* Month */
		int month = 0;
		if (fcBirthDate.charAt(3) == '0')
			month = Integer.parseInt(fcBirthDate.substring(4, 5));
		else
			month = Integer.parseInt(fcBirthDate.substring(3, 5));
		switch (month) {
		case 1: {
			fiscalCode += "A";
			break;
		}
		case 2: {
			fiscalCode += "B";
			break;
		}
		case 3: {
			fiscalCode += "C";
			break;
		}
		case 4: {
			fiscalCode += "D";
			break;
		}
		case 5: {
			fiscalCode += "E";
			break;
		}
		case 6: {
			fiscalCode += "H";
			break;
		}
		case 7: {
			fiscalCode += "L";
			break;
		}
		case 8: {
			fiscalCode += "M";
			break;
		}
		case 9: {
			fiscalCode += "P";
			break;
		}
		case 10: {
			fiscalCode += "R";
			break;
		}
		case 11: {
			fiscalCode += "S";
			break;
		}
		case 12: {
			fiscalCode += "T";
			break;
		}
		}
		/* day */
		int day = 0;
		if (fcBirthDate.charAt(0) == '0')
			day = Integer.parseInt(fcBirthDate.substring(0, 1));
		else
			day = Integer.parseInt(fcBirthDate.substring(0, 2));
		if (sex == 'M')
			fiscalCode += day;
		else {
			day += 40;
			fiscalCode += Integer.toString(day);
		}
		/* birth city */
		fiscalCode += placeOfBirth.getCode();

		/* Control char */
		fiscalCode = fiscalCode.toUpperCase();
		int evenSum = 0;
		for (int i = 1; i <= 13; i += 2) {
			switch (fiscalCode.charAt(i)) {
			case '0': {
				evenSum += 0;
				break;
			}
			case '1': {
				evenSum += 1;
				break;
			}
			case '2': {
				evenSum += 2;
				break;
			}
			case '3': {
				evenSum += 3;
				break;
			}
			case '4': {
				evenSum += 4;
				break;
			}
			case '5': {
				evenSum += 5;
				break;
			}
			case '6': {
				evenSum += 6;
				break;
			}
			case '7': {
				evenSum += 7;
				break;
			}
			case '8': {
				evenSum += 8;
				break;
			}
			case '9': {
				evenSum += 9;
				break;
			}
			case 'A': {
				evenSum += 0;
				break;
			}
			case 'B': {
				evenSum += 1;
				break;
			}
			case 'C': {
				evenSum += 2;
				break;
			}
			case 'D': {
				evenSum += 3;
				break;
			}
			case 'E': {
				evenSum += 4;
				break;
			}
			case 'F': {
				evenSum += 5;
				break;
			}
			case 'G': {
				evenSum += 6;
				break;
			}
			case 'H': {
				evenSum += 7;
				break;
			}
			case 'I': {
				evenSum += 8;
				break;
			}
			case 'J': {
				evenSum += 9;
				break;
			}
			case 'K': {
				evenSum += 10;
				break;
			}
			case 'L': {
				evenSum += 11;
				break;
			}
			case 'M': {
				evenSum += 12;
				break;
			}
			case 'N': {
				evenSum += 13;
				break;
			}
			case 'O': {
				evenSum += 14;
				break;
			}
			case 'P': {
				evenSum += 15;
				break;
			}
			case 'Q': {
				evenSum += 16;
				break;
			}
			case 'R': {
				evenSum += 17;
				break;
			}
			case 'S': {
				evenSum += 18;
				break;
			}
			case 'T': {
				evenSum += 19;
				break;
			}
			case 'U': {
				evenSum += 20;
				break;
			}
			case 'V': {
				evenSum += 21;
				break;
			}
			case 'W': {
				evenSum += 22;
				break;
			}
			case 'X': {
				evenSum += 23;
				break;
			}
			case 'Y': {
				evenSum += 24;
				break;
			}
			case 'Z': {
				evenSum += 25;
				break;
			}
			}
		}
		int oddSum = 0;
		for (int i = 0; i <= 14; i += 2) {
			switch (fiscalCode.charAt(i)) {
			case '0': {
				oddSum += 1;
				break;
			}
			case '1': {
				oddSum += 0;
				break;
			}
			case '2': {
				oddSum += 5;
				break;
			}
			case '3': {
				oddSum += 7;
				break;
			}
			case '4': {
				oddSum += 9;
				break;
			}
			case '5': {
				oddSum += 13;
				break;
			}
			case '6': {
				oddSum += 15;
				break;
			}
			case '7': {
				oddSum += 17;
				break;
			}
			case '8': {
				oddSum += 19;
				break;
			}
			case '9': {
				oddSum += 21;
				break;
			}
			case 'A': {
				oddSum += 1;
				break;
			}
			case 'B': {
				oddSum += 0;
				break;
			}
			case 'C': {
				oddSum += 5;
				break;
			}
			case 'D': {
				oddSum += 7;
				break;
			}
			case 'E': {
				oddSum += 9;
				break;
			}
			case 'F': {
				oddSum += 13;
				break;
			}
			case 'G': {
				oddSum += 15;
				break;
			}
			case 'H': {
				oddSum += 17;
				break;
			}
			case 'I': {
				oddSum += 19;
				break;
			}
			case 'J': {
				oddSum += 21;
				break;
			}
			case 'K': {
				oddSum += 2;
				break;
			}
			case 'L': {
				oddSum += 4;
				break;
			}
			case 'M': {
				oddSum += 18;
				break;
			}
			case 'N': {
				oddSum += 20;
				break;
			}
			case 'O': {
				oddSum += 11;
				break;
			}
			case 'P': {
				oddSum += 3;
				break;
			}
			case 'Q': {
				oddSum += 6;
				break;
			}
			case 'R': {
				oddSum += 8;
				break;
			}
			case 'S': {
				oddSum += 12;
				break;
			}
			case 'T': {
				oddSum += 14;
				break;
			}
			case 'U': {
				oddSum += 16;
				break;
			}
			case 'V': {
				oddSum += 10;
				break;
			}
			case 'W': {
				oddSum += 22;
				break;
			}
			case 'X': {
				oddSum += 25;
				break;
			}
			case 'Y': {
				oddSum += 24;
				break;
			}
			case 'Z': {
				oddSum += 23;
				break;
			}
			}
		}
		int controlInteger = (evenSum + oddSum) % 26;
		String controlCharacter = "";
		switch (controlInteger) {
		case 0: {
			controlCharacter = "A";
			break;
		}
		case 1: {
			controlCharacter = "B";
			break;
		}
		case 2: {
			controlCharacter = "C";
			break;
		}
		case 3: {
			controlCharacter = "D";
			break;
		}
		case 4: {
			controlCharacter = "E";
			break;
		}
		case 5: {
			controlCharacter = "F";
			break;
		}
		case 6: {
			controlCharacter = "G";
			break;
		}
		case 7: {
			controlCharacter = "H";
			break;
		}
		case 8: {
			controlCharacter = "I";
			break;
		}
		case 9: {
			controlCharacter = "J";
			break;
		}
		case 10: {
			controlCharacter = "K";
			break;
		}
		case 11: {
			controlCharacter = "L";
			break;
		}
		case 12: {
			controlCharacter = "M";
			break;
		}
		case 13: {
			controlCharacter = "N";
			break;
		}
		case 14: {
			controlCharacter = "O";
			break;
		}
		case 15: {
			controlCharacter = "P";
			break;
		}
		case 16: {
			controlCharacter = "Q";
			break;
		}
		case 17: {
			controlCharacter = "R";
			break;
		}
		case 18: {
			controlCharacter = "S";
			break;
		}
		case 19: {
			controlCharacter = "T";
			break;
		}
		case 20: {
			controlCharacter = "U";
			break;
		}
		case 21: {
			controlCharacter = "V";
			break;
		}
		case 22: {
			controlCharacter = "W";
			break;
		}
		case 23: {
			controlCharacter = "X";
			break;
		}
		case 24: {
			controlCharacter = "Y";
			break;
		}
		case 25: {
			controlCharacter = "Z";
			break;
		}
		}
		fiscalCode += controlCharacter;
		return fiscalCode.toUpperCase();
	}

	private String consonants(String word) {
		word = word.toLowerCase();
		String consonants = "";
		for (char character : word.toCharArray()) {
			if (character != 'a' && character != 'e' && character != 'i'
					&& character != 'o' && character != 'u')
				consonants += character;
		}
		return consonants;
	}

	private String vowels(String word) {
		word = word.toLowerCase();
		String vowels = "";
		for (char character : word.toCharArray()) {
			if (character == 'a' || character == 'e' || character == 'i'
					|| character == 'o' || character == 'u')
				vowels += character;
		}
		return vowels;
	}


	/**
	* This method tries to reverse the fiscal code. Only some parameters can be reversed from the fiscal code.
	* @param fc An Italian fiscal code.
	* @return An array of Object containing at 0 the sex, at 1 the birthdate, at 2 the city.
	*/
	public static Object[] reverseFC(String fc) {
		Object[] toReturn = new Object[3];
		Character sex = new Character('M');

		// day
		String day = fc.substring(9, 11);
		int numDay = Integer.parseInt(day);
		if (numDay > 31) {
			sex = new Character('F');
			numDay -= 40;
		}
		// month
		int mm = 0;
		char m = fc.substring(8, 9).toLowerCase().charAt(0);
		switch (m) {
		case 'a':
			mm = 0;
			break;
		case 'b':
			mm = 1;
			break;
		case 'c':
			mm = 2;
			break;
		case 'd':
			mm = 3;
			break;
		case 'e':
			mm = 4;
			break;
		case 'h':
			mm = 5;
			break;
		case 'l':
			mm = 6;
			break;
		case 'm':
			mm = 7;
			break;
		case 'p':
			mm = 8;
			break;
		case 'r':
			mm = 9;
			break;
		case 's':
			mm = 10;
			break;
		case 't':
			mm = 11;
			break;
		}
		// year
		int theYear = 0;
		int thisYear = Integer.parseInt(String.valueOf(
				Calendar.getInstance().get(Calendar.YEAR)).substring(2, 4));
		String yy = fc.substring(6, 8);
		int y = Integer.parseInt(yy);
		if (y >= thisYear) {
			theYear = 1900 + y;
		} else {
			theYear = 2000 + y;
		}
		Date birthDate = new Date(theYear, mm, numDay);
		String cityCode = fc.substring(11, 15);
		City city = null;
		for (City c : CityParser.getCityDb(false)) {
			if (c.getCode().equalsIgnoreCase(cityCode)) {
				city = c;
				break;
			}
		}
		toReturn[0] = sex;
		toReturn[1] = birthDate;
		toReturn[2] = city;
		return toReturn;
	}
}