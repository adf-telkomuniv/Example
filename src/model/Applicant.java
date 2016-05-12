/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dee
 */
public class Applicant extends User implements Serializable {

    private String lastEducation;
    private char gender = 'n';
    private String expertise;
    private List<ApplicationFile> applicationFiles;

    public Applicant(String email, String password, String name) {
        super(email, password, name);
        applicationFiles = new ArrayList();
    }

    public Applicant(String email, String password, String name, String address, String lastEducation, String expertise, char gender) {
        super(email, password, name, address);
        applicationFiles = new ArrayList();
        this.lastEducation = lastEducation;
        this.expertise = expertise;
        this.gender = gender;
    }

    public String getLastEducation() {
        return lastEducation;
    }

    public void setLastEducation(String lastEducation) {
        this.lastEducation = lastEducation;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        if (gender != 'm' && gender != 'f') {
            throw new IllegalStateException("gender = m/f");
        }
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Applicant{"
                + "\n email=" + getEmail()
                + "\n name=" + getName()
                + "\n gender=" + (gender == 'm' ? "male" : gender == 'f' ? "female" : "n/a")
                + "\n address=" + getAddress()
                + "\n lastEducation=" + lastEducation
                + "\n  expertise=" + expertise
                + "\n}";
    }

    public List<ApplicationFile> getApplicationFiles() {
        return applicationFiles;
    }

    /**
     * create new application file
     *
     * @param resume
     * @return new application file
     */
    public ApplicationFile createApplicationFile(String resume) {
        ApplicationFile file = new ApplicationFile(ApplicationFile.generateId(), getName(), resume);
        applicationFiles.add(file);
        return file;
    }

    public void setApplicationFiles(List<ApplicationFile> applicationFiles) {
        this.applicationFiles = applicationFiles;
    }

    /**
     *
     * @param i
     * @return i-th ApplicationFile from the list
     */
    public ApplicationFile getApplicationFile(int i) {
        return applicationFiles.get(i);
    }

    /**
     * search ApplicationFile by Id
     *
     * @param idApplication
     * @return ApplicationFile, null if not found
     */
    public ApplicationFile searchApplicationFile(int idApplication) {
        for (ApplicationFile file : applicationFiles) {
            if (file.getApplicationId() == idApplication) {
                return file;
            }
        }
        return null;
    }

}
