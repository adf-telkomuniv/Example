/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author dee
 */
public class Company extends User implements Serializable {

    private List<Vacancy> vacancyList;

    public Company(String email, String password, String name) {
        super(email, password, name);
        vacancyList = new ArrayList();
    }

    public Company(String email, String password, String name, String address) {
        super(email, password, name, address);
        vacancyList = new ArrayList();
    }

    public Vacancy createVacancy(String vacancyName, Date deadline) {
        Vacancy v = new Vacancy(vacancyName, deadline);
        vacancyList.add(v);
        return v;
    }

    public Vacancy createVacancy(int idVacancy, String vacancyName, Date deadline) {
        Vacancy v = new Vacancy(idVacancy, vacancyName, deadline);
        vacancyList.add(v);
        return v;
    }

    public Vacancy getVacancy(int i) {
        return vacancyList.get(i);
    }

    public Vacancy searchVacancy(int idVacancy) {
        for (Vacancy vacancy : vacancyList) {
            if (vacancy.getVacancyId() == idVacancy) {
                return vacancy;
            }
        }
        return null;
    }

    public void removeVacancy(int idVacancy) {
        Vacancy v = searchVacancy(idVacancy);
        if (v != null) {
            vacancyList.remove(v);
        }
    }

    public List<Vacancy> getVacancyList() {
        return vacancyList;
    }

    public List<Vacancy> getActiveVacancy() {
        List<Vacancy> list = new ArrayList();
        for (Vacancy v : vacancyList) {
            if (v.isActive()) {
                list.add(v);
            }
        }
        return list;
    }

    public void setVacancyList(List<Vacancy> vacancyList) {
        this.vacancyList = vacancyList;
    }

    @Override
    public String toString() {
//        return super.toString()
//                + "\n\tCompany{"
//                + "\n\t vacancyList=" + vacancyList
//                + "\n\t}";
        return "Company{"
                + "\n email=" + getEmail()
                + "\n name=" + getName()
                + "\n address=" + getAddress()
                + "\n}";
    }

}
