package controller;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import packageModel.ItemModel;
import packageModel.UserModel;

import java.util.Properties;

import static io.restassured.RestAssured.given;

public class FinancialController {
    Properties prop;

    public FinancialController(Properties prop) {
        this.prop = prop;
    }

    public Response doRegistration(UserModel userModel) throws ConfigurationException {

        RestAssured.baseURI = prop.getProperty("baseUrl");

        return given().contentType("application/json").body(userModel)
                .when().post("/api/auth/register");
    }

    public Response doAdminLogin(UserModel userModel) throws ConfigurationException {
        RestAssured.baseURI = prop.getProperty("baseUrl");

        return given().contentType("application/json").body(userModel)
                .when().post("/api/auth/login");
    }

    public Response getUserList(UserModel userModel) throws ConfigurationException {
        RestAssured.baseURI= prop.getProperty("baseUrl");

        return given().contentType("application/json").
                header("Authorization","Bearer "+prop.getProperty("token"))
                .when().get("/api/user/users");
    }

    public Response searchUser(String id) throws ConfigurationException {
        RestAssured.baseURI= prop.getProperty("baseUrl");

        return given().contentType("application/json").
                header("Authorization","Bearer "+prop.getProperty("token"))
                .when().get("/api/user/users?id="+id);
    }

    public Response editUserInfo(String userid, UserModel userModel){

        RestAssured.baseURI= prop.getProperty("baseUrl");
        return given().contentType("application/json").body(userModel)
                .header("Authorization","Bearer "+ prop.getProperty("token"))
                .when().put("/api/user/"+ userid);
    }

    public Response doUserLogin(UserModel userModel) throws ConfigurationException {
        RestAssured.baseURI= prop.getProperty("baseUrl");

        return given().contentType("application/json").body(userModel)
                .header("Authorization","Bearer "+ prop.getProperty("token"))
                .when().post("/api/auth/login");
    }
    public Response createItem(ItemModel itemModel) {
        RestAssured.baseURI = prop.getProperty("baseUrl");
        return given().contentType("application/json").body(itemModel)
                .header("Authorization", "Bearer " + prop.getProperty("userToken"))
                .when().post("/api/costs");
    }
    public Response getItemList(ItemModel itemModel) throws ConfigurationException {
        RestAssured.baseURI= prop.getProperty("baseUrl");

        return given().contentType("application/json").
                header("Authorization","Bearer "+prop.getProperty("userToken"))
                .when().get("/api/costs");
    }

    public Response editItemName(String itemid, ItemModel itemModel){
        RestAssured.baseURI= prop.getProperty("baseUrl");
        return given().contentType("application/json").body(itemModel)
                .header("Authorization","Bearer "+ prop.getProperty("userToken"))
                .when().put("/api/costs/"+ itemid);
    }

    public Response deleteItem(String itemid, ItemModel itemModel) {
        RestAssured.baseURI = prop.getProperty("baseUrl");
        return given().contentType("application/json")
                .header("Authorization", "Bearer " + prop.getProperty("userToken"))
                .when().delete("/api/costs/"+itemid);
    }
}