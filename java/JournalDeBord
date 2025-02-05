// Day 1  
For this session, we manually tested the website. To speed up the process, we divided our work into four parts for the 4 members of our group.  

### Issues encountered:  
- Some tests were redundant, leading to a waste of time.  
- Lack of methodology in task distribution, causing overlaps in testing.  


// Day 2  
For this session, we first generated a test script using Playwright. Then, we wrote three automated tests, resetting the database after each test.  

### Issues encountered:  
- Resetting the database after each test caused data loss, preventing other testers from retrieving the necessary information for their tests.  
- Some tests failed randomly due to a data synchronization issue.  


// Day 3
For this session, we first continued the refactoring of our code by creating the EmployeePage, HomePage and TeamPage files concerning the codes and steps common to all our tests which are in dmerej foder. Next we make a list of the advantages and disadvantages of resetting the database, the details of which are in the following lines.

List of pros and cons of resetting the database between each test:


advantages:

- Avoid redundancy in code when running tests
- Ensure a concurrent environment for all tests
- Reduced and optimized run-time when database is empty

Disadvantages

- Makes tests longer/slower
- Heavier to maintain since a change in base code will affect many classes


# Methods to Avoid Resetting the Database

## 1. Using Transactions
### Concept
Tests are executed within a transaction. If all tests pass, we can retrieve all commands between `BEGIN` and `COMMIT`, and delete them from the database. If a test fails, the transaction is rolled back.

### Advantages
- Prevents the database from being populated with unnecessary data.
- Ensures data integrity by rolling back changes if a test fails.

### Disadvantages
- May cause issues when tests involve multiple connections or external services.
- Some databases do not support transactions for all operations (e.g., certain DDL queries).

---

## 2. Using Test Fixtures
### Concept
Load a specific dataset before each test and ensure it is removed afterward.

### Advantages
- Guarantees a stable and predictable database state.
- Easy to manage and modify according to test needs.

### Disadvantages
- Can slow down test execution if data loading takes too long.
- Risk of test dependencies if data is not well isolated.

---

## 3. Mocking the Database
### Concept
Use a framework like Mockito to simulate database calls and avoid real insertions.

### Advantages
- Tests run faster since no actual database queries are executed.
- Avoids issues related to database resets.

### Disadvantages
- Less representative of a real environment, which may hide database-related bugs.
- Requires maintaining mocks as the database schema evolves.
