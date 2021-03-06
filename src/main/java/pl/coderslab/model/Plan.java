package pl.coderslab.model;

import java.util.Date;

public class Plan {

    private int id;
    private String name;
    private String description;
    private Date created;
    private Admin admin;


    public Plan(int id, String name, String description, Date created, Admin admin) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
        this.admin = admin;
    }

    public Plan(int id, String name, String description, Admin admin) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.admin = admin;
    }

    public Plan(String name, String description, Date created, Admin admin){
        this.name = name;
        this.description = description;
        this.created = created;
        this.admin = admin;
    }


    public Plan(String name, String description, Admin admin) {
        this.name = name;
        this.description = description;
        this.admin = admin;
    }

    public Plan() {
    }

    public Admin getAdmin() {
        return admin;
    }

    public Plan setAdmin(Admin admin) {
        this.admin = admin;
        return this;
    }

    public int getId() {
        return id;
    }

    public Plan setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Plan setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Plan setDescription(String description) {
        this.description = description;
        return this;
    }

    public Date getCreated() {
        return created;
    }

    public Plan setCreated(Date created) {
        this.created = created;
        return this;
    }



    @Override
    public String toString() {
        return "Plan [id=" + id + ", name=" + name + ", description=" + description + ", created=" + created +
                ", admin=" + admin + "]";
    }



}
