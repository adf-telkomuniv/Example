/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dee
 */
public class Vacancy implements Serializable {

    private static int iterator;

    private int vacancyId;
    private String vacancyName;
    private String vacancyDetail;
    private Date deadline;
    private boolean active;

    private Map<String, ApplicationFile> submittedFiles;
    private Map<String, ApplicationFile> acceptedFiles;
//    private List<ApplicationFile> submittedFiles;
//    private List<ApplicationFile> acceptedFiles;

    public Vacancy(String vacancyName, Date deadline) {
        this.vacancyName = vacancyName;
        this.deadline = deadline;
        submittedFiles = new HashMap();
        acceptedFiles = new HashMap();
//        submittedFiles = new ArrayList();
//        acceptedFiles = new ArrayList();
        active = true;
    }

    public Vacancy(int idVacancy, String vacancyName, Date deadline) {
        this.vacancyId = idVacancy;
        this.vacancyName = vacancyName;
        this.deadline = deadline;
        submittedFiles = new HashMap();
        acceptedFiles = new HashMap();
//        submittedFiles = new ArrayList();
//        acceptedFiles = new ArrayList();
        active = true;
    }

    public String getVacancyDetail() {
        return vacancyDetail;
    }

    public void setVacancyDetail(String vacancyDetail) {
        this.vacancyDetail = vacancyDetail;
    }

//    public void acceptFile(int i) {
//        ApplicationFile a = removeSubmittedFile(i);
//        addAcceptedFile(a);
//    }
    public void acceptFile(String username) {
        ApplicationFile a = removeSubmittedFile(username);
        addAcceptedFile(username, a);
    }

    public void acceptFile(int applicationId) {
        Object[] a = removeSubmittedFile(applicationId);
        if (a != null) {
            addAcceptedFile((String) a[0], (ApplicationFile) a[1]);
        } else {
            throw new IllegalStateException("Application File not found");
        }
    }

    public String getVacancyName() {
        return vacancyName;
    }

    public void setVacancyName(String vacancyName) {
        this.vacancyName = vacancyName;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

//    public List<ApplicationFile> getSubmittedFiles() {
//        return submittedFiles;
//    }
//    public void setSubmittedFiles(List<ApplicationFile> submittedFiles) {
//        this.submittedFiles = submittedFiles;
//    }
//
//    public void addSubmittedFile(ApplicationFile file) {
//        submittedFiles.add(file);
//    }
//
//    public ApplicationFile getSubmittedFile(int i) {
//        return submittedFiles.get(i);
//    }
//
//    public ApplicationFile removeSubmittedFile(int i) {
//        ApplicationFile file = submittedFiles.remove(i);
//        return file;
//    }
//
//    public List<ApplicationFile> getAcceptedFiles() {
//        return acceptedFiles;
//    }
//
//    public void setAcceptedFiles(List<ApplicationFile> acceptedFiles) {
//        this.acceptedFiles = acceptedFiles;
//    }
//
//    public void addAcceptedFile(ApplicationFile file) {
//        acceptedFiles.add(file);
//    }
//
//    public ApplicationFile getAcceptedFile(int i) {
//        return acceptedFiles.get(i);
//    }
//
//    public ApplicationFile removeAcceptedFile(int i) {
//        ApplicationFile file = acceptedFiles.remove(i);
//        return file;
//    }
//    public ApplicationFile searchSubmittedFile(int idApplication) {
//        for (ApplicationFile file : submittedFiles) {
//            if (file.getIdApplication() == idApplication) {
//                return file;
//            }
//        }
//        return null;
//    }
//
//    public ApplicationFile searchAcceptedFile(int idApplication) {
//        for (ApplicationFile file : acceptedFiles) {
//            if (file.getIdApplication() == idApplication) {
//                return file;
//            }
//        }
//        return null;
//    }
    public Map<String, ApplicationFile> getSubmittedFiles() {
        return submittedFiles;
    }

    public void setSubmittedFiles(Map<String, ApplicationFile> submittedFiles) {
        this.submittedFiles = submittedFiles;
    }

    public void addSubmittedFile(String username, ApplicationFile file) {
        submittedFiles.put(username, file);
    }

    public ApplicationFile getSubmittedFile(String username) {
        return submittedFiles.get(username);
    }

    public Object[] getSubmittedFile(int applicationId) {
        for (Map.Entry<String, ApplicationFile> entry : submittedFiles.entrySet()) {
            String username = entry.getKey();
            ApplicationFile file = entry.getValue();
            if (file.getApplicationId() == applicationId) {
                return new Object[]{username, file};
            }
        }
        return null;
    }

    public ApplicationFile removeSubmittedFile(String username) {
        ApplicationFile file = submittedFiles.remove(username);
        return file;
    }

    public Object[] removeSubmittedFile(int applicationId) {
        for (Map.Entry<String, ApplicationFile> entry : submittedFiles.entrySet()) {
            String username = entry.getKey();
            ApplicationFile file = entry.getValue();
            if (file.getApplicationId() == applicationId) {
//                submittedFiles.remove(username);
                return new Object[]{username, file};
            }
        }
        return null;
    }

    public Map<String, ApplicationFile> getAcceptedFiles() {
        return acceptedFiles;
    }

    public void setAcceptedFiles(Map<String, ApplicationFile> submittedFiles) {
        this.acceptedFiles = submittedFiles;
    }

    public void addAcceptedFile(String username, ApplicationFile file) {
        acceptedFiles.put(username, file);
    }

    public ApplicationFile getAcceptedFile(String username) {
        return acceptedFiles.get(username);
    }

    public Object[] getAcceptedFile(int applicationId) {
        for (Map.Entry<String, ApplicationFile> entry : acceptedFiles.entrySet()) {
            String username = entry.getKey();
            ApplicationFile file = entry.getValue();
            if (file.getApplicationId() == applicationId) {
                return new Object[]{username, file};
            }
        }
        return null;
    }

    public ApplicationFile removeAcceptedFile(String username) {
        ApplicationFile file = acceptedFiles.remove(username);
        return file;
    }

    public Object[] removeAcceptedFile(int applicationId) {
        for (Map.Entry<String, ApplicationFile> entry : acceptedFiles.entrySet()) {
            String username = entry.getKey();
            ApplicationFile file = entry.getValue();
            if (file.getApplicationId() == applicationId) {
                acceptedFiles.remove(username);
                return new Object[]{username, file};
            }
        }
        return null;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getVacancyId() {
        return vacancyId;
    }

    @Override
    public String toString() {
//        return "\n\t\tVacancy{"
//                + "\n\t\t vacancyId=" + vacancyId
//                + "\n\t\t vacancyName=" + vacancyName
//                + "\n\t\t deadline=" + deadline
//                + "\n\t\t active=" + active
//                + "\n\t\t submittedFiles=" + submittedFiles
//                + "\n\t\t acceptedFiles=" + acceptedFiles
//                + "\n\t\t}";
        return "Vacancy{"
                + "\n idVacancy=" + vacancyId
                + "\n vacancy name=" + vacancyName
                + "\n vacancy detail=" + vacancyDetail
                + "\n deadline=" + deadline
                + "\n active=" + active
                + "\n submittedFiles=" + submittedFiles
                + "\n acceptedFiles=" + acceptedFiles
                + "\n}";
    }

    public static int getIterator() {
        return iterator;
    }

    public static void setIterator(int iterator) {
        Vacancy.iterator = iterator;
    }

    public static int generateId() {
        return ++iterator;
    }

}
