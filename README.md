# API Automation using Rest-Assured & TestNG

This project automates API testing for [Daily Finance](https://dailyfinance.roadtocareer.net/) using **Postman** for API inspection and collection, and **Rest Assured** with **TestNG** in **Java** for automation. The automation follows the **Page Object Model (POM)** architecture and includes both positive and negative test cases. Allure is used for detailed test reporting.

## Technologies Used

- **Java (JDK 17)**
- **IntelliJ IDEA**
- **Gradle**
- **Postman**
- **Rest-Assured**
- **TestNG**
- **Allure Reporting**

## How to Run

- **Install JDK 17 and IntelliJ IDEA.**   
- **Clone the repository**  
   - `git clone https://github.com/Naila-Nur99/API-Automation-using-RestAssured-TestNG.git>`
- **Import the project** into IntelliJ and ensure dependencies (Rest Assured, TestNG, Allure) are set in
  - `build.gradle `
- **Test API endpoints** using the browser's **Network tab** and **Postman**, then export the collection.
- **Run tests:**  
   - `gradle clean test` or use IntelliJ’s **Run** button. 
- **Generate Allure report:**
   - `allure serve allure-results` 
  

## Postman Collection 

   - [Documentation Link](https://documenter.getpostman.com/view/42658049/2sB2j1hCNe)

## Test Case Link

   - [Positive & Negative Test cases](https://docs.google.com/spreadsheets/d/1N9j8EQozoovMP1AyqIf5ZnT4ZPaIuxjO/edit?usp=sharing&ouid=105680020578634715377&rtpof=true&sd=true) 

## Record of Full Automation 

   - [Automation Record](https://drive.google.com/file/d/1-1heTJl4hmBBld0EiTpk2_6uCFj1ofe2/view?usp=sharing)

## Allure Report 
   
- **Report of Allure Overview:**
  
  ![allure_report_restAssured](https://github.com/user-attachments/assets/3c4bceaf-c496-4272-a3f9-4c480f1cc87c)


- **Report of Allure Behavior:**
  
  ![behavior_allure_report_restAssured](https://github.com/user-attachments/assets/a02b6a04-3938-43ff-bc9b-d5fe1848a1ec)


  
