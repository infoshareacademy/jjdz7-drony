package com.korpodrony.reports.dto;

import com.korpodrony.reports.entity.Action;
import com.korpodrony.reports.entity.View;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ReportsStatisticDTO implements Serializable {

    private static final long serialVersionUID = 42L;

    private int id;
    private String email;
    private View view;
    private Action action;
    private LocalDateTime timeOfAction;

    public ReportsStatisticDTO(int id, String email, View view, Action action, LocalDateTime timeOfAction) {
        this.id = id;
        this.email = email;
        this.view = view;
        this.action = action;
        this.timeOfAction = timeOfAction;
    }

    public ReportsStatisticDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public LocalDateTime getTimeOfAction() {
        return timeOfAction;
    }

    public void setTimeOfAction(LocalDateTime timeOfAction) {
        this.timeOfAction = timeOfAction;
    }
}
