package com.canmakethat.you.fiscalcode;

import java.lang.Exception;

public class NotSuchCityException extends Exception {
	
	public NotSuchCityException() {
		super("The city name you specified is not a valid city name nor a country name.");
	}

}