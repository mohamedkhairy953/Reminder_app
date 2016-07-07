package com.example.moham.contacting;

import java.io.Serializable;

/**
 * Created by moham on 6/16/2016.
 */
public class ContacterModel implements Serializable{

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String name;
    private String phone;
    private String remindMe;
    private byte[] image;
    private boolean is_selected;


    private boolean will_notify;
    private String facebook_usernae;
    private int R_code;

    public int getR_code() {
        return R_code;
    }

    public void setR_code(int r_code) {
        R_code = r_code;
    }

    public boolean is_selected() {
        return is_selected;
    }

    public void setIs_selected(boolean is_selected) {
        this.is_selected = is_selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        if (name.matches("[a-zA-Z]{1,}"))
            this.name = name;
        else {
            throw new Exception("valid name is just chars");
        }
    }

    public boolean isWill_notify() {
        return will_notify;
    }

    public void setWill_notify(boolean will_notify) {
        this.will_notify = will_notify;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) throws Exception {
        if (phone.matches("01[0-9]{9}"))
            this.phone = phone;
        else {
            throw new Exception("please inser valid phone");
        }
    }

    public String getRemindMe() {
        return remindMe;
    }

    public void setRemindMe(String remindMe) throws Exception {
        if (remindMe == null) {
            throw new Exception("you have to select remind me date");
        }
        this.remindMe = remindMe;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getFacebook_usernae() {
        return facebook_usernae;
    }

    public void setFacebook_usernae(String facebook_usernae) {
        this.facebook_usernae = facebook_usernae;
    }
}
