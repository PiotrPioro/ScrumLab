package pl.coderslab.dao;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.model.Admin;
import pl.coderslab.utils.DbUtil;
import pl.coderslab.utils.JBCrypt;

import java.sql.*;
import java.util.Arrays;

public class AdminDao {

    private static final String CREATE_ADMIN_QUERY = "INSERT INTO admins (first_name, last_name, email, password, superadmin, enable) VALUES (?,?,?,?,?,?);";
    private static final String DELETE_ADMIN_QUERY = "DELETE FROM admins where id = ?;";
    private static final String FIND_ALL_ADMINS_QUERY = "SELECT * FROM admins;";
    private static final String READ_ADMIN_QUERY = "SELECT * from admins where id = ?;";
    private static final String READ_BY_EMAIL_ADMIN_QUERY = "SELECT * from admins where email = ?;";
    private static final String UPDATE_ADMIN_QUERY = "UPDATE admins SET first_name = ? , last_name = ?, email = ?, password = ?, superadmin = ?, enable = ? WHERE id = ?;";

    private static final String CHECK_LOGIN_QUERY = "select * from admins where email = ?;";


    public static String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public Admin create(Admin admin){
        try(Connection conn = DbUtil.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(CREATE_ADMIN_QUERY, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, admin.getFirstName());
            stmt.setString(2, admin.getLastName());
            stmt.setString(3, admin.getEmail());
            stmt.setString(4, hashPassword(admin.getPassword()));
            stmt.setInt(5, admin.getSuperAdmin());
            stmt.setInt(6, admin.getEnable());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                admin.setId(rs.getInt(1));
            }
            return admin;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public Admin read(int adminID){
        try(Connection conn = DbUtil.getConnection()){
            PreparedStatement stmt2 = conn.prepareStatement(READ_ADMIN_QUERY);
            stmt2.setInt(1, adminID);
            Admin adminRead = new Admin();
            ResultSet rs2 = stmt2.executeQuery();
            if(rs2.next()){
                adminRead.setId(adminID);
                adminRead.setFirstName((rs2.getString("first_name")));
                adminRead.setLastName((rs2.getString("last_name")));
                adminRead.setEmail(rs2.getString("email"));
                adminRead.setPassword(rs2.getString("password"));
                adminRead.setSuperAdmin(rs2.getInt("superadmin"));
                adminRead.setEnable(rs2.getInt("enable"));
            }
            return adminRead;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public Admin readByEmail(String adminEmail){
        try(Connection conn = DbUtil.getConnection()){
            PreparedStatement stmt2 = conn.prepareStatement(READ_BY_EMAIL_ADMIN_QUERY);
            stmt2.setString(1, adminEmail);
            Admin adminRead = new Admin();
            ResultSet rs2 = stmt2.executeQuery();
            if(rs2.next()){
                adminRead.setEmail(adminEmail);
                adminRead.setId(rs2.getInt("id"));
                adminRead.setFirstName((rs2.getString(2)));
                adminRead.setLastName((rs2.getString(3)));
                adminRead.setPassword(rs2.getString("password"));
                adminRead.setSuperAdmin(rs2.getInt("superadmin"));
                adminRead.setEnable(rs2.getInt("enable"));
            }
            return adminRead;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void update(Admin admin){
        try(Connection conn = DbUtil.getConnection()){
            PreparedStatement stmt3 = conn.prepareStatement(UPDATE_ADMIN_QUERY);
            stmt3.setInt(7, admin.getId());
            stmt3.setString(1, admin.getFirstName());
            stmt3.setString(2, admin.getLastName());
            stmt3.setString(3,admin.getEmail());
            stmt3.setString(4, hashPassword(admin.getPassword()));
            stmt3.setInt(5, admin.getSuperAdmin());
            stmt3.setInt(6, admin.getEnable());
            stmt3.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void delete(int adminId){
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement stmt4 = conn.prepareStatement(DELETE_ADMIN_QUERY);
            stmt4.setInt(1, adminId);
            stmt4.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Admin[] findAll(){
        Admin[] arrAdmin = new Admin[0];
        try(Connection conn = DbUtil.getConnection()){
            PreparedStatement stmt5 = conn.prepareStatement(FIND_ALL_ADMINS_QUERY);
            ResultSet rs3 = stmt5.executeQuery();
            while(rs3.next()) {
                Admin admin5 = new Admin();
                admin5.setId(rs3.getInt(1));
                admin5.setFirstName(rs3.getString(2));
                admin5.setLastName(rs3.getString(3));
                admin5.setEmail(rs3.getString(4));
                admin5.setPassword(rs3.getString(5));
                admin5.setSuperAdmin(rs3.getInt(6));
                admin5.setEnable(rs3.getInt(7));
                arrAdmin = Arrays.copyOf(arrAdmin, arrAdmin.length + 1);
                arrAdmin[arrAdmin.length - 1] = admin5;
            }
            return arrAdmin;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkLogin(String email, String password){
        try(Connection conn = DbUtil.getConnection()){
            PreparedStatement statement = conn.prepareStatement(CHECK_LOGIN_QUERY);

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                String baseEmail = resultSet.getString("email");
                String basePassword = resultSet.getString("password");

                if (baseEmail.equals(email) && JBCrypt.BCrypt.checkpw(password, basePassword)){
                    return true;
                }else {
                    return false;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}


