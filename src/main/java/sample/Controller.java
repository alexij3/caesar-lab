package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

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

public class Controller {

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
    private Button decryptAllVariantsBtn;

    @FXML
    private Spinner<Integer> cipherStepInput;

    @FXML
    private Button decryptFrequencyAnalysisBtn;

    @FXML
    private TextArea decryptedTextArea;

    @FXML
    void decryptAllVariants(MouseEvent event) {
        Cipher cipher = new CaesarCipher();
        FrequencyAnalysis frequencyAnalysis = new FrequencyAnalysis(cipher);
        Alphabet alphabet = new Alphabet('E', alphabetChars);
        frequencyAnalysis.decrypt(this.encryptedText.getText(), alphabet);


        StringBuilder stringBuilder = new StringBuilder();

        List<String> decryptedMessages = cipher.decrypt(this.encryptedText.getText(), alphabet);
        for (String decryptedMessage:  decryptedMessages) {
            stringBuilder.append(decryptedMessage);
            stringBuilder.append("\n\n");

        }

        this.decryptedTextArea.setText(stringBuilder.toString());
    }

    @FXML
    void decryptWithFreqAnalysis(MouseEvent event) {
        Cipher cipher = new CaesarCipher();
        FrequencyAnalysis frequencyAnalysis = new FrequencyAnalysis(cipher);
        Alphabet alphabet = new Alphabet('E', alphabetChars);
        frequencyAnalysis.decrypt(this.encryptedText.getText(), alphabet);

        this.decryptedTextArea.setText(frequencyAnalysis.decrypt(this.encryptedText.getText(), alphabet));
    }

    @FXML
    void encryptText(MouseEvent event) {
        String initialText = this.initialText.getText();

        int step = this.cipherStepInput.getValue();
        Cipher caesar = new CaesarCipher();

        Alphabet alphabet = new Alphabet('E', alphabetChars);
        String encryptedText = caesar.encrypt(initialText, alphabet, step);
        this.encryptedText.setText(encryptedText);

        File file = this.fileChooser.showSaveDialog(openEncFileItem.getParentPopup().getScene().getWindow());
        if (file != null) {
            saveTextToFile(Collections.singletonList(encryptedText), file);
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
        this.cipherStepInput.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                this.cipherStepInput.increment(0); // won't change value, but will commit editor
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
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return stringBuffer.toString();
    }

}
