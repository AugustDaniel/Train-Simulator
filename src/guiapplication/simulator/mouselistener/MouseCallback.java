package guiapplication.simulator.mouselistener;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public interface MouseCallback {

    default void onMousePressed(MouseEvent e) {
    }

    default void onMouseReleased(MouseEvent e) {
    }

    default void onMouseDragged(MouseEvent e) {
    }

    default void onMouseScrolled(ScrollEvent e) {
    }
}
