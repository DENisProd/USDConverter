module ru.denis.usdconverter {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jsoup;
    requires org.json;


    opens ru.denis.usdconverter to javafx.fxml;
    exports ru.denis.usdconverter;
    exports ru.denis.usdconverter.exceptions;
}