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
    //    1. Edit Vacancy Detail
    public void editVacancy(Vacancy v, String name, String detail, Date deadline) {
        v.setVacancyName(name);
        v.setVacancyDetail(detail);
        v.setDeadline(deadline);
        if (saveMode == 1) {
            saveFile();
        } else if (saveMode == 2) {
        }
    }

    //    4. Accept Application File
    public void acceptApplication(Vacancy v, int applicationId) {
        v.acceptFile(applicationId);
        if (saveMode == 1) {
            saveFile();
        } else if (saveMode == 2) {
        }
    }

    //    5. Close Vacancy
    public void closeVacancy(Vacancy v) {
        v.setActive(false);
        if (saveMode == 1) {
            saveFile();
        } else if (saveMode == 2) {
        }
    }

    // Company Menu
    //    1. Edit Profile
    public void editCompany(Company c, String name, String address) {
        c.setName(name);
        c.setAddress(address);
        if (saveMode == 1) {
            saveFile();
        } else if (saveMode == 2) {
        }
    }

    //    3. Create Vacancy
    public void createVacancy(Company c, String name, Date deadline) {
        c.createVacancy(Vacancy.generateId(), name, deadline);
        if (saveMode == 1) {
            saveFile();

        } else if (saveMode == 2) {
        }
    }

    // Applicant Menu
    //    1. Edit Profile
    public void editProfile(Applicant a, String name, String address, String lastEducation, String expertise) {
        a.setName(name);
        a.setAddress(address);
        a.setLastEducation(lastEducation);
        a.setExpertise(expertise);
        if (saveMode == 1) {
            saveFile();

        } else if (saveMode == 2) {
        }
    }

    //    3. Apply Vacancy
    public void applyJob(Applicant a, int vacancyId, String resume) {
        Vacancy v = getVacancy(vacancyId);
        if (v == null) {
            throw new IllegalStateException("Vacancy not found");
        }
        ApplicationFile file = a.createApplicationFile(resume);
        v.addSubmittedFile(a.getEmail(), file);
        if (saveMode == 1) {
            saveFile();

        } else if (saveMode == 2) {
        }
    }

    public Vacancy getVacancy(int vacancyId) {
        if (saveMode == 1) {
            loadFile();
        } else if (saveMode == 2) {
        }
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
    //    1. Log In
    public User searchUser(String email, String password) {
        if (saveMode == 1) {
            loadFile();
        } else if (saveMode == 2) {
        }
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    //    2. Register
    public void register(int option, String email, String password, String name, String address) {
        User user;
        if (option == 1) {
            user = new Applicant(email, password);
        } else if (option == 0) {
            user = new Company(email, password);
        } else {
            throw new IllegalStateException("define option=1/0");
        }
        user.setName(name);
        user.setAddress(address);
        adduser(user);
    }

    public List<User> getUsers() {
        if (saveMode == 1) {
            loadFile();
        } else if (saveMode == 2) {
        }
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
        if (saveMode == 1) {
            saveFile();

        } else if (saveMode == 2) {
        }
    }

    public void adduser(User user) {
        users.add(user);
        if (saveMode == 1) {
            saveFile();

        } else if (saveMode == 2) {
        }
    }

    public User getUser(int i) {
        if (saveMode == 1) {
            loadFile();
        } else if (saveMode == 2) {
        }
        return users.get(i);
    }

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

    private void saveFile() {
        file.saveUsers(users);
        file.saveLog();
    }

    private void loadFile() {
        users = file.loadUsers();
        int[] log = file.loadLog();
        Vacancy.setIterator(log[0]);
        ApplicationFile.setIterator(log[1]);
    }

}
