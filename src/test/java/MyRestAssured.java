import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import utils.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class MyRestAssured {
    Properties prop;
    public MyRestAssured() throws IOException {
        prop= new Properties();
        FileInputStream fs=new FileInputStream("./src/test/resources/config.properties");
        prop.load(fs);

    }
    //@Test

    public void doRegistration(){

        RestAssured.baseURI= prop.getProperty("baseUrl");
        //RestAssured.baseURI= "https://dailyfinanceapi.roadtocareer.net";
        boolean b = true;
        Response res =given().contentType("application/json").body("\n" +
                "{\n" +
                "\"firstName\":\"nur \",\n" +
                "\"lastName\":\"mumu\",\n" +
                "\"email\":\"nailanoorin1999+24@gmail.com\",\n" +
                "\"password\":\"1234\",\n" +
                "\"phoneNumber\":\"01785503378\",\n" +
                "\"address\":\"Dhaka\",\n" +
                "\"gender\":\"Female\",\n" +
                "\"termsAccepted\":true\n" +
                "}")
                .when().post("/api/auth/register");
        //System.out.println(res.asString());

    }
    //@Test
    public void doAdminLogin() throws ConfigurationException {
        RestAssured.baseURI= prop.getProperty("baseUrl");
        //RestAssured.baseURI= "https://dailyfinanceapi.roadtocareer.net";
        boolean b = true;
        Response res =given().contentType("application/json").body("{\n" +
                        "    \"email\":\"admin@test.com\",\n" +
                        "    \"password\":\"admin123\"\n" +
                        "    }")
                .when().post("/api/auth/login");
        //System.out.println(res.asString());

        JsonPath jsonObj= res.jsonPath();
        String token = jsonObj.get("token").toString();
        System.out.println(token);
        Utils.setEnVar("token",token);

    }
    //@Test
    public void getUserList() throws ConfigurationException {
        //RestAssured.baseURI= "https://dailyfinanceapi.roadtocareer.net";
        RestAssured.baseURI= prop.getProperty("baseUrl");

        Response res= given().contentType("application/json").
                header("Authorization","Bearer "+prop.getProperty("token"))
                .when().get("/api/user/users");

        System.out.println(res.asString());

        //JsonPath jsonObj= res.jsonPath();
        //String userList= jsonObj.get("users").toString();
        //System.out.println(userList);

    }
    //@Test
    public void searchUser() throws ConfigurationException {
        //RestAssured.baseURI= "https://dailyfinanceapi.roadtocareer.net";
        RestAssured.baseURI= prop.getProperty("baseUrl");

        Response res= given().contentType("application/json").
                header("Authorization","Bearer "+prop.getProperty("token"))
                .when().get("/api/user/users?id=2114eca2-2873-4b54-9384-8d292dd413ec");

        System.out.println(res.asString());

        //JsonPath jsonObj= res.jsonPath();
        //String id= jsonObj.get("user.id").toString();
        //System.out.println(id);

    }
    //@Test
    public void editUserInfo(){
        //RestAssured.baseURI= "https://dailyfinanceapi.roadtocareer.net";
        RestAssured.baseURI= prop.getProperty("baseUrl");
        Response res= given().contentType("application/json").body("{\n" +
                        "\"firstName\":\"mmm\",\n" +
                        "\"phoneNumber\":\"01785503301\"\n" +
                        "\n" +
                        "}")
                .header("Authorization","Bearer "+ prop.getProperty("token"))
                .when().get("/api/user/5d17f6b2-0128-49fd-a808-323afa7002bd");
        System.out.println(res.asString());
    }
    //@Test
    public void doUserLogin() throws ConfigurationException {
        RestAssured.baseURI= prop.getProperty("baseUrl");
        //RestAssured.baseURI= "https://dailyfinanceapi.roadtocareer.net";

        Response res =given().contentType("application/json").body("{\"email\": \"nailanoorin1999+21@gmail.com\",\n" +
                        " \"password\": \"1234\"\n" +
                        " }")
                .header("Authorization","Bearer "+ prop.getProperty("token"))
                .when().post("/api/auth/login");
        //System.out.println(res.asString());

        JsonPath jsonObj= res.jsonPath();
        String token = jsonObj.get("token").toString();
        System.out.println(token);
        Utils.setEnVar("Usertoken",token);

    }

    //@Test
    public void createItem(){
        RestAssured.baseURI= prop.getProperty("baseUrl");
        //RestAssured.baseURI= "https://dailyfinanceapi.roadtocareer.net";
        Response res= given().contentType("application/json").body("{\"itemName\":\"Tranportation\",\n" +
                        "\"quantity\":4,\n" +
                        "\"amount\":\"500\",\n" +
                        "\"purchaseDate\":\"2025-04-19\",\n" +
                        "\"month\":\"April\",\n" +
                        "\"remarks\":\"Taxi Fare\"\n" +
                        "}")
                .header("Authorization","Bearer "+ prop.getProperty("userToken"))
                .when().post("/api/costs");
        System.out.println(res.asString());
    }

    //@Test
    public void getItemList() throws ConfigurationException {
        //RestAssured.baseURI= "https://dailyfinanceapi.roadtocareer.net";
        RestAssured.baseURI= prop.getProperty("baseUrl");

        Response res= given().contentType("application/json").
                header("Authorization","Bearer "+prop.getProperty("Usertoken"))
                .when().get("/api/costs");

        System.out.println(res.asString());

        //JsonPath jsonObj= res.jsonPath();
        //String userList= jsonObj.get("users").toString();
        //System.out.println(userList);

    }
    //@Test
    public void editItemName(){
        //RestAssured.baseURI= "https://dailyfinanceapi.roadtocareer.net";
        RestAssured.baseURI= prop.getProperty("baseUrl");
        Response res= given().contentType("application/json").body("{\n" +
                        "    \"itemName\": \"Transport\"\n" +
                        "}")
                .header("Authorization","Bearer "+ prop.getProperty("Usertoken"))
                .when().get("/api/costs/fc933cf5-c00c-4d61-beb6-23e83a8aa94c");
        System.out.println(res.asString());
    }
    //@Test
    public void deleteItem() {
        RestAssured.baseURI = prop.getProperty("baseUrl");
        //RestAssured.baseURI= "https://dailyfinanceapi.roadtocareer.net";
        Response res = (Response) given().contentType("application/json")
                .header("Authorization", "Bearer " + prop.getProperty("Usertoken"))
                .when().delete("/api/costs/483b8555-7c93-487b-a0ff-2e2c4244bade");
        System.out.println(res.asString());
    }










}
