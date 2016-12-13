package gui;

import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by miwas on 11.12.16.
 */
public class AdditionalOptionsForOneDimAutomaton extends AdditionalOptions{
    Slider slider;

    public AdditionalOptionsForOneDimAutomaton(Controller context) {
        super(context);
    }

    @FXML
    protected void initialize() {
        slider = new Slider();
        slider.setValue(30);
        slider.setMin(0);
        slider.setMax(255);
        slider.applyCss();

        Label title = new Label("Zasada: ");
        Label rule = new Label(String.valueOf((int)slider.getValue()));
        HBox labelBox = new HBox(title, rule);
        VBox root = new VBox(labelBox, slider);

        slider.valueProperty().addListener((arg0, arg1, arg2) -> rule.setText(String.valueOf(getRule())));
        panel.getChildren().add(title);
        panel.requestLayout();
    }

    public int getRule(){
        return (int) slider.getValue();
    }
}
