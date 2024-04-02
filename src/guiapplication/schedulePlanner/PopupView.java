package guiapplication.schedulePlanner;

abstract public class PopupView implements View {

    private ReturnableView mainView;
    private boolean closePopup;

    public PopupView(ReturnableView mainView) {
        this.mainView = mainView;
        this.closePopup = false;
    }

    public void setClosePopup(boolean state) {
        this.closePopup = state;
    }

    public ReturnableView getMainView() {
        return mainView;
    }

    public void callMainView() {
        if (closePopup) {
            mainView.setPopup(null);
        }

        mainView.returnToView();
        setClosePopup(false);
    }
}
