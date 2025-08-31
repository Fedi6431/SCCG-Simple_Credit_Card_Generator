import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
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

public class CCGenerator {
    public Random rand = new Random();
    Logging log = new Logging();

    private JTextField cardNumberField;
    private JTextField pinField;
    private JTextField expiryDateField;
    private JTextField cfcField;

    // CC information
    public int MII = 0;
    public String type = null;
    public int BINumber = 0;
    public String card = null;
    public String accountNumber = null;
    public int expiryMonth = 1;
    public int expiryYear = 0;
    public int cvc = 0;
    public int pin = 0;

    public static void main(String[] args) {
        CCGenerator main = new CCGenerator();
        Logging log = new Logging();
        log.makeFile();
        main.createUI();
    }

    public void createUI() {
        // UI Components
        JFrame frame = new JFrame();
        frame.setTitle("Credit Card Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);

        try {
            Image image = ImageIO.read(new File("img/ccc.png"));
            frame.setIconImage(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Title
        JLabel titleLabel = new JLabel("Credit Card Generator", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBounds(10, 10, 460, 30);

        // Card Number
        JLabel cardNumberLabel = new JLabel("Card Number:");
        cardNumberLabel.setBounds(20, 50, 100, 25);
        cardNumberField = new JTextField();
        cardNumberField.setBounds(120, 50, 350, 25);
        cardNumberField.setEditable(false);

        // PIN
        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setBounds(20, 80, 100, 25);
        pinField = new JTextField();
        pinField.setBounds(120, 80, 100, 25);
        pinField.setEditable(false);

        // Expiry Date
        JLabel expiryDateLabel = new JLabel("Expiry Date:");
        expiryDateLabel.setBounds(230, 80, 100, 25);
        expiryDateField = new JTextField();
        expiryDateField.setBounds(330, 80, 100, 25);
        expiryDateField.setEditable(false);

        // CFC (CVC)
        JLabel cfcLabel = new JLabel("CFC:");
        cfcLabel.setBounds(20, 110, 100, 25);
        cfcField = new JTextField();
        cfcField.setBounds(120, 110, 100, 25);
        cfcField.setEditable(false);

        // Generate Button
        JButton generateButton = new JButton("Generate");
        generateButton.setBounds(200, 150, 100, 30);
        generateButton.addActionListener(e -> {
            generateCard();
            cardNumberField.setText(card);
            pinField.setText(String.format("%04d", pin));
            expiryDateField.setText(expiryMonth + "/" + expiryYear);
            cfcField.setText(String.format("%03d", cvc));
        });

        // Add components to frame
        frame.setLayout(null);
        frame.add(titleLabel);
        frame.add(cardNumberLabel);
        frame.add(cardNumberField);
        frame.add(pinLabel);
        frame.add(pinField);
        frame.add(expiryDateLabel);
        frame.add(expiryDateField);
        frame.add(cfcLabel);
        frame.add(cfcField);
        frame.add(generateButton);

        frame.setVisible(true);
    }

    // Set type function
    public void setType(String type1) {
        type = type1;
    }
    // Get type function
    public String getType() {
        return type;
    }

    public String generateCard() {
        // Generate CFC (CVC)
        cvc = rand.nextInt(100, 1000);
        // Generate pin
        pin = rand.nextInt(0, 10000);
        // Generate expiry moth
        expiryMonth = rand.nextInt(0,12);
        // Generate expiry year
        expiryYear = Year.now().getValue() + rand.nextInt(1,6);
        // Generate a number from 3 to 6 assign it to the MII variable (Major Industry Identifier)
        // Set the card variable to the MII value
        MII = rand.nextInt(3, 7);
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
            BIN.put("Visa","473223,473224,473225,473226,451774,493487,472260,477162,410189,424947,448384,417366,455692,491650,410852,432733");
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
    private final String path = "logs/";
    private final String filename = "log-" + getCurrentDate();

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
                    appendInfo("Directory created");
            } else {
                    appendWarn("Directory already exist");
            }
            File file = new File(path + filename);
            if (file.createNewFile()) {
                    appendInfo("File created");
            } else {
                    appendWarn("File already exist");

            }
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
