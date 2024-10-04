import io.restassured.response.Response;
import org.com.solvd.utils.LocalData;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ApiService;

import java.io.IOException;

public class UserTest {
    @Test
    public void postNewUser() throws IOException {
        Response response = ApiService.postUser();
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
        Response response = ApiService.putUser();
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
}
