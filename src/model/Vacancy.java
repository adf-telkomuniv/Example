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
//    private Map<String, ApplicationFile> acceptedFiles;

    public Vacancy(String vacancyName, Date deadline) {
        this.vacancyName = vacancyName;
        this.deadline = deadline;
        submittedFiles = new HashMap();
//        acceptedFiles = new HashMap();
        active = true;
    }

    public Vacancy(int idVacancy, String vacancyName, Date deadline) {
        this.vacancyId = idVacancy;
        this.vacancyName = vacancyName;
        this.deadline = deadline;
        submittedFiles = new HashMap();
//        acceptedFiles = new HashMap();
        active = true;
    }

    public Vacancy(int vacancyId, String vacancyName, Date deadline, String vacancyDetail, boolean active) {
        this.vacancyId = vacancyId;
        this.vacancyName = vacancyName;
        this.vacancyDetail = vacancyDetail;
        this.deadline = deadline;
        this.active = active;
        submittedFiles = new HashMap();
    }

    public String getVacancyDetail() {
        return vacancyDetail;
    }

    public void setVacancyDetail(String vacancyDetail) {
        this.vacancyDetail = vacancyDetail;
    }

////    public void acceptFile(String username) {
////        ApplicationFile a = removeSubmittedFile(username);
////        addAcceptedFile(username, a);
////    }
//    public void acceptFile(int applicationId) {
//        ApplicationFile file = getSubmittedFile(applicationId);
//        if (file != null) {
//            file.setStatus(1);
//        } else {
//            throw new IllegalStateException("Application File not found");
//        }
//    }
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

    public Map<String, ApplicationFile> getSubmittedFiles() {
        return submittedFiles;
    }

    public List<ApplicationFile> getSubmittedFiles(boolean accepted) {
        List<ApplicationFile> list = new ArrayList();
        for (Map.Entry<String, ApplicationFile> entry : submittedFiles.entrySet()) {
            ApplicationFile file = entry.getValue();
            if ((file.getStatus() == 1) == accepted) {
                list.add(file);
            }
        }
//        return list.stream().toArray(size -> new ApplicationFile[size]);
        return list;
    }

    public void setSubmittedFiles(Map<String, ApplicationFile> submittedFiles) {
        this.submittedFiles = submittedFiles;
    }

    public void addSubmittedFile(String email, ApplicationFile file) {
        submittedFiles.put(email, file);
    }

    public ApplicationFile getSubmittedFile(String email) {
        return submittedFiles.get(email);
    }

    public ApplicationFile getSubmittedFile(int applicationId) {
        for (Map.Entry<String, ApplicationFile> entry : submittedFiles.entrySet()) {
            ApplicationFile file = entry.getValue();
            if (file.getApplicationId() == applicationId) {
                return file;
            }
        }
        return null;
    }

    public ApplicationFile removeSubmittedFile(String email) {
        ApplicationFile file = submittedFiles.remove(email);
        file.setStatus(-1);
        return file;
    }

    public ApplicationFile removeSubmittedFile(int applicationId) {
        for (Map.Entry<String, ApplicationFile> entry : submittedFiles.entrySet()) {
            ApplicationFile file = entry.getValue();
            if (file.getApplicationId() == applicationId) {
                submittedFiles.remove(entry.getKey());
                file.setStatus(-1);
                return file;
            }
        }
        return null;
    }

//    public Map<String, ApplicationFile> getAcceptedFiles() {
//        return acceptedFiles;
//    }
//    public void setAcceptedFiles(Map<String, ApplicationFile> submittedFiles) {
//        this.acceptedFiles = submittedFiles;
//    }
//    public void addAcceptedFile(String username, ApplicationFile file) {
//        acceptedFiles.put(username, file);
//    }
//    public ApplicationFile getAcceptedFile(String username) {
//        return acceptedFiles.get(username);
//    }
//    public Object[] getAcceptedFile(int applicationId) {
//        for (Map.Entry<String, ApplicationFile> entry : acceptedFiles.entrySet()) {
//            String username = entry.getKey();
//            ApplicationFile file = entry.getValue();
//            if (file.getApplicationId() == applicationId) {
//                return new Object[]{username, file};
//            }
//        }
//        return null;
//    }
//    public ApplicationFile removeAcceptedFile(String username) {
//        ApplicationFile file = acceptedFiles.remove(username);
//        return file;
//    }
//    public Object[] removeAcceptedFile(int applicationId) {
//        for (Map.Entry<String, ApplicationFile> entry : acceptedFiles.entrySet()) {
//            String username = entry.getKey();
//            ApplicationFile file = entry.getValue();
//            if (file.getApplicationId() == applicationId) {
//                acceptedFiles.remove(username);
//                return new Object[]{username, file};
//            }
//        }
//        return null;
//    }
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getVacancyId() {
        return vacancyId;
    }

    public void setVacancyId(int vacancyId) {
        this.vacancyId = vacancyId;
    }

    @Override
    public String toString() {
        return "Vacancy{"
                + "\n vacancy Id=" + vacancyId
                + "\n vacancy name=" + vacancyName
                + "\n vacancy detail=" + vacancyDetail
                + "\n deadline=" + deadline
                + "\n status=" + (active ? "active" : "closed")
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
