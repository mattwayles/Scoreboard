package com.advancedsportstechnologies.modules.shared.view;

import com.advancedsportstechnologies.Run;
import com.advancedsportstechnologies.modules.shared.model.Team;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class TeamView extends Team {
    private Label nameLabel;
    private Label scoreLabel;
    private HBox gamesWonImgs;
    private static int count = 1;

    public TeamView(String teamName) {
        super(teamName);
        this.nameLabel = new Label(getTeamName());
        nameLabel.setFont(Run.WIDTH > 1280 ? new Font(82) : new Font(60));
        this.nameLabel.getStyleClass().add("team" + count + "NameLabel");
        this.scoreLabel = new Label(String.valueOf(getScore()));
        scoreLabel.setFont(Run.WIDTH > 1280 ? new Font(500) : new Font(350));
        this.scoreLabel.getStyleClass().add("scoreLabel");
        this.gamesWonImgs = new HBox(50);
        this.getGamesWonImgs().getChildren().add(new ImageView(new Image("/img/placeholder.png")));
        this.gamesWonImgs.setAlignment(Pos.CENTER);
        count++;
    }

    public Label getNameLabel() { return this.nameLabel; }

    public void setNameLabel(String name) { this.nameLabel.textProperty().setValue(name);}

    public Label getScoreLabel() { return this.scoreLabel; }

    public void setScoreLabel(int score) { this.scoreLabel.textProperty().setValue(String.valueOf(score));}

    public HBox getGamesWonImgs() { return this.gamesWonImgs; }

    public void setGamesWonImgs(HBox gamesWonImgs) { this.gamesWonImgs = gamesWonImgs;}

    public static void resetCount() { count = 1; }

}
