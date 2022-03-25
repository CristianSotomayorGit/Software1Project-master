package com.example.software1project;

public class InHousePart extends Part {
    private int machineId;

    public InHousePart(int id, String name, double price, int stock, int minimum, int maximum, int machineId) {
        super(id, name, price, stock, minimum, maximum);

        this.machineId = machineId;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}