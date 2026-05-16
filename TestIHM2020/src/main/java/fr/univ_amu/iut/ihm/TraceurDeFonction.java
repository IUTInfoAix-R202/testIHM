package fr.univ_amu.iut.ihm;

import fr.univ_amu.iut.utilitaires.Analyseur;
import fr.univ_amu.iut.utilitaires.Basic2DPoint;
import fr.univ_amu.iut.utilitaires.CalculateurPointsFonction;
import fr.univ_amu.iut.utilitaires.Expression;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class TraceurDeFonction extends Application {

  // Composants identifies (package-private comme demande dans l'enonce)
  TextField texteAAnalyser;
  Label resultatAnalyse;
  Button demandeAnalyser;
  Button demandeTracer;
  Button demandeEffacer;
  Pane zoneTraceCourbe;
  Spinner<Double> choixXMin;
  Spinner<Double> choixXMax;
  Slider choixEspacementX_v1;
  Spinner<Double> choixEspacementX_v2;
  Slider choixEspacementY_v1;
  Spinner<Double> choixEspacementY_v2;
  ColorPicker choixCouleur;
  Spinner<Double> choixEpaisseur;
  Group segmentsATracer;
  Group quadrillage;
  Line axeX;
  Line axeY;

  // Etat du trace en cours
  private double currentXMin, currentXMax, currentYMin, currentYMax;
  private double Ax, Bx, Ay, By;
  private final List<Line> segmentsCourbe = new ArrayList<>();
  private Expression expressionAnalysee;

  @Override
  public void start(Stage primaryStage) {
    BorderPane root = new BorderPane();

    // -- Ligne expression + bouton Analyser --
    texteAAnalyser = new TextField("exp(-x * 0.2) * sin(x)");
    texteAAnalyser.setPrefWidth(320);
    demandeAnalyser = new Button("Analyser");
    HBox ligneExpression = new HBox(8, new Label("Expression :"), texteAAnalyser, demandeAnalyser);
    ligneExpression.setAlignment(Pos.CENTER_LEFT);

    // -- Resultat de l'analyse --
    resultatAnalyse = new Label();
    resultatAnalyse.setWrapText(true);
    resultatAnalyse.setMaxWidth(580);

    // -- Ligne intervalle + boutons Tracer / Effacer --
    choixXMin = creerSpinner(-100.0, 100.0, -0.5, 0.5, 80);
    choixXMax = creerSpinner(-100.0, 100.0, 20.0, 0.5, 80);
    demandeTracer = new Button("Tracer");
    demandeEffacer = new Button("Effacer");
    HBox ligneIntervalle = new HBox(8,
        new Label("xMin :"), choixXMin,
        new Label("xMax :"), choixXMax,
        demandeTracer, demandeEffacer);
    ligneIntervalle.setAlignment(Pos.CENTER_LEFT);

    // -- Espacement X : 2 facons (Slider + Spinner) --
    choixEspacementX_v1 = creerSlider(0.5, 2.0, 0.5, 0.5);
    choixEspacementX_v2 = creerSpinner(0.5, 2.0, 0.5, 0.1, 80);
    HBox ligneEspX = new HBox(8,
        new Label("Espacement X :"), choixEspacementX_v1, choixEspacementX_v2);
    ligneEspX.setAlignment(Pos.CENTER_LEFT);

    // -- Espacement Y : 2 facons (Slider + Spinner) --
    choixEspacementY_v1 = creerSlider(0.25, 2.0, 0.25, 0.5);
    choixEspacementY_v2 = creerSpinner(0.25, 2.0, 0.25, 0.05, 80);
    HBox ligneEspY = new HBox(8,
        new Label("Espacement Y :"), choixEspacementY_v1, choixEspacementY_v2);
    ligneEspY.setAlignment(Pos.CENTER_LEFT);

    // -- Apparence du trace --
    choixCouleur = new ColorPicker(Color.RED);
    choixEpaisseur = creerSpinner(0.5, 10.0, 1.0, 0.5, 80);
    HBox ligneApparence = new HBox(8,
        new Label("Couleur :"), choixCouleur,
        new Label("Epaisseur :"), choixEpaisseur);
    ligneApparence.setAlignment(Pos.CENTER_LEFT);

    VBox controles = new VBox(8,
        ligneExpression, resultatAnalyse,
        ligneIntervalle, ligneEspX, ligneEspY, ligneApparence);
    controles.setPadding(new Insets(10));

    // -- Zone de trace --
    zoneTraceCourbe = new Pane();
    zoneTraceCourbe.setStyle("-fx-background-color: white; -fx-border-color: lightgrey;");
    quadrillage = new Group();
    axeX = new Line();
    axeY = new Line();
    segmentsATracer = new Group();
    zoneTraceCourbe.getChildren().addAll(quadrillage, axeX, axeY, segmentsATracer);

    root.setTop(controles);
    root.setCenter(zoneTraceCourbe);
    BorderPane.setMargin(zoneTraceCourbe, new Insets(0, 10, 10, 10));

    // -- Synchronisation Slider <-> Spinner --
    synchroniser(choixEspacementX_v1, choixEspacementX_v2);
    synchroniser(choixEspacementY_v1, choixEspacementY_v2);

    // -- Handlers boutons --
    demandeAnalyser.setOnAction(e -> analyser());
    demandeTracer.setOnAction(e -> tracer());
    demandeEffacer.setOnAction(e -> effacer());

    // -- Mise a jour live de la couleur et de l'epaisseur du trace en cours --
    choixCouleur.valueProperty().addListener((o, anc, nouv) -> {
      for (Line l : segmentsCourbe) {
        l.setStroke(nouv);
      }
    });
    choixEpaisseur.valueProperty().addListener((o, anc, nouv) -> {
      if (nouv != null) {
        for (Line l : segmentsCourbe) {
          l.setStrokeWidth(nouv);
        }
      }
    });

    setIds();

    Scene scene = new Scene(root, 600, 650);
    primaryStage.setTitle("Traceur de fonction");
    primaryStage.setResizable(false);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private static Spinner<Double> creerSpinner(double min, double max, double init, double pas, double largeur) {
    Spinner<Double> s = new Spinner<>(min, max, init, pas);
    s.setEditable(true);
    s.setPrefWidth(largeur);
    return s;
  }

  private static Slider creerSlider(double min, double max, double init, double tickUnit) {
    Slider s = new Slider(min, max, init);
    s.setShowTickLabels(true);
    s.setShowTickMarks(true);
    s.setMajorTickUnit(tickUnit);
    s.setBlockIncrement(tickUnit / 5);
    return s;
  }

  // Lie un Slider et un Spinner pour qu'ils restent en phase
  private static void synchroniser(Slider slider, Spinner<Double> spinner) {
    slider.valueProperty().addListener((o, anc, nouv) -> {
      double v = nouv.doubleValue();
      if (Math.abs(spinner.getValue() - v) > 1e-9) {
        spinner.getValueFactory().setValue(v);
      }
    });
    spinner.valueProperty().addListener((o, anc, nouv) -> {
      if (nouv != null && Math.abs(slider.getValue() - nouv) > 1e-9) {
        slider.setValue(nouv);
      }
    });
  }

  private void analyser() {
    try {
      Analyseur analyseur = new Analyseur(texteAAnalyser.getText());
      expressionAnalysee = analyseur.analyser();
      resultatAnalyse.setText("Expression analysée : f(x) = " + expressionAnalysee.toString());
      resultatAnalyse.setStyle("-fx-text-fill: black;");
    } catch (Exception ex) {
      expressionAnalysee = null;
      resultatAnalyse.setText(ex.getMessage());
      resultatAnalyse.setStyle("-fx-text-fill: red;");
    }
  }

  private void tracer() {
    if (expressionAnalysee == null) {
      analyser();
      if (expressionAnalysee == null) {
        return;
      }
    }

    currentXMin = choixXMin.getValue();
    currentXMax = choixXMax.getValue();
    if (currentXMax <= currentXMin) {
      resultatAnalyse.setText("xMax doit etre strictement superieur a xMin");
      resultatAnalyse.setStyle("-fx-text-fill: red;");
      return;
    }

    effacer();

    CalculateurPointsFonction calc = new CalculateurPointsFonction(expressionAnalysee, currentXMin, currentXMax);
    currentYMin = calc.getYMin();
    currentYMax = calc.getYMax();

    // Marge en haut et en bas du trace : espY / 4 (cf. enonce)
    double espY = choixEspacementY_v2.getValue();
    currentYMin -= espY / 4;
    currentYMax += espY / 4;

    calculCoeffTransformationsAffines();
    dessinerQuadrillage();
    dessinerAxes();
    dessinerCourbe(calc.getListePoints());
  }

  private void effacer() {
    quadrillage.getChildren().clear();
    segmentsATracer.getChildren().clear();
    segmentsCourbe.clear();
    axeX.setStartX(0);
    axeX.setStartY(0);
    axeX.setEndX(0);
    axeX.setEndY(0);
    axeY.setStartX(0);
    axeY.setStartY(0);
    axeY.setEndX(0);
    axeY.setEndY(0);
  }

  void calculCoeffTransformationsAffines() {
    double width = zoneTraceCourbe.getWidth();
    double height = zoneTraceCourbe.getHeight();
    Ax = width / (currentXMax - currentXMin);
    Bx = -Ax * currentXMin;
    Ay = -height / (currentYMax - currentYMin);
    By = -Ay * currentYMax;
  }

  int transformationAffineX(double x) {
    return (int) Math.round(Ax * x + Bx);
  }

  int transformationAffineY(double y) {
    return (int) Math.round(Ay * y + By);
  }

  private void dessinerQuadrillage() {
    double espX = choixEspacementX_v2.getValue();
    double espY = choixEspacementY_v2.getValue();
    double width = zoneTraceCourbe.getWidth();
    double height = zoneTraceCourbe.getHeight();

    double xStart = Math.ceil(currentXMin / espX) * espX;
    for (double xa = xStart; xa <= currentXMax + 1e-9; xa += espX) {
      int xe = transformationAffineX(xa);
      Line l = new Line(xe, 0, xe, height);
      l.setStroke(Color.LIGHTGREY);
      quadrillage.getChildren().add(l);
    }
    double yStart = Math.ceil(currentYMin / espY) * espY;
    for (double ya = yStart; ya <= currentYMax + 1e-9; ya += espY) {
      int ye = transformationAffineY(ya);
      Line l = new Line(0, ye, width, ye);
      l.setStroke(Color.LIGHTGREY);
      quadrillage.getChildren().add(l);
    }
  }

  private void dessinerAxes() {
    double width = zoneTraceCourbe.getWidth();
    double height = zoneTraceCourbe.getHeight();
    if (currentYMin <= 0 && 0 <= currentYMax) {
      int ye = transformationAffineY(0);
      axeX.setStartX(0);
      axeX.setStartY(ye);
      axeX.setEndX(width);
      axeX.setEndY(ye);
    }
    if (currentXMin <= 0 && 0 <= currentXMax) {
      int xe = transformationAffineX(0);
      axeY.setStartX(xe);
      axeY.setStartY(0);
      axeY.setEndX(xe);
      axeY.setEndY(height);
    }
  }

  private void dessinerCourbe(List<Basic2DPoint> points) {
    Color couleur = choixCouleur.getValue();
    double epaisseur = choixEpaisseur.getValue();
    for (int i = 0; i < points.size() - 1; i++) {
      Basic2DPoint p1 = points.get(i);
      Basic2DPoint p2 = points.get(i + 1);
      Line l = new Line(
          transformationAffineX(p1.getX()),
          transformationAffineY(p1.getY()),
          transformationAffineX(p2.getX()),
          transformationAffineY(p2.getY()));
      l.setStroke(couleur);
      l.setStrokeWidth(epaisseur);
      segmentsCourbe.add(l);
      segmentsATracer.getChildren().add(l);
    }
  }

  private void setIds() {
    texteAAnalyser.setId("texteAAnalyser");
    resultatAnalyse.setId("resultatAnalyse");
    demandeAnalyser.setId("demandeAnalyser");
    demandeTracer.setId("demandeTracer");
    demandeEffacer.setId("demandeEffacer");
    zoneTraceCourbe.setId("zoneTraceCourbe");
    choixXMin.setId("choixXMin");
    choixXMax.setId("choixXMax");
    choixEspacementX_v1.setId("choixEspacementX_v1");
    choixEspacementX_v2.setId("choixEspacementX_v2");
    choixEspacementY_v1.setId("choixEspacementY_v1");
    choixEspacementY_v2.setId("choixEspacementY_v2");
    choixCouleur.setId("choixCouleur");
    choixEpaisseur.setId("choixEpaisseur");
    segmentsATracer.setId("segmentsATracer");
    quadrillage.setId("quadrillage");
    axeX.setId("axeX");
    axeY.setId("axeY");
  }

  public static void main(String[] args) {
    launch(args);
  }
}
