package guiapplication.scheduleview.components;

import data.Schedule;
import guiapplication.View;
import javafx.scene.Node;
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
            IOHelper.saveObject(this.subject.getSchedule(),
                    IOHelper.getFileFromChooser("Selecteer gewenste locatie"))
        );

        Button loadScheduleButton = new Button("Laad planning");
        loadScheduleButton.setOnAction(e ->
            this.subject.setSchedule((Schedule) IOHelper.loadObject(IOHelper.getFileFromChooser("Selecteer planning")))
        );

        return new VBox(saveScheduleButton,loadScheduleButton);
    }
}
