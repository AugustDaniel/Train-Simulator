package guiApplication.scheduleView;


import data.Journey;
import data.Schedule;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ScheduleView {

    private Schedule schedule;

    public ScheduleView(Schedule schedule) {
        this.schedule = schedule;
    }

    public Node getNode() {
        ObservableList<Journey> journeys = new ObservableListBase<Journey>() {
            @Override
            public Journey get(int index) {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }
        };


        TableView<Journey> table = new TableView<Journey>();
        table.setItems(journeys);
        TableColumn<Journey, Integer> peron = new TableColumn<Journey, Integer> ("Platform");
        peron.setCellValueFactory(new PropertyValueFactory<Journey, Integer>("platform"));

        TableColumn<Journey, Integer> wagons = new TableColumn<Journey, Integer> ("Wagons(Capaciteit)");
        wagons.setCellValueFactory(new PropertyValueFactory<Journey, Integer>("platform"));

        TableColumn<Journey, Integer> train = new TableColumn<Journey, Integer> ("Trein");
        train.setCellValueFactory(new PropertyValueFactory<Journey, Integer>("platform"));

        TableColumn<Journey, Integer> arrival = new TableColumn<Journey, Integer> ("Aankomst");
        arrival.setCellValueFactory(new PropertyValueFactory<Journey, Integer>("platform"));

        TableColumn<Journey, Integer> departure = new TableColumn<Journey, Integer> ("Vertrek");
        departure.setCellValueFactory(new PropertyValueFactory<Journey, Integer>("platform"));

        table.getColumns().addAll(peron, train, wagons, arrival, departure);

        return table; //todo change view to right content
    }
}
