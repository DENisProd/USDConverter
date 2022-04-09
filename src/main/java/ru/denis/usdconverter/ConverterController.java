package ru.denis.usdconverter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.json.JSONException;
import ru.denis.usdconverter.exceptions.NullArgumentException;
import ru.denis.usdconverter.exceptions.WrongParsingException;

import java.io.IOException;
import java.net.UnknownHostException;

public class ConverterController {

    @FXML
    private Label result;

    @FXML
    private TextField textField;

    @FXML
    public void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) calculate();
    }

    @FXML
    protected void onCalculateButtonClick() {
        calculate();
    }

    private void calculate() {
        float dollarCurency = 0;
        float dollarAmount = parseTextField();

        try {
            dollarCurency = ValuteParser.getUSD();
            result.setText(dollarCurency + " RUB");
        } catch (IOException e) {
            if (e instanceof UnknownHostException)
                result.setText("Ошибка интернет-соединения");
            else
                result.setText("Ошибка сервера");
        } catch (JSONException ex) {
            result.setText("Ошибка приложения");
        } catch (WrongParsingException exception) {
            result.setText(exception.getMessage());
        }

        float resultInRUB = dollarCurency * dollarAmount;
        result.setText(resultInRUB + " RUB \n(При курсе USD: " + dollarCurency + " )");
    }

    private float parseTextField() {
        String textFromField = textField.getText();
        float dollarAmount = 0;

        try {
            dollarAmount = Float.parseFloat(textFromField.replace(',','.'));
        } catch (NumberFormatException e) {
            result.setText("Введите количество USD");
        }

        return dollarAmount;
    }

}
