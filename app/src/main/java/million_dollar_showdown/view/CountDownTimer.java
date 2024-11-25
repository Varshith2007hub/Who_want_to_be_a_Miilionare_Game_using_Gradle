package million_dollar_showdown.view;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CountDownTimer implements ActionListener {
    private GameView prototype;
    Timer timer;

    CountDownTimer(GameView prototype, int delay) {
        this.prototype = prototype;
        this.timer = new Timer(delay, this);
    }

    public void start() {
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (GameView.secondsLeft > 0) {
            GameView.secondsLeft--;
            GameView.updateTimerLabel();
        } else {
            prototype.scheduleGameOverDisplay(prototype.controllerInterface, GameView.getPrizeForCurrentStage());
            timer.stop();
        }
    }
}