package million_dollar_showdown.view;

import javax.swing.*;
import java.awt.*;

public class ScoreAndLabelPanel extends JPanel {
    public static JLabel highScoreLabel;
    public static JLabel gameLogoLabel;

    public ScoreAndLabelPanel() {
        setLayout(new GridBagLayout());

        gameLogoLabel = new JLabel();
        ImageIcon gameLogoIcon = new ImageIcon("images/game_icon.png");
        int logoWidth = 250;
        int logoHeight = 200;
        gameLogoIcon = new ImageIcon(
                gameLogoIcon.getImage().getScaledInstance(logoWidth, logoHeight, Image.SCALE_SMOOTH));
        gameLogoLabel.setIcon(gameLogoIcon);
        gameLogoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbcLogo = new GridBagConstraints();
        gbcLogo.gridx = 2;
        gbcLogo.gridy = 2;
        gbcLogo.insets = new Insets(0, 400, 0, 0);
        gbcLogo.anchor = GridBagConstraints.CENTER;
        add(gameLogoLabel, gbcLogo);

        String highScore = "High Score: $ 500";
        highScoreLabel = new JLabel(highScore);
        GridBagConstraints gbcScore = new GridBagConstraints();
        gbcScore.gridx = 3;
        gbcScore.gridy = 2;
        gbcScore.insets = new Insets(0, 400, 100, -100);
        add(highScoreLabel, gbcScore);
        gameLogoLabel.setVisible(true);
        highScoreLabel.setVisible(true);
    }
}