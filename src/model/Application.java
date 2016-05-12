/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template fileIO, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import data.Database;
import data.FileIO;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
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
    FileIO fileIO;
    Database database;

    public Application() {
        users = new ArrayList();
    }

    // ======================== Vacancy Menu ========================
    /**
     * Vacancy Menu 2 - Edit Vacancy Detail
     *
     * @param vacancy
     * @param name
     * @param detail
     * @param deadline
     * @throws java.sql.SQLException
     */
    public void editVacancy(Vacancy vacancy, String name, String detail, Date deadline) throws SQLException {
        vacancy.setVacancyName(name);
        vacancy.setVacancyDetail(detail);
        vacancy.setDeadline(deadline);
        if (saveMode == 1) {
            saveFile();
        } else if (saveMode == 2) {
            database.updateVacancy(vacancy);
        }
    }

    /**
     * Vacancy Menu 3,4 - Show Submitted List, Show Accepted List
     *
     * @param vacancy
     * @param accepted
     * @return
     * @throws java.sql.SQLException
     */
    public String[] getSubmittedFiles(Vacancy vacancy, boolean accepted) throws SQLException {
        List<ApplicationFile> files = vacancy.getSubmittedFiles(accepted);
        if (saveMode == 2) {
            files = database.loadSubmittedFiles(vacancy, accepted);
        }
        String[] s = new String[files.size()];
        for (int i = 0; i < s.length; i++) {
            s[i] = "application id:" + files.get(i).getApplicationId()
                    + "\t applicant id:" + files.get(i).getName()
                    + "\t resume:" + files.get(i).getResume();

        }
        return s;
    }

    /**
     * Vacancy Menu 5 - Accept Application File
     *
     * @param vacancy
     * @param applicationId
     * @throws java.sql.SQLException
     */
    public void acceptApplication(Vacancy vacancy, int applicationId) throws SQLException {
        ApplicationFile file = vacancy.getSubmittedFile(applicationId);
        if (saveMode == 2) {
            file = database.loadSubmitted(vacancy, applicationId);
        }
        if (file != null) {
            file.setStatus(1);
            if (saveMode == 1) {
                saveFile();
            } else if (saveMode == 2) {
                database.updateApplication(file);
            }
        } else {
            throw new IllegalStateException("Application File not found");
        }
    }

    /**
     * Vacancy Menu 6 - Close Vacancy,
     *
     * @param vacancy
     * @throws java.sql.SQLException
     */
    public void closeVacancy(Vacancy vacancy) throws SQLException {
        vacancy.setActive(false);
        if (saveMode == 1) {
            saveFile();
        } else if (saveMode == 2) {
            database.updateVacancy(vacancy);
        }

    }

    // ======================== Company Menu ========================
    /**
     * Company Menu 2 - Edit Company Profile
     *
     * @param company
     * @param name
     * @param address
     * @throws java.sql.SQLException
     */
    public void editCompany(Company company, String name, String address) throws SQLException {
        company.setName(name);
        company.setAddress(address);
        if (saveMode == 1) {
            saveFile();
        } else if (saveMode == 2) {
            database.updateUser(company);
        }
    }

    /**
     * Company Menu 3 - View Vacancy List
     *
     * @param company
     * @return
     * @throws java.sql.SQLException
     */
    public List<Vacancy> getVacancyList(Company company) throws SQLException {
        if (saveMode == 2) {
            return database.loadVacancyList(company);
        }
        return company.getVacancyList();
    }

    /**
     * Company Menu 4 - Create new Vacancy
     *
     * @param company
     * @param vacancyName
     * @param deadline
     * @throws java.sql.SQLException
     */
    public void createVacancy(Company company, String vacancyName, Date deadline) throws SQLException {
        Vacancy vacancy = company.createVacancy(Vacancy.generateId(), vacancyName, deadline);
        if (saveMode == 1) {
            saveFile();
        } else if (saveMode == 2) {
            database.saveVacancy(company, vacancy);
        }
    }

    /**
     * Company Menu 5 - Select Vacancy
     *
     * @param company
     * @param idVacancy
     * @return
     * @throws java.sql.SQLException
     */
    public Vacancy searchVacancy(Company company, int idVacancy) throws SQLException {
        Vacancy vacancy = company.searchVacancy(idVacancy);
        if (saveMode == 2) {
            vacancy = database.loadVacancy(company, idVacancy);
        }
        if (vacancy != null) {
            return vacancy;
        }
        return null;
    }

    // ======================== Applicant Menu ========================
    /**
     * Applicant Menu 2 - Edit Applicant Profile
     *
     * @param applicant
     * @param name
     * @param gender
     * @param address
     * @param lastEducation
     * @param expertise
     */
    public void editProfile(Applicant applicant, String name, char gender, String address,
            String lastEducation, String expertise) throws SQLException {
        applicant.setName(name);
        applicant.setGender(gender);
        applicant.setAddress(address);
        applicant.setLastEducation(lastEducation);
        applicant.setExpertise(expertise);
        if (saveMode == 1) {
            saveFile();
        } else if (saveMode == 2) {
            database.updateApplicant(applicant);
        }
    }

    /**
     * Applicant Menu 3 - Vacancy List
     *
     * @param applicant
     * @return
     * @throws SQLException
     */
    public String[] getAvailableVacancyList(Applicant applicant) throws SQLException {
        List<String> list = new ArrayList();
        List<Company> companies = getCompanyList();
        if (saveMode == 2) {
            companies = database.loadAvailableVacancyList(applicant);
        }
        for (Company c : companies) {
            List<Vacancy> vacancies = c.getActiveVacancy();
            if (vacancies.size() > 0) {
                for (Vacancy v : vacancies) {
                    String s = "";
                    s += "Company:" + c.getName()
                            + "\tVacancy ID:" + v.getVacancyId()
                            + "\tVacancy Name:" + v.getVacancyName();
                    ApplicationFile file;
                    if (applicant != null && (file = v.getSubmittedFile(applicant.getEmail())) != null) {
                        s += ("\tStatus : "
                                + (file.getStatus() == 0 ? "submitted"
                                        : file.getStatus() == 1 ? "accepted" : "rejected"));
                    } else {
                        s += ("\tDeadline:" + v.getDeadline());
                    }
                    list.add(s);
                }
            }
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * Applicant Menu 4 - Apply Vacancy
     *
     * @param applicant
     * @param vacancy
     * @param resume
     */
    public void applyJob(Applicant applicant, Vacancy vacancy, String resume) throws SQLException {
        ApplicationFile file = applicant.createApplicationFile(resume);
        vacancy.addSubmittedFile(applicant.getEmail(), file);
        if (saveMode == 1) {
            saveFile();
        } else if (saveMode == 2) {
            database.saveApplication(applicant, file);
            database.saveSubmitted(applicant, vacancy, file);
        }
    }

    /**
     * get Vacancy by ID, used for Apply Vacancy
     *
     * @param vacancyId
     * @return Vacancy, null if not found
     */
    public Vacancy getVacancy(int vacancyId) throws SQLException {
        if (saveMode == 2) {
            return database.loadVacancy(null, vacancyId);
        }
        for (Company c : getCompanyList()) {
            for (Vacancy v : c.getActiveVacancy()) {
                if (v.getVacancyId() == vacancyId) {
                    return v;
                }
            }
        }
        return null;
    }

    /**
     * Applicant Menu 5 - Application File List
     *
     * @param applicant
     * @return
     * @throws SQLException
     */
    public String[] getApplicationFiles(Applicant applicant) throws SQLException {
        List<ApplicationFile> files = applicant.getApplicationFiles();
        if (saveMode == 2) {
            files = database.loadApplicationList(applicant);
        }
        String[] s = new String[files.size()];
        for (int i = 0; i < s.length; i++) {
            s[i] = files.get(i).toString();

        }
        return s;
    }

    /**
     * Get Company List
     *
     * @return
     */
    public List<Company> getCompanyList() {
        List<Company> companies = new ArrayList();
        for (User u : users) {
            if (u instanceof Company) {
                companies.add((Company) u);
            }
        }
        return companies;
    }

    // Main Menu
    /**
     * Main Menu 2 - Log In
     *
     * @param email
     * @param password
     * @return User, null if not found
     */
    public User logIn(String email, String password) throws SQLException {
        User user = searchUser(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public User searchUser(String email) throws SQLException {
        if (saveMode == 2) {
            return database.loadUser(email);
        }

        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Main Menu 3,4 - Register
     *
     * @param option user type, 0 = Company, 1 = Applicant
     * @param email
     * @param password
     * @param name
     * @throws java.sql.SQLException
     */
    public void register(int option, String email, String password, String name) throws SQLException {
        User user = searchUser(email);
        if (user == null) {
            switch (option) {
                case 1:
                    user = new Applicant(email, password, name);
                    break;
                case 0:
                    user = new Company(email, password, name);
                    break;
                default:
                    throw new IllegalStateException("define option=1/0");
            }
            users.add(user);
            if (saveMode == 1) {
                saveFile();
            } else if (saveMode == 2) {
                if (user instanceof Applicant) {
                    database.saveApplicant((Applicant) user);
                } else {
                    database.saveUser(user);
                }
            }
        } else {
            throw new IllegalStateException("email already exists");
        }
    }

    /**
     * set save mode
     *
     * @param saveMode save mode, 0 = no save, 1 = fileIO, 2 = database, 3 = ORM
     */
    public void setSaveMode(int saveMode) {
        this.saveMode = saveMode;
        fileIO = null;
        database = null;
        switch (saveMode) {
            case 0:
                break;
            case 1:
                String filename = "data.dat";
                String fileLog = "log.dat";
                fileIO = new FileIO(filename, fileLog);
                loadFile();
                break;
            case 2:
                String server = "jdbc:mysql://localhost:3306/jobvacancy";
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

    /**
     * save users list and log id to fileIO
     */
    private void saveFile() {
        fileIO.saveUsers(users);
        fileIO.saveLog();
    }

    /**
     * load users list and log id from fileIO
     */
    private void loadFile() {
        users = fileIO.loadUsers();
        int[] log = fileIO.loadLog();
        Vacancy.setIterator(log[0]);
        ApplicationFile.setIterator(log[1]);
    }

    /**
     * MD5 Hashing
     *
     * @param input
     * @return
     */
    public static String md5(String input) {
        String result = null;
        if (input == null) {
            return null;
        }
        try {
            MessageDigest dgt = MessageDigest.getInstance("MD5");
            dgt.update(input.getBytes(), 0, input.length());
            result = new BigInteger(1, dgt.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e.getMessage());
        }
        return result;
    }

}
