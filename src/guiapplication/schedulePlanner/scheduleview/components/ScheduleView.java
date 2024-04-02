package guiapplication.schedulePlanner.scheduleview.components;

import guiapplication.schedulePlanner.PopupView;
import guiapplication.schedulePlanner.ReturnableView;
import guiapplication.schedulePlanner.View;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import data.ScheduleSubject;
import javafx.scene.layout.VBox;
import util.Observer;

public class ScheduleView extends ReturnableView implements Observer {

    private ScheduleSubject subject;
    private final BorderPane mainPane;
    private PopupView popup;
    private View builderView;
    private View fileView;
    private View tableView;

    public ScheduleView(ScheduleSubject subject) {
        this.subject = subject;
        this.subject.attach(this);
        this.mainPane = new BorderPane();
        this.popup = null;
        this.builderView = new ScheduleBuilderView(this.subject, this);
        this.fileView = new ScheduleFileView(this.subject);
        this.tableView = new ScheduleTableView(this.subject);
    }

    public void setPopup(PopupView popup) {
        this.popup = popup;
    }

    @Override
    public void update() {
        returnToView();
    }

    @Override
    public Node getNode() {
        returnToView();
        return mainPane;
    }

    @Override
    public void returnToView() {
        mainPane.getChildren().clear();
        mainPane.setLeft(getButtons());
        mainPane.setCenter(tableView.getNode());

        if (popup != null) {
            mainPane.setBottom(popup.getNode());
        } else {
            mainPane.setBottom(null);
        }
    }

    public Node getButtons() {
        Node builderButtons = builderView.getNode();
        Node fileButtons = fileView.getNode();

        VBox box = new VBox(builderButtons, fileButtons);
        return box;
    }
}
