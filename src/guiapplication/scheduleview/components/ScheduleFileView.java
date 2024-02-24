package guiapplication.scheduleview.components;

import data.Schedule;
import guiapplication.View;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import util.IOHelper;

import java.io.File;

public class ScheduleFileView implements View {



    @Override
    public Node getNode() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        Button saveScheduleButton = new Button("Sla planning op");
        saveScheduleButton.setOnAction(e -> {
            IOHelper.saveObject(this.schedule, selectedFile);
        });

        Button loadScheduleButton = new Button("Laad planning");
        loadScheduleButton.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(new Stage());
            this.schedule = (Schedule) IOHelper.loadObject(selectedFile);
        });
        return null;
    }
}
