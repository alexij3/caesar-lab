package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import sample.cipher.SDES;

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
        SDES sdes = new SDES(Integer.parseInt(key, 2));
        String decrypted = "" + (char) sdes.decrypt(this.encryptedText.getText().charAt(0));
        this.decryptedTextArea.setText(decrypted);
    }

    @FXML
    void encryptText(MouseEvent event) {
        String key = this.keyInput.getText();
        SDES sdes = new SDES(Integer.parseInt(key, 2));
        String encrypted = "" + (char) sdes.encrypt(this.initialText.getText().charAt(0));
        this.encryptedText.setText(encrypted);
    }

    @FXML
    void initialize() {
    }

}
