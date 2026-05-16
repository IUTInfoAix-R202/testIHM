package fr.univ_amu.iut;

import fr.univ_amu.iut.game.LetterStatus;
import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class WordleAnimation {
    private final static double speed = 400.0;
    private final static double delay = 400.0;

    public static void flipTile(Node node, int column, LetterStatus letterStatus) {
        PauseTransition pause = new PauseTransition(new Duration(column * delay));

        RotateTransition rotateIn = new RotateTransition(Duration.millis(speed), node);
        rotateIn.setByAngle(90.0);
        rotateIn.setAxis(Rotate.X_AXIS);
        rotateIn.setOnFinished(event -> LetterStatusPseudoClass.updatePseudoClass(node, letterStatus));

        RotateTransition rotateOut = new RotateTransition(Duration.millis(speed), node);
        rotateOut.setByAngle(- 90.0);
        rotateOut.setAxis(Rotate.X_AXIS);

        SequentialTransition sequentialTransition = new SequentialTransition(pause, rotateIn, rotateOut);
        sequentialTransition.play();
    }

    public static void flashTile(Node node) {
        ScaleTransition scaleTransitionIn = new ScaleTransition(Duration.millis(20.0), node);
        scaleTransitionIn.setToX(1.08);
        scaleTransitionIn.setToY(1.08);
        scaleTransitionIn.setCycleCount(2);
        scaleTransitionIn.setAutoReverse(true);

        ScaleTransition scaleTransitionOut = new ScaleTransition(Duration.millis(20.0), node);
        scaleTransitionOut.setToX(0.92);
        scaleTransitionOut.setToY(0.92);
        scaleTransitionOut.setCycleCount(2);
        scaleTransitionOut.setAutoReverse(true);

        SequentialTransition sequentialTransition = new SequentialTransition(scaleTransitionIn, scaleTransitionOut);
        sequentialTransition.play();
    }

    public static void wiggleRow(Node node) {
        node.setTranslateX(- 5.0);
        TranslateTransition transition = new TranslateTransition(Duration.millis(50.0), node);
        transition.setByX(10);
        transition.setCycleCount(6);
        transition.setAutoReverse(true);
        transition.setOnFinished(event -> node.setTranslateX(0.0));
        transition.play();
    }

    public static void showBadWordLabel(Node node) {
        FadeTransition fadeIn = new FadeTransition(new Duration(200.0), node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        PauseTransition pause = new PauseTransition(new Duration(1000.0));

        FadeTransition fadeOut = new FadeTransition(new Duration(200.0), node);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        SequentialTransition sequentialTransition = new SequentialTransition(fadeIn, pause, fadeOut);
        sequentialTransition.play();
    }
}
