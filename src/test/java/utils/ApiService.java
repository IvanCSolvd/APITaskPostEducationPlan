package utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.com.solvd.models.User;
import org.com.solvd.utils.Auth;
import org.com.solvd.utils.LocalData;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ApiService {

    public static Response postUser() throws IOException {
        String body = Files.readString(Paths.get("src/test/resources/postUser.json"));
        Response response = (Response) RestAssured.given()
                .log()
                .all()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + Auth.getTokenFromPropertiesFile())
                .body(body)
                .baseUri(Constants.baseUrl)
                .basePath(Constants.endpointUser)
                .post().then().extract();

        response.getBody().prettyPrint();

        LocalData.getInstance().setUserResponse(new Gson().fromJson(response.asPrettyString(), User.class));
        return response;
    }

    public static Response getAllUsers() {
        Response response = (Response) RestAssured.given()
                .log()
                .all()
                .header("Content-Type", "application/json")
                .get(Constants.baseUrl + Constants.endpointUser)
                .then().extract();
        String jsonResponse = response.getBody().asString();

        Type listType = new TypeToken<List<User>>() {
        }.getType();
        List<User> users = new Gson().fromJson(jsonResponse, listType);
        for (User user : users) {
            System.out.println(user);
        }
        LocalData.getInstance().setUserResponses(users);
        response.getBody().prettyPrint();
        return response;
    }

    public static Response putUser() throws IOException {
        String body = Files.readString(Paths.get("src/test/resources/putUser.json"));

        Response response = (Response) RestAssured.given()
                .log()
                .all()
                .header("Content-Type", "application/json")
                .body(body)
                .baseUri(Constants.baseUrl)
                .basePath(Constants.endpointUser)
                .put().then().extract();

        response.getBody().prettyPrint();
        return response;
    }

    public static Response deleteUser(String userId) throws IOException {
        Response response = (Response) RestAssured.given()
                .log()
                .all()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + Auth.getTokenFromPropertiesFile())
                .baseUri(Constants.baseUrl)
                .basePath(Constants.endpointUser)
                .delete(userId).then().extract();

        response.getBody().prettyPrint();
        return response;
    }


    public static Response getUsersByID(Long id) {
        Response response = (Response) RestAssured.given()
                .log()
                .all()
                .header("Content-Type", "application/json")
                .get(Constants.baseUrl + Constants.endpointUser)
                .then().extract();
        String jsonResponse = response.getBody().asString();

        Type listType = new TypeToken<List<User>>() {
        }.getType();
        List<User> users = new Gson().fromJson(jsonResponse, listType);
        for (User user : users) {
            System.out.println(user);
        }
        LocalData.getInstance().setUserResponses(users);
        response.getBody().prettyPrint();
        return response;
    }
}
