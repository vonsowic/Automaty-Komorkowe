package gui;

import javafx.scene.layout.VBox;

/**
 * Created by miwas on 08.12.16.
 */
public abstract class AdditionalOptions {

    VBox panel;
    Controller context;

    public AdditionalOptions(Controller context) {
        this.context = context;
        this.panel = context.getAdditionalOptionsBox();
        clearBox();
    }

    public void clearBox(){
        panel.getChildren().removeAll(panel);
    }


}
