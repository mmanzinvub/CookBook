module hr.vub.cookbook {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens hr.vub.cookbook to javafx.fxml;
    exports hr.vub.cookbook;
}