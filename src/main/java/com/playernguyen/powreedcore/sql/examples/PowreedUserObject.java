package com.playernguyen.powreedcore.sql.examples;

import java.util.UUID;

public class PowreedUserObject {

    private final UUID uuid;
    private final double balance;

    public PowreedUserObject(UUID uuid, double balance) {
        this.uuid = uuid;
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public UUID getUUID() {
        return uuid;
    }

    @Override
    public String toString() {
        return "PowreedUserObject{" +
                "uuid=" + uuid +
                ", balance=" + balance +
                '}';
    }
}
