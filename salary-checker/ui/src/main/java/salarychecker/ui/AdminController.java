package salarychecker.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import salarychecker.core.*;
import salarychecker.json.SalaryCheckerPersistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminController {

    AdminUser adminUser = new AdminUser();
    Accounts accounts = new Accounts();
    SalaryCheckerPersistence SCP = new SalaryCheckerPersistence();
    ObservableList<String> nameOfUsers = FXCollections.observableArrayList();

    @FXML private Text adminName;
    @FXML private TextField createFirstNameField;
    @FXML private TextField createLastNameField;
    @FXML private TextField createEmailField;
    @FXML private TextField createPasswordField;
    @FXML private TextField createEmployeeNumberField;
    @FXML private TextField createSocialNumberField;
    @FXML private TextField createTaxField;
    @FXML private TextField createWageField;
    @FXML private Text errorMessageDisplay;
    @FXML private ListView<String> userList;

    public void loadListView() {
        userList.getItems().clear();
        List<AbstractUser> tempuserlist;
        tempuserlist = accounts.getAccounts().stream().filter(u-> u instanceof User).collect(Collectors.toList());
        for (AbstractUser u : tempuserlist) {
            String name = u.getFirstname() + " " + u.getLastname();
            nameOfUsers.add(name);
        }
        userList.setItems(nameOfUsers);
    }

    public void loadInfo(){
        String name = adminUser.getFirstname() + " " + adminUser.getLastname();
        adminName.setText(name);
    }

    public void setAdminUser(AdminUser adminUser) {
        this.adminUser = adminUser;
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }

    @FXML
    private void createUserAction(ActionEvent event) throws IOException {
        String firstname = createFirstNameField.getText();
        String lastname = createLastNameField.getText();
        String email = createEmailField.getText();
        String password = createPasswordField.getText();
        String tempemployeeN = createEmployeeNumberField.getText();
        int employeenumber = 0;
        if (! tempemployeeN.isEmpty()){
                employeenumber = Integer.parseInt(tempemployeeN);
        }
        String socialnumber = createSocialNumberField.getText();
        String temptaxcount = createTaxField.getText();
        Double taxcount = 0.0;
        if (! temptaxcount.isEmpty()){
            taxcount = Double.valueOf(temptaxcount);
        }
        String temphourwage = createWageField.getText();
        Double hourwage = 0.0;
        if (! temphourwage.isEmpty()){
            hourwage = Double.valueOf(temphourwage);
        }
        try {
            adminUser.setAccounts(accounts);
            adminUser.createUser(firstname, lastname, email, password, socialnumber, employeenumber, adminUser.getEmail(), taxcount, hourwage);
            createFirstNameField.clear();
            createLastNameField.clear();
            createEmailField.clear();
            createPasswordField.clear();
            createEmployeeNumberField.clear();
            createSocialNumberField.clear();
            createTaxField.clear();
            createWageField.clear();
        }
        catch (IllegalArgumentException e){
            errorMessageDisplay.setText(e.getMessage());
        }
        loadListView();
        SCP.setSaveFile("Accounts.json");
        SCP.saveAccounts(accounts);
    }

    @FXML
    private void logOutAction(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
            Parent root = fxmlLoader.load();
            Scene homepageScene = new Scene(root);
            Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow());
            window.setScene(homepageScene);
            window.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



}