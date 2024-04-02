package guiapplication.schedulePlanner.Simulator.mouselistener;

import javafx.scene.Node;

import java.util.LinkedList;
import java.util.List;

public class MouseListener {

    private List<MouseCallback> callbacks;

    public MouseListener(Node node) {
        this.callbacks = new LinkedList<>();

        node.setOnMousePressed(e -> {
            for (MouseCallback callback : callbacks) {
                callback.onMousePressed(e);
            }
        });

        node.setOnMouseReleased(e -> {
            for (MouseCallback callback : callbacks) {
                callback.onMouseReleased(e);
            }
        });

        node.setOnMouseDragged(e -> {
            for (MouseCallback callback : callbacks) {
                callback.onMouseDragged(e);
            }
        });

        node.setOnScroll(e -> {
            for (MouseCallback callback : callbacks) {
                callback.onMouseScrolled(e);
            }
        });
    }

    public void addCallback(MouseCallback callback) {
        callbacks.add(callback);
    }
}
