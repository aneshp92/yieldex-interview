Steps to run application

1. Create MongoDB & Import transactions.csv
    - `brew tap mongodb/brew`
    - `brew tap | grep mongodb`
    - `brew install mongodb-community@4.4`
    - `brew services start mongodb/brew/mongodb-community`
    - `mongo`
    - `mongoimport --type csv -d yieldex -c transactions --headerline <path to transactions.csv>`
    
2. 