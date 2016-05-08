/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import data.Database;
import data.FileIO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author dee
 */
public class Application {

    private List<User> users;
    private int saveMode = 0;
    FileIO file;
    Database database;

    public Application() {
        users = new ArrayList();
    }

    // Vacancy Menu
    /**
     * Vacancy Menu 2 - Edit Vacancy Detail
     *
     * @param vacancy
     * @param name
     * @param detail
     * @param deadline
     */
    public void editVacancy(Vacancy vacancy,
            String name, String detail, Date deadline) {
        vacancy.setVacancyName(name);
        vacancy.setVacancyDetail(detail);
        vacancy.setDeadline(deadline);
        save();
    }

    /**
     * Vacancy Menu 5 - Accept Application File
     *
     * @param vacancy
     * @param applicationId
     */
    public void acceptApplication(Vacancy vacancy, int applicationId) {
        vacancy.acceptFile(applicationId);
        save();
    }

    /**
     * Vacancy Menu 6 - Close Vacancy,
     *
     * @param vacancy
     */
    public void closeVacancy(Vacancy vacancy) {
        vacancy.setActive(false);
        save();
    }

    // Company Menu
    /**
     * Company Menu 2 - Edit Company Profile
     *
     * @param company
     * @param name
     * @param address
     */
    public void editCompany(Company company, String name, String address) {
        company.setName(name);
        company.setAddress(address);
        save();
    }

    /**
     * Company Menu 4 - Create new Vacancy
     *
     * @param company
     * @param vacancyName
     * @param deadline
     */
    public void createVacancy(Company company,
            String vacancyName, Date deadline) {
        company.createVacancy(Vacancy.generateId(), vacancyName, deadline);
        save();
    }

    // Applicant Menu
    /**
     * Applicant Menu 2 - Edit Applicant Profile
     *
     * @param applicant
     * @param name
     * @param address
     * @param lastEducation
     * @param expertise
     */
    public void editProfile(Applicant applicant, String name, String address,
            String lastEducation, String expertise) {
        applicant.setName(name);
        applicant.setAddress(address);
        applicant.setLastEducation(lastEducation);
        applicant.setExpertise(expertise);
        save();
    }

    /**
     * Applicant Menu 4 - Apply Vacancy
     *
     * @param applicant
     * @param v
     * @param resume
     */
    public void applyJob(Applicant applicant, Vacancy v, String resume) {
        ApplicationFile appFile = applicant.createApplicationFile(resume);
        v.addSubmittedFile(applicant.getEmail(), appFile);
        save();
    }

    /**
     * get Vacancy by ID
     *
     * @param vacancyId
     * @return Vacancy, null if not found
     */
    public Vacancy getVacancy(int vacancyId) {
//        load();
        for (User u : users) {
            if (u instanceof Company) {
                Company c = (Company) u;
                for (Vacancy v : c.getActiveVacancy()) {
                    if (v.getVacancyId() == vacancyId) {
                        return v;
                    }
                }
            }
        }
        return null;
    }

    // Main Menu
    /**
     * Main Menu 1 - Log In
     *
     * @param email
     * @param password
     * @return User, null if not found
     */
    public User logIn(String email, String password) {
        User user = searchUser(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public User searchUser(String email) {
//        load();
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Main Menu 2 - Register
     *
     * @param option user type, 0 = Company, 1 = Applicant
     * @param email
     * @param password
     * @param name
     * @param address
     */
    public void register(int option, String email, String password, String name, String address) {
        User user = searchUser(email);
        if (user == null) {
            switch (option) {
                case 1:
                    user = new Applicant(email, password);
                    break;
                case 0:
                    user = new Company(email, password);
                    break;
                default:
                    throw new IllegalStateException("define option=1/0");
            }
            user.setName(name);
            user.setAddress(address);
            adduser(user);
        } else {
            throw new IllegalStateException("email already exists");
        }
    }

    /**
     *
     * @return list of User
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     *
     * @param users list of User
     */
    public void setUsers(List<User> users) {
        this.users = users;
        save();
    }

    /**
     * add user to users list
     *
     * @param user new user
     */
    public void adduser(User user) {
        users.add(user);
        save();
    }

    /**
     * get i-th user from users list
     *
     * @param i
     * @return i-th user
     */
    public User getUser(int i) {
        return users.get(i);
    }

    /**
     * set save mode
     *
     * @param saveMode save mode, 0 = no save, 1 = file, 2 = database, 3 = ORM
     */
    public void setSaveMode(int saveMode) {
        this.saveMode = saveMode;
        file = null;
        database = null;
        switch (saveMode) {
            case 0:
                break;
            case 1:
                String filename = "data.dat";
                String fileLog = "log.dat";
                file = new FileIO(filename, fileLog);
                loadFile();
                break;
            case 2:
                String server = "jdbc:mysql://localhost:3306/test";
                String dbuser = "root";
                String dbpasswd = "";
                database = new Database(server, dbuser, dbpasswd);
            default:
                throw new AssertionError("specify save mode : 0/1/2");
        }
    }

    public int getSaveMode() {
        return saveMode;
    }

    private void save() {
        if (saveMode == 1) {
            saveFile();
        } else if (saveMode == 2) {
        }
        System.out.println("saved");
    }

//    private void load() {
//        if (saveMode == 1) {
//            loadFile();
//        } else if (saveMode == 2) {
//        }
//    }

    /**
     * save users list and log id to file
     */
    private void saveFile() {
        System.out.println("Save file = " + users);
        file.saveUsers(users);
        file.saveLog();
    }

    /**
     * load users list and log id from file
     */
    private void loadFile() {
        users = file.loadUsers();
        int[] log = file.loadLog();
        Vacancy.setIterator(log[0]);
        ApplicationFile.setIterator(log[1]);
    }

}
