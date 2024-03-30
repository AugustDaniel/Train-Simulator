package guiapplication.schedulePlanner.Simulator.mouselistener;

import javafx.scene.canvas.Canvas;

import java.util.LinkedList;
import java.util.List;

public class MouseListener {

    private List<MouseCallback> callbacks;

    public MouseListener(Canvas canvas) {
        this.callbacks = new LinkedList<>();

        canvas.setOnMousePressed(e -> {
            for (MouseCallback callback : callbacks) {
                callback.onMousePressed(e);
            }
        });

        canvas.setOnMouseReleased(e -> {
            for (MouseCallback callback : callbacks) {
                callback.onMouseReleased(e);
            }
        });

        canvas.setOnMouseDragged(e -> {
            for (MouseCallback callback : callbacks) {
                callback.onMouseDragged(e);
            }
        });

        canvas.setOnScroll(e -> {
            for (MouseCallback callback : callbacks) {
                callback.onMouseScrolled(e);
            }
        });
    }

    public void addCallback(MouseCallback callback) {
        this.callbacks.add(callback);
    }
}
