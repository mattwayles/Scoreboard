package main.java.com.socialsportstechnology.games.cornhole.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import main.java.com.socialsportstechnology.games.cornhole.model.Team;

public class TeamView extends Team {
    private Label nameLabel;
    private Label scoreLabel;
    private HBox gamesWonImgs;
    private static int count = 1;

    TeamView(String teamName) {
        super(teamName);
        this.nameLabel = new Label(getTeamName());
        this.nameLabel.getStyleClass().add("team" + count + "NameLabel");
        this.scoreLabel = new Label(String.valueOf(getScore()));
        this.scoreLabel.getStyleClass().add("scoreLabel");
        this.gamesWonImgs = new HBox(50);
        this.getGamesWonImgs().getChildren().add(new ImageView(new Image("/img/placeholder.png")));
        this.gamesWonImgs.setAlignment(Pos.CENTER);
        count++;
    }

    Label getNameLabel() { return this.nameLabel; }

    public void setNameLabel(String name) { this.nameLabel.textProperty().setValue(name);}

    Label getScoreLabel() { return this.scoreLabel; }

    public void setScoreLabel(int score) { this.scoreLabel.textProperty().setValue(String.valueOf(score));}

    public HBox getGamesWonImgs() { return this.gamesWonImgs; }

    public void setGamesWonImgs(HBox gamesWonImgs) { this.gamesWonImgs = gamesWonImgs;}

    public static void resetCount() { count = 1; }

}
