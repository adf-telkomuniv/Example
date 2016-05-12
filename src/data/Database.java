/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Applicant;
import model.ApplicationFile;
import model.Company;
import model.User;
import model.Vacancy;

/**
 *
 * @author dee
 */
public class Database {

    private String server = "";
    private String dbuser = "root";
    private String dbpasswd = "";
    private Statement statement = null;
    private Connection connection = null;

    public Database(String server, String dbuser, String dbpasswd) {
        this.server = server;
        this.dbuser = dbuser;
        this.dbpasswd = dbpasswd;
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection(server, dbuser, dbpasswd);
            statement = connection.createStatement();
        } catch (Exception e) {
            throw new AssertionError("Error Connection to Database");
        }
    }

    public void saveUser(User user) throws SQLException {
        String query = "insert into users(email, password, name, address) "
                + "values ("
                + "'" + user.getEmail() + "', "
                + "'" + user.getPassword() + "', "
                + "'" + user.getName() + "', "
                + "'" + user.getAddress() + "' "
                + ")";
        statement.execute(query);
    }

    public void saveApplicant(Applicant applicant) throws SQLException {
        saveUser(applicant);
        String query = "insert into applicants(email, gender, lastEducation, expertise) "
                + "values ("
                + "'" + applicant.getEmail() + "', "
                + "'" + applicant.getGender() + "', "
                + "'" + applicant.getLastEducation() + "', "
                + "'" + applicant.getExpertise() + "' "
                + ")";
        statement.execute(query);
    }

    public void saveApplication(Applicant applicant, ApplicationFile file) throws SQLException {
        String query = "insert into applicationfiles (name, resume, status, date_created, email) "
                + "values ("
                + "'" + file.getName() + "', "
                + "'" + file.getResume() + "', "
                + file.getStatus() + ", "
                + "'" + new java.sql.Date(file.getDate_created().getTime()) + "', "
                + "'" + applicant.getEmail() + "' "
                + ")";
        statement.execute(query, Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = statement.getGeneratedKeys();
        int generatedId = -1;
        if (rs.next()) {
            generatedId = rs.getInt(1);
        }
        file.setApplicationId(generatedId);
    }

    public void saveVacancy(Company company, Vacancy vacancy) throws SQLException {
        String query = "insert into vacancies (name, detail, deadline, active, companyemail) "
                + "values ("
                + "'" + vacancy.getVacancyName() + "', "
                + "'" + vacancy.getVacancyDetail() + "', "
                + "'" + new java.sql.Date(vacancy.getDeadline().getTime()) + "', "
                + vacancy.isActive() + ", "
                + "'" + company.getEmail() + "', "
                + ")";
        statement.execute(query, Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = statement.getGeneratedKeys();
        int generatedId = -1;
        if (rs.next()) {
            generatedId = rs.getInt(1);
        }
        vacancy.setVacancyId(generatedId);
    }

    public void saveSubmitted(Applicant applicant, Vacancy vacancy, ApplicationFile file) throws SQLException {
        String query = "insert into submittedfiles (vacancyid, email, applicationid) "
                + "values ("
                + vacancy.getVacancyId() + ", "
                + "'" + applicant.getEmail() + "', "
                + file.getApplicationId() + ") "
                + "on duplicate key update applicationid = " + file.getApplicationId();
        statement.execute(query);
    }

    public void updateUser(User user) throws SQLException {
        String query = "update user set "
                + "password = '" + user.getPassword() + "', "
                + "name = '" + user.getName() + "', "
                + "address = '" + user.getAddress() + "' "
                + "where email = '" + user.getEmail() + "'";
        statement.executeUpdate(query);
    }

    public void updateApplicant(Applicant applicant) throws SQLException {
        updateUser(applicant);
        String query = "update applicants set "
                + "gender = '" + applicant.getGender() + "', "
                + "lastEducation = '" + applicant.getLastEducation() + "', "
                + "expertise = '" + applicant.getExpertise() + "' "
                + "where email = '" + applicant.getEmail() + "'";
        statement.executeUpdate(query);
    }

    public void updateApplication(ApplicationFile file) throws SQLException {
        String query = "update applicationfile set "
                + "name='" + file.getName() + "', "
                + "resume='" + file.getResume() + "', "
                + "status=" + file.getStatus() + " "
                + "where applicationId=" + file.getApplicationId();
        statement.executeUpdate(query);
    }

    public void updateVacancy(Vacancy vacancy) throws SQLException {
        String query = "update applicants set "
                + "name='" + vacancy.getVacancyName() + "', "
                + "detail='" + vacancy.getVacancyDetail() + "', "
                + "deadline='" + new java.sql.Date(vacancy.getDeadline().getTime()) + "', "
                + "active= " + vacancy.isActive() + " "
                + "where vacancyid=" + vacancy.getVacancyId();
        statement.executeUpdate(query);
    }

    public Company loadCompany(String email) throws SQLException {
        Company c = null;
        String query = "select password, name, address from users where email = '" + email + "'";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            c = new Company(email, rs.getString(1), rs.getString(2), rs.getString(3));
        }
        return c;
    }

    public Applicant loadApplicant(String email) throws SQLException {
        Applicant a = null;
        String query = "SELECT "
                + "password, name, address,lastEducation, expertise, gender "
                + "FROM users u, applicants a "
                + "WHERE u.email = a.email and u.email = '" + email + "'";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            a = new Applicant(email, rs.getString(1), rs.getString(2),
                    rs.getString(3), rs.getString(4), rs.getString(5),
                    rs.getString(6).charAt(0));
        }
        return a;
    }

    public User loadUser(String email) throws SQLException {
        User u = null;
        String query = "SELECT "
                + "lastEducation, expertise, gender "
                + "FROM applicants "
                + "WHERE email = '" + email + "'";
        ResultSet rs = statement.executeQuery(query);

        query = "select password, name, address from users where email = '" + email + "'";
        ResultSet rs2 = statement.executeQuery(query);

        while (rs.next()) {
            while (rs2.next()) {
                u = new Applicant(email, rs2.getString(1), rs2.getString(2),
                        rs2.getString(3), rs.getString(1), rs.getString(2),
                        rs.getString(3).charAt(0));
            }
        }
        if (u == null) {
            u = new Company(email, rs2.getString(1), rs2.getString(2), rs2.getString(3));
        }
        return u;
    }

    public Vacancy loadVacancy(Company c, int vacancyId) throws SQLException {
        Vacancy v = null;
        String query = "select vacancyid, name, deadline, detail, active from vacancies where vacancyid = " + vacancyId;
        if (c != null) {
            query += " and companyemail = '" + c.getEmail() + "'";
        }
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            v = new Vacancy(vacancyId, rs.getString(2), rs.getDate(3), rs.getString(4), rs.getBoolean(5));
        }
        return v;
    }

    public List<Vacancy> loadVacancyList(Company c) throws SQLException {
        List<Vacancy> list = new ArrayList();
        String query = "select vacancyid, name, deadline, detail, active from vacancies where companyemail = '" + c.getEmail() + "'";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            list.add(new Vacancy(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getString(4), rs.getBoolean(5)));
        }
        return list;
    }

    public ApplicationFile loadSubmitted(Vacancy vacancy, int applicationId) throws SQLException {
        ApplicationFile file = null;
        String query = "select a.applicationid, name, resume, status, date_created "
                + "from applicationfiles a, submittedfiles s "
                + "where a.applicationId=" + applicationId + " "
                + "and a.applicationId = s.applicationId and s.vacancyId=" + vacancy.getVacancyId();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            file = new ApplicationFile(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5));
        }
        return file;
    }

    public List<ApplicationFile> loadSubmittedFiles(Vacancy vacancy, boolean accepted) throws SQLException {
        List<ApplicationFile> files = new ArrayList();
        String query = "select a.applicationid, name, resume, status, date_created "
                + "from applicationfiles a, submittedfiles s "
                + "where a.applicationId = s.applicationId "
                + "and status=" + accepted + " "
                + "and s.vacancyId=" + vacancy.getVacancyId();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            files.add(new ApplicationFile(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5)));
        }
        return files;
    }

    public List<ApplicationFile> loadApplicationList(Applicant a) throws SQLException {
        List<ApplicationFile> list = new ArrayList();
        String query = "select applicationid, name, resume, status, date_created "
                + "from applicationfiles where email = '" + a.getEmail() + "'";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            list.add(new ApplicationFile(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5)));
        }
        return list;
    }

    public List<Company> loadAvailableVacancyList(Applicant applicant) throws SQLException {
        Map<Integer, ApplicationFile> files = new HashMap();
        Map<String, Company> companies = new HashMap();
        String query = "select (email,password,name,address) from users where email not in (select email from applicants)";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            companies.put(rs.getString(1), new Company(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
        }

        if (applicant != null) {
            query = "select s.vacancyid, a.applicationId, name, resume, status, date_created "
                    + "from applicationfiles a join submittedfiles s on s.applicationId =a.applicationId "
                    + "where s.email = '" + applicant.getEmail() + "'";
            rs = statement.executeQuery(query);
            while (rs.next()) {
                files.put(rs.getInt(1), new ApplicationFile(
                        rs.getInt(2), rs.getString(3), rs.getString(4),
                        rs.getInt(5), rs.getDate(6)));
            }
        }
        query = "select vacancyId, v.name, deadline, detail, active, u.email "
                + "from vacancies v, users u where u.email = v.companyemail";
        rs = statement.executeQuery(query);
        while (rs.next()) {
            Vacancy v = new Vacancy(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getString(4), rs.getBoolean(5));
            if (applicant != null) {
                ApplicationFile file = files.get(v.getVacancyId());
                v.addSubmittedFile(applicant.getEmail(), file);
            }
            Company c = companies.get(rs.getString(6));
            c.getVacancyList().add(v);
        }

        return new ArrayList(companies.values());
    }

}
