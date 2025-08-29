import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public String card = null;
    public String type = null;
    public int MII = 0;
    public int BINumber = 0;

    public static void main(String[] args) {
        Main main = new Main();
        main.generate();

        System.out.println("Card Number: " + main.card);
    }

    public String generate() {
        Random rand = new Random();

        // MII assigner (Major Industry identifier)
        MII = rand.nextInt(3, 7);
        card =String.valueOf(MII);
        System.out.println("[!] MII number assigned: " + MII);

        Map<String, String> BIN = new HashMap<>();
        System.out.println("[!] Hash map created");

        if (MII == 3) {
            setType("American Express");
            System.out.println("[!] Selected type: " + getType());
            BIN.put("American Express","37641,37645,37606,371708,371725,371726,371727,371736,371741,371758,372557,372559,372583");
            System.out.println("[!] Added 'American Express' element to the hash map with the value of " + BIN.get("American Express"));

            String[] binStrings = BIN.get("American Express").split(",");
            BINumberCalculator(rand, binStrings);
        } else if (MII == 4) {
            setType("Visa");
            System.out.println("[!] Selected type: " + getType());
            BIN.put("Visa","473223,473224,473225,473226,451774,493487,472260,477162,410189,424947,448384,417366,455692,491650,410852");
            System.out.println("[!] Added 'visa' element to the hash map with the value of " + BIN.get("Visa"));

            String[] binStrings = BIN.get("Visa").split(",");
            BINumberCalculator(rand, binStrings);

        } else if (MII == 5) {
            setType("Mastercard");
            System.out.println("[!] Selected type: " + getType());
            BIN.put("Mastercard","424947,448384,417366,550356,529206,546342,524088,519696,525640,529585,552114,540185,525717");
            System.out.println("[!] Added 'Mastercard' element to the hash map with the value of " + BIN.get("Mastercard"));

            String[] binStrings = BIN.get("Mastercard").split(",");
            BINumberCalculator(rand, binStrings);

        } else if (MII == 6) {
            setType("Unionpay");
            System.out.println("[!] Selected type: " + getType());
            BIN.put("Unionpay","811126,813407,811292,813990,812071,813192,816433,816375,622305,622698");
            System.out.println("[!] Added 'Unionpay' element to the hash map with the value of " + BIN.get("Unionpay"));

            String[] binStrings = BIN.get("Unionpay").split(",");
            BINumberCalculator(rand, binStrings);

        } else {
            setType(null);
            System.out.println("[WARN] Selected type: " + getType());
        }



        StringBuilder accNumbers = new StringBuilder();
        for (int i = 0; i < 11; i++) {
            int randomAccNum = rand.nextInt(0, 10);
            accNumbers.append(randomAccNum);
        }
        card += accNumbers.toString();
        System.out.println("[!] Selected random numbers instead of a real account number: " + accNumbers);

        int sum = 0;
        boolean alternate = false;
        for (int i = card.length() - 1; i >= 0; i--) {
            int n = Character.getNumericValue(card.charAt(i));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n -= 9;
                }
            }
            sum += n;
            alternate = !alternate;
        }

        System.out.println("[!] Calculated sum for Luhn algorithm: " + sum);

        if (sum % 10 == 0) {
            return card;
        }  else {
            System.out.println("[WARN] Re-generating card");
            return generate();
        }
    }

    public void BINumberCalculator(Random rand, String[] binStrings) {
        int[] tempArr = new int[binStrings.length];
        for (int i = 0; i < binStrings.length; i++) {
            tempArr[i] = Integer.parseInt(binStrings[i]);
        }
        BINumber = tempArr[rand.nextInt(tempArr.length)];
        card += BINumber;
        System.out.println("[!] Bin number assigned: " + BINumber);
    }


    public void setType(String type1) {
        type = type1;
    }

    public String getType() {
        return type;
    }
}
