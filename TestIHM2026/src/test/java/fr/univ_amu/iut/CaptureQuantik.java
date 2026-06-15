package fr.univ_amu.iut;

import fr.univ_amu.iut.modele.Forme;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

/**
 * Generateur de la capture d'ecran de l'application Quantik (illustration des README).
 *
 * <p>Inspire du mecanisme de capture de la SAE 2.01 (rendu hors-ecran via {@link
 * javafx.scene.Scene#snapshot}), mais sans dependance {@code javafx.swing} : la {@link
 * WritableImage} est convertie en {@link BufferedImage} via son {@link PixelReader}. S'appuie sur
 * le toolkit headless deja demarre par TestFX.
 *
 * <p>Ce n'est pas un test de verification : il produit {@code
 * src/main/resources/assets/quantik_screenshot.png}. On le laisse desactive pour qu'il ne tourne
 * pas en CI ; retirer l'annotation {@link org.junit.jupiter.api.Disabled} pour regenerer l'image.
 */
@org.junit.jupiter.api.Disabled("Outil de generation de capture : activer pour regenerer le PNG")
@ExtendWith(ApplicationExtension.class)
class CaptureQuantik {

  private static final Path SORTIE = Path.of("src/main/resources/assets/quantik_screenshot.png");

  private Scene scene;

  @Start
  void start(Stage stage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/quantikView.fxml"));
    Parent racine = loader.load();
    QuantikController controleur = loader.getController();
    QuantikViewModel vm = controleur.getViewModel();

    // Une position de milieu de partie : formes variees, deux couleurs.
    jouer(vm, Forme.CUBE, 0, 0); // BLANC
    jouer(vm, Forme.SPHERE, 1, 2); // NOIR
    jouer(vm, Forme.CYLINDRE, 2, 1); // BLANC
    jouer(vm, Forme.CONE, 3, 3); // NOIR
    jouer(vm, Forme.SPHERE, 3, 0); // BLANC
    jouer(vm, Forme.CUBE, 1, 3); // NOIR
    vm.selectionner(Forme.CONE); // BLANC choisit une forme (vignette surlignee)

    scene = new Scene(racine, 720, 560);
    scene.getStylesheets().add(getClass().getResource("/css/quantik.css").toExternalForm());
    stage.setScene(scene);
    stage.show();
  }

  private void jouer(QuantikViewModel vm, Forme forme, int ligne, int colonne) {
    vm.selectionner(forme);
    vm.jouerEn(ligne, colonne);
  }

  @Test
  void genererLaCapture(FxRobot robot) throws Exception {
    java.util.concurrent.atomic.AtomicReference<WritableImage> capture =
        new java.util.concurrent.atomic.AtomicReference<>();
    robot.interact(
        () -> {
          scene.getRoot().applyCss();
          scene.getRoot().layout();
          capture.set(scene.snapshot(null));
        });
    ecrirePng(capture.get(), SORTIE);
    System.out.println("Capture ecrite dans " + SORTIE.toAbsolutePath());
  }

  private static void ecrirePng(WritableImage image, Path fichier) throws Exception {
    int largeur = (int) image.getWidth();
    int hauteur = (int) image.getHeight();
    BufferedImage rendu = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_ARGB);
    PixelReader lecteur = image.getPixelReader();
    for (int y = 0; y < hauteur; y++) {
      for (int x = 0; x < largeur; x++) {
        rendu.setRGB(x, y, lecteur.getArgb(x, y));
      }
    }
    if (fichier.getParent() != null) {
      Files.createDirectories(fichier.getParent());
    }
    ImageIO.write(rendu, "png", fichier.toFile());
  }
}
