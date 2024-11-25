package million_dollar_showdown.view;

import javax.swing.*;

import million_dollar_showdown.model.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LifelinesPanel extends JPanel {
    static JButton fiftyFiftyButton, flipButton, add15secButton;
    private GameView gameView;

    public LifelinesPanel(GameView gameView) {
        this.gameView = gameView;
        initLifelinesPanel();
    }

    private void initLifelinesPanel() {
        setLayout(new GridLayout(3, 1));

        fiftyFiftyButton = new JButton();
        fiftyFiftyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameView.controllerInterface.onLifelineButtonClick(new FiftyFiftylifeline(gameView));
                fiftyFiftyButton.setEnabled(false);
            }
        });

        ImageIcon fiftyFiftyIcon = new ImageIcon("images/fifty_fifty.png");
        int fiftyFiftyWidth = 170;
        int fiftyFiftyHeight = 80;
        Image scaledFiftyFiftyImage = fiftyFiftyIcon.getImage().getScaledInstance(fiftyFiftyWidth, fiftyFiftyHeight,
                Image.SCALE_SMOOTH);
        fiftyFiftyButton.setIcon(new ImageIcon(scaledFiftyFiftyImage));

        flipButton = new JButton();
        flipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameView.controllerInterface.onLifelineButtonClick(new FlipQuestion(gameView));
                flipButton.setEnabled(false);
            }
        });

        ImageIcon flipIcon = new ImageIcon("images/flip.png");
        int flipWidth = 170;
        int flipHeight = 80;
        Image scaledAudienceImage = flipIcon.getImage().getScaledInstance(flipWidth, flipHeight, Image.SCALE_SMOOTH);
        flipButton.setIcon(new ImageIcon(scaledAudienceImage));

        add15secButton = new JButton();
        add15secButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameView.controllerInterface.onLifelineButtonClick(new Add15Sec(gameView));
            }
        });

        ImageIcon add15SecIcon = new ImageIcon("images/add15sec.png");
        int add15SecWidth = 170;
        int add15SecHeight = 80;
        Image scaledadd15SecImage = add15SecIcon.getImage().getScaledInstance(add15SecWidth,
                add15SecHeight, Image.SCALE_SMOOTH);
        add15secButton.setIcon(new ImageIcon(scaledadd15SecImage));

        add(fiftyFiftyButton);
        add(flipButton);
        add(add15secButton);
    }

    public JButton getFiftyFiftyButton() {
        return fiftyFiftyButton;
    }

    public JButton getFlipButton() {
        return flipButton;
    }

    public JButton getAdd15secButton() {
        return add15secButton;
    }
}