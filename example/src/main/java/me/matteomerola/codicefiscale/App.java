package me.matteomerola.codicefiscale;

import me.matteomerola.codicefiscale.FiscalCodeCalculator;
import me.matteomerola.codicefiscale.exceptions.NotSuchCityException;
import java.util.Date;

/**
 * Hello world!
 *
 */
public class App {
    public static final String QUIT = "q";

    public static void main(String[] args) {
      FiscalCodeCalculator fcc = new FiscalCodeCalculator();
      try {
        String fiscalCode = fcc.calculateFC("Mario", "Rossi", 'M', new Date(1979, 2, 31), "Pozzilli");
        System.out.println(fiscalCode);
        System.out.println("tryin to reverse now...");
        Object[] response = fcc.reverseFC(fiscalCode);
        for(Object o : response) {
          System.out.println(o);
        }
      } catch(NotSuchCityException e) {
        System.err.println(e.getMessage());
      }
    }
}
