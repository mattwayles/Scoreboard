package com.advancedsportstechnology.config.view;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.scene.image.ImageView;

public class MainView extends Node {
    private static final double HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();

    private static final String TITLE = "Chucktown Social Scoreboard";
    private static final String VERSION = "v0.3";

    private Pane mainView;
    private Node currentControl;

    public MainView() {
        this.mainView = createMainView();
    }

    public Pane getMainView() { return mainView; }

    public void setMainView(Pane view) { this.mainView = view; }

    public Node getCurrentControl() { return currentControl; }

    public void setCurrentControl(Node view) { this.currentControl = view; }

    private VBox createMainView() {
        VBox titleBox = createLabelBox();
        Label beginLabel = createBeginLabel();
        GameSelectView gameSelection = new GameSelectView();
        VBox mainView = new VBox(titleBox, gameSelection.getGameSelectView(), beginLabel);
        mainView.getStyleClass().add("mainView");
        mainView.setMaxHeight(HEIGHT);
        mainView.setSpacing(HEIGHT / 7);

        setCurrentControl(gameSelection);
        return mainView;
    }

    private VBox createLabelBox() {
        Text title = new Text(TITLE);
        title.getStyleClass().add("mainTitle");
        Label version = new Label(VERSION);
        version.getStyleClass().add("version");
        ImageView logo = new ImageView(new Image("/img/astLogo.png"));
        HBox logoBox = new HBox(logo, title);
        logoBox.setSpacing(20);
        logoBox.setAlignment(Pos.CENTER);
        VBox titleBox = new VBox(logoBox, version);
        titleBox.getStyleClass().add("titleBox");
        return titleBox;
    }

    private Label createBeginLabel() {
        Label beginLabel = new Label("Press Start to Continue (Q / W until controllers work)");
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
