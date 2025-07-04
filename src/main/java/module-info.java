module hr.vub.cookbook {
    requires javafx.controls;
    requires javafx.fxml;


    opens hr.vub.cookbook to javafx.fxml;
    exports hr.vub.cookbook;
}