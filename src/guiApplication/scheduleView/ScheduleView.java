package guiApplication.scheduleView;

import data.Journey;
import data.Schedule;
import guiApplication.ReturnableView;
import guiApplication.View;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ScheduleView extends ReturnableView {

    private Schedule schedule;
    private final BorderPane mainPane;
    private final View addJourneyPopup;

    public ScheduleView(Schedule schedule) {
        this.schedule = schedule;
        this.mainPane = new BorderPane();
        this.addJourneyPopup = new AddJourneyPopup(this, this.schedule);
    }

    @Override
    public Node getNode() {
        this.mainPane.setCenter(getTableView());
        this.mainPane.setLeft(getButtons());
        return this.mainPane;
    }

    @Override
    public void returnToView() {
        this.mainPane.getChildren().clear();
        this.mainPane.setLeft(getButtons());
        this.mainPane.setCenter(getTableView());
    }

    private Node getButtons() {
        Button addJourneyButton = new Button("Voeg reis toe");

        addJourneyButton.setOnAction(e -> {
            this.mainPane.setBottom(this.addJourneyPopup.getNode());
        });

        VBox box = new VBox(addJourneyButton);
        box.setPadding(new Insets(10,10,10,10));

        return box; //todo add more buttons
    }

    private Node getTableView() {
        ObservableList<Journey> journeys = FXCollections.observableList(this.schedule.getJourneyList());

        TableView<Journey> table = new TableView<>();
        table.setItems(journeys);

        TableColumn<Journey, Integer> peron = new TableColumn<> ("Platform");
        peron.setCellValueFactory(e -> new SimpleIntegerProperty(e.getValue().getPlatform().getPlatformNumber()).asObject());

        TableColumn<Journey, Integer> wagons = new TableColumn<> ("Wagons(Capaciteit)");
        wagons.setCellValueFactory(e -> new SimpleIntegerProperty(e.getValue().getTrain().getCapacity()).asObject());

        TableColumn<Journey, String> train = new TableColumn<> ("Trein");
        train.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getTrain().getTrainIDNumber()));

        TableColumn<Journey, Integer> arrival = new TableColumn<> ("Aankomst");
        arrival.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));

        TableColumn<Journey, Integer> departure = new TableColumn<> ("Vertrek");
        departure.setCellValueFactory(new PropertyValueFactory<>("departureTime"));

        table.getColumns().addAll(peron, train, wagons, arrival, departure);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return table;
    }
}
