package salarychecker.core;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

    private User testUser;
    private UserSale testPeriod;

    @BeforeEach
    public void setUp() {
        testUser = new User("Firstname", "Lastname", "email@email.com", "password!123", 
            "22019893456", 33333, "employer_email@email.com", 35.5, 132.0);
        testPeriod = new UserSale("PeriodString" , 1000.0, 500.0);
        testPeriod.setDifference();
    }

    @Test
    public void testConstructors() {
        User emptyConstructor = new User();
        Assertions.assertNull(emptyConstructor.getEmail());
        User smallConstructor = new User("Firstname", "Lastname", "email@email.com", 12345);
        assertEquals("Firstname", smallConstructor.getFirstname());
        assertEquals("Lastname", smallConstructor.getLastname());
        assertEquals("email@email.com", smallConstructor.getEmail());
        assertEquals(12345, smallConstructor.getEmployeeNumber());
    }

    @Test
    public void testGetMethods() {
        Assertions.assertEquals("Firstname", testUser.getFirstname());
        Assertions.assertEquals("Lastname", testUser.getLastname());
        Assertions.assertEquals("email@email.com", testUser.getEmail());
        Assertions.assertEquals("password!123", testUser.getPassword());
        Assertions.assertEquals("22019893456", testUser.getSocialNumber());
        Assertions.assertEquals(33333, testUser.getEmployeeNumber());
        Assertions.assertEquals("employer_email@email.com", testUser.getEmployerEmail());
        Assertions.assertEquals(35.5, testUser.getTaxCount());
        Assertions.assertTrue( 0 == testUser.getUserSaleList().size());
    }

    @Test
    public void testSetMethods() {
        Accounts accounts = new Accounts();
        accounts.addUser(testUser);
        testUser.addObserver(accounts);
        testUser.setFirstname("Sander");
        Assertions.assertNotEquals("Firstname", testUser.getFirstname());
        testUser.setLastname("Olsen");
        Assertions.assertNotEquals("Lastname", testUser.getLastname());
        testUser.setEmail("email@gmail.com");
        Assertions.assertNotEquals("email@email.com", testUser.getEmail());
        testUser.setPassword("Passord!1");
        Assertions.assertNotEquals("password!123", testUser.getPassword());
        testUser.setSocialNumber("22030199999");
        Assertions.assertNotEquals("22019893456", testUser.getSocialNumber());
        testUser.setEmployeeNumber(11111);
        Assertions.assertNotEquals(33333, testUser.getEmployeeNumber());
        testUser.setEmployerEmail("employer_email@gmail.com");
        Assertions.assertNotEquals("employer_email@email.com", testUser.getEmployerEmail());
        testUser.setTaxCount(34.5);
        Assertions.assertNotEquals(35.5, testUser.getTaxCount());
        testUser.setHourRate(145);
        Assertions.assertEquals(145, testUser.getHourRate());
    }

    @Test
    public void testIsExistingUserSale(){
        UserSale notTestPeriod = new UserSale("notUserString", 1, 1);
        testUser.addUserSale(testPeriod);
        Assertions.assertTrue(testUser.isExistingUserSale(testPeriod));
        Assertions.assertFalse(testUser.isExistingUserSale(notTestPeriod));
    } 

    @Test
    public void testAddUserSale(){
        testUser.addUserSale(testPeriod);
        Assertions.assertTrue(1 == testUser.getUserSaleList().size());
        testUser.addUserSale(testPeriod);
        Assertions.assertTrue(1 == testUser.getUserSaleList().size());
    }

    @Test
    public void testObserver(){
        Accounts accounts = new Accounts();
        accounts.addUser(testUser);
        testUser.addObserver(accounts);
        assertTrue(testUser.getUserObs().size() == 1);
        testUser.removeObserver(accounts);
        assertFalse(testUser.getUserObs().size() == 1);
    }

    @Test
    public void testGetUserSale() {
        testUser.addUserSale(testPeriod);
        assertEquals(testPeriod, testUser.getUserSale("PeriodString"));
    }

}
