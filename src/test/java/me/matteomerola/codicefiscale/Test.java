package me.matteomerola.codicefiscale;

import junit.framework.TestCase;
import me.matteomerola.codicefiscale.exceptions.*;
import java.util.Date;

/**
* This class represents a test case for the Parser
* @author Matteo Merola, datasounds
*/
public class Test extends TestCase {

  private static String FC = "RSSMRA78H12H501W";
  private static String CITY = "Roma";
  private static String NAME = "Mario";
  private static String SURNAME = "Rossi";
  private static int DAY = 12;
  private static int MONTH = 5;
  private static int YEAR = 1978;
  private static char GENDER = 'M';

  public void testFC() throws Exception {
    FiscalCodeCalculator fcc = new FiscalCodeCalculator();
    try {
			String fiscalCode = fcc.calculateFC(
        NAME,
        SURNAME,
        GENDER,
        new Date(YEAR, MONTH, DAY),
        CITY
      );
      assertEquals(fiscalCode, FC);

			Object[] response = fcc.reverseFC(FC);
      assertEquals(response[0], GENDER);
      assertEquals(response[1], new Date(YEAR, MONTH, DAY));
      assertTrue(((City) response[2]).getName().equalsIgnoreCase(CITY));
		} catch(NotSuchCityException e) {
			System.out.println(e.getMessage());
		}
  }

  public void testError() throws Exception {
    FiscalCodeCalculator fcc = new FiscalCodeCalculator();
    boolean exceptionFired = false;
    try {
      String fiscalCode = fcc.calculateFC(
        NAME,
        SURNAME,
        GENDER,
        new Date(YEAR, MONTH, DAY),
        "flsdjflasd"
      );
    } catch(NotSuchCityException e) {
      exceptionFired = true;
    }
    assertTrue(exceptionFired);
  }
}
