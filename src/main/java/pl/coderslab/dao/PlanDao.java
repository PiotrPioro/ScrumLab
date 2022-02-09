package pl.coderslab.dao;

import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.*;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlanDao {

    private static final AdminDao adminDao = new AdminDao();
    private static final PlanDao planDao = new PlanDao();

    // zapytania SQL
    private static final String CREATE_PLAN_QUERY = "INSERT INTO `plan` (`id`, `name`, `description`, `created`, `admin_id`)" +
            " VALUES(null, ?, ?, now(), ?) ;";
    private static final String DELETE_PLAN_QUERY = "DELETE FROM plan where id = ?;";
    private static final String FIND_ALL_PLANS_QUERY = "SELECT * FROM plan order by created desc ;";
    private static final String READ_PLAN_QUERY = "SELECT * from plan where id = ?;";
    private static final String UPDATE_PLAN_QUERY = "UPDATE	plan SET name = ?, description = ?, admin_id = ? WHERE	id = ?;";
    private static final String CREATE_RECIPE_PLAN_QUERY = "INSERT INTO `recipe_plan` (`id`, `recipe_id`, `meal_name`, `display_order`, `day_name_id`, `plan_id`) VALUES (null, ?, ?, ?, ?, ?) ;";
    private static final String COUNT_PLAN_QUERY = "SELECT count(admin_id) from plan where admin_id= ?;";
    private static final String GET_LAST_RECIPE_PLAN_BU_USER_ID = "SELECT day_name.name as day_name, meal_name, recipe.id, recipe.name as recipe_name, recipe.description as recipe_description\n" +
            "FROM `recipe_plan`\n" +
            "JOIN day_name on day_name.id=day_name_id\n" +
            "JOIN recipe on recipe.id=recipe_id WHERE\n" +
            "recipe_plan.plan_id =  (SELECT MAX(id) from plan WHERE admin_id = ?)\n" +
            "ORDER by day_name.display_order, recipe_plan.display_order;";
    private static final String GET_RECIPE_PLAN_BY_PLAN_ID = "SELECT day_name.name as day_name, meal_name, recipe.id, recipe.name as recipe_name, recipe.description as recipe_description\n" +
            "FROM `recipe_plan`\n" +
            "JOIN day_name on day_name.id=day_name_id\n" +
            "JOIN recipe on recipe.id=recipe_id WHERE plan_id = ?\n" +
            "ORDER by day_name.display_order, recipe_plan.display_order;";
    private static final String GET_LAST_PLAN_BY_USER_ID = "select * from plan where admin_id = ? order by id desc limit 1;";
    private static final String GET_PLANS_BU_USER_ID = "select * from plan where admin_id = ?";
    private static final String GET_PLAN_DETAILS_QUERY = "SELECT recipe_plan.id as recipe_plan_id, day_name.name as day_name, meal_name,\n" +
            "recipe.name as recipe_name, recipe_plan.plan_id as plan_id,\n" +
            "recipe.description as recipe_description,\n" +
            "recipe.id as recipe_id,\n" +
            "recipe_plan.display_order as display_order\n" +
            "FROM `recipe_plan`\n" +
            "JOIN day_name on day_name.id=day_name_id\n" +
            "JOIN recipe on recipe.id=recipe_id WHERE admin_id = ? and plan_id=?\n" +
            "ORDER by day_name.display_order, recipe_plan.display_order;";
    //koljeność metod C R U D


    //create
    public Plan create(Plan plan) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement insertStm = connection.prepareStatement(CREATE_PLAN_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertStm.setString(1, plan.getName());
            insertStm.setString(2, plan.getDescription());
            insertStm.setInt(3, plan.getAdmin().getId());
            int result = insertStm.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }

            try (ResultSet generatedKeys = insertStm.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    plan.setId(generatedKeys.getInt(1));
                    return plan;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //read
    public Plan read(int id) {
        Plan plan = new Plan();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_PLAN_QUERY)
        ) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    plan.setId(resultSet.getInt("id"));
                    plan.setName(resultSet.getString("name"));
                    plan.setDescription(resultSet.getString("description"));
                    plan.setCreated(resultSet.getDate("created"));
                    int adminId = resultSet.getInt("admin_id");
                    Admin admin = adminDao.read(adminId);
                    plan.setAdmin(admin);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plan;

    }

    //update
    public void update(Plan plan) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PLAN_QUERY)) {
            statement.setInt(4, plan.getId());
            statement.setString(1, plan.getName());
            statement.setString(2, plan.getDescription());
            statement.setInt(3, plan.getAdmin().getId());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //delete
    public void delete(Integer planId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PLAN_QUERY)) {
            statement.setInt(1, planId);
            int deleted = statement.executeUpdate();

            if (deleted == 0) {
                System.out.println(deleted);

                throw new NotFoundException("Product not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<Plan> findAll() {
        List<Plan> planList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_PLANS_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Plan planToAdd = new Plan();
                planToAdd.setId(resultSet.getInt("id"));
                planToAdd.setName(resultSet.getString("name"));
                planToAdd.setDescription(resultSet.getString("description"));
                planToAdd.setCreated(resultSet.getDate("created"));
                int adminId = resultSet.getInt(("admin_id"));
                Admin admin = adminDao.read(adminId);
                planToAdd.setAdmin(admin);
                planList.add(planToAdd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planList;

    }

    //liczenie planów
    public int countPlans(int userId) {
        int counter = -1;
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(COUNT_PLAN_QUERY)
        ) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    counter = resultSet.getInt("count(admin_id)");

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return counter;

    }

    // ostatnio dodany plan
    public Plan getLastPlanByUserId(int adminId) {
        Plan plan = new Plan();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_LAST_PLAN_BY_USER_ID)
        ) {
            statement.setInt(1, adminId);
            Admin admin = adminDao.read(adminId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    plan.setId(resultSet.getInt("id"));
                    plan.setName(resultSet.getString("name"));
                    plan.setDescription(resultSet.getString("description"));
                    plan.setCreated(resultSet.getDate("created"));
                    plan.setAdmin(admin);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plan;

    }

    public List<Plan> readAllPlansByUserId(int id) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(GET_PLANS_BU_USER_ID)) {

            List<Plan> list = new ArrayList<>();

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Plan plan = new Plan();
                plan.setId(resultSet.getInt("id"));
                plan.setName(resultSet.getString("name"));
                plan.setDescription(resultSet.getString("description"));
                plan.setCreated(resultSet.getDate("created"));
                int adminId = resultSet.getInt("admin_id");
                Admin admin = adminDao.read(adminId);
                plan.setAdmin(admin);

                list.add(plan);
            }

            if (list.isEmpty()) {
                return null;
            }

            return list;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public PlanDetails getPlanDetails(Admin admin, int planId) {
        Plan plan = planDao.read(planId);
        PlanDetails planDetails = new PlanDetails();
        List<PlanDetailClass> list = new ArrayList<>();

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_PLAN_DETAILS_QUERY);) {

            statement.setInt(1, admin.getId());
            statement.setInt(2, planId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {

                    PlanDetailClass planDetailClass = new PlanDetailClass();
                    planDetailClass.setPlanRecipeId(resultSet.getInt("recipe_plan_id"));
                    planDetailClass.setDayName(resultSet.getString("day_name"));
                    planDetailClass.setMealName(resultSet.getString("meal_name"));
                    planDetailClass.setRecipeName(resultSet.getString("recipe_name"));
                    planDetailClass.setRecipeId(resultSet.getInt("recipe_id"));
                    planDetailClass.setRecipeDescription(resultSet.getString("recipe_description"));

                    list.add(planDetailClass);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        planDetails.setId(planId);
        planDetails.setName(plan.getName());
        planDetails.setDescription(plan.getDescription());
        planDetails.setCreated(plan.getCreated());
        planDetails.setAdmin(plan.getAdmin());
        planDetails.setListPlanDetails(list);

        return planDetails;
    }
}