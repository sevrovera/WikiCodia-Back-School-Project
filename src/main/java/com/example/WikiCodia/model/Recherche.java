package com.example.WikiCodia.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;

public class Recherche implements  Serializable {

    private String searchString;

    private LocalDate dateCreate;

    private LocalDate dateModif;

    private List<Langage> language;

    private List<Framework> framework;

    private List<Categorie> category;

    private List<String> popularity;

    private List<Type> type;

    public Recherche(String searchString, LocalDate dateCreate, LocalDate dateModif, List<Langage> language, List<Framework> framework, List<Categorie> category, List<String> popularity, List<Type> type) {
        this.searchString = searchString;
        this.dateCreate = dateCreate;
        this.dateModif = dateModif;
        this.language = language;
        this.framework = framework;
        this.category = category;
        this.popularity = popularity;
        this.type = type;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public LocalDate getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDate dateCreate) {
        this.dateCreate = dateCreate;
    }

    public LocalDate getDateModif() {
        return dateModif;
    }

    public void setDateModif(LocalDate dateModif) {
        this.dateModif = dateModif;
    }

    public List<Langage> getLanguage() {
        return language;
    }

    public void setLanguage(List<Langage> language) {
        this.language = language;
    }

    public List<Framework> getFramework() {
        return framework;
    }

    public void setFramework(List<Framework> framework) {
        this.framework = framework;
    }

    public List<Categorie> getCategory() {
        return category;
    }

    public void setCategory(List<Categorie> category) {
        this.category = category;
    }

    public List<String> getPopularity() {
        return popularity;
    }

    public void setPopularity(List<String> popularity) {
        this.popularity = popularity;
    }

    public List<Type> getType() {
        return type;
    }

    public void setType(List<Type> type) {
        this.type = type;
    }

    public boolean checkNull() throws IllegalAccessException {
        for (Field f: getClass().getDeclaredFields())
            if(f.get(this) != null)
                return false;
        return false;
    }
}

