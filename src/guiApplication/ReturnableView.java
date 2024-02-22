package guiApplication;

import javafx.scene.Node;

abstract public class ReturnableView implements View {

    @Override
    abstract public Node getNode();

    abstract public void returnToView();
}
