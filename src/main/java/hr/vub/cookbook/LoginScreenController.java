package hr.vub.cookbook;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginScreenController {
    Stage stage;

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    private final String VALID_USERNAME = "cook";
    private final String VALID_PASSWORD = "2222";

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.equals(VALID_USERNAME) && password.equals(VALID_PASSWORD)) {
            SceneManager.switchTo("recipe");
        } else {
            messageLabel.setText("Neispravno korisničko ime ili lozinka.");
        }
    }
}