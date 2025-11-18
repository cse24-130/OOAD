# Banking System - OOAD Assignment

## Overview
This is a Banking System application developed as part of the Object-Oriented Analysis and Design (OOAD) course. The system allows customers to manage multiple types of bank accounts including Savings, Investment, and Cheque accounts.

## Features
- Customer registration and authentication
- Multiple account types:
  - **Savings Account**: Pays 0.05% monthly interest, no withdrawals allowed
  - **Investment Account**: Pays 5% monthly interest, requires minimum BWP 500.00 initial deposit
  - **Cheque Account**: For salary payments, allows deposits and withdrawals with overdraft facility
- Deposit and withdrawal operations
- File-based data persistence (no database required)
- Modern JavaFX GUI with professional styling

## Currency
All monetary values are in **Botswana Pula (BWP)**.

## Project Structure

### Core Model Classes
- `Account.java` - Abstract base class for all account types
- `Customer.java` - Customer entity
- `SavingsAccount.java` - Savings account implementation
- `InvestmentAccount.java` - Investment account implementation
- `ChequeAccount.java` - Cheque account implementation
- `InterestBearing.java` - Interface for interest-bearing accounts

### Data Access Layer (File-based)
- `CustomerDAO.java` - Customer data persistence
- `AccountDAO.java` - Account data persistence

### Controllers
- `LoginController.java` - Handles user authentication
- `AccountController.java` - Manages account operations

### Application
- `BankingApplication.java` - Main JavaFX application entry point
- `InterestPaymentService.java` - Service for paying monthly interest
- `SampleDataInitializer.java` - Utility to create sample data

### GUI
- `LoginView.fxml` - Login screen
- `AccountView.fxml` - Account management interface
- `styles.css` - Application styling

## Setup and Running

### Prerequisites
- Java JDK 11 or higher
- JavaFX SDK (compatible with your JDK version)

### Running the Application

1. **Initialize Sample Data** (First time only):
   ```bash
   java SampleDataInitializer
   ```
   This creates 3 sample customers with accounts that you can use for testing.

2. **Run the Application**:
   ```bash
   java BankingApplication
   ```
   Or configure your IDE to run `BankingApplication` as the main class.

### Login Credentials
After initializing sample data, you can login with:
- ID Number: `ID001`, `ID002`, or `ID003`
- Password: (any password - authentication is simplified for this assignment)

## Data Storage
All data is stored in the `data/` directory:
- `data/customers.txt` - Customer information
- `data/accounts.txt` - Account information

## OOP Principles Demonstrated
- **Abstraction**: Abstract `Account` class
- **Inheritance**: `SavingsAccount`, `InvestmentAccount`, `ChequeAccount` extend `Account`
- **Polymorphism**: Different account types implementing common interface
- **Encapsulation**: Private fields with public getters/setters
- **Interfaces**: `InterestBearing` interface for interest calculations
- **Method Overriding**: Each account type overrides `deposit()` and `withdraw()`

## Interest Rates
- **Savings Account**: 0.05% monthly
- **Investment Account**: 5% monthly

## Account Requirements
- **Investment Account**: Minimum initial deposit of BWP 500.00
- **Cheque Account**: Requires company name and address (for working customers)
- **Savings Account**: No withdrawals allowed

## Notes
- Account numbers are auto-generated
- All amounts are displayed in BWP (Botswana Pula)
- The system uses file-based persistence instead of a database
- Colors and styling are applied through CSS

