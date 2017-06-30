package com.styleapp.styleappadm.classes;

/**
 * Created by Luis on 28/06/2017.
 */

public class Instanced_Service {
    private String serviceName;
    private int imgsrc;
    private String state;
    private int stars;

    public Instanced_Service(String serviceName, String state, int imgsrc){
        this.serviceName=serviceName;
        this.imgsrc=imgsrc;
        this.state=state;
    }
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(int imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
