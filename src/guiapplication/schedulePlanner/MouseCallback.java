package guiapplication.schedulePlanner;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public interface MouseCallback {

    void onMousePressed(MouseEvent e);

    void onMouseReleased(MouseEvent e);

    void onMouseDragged(MouseEvent e);

    void onMouseScrolled(ScrollEvent e);
}
