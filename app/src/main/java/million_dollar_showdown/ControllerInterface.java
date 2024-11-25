package million_dollar_showdown;

public interface ControllerInterface {
    public void initializeQuestion(int number);

    public void onLifelineButtonClick(LifelineAction lifelineAction);

    public int loadHighScore();

    public void saveHighScore(int highScore);

}