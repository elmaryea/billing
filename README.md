This program is to assist with billing for home based child care providers.

I don't currently have a name for the program, beyond the repo name. Currently the database is being hosted on amazon to allow for development in multiple locations. When the program is completed though a mysql installer will be packaged with the installer for the program so that it can be used offline.

Database Schema:
- Accounts (Account_ID, First_Name_1, Last_Name_1, First_Name_2, Last_Name_2, Home_Phone, Cell_Phone, Balance, Business_ID)
- Addresses (Address_ID, Street, City, State, Zip_Code, Account_ID, Payer_ID),
- Businesses (Business_ID, Business_Name)
- Children (Child_ID, First_Name, Last_Name, Balance, Account_ID)
- Payers (Payer_ID, First_Name, Last_Name, Home_Phone, Cell_Phone, Account_ID)
- Payment (Payment_ID, Date_Paid, Amount_Paid, Balance, Cash, Check_Number, Account_ID, Payer_ID, Payment_ID_Ref, Child_ID)
- User_Business (Business_ID, User_ID)
- Users (User_ID, Username, First_Name, Last_Name, Email_Address, Last_Pass_Change, Last_Program_Open)



Payments are a little bit convoluted, so below is a little background about how it is layed out and supposed to work:
*  Account represents a family. The account supports two parents (first and last names. Although the this could be changed for more than two parents.). Each account also has a set of payers who can be for the account. The parents are automatically added as payers, however others can be added as well (ie. grandparents). Each account account also has a set of children who will be payed for by one or more of the account payers. The account has a total balance which is equal to the sum of the balances of the children.
  
* When a payment is made by a payer a few things happen. If there is only one child, then the payment is added to the payments table with the relevent account, payer and child information. This happens regarless of whether the account  balance is being paid in full. If there is more than one child, a few things could happen. If the account balance is being paid in full, then the full payment is added to the payment table with the relevent Account and Payment information, but the child information is left blank. In addition payments are added to the table for each child with a reference the payment entry that contains the full amount. If the account balance is not being paid in full then the program will need the split (how much of the payment is going torwards each child). Then the full payment is put in as well as any additional payments as needed. If the account has multiple children, but a payment is only going torwards one child then only one payment needs to be added to the table.
