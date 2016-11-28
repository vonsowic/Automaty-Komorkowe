package gui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by miwas on 27.11.16.
 */
public class Controller implements Initializable{

    @FXML private Slider timeLoopSlider;
    @FXML private Slider widthSlider;
    @FXML private Slider heightSlider;


    @FXML private Button oneMoveButton;
    @FXML private Button startButton;

    @FXML private GridPane simulationWindow;

    @FXML final ToggleGroup choosenAutomatonGroup;
    @FXML private RadioButton gameOfLifeRButton;
    @FXML private RadioButton langtonAntRButton;
    @FXML private RadioButton wireWorldRButton;

    private int cellSize;

    public Controller() {
        choosenAutomatonGroup = new ToggleGroup();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameOfLifeRButton.setToggleGroup(choosenAutomatonGroup);
        langtonAntRButton.setToggleGroup(choosenAutomatonGroup);
        wireWorldRButton.setToggleGroup(choosenAutomatonGroup);

    }

    //////////////////////////// Uchwyty do kontrolek //////////////////////////////////////////////////////////////////
    /**
     * Uchwyt do przycisku "Jeden ruch"
     * @param actionEvent
     */
    public void oneMove(ActionEvent actionEvent) {
    }

    /**
     * Uchwyt do przycisku "Start"
     * @param actionEvent
     */
    public void startSimulation(ActionEvent actionEvent) {

    }


    public void setWidth(MouseEvent mouseEvent) {

        if ( widthSlider.getValue() > simulationWindow.getColumnConstraints().size()){
            this.addColumns(simulationWindow, (int) widthSlider.getValue());
        }

    }
    ///////////////////////////////// Metody prywatne //////////////////////////////////////////////////////////////////

    private void addRows(GridPane pane, int requestedNumber){
        int numberOfRows = pane.getRowConstraints().size()+1;
        for ( int i = pane.getColumnConstraints().size() ; i<=requestedNumber; i++){
            Circle cell = new Circle(cellSize);
            cell.setFill(Color.GHOSTWHITE);
            pane.add(cell, i, numberOfRows);

        }
    }

    private void addColumns(GridPane pane, int requestedNumber){
        int numberOfColumns = pane.getColumnConstraints().size()+1;
        for ( int i = pane.getRowConstraints().size() ; i<=requestedNumber; i++){
            Circle cell = new Circle(cellSize);
            cell.setFill(Color.GHOSTWHITE);
            pane.add(cell, numberOfColumns, i);

        }
    }

    private int getTimeLoop(){
        return (int) timeLoopSlider.getValue();
    }
}
