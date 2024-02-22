package guiApplication.scheduleView;

import data.Journey;
import data.Schedule;
import guiApplication.View;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ScheduleView implements View {

    private Schedule schedule;
    private final BorderPane mainPane;


    public ScheduleView(Schedule schedule) {
        this.schedule = schedule;
        this.mainPane = new BorderPane();
//        this.addJourneyView = new AddJourneyView();
    }

    @Override
    public Node getNode() {
        this.mainPane.setCenter(getTableView());
        this.mainPane.setLeft(getButtons());
        return this.mainPane;
    }

    private Node getButtons() {
        Button addJourneyButton = new Button("Voeg reis toe");
        addJourneyButton.setOnAction(e -> {
//            this.mainPane.setLeft(this.addJourneyView.getNode());
        });

        return new VBox(addJourneyButton);
    }

    public Node getTableView() {
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

        TableView<Journey> table = new TableView<>();
        table.setItems(journeys);

        TableColumn<Journey, Integer> peron = new TableColumn<> ("Platform");
        peron.setCellValueFactory(new PropertyValueFactory<>("platform"));

        TableColumn<Journey, Integer> wagons = new TableColumn<> ("Wagons(Capaciteit)");
        wagons.setCellValueFactory(new PropertyValueFactory<>("platform"));

        TableColumn<Journey, Integer> train = new TableColumn<> ("Trein");
        train.setCellValueFactory(new PropertyValueFactory<>("platform"));

        TableColumn<Journey, Integer> arrival = new TableColumn<> ("Aankomst");
        arrival.setCellValueFactory(new PropertyValueFactory<>("platform"));

        TableColumn<Journey, Integer> departure = new TableColumn<> ("Vertrek");
        departure.setCellValueFactory(new PropertyValueFactory<>("platform"));

        table.getColumns().addAll(peron, train, wagons, arrival, departure);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return table;
    }
}
