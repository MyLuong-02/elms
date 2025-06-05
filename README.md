# further-programming-assignment-1-build-a-console-app-My-Luong
 **RMIT University Vietnam**
**COSC2731 - Further Programming** 
**Semester: Semester 1 - 2025**
**Assignment 1: Build a Console App (Individual Project)**
**Author: Luong Thi Tra My - s3987023**

# Project name: Equipment Lending Management App

## Assignment Overview 
This project is developed as **Assignment 1** for the **COSC2371 Further Programming** course. The task is to design and implement a **Java console-based application** that manages the lending of university-owned equipment and tools.

The system follows **object-oriented programming (OOP)** principles and allows administrators to view university members, manage equipment inventory, and lending records efficiently.

---

## Features 
### Core features:
- View all university members, including **students**, **staff (academics & professionals)**
- View all **equipment**, and **lending records**
- View lending records by borrower and equipment 
- Manage **inventory (equipment)**, including equipment addition, update, and removal.
- Manage **lending records**, including lending record addition, update, and deletion.
- - Tracks **overdue lending records** and **available equipment**
- Generate reports for **overdue lending records** and **available equipment** and export them to text files
  
  ### Supporting features:
- Includes supervisor validation for student borrowers
- Maintain a list of lending records for each student borrowing 
- View a list of student that a staff is supervising 
- Automatically updates equipment status depended on the modifications of lending records
- Prevents duplicate IDs and validates all user input
- Ask for confirmation before any deletion of equipment or lending record
- Prevent typo or unmaching patterns by providing selection list for static values such as statuses for equipment and lending records, condition for equipment 
- Always display information for the admin to view before making any modifications 
- Always display List of IDs along with specific actions to assist the admin in typing it correctly and also help them avoid unexpected errors 
- Implment related error handling functions to prevent scrash during program running

---
## 🏗️ Project Structure
console_app/src
├── controller/
│ ├── InputValidation.java
│ ├── InventoryManager.java
│ ├── InventoryService.java
│ ├── LendingManager.java
│ ├── LendingService.java
│ ├── StaffController.java
│ ├── StudentController.java
├── helper/ 
│ ├── EquipmentDataLoader.java
│ ├── EquipmentDataProcessor.java
│ ├── EquipmentDataWriter.java
│ ├── LendingRecordDataLoader.java
│ ├── LendingRecordDataWriter.java
│ ├── LendingRecordProcessor.java
│ ├── StaffDataLoader.java
│ ├── StaffDataProcessor.java
│ ├── StudentDataLoader.java
│ ├── StudentDataProcessor.java
├── model/
│ ├── Academic.java
│ ├── Borrower.java
│ ├── Equipment.java
│ ├── LendingRecord.java
│ ├── Person.java
│ ├── Professional.java
│ ├── Staff.java
│ ├── Student.java
├── utils/
│ ├── DateUtils.java
│ ├── ReportGenerator.java
├── view/
│ ├── InventoryServiceView.java
│ ├── LendingServiceView.java
│ ├── MenuView.java
│ ├── PersonView.java
console_app/database/
├── equipment.txt
├── lending_records.txt
├── staff.txt
├── students.txt

---

### Technologies 
- Java 17+
- No external libraries (Standard Java SE only)
- Console-based interface
- Data persistence via `.txt` files
  
---

## How to Run 

1. Clone the repository from Github 
   1. Link to the Github repository: https://github.com/RMIT-Vietnam-Teaching/further-programming-assignment-1-build-a-console-app-My-Luong
2. Run the application: 
   1. Open IntelliJ 
   2.  Press **Open Project**" button and select the foler that contains source code just cloned
   3.    Open this folder 
   4.      Go to the main file to run the application following this path: console_app/src/view/MenuView.java
   5.      After openning MenuView.java file, right-click on it and press "Run" button to run the program 
3.       Follow the on-screen menu options to view students, staff, equipment and lending records, manage equipment, and lending records.


## Key Concept Applied 

- Object-Oriented Programming (OOP)
  - Inheritance 
  - Polymorphism 
  - Abstraction
  - Encapsulation 
- Interfaces and abstract classes 
- Java Collection Framework (JCF)
  - ArrayList
  - HashMap 
  - HashSet
- File I/O
  - BufferedReader and BufferedWriter
- Exception Handling 
- Input Validation & User-Friendly Console Interaction
- Seperation of concerns following MVC  

## Testing 
- Manual testing is performed via the console-based menu.

