module fr.univ_amu.iut.TestIHM2016 {
    requires javafx.controls;
    requires javafx.fxml;

    opens fr.univ_amu.iut.tictactoe to javafx.fxml;

    exports fr.univ_amu.iut;
    exports fr.univ_amu.iut.tictactoe;
}
