module fr.univ_amu.iut.TestIHM2018 {
    requires javafx.controls;
    requires javafx.fxml;

    opens fr.univ_amu.iut.Mastermind to javafx.fxml;

    exports fr.univ_amu.iut;
    exports fr.univ_amu.iut.Mastermind;
}
