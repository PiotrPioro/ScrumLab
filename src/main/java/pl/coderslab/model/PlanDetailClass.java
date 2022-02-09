package pl.coderslab.model;

public class PlanDetailClass {

    private int planRecipeId;
    private String dayName;
    private String mealName;
    private String recipeName;
    private int recipeId;
    private String recipeDescription;

    public PlanDetailClass() {
    }

    public PlanDetailClass(int planRecipeId, String dayName, String mealName, String recipeName, int recipeId, String recipeDescription) {
        this.planRecipeId = planRecipeId;
        this.dayName = dayName;
        this.mealName = mealName;
        this.recipeName = recipeName;
        this.recipeId = recipeId;
        this.recipeDescription = recipeDescription;
    }

    public int getPlanRecipeId() {
        return planRecipeId;
    }

    public void setPlanRecipeId(int planRecipeId) {
        this.planRecipeId = planRecipeId;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public void setRecipeDescription(String recipeDescription) {
        this.recipeDescription = recipeDescription;
    }

    @Override
    public String toString() {
        return "PlanDetail [" + "dayName=" + this.dayName + ", mealName=" + this.mealName + ", recipeName=" + this.recipeName + ", recipeDescription=" + recipeDescription + "]";
    }
}
