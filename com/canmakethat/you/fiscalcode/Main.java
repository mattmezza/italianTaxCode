package com.canmakethat.you.fiscalcode;

import java.util.List;
import java.util.Date;

public class Main {
	
	public static void main(String[] args) {
		FiscalCodeCalculator fcc = new FiscalCodeCalculator();
		try {
			String fiscalCode = fcc.calculateFC("Matteo", "Merola", 'M', new Date(1989, 2, 11), "Venafro");
			System.out.println(fiscalCode);
			System.out.println("tryin to reverse now...");
			Object[] response = fcc.reverseFC(fiscalCode);
			for(Object o : response) {
				System.out.println(o);
			}
		} catch(NotSuchCityException e) {
			System.out.println(e.getMessage());
		}
	}
}