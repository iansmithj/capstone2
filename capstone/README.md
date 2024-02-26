# Module 2 Capstone - TEnmo

Congratulations—you've landed a job with TEnmo, whose product is an online payment service for transferring "TE bucks" between friends. However, they don't have a product yet. You've been tasked with writing the backend, a RESTful API server.

## Authentication
The user registration and authentication functionality for the system has already been implemented. If you review the login code, you'll notice that after successful authentication, an instance of `AuthenticatedUser` is stored in the `currentUser` member variable of `App`. The user's authorization token (i.e JWT) can be accessed from `App` as `currentUser.getToken()`.

When the use cases refer to an "authenticated user", this means a request that includes the token as a header. You can also reference other information about the current user by using the `User` object retrieved from `currentUser.getUser()`.

## Application Use Cases
These use cases are written from the user's perspective. the API being built must provide the capabilities
necessary to support them.

1. **[DONE]** As a user of the system, I need to be able to register myself with a username and password.
   1. A new registered user starts with an initial balance of 1,000 TE Bucks.
   2. Note: The ability to register has been provided in your starter code.
2. **[DONE]** As a user of the system, I need to be able to log in using my registered username and password.
   1. Logging in returns an Authentication Token. I need to include this token with all my subsequent interactions with the system outside of registering and logging in.
   2. Note: The ability to log in has been provided in your starter code.
3. As an authenticated user of the system, I need to be able to see my Account, including the balance.
4. As an authenticated user of the system, I need to be able to *send* a transfer of a specific amount of TE Bucks to a registered user.
   1. I should be able to choose from a list of users to send TE Bucks to.
   2. I must not be allowed to send money to myself.
   3. A transfer includes the User IDs of the _from_ and _to_ users and the amount of TE Bucks.
   4. The receiver's account balance is increased by the amount of the transfer.
   5. The sender's account balance is decreased by the amount of the transfer.
   6. I can't send more TE Bucks than I have in my account.
   7. I can't send a zero or negative amount.
5. As an authenticated user of the system, I need to be able to see transfers I have sent or received.
6. As an authenticated user of the system, I need to be able to retrieve the details of any transfer based upon the transfer ID.
7. As an authenticated user of the system, I need to be able to *request* a transfer of a specific amount of TE Bucks from another registered user.
   1. I should be able to choose from a list of users to request TE Bucks from.
   2. I must not be allowed to request money from myself.
   3. I can't request a zero or negative amount.
   4. A transfer includes the User IDs of the _from_ and _to_ users and the amount of TE Bucks.
   5. No account balance changes until the request is approved.
   6. The transfer request should appear in both users' list of transfers (use case #5).
8. As an authenticated user of the system, I need to be able to see my pending transfers.
9. As an authenticated user of the system, I need to be able to either approve or reject a Request Transfer.
   1. I can't "approve" a given Request Transfer for more TE Bucks than I have in my account.
   2. If the transfer is approved, the requester's account balance is increased by the amount of the request.
   3. If the transfer is approved, the requestee's account balance is decreased by the amount of the request.
   4. If the transfer is rejected, no account balance changes.


## Database schema

### `tenmo_user` table

Stores the login information for users of the system.

| Field           | Description                                                                    |
|-----------------|--------------------------------------------------------------------------------|
| `user_id`       | Unique identifier of the user                                                  |
| `username`      | String that identifies the name of the user; used as part of the login process |
| `password_hash` | Hashed version of the user's password                                          |
| `role`          | Name of the user's role                                                        |

## How to set up the database

Create a new Postgres database called `tenmo`. Run the `database/tenmo.sql` script in pgAdmin to set up the database.

### Datasource

A Datasource has been configured for you in `/src/resources/application.properties`. 

```
# datasource connection properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tenmo
spring.datasource.name=tenmo
spring.datasource.username=postgres
spring.datasource.password=postgres1
```

### JdbcTemplate

If you look in `/src/main/java/com/techelevator/dao`, you'll see `JdbcUserDao`. This is an example of how to get an instance of `JdbcTemplate` in your DAOs. If you declare a field of type `JdbcTemplate` and add it as an argument to the constructor, Spring automatically injects an instance for you:

```java
@Service
public class JdbcUserDao implements UserDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
```

## Testing


### DAO integration tests

`com.techelevator.dao.BaseDaoTests` has been provided for you to use as a base class for any DAO integration test. It initializes a Datasource for testing and manages rollback of database changes between tests.

`com.techelevator.dao.JdbUserDaoTests` has been provided for you as an example for writing your own DAO integration tests.

Remember that when testing, you're using a copy of the real database. The schema and data for the test database are defined in `/src/test/resources/test-data.sql`. The schema in this file matches the schema defined in `database/tenmo.sql`.

