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
import javafx.scene.control.TextField;
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

import java.security.*;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import java.util.Formatter;

public class HmacController {
	
	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    private static final String alphabetChars = "abcdefghijklmnopqrstuvwxyz";

    private final FileChooser fileChooser = new FileChooser();

    @FXML
    private TextArea initialText;

    @FXML
    private TextArea hmac;	

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
    private TextField keyInput;


    @FXML
    void generateHmac(MouseEvent event) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
        this.hmac.setText(calculateRFC2104HMAC(this.initialText.getText(), this.keyInput.getText()));
    }
	
	@FXML
    void checkHmac(MouseEvent event) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
        String hmac = calculateRFC2104HMAC(this.initialText.getText(), this.keyInput.getText());
		if (hmac.equals(this.hmac.getText())) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setHeaderText("Result of HMAC check:");
			alert.setContentText("HMAC is correct");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Result of HMAC check:");
			alert.setContentText("HMAC is wrong");
			
			alert.showAndWait();
		}
    }
	

    @FXML
    void initialize() {
        
    }
	
	private static String calculateRFC2104HMAC(String data, String key)
		throws SignatureException, NoSuchAlgorithmException, InvalidKeyException
	{
		SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
		Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
		mac.init(signingKey);
		return toHexString(mac.doFinal(data.getBytes()));
	}
	
	private static String toHexString(byte[] bytes) {
		Formatter formatter = new Formatter();
		
		for (byte b : bytes) {
			formatter.format("%02x", b);
		}

		return formatter.toString();
	}


}
