package com.example.nativeloginpage;

import java.util.ArrayList;

public class Chat {
    private String name;
    private String msg;

    public Chat(String cName, String msg) {
        this.name = cName;
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String cName) {
        this.name = cName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private static int lastContactId = 0;

    public static ArrayList<Chat> createContactsList(int numContacts) {
        ArrayList<Chat> Chats = new ArrayList<Chat>();

        for (int i = 1; i <= numContacts; i++) {
            Chats.add(new Chat("Friend  " + lastContactId, "How are you? "+ ++lastContactId));
        }

        return Chats;
    }
}
