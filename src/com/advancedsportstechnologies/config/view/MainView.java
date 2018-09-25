package com.advancedsportstechnologies.config.view;

import com.advancedsportstechnologies.Run;
import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.scene.image.ImageView;

import java.util.HashSet;
import java.util.Set;

public class MainView extends Node {
    private static final String TITLE = "Chucktown Social Scoreboard";
    private static final String VERSION = Run.version;

    private Pane mainView;
    private Node currentControl;
    private long keyPressTime;
    private KeyCode lastKeyPressed;
    private Set<KeyCode> keysDown = new HashSet<>();

    public MainView() {}

    public MainView(GameSelectView gameSelectView) {
        this.mainView = createMainView(gameSelectView);
    }

    public Pane getMainView() { return mainView; }

    public void setMainView(Pane view) { this.mainView = view; }

    public Node getCurrentControl() { return currentControl; }

    public void setCurrentControl(MainView view) { this.currentControl = view; }

    public long getKeyPressTime() { return this.keyPressTime; }

    public void setKeyPressTime(long time) { this.keyPressTime = time; }

    public KeyCode getLastKeyPressed() { return this.lastKeyPressed; }

    public void setLastKeyPressed(KeyCode key) { this.lastKeyPressed = key;}

    public Set<KeyCode> getKeysDown() { return this.keysDown; }

    public void setKeysDown(Set<KeyCode> keys) { this.keysDown = keys; }

    private VBox createMainView(GameSelectView gameSelectView) {
        VBox titleBox = createLabelBox();
        Label beginLabel = createBeginLabel();
        VBox mainView = new VBox(titleBox, gameSelectView.getGameSelectView(), beginLabel);
        mainView.getStyleClass().add("mainView");
        mainView.setMaxHeight(Run.HEIGHT);
        mainView.setSpacing(Run.HEIGHT / 7);

        setCurrentControl(gameSelectView);
        return mainView;
    }

    private VBox createLabelBox() {
        Text title = new Text(TITLE);
        title.getStyleClass().add("mainTitle");
        Label version = new Label(VERSION);
        version.getStyleClass().add("version");
        ImageView logo = new ImageView(new Image("img/astLogo.png"));
        VBox titleBox = new VBox(logo, title, version);
        titleBox.getStyleClass().add("titleBox");
        return titleBox;
    }

    private Label createBeginLabel() {
        Label beginLabel = new Label("Press Start to Continue");
        beginLabel.getStyleClass().add("pressStartLabel");
        return beginLabel;
    }

    public void updateConfigView(Node node) {
        this.mainView.getChildren().clear();
        this.mainView.getChildren().addAll(createLabelBox(), node, createBeginLabel());
    }

    public void updateMainView(Node node) {
        this.mainView.getChildren().clear();
        this.mainView.getChildren().add(node);
    }

    @Override
    protected NGNode impl_createPeer() {
        return null;
    }

    @Override
    public BaseBounds impl_computeGeomBounds(BaseBounds bounds, BaseTransform tx) {
        return null;
    }

    @Override
    protected boolean impl_computeContains(double localX, double localY) {
        return false;
    }

    @Override
    public Object impl_processMXNode(MXNodeAlgorithm alg, MXNodeAlgorithmContext ctx) {
        return null;
    }
}
