package appointments.validators;

public class TaxNumberValidatorPojo {
    public static boolean check(String taxNumber,int numberLength){
        if (taxNumber.length() != numberLength)
            return false;
        try {
            int checksum = Integer.parseInt(String.valueOf(taxNumber.charAt(9)));

            int number = 0;
            for (int i = 0; i < taxNumber.length() - 1; i++) {
                int c = Integer.parseInt(String.valueOf(taxNumber.charAt(i)));
                number += c * (i + 1);
            }

            return number % 11 == checksum;

        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
