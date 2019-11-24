package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import sample.cipher.AffineCipher;
import sample.cipher.CaesarCipher;
import sample.cipher.Cipher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AffineCipherController {

    private static final String alphabetChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private final FileChooser fileChooser = new FileChooser();

    @FXML
    private TextArea initialText;

    @FXML
    private TextArea encryptedText;

    @FXML
    private Button encryptBtn;

    @FXML
    private MenuItem openTextFileItem;

    @FXML
    private MenuItem openEncFileItem;

    @FXML
    private Button decryptBtn;

    @FXML
    private Spinner<Integer> affineCipherA;

    @FXML
    private Spinner<Integer> affineCipherB;

    @FXML
    private Button decryptFrequencyAnalysisBtn;

    @FXML
    private TextArea decryptedTextArea;

    @FXML
    void decrypt(MouseEvent event) {
        Alphabet alphabet = new Alphabet('E', alphabetChars);
        Cipher cipher = new AffineCipher(alphabet, this.affineCipherA.getValue(), this.affineCipherB.getValue());

        String decryptedMessage = cipher.decrypt(this.encryptedText.getText());
        this.decryptedTextArea.setText(decryptedMessage);
    }

    @FXML
    void encryptText(MouseEvent event) {
        String initialText = this.initialText.getText();

        int a = this.affineCipherA.getValue();
        int b = this.affineCipherB.getValue();

        if (!coprime(a, this.alphabetChars.length())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Encryption error");
            alert.setHeaderText("Could not encrypt the text");
            alert.setContentText("Number A and alphabet size (" + this.alphabetChars.length() + ") have to be co-prime.");

            alert.showAndWait();
        } else {
            Cipher cipher = new AffineCipher(new Alphabet('E', this.alphabetChars), a, b);

            Alphabet alphabet = new Alphabet('E', alphabetChars);
            String encryptedText = cipher.encrypt(initialText);
            this.encryptedText.setText(encryptedText);

            File file = this.fileChooser.showSaveDialog(openEncFileItem.getParentPopup().getScene().getWindow());
            if (file != null) {
                saveTextToFile(Collections.singletonList(encryptedText), file);
            }
        }
    }

    @FXML
    void openTextFile(ActionEvent event) {
        File file = this.fileChooser.showOpenDialog(openEncFileItem.getParentPopup().getScene().getWindow());
        if (file != null) {
            this.initialText.setText(readFile(file));
        }
    }

    @FXML
    void openEncFile(ActionEvent event) {
        File file = this.fileChooser.showOpenDialog(openEncFileItem.getParentPopup().getScene().getWindow());
        if (file != null) {
            this.encryptedText.setText(readFile(file));
        }
    }

    @FXML
    void initialize() {
        this.fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text", "*.txt"));
        this.affineCipherA.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                this.affineCipherA.increment(0); // won't change value, but will commit editor
            }
        });
        this.affineCipherB.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                this.affineCipherB.increment(0); // won't change value, but will commit editor
            }
        });
    }

    private void saveTextToFile(List<String> content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            for (String contentString: content) {
                writer.println(contentString);
                writer.println();
            }
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(CaesarCipherController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String readFile(File file){
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {

            bufferedReader = new BufferedReader(new FileReader(file));

            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(CaesarCipherController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CaesarCipherController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                Logger.getLogger(CaesarCipherController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return stringBuffer.toString();
    }

    private boolean coprime(int p, int q) {
        return (gcd(p, q) == 1);
    }

    private int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }
}
