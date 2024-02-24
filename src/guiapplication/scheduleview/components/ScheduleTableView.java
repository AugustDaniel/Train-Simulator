package guiapplication.scheduleview.components;

import data.Journey;
import guiapplication.View;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import data.ScheduleSubject;

public class ScheduleTableView implements View {

    private ScheduleSubject subject;

    public ScheduleTableView(ScheduleSubject subject) {
        this.subject = subject;
    }

    @Override
    public Node getNode() {
        ObservableList<Journey> journeys = FXCollections.observableList(subject.getSchedule().getJourneyList());

        TableView<Journey> table = new TableView<>();
        table.setItems(journeys);

        TableColumn<Journey, Integer> peron = new TableColumn<>("Platform");
        peron.setCellValueFactory(e -> new SimpleIntegerProperty(e.getValue().getPlatform().getPlatformNumber()).asObject());

        TableColumn<Journey, Integer> wagons = new TableColumn<>("Wagons(Capaciteit)");
        wagons.setCellValueFactory(e -> new SimpleIntegerProperty(e.getValue().getTrain().getCapacity()).asObject());

        TableColumn<Journey, String> train = new TableColumn<>("Trein");
        train.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getTrain().getTrainIDNumber()));

        TableColumn<Journey, Integer> arrival = new TableColumn<>("Aankomst");
        arrival.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));

        TableColumn<Journey, Integer> departure = new TableColumn<>("Vertrek");
        departure.setCellValueFactory(new PropertyValueFactory<>("departureTime"));

        table.getColumns().addAll(peron, train, wagons, arrival, departure);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return table;
    }
}
