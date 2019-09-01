package model;

import exceptions.ElementIsNotUniqueException;
import exceptions.EmptyArrayException;
import exceptions.NoElementFoundException;
import utils.ArrayService;

public class Organization {
    public User[] users;
    public Plan[] plans;
    public Activity[] activities;

    public Organization(User[] users, Plan[] plans, Activity[] activities) {
        this.users = users;
        this.plans = plans;
        this.activities = activities;
    }

    public Organization() {
        this(new User[0], new Plan[0], new Activity[0]);
    }

    public boolean addUser(User user) {
        try {
            users = ArrayService.addToArray(users, user);
            return true;
        } catch (ElementIsNotUniqueException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean addPlan(Plan plan) {
        try {
            plans = ArrayService.addToArray(plans, plan);
            return true;
        } catch (ElementIsNotUniqueException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean addActivity(Activity activity) {
        try {
            activities = ArrayService.addToArray(activities, activity);
            return true;
        } catch (ElementIsNotUniqueException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean removeUser(User user) {
        try {
            users = ArrayService.removeElement(users, user);
            return true;
        } catch (NoElementFoundException | EmptyArrayException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean removePlan(Plan plan) {
        try {
            plans = ArrayService.removeElement(plans, plan);
            return true;
        } catch (NoElementFoundException | EmptyArrayException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean removeActivity(Activity activity) {
        try {
            activities = ArrayService.removeElement(activities, activity);
            return true;
        } catch (NoElementFoundException | EmptyArrayException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public User getUser(int index) throws NoElementFoundException, EmptyArrayException {
        return ArrayService.getElementByIndex(users, index);
    }

    public Activity getActivity(int index) throws EmptyArrayException, NoElementFoundException {
        return ArrayService.getElementByIndex(activities, index);
    }

    public Plan getPlan(int index) throws EmptyArrayException, NoElementFoundException {
        return ArrayService.getElementByIndex(plans, index);
    }

}
