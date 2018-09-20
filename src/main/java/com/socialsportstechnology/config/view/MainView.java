package main.java.com.socialsportstechnology.config.view;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MainView extends Node {
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
        //TODO: Move this to CSS?
        mainView.setSpacing(150);
        mainView.setAlignment(Pos.CENTER);

        setCurrentControl(gameSelection);
        return mainView;
    }

    private VBox createLabelBox() {
        Text title = new Text("Chucktown Social Scoreboard");
        title.getStyleClass().add("mainTitle");
        Label version = new Label("v0.2");
        version.getStyleClass().add("version");
        VBox titleBox = new VBox(title, version);
        titleBox.setAlignment(Pos.CENTER);
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
