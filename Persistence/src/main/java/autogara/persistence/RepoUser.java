package autogara.persistence;

import autogara.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RepoUser implements RepositoryUser{
    private String numeTabela;

    public RepoUser(String numeTabela){
        this.numeTabela = numeTabela;
    }

    @Override
    public User findByUserAndPass(String username, String password) {
        try{
            Connection conn = JDBCUtils.connect();
            String cautare = "select * from " + numeTabela + " where username = ? and password = ?";
            PreparedStatement prep = conn.prepareStatement(cautare);
            prep.setString(1, username);
            prep.setString(2, password);
            ResultSet rs = prep.executeQuery();
            User user = new User(rs.getString(1), rs.getString(2), rs.getString(3));
            rs.close();
            prep.close();
            conn.close();
            return user;

        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void save(User entity) {

    }

    @Override
    public void delete(String s) {

    }

    @Override
    public void update(String s, User entity) {

    }

    @Override
    public User findOne(String s) {
        return null;
    }

    @Override
    public ArrayList<User> findAll() {
        return null;
    }
}
