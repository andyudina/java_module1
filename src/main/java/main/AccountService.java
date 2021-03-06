package main;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class AccountService {
    private Map<String, UserProfile> users = new HashMap<>();
    private Map<String, UserProfile> sessions = new HashMap<>();

    public boolean addUser(String userName, UserProfile userProfile) {
        if (users.containsKey(userName))
            return false;
        users.put(userName, userProfile);
        return true;
    }

    public void addSessions(String sessionId, UserProfile userProfile) {
        sessions.put(sessionId, userProfile);
    }

    public UserProfile getUser(String userName) {
        return users.get(userName);
    }

    public UserProfile getSessions(String sessionId) {
        return sessions.get(sessionId);
    }

    public void deleteSession(String sessionId) {
        sessions.remove(sessionId);
    }

    public ArrayList<String> getSignedUpUsers() {
        ArrayList<String> userList = new ArrayList<>();
        for (UserProfile userProfile: users.values()) {
            userList.add(userProfile.getLogin());

        }
        return userList;     
    }

    public ArrayList<String> getLogInUsers() {
        ArrayList<String> userList = new ArrayList<>();
        for (UserProfile userProfile: sessions.values()) {
            userList.add(userProfile.getLogin());
        }
        return userList;     
    }
}
