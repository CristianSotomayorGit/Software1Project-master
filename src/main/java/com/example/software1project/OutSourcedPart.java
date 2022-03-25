package com.example.software1project;

public class OutSourcedPart extends Part {
    private String companyName;

    public OutSourcedPart(int partId, String name, double price, int stock, int minimum, int maximum, String companyName) {
        super(partId, name, price, stock, minimum, maximum);

        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
