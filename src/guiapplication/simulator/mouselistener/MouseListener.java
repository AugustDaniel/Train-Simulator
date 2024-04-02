package guiapplication.simulator.mouselistener;

import javafx.scene.Node;

import java.util.LinkedList;
import java.util.List;

public class MouseListener {

    private List<MouseCallback> callbacks;

    public MouseListener(Node node) {
        this.callbacks = new LinkedList<>();

        node.setOnMousePressed(entry -> {
            for (MouseCallback callback : callbacks) {
                callback.onMousePressed(entry);
            }
        });

        node.setOnMouseReleased(entry -> {
            for (MouseCallback callback : callbacks) {
                callback.onMouseReleased(entry);
            }
        });

        node.setOnMouseDragged(entry -> {
            for (MouseCallback callback : callbacks) {
                callback.onMouseDragged(entry);
            }
        });

        node.setOnScroll(entry -> {
            for (MouseCallback callback : callbacks) {
                callback.onMouseScrolled(entry);
            }
        });
    }

    public void addCallback(MouseCallback callback) {
        callbacks.add(callback);
    }
}
