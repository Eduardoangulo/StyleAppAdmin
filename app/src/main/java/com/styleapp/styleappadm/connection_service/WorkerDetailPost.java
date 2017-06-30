package com.styleapp.styleappadm.connection_service;

/**
 * Created by Luis on 30/06/2017.
 */

public class WorkerDetailPost {
    private Integer worker_id;

    public WorkerDetailPost(Integer worker_id) {
        this.worker_id = worker_id;
    }

    public Integer getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(Integer worker_id) {
        this.worker_id = worker_id;
    }
}
