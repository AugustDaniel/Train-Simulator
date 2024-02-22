package guiApplication;

abstract public class PopupView implements View{

    private ReturnableView mainView;

    public PopupView(ReturnableView mainView) {
        this.mainView = mainView;
    }

    public ReturnableView getMainView() {
        return mainView;
    }

    public void callMainView() {
        mainView.returnToView();
    }
}
