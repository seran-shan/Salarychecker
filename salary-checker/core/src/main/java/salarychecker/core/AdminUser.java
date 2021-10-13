package salarychecker.core;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * This class makes it possible to use the app with admin privileges 
 */
public class AdminUser extends AbstractUser {

    private Accounts accounts;

    /**
     * Constructor
     * @param firstname
     * @param lastname
     * @param email
     * @param password
     */
    public AdminUser(String firstname, String lastname, String email, String password) {
        super.firstname = firstname;
        super.lastname = lastname;
        super.email = email;
        try {
            super.password = encryptDecrypt.encrypt(password, firstname + lastname);
        } catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidAlgorithmParameterException | BadPaddingException | IllegalBlockSizeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Empty constructor to use in json deserializer.
     */
    public AdminUser() {}

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }


    public void createUser(String firstname, String lastname, String email, String password, String socialNumber,
                           int employeeNumber, String employerEmail, double taxCount) {
        User user = new User(firstname, lastname, email, password, socialNumber, employeeNumber, employerEmail, taxCount);
        accounts.addUser(user);
    }


}