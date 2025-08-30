import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.Year;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public Random rand = new Random();
    Logging log = new Logging();
    // CC information
    public int MII = rand.nextInt(3, 7);
    public String type = null;
    public int BINumber = 0;
    public String card = null;
    public String accountNumber = null;
    public int expiryMonth = 1;
    public int expiryYear = Year.now().getValue();
    public int cvc = rand.nextInt(100,1000);
    public int pin = rand.nextInt(0,10000);


    // Set type function
    public void setType(String type1) {
        type = type1;
    }
    // Get type function
    public String getType() {
        return type;
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.log.makeFile();
        main.log.append("#######################################################");
        main.log.append("####################PROGRAM-STARTED####################");
        main.log.append("#######################################################");

        main.log.append("MADE BY FEDI6431");

        main.generateCard();
        System.out.println("Credit Card information:");
        System.out.println("MII (Major Industry Number): " + main.MII);
        System.out.println("Card type: " + main.type);
        System.out.println("BIN (Bank Identification Number): " + main.BINumber);
        System.out.println("Account number: " + main.accountNumber);
        System.out.println("Card number: " + main.card);
        System.out.println("CVC/CVV: " + main.cvc);
        System.out.println("PIN: " + main.pin);
        System.out.println("Expiry date: " + (main.expiryMonth + main.rand.nextInt(1,12)) + "/" + (main.expiryYear + main.rand.nextInt(1,6)));
    }

    public String generateCard() {
        // Generate a number from 3 to 6 assign it to the MII variable (Major Industry Identifier)
        // Set the card variable to the MII value
        card = String.valueOf(MII);
        // Print that the MII number has been assigned
        log.appendInfo("MII (Major Industry Number) assigned: " + MII);
        // Create a hash map
        Map<String, String> BIN = new HashMap<>();
        // Print that the hash map has been created
        log.appendInfo("Hash map created");
        // Based on the output, a card type is selected
        if (MII == 3) {
            // Set the type to "American Express"
            setType("American Express");
            // Print that the type has been selected
            log.appendInfo("Selected type: " + getType());
            System.out.println();
            // Add to the hash map various BIN (Bank Identification Number)
            BIN.put("American Express","37641,37645,37606,371708,371725,371726,371727,371736,371741,371758,372557,372559,372583");
            // Print that it has been added an element to the hash map
            log.appendInfo("Added 'American Express' element to the hash map with the value of " + BIN.get("American Express"));
            // Makes an array and remove all the ","
            String[] binStrings = BIN.get("American Express").split(",");
            BINumberCalculator(rand, binStrings);
        } else if (MII == 4) {
            // Set the type to "Visa"
            setType("Visa");
            // Print that the type has been selected
            log.appendInfo("Selected type: " + getType());
            // Add to the hash map various BIN (Bank Identification Number)
            BIN.put("Visa","473223,473224,473225,473226,451774,493487,472260,477162,410189,424947,448384,417366,455692,491650,410852");
            // Print that it has been added an element to the hash map
            log.appendInfo("Added 'visa' element to the hash map with the value of " + BIN.get("Visa"));
            // Makes an array and remove all the ","
            String[] binStrings = BIN.get("Visa").split(",");
            BINumberCalculator(rand, binStrings);
        } else if (MII == 5) {
            // Set the type to "Mastercard"
            setType("Mastercard");
            // Print that the type has been selected
            log.appendInfo("Selected type: " + getType());
            // Add to the hash map various BIN (Bank Identification Number)
            BIN.put("Mastercard","424947,448384,417366,550356,529206,546342,524088,519696,525640,529585,552114,540185,525717");
            // Print that it has been added an element to the hash map
            log.appendInfo("Added 'Mastercard' element to the hash map with the value of " + BIN.get("Mastercard"));
            // Makes an array and remove all the ","
            String[] binStrings = BIN.get("Mastercard").split(",");
            BINumberCalculator(rand, binStrings);
        } else if (MII == 6) {
            // Set the type to "Unionpay"
            setType("Unionpay");
            // Print that the type has been selected
            log.appendInfo("Selected type: " + getType());
            // Add to the hash map various BIN (Bank Identification Number)
            BIN.put("Unionpay","811126,813407,811292,813990,812071,813192,816433,816375,622305,622698");
            // Print that it has been added an element to the hash map
            log.appendInfo("Added 'Unionpay' element to the hash map with the value of " + BIN.get("Unionpay"));
            // Makes an array and remove all the ","
            String[] binStrings = BIN.get("Unionpay").split(",");
            BINumberCalculator(rand, binStrings);
        } else {
            // Set the type to "null"
            setType(null);
            // Print a warning that the type has been selected as null
            log.appendInfo("Selected type: " + getType());
        }
        // Generate a random account number
        // Create a variable
        StringBuilder accNumbers = new StringBuilder();
        // For loop 11 times
        for (int i = 0; i < 11; i++) {
            // Generate a random number from 0 to 9
            // Add the number to the list
            accNumbers.append(rand.nextInt(0, 10));
        }
        // Print that it has generated a random account number
        log.appendInfo("Selected random numbers instead of a real account number: " + accNumbers);
        // Add the account number to the card number
        accountNumber = accNumbers.toString();
        card += accountNumber;

        // Luhn Algorithm check
        // Sum variable
        int sum = 0;
        // Check if the number is alternate with a boolean
        boolean alternate = false;
        // For the card length loop
        for (int i = card.length() - 1; i >= 0; i--) {
            // Get the card number value
            int n = Character.getNumericValue(card.charAt(i));
            // Check if it's alternate
            if (alternate) {
                // If it's alternate, it multiply the number by 2
                n *= 2;
                // If the number is greater than 9 it removes 9 form the number
                if (n > 9) {
                    n -= 9;
                }
            }
            // Add the calculated number to the sum
            sum += n;
            // Invert the alternate value
            alternate = !alternate;
        }
        // Print that the algorithm has been checked
        log.appendInfo("Calculated sum for Luhn algorithm: " + sum);
        // If the sum is divisible by 10, it returns the card number. If the sum isn't divisible by 10, it re call itself to re-generate the card
        if (sum % 10 == 0) {
            return card;
        }  else {
            log.appendWarn("Re-generating card");
            return generateCard();
        }
    }

    // BIN number calculator
    public void BINumberCalculator(Random rand, String[] binStrings) {
        // Creates a temp array with the length of the variable "binStrings"
        int[] tempArr = new int[binStrings.length];
        // For the length of the variable
        for (int i = 0; i < binStrings.length; i++) {
            // It converts the array from String to integer
            tempArr[i] = Integer.parseInt(binStrings[i]);
        }
        // It decides the BIN number by selecting a random number from the tempArr length
        BINumber = tempArr[rand.nextInt(tempArr.length)];
        // Add the BIN to the card
        card += BINumber;
        // Print that the BIN has been assigned
        log.appendInfo("BIN assigned: " + BINumber);
    }
}

class Logging {
    // File path and filename variables
    private String path = "logs/";
    // Set path method
    public void setPath(String dirPath, boolean defaultPath) {
        if (defaultPath) {
            path = "logs/";
        } else {
            if (!dirPath.endsWith("/")) {
                path = dirPath + "/";
            } else {
                path = dirPath;
            }
        }
    }
    // Return path variable method
    public String getPath() {
        return path;
    }

    private String filename = "log-" + getCurrentDate();
    // Set filename method
    public void setFilename(String name, boolean defaultPath) {
        if (defaultPath) {
            filename = "log-" + getCurrentDate();
        } else {
            filename = name;
        }
    }
    // Return filename variable method
    public String getFilename() {
        return filename;
    }

    // Verbose log variable
    private boolean verboseLog = false;
    // Verbose log variable editor method
    public void verbose(boolean bool) {
        verboseLog = bool;
    }
    // Return verbose variable method
    public boolean getVerbose() {
        return verboseLog;
    }

    // Startup pop up variable
    private String startUpPopUp = "";
    // Startup pop up variable editor method
    public void setStartUpPopUp(String string, boolean fancy) {
        if (fancy) {
            int numHashes = string.length() + 4; // +2 for the additional # on both sides
            startUpPopUp = "#".repeat(Math.max(0, numHashes)) +
                    "\n# " + string + " #\n" + // Add the string with # on both sides
                    "#".repeat(Math.max(0, numHashes)); // Convert StringBuilder to String
        } else {
            startUpPopUp = string;
        }
    }
    // Return startup pop up variable method
    public String getStartUpPopUp() {
        return  startUpPopUp;
    }

    // Return current date method
    private String getCurrentDate() {
        SimpleDateFormat ft
                = new SimpleDateFormat("dd-MM-yyyy");
        return ft.format(new Date());
    }

    // Return current time method
    private LocalTime getCurrentTime() {
        return LocalTime.now();
    }

    // Make file method
    public void makeFile() {
        try {
            File dir = new File(path);
            if (dir.mkdir()) {
                if (verboseLog) {
                    appendInfo("Directory created");
                }
            } else {
                if (verboseLog) {
                    appendWarn("Directory already exist");
                }
            }

            File file = new File(path + filename);
            if (file.createNewFile()) {
                if (verboseLog) {
                    appendInfo("File created");
                }
            } else {
                if (verboseLog) {
                    appendWarn("File already exist");
                }
            }
            append(startUpPopUp);
        } catch (IOException e) {
            appendErr(e.getMessage());
        }

    }
    // Append in file method (startup only)
    void append(String str) {
        try {
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(path + filename, true));
            out.write(str + "\n");
            out.close();
        } catch (IOException e) {
            appendErr(e.getMessage());
        }
    }
    // Append an info in the file method
    public void appendInfo(String str) {
        try {
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(path + filename, true));
            out.write(getCurrentDate() + " " + getCurrentTime() + " | info: "+ str + "\n");
            out.close();
        } catch (IOException e) {
            appendErr(e.getMessage());
        }
    }
    // Append a warning in the file method
    public void appendWarn(String str) {
        try {
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(path + filename, true));
            out.write(getCurrentDate() + " " + getCurrentTime() + " | warn: "+ str + "\n");
            out.close();
        } catch (IOException e) {
            appendErr(e.getMessage());
        }
    }
    // Append an error in the file method
    public void appendErr(String str) {
        try {
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(path + filename, true));
            out.write(getCurrentDate() + " " + getCurrentTime() + " | err: "+ str + "\n");
            out.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
