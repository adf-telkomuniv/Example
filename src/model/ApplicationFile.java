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
    private boolean accepted;

    public ApplicationFile(int idApplication) {
        this.applicationId = idApplication;
        date_created = new Date();
    }

    public ApplicationFile(int idApplication, String name, String resume) {
        this.applicationId = idApplication;
        this.resume = resume;
        this.name = name;
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
                + "\n idApplication=" + applicationId
                + "\n name=" + name
                + "\n date_created=" + date_created
                + "\n resume=" + resume
                + "\n status = " + accepted
                + "\n}";

    }

    public static int getIterator() {
        return iterator;
    }

    public static void setIterator(int iterator) {
        ApplicationFile.iterator = iterator;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
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
