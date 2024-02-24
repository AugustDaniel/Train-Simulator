package guiapplication;

abstract public class ReturnableView implements View {

    private PopupView popup;

    abstract public void returnToView();

    public void setPopup(PopupView popup) {
        this.popup = popup;
    }
}
