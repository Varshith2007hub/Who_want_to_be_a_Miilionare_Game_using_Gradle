package million_dollar_showdown.view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class QuestionPanel extends JPanel {
    public static JLabel questionLabel;
    public static JLabel timerLabel;
    public static JLabel gameOverLabel;
    private JLabel scoreLabel;
    private JLabel messageLabel;

    public QuestionPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        questionLabel = new JLabel();
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        Border questionBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
        questionLabel.setBorder(questionBorder);
        questionLabel.setFont(new Font("Serif", Font.PLAIN, 25));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(questionLabel, gbc);

        timerLabel = new JLabel("Time left: 0 seconds");
        timerLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(timerLabel, gbc);

        gameOverLabel = new JLabel("");
        gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Serif", Font.BOLD, 30));
        add(gameOverLabel, gbc);

        scoreLabel = new JLabel("");
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(scoreLabel, gbc);

        messageLabel = new JLabel();
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(messageLabel);
    }

    public void displayMessage(String message) {
        GridBagConstraints gbcMessage = new GridBagConstraints();
        gbcMessage.gridx = 0;
        gbcMessage.gridy = 3;
        gbcMessage.insets = new Insets(20, 0, 0, 0);
        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(messageLabel, gbcMessage);
    }

    public void setQuestion(String question) {
        questionLabel.setText(question);
    }

    public void setTimerLabel(String timerText) {
        timerLabel.setText(timerText);
    }

    public void setGameOverLabel(String gameOverText) {
        gameOverLabel.setText(gameOverText);
    }

    public void updateScoreLabel(int score) {
        scoreLabel.setText("Your Final Score: $" + score);
    }
}