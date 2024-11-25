package million_dollar_showdown.view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnswerPanel extends JPanel {
    public static JButton[] answerButtons;
    private GameView gameView;

    public AnswerPanel(ActionListener actionListener, GameView gameView) {
        this.gameView = gameView;
        setLayout(new GridLayout(3, 2));

        answerButtons = new JButton[4];

        for (int i = 0; i < 4; i++) {
            answerButtons[i] = new JButton();
            answerButtons[i].addActionListener(actionListener);
            Border answerBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
            answerButtons[i].setBorder(answerBorder);
            answerButtons[i].setFont(new Font("Serif", Font.PLAIN, 20));
            answerButtons[i].setBackground(new Color(173, 216, 230));
            add(answerButtons[i]);
        }

        JPanel quitPanel = new JPanel();
        quitPanel.setLayout(new BorderLayout());
        JButton quitButton = new JButton("QUIT GAME");
        quitButton.setPreferredSize(new Dimension(150, 30));
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (GameView.countdownTimer != null) {
                    GameView.countdownTimer.timer.stop();
                }
                gameView.scheduleGameOverDisplay(gameView.controllerInterface, GameView.getPrizeForCurrentStage());
            }
        });
        quitPanel.add(quitButton, BorderLayout.EAST);
        JPanel spacePanel1 = new JPanel();
        add(spacePanel1);
        add(quitPanel);
    }

    public void setCountdownTimer(CountDownTimer countdownTimer) {
        GameView.countdownTimer = countdownTimer;
    }

    public void setAnswerOptions(ArrayList<String> options) {
        for (int i = 0; i < 4; i++) {
            answerButtons[i].setText(options.get(i));
        }
    }

    public void disableAnswerButton(int index) {
        answerButtons[index].setEnabled(false);
        answerButtons[index].setBackground(Color.GRAY);
    }

    public void resetAnswerButtonBackground() {
        for (JButton button : answerButtons) {
            button.setBackground(new Color(173, 216, 230));
        }
    }
}