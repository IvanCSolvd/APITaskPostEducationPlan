package org.com.solvd.utils;


import org.com.solvd.models.User;

import java.util.List;

public class LocalData {
    private static LocalData instance;
    private User userResponse;
    private List<User> userResponses;

    private LocalData() {
    }

    public static LocalData getInstance() {
        if (instance == null) {
            synchronized (LocalData.class) {
                if (instance == null) {
                    instance = new LocalData();
                }
            }
        }
        return instance;
    }

    public User getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(User userResponse) {
        this.userResponse = userResponse;
    }

    public List<User> getUserResponses() {
        return userResponses;
    }

    public void setUserResponses(List<User> userResponses) {
        this.userResponses = userResponses;
    }
}
