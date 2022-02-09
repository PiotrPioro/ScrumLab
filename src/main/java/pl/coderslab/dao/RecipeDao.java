package pl.coderslab.dao;

import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Recipe;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeDao {
    private static final String CREATE_RECIPE_QUERY = "INSERT INTO recipe (name, ingredients, description, created, updated, preparation_time, preparation, admin_id) values (?, ?, ?, now(), now(), ?, ?, ?);";
    private static final String DELETE_RECIPE_QUERY = "DELETE FROM recipe where id = ?;";
    private static final String FIND_ALL_RECIPES_QUERY = "SELECT * FROM recipe;";
    private static final String READ_RECIPE_QUERY = "SELECT * from recipe where id = ?;";
    private static final String UPDATE_RECIPE_QUERY = "UPDATE recipe SET name = ? , ingredients = ?, description = ?, preparation_time = ?, preparation = ?, updated = now(), admin_id = ? WHERE id = ?;";
    private static final String NUMBER_OF_RECIPES_QUERY = "select count(id) from recipe where admin_id = ?;";

//READ//
    public Recipe read(Integer bookId) {
        Recipe recipe = new Recipe();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_RECIPE_QUERY)
        ) {
            statement.setInt(1, bookId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    recipe.setId(resultSet.getInt("id"));
                    recipe.setName(resultSet.getString("name"));
                    recipe.setDescription(resultSet.getString("description"));
                    recipe.setIngredients(resultSet.getString("ingredients"));
                    recipe.setCreated(resultSet.getDate("created"));
                    recipe.setUpdated(resultSet.getDate("updated"));
                    recipe.setPreparation(resultSet.getString("preparation"));
                    recipe.setPrepTime(resultSet.getInt("preparation_time"));
                    recipe.setAdminId(resultSet.getInt("admin_id"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recipe;

    }
//FIND ALL//
    public List<Recipe> findAll() {
        List<Recipe> recipeList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_RECIPES_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Recipe recipe = new Recipe();
                recipe.setId(resultSet.getInt("id"));
                recipe.setName(resultSet.getString("name"));
                recipe.setDescription(resultSet.getString("description"));
                recipe.setIngredients(resultSet.getString("ingredients"));
                recipe.setCreated(resultSet.getDate("created"));
                recipe.setUpdated(resultSet.getDate("updated"));
                recipe.setPreparation(resultSet.getString("preparation"));
                recipe.setPrepTime(resultSet.getInt("preparation_time"));
                recipe.setAdminId(resultSet.getInt("admin_id"));
                recipeList.add(recipe);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipeList;

    }

  //CREATE//
    public Recipe create(Recipe recipe) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement insertStm = connection.prepareStatement(CREATE_RECIPE_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertStm.setString(1, recipe.getName());
            insertStm.setString(2, recipe.getIngredients());
            insertStm.setString(3, recipe.getDescription());
            insertStm.setInt(4, recipe.getPrepTime());
            insertStm.setString(5, recipe.getPreparation());
            insertStm.setInt(6, recipe.getAdminId());
            int result = insertStm.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }

            try (ResultSet generatedKeys = insertStm.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    recipe.setId(generatedKeys.getInt(1));
                    return recipe;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


//DELETE//
    public void delete(Integer recipeId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_RECIPE_QUERY)) {
            statement.setInt(1, recipeId);
            statement.executeUpdate();

            boolean deleted = statement.execute();
            if (!deleted) {
                throw new NotFoundException("Product not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


 //UPDATE//
    public void update(Recipe recipe) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_RECIPE_QUERY)) {
            statement.setInt(7, recipe.getId());
            statement.setString(1, recipe.getName());
            statement.setString(2, recipe.getIngredients());
            statement.setString(3, recipe.getDescription());
            statement.setInt(4, recipe.getPrepTime());
            statement.setString(5, recipe.getPreparation());
            statement.setInt(6, recipe.getAdminId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int numberOfRecipesByAdminId (int adminId){

        int number = -1;
        try(Connection connection = DbUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(NUMBER_OF_RECIPES_QUERY)) {

            statement.setInt(1, adminId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                number = resultSet.getInt("count(id)");
            }

            return number;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return number;
    }
}
