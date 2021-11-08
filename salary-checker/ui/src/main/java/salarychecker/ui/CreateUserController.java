package salarychecker.ui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import salarychecker.core.Accounts;
import salarychecker.core.AdminUser;
import salarychecker.core.UserValidation;
import salarychecker.json.SalaryCheckerPersistence;

/**
 * This is the class for controlling CreateUser scene.
 */
public class CreateUserController extends AbstractController {

  private AdminUser adminUser;
  private Accounts accounts;
  UserValidation userValidation = new UserValidation();

  @FXML private AnchorPane firstLastPane;
  @FXML private AnchorPane empemailPane;
  @FXML private AnchorPane socialPassPane;
  @FXML private AnchorPane wageTaxPane;
  @FXML private Button goBackButton;
  @FXML private TextField nameField;
  @FXML private TextField lastNameField;
  @FXML private TextField wageField;
  @FXML private TextField taxField;
  @FXML private TextField confirmPasswordField;
  @FXML private TextField passwordField;
  @FXML private TextField socialField;
  @FXML private TextField emailField;
  @FXML private TextField employerIdField;
  @FXML private Text errorMessageDisplay;
  @FXML private Button goOnButton;
  @FXML private Button createUserButton;

  private String firstname;
  private String lastname;
  private double wage;
  private double tax;
  private String password;
  private int employeeNumber;
  private String email;
  private String socialNumber;

  /**
   * This is the method that both loads users and sets the first visible scene.
   * The method is protected because it will be called from AbstractController.
   */
  protected void loadUserAndAccount() {
    adminUser = (AdminUser) super.user;
    accounts = super.accounts;
    empemailPane.setVisible(false);
    socialPassPane.setVisible(false);
    wageTaxPane.setVisible(false);
    goBackButton.setVisible(false);
    createUserButton.setVisible(false);
  }

  /**
   * This is the method that sets new AnchorPane when user clicks on "->".
   * The method checks what AnchorPane is visible and validates using
   * UserValidation(). If somethings is throw this will be displayed in
   * 'errorMessageDisplay'. After validation is run it sets new AnchorPane.
   *
   * @param event when user clicks on '->'.
   */
  @FXML
  private void goFurtherAction(ActionEvent event) {
    if (firstLastPane.isVisible()) {
      try {
        userValidation.checkValidFirstname(nameField.getText());
        firstname = nameField.getText();
        userValidation.checkValidLastname(lastNameField.getText());
        lastname = lastNameField.getText();
        firstLastPane.setVisible(false);
        setLayout(empemailPane);
        empemailPane.setVisible(true);
        goBackButton.setVisible(true);
        errorMessageDisplay.setText("");
      } catch (IllegalArgumentException e) {
        errorMessageDisplay.setText(e.getMessage());
      }
    } else if (empemailPane.isVisible()) {
      try {
        String employeeid = employerIdField.getText();
        employeeNumber = 0;
        if (!employeeid.isEmpty()) {
          userValidation.checkValidEmployeeNumber(Integer.parseInt(employeeid));
          employeeNumber = Integer.parseInt(employeeid);
        } else {
          userValidation.checkValidEmployeeNumber(employeeNumber);
        }
        userValidation.checkValidEmail(emailField.getText());
        email = emailField.getText();
        empemailPane.setVisible(false);
        setLayout(socialPassPane);
        socialPassPane.setVisible(true);
        errorMessageDisplay.setText("");
      } catch (IllegalArgumentException e) {
        errorMessageDisplay.setText(e.getMessage());
      }
    } else if (socialPassPane.isVisible()) {
      try {
        userValidation.checkValidSocialNumber(socialField.getText());
        socialNumber = socialField.getText();
        String temppassword = passwordField.getText();
        String confirm = confirmPasswordField.getText();
        userValidation.isEqualPassword(temppassword, confirm);
        userValidation.checkValidPassword(temppassword);
        password = temppassword;
        socialPassPane.setVisible(false);
        setLayout(wageTaxPane);
        wageTaxPane.setVisible(true);
        createUserButton.setVisible(true);
        goOnButton.setVisible(false);
        errorMessageDisplay.setText("");
      } catch (IllegalArgumentException e) {
        errorMessageDisplay.setText(e.getMessage());
      }
    }
  }

  /**
   * This is the method that creates the user and adds it to "Accounts.json"
   * It checks if the last AnchorPane is visible and takes these arguments and validates
   * them before creating User. The method use both UserValidation() and AdminUser().
   *
   * @param event when user clicks on "Opprett bruker".
   * @throws IOException if something goes wrong when saving to Accounts.json.
   */
  @FXML
  private void createUserAction(ActionEvent event) throws IOException {
    SalaryCheckerPersistence persistence = new SalaryCheckerPersistence();
    if (wageTaxPane.isVisible()) {
      try {
        String tempwage = wageField.getText();
        wage = 0.0;
        if (! tempwage.isEmpty()) {
          userValidation.checkValidHourRate(Double.parseDouble(wageField.getText()));
          wage = Double.parseDouble(tempwage);
        } else {
          userValidation.checkValidHourRate(wage);
        }
        String temptax = taxField.getText();
        tax = 0.0;
        if (! temptax.isEmpty()) {
          userValidation.checkValidTaxCount(Double.parseDouble(taxField.getText()));
          tax = Double.parseDouble(temptax);
        } else {
          userValidation.checkValidTaxCount(tax);
        }
        adminUser.setAccounts(accounts);
        adminUser.createUser(firstname, lastname, email, password,
            socialNumber, employeeNumber, adminUser.getEmail(), tax, wage);
        errorMessageDisplay.setFill(Paint.valueOf("#008000"));
        errorMessageDisplay.setText("User created!");
      } catch (IllegalArgumentException e) {
        errorMessageDisplay.setText(e.getMessage());
      }
      persistence.setFilePath("Accounts.json");
      persistence.saveAccounts(accounts);
    }
  }

  /**
   * This is the method for handling "<-". It checks what AnchorPane is visible and
   * sets new AnchorPane based on this.
   *
   * @param event when user clicks on "<-".
   */
  @FXML
  private void goBackAction(ActionEvent event) {
    if (empemailPane.isVisible()) {
      empemailPane.setVisible(false);
      setLayout(firstLastPane);
      firstLastPane.setVisible(true);
      goBackButton.setVisible(false);
    } else if (socialPassPane.isVisible()) {
      socialPassPane.setVisible(false);
      setLayout(empemailPane);
      empemailPane.setVisible(true);
    } else if (wageTaxPane.isVisible()) {
      wageTaxPane.setVisible(false);
      setLayout(socialPassPane);
      socialPassPane.setVisible(true);
      goOnButton.setVisible(true);
      createUserButton.setVisible(false);
    }
  }


  /**
   * This is a helper method, for setting layout of AnchorPane that will be shown.
   *
   * @param pane that needs to change layout.
   */
  private void setLayout(AnchorPane pane) {
    pane.setLayoutX(40.0);
    pane.setLayoutY(370.0);
  }

}