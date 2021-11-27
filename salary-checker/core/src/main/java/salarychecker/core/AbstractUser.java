package salarychecker.core;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Abstract class that contains the mutual implementation for
 * User and AdminUser.
 */
public abstract class AbstractUser {

  protected String firstname;
  protected String lastname;
  protected String email;
  protected String password;

  protected UserValidation userValidation = new UserValidation();
  protected Collection<IUserObserver> userObs = new ArrayList<>();

  
  /**
   * Access method for firstname.
   *
   * @return the firstname
   */
  public String getFirstname() {
    return firstname;
  }

  /**
   * Mutation method for new firstname.
   *
   * @param firstname the firstname to set
   */
  public void setFirstname(String firstname) {
    userValidation.checkValidFirstname(firstname);
    this.firstname = firstname;
    for (IUserObserver userObserver : userObs) {
      userObserver.userInfoChanged(this);
    }
  }

  /**
   * Access method for lastname.
   *
   * @return the lastname
   */
  public String getLastname() {
    return lastname;
  }

  /**
   * Mutation method for new lastname.
   *
   * @param lastname the firstname to set
   */
  public void setLastname(String lastname) {
    userValidation.checkValidLastname(lastname);
    this.lastname = lastname;
    for (IUserObserver userObserver : userObs) {
      userObserver.userInfoChanged(this);
    }
  }

  /**
   * Access method for email.
   *
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Mutation method for new email.
   *
   * @param email the firstname to set
   */
  public void setEmail(String email) {
    userValidation.checkValidEmail(email);
    this.email = email;
    for (IUserObserver userObserver : userObs) {
      userObserver.userInfoChanged(this);
    }
  }

  /**
   * Access method for password.
   *
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Mutation method for new password.
   *
   * @param password the firstname to set
   */
  public void setPassword(String password) {
    userValidation.checkValidPassword(password);
    this.password = password;
    for (IUserObserver userObserver : userObs) {
      userObserver.userInfoChanged(this);
    }
  }

  /**
   * Method to add a new observer of this class.
   *
   * @param userObserver the observer
   */
  public void addObserver(IUserObserver userObserver) {
    userObs.add(userObserver);
  }

  /**
   * Removes a observer of this class.
   *
   * @param userObserver the observer to remove
   */
  public void removeObserver(IUserObserver userObserver) {
    userObs.remove(userObserver);
  }

  /**
   * Access method for userObs.
   *
   * @return the userObs
   */
  public Collection<IUserObserver> getUserObs() {
    return new ArrayList<>(userObs);
  }

}
