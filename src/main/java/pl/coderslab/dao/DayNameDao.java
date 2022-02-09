package pl.coderslab.dao;


import pl.coderslab.model.DayName;
import pl.coderslab.utils.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DayNameDao {


    private static final String FIND_ALL_DAY_NAME_QUERY = "SELECT * FROM day_name;";
    private static final String create_query = "SELECT * from day_name where id = ?;";

    public DayName read(int dayNameID){
        try(Connection conn = DbUtil.getConnection()){
            PreparedStatement statement = conn.prepareStatement(create_query);

            statement.setInt(1, dayNameID);
            DayName dayNameRead = new DayName();
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){

                dayNameRead.setId(dayNameID);
                dayNameRead.setName((resultSet.getString("name")));
                dayNameRead.setDisplayOrder((resultSet.getString("display_order")));

            }
            return dayNameRead;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<DayName> findAll() {
        List<DayName> dayNameList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_DAY_NAME_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                DayName dayNameToAdd = new DayName();
                dayNameToAdd.setId(resultSet.getInt("id"));
                dayNameToAdd.setName(resultSet.getString("name"));
                dayNameToAdd.setDisplayOrder(resultSet.getString("display_order"));
                dayNameList.add(dayNameToAdd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dayNameList;

    }
}
