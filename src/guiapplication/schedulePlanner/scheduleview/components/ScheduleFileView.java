package guiapplication.schedulePlanner.scheduleview.components;

import data.Schedule;
import guiapplication.schedulePlanner.View;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import util.IOHelper;
import data.ScheduleSubject;

public class ScheduleFileView implements View {

    private ScheduleSubject subject;

    public ScheduleFileView(ScheduleSubject subject) {
        this.subject = subject;
    }

    @Override
    public Node getNode() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        Button saveScheduleButton = new Button("Sla planning op");
        saveScheduleButton.setOnAction(e ->
                {
                    try {
                        IOHelper.saveObject(subject.getSchedule(),
                                IOHelper.getFileFromChooser("Selecteer gewenste locatie"));
                    } catch (Exception ex) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Ongeldige locatie");
                        alert.showAndWait();
                    }
                }
        );
        Button loadScheduleButton = new Button("Laad planning");
        loadScheduleButton.setOnAction(e ->
                {
                    try {
                        subject.setSchedule((Schedule) IOHelper.readObject(IOHelper.getFileFromChooser("Selecteer planning")));
                    } catch (Exception ex) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Ongeldige planning");
                        alert.showAndWait();
                    }
                }
        );
        VBox box = new VBox(saveScheduleButton,loadScheduleButton);
        box.setPadding(new Insets(20,10,10,10));
        box.setSpacing(10);
        box.setAlignment(Pos.BASELINE_LEFT);
        return box;
    }
}
