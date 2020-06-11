package autogara.persistence;

import autogara.domain.Bilet;

import java.sql.*;
import java.util.ArrayList;

public class RepoBilet implements RepositoryBilet{
    private String numeTabela;

    public RepoBilet(String numeTabela){
        this.numeTabela = numeTabela;
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
    public void save(Bilet e){
        try{
            Connection connection = JDBCUtils.connect();
            String insert = "insert into " + numeTabela + " values (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, e.getId());
            preparedStatement.setString(2, e.getIdExcursie());
            preparedStatement.setString(3, e.getClient());
            preparedStatement.setInt(4, e.getNrTel());
            preparedStatement.setInt(5, e.getNrLocuri());
            int result = preparedStatement.executeUpdate();
            connection.close();
        }catch(SQLException ex){
            System.err.println(ex.getSQLState());
            System.err.println(ex.getErrorCode());
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void delete(String s) {

    }

    @Override
    public void update(String ID, Bilet e){

    }

    @Override
    public Bilet findOne(String s) {
        /*try{
            Connection connection = JDBCUtils.connect();
            String find = "select * from " + numeTabela + " where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(find);
            preparedStatement.setString(1, s);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                String id = resultSet.getString(1);
                String idExcursie = resultSet.getString(2);
                String client = resultSet.getString(3);
                int nrTel = resultSet.getInt(4);
                int nrLocuri = resultSet.getInt(5);
                connection.close();
                return new Bilet(id, idExcursie, client, nrTel, nrLocuri);
            }
        }catch(SQLException ex){
            System.err.println(ex.getSQLState());
            System.err.println(ex.getErrorCode());
            System.err.println(ex.getMessage());
        }*/
        return null;
    }

    @Override
    public ArrayList<Bilet> findAll(){
        /*try{
            Connection connection = JDBCUtils.connect();
            String select = "select * from " + numeTabela;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            HashMap<String, Bilet> map = new HashMap<>();
            while (resultSet.next()){
                Bilet bilet = new Bilet(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getInt(5));
                map.put(bilet.getId(), bilet);
            }
            connection.close();
            return map.values();
        }catch(SQLException ex){
            System.err.println(ex.getSQLState());
            System.err.println(ex.getErrorCode());
            System.err.println(ex.getMessage());
        }*/
        return null;
    }


}
