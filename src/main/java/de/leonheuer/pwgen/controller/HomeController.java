package de.leonheuer.pwgen.controller;

import de.leonheuer.pwgen.PasswordGen;
import de.leonheuer.pwgen.manager.DataManager;
import de.leonheuer.pwgen.util.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class HomeController {

    private final DataManager dm;
    public CheckBox cbCaps;
    public CheckBox cbSmall;
    public CheckBox cbNums;
    public CheckBox cbChars;
    public Slider sLength;
    public TextArea taOutput;
    public ProgressBar pbProgress;
    public Slider sAmount;
    public Label lTime;
    public CheckBox cbWindows;

    public HomeController() {
        dm = PasswordGen.getInstance().getDataManager();
    }

    @SuppressWarnings("unused")
    @FXML
    public void onGenerateClick(MouseEvent mouseEvent) {
        Util.generate(this);
    }

    @SuppressWarnings("unused")
    @FXML
    public void onCapsClick(MouseEvent mouseEvent) {
        dm.setCaps(cbCaps.isSelected());
        dm.save();
    }

    @SuppressWarnings("unused")
    @FXML
    public void onSmallClick(MouseEvent mouseEvent) {
        dm.setSmall(cbSmall.isSelected());
        dm.save();
    }

    @SuppressWarnings("unused")
    @FXML
    public void onNumsClick(MouseEvent mouseEvent) {
        dm.setNumbers(cbNums.isSelected());
        dm.save();
    }

    @SuppressWarnings("unused")
    @FXML
    public void onCharsClick(MouseEvent mouseEvent) {
        dm.setChars(cbChars.isSelected());
        dm.save();
    }

    @SuppressWarnings("unused")
    @FXML
    public void onWindowsClick(MouseEvent mouseEvent) {
        dm.setWindows(cbWindows.isSelected());
        dm.save();
        try {
            if (cbWindows.isSelected()) {
                    Util.addToStartup();
            } else {
                Util.removeFromStartup();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    @FXML
    public void onLengthReleased(MouseEvent mouseEvent) {
        dm.setLength((int) sLength.getValue());
        dm.save();
    }

    @SuppressWarnings("unused")
    @FXML
    public void onAmountReleased(MouseEvent mouseEvent) {
        dm.setAmount((int) sAmount.getValue());
        dm.save();
    }

    @SuppressWarnings("unused")
    @FXML
    public void onOutputClear(ActionEvent actionEvent) {
        taOutput.clear();
    }

    public void onStartup() {
        cbCaps.setSelected(dm.isCaps());
        cbSmall.setSelected(dm.isSmall());
        cbNums.setSelected(dm.isNumbers());
        cbChars.setSelected(dm.isChars());
        cbWindows.setSelected(dm.isWindows());
        sLength.setValue(dm.getLength());
        sAmount.setValue(dm.getAmount());
    }
}
