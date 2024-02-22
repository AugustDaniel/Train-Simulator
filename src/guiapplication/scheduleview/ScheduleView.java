package guiapplication.scheduleview;

import data.Journey;
import data.Schedule;
import guiapplication.ReturnableView;
import guiapplication.scheduleview.popups.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    private MenuMode menuToggle;

    enum MenuMode {CREATE, DELETE}

    public ScheduleView(Schedule schedule) {
        this.schedule = schedule;
        this.mainPane = new BorderPane();
        this.menuToggle = MenuMode.CREATE;
    }

    @Override
    public Node getNode() {
        this.returnToView();
        return this.mainPane;
    }

    @Override
    public void returnToView() {
        this.mainPane.getChildren().clear();
        this.mainPane.setLeft(getButtons());
        this.mainPane.setCenter(getTableView());
    }

    private Node getButtons() {
        VBox box = new VBox();

        switch (menuToggle) {
            case CREATE: box = getAddButtons(); break;
            case DELETE: box = getDeleteButtons(); break;
        }

        Button switchModeButton = new Button("Verander modus");
        switchModeButton.setOnAction(event -> {
            switch (menuToggle) {
                case CREATE: menuToggle = MenuMode.DELETE; break;
                case DELETE: menuToggle = MenuMode.CREATE; break;
            }
            this.returnToView();
        });

        box.getChildren().add(switchModeButton);
        box.setPadding(new Insets(10));
        box.setSpacing(10);
        box.setAlignment(Pos.BASELINE_LEFT);
        return box;
    }

    private VBox getAddButtons() {
        VBox box = new VBox(
                getButton("Creëer reis", new CreateJourneyPopup(this, this.schedule).getNode()),
                getButton("Creëer trein", new CreateTrainPopup(this, this.schedule).getNode()),
                getButton("Creëer wagon", new CreateWagonPopup(this, this.schedule).getNode()),
                getButton("Creëer perron", new CreatePlatformPopup(this, this.schedule).getNode())
        );
        return box;
    }

    private VBox getDeleteButtons() {
        VBox box = new VBox(
                getButton("Verwijder reis", new DeleteJourneyPopup(this, this.schedule).getNode()),
                getButton("Verwijder trein", new DeleteTrainPopup(this, this.schedule).getNode()),
                getButton("Verwijder wagon", new DeleteWagonPopup(this, this.schedule).getNode()),
                getButton("Verwijder perron", new DeletePlatformPopup(this, this.schedule).getNode())
        );
        return box;
    }

    private Node getButton(String text, Node popup) {
        Button button = new Button(text);
        button.setOnAction(e -> {
            this.mainPane.setBottom(popup);
        });

        return button;
    }

    private Node getTableView() {
        ObservableList<Journey> journeys = FXCollections.observableList(this.schedule.getJourneyList());

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
