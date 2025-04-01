package Model;

import java.util.Date;

public class Person {
    private String name;
    private final String cpf;
    private final Date dateOfBirth;

    private Person(String name, String cpf, Date dateOfBirth) {
        this.name = name;
        this.cpf = cpf;
        this.dateOfBirth = dateOfBirth;
    }

    public static Person getPerson(String name, String cpf, Date dateOfBirth) {
        if(dateOfBirth.after(new Date())) {
            throw new IllegalArgumentException("Data de nascimento não pode ser após hoje.");
        }
        return new Person(name, cpf, dateOfBirth);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }
}