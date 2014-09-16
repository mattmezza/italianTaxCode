italianTaxCode
==============

Everybody loves to pay taxes!  [-_-]


This is a library to calculate Italian tax code. It can calculate a tax code from data (name, surname, sex, birth date, birth place) and data (sex, birth date, birth place) from code.


Questa libreria Java ti può essere utile se devi implementare il calcolo del codice fiscale. Supporta il calcolo del codice fiscale a partire dai dati (nome, cognome, sesso, data di nascita, luogo di nascita) e dei dati (sesso, data di nascita, luogo di nascita) a partire dal codice fiscale.


### Usage

    FiscalCodeCalculator fcc = new FiscalCodeCalculator();
    try {
      String fiscalCode = fcc.calculateFC("Mario", "Rossi", 'M', new Date(1979, 2, 31), "Pozzilli");
      System.out.println(fiscalCode);
      System.out.println("tryin to reverse now...");
      Object[] response = fcc.reverseFC(fiscalCode);
      for(Object o : response) {
        System.out.println(o);
      }
    } catch(NotSuchCityException e) {}

=============
_Matteo Merola_
