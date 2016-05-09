/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_console;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import model.Applicant;
import model.Company;
import model.User;
import model.Vacancy;
import model.Application;
import model.ApplicationFile;

/**
 *
 * @author dee
 */
public class ConsoleView {

    private Scanner numIn;
    private Scanner strIn;
    private final Application app;

    public ConsoleView(Application model) {
        this.app = model;
        numIn = new Scanner(System.in);
        strIn = new Scanner(System.in);
        mainMenu();
    }

    private void vacancyMenu(Vacancy v) {
        int menu = -1;
        do {
            try {
                System.out.println("---------Vacancy Menu--------");
                System.out.println("1. View Vacancy Detail");
                System.out.println("2. Edit Vacancy Detail");
                System.out.println("3. View Submitted Application");
                System.out.println("4. View Accepted Application");
                System.out.println("5. Accept Application File");
                System.out.println("6. Close Vacancy");
                System.out.println("0. Back");
                System.out.println("Input menu : ");
                menu = numIn.nextInt();
                switch (menu) {
                    case 1:
                        System.out.println("--------Vacancy Detail---------");
                        System.out.println(v);
                        break;
                    case 2:
                        System.out.println("----------Edit Detail----------");
                        System.out.println("input Vacancy Name : ");
                        String name = strIn.nextLine();
                        System.out.println("input Vacancy Detail : ");
                        String detail = strIn.nextLine();
                        System.out.println("input deadline : ");
                        try {
                            Date deadline = new Date(strIn.nextLine());
                            app.editVacancy(v, name, detail, deadline);
                        } catch (IllegalArgumentException e) {
                            System.out.println("error while parsing date");
                        }
                        break;
                    case 3:
                        System.out.println("-------View Submitted Application-------");
                        printMap(v.getSubmittedFiles());
                        break;
                    case 4:
                        System.out.println("-------View Accepted Application-------");
                        printMap(v.getAcceptedFiles());
                        break;
                    case 5:
                        System.out.println("-------Accept Application-------");
                        System.out.println("Input Application ID : ");
                        int applicationId = numIn.nextInt();
                        app.acceptApplication(v, applicationId);
                        break;
                    case 6:
                        app.closeVacancy(v);
                        System.out.println("Vacancy closed");
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Wrong Choice");
                }
            } catch (InputMismatchException e) {
                System.out.println("Wrong input type");
                numIn = new Scanner(System.in);
                strIn = new Scanner(System.in);
            } catch (Exception e) {
                System.out.println("Error in Vacancy Menu " + e.getMessage());
            }
        } while (menu != 0);
    }

    private void companyMenu(Company c) {
        int menu = -1;
        do {
            try {
                System.out.println("----Company Menu----");
                System.out.println("1. View Profile");
                System.out.println("2. Edit Profile");
                System.out.println("3. Show Vacancy List");
                System.out.println("4. Create Vacancy");
                System.out.println("5. Select Vacancy");
                System.out.println("0. Back");
                System.out.print("Input Menu : ");
                menu = numIn.nextInt();
                switch (menu) {
                    case 1:
                        System.out.println("----------Profile------------");
                        System.out.println(c);
                        break;
                    case 2:
                        System.out.println("----------Edit Profile-----------");
                        System.out.println("input company name : ");
                        String name = strIn.nextLine();
                        System.out.println("input company address : ");
                        String address = strIn.nextLine();
                        app.editCompany(c, name, address);
                        break;
                    case 3:
                        System.out.println("-------View Vacancy List-------");
                        List<Vacancy> vacancyList = c.getVacancyList();
                        vacancyList.stream().forEach(System.out::println);
                        break;
                    case 4:
                        System.out.println("-------Create new Vacancy-------");
                        System.out.println("Input Vacancy Name : ");
                        name = strIn.nextLine();
                        System.out.println("Select Deadline : ");
                        try {
                            Date deadline = new Date(strIn.nextLine());
                            app.createVacancy(c, name, deadline);
                        } catch (IllegalArgumentException e) {
                            System.out.println("error while parsing date");
                        }
                        break;
                    case 5:
                        System.out.println("-------Select Vacancy-------");
                        System.out.println("Input Vacancy ID : ");
                        int idVacancy = numIn.nextInt();
                        Vacancy v = c.searchVacancy(idVacancy);
                        if (v != null) {
                            vacancyMenu(v);
                        } else {
                            System.out.println("vacancy id not found");
                        }
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Wrong Choice");
                }
            } catch (InputMismatchException e) {
                System.out.println("Wrong input type");
                numIn = new Scanner(System.in);
                strIn = new Scanner(System.in);
            } catch (Exception e) {
                System.out.println("Error in Company Menu " + e.getMessage());
            }
        } while (menu != 0);

    }

    private void applicantMenu(Applicant a) {
        int menu = -1;
        do {
            try {
                System.out.println("--------Applicant Menu-------");
                System.out.println("1. View Profile");
                System.out.println("2. Edit Profile");
                System.out.println("3. Show Vacancy List");
                System.out.println("4. Apply Vacancy");
                System.out.println("5. Show Application File List");
                System.out.println("0. Back");
                System.out.print("Input Menu : ");
                menu = numIn.nextInt();
                switch (menu) {
                    case 1:
                        System.out.println("----------Profile------------");
                        System.out.println(a);
                        break;
                    case 2:
                        System.out.println("----------Edit Profile-----------");
                        System.out.println("input applicant name : ");
                        String name = strIn.nextLine();
                        System.out.println("input applicant address : ");
                        String address = strIn.nextLine();
                        System.out.println("input applicant last Education : ");
                        String lastEducation = strIn.nextLine();
                        System.out.println("input applicant expertise : ");
                        String expertise = strIn.nextLine();
                        app.editProfile(a, name, address, lastEducation, expertise);
                        break;
                    case 3:
                        System.out.println("---------Vacancy List---------");
                        showVacancy();
                        break;
                    case 4:
                        System.out.println("-------Apply Vacancy-------");
                        System.out.println("Input Vacancy ID : ");
                        int vacancyId = numIn.nextInt();
                        Vacancy v = app.getVacancy(vacancyId);
                        if (v != null) {
                            System.out.println(v);
                            System.out.println("Input Resume : ");
                            String resume = strIn.nextLine();
                            app.applyJob(a, v, resume);
                        } else {
                            System.out.println("Vacancy ID not found");
                        }
                        break;
                    case 5:
                        System.out.println("-------Application File List-------");
                        for (ApplicationFile file : a.getApplicationFiles()) {
                            System.out.println(file);
                        }
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Wrong Choice");
                }
            } catch (InputMismatchException e) {
                System.out.println("Wrong input type");
                numIn = new Scanner(System.in);
                strIn = new Scanner(System.in);
            } catch (Exception e) {
                System.out.println("Error in Applicant Menu " + e.getMessage());
            }
        } while (menu != 0);

    }

    private void mainMenu() {

        int menu = -1;
        do {
            try {
                System.out.println("----------------------");
                System.out.println("-------Main Menu------");
                System.out.println("1. Show Vacancies");
                System.out.println("2. Log In");
                System.out.println("3. Register Company");
                System.out.println("4. Register Applicant");
                System.out.println("0. Exit");
                System.out.print("Input Menu : ");
                menu = numIn.nextInt();
                switch (menu) {
                    case 1:
                        System.out.println("---------Vacancy List---------");
                        showVacancy();
                        break;
                    case 2:
                        System.out.println("input email : ");
                        String email = strIn.nextLine();
                        System.out.println("input password : ");
                        String password = strIn.nextLine();
                        User user = app.logIn(email, password);
                        if (user == null) {
                            System.out.println("Wrong email and password");
                        } else if (user instanceof Applicant) {
                            applicantMenu((Applicant) user);
                        } else if (user instanceof Company) {
                            companyMenu((Company) user);
                        }
                        break;
                    case 3:
                        System.out.println("input email : ");
                        email = strIn.nextLine();
                        System.out.println("input password : ");
                        password = strIn.nextLine();
                        System.out.println("input company name : ");
                        String name = strIn.nextLine();
                        System.out.println("input company address : ");
                        String address = strIn.nextLine();
                        app.register(0, email, password, name, address);
                        break;
                    case 4:
                        System.out.println("input email : ");
                        email = strIn.nextLine();
                        System.out.println("input password : ");
                        password = strIn.nextLine();
                        System.out.println("input applicant name : ");
                        name = strIn.nextLine();
                        System.out.println("input applicant address : ");
                        address = strIn.nextLine();
                        app.register(1, email, password, name, address);
                        break;
                    case 0:
                        System.out.println("thank you");
                        break;
                    default:
                        System.out.println("Wrong Choice");
                }
            } catch (InputMismatchException e) {
                System.out.println("Wrong input type");
                numIn = new Scanner(System.in);
                strIn = new Scanner(System.in);
            } catch (Exception e) {
                System.out.println("Error in Main Menu " + e.getMessage());
            }
        } while (menu != 0);

    }

    private void printMap(Map<String, ApplicationFile> map) {
        if (map.isEmpty()) {
            System.out.println("Empty List");
        } else {
            for (Map.Entry<String, ApplicationFile> entry : map.entrySet()) {
                String key = entry.getKey();
                ApplicationFile file = entry.getValue();
                System.out.println("application id:" + file.getApplicationId()
                        + "\t applicant id:" + key
                        + "\t resume:" + file.getResume());
            }
        }
    }

    private void showVacancy() {
        for (User u : app.getUsers()) {
            if (u instanceof Company) {
                Company c = (Company) u;
                System.out.println("Company : " + c.getName());
                for (Vacancy v : c.getActiveVacancy()) {
                    System.out.println(
                            "Vacancy ID:" + v.getVacancyId()
                            + "\tVacancy Name:" + v.getVacancyName()
                            + "\tDeadline:" + v.getDeadline());
                }
            }
        }
    }
}
