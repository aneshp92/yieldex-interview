# Overview

The goal of this assessment is to create an API for bank account users. The database for this API should be seeded with the data from the following url

[seed data download](https://public-interview.s3.amazonaws.com/transactions.zip)

# Schema

```
User {
  userId
  firstName
  lastName
  email
  password
}
```

```
Merchant {
  merchantId
  name
  latitude
  longitude
  address
}
```

```
Transaction {
  userId
  merchantId
  amountInCents
  timestamp
}
```

# Required operations

1. Create a new user (_25 points_) -- DONE
- Avoid duplicates on the user profile by email address
- Generate a userId for a new user
- Do not store the password in plain text

2. Update a user (_25 points_) -- DONE
- Update the first or the last name
- Update email
- Update password
- *Bonus: authenticate the update operation with basic auth using the email and password* (_10 points_) --TODO

3. Retrieve a user balance (_25 points_) -- DONE
- For all the transactions for a userId, sum the amountInCents. This derives the user balance. amountInCents can be positive (a credit) or negative (a debit)

4. Authorize a transaction by balance (_25 points_) -- DONE
- Add an endpoint which makes a request to a user with a transaction
- If the user has sufficient balance for the transaction, return an approval code
- If the user has insufficient balance for the transaction, return a decline code

# Additional requirements

1. Unit tests -- DONE
2. Input validation -- DONE
3. Data Models & Schemas -- DONE

# Bonus operations

1. Add the ability to recieve transactions for a user -- DONE
- Lookup between a starting and ending timestamp (_5 points_)
- Lookup by merchantId (_5 points_)
- Add pagination to both queries (_5 points_)

2. Summarize the amount a user has spent at each merchant (_10 points_) -- DONE

3. Enrich merchants with a google place location using the Google Places API (_15 points_) -- Not doing (Requires me to pay for it)

# Grading

The maximum score for the assignment is 150 points. 90 points is the passing grade. If you assignment fails to pass, you will be provided your grade with the reasons why.

# Delivery

Once complete, please attach @pseudosky as a git project collaborators.


#Running the Application

1. Start Application using maven runner `clean install spring-boot:run`
2. Run /normalizeUsersAndMerchants to normalize data (found in postman collection below)


#Postman Collection:
https://www.getpostman.com/collections/59aaeac2b41ff9b16bf9
