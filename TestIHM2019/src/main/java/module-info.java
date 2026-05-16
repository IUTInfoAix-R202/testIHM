module fr.univ_amu.iut.TestIHM2019 {
    requires javafx.controls;
    requires javafx.fxml;

    opens fr.univ_amu.iut.lightsout to javafx.fxml;

    exports fr.univ_amu.iut;
    exports fr.univ_amu.iut.lightsout;
}
