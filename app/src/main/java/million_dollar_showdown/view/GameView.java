package million_dollar_showdown.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;

import million_dollar_showdown.model.*;
import million_dollar_showdown.ControllerInterface;
import million_dollar_showdown.GameObserver;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class GameView implements ActionListener, TimerCallBack, FiftyFiftyCallback, QuestionCallback, GameObserver {
    private JFrame frame;
    private JPanel mainPanel, lifelinesWrapperPanel;
    private JButton startButton;
    private JLabel gameNameLabel;
    public static int secondsLeft;
    public static int stage = 1;
    private javax.swing.Timer delayTimer;
    ControllerInterface controllerInterface;
    static CountDownTimer countdownTimer;
    static GameModel questionSelection;
    TimerCallBack timerCallBack;
    FiftyFiftyCallback fiftyCallback;
    private static ScoreAndLabelPanel scoreAndLabelPanel;
    private static QuestionPanel questionPanel;
    private static LifelinesPanel lifelinesPanel;
    private static AnswerPanel answerPanel;
    private static FlowChartPanel flowChartPanel;
    private static final int ANIMATION_DELAY = 100;

    public GameView(GameModel questionSelection, ControllerInterface controllerInterface) {
        this.controllerInterface = controllerInterface;
        GameView.questionSelection = questionSelection;
        questionSelection.register(this);
        countdownTimer = new CountDownTimer(this, 1000);
        frame = new JFrame("Million Dollar Showdown");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Score and Label panel
        scoreAndLabelPanel = new ScoreAndLabelPanel();
        mainPanel.add(scoreAndLabelPanel, BorderLayout.NORTH);

        // Question panel
        questionPanel = new QuestionPanel();
        mainPanel.add(questionPanel, BorderLayout.CENTER);

        // Answer panel
        answerPanel = new AnswerPanel(this, this);
        answerPanel.setCountdownTimer(countdownTimer); // to stop the timer after clicking quit button
        mainPanel.add(answerPanel, BorderLayout.SOUTH);

        EmptyBorder spaceAboveFlowChart = new EmptyBorder(200, 0, 0, 0);
        EmptyBorder spaceAboveLifelines = new EmptyBorder(200, 0, 0, 0);

        // Lifelines panel
        lifelinesPanel = new LifelinesPanel(this);
        lifelinesWrapperPanel = new JPanel();
        lifelinesWrapperPanel.setLayout(new BorderLayout());
        lifelinesWrapperPanel.setBorder(spaceAboveLifelines);
        lifelinesWrapperPanel.add(lifelinesPanel, BorderLayout.NORTH);

        // Flowchart panel
        flowChartPanel = new FlowChartPanel();
        lifelinesPanel.setPreferredSize(
                new Dimension(flowChartPanel.getPreferredSize().width + 10, flowChartPanel.getPreferredSize().height));
        flowChartPanel.setBorder(spaceAboveFlowChart);
        mainPanel.add(lifelinesWrapperPanel, BorderLayout.WEST);
        mainPanel.add(flowChartPanel, BorderLayout.EAST);
        frame.add(mainPanel);
        frame.setVisible(true);

        // Initialize delayTimer with 3 seconds delay
        delayTimer = new javax.swing.Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadNextQuestion();
            }
        });

        // Add a start button to the main panel
        startButton = new JButton("Start Game");
        startButton.addActionListener(this);
        Dimension buttonSize = new Dimension(150, 50);
        startButton.setPreferredSize(buttonSize);

        JPanel startPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcStart = new GridBagConstraints();
        gbcStart.gridx = 0;
        gbcStart.gridy = 0;
        startPanel.add(startButton, gbcStart);
        scoreAndLabelPanel.setVisible(false);
        questionPanel.setVisible(false);
        answerPanel.setVisible(false);
        lifelinesPanel.setVisible(false);
        flowChartPanel.setVisible(false);
        mainPanel.add(startPanel, BorderLayout.CENTER);

        EmptyBorder gameLabelSpace = new EmptyBorder(100, 0, 0, 0);

        gameNameLabel = new JLabel("", SwingConstants.CENTER);
        gameNameLabel.setFont(new Font("Arial", Font.BOLD, 36));
        gameNameLabel.setBorder(gameLabelSpace);
        mainPanel.add(gameNameLabel, BorderLayout.NORTH);
        animateGameName();

    }

    static void setTimerDuration() {
        if (stage >= 1 && stage <= 4) {
            secondsLeft = 45;
        } else if (stage >= 5 && stage <= 7) {
            secondsLeft = 60;
        } else if (stage >= 8 && stage <= 10) {
            secondsLeft = 90;
        }
        updateTimerLabel();
    }

    @Override
    public void onFlipQuestionAction() {
        String newQuestion = questionSelection.extractquestion(stage);
        ArrayList<String> options = questionSelection.options();
        updatequestions(newQuestion, options);
    }

    @Override
    public ArrayList<String> getQuestionOptions() {
        return questionSelection.options();
    }

    @Override
    public String getCorrectAnswer() {
        return questionSelection.correctAnswer();
    }

    @Override
    public void onFiftyFiftyAction(ArrayList<String> updatedOptions) {
        for (int i = 0; i < updatedOptions.size(); i++) {
            String option = updatedOptions.get(i);
            if (!option.isEmpty()) {
                AnswerPanel.answerButtons[i].setText(option);
                AnswerPanel.answerButtons[i].setEnabled(true);
                AnswerPanel.answerButtons[i].setBackground(new Color(173, 216, 230));
            } else {
                AnswerPanel.answerButtons[i].setEnabled(false);
                AnswerPanel.answerButtons[i].setBackground(Color.GRAY);
            }
        }
    }

    @Override
    public void onAdd15SecAction(int additionalSeconds) {
        secondsLeft += additionalSeconds;
        updateTimerLabel();
        LifelinesPanel.add15secButton.setEnabled(false);
    }

    public static void updateTimerLabel() {
        questionPanel.setTimerLabel("Time left: " + secondsLeft + " seconds");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            startButton.setVisible(false);
            frame.revalidate();
            frame.repaint();
            initializeGame();
        } else {
            JButton selectedButton = (JButton) e.getSource();
            String selectedAnswer = selectedButton.getText();

            if (questionSelection.correctAnswer() != null
                    && selectedAnswer.equalsIgnoreCase(questionSelection.correctAnswer())) {
                selectedButton.setBackground(Color.GREEN);
                countdownTimer.timer.stop();
                delayTimer.start();
            } else {
                selectedButton.setBackground(Color.RED);
                for (JButton button : AnswerPanel.answerButtons) {
                    if (questionSelection.checkAnswer(button.getText())) {
                        button.setBackground(Color.GREEN);
                        break;
                    }
                }
                for (JButton button : AnswerPanel.answerButtons) {
                    button.setEnabled(false);
                }

                int currentScore;
                if (stage >= 1 && stage <= 4) {
                    currentScore = 0;
                } else if (stage >= 5 && stage <= 7) {
                    currentScore = 5000;
                } else if (stage >= 8 && stage <= 10) {
                    currentScore = 75000;
                } else {
                    currentScore = 0;
                }

                scheduleGameOverDisplay(controllerInterface, currentScore);

                countdownTimer.timer.stop();
                updateTimerLabel();
            }
        }
    }

    private void animateGameName() {
        String gameName = "$ MILLION-DOLLAR SHOWDOWN $";
        for (int i = 0; i < gameName.length(); i++) {
            final int index = i;
            gameNameLabel.setText(gameName.substring(0, index + 1));
            try {
                TimeUnit.MILLISECONDS.sleep(ANIMATION_DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void displayGameOver(ControllerInterface controllerInterface, int currentScore) {
        QuestionPanel.questionLabel.setVisible(false);
        QuestionPanel.timerLabel.setVisible(false);
        answerPanel.setVisible(false);
        if (currentScore == 1000000) {
            questionPanel.setGameOverLabel("Congratulations! You are a millionaire");
            controllerInterface.saveHighScore(1000000);
            ScoreAndLabelPanel.highScoreLabel.setText("High Score: $ 1000000");
        } else {
            questionPanel.setGameOverLabel("Game Over!");
            int highScore = getHighScore();

            if (currentScore > highScore) {
                setHighScore(currentScore);
                controllerInterface.saveHighScore(currentScore);
                questionPanel.updateScoreLabel(currentScore);
                questionPanel.displayMessage("Hurray! Your score is the new high score :)");
            } else {
                questionPanel.updateScoreLabel(currentScore);
            }
        }
    }

    private void initializeGame() {
        startButton.setVisible(false);
        gameNameLabel.setVisible(false);
        answerPanel.setVisible(true);
        lifelinesPanel.setVisible(true);
        flowChartPanel.setVisible(true);
        scoreAndLabelPanel.setVisible(true);
        questionPanel.setVisible(true);

        countdownTimer.start();
        controllerInterface.initializeQuestion(stage);

        int highScore = controllerInterface.loadHighScore();
        setHighScore(highScore);
    }

    public void loadNextQuestion() {
        delayTimer.stop();

        for (JButton button : AnswerPanel.answerButtons) {
            button.setBackground(new Color(173, 216, 230));
        }

        for (JButton button : AnswerPanel.answerButtons) {
            button.setEnabled(true);
        }

        countdownTimer.start();

        stage++;
        setTimerDuration();
        updateTimerLabel();

        if (stage <= 10) {
            controllerInterface.initializeQuestion(stage);
        } else {
            displayGameOver(controllerInterface, getPrizeForCurrentStage());
        }
        update();
    }

    public void scheduleGameOverDisplay(ControllerInterface controllerInterface, int currentScore) {
        for (JButton button : AnswerPanel.answerButtons) {
            if (questionSelection.checkAnswer(button.getText())) {
                button.setBackground(Color.GREEN);
                break;
            }
        }

        java.util.Timer gameOverTimer = new java.util.Timer();
        gameOverTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                displayGameOver(controllerInterface, currentScore);
            }
        }, 3000);
    }

    private static int getHighScore() {
        String highScoreStr = ScoreAndLabelPanel.highScoreLabel.getText();
        try {
            return Integer.parseInt(highScoreStr.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void setHighScore(int newHighScore) {
        ScoreAndLabelPanel.highScoreLabel.setText("High Score: $ " + newHighScore);
    }

    static int getPrizeForCurrentStage() {
        int greenRow = GameView.stage - 2;
        if (greenRow >= 0 && greenRow < FlowChartPanel.flowChartTable.getRowCount()) {
            String prizeStr = (String) FlowChartPanel.flowChartTable.getValueAt(greenRow, 1);
            try {
                return Integer.parseInt(prizeStr.replaceAll("[^0-9]", ""));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public void updateoptions(String option1, String option2) {
        answerPanel.disableAnswerButton(findIndexByOption(option1));
        answerPanel.disableAnswerButton(findIndexByOption(option2));
    }

    public void updatequestions(String newquestion, ArrayList<String> options) {
        questionPanel.setQuestion(newquestion);
        answerPanel.setAnswerOptions(options);
    }

    private int findIndexByOption(String option) {
        for (int i = 0; i < AnswerPanel.answerButtons.length; i++) {
            if (AnswerPanel.answerButtons[i].getText().equals(option)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void update() {
        FlowChartPanel.flowChartTable.repaint();
    }
}