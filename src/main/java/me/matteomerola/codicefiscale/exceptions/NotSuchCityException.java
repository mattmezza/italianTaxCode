package me.matteomerola.codicefiscale.exceptions;

public class NotSuchCityException extends Exception {

	public NotSuchCityException() {
		super("The city name you specified is not a valid city name nor a country name.");
	}

}
