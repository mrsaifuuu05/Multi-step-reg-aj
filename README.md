# Multi-Step Registration Form

## Student Details

| Field       | Details                              |
|-------------|--------------------------------------|
| Name        | MOHAMMAD SAIF MAINUDDIN SHAIKH  |
| USN         | 2BL24CS409         |
| Branch      | Computer Science & Engineering       |
| Semester    | VI Semester                          |
| Subject     | Advanced Java Programming            |
| Problem No. | Problem 23                           |

## Problem Statement

This is a Multi-Step Registration Form built using Java Servlets and HttpSession. In Step 1, the user enters their Name and Email, which are stored in an HttpSession on the server. The user is then redirected to Step 2 where they enter their Mobile Number and City. The Step 2 Servlet retrieves the Name and Email from the session using session.getAttribute(), combines them with the new inputs, and displays a complete registration summary of all four fields.

## Technologies Used

- Java (Servlets)
- HTML, CSS (inline)
- Apache Tomcat 10
- Eclipse IDE

## How to Run This Project

1. Clone this repository or download the ZIP.
2. Import the project into Eclipse as a Dynamic Web Project.
3. Add Apache Tomcat 10 as the server in Eclipse.
4. Right-click project → Run As → Run on Server.
5. Open browser and go to: `http://localhost:8080/MultiStepRegistration/index.html`

## Screenshots

### Step 1 – Input Form (Name & Email)
![Step 1 Form](screenshots/screenshot1.png)

### Step 2 – Input Form (Mobile & City)
![Step 2 Form](screenshots/screenshot2.png)

### Output – Registration Summary
![Registration Summary](screenshots/screenshot3.png)

## Servlet Concept Practiced

HttpSession, session.setAttribute(), session.getAttribute(), session.invalidate(), Multi-step form flow, Input Validation, doGet/doPost
