package pl.coderslab.dao;

import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.RecipePlan;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class RecipePlanDao {

    private static final String create_query = "INSERT INTO `recipe_plan` (`id`, `recipe_id`, `meal_name`, `display_order`, `day_name_id`, `plan_id`) VALUES (null, ?, ?, ?, ?, ?) ;";
    private static final String delete_query = "DELETE FROM recipe_plan where id = ?;";

    public RecipePlan create(RecipePlan recipePlan){
        try(Connection conn = DbUtil.getConnection()){
            PreparedStatement statement = conn.prepareStatement(create_query, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, recipePlan.getRecipeId());
            statement.setString(2, recipePlan.getMealName());
            statement.setInt(3, recipePlan.getDisplayOrder());
            statement.setInt(4, recipePlan.getDayNameId());
            statement.setInt(5, recipePlan.getPlanId());

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();

            if(resultSet.next()){
                recipePlan.setId(resultSet.getInt(1));
            }
            return recipePlan;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void delete(int planId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(delete_query)) {

            statement.setInt(1, planId);
            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
