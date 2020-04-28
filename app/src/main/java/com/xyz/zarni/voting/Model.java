package com.xyz.zarni.voting;

/**
 * Created by Zarni on 12/17/2017.
 */

public class Model {

    private int kqvote,ppvote,popuvote;
    private int count = 0;

    private String  name,section,fb_acc,profile,image_one,image_two,image_three,phone;

    public Model() {
    }

    public Model( String name, String section, String fb_acc,String phone, String profile, String image_one, String image_two, String image_three) {
        this.name = name;
        this.section = section;
        this.fb_acc = fb_acc;
        this.profile = profile;
        this.image_one = image_one;
        this.image_two = image_two;
        this.image_three = image_three;
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFb_acc() {
        return fb_acc;
    }

    public void setFb_acc(String fb_acc) {
        this.fb_acc = fb_acc;
    }


    public int getKqvote() {
        return kqvote;
    }

    public void setKqvote(int kqvote) {
        this.kqvote = kqvote;
    }

    public int getPpvote() {
        return ppvote;
    }

    public void setPpvote(int ppvote) {
        this.ppvote = ppvote;
    }

    public int getPopuvote() {
        return popuvote;
    }

    public void setPopuvote(int popuvote) {
        this.popuvote = popuvote;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getImage_one() {
        return image_one;
    }

    public void setImage_one(String image_one) {
        this.image_one = image_one;
    }

    public String getImage_two() {
        return image_two;
    }

    public void setImage_two(String image_two) {
        this.image_two = image_two;
    }

    public String getImage_three() {
        return image_three;
    }

    public void setImage_three(String image_three) {
        this.image_three = image_three;
    }
}
