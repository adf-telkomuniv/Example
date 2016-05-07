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
    private String expertise;
    private List<ApplicationFile> applicationFiles;

    public Applicant(String email, String password) {
        super(email, password);
        applicationFiles = new ArrayList();
    }

    public Applicant(String lastEducation, String expertise, String email, String password) {
        super(email, password);
        applicationFiles = new ArrayList();
        this.lastEducation = lastEducation;
        this.expertise = expertise;
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

    @Override
    public String toString() {
//        return super.toString()
//                + "\n\tApplicant{"
//                + "\n\t lastEducation=" + lastEducation
//                + "\n\t  expertise=" + expertise
//                + "\n\t}";
        return "Applicant{"
                + "\n email=" + getEmail()
                + "\n name=" + getName()
                + "\n address=" + getAddress()
                + "\n lastEducation=" + lastEducation
                + "\n  expertise=" + expertise
                + "\n}";
    }

    public List<ApplicationFile> getApplicationFiles() {
        return applicationFiles;
    }

    public ApplicationFile createApplicationFile(String resume) {
        ApplicationFile file = new ApplicationFile(ApplicationFile.generateId(), getName(), resume);
        applicationFiles.add(file);
        return file;
    }

    public void setApplicationFiles(List<ApplicationFile> applicationFiles) {
        this.applicationFiles = applicationFiles;
    }

    public ApplicationFile getApplicationFile(int i) {
        return applicationFiles.get(i);
    }

    public ApplicationFile searchgetApplicationFile(int idApplication) {
        for (ApplicationFile file : applicationFiles) {
            if (file.getApplicationId() == idApplication) {
                return file;
            }
        }
        return null;
    }

}
