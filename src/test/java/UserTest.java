import io.restassured.response.Response;
import org.com.solvd.utils.LocalData;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ApiService;

import java.io.IOException;

public class UserTest {
    @Test
    public void postNewUser() throws IOException {
        Response response = ApiService.postUser("src/test/resources/postUser.json");
        Assert.assertEquals(response.statusCode(), 201, "The status code was not the expected one");
        Assert.assertEquals(LocalData.getInstance().getUserResponse().getId(), 111111, "Incorrect ID");
        Assert.assertEquals(LocalData.getInstance().getUserResponse().getStatus(), "active", "User status is wrong");
    }

    @Test
    public void getAllUsers() {
        Response response = ApiService.getAllUsers();
        Assert.assertEquals(response.statusCode(), 200, "The status code was not the expected one");
    }

    @Test
    public void putUser() throws IOException {
        Response response = ApiService.putUser("src/test/resources/putUser.json");
        Assert.assertEquals(response.statusCode(), 200, "The status code was not the expected one");
        Assert.assertEquals(LocalData.getInstance().getUserResponse().getStatus(), "inactive", "User status is wrong");
    }

    @Test
    public void deleteUser() throws IOException {
        Response response = ApiService.deleteUser("111111");
        Assert.assertFalse(response.asPrettyString().contains("Resource not found"));
    }

    @Test
    public void deleteNonExistingUser() throws IOException {
        Response response = ApiService.deleteUser("1204124912");
        Assert.assertTrue(response.asPrettyString().contains("Resource not found"));
    }

    @Test
    public void getUserById() {
        Response response = ApiService.getUsersByID(111111);
        Assert.assertEquals(response.statusCode(), 200, "The status code was not the expected one");
        Assert.assertEquals(LocalData.getInstance().getUserResponse().getId(), "111111", "User id is wrong");
    }

    @Test
    public void putUserWithMissingEmail() throws IOException {
        Response response = ApiService.putUser("src/test/resources/putUserInvalidMail.json");
        Assert.assertEquals(response.statusCode(), 422, "The status code was not the expected one");
        Assert.assertTrue(response.asPrettyString().contains("Error Message"), "Email is invalid");
    }

    @Test
    public void postUserMissingName() throws IOException {
        Response response = ApiService.postUser("src/test/resources/postUserMissingName.json");
        Assert.assertEquals(response.statusCode(), 422, "The status code was not the expected one");
        Assert.assertEquals(LocalData.getInstance().getUserResponse(), "Error Message", "Name is missing");
    }

    @Test
    public void getUserByGender() {
        Response response = ApiService.getUsersByString("src/test/resources/getUserByGender.json");
        Assert.assertEquals(response.statusCode(), 200, "The status code was not the expected one");
        Assert.assertEquals(LocalData.getInstance().getUserResponse().getGender(), "female", "Users gender is wrong");
    }

    @Test
    public void getUserByStatus() {
        Response response = ApiService.getUsersByString("src/test/resources/getUserByStatus.json");
        Assert.assertEquals(response.statusCode(), 200, "The status code was not the expected one");
        Assert.assertEquals(LocalData.getInstance().getUserResponse().getStatus(), "active", "Users status is wrong");
    }
}
