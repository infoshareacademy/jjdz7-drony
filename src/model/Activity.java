package model;

public class Activity {
    private static int currentID = 0;
    private int activityID;
    private String name;
    private short maxUsers;
    private int[] assignedUsersIDs;
//    Trainer trainer;
    private byte duration; /*Unit of duration is quarter*/

    boolean assignUser(User user){
        return false;
    }

    boolean unassignUser(User user){
        return false;
    }
}
