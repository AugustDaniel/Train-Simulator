package guiapplication.schedulePlanner.scheduleview.components;

import data.Journey;
import guiapplication.schedulePlanner.View;
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

        TableColumn<Journey, Integer> platform = new TableColumn<>("Perron");
        platform.setCellValueFactory(e -> new SimpleIntegerProperty(e.getValue().getPlatform().getPlatformNumber()).asObject());

        TableColumn<Journey, Integer> wagons = new TableColumn<>("Wagons(Capaciteit)");
        wagons.setCellValueFactory(e -> new SimpleIntegerProperty(e.getValue().getTrain().getCapacity()).asObject());

        TableColumn<Journey, String> trainID = new TableColumn<>("TreinID");
        trainID.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getTrain().getTrainIDNumber()));

        TableColumn<Journey, String> machinist = new TableColumn<>("Machinist");
        machinist.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getMachinist().getName()));

        TableColumn<Journey, Integer> trainPopularity = new TableColumn<>("Reis populareit");
        trainPopularity.setCellValueFactory(e -> new SimpleIntegerProperty(e.getValue().getTrainPopularity()).asObject());

        TableColumn<Journey, Integer> arrival = new TableColumn<>("Aankomst");
        arrival.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));

        TableColumn<Journey, Integer> departure = new TableColumn<>("Vertrek");
        departure.setCellValueFactory(new PropertyValueFactory<>("departureTime"));

        table.getColumns().addAll(platform, trainID, machinist, trainPopularity, wagons, arrival, departure);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return table;
    }
}
