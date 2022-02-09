package pl.coderslab.model;

import javax.xml.crypto.Data;
import java.util.Date;

public class Recipe {

    private int id;
    private String name;
    private String ingredients;
    private String description;
    private Date created;
    private Date updated;
    private int prepTime;
    private String preparation;
    private int adminId;


    public Recipe() {
    };
    public Recipe(String name, String ingredients, String description, int prepTime, String preparation, int adminId) {
        this.name = name;
        this.ingredients = ingredients;
        this.description = description;
        this.prepTime = prepTime;
        this.preparation = preparation;
        this.adminId = adminId;
    }

    public Recipe(String name, String ingredients, String description, Date created, Date updated, int prepTime, String preparation, int adminId) {
        this.name = name;
        this.ingredients = ingredients;
        this.description = description;
        this.created = created;
        this.updated = updated;
        this.prepTime = prepTime;
        this.preparation = preparation;
        this.adminId = adminId;
    }

    public Recipe(int id, String name, String ingredients, String description, int prepTime, String preparation, int adminId) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.description = description;
        this.prepTime = prepTime;
        this.preparation = preparation;
        this.adminId = adminId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getDescription() {
        return description;
    }

    public String getPreparation() {
        return preparation;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", description='" + description + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", prepTime=" + prepTime +
                ", preparation='" + preparation + '\'' +
                ", adminId=" + adminId +
                '}';
    }
}
