package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import sample.cipher.RSA;
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

public class RsaController {

    private static final String alphabetChars = "abcdefghijklmnopqrstuvwxyz";

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
    private Spinner<Integer> pValue;

    @FXML
    private Spinner<Integer> qValue;

    @FXML
    private Spinner<Integer> eValue;

    @FXML
    private Spinner<Integer> dValue;

    @FXML
    private Button decryptFrequencyAnalysisBtn;

    @FXML
    private TextArea decryptedTextArea;

    private RSA rsa;

    @FXML
    void decrypt(MouseEvent event) {
        Alphabet alphabet = new Alphabet('E', alphabetChars);

        int p = this.pValue.getValue();
        int q = this.qValue.getValue();
        int e = this.eValue.getValue();
        int d = this.dValue.getValue();

        String decryptedMessage = rsa.decrypt(this.encryptedText.getText());
        this.decryptedTextArea.setText(decryptedMessage);
    }

    @FXML
    void encryptText(MouseEvent event) {
        String initialText = this.initialText.getText();

        int p = this.pValue.getValue();
        int q = this.qValue.getValue();
        int e = this.eValue.getValue();
        int d = this.dValue.getValue();

        rsa = new RSA(new Alphabet('E', alphabetChars), q, p, e, d);

        this.encryptedText.setText(rsa.encrypt(initialText));

        File file = this.fileChooser.showSaveDialog(openEncFileItem.getParentPopup().getScene().getWindow());
        if (file != null) {
            saveTextToFile(Collections.singletonList(new String(encryptedText.getText())), file);
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
        this.pValue.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                this.pValue.increment(0); // won't change value, but will commit editor
            }
        });
        this.qValue.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                this.qValue.increment(0); // won't change value, but will commit editor
            }
        });
        this.qValue.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                this.qValue.increment(0); // won't change value, but will commit editor
            }
        });
        this.qValue.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                this.qValue.increment(0); // won't change value, but will commit editor
            }
        });
        this.pValue.getValueFactory().setValue(3);
        this.qValue.getValueFactory().setValue(11);
        this.eValue.getValueFactory().setValue(7);
        this.dValue.getValueFactory().setValue(3);
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
