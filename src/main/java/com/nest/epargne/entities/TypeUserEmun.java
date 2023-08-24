package com.nest.epargne.entities;

public enum TypeUserEmun {
    PROFESSIONNAL("PROFESSIONNAL"),
    PATIENT("PATIENT");
    public  final String label ;

    private TypeUserEmun(String label) {
        this.label = label;
    }
}
