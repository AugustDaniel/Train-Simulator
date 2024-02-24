package guiapplication.scheduleview.components;

import data.ScheduleBuilder;
import guiapplication.View;
import guiapplication.scheduleview.popups.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import data.ScheduleSubject;

public class ScheduleBuilderView implements View {

    private ScheduleSubject subject;
    private ScheduleBuilder builder;
    private MenuMode menuToggle;

    enum MenuMode {CREATE, DELETE}

    public ScheduleBuilderView(ScheduleSubject subject, ScheduleBuilder builder) {

    }

    @Override
    public Node getNode() {
        return null;
    }

    private Node getButtons() {
        VBox box = new VBox();

//        switch (menuToggle) {
//            case CREATE: box = getAddButtons(); break;
//            case DELETE: box = getDeleteButtons(); break;
//        }
//
//        Button switchModeButton = new Button("Verander modus");
//        switchModeButton.setOnAction(event -> {
//            switch (menuToggle) {
//                case CREATE: menuToggle = ScheduleView.MenuMode.DELETE; break;
//                case DELETE: menuToggle = ScheduleView.MenuMode.CREATE; break;
//            }
//            this.returnToView();
//        });
//
//        box.getChildren().addAll(switchModeButton);
//        box.setPadding(new Insets(10));
//        box.setSpacing(10);
//        box.setAlignment(Pos.BASELINE_LEFT);
        return box;
    }

//    private VBox getAddButtons() {
//        VBox box = new VBox(
//                getButton("Creëer reis", new CreateJourneyPopup(this, this.schedule).getNode()),
//                getButton("Creëer trein", new CreateTrainPopup(this, this.schedule).getNode()),
//                getButton("Creëer wagon", new CreateWagonPopup(this, this.schedule).getNode()),
//                getButton("Creëer perron", new CreatePlatformPopup(this, this.schedule).getNode())
//        );
//        return box;
//    }
//
//    private VBox getDeleteButtons() {
//        VBox box = new VBox(
//                getButton("Verwijder reis", new DeleteJourneyPopup(this, this.schedule).getNode()),
//                getButton("Verwijder trein", new DeleteTrainPopup(this, this.schedule).getNode()),
//                getButton("Verwijder wagon", new DeleteWagonPopup(this, this.schedule).getNode()),
//                getButton("Verwijder perron", new DeletePlatformPopup(this, this.schedule).getNode())
//        );
//        return box;
//    }
//
//    private Node getButton(String text, Node popup) {
//        Button button = new Button(text);
//        button.setOnAction(e -> {
//            returnToView();
//            this.mainPane.setBottom(popup);
//        });
//
//        return button;
//    }
}
