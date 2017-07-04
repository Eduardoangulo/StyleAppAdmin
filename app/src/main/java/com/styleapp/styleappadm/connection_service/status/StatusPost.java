package com.styleapp.styleappadm.connection_service.status;

/**
 * Created by Luis on 04/07/2017.
 */

public class StatusPost {
    private Integer detail_id;

    public StatusPost(Integer detail_id) {
        this.detail_id = detail_id;
    }

    public Integer getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(Integer detail_id) {
        this.detail_id = detail_id;
    }
}
