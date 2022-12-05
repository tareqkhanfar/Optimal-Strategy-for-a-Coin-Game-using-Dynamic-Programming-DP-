module com.khanfar.projectone {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.khanfar.projectone to javafx.fxml;
    exports com.khanfar.projectone;
    opens com.khanfar.FrontEnd to javafx.fxml;
    exports com.khanfar.FrontEnd;
}