module com.ebill.ebill {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.ebill.ebill to javafx.fxml;
    exports com.ebill.ebill;
    opens com.ebill.ebill.Controllers to javafx.fxml;
    exports com.ebill.ebill.Controllers;
}