package salarychecker.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import salarychecker.core.Accounts;
import salarychecker.core.User;
import salarychecker.json.SalaryCheckerPersistence;

import java.io.IOException;

public class HomepageController {

    @FXML private Text navnDisplay;
    @FXML private Text epostTitle;
    @FXML private Text epostDisplay;
    @FXML private Text idTitle;
    @FXML private Text idDisplay;
    @FXML private TextField newPassword;
    @FXML private TextField confirmNewPessword;
    @FXML private Button changebutton;
    Alert a = new Alert(Alert.AlertType.NONE);


    User user = new User();
    Accounts existingaccounts = new Accounts();


    public void loadInfo() {
        navnDisplay.setText(user.getFirstname()+ " " + user.getLastname());
        epostDisplay.setText(user.getEmail());
        idDisplay.setText(String.valueOf(user.getEmployeeNumber()));
    }

    @FXML
    void passwordAction(ActionEvent event) throws IOException {
        String password1 = newPassword.getText();
        String password2 = confirmNewPessword.getText();
        String email = epostDisplay.getText();

        if (password1.equals(password2)){
            user.setPassword(password1);
            changePasswordPersistence(email, password1);
        }
        else {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Passwords does not match");
            a.show();
            throw new IllegalArgumentException("Passwords does not match.");
        }
    }

    private void changePasswordPersistence(String email, String password) throws IOException {
        existingaccounts.updatePassword(email, password);
        SalaryCheckerPersistence SCP = new SalaryCheckerPersistence();
        SCP.setSaveFile("Accounts.json");
        SCP.saveAccounts(existingaccounts);
        Success();
        newPassword.clear();
        confirmNewPessword.clear();
    }

    private void Success() {
        a.setAlertType(Alert.AlertType.INFORMATION);
        a.setContentText("Password changed!");
        a.show();
    }

    public void setUser(User user) {
        this.user=user;
    }

    public void setAccounts(Accounts accounts) {
        this.existingaccounts = accounts;
    }
}
