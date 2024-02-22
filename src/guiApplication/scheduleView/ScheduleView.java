package guiApplication.scheduleView;


import data.Journey;
import data.Schedule;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ScheduleView {

    private Schedule schedule;

    public ScheduleView(Schedule schedule) {
        this.schedule = schedule;
    }

    public Node getNode() {
        TableView<Journey> table = new TableView<Journey>();
        TableColumn<Journey, String> peron = new TableColumn<Journey, String> ("Platform");
        TableColumn<Journey, String> wagons = new TableColumn<Journey, String> ("Wagons(Capaciteit)");
        TableColumn<Journey, String> train = new TableColumn<Journey, String> ("Trein");
        TableColumn<Journey, String> arrival = new TableColumn<Journey, String> ("Aankomst");
        TableColumn<Journey, String> departure = new TableColumn<Journey, String> ("Vertrek");

        table.getColumns().addAll(peron, train, wagons, arrival, departure);

        return table; //todo change view to right content
    }
}
