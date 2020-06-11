package autogara.persistence;

import autogara.domain.Excursie;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

@Component
public class RepoExcursie implements RepositoryExcursie{
    private String numeTabela;

    public RepoExcursie(){
        this.numeTabela = "Excursii";
    }

    @Override
    public int size() {
        try{
            Connection connection = JDBCUtils.connect();
            String size = "select count(*) as [SIZE] from " + numeTabela;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(size);
            if(resultSet.next()){
                connection.close();
                return resultSet.getInt("SIZE");
            }
            connection.close();
        }catch(SQLException ex){
            System.err.println(ex.getSQLState());
            System.err.println(ex.getErrorCode());
            System.err.println(ex.getMessage());
        }
        return 0;
    }

    @Override
    public void save(Excursie e){
        try{
            Connection connection = JDBCUtils.connect();
            LocalTime date = e.getOraPlecarii();
            String ora = date.getHour() + ":" + date.getMinute();
            String insert = "insert into " + numeTabela + " values (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, e.getId());
            preparedStatement.setString(2, e.getNumeCompTrans());
            preparedStatement.setString(3, e.getObiectiv());
            preparedStatement.setString(4, ora);
            preparedStatement.setDouble(5, e.getPret());
            preparedStatement.setInt(6, e.getNrLocuri());
            int result = preparedStatement.executeUpdate();
            connection.close();
        }catch(SQLException ex){
            System.err.println(ex.getSQLState());
            System.err.println(ex.getErrorCode());
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void delete(String ID) {
        try {
            Connection connection = JDBCUtils.connect();
            String deleteStr = "delete from " + numeTabela + " where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteStr);
            preparedStatement.setString(1, ID);
            int result = preparedStatement.executeUpdate();
            connection.close();
        }catch(SQLException ex){
            System.err.println(ex.getSQLState());
            System.err.println(ex.getErrorCode());
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void update(String ID, Excursie e){
        try{
            Connection connection = JDBCUtils.connect();
            String update = "update " + numeTabela + " set nrLocuri=" + e.getNrLocuri() + " where id='" + e.getId() + "'";
            Statement statement = connection.createStatement();
            statement.executeUpdate(update);
            connection.close();
        }catch(SQLException ex){
            System.err.println(ex.getSQLState());
            System.err.println(ex.getErrorCode());
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public Excursie findOne(String s) {
        try{
            Connection connection = JDBCUtils.connect();
            String find = "select * from " + numeTabela + " where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(find);
            preparedStatement.setString(1, s);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String id = resultSet.getString(1);
                String nume = resultSet.getString(2);
                String obiectiv = resultSet.getString(3);
                LocalTime ora = LocalTime.of(Integer.parseInt(resultSet.getString(4).split(":")[0]),
                        Integer.parseInt(resultSet.getString(4).split(":")[1]));
                Double pret = resultSet.getDouble(5);
                int nrLocuri = resultSet.getInt(6);
                connection.close();
                return new Excursie(id, nume, obiectiv, ora, pret, nrLocuri);
            }
        }catch(SQLException ex){
            System.err.println(ex.getSQLState());
            System.err.println(ex.getErrorCode());
            System.err.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Excursie> findAll(){
        try{
            Connection connection = JDBCUtils.connect();
            String select = "select * from " + numeTabela;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            HashMap<String, Excursie> map = new HashMap<>();
            while (resultSet.next()){
                LocalTime ora = LocalTime.of(Integer.parseInt(resultSet.getString(4).split(":")[0]),
                        Integer.parseInt(resultSet.getString(4).split(":")[1]));
                Excursie excursie = new Excursie(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        ora,
                        Double.parseDouble(resultSet.getString(5)),
                        Integer.parseInt(resultSet.getString(6)));
                map.put(excursie.getId(), excursie);
            }
            connection.close();
            return new ArrayList<>(map.values());
        }catch(SQLException ex){
            System.err.println(ex.getSQLState());
            System.err.println(ex.getErrorCode());
            System.err.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Excursie> filtrare1(String Obiectiv, LocalTime ora1, LocalTime ora2) {
        try{
            Connection connection = JDBCUtils.connect();
            String ora1str = "" + ora1.getHour() + ":" + ora1.getMinute();
            String ora2str = "" + ora2.getHour() + ":" + ora2.getMinute();
            String select = "select * from " + numeTabela + " where obiectiv=? and oraPlecarii between ? and ?";
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setString(1, Obiectiv);
            preparedStatement.setString(2, ora1str);
            preparedStatement.setString(3, ora2str);
            ResultSet resultSet = preparedStatement.executeQuery();
            HashMap<String, Excursie> map = new HashMap<>();
            while (resultSet.next()){
                LocalTime ora = LocalTime.of(Integer.parseInt(resultSet.getString(4).split(":")[0]),
                        Integer.parseInt(resultSet.getString(4).split(":")[1]));
                Excursie excursie = new Excursie(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        ora,
                        Double.parseDouble(resultSet.getString(5)),
                        Integer.parseInt(resultSet.getString(6)));
                map.put(excursie.getId(), excursie);
            }
            resultSet.close();
            connection.close();
            return new ArrayList<>(map.values());
        }catch(SQLException ex){
            System.err.println(ex.getSQLState());
            System.err.println(ex.getErrorCode());
            System.err.println(ex.getMessage());
        }
        return null;
    }
}
