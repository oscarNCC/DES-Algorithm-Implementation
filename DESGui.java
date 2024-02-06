import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DESGui {
    private JFrame frame;
    private JTextField keyTextField;
    private JTextField inputTextField;
    private JTextArea resultTextArea;
    private JButton encryptButton;
    private JButton decryptButton;
    private JButton randomKeyButton;

    public DESGui() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblKey = new JLabel("Key:");
        lblKey.setBounds(10, 10, 80, 25);
        frame.getContentPane().add(lblKey);

        keyTextField = new JTextField();
        keyTextField.setBounds(100, 10, 320, 25);
        frame.getContentPane().add(keyTextField);
        keyTextField.setColumns(10);

        JLabel lblInput = new JLabel("Input:");
        lblInput.setBounds(10, 45, 80, 25);
        frame.getContentPane().add(lblInput);

        inputTextField = new JTextField();
        inputTextField.setBounds(100, 45, 320, 25);
        frame.getContentPane().add(inputTextField);
        inputTextField.setColumns(10);

        encryptButton = new JButton("Encrypt");
        encryptButton.setBounds(10, 80, 90, 25);
        frame.getContentPane().add(encryptButton);

        decryptButton = new JButton("Decrypt");
        decryptButton.setBounds(110, 80, 90, 25);
        frame.getContentPane().add(decryptButton);

        randomKeyButton = new JButton("Random Key");
        randomKeyButton.setBounds(210, 80, 120, 25);
        frame.getContentPane().add(randomKeyButton);

        resultTextArea = new JTextArea();
        resultTextArea.setBounds(10, 115, 410, 135);
        frame.getContentPane().add(resultTextArea);

        encryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String key = keyTextField.getText();
                String input = inputTextField.getText();
                
                // Validate hex input
                if (isValidHex(key) && isValidHex(input)) {
                    des_e des = new des_e();
                    String cipherText = des.encrypt(key, input);
                    resultTextArea.setText("Encrypted text: \n" + cipherText);
                } else {
                    resultTextArea.setText("Error: Key and input must be hexadecimal (0-9, a-f, A-F).");
                }
            }
        });

        randomKeyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generateRandomHexKey(16); // Always generate a new key when clicked
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String key = keyTextField.getText();
                String input = inputTextField.getText();
                
                // Validate hex input
                if (isValidHex(key) && isValidHex(input)) {
                    des_d des_de = new des_d();
                    String answer = des_de.decrypt(key, input);
                    resultTextArea.setText("Decrypted text: \n" + answer);
                } else {
                    resultTextArea.setText("Error: Key and input must be hexadecimal (0-9, a-f, A-F).");
                }
            }
  
        });
    }

    private static boolean isValidHex(String s) {
        return s.matches("^[0-9a-fA-F]+$");
    }

    private void generateRandomHexKey(int length) {
        StringBuilder keyBuilder = new StringBuilder(length);
        String hexChars = "0123456789abcdef";
        for (int i = 0; i < length; i++) {
            int randomIndex = (int) (Math.random() * hexChars.length());
            keyBuilder.append(hexChars.charAt(randomIndex));
        }
        keyTextField.setText(keyBuilder.toString()); // Update the text field with new key
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    DESGui window = new DESGui();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
