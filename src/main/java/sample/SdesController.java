package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import sample.cipher.RSA;
import sample.cipher.SDES;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SdesController {

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
    private TextField keyInput;

    @FXML
    private Button decryptFrequencyAnalysisBtn;

    @FXML
    private TextArea decryptedTextArea;

    @FXML
    void decrypt(MouseEvent event) {
        String key = this.keyInput.getText();
        SDES sdes = new SDES(new BigInteger(key.getBytes()).intValue());
        this.decryptedTextArea.setText(String.valueOf((char)(sdes.decrypt(this.encryptedText.getText().getBytes()[0]))));
    }

    @FXML
    void encryptText(MouseEvent event) {
        String key = this.keyInput.getText();
        SDES sdes = new SDES(new BigInteger(key.getBytes()).intValue());
        this.encryptedText.setText(String.valueOf((char)(sdes.encrypt(this.initialText.getText().getBytes()[0]))));
    }

    @FXML
    void initialize() {

    }

}
