import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.com.solvd.models.User;
import org.com.solvd.utils.Auth;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GraphQLTests {
    public static String baseURI = "https://gorest.co.in/public/v2";
    private static RequestSpecification request;

    @BeforeClass
    public void setUp() {
        request = new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "*/*")
                .addHeader("Authorization", "Bearer " + Auth.getTokenFromPropertiesFile())
                .build();
    }

    @Test
    public void getAllUsers() {
        String bodyQuery = "{\n" +
                "  \"query\": \"query Users { users { nodes { email gender id name status } }}\"\n" +
                "}";
        Response response = RestAssured.given().log().all()
                .spec(request)
                .body(bodyQuery)
                .when()
                .post("/graphql");

        response.then().log().all(true).assertThat().statusCode(200);
    }

    @Test
    public void getAUserByID() {
        String bodyQuery = "{\n" +
                "  \"query\": \"query User { user(id: \\\"111111\\\") { email gender id name status }}\"\n" +
                "}";
        Response response = RestAssured.given().log().all()
                .spec(request)
                .body(bodyQuery)
                .when()
                .post("/graphql");

        response.then().log().all(true).assertThat().statusCode(200);
    }

    @Test
    public void createUser() {
        User userObj = new User(111112, "Jane Feld", "jane@test.com", "female", "active");
        String jsonObj = new Gson().toJson(userObj);
        System.out.println(jsonObj);
        String bodyQuery = "{\n" +
                "  \"query\": \"mutation CreateUser { createUser( input: { name: \\\"Alec Great\\\" gender: \\\"male\\\" email: \\\"alec@mail.com\\\" status: \\\"active\\\" } ) { user { email gender id name status } }}\"\n" +
                "}";

        Response response = RestAssured.given().log().all()
                .spec(request)
                .body(bodyQuery)
                .when()
                .post("/graphql");

        response.then().log().all(true).assertThat().statusCode(200);
    }
}