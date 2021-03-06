# Integration Test:
## Motivation for integrationtest:
The group has strived towards writing test-code for all classes and methods in the project. The project includes an application-test and mock-tests as well. An integrationtest might seem redundant. However, all individual tests might succeed and the modules still don't work together as intended. The integrationtest does exactly this - testing if seperate modules of the application work together as intended.  

## Implementation of Integrationtest in SalaryChecker
The integrationtest-module verifies that the javafx client application properly connects and communicates with a spring boot server. 

By using maven and the safe-fail plugin the integrationtest-module, when testing, will start the server-application with a test-configuration.

### Note: 
The group feels like the integrationtest could have been more thourogh by testing more methods. However, the group chose to downprioritize this due to time-limits.