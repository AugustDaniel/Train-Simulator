package guiApplication.scheduleView;

import guiApplication.View;
import javafx.scene.Node;
import javafx.scene.control.Button;
import java.awt.*;

public class AddJourneyView implements View {

    private View mainView;

    public AddJourneyView() {
//        this.mainView = mainView;
    }

    @Override
    public Node getNode() {
        Button button = new Button("change");

        button.setOnAction(e -> {

        });
        return null;
    }
}
