package guiApplication.scheduleView;

import guiApplication.ReturnableView;
import guiApplication.View;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.awt.*;

public class AddJourneyView implements View {

    private ReturnableView mainView;

    public AddJourneyView(ReturnableView mainView) {
        this.mainView = mainView;
    }

    @Override
    public Node getNode() {
        BorderPane pane = new BorderPane();
        Button button = new Button("change");

        button.setOnAction(e -> {
            this.mainView.returnToView();
        });

        pane.setCenter(button);

        return pane;
    }
}
