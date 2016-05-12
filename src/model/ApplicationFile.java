/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author dee
 */
public class ApplicationFile implements Serializable {

    private static int iterator;

    private int applicationId;
    private Date date_created;
    private String name;
    private String resume;
    private int status;

    public ApplicationFile(int idApplication, String name, String resume) {
        this.applicationId = idApplication;
        date_created = new Date();
        this.resume = resume;
        this.name = name;
        this.status = 0;
    }

    public ApplicationFile(int applicationId, String name, String resume, int status, Date date_created) {
        this.applicationId = applicationId;
        this.date_created = date_created;
        this.name = name;
        this.resume = resume;
        this.status = status;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public Date getDate_created() {
        return date_created;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    @Override
    public String toString() {
        return "ApplicationFile{"
                + "\n Application Id = " + applicationId
                + "\n Application Name = " + name
                + "\n Date Created = " + date_created
                + "\n Resume = " + resume
                + "\n Status = " + (status == 0 ? "submitted" : status == 1 ? "accepted" : "rejected")
                + "\n}";

    }

    public static int getIterator() {
        return iterator;
    }

    public static void setIterator(int iterator) {
        ApplicationFile.iterator = iterator;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static int generateId() {
        return ++iterator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
