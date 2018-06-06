package com.smad.bsnassignment.module.feedback;

public class Feedback {
    private String user_id;
    private String feedback;

    public Feedback(String user_id, String feedback) {
        this.user_id = user_id;
        this.feedback = feedback;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getFeedback() {
        return feedback;
    }
}
