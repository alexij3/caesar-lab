package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import sample.cipher.CaesarCipher;
import sample.cipher.Cipher;
import sample.cipher.SimpleReplacementCipher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleReplacementController {

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
    private Button decryptAllVariantsBtn;

    @FXML
    private Spinner<Integer> cipherStepInput;

    @FXML
    private Button decryptFrequencyAnalysisBtn;

    @FXML
    private TextArea decryptedTextArea;

    private SimpleReplacementCipher simpleReplacementCipher;

    @FXML
    private TableView<Integer> table;

    @FXML
    void decryptAllVariants(MouseEvent event) {
        String decryptedMessage = this.simpleReplacementCipher.decrypt(this.encryptedText.getText());
        this.decryptedTextArea.setText(decryptedMessage);
    }

    @FXML
    void encryptText(MouseEvent event) {
        String initialText = this.initialText.getText();

        int step = this.cipherStepInput.getValue();
        this.simpleReplacementCipher = new SimpleReplacementCipher();

        Alphabet alphabet = new Alphabet('E', alphabetChars);
        simpleReplacementCipher.setStep(step);
        simpleReplacementCipher.setAlphabet(alphabet);
        String encryptedText = simpleReplacementCipher.encrypt(initialText);
        this.encryptedText.setText(encryptedText);
        this.addColumns(table, simpleReplacementCipher.getCharactersMap());

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
        this.table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
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
            Logger.getLogger(SimpleReplacementController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(SimpleReplacementController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SimpleReplacementController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                Logger.getLogger(SimpleReplacementController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return stringBuffer.toString();
    }

    private void addColumns(TableView table, Map<Integer, Character> map) {
        table.getColumns().removeAll(table.getColumns());
        map.forEach((key, value) -> {
            TableColumn<Integer, String> column = new TableColumn<>(String.valueOf(value));
            column.getColumns().add(new TableColumn<>(String.valueOf(key)));
            table.getColumns().add(column);
        });
        table.getItems().add(1);
    }

}
