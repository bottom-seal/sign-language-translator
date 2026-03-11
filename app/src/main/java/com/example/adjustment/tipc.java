package com.example.adjustment;

import android.widget.LinearLayout;

public class tipc {
    private String name,text;
    private LinearLayout conversationLayout;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LinearLayout getConversationLayout() {
        return conversationLayout;
    }

    public void setConversationLayout(LinearLayout conversationLayout) {
        this.conversationLayout = conversationLayout;
    }

}
