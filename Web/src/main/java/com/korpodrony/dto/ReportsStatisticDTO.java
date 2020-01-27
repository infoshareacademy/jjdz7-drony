package com.korpodrony.dto;

import com.korpodrony.entity.Action;
import com.korpodrony.entity.View;

import java.time.LocalDateTime;
import java.util.Objects;

public class ReportsStatisticDTO {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportsStatisticDTO that = (ReportsStatisticDTO) o;
        return id == that.id &&
                Objects.equals(email, that.email) &&
                Objects.equals(view, that.view) &&
                Objects.equals(action, that.action) &&
                Objects.equals(timeOfAction, that.timeOfAction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, view, action, timeOfAction);
    }
}
