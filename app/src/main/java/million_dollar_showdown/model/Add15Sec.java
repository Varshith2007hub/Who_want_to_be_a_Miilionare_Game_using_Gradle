package million_dollar_showdown.model;

import million_dollar_showdown.LifelineAction;

public class Add15Sec implements LifelineAction {
    private TimerCallBack timerCallback;

    public Add15Sec(TimerCallBack timerCallback) {
        this.timerCallback = timerCallback;
    }

    @Override
    public void performAction() {
        int additionalSeconds = 15;
        timerCallback.onAdd15SecAction(additionalSeconds);
    }
}
