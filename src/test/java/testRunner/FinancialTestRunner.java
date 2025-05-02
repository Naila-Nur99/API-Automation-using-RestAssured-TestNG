package testRunner;

import com.github.javafaker.Faker;
import controller.FinancialController;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.Test;
import packageModel.ItemModel;
import setup.Setup;
import packageModel.UserModel;
import utils.Utils;

public class FinancialTestRunner extends Setup {

    @Test(priority = 1, description = "User Registration")
    public void doRegistration() throws ConfigurationException {
        FinancialController userController = new FinancialController(prop);
        UserModel userModel = new UserModel();
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = "tasnimislam2334" + Utils.generateRandomNumber(1000, 9999) + "@gmail.com";
        String password = "1234"; // Generates a random password
        String phoneNumber = "0150" + Utils.generateRandomNumber(1000000, 9999999);
        String address = faker.address().fullAddress();
        String gender = faker.options().option("Male", "Female"); // Randomly selects a gender

        userModel.setFirstName(firstName);
        userModel.setLastName(lastName);
        userModel.setEmail(email);
        userModel.setPassword(password);
        userModel.setPhoneNumber(phoneNumber);
        userModel.setAddress(address);
        userModel.setGender(gender);
        userModel.setTermsAccepted(true);
        Response res = userController.doRegistration(userModel);
        System.out.println(res.asString());

        JsonPath jsonPath = res.jsonPath();
        String userId = jsonPath.get("_id");
        System.out.println(userId);
        Utils.setEnVar("UserId", userId);
        String userLastName = jsonPath.get("lastName");
        Utils.setEnVar("userLastName", userLastName);
        String userEmail = jsonPath.get("email");
        Utils.setEnVar("userEmail", userEmail);
        String userPassword = jsonPath.get("password");
        Utils.setEnVar("userPassword", password);
        String userAddress = jsonPath.get("address");
        Utils.setEnVar("userAddress", address);
        String userGender = jsonPath.get("gender");
        Utils.setEnVar("userGender", gender);

    }
    @Test(priority = 2, description = "Negative Test: Register with Existing Credentials")
    public void registerWithExistingEmail() throws ConfigurationException {
        FinancialController userController = new FinancialController(prop);
        UserModel userModel = new UserModel();
        userModel.setFirstName("Test");
        userModel.setLastName("User");
        userModel.setEmail(prop.getProperty("userEmail")); // Reusing same email
        userModel.setPassword("Password123");
        userModel.setPhoneNumber("01500000000");
        userModel.setAddress("Test Address");
        userModel.setGender("Female");
        userModel.setTermsAccepted(true);
        Response res = userController.doRegistration(userModel);
        System.out.println(res.asString());
        Assert.assertEquals(res.getStatusCode(), 400); // Expecting failure
        Assert.assertTrue(res.asString().contains("User already exists with this email address"));
    }
    @Test(priority = 3, description = "Negative Test: Register user with missing fields")
    public void registerUserWithMissingFields() throws ConfigurationException {
        FinancialController userController = new FinancialController(prop);
        UserModel userModel = new UserModel();  // Empty user data

        Response res = userController.doRegistration(userModel);
        System.out.println(res.asString());

        Assert.assertEquals(res.getStatusCode(), 500); // Expecting failure
        Assert.assertTrue(res.asString().contains("Server error"));


    }


    @Test(priority = 4, description = "Admin Login")
    public void userLogin() throws ConfigurationException {
        FinancialController userController = new FinancialController(prop);
        UserModel userModel = new UserModel();
        userModel.setEmail("admin@test.com");
        userModel.setPassword("admin123");
        Response res = userController.doAdminLogin(userModel); // Fixed the method call
        System.out.println(res.asString());
        JsonPath jsonObj = res.jsonPath();
        String token = jsonObj.get("token");
        System.out.println("token: " + token);
        Utils.setEnVar("token", token);
    }
    @Test(priority = 5, description = "Negative Test: Admin login with invalid credentials")
    public void invalidAdminLogin() throws ConfigurationException {
        FinancialController userController = new FinancialController(prop);
        UserModel userModel = new UserModel();
        userModel.setEmail("admin@test.com");
        userModel.setPassword("wrongpass");
        Response res = userController.doAdminLogin(userModel);
        System.out.println(res.asString());
        Assert.assertEquals(res.getStatusCode(), 401);
        Assert.assertTrue(res.asString().contains("Invalid email or password"));
    }

    @Test(priority = 6, description = "Get User List")
    public void getUserList() throws ConfigurationException {
        FinancialController userController = new FinancialController(prop);
        UserModel userModel = new UserModel();
        Response res = userController.getUserList(userModel); // Fixed the method call
        System.out.println(res.asString());
    }

    @Test(priority = 7, description = "Search User")
    public void searchUser() throws ConfigurationException {
        FinancialController userController = new FinancialController(prop);
        Response res = userController.searchUser(prop.getProperty("UserId"));
        System.out.println(res.asString());
    }


    @Test(priority = 8, description = "Edit User FirstName and PhoneNumber")
    public void editUserInfo() throws ConfigurationException {
        FinancialController userController = new FinancialController(prop);
        UserModel userModel = new UserModel();
        Faker faker = new Faker();
        userModel.setFirstName(faker.name().firstName());
        userModel.setLastName(prop.getProperty("userLastName"));
        userModel.setEmail(prop.getProperty("userEmail"));
        userModel.setPassword(prop.getProperty("userPassword"));
        userModel.setPhoneNumber("0160" + Utils.generateRandomNumber(1000000, 9999999));
        userModel.setAddress(prop.getProperty("userAddress"));
        userModel.setGender(prop.getProperty("userGender"));
        Response responseNew = userController.editUserInfo(prop.getProperty("UserId"), userModel);
        System.out.println(responseNew.asString());
    }

    @Test(priority = 9, description = "User Login")
    public void doUserLogin() throws ConfigurationException {
        FinancialController financeController=new FinancialController(prop);
        UserModel userModel=new UserModel();
        userModel.setEmail("lucyni@gmail.com");
        userModel.setPassword("1234");
        Response response= financeController.doUserLogin(userModel);
        System.out.println(response.asString());
        JsonPath jsonPath=response.jsonPath();
        String userToken=jsonPath.get("token");
        System.out.println(userToken);
        Utils.setEnVar("userToken",userToken);
    }
    @Test(priority = 10, description = "Negative Test: User login with wrong password")
    public void invalidUserLogin() throws ConfigurationException {
        FinancialController userController = new FinancialController(prop);
        UserModel userModel = new UserModel();
        userModel.setEmail(prop.getProperty("userEmail"));
        userModel.setPassword("WrongPassword123");
        Response res = userController.doUserLogin(userModel);
        System.out.println(res.asString());
        Assert.assertEquals(res.getStatusCode(), 401);
        Assert.assertTrue(res.asString().contains("Invalid email or password"));
    }
    @Test(priority = 11, description = "Negative Test: Login with empty email and password")
    public void loginWithEmptyFields() throws ConfigurationException {
        FinancialController userController = new FinancialController(prop);
        UserModel userModel = new UserModel(); // Empty email and password

        Response res = userController.doUserLogin(userModel);
        System.out.println(res.asString());

        Assert.assertEquals(res.getStatusCode(), 401);
        Assert.assertTrue(res.asString().contains("Invalid email or password"));
    }

    @Test(priority = 12, description = "Create Item")
    public void createItem() throws ConfigurationException {
        FinancialController userController = new FinancialController(prop);
        ItemModel itemModel = new ItemModel();
        itemModel.setItemName("Food");
        itemModel.setQuantity("4");
        itemModel.setAmount("5000");
        itemModel.setPurchaseDate("2025-04-19");
        itemModel.setMonth("April");
        itemModel.setRemarks("Dinner");
        Response res = userController.createItem(itemModel);
        System.out.println(res.asString());
        JsonPath jsonPath = res.jsonPath();
        String itemId = jsonPath.get("_id");
        System.out.println(itemId);
        Utils.setEnVar("ItemId", itemId);
        String itemName = jsonPath.get("itemName");
        Utils.setEnVar("ItemName", itemName);
    }
    @Test(priority = 13, description = "Negative Test: Create item with invalid data types")
    public void createItemWithInvalidTypes() {
        FinancialController userController = new FinancialController(prop);
        ItemModel itemModel = new ItemModel();
        itemModel.setItemName("Electricity");
        itemModel.setQuantity("abc");  // Invalid number
        itemModel.setAmount("xyz");    // Invalid number
        itemModel.setPurchaseDate("invalid-date"); // Invalid date format
        itemModel.setMonth("April");
        itemModel.setRemarks("Wrong input types");

        Response res = userController.createItem(itemModel);
        System.out.println(res.asString());

        Assert.assertEquals(res.getStatusCode(), 500);
        Assert.assertTrue(res.asString().contains("error"));

       }
    @Test(priority = 14, description = "Negative Test: Create item with missing values")
    public void createItemWithEmptyFields() {
        FinancialController userController = new FinancialController(prop);
        ItemModel itemModel = new ItemModel(); // Empty fields
        Response res = userController.createItem(itemModel);
        System.out.println(res.asString());
        itemModel.setItemName("");
        itemModel.setQuantity("4");
        itemModel.setAmount("5000");
        itemModel.setPurchaseDate("2025-04-19");
        itemModel.setMonth("April");
        itemModel.setRemarks("");
        System.out.println(res.asString());
        Assert.assertEquals(res.getStatusCode(), 500);
        Assert.assertTrue(res.asString().contains("error"));


    }
    @Test(priority = 15, description = "Negative Test: Create item wrong token")
    public void createItemWithoutToken() {
        FinancialController userController = new FinancialController(prop);
        ItemModel itemModel = new ItemModel();
        itemModel.setItemName("Groceries");
        itemModel.setQuantity("2");
        itemModel.setAmount("1500");
        itemModel.setPurchaseDate("2025-04-25");
        itemModel.setMonth("April");
        itemModel.setRemarks("No token");

        prop.setProperty("userToken", "wrong token");

        Response res = userController.createItem(itemModel);
        System.out.println(res.asString());

        Assert.assertEquals(res.getStatusCode(), 401);
        Assert.assertTrue(res.asString().contains("Not authorized, token failed"));
    }


    @Test(priority = 15, description = "Get Item List")
    public void getItemList() throws ConfigurationException {
        FinancialController userController = new FinancialController(prop);
        ItemModel itemModel = new ItemModel();
        Response res = userController.getItemList(itemModel);
        System.out.println(res.asString());

    }
    @Test(priority = 16, description = "Get Item List With Invalid Token")
    public void getItemListWithInvalidToken() throws ConfigurationException {
        FinancialController userController = new FinancialController(prop);
        ItemModel itemModel = new ItemModel();

        // Temporarily remove/replace token
        prop.setProperty("userToken", "invalidToken");

        Response res = userController.getItemList(itemModel);
        System.out.println(res.asString());

        Assert.assertEquals(res.getStatusCode(), 401);
        Assert.assertTrue(res.asString().contains("Not authorized, token failed"));
    }

    @Test(priority = 17, description = "Edit Item Name")
    public void editItemName() throws ConfigurationException {
        FinancialController userController = new FinancialController(prop);
        ItemModel itemModel = new ItemModel();
        itemModel.setItemName("Fast Food");
        itemModel.setQuantity("4");
        itemModel.setAmount("5000");
        itemModel.setPurchaseDate("2025-04-19");
        itemModel.setMonth("April");
        itemModel.setRemarks("Dinner");
        Response responseNew = userController.editItemName(prop.getProperty("ItemId"), itemModel);
        System.out.println(responseNew.asString());

    }
    @Test(priority = 18, description = "Negative Test: Edit item with invalid ID")
    public void editItemWithInvalidId() {
        FinancialController userController = new FinancialController(prop);
        ItemModel itemModel = new ItemModel();
        itemModel.setItemName("Invalid Edit");
        itemModel.setQuantity("2");
        itemModel.setAmount("3000");
        itemModel.setPurchaseDate("2025-04-20");
        itemModel.setMonth("April");
        itemModel.setRemarks("Invalid test");

        Response res = userController.editItemName("invalidItemId123", itemModel);
        System.out.println(res.asString());

        Assert.assertEquals(res.getStatusCode(), 404);
        Assert.assertTrue(res.asString().contains("Cost not found"));
    }


    @Test(priority = 19, description = "Delete Item")
    public void deleteItem() throws ConfigurationException {
        FinancialController userController = new FinancialController(prop);
        ItemModel itemModel = new ItemModel();
        Response responseNew = userController.deleteItem(prop.getProperty("ItemId"), itemModel);
        System.out.println(responseNew.asString());

    }
    @Test(priority = 20, description = "Negative Test: Delete item with invalid ID")
    public void deleteItemWithWrongId() {
        FinancialController userController = new FinancialController(prop);
        ItemModel itemModel = new ItemModel();
        Response res = userController.deleteItem("wrongItemId123", itemModel);
        System.out.println(res.asString());
        Assert.assertEquals(res.getStatusCode(), 404);
        Assert.assertTrue(res.asString().contains("Cost not found"));
    }



}