package fr.univ_amu.iut.exercice2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RectanglePerimeterCalculatorAndDrawer extends Application {

    private PanneauPrincipal root;

    @Override
    public void start(Stage stage) throws Exception {
        root = new PanneauPrincipal();
        // Question introduction (1 point)
        stage.setTitle("Perimeter Calculator And Drawer");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        // Fin Question introduction (1 point)
    }

}
