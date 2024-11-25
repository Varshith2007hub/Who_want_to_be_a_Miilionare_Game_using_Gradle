package million_dollar_showdown.controller;

import million_dollar_showdown.ControllerInterface;
import million_dollar_showdown.LifelineAction;
import million_dollar_showdown.model.GameModel;
import million_dollar_showdown.view.AnswerPanel;
import million_dollar_showdown.view.GameView;
import million_dollar_showdown.view.QuestionPanel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GameController implements ControllerInterface {
    private GameModel questionSelection;
    private GameView prototypeView;

    public GameController(GameModel questionSelection) {
        this.questionSelection = questionSelection;
        this.prototypeView = new GameView(questionSelection, this);
    }

    public void initializeQuestion(int number) {
        String question = questionSelection.extractquestion(number);
        QuestionPanel.questionLabel.setText(question);
        ArrayList<String> options = questionSelection.options();
        for (int i = 0; i < 4; i++) {
            AnswerPanel.answerButtons[i].setText(options.get(i));
        }
    }

    @Override
    public void onLifelineButtonClick(LifelineAction lifelineAction) {
        lifelineAction.performAction();
    }

    public void updateHighScore(int score) {
        GameView.setHighScore(score);
        saveHighScore(score);
    }

    @Override
    public int loadHighScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader("highscore.txt"))) {
            String line = reader.readLine();
            if (line != null && !line.isEmpty()) {
                return Integer.parseInt(line);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void saveHighScore(int highScore) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("highscore.txt"))) {
            writer.write(String.valueOf(highScore));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}