package salarychecker.ui;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

import salarychecker.core.AbstractUser;
import salarychecker.core.Accounts;
import salarychecker.core.User;
import salarychecker.json.SalaryCheckerPersistence;

/**
 * A implementation of {@link SalaryCheckerAccess}
 */
public class RemoteSalaryCheckerAccess implements SalaryCheckerAccess {

    private final URI baseURI;
    private ObjectMapper objectMapper;
    private Accounts accounts;

    /**
     * This constructor initialize the objectmapper used for serializing, 
     * and sets the baseURI.
     *
     * @param baseURI URL used for HTTP requests.
     */
    public RemoteSalaryCheckerAccess(URI baseURI) {
        this.baseURI = baseURI;
        this.objectMapper = SalaryCheckerPersistence.createObjectMapper();
    }

    /**
     * Creates a URI by resolving the input string against the uri for 
     * fetching from the server.
     *
     * @param URI the path.
     * @return the URI on the server with the given path.
     */
    public URI resolveURIAccounts(String URI) {
        return baseURI.resolve(URLEncoder.encode(URI, StandardCharsets.UTF_8));
    }


    /**
     * Sends a GET-request to fetch a Accounts object from the
     * server.
     */
    @Override
    public Accounts readAccounts() {
        if (accounts == null) {
            HttpRequest httpRequest = HttpRequest.newBuilder(baseURI)
                                                 .header("Accept", "application/json")
                                                 .GET()
                                                 .build();
            try {
                final HttpResponse<String> httpResponse = 
                HttpClient.newBuilder()
                          .build()
                          .send(httpRequest, HttpResponse.BodyHandlers.ofString());

                this.accounts = objectMapper.readValue(httpResponse.body(), Accounts.class);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return accounts;
    }
    

    /**
     * Sends a GET-request to fetch a User object with the corresponding email from the
     * server.
     *
     * @param email the email of the User to be returned
     * @return the user with the right email
     */
    @Override
    public User readUser(String email) {
        String getMappingPath = "user?";
        String key = "email";
        String value = email;
        try {
            HttpRequest request = 
                HttpRequest.newBuilder(resolveURIAccounts(getMappingPath + key + value))
                           .header("Accept", "application/json")
                           .GET()
                           .build();
            final HttpResponse<String> httpResponse =
                    HttpClient.newBuilder()
                              .build()
                              .send(request, HttpResponse.BodyHandlers.ofString());

            return objectMapper.readValue(httpResponse.body(), User.class);
          } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
          }
    }

    /**
     * Sends a GET-request to fetch a User object with the corresponding email from the
     * server.
     *
     * @param employerEmail the employerEmail of the Accounts to be returned
     * @return the accounts with the right email
     */
    @Override
    public List<AbstractUser> readAccountsWithSameEmployer(String employerEmail) {
        String getMappingPath = "users?";
        String key = "employerEmail";
        String value = employerEmail;
        try {
            HttpRequest request = 
                HttpRequest.newBuilder(resolveURIAccounts(getMappingPath + key + value))
                           .header("Accept", "application/json")
                           .GET()
                           .build();
            final HttpResponse<String> httpResponse =
                    HttpClient.newBuilder()
                              .build()
                              .send(request, HttpResponse.BodyHandlers.ofString());

            return objectMapper.readValue(httpResponse.body(), List.class);
          } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
          }
    }

    /**
     * Sends a POST-request to activate a new user login.
     */
    @Override
    public void userLogin(String email, String password) {
        String postMappingPath = "login?";
        String key1 = "email";
        String value1 = email + "&";
        String key2 = "password";
        String value2 = password;
        
        try {
        HttpRequest httpRequest = HttpRequest
                .newBuilder(resolveURIAccounts(postMappingPath + key1 + value1 + key2 + value2))
                .header("Accept", "application/json")
                .POST(BodyPublishers.ofString(email + "|" + password))
                .build();
 
                HttpClient.newBuilder()
                          .build()
                          .send(httpRequest, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sends a POST-request to register a new Accounts object to use in the app.
     * 
     * @param accounts the accounts to register.
     */
    @Override
    public void registerNewAccounts(Accounts accounts) {
        try {
            String json = objectMapper.writeValueAsString(accounts);
            HttpRequest request = HttpRequest.newBuilder()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .POST(BodyPublishers.ofString(json))
                    .build();
            
            HttpClient.newBuilder()
                      .build()
                      .send(request, HttpResponse.BodyHandlers.ofString());
            
          } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
          }
    }

    /**
     * Sends a POST-request to register a new User object to use in the app.
     * 
     * @param user the user to register
     */
    @Override
    public void createUser(User user) {
        String postMappingPath = "create-user";
        try {
            String json = objectMapper.writeValueAsString(user);
            HttpRequest httpRequest = HttpRequest.newBuilder(resolveURIAccounts(postMappingPath))
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .POST(BodyPublishers.ofString(json))
                    .build();
            
            HttpClient.newBuilder()
                      .build()
                      .send(httpRequest, HttpResponse.BodyHandlers.ofString());
            
          } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
          }
    }

    /**
     * Sends a PUT-request and updates the attribute of the user
     *
     * @param user the user to update
     */
    @Override
    public void updateUserAttributes(User user) {
        String putMappingPath = "user/update-profile";
        try {
            String json = objectMapper.writeValueAsString(user);
            HttpRequest httpRequest = HttpRequest.newBuilder(resolveURIAccounts(putMappingPath))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .PUT(BodyPublishers.ofString(json))
                .build();
            
            HttpClient.newBuilder()
                        .build()
                        .send(httpRequest, HttpResponse.BodyHandlers.ofString());
           
          } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
          }
      
    }

    /**
     * Sends a DELETE-request to delte the Accounts objects used in the server.
     */
    @Override
    public void deleteAccounts() {
        try {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                                             .DELETE()
                                             .build();
 
        HttpClient.newBuilder()
                  .build()
                  .send(httpRequest, HttpResponse.BodyHandlers.ofString());
                          
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}