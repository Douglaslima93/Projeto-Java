package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Task;
import util.ConnectionFactory;

/**
 *
 * @author WIN10
 */
public class TaskController {
    
    public void save(Task task){
        
        String sql = "INSERT INTO tasks "
                + "(idProject,"
                + " name,"
                + "description,"
                + "completed,"
                + "notes,"
                + "deadline,"
                + "createdAt,"
                + "updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao salvar a tarefa "
            + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
            
        }
    }
    
    public void update(Task task) {
        
        String sql = "UPDATE tasks SET"
                + "idProject = ?, "
                + "name = ?, "
                + "description = ?, "
                + "notes = ?, "
                + "completed = ?, "
                + "deadline = ?, "
                + "createdAt = ?, "
                + "updatedAt = ?, "
                + "WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            //Estabelecendo a conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            
            //Preparando a query
            statement = connection.prepareStatement(sql);
            
            //Srtando os valores do statement
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setString(4, task.getNotes());
            statement.setBoolean(5, task.isIsCompleted());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.setInt(9, task.getId());
            
            //Executando a query
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao atualizar a tarefa " + ex.getMessage(), ex);
        }
    }
    
    public void removeById(int taskId) throws SQLException{
        
        String sql = "DELETE FROM tasks WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            //Criação da conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            
            //Preparando a query
            statement = connection.prepareStatement(sql);
            
            //Setando os valores
            statement.setInt(1, taskId);
            
            //Executando a query
            statement.execute();          
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao deletar a tarefa" + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public List<Task> getAll(int idProject) {
        
        String sql = "SELECT * FROM tasks WHERE idProjetc = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;
        
        //Lista de tarefas que será devolvida quando a chamada do método acontecer
        List<Task> tasks = new ArrayList<Task>();
        
        try {
            //Criação da conexão
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            
            //Setando o valor que corresponde ao filtro de busca
            statement.setInt(1, idProject);
            
            //Valor retornado pela execução da query
            resultset = statement.executeQuery();
            
            //Enquanto houverem valores a serem percorridos no meu resultSet
            while(resultset.next()){
                
                Task task = new Task();
                task.setId(resultset.getInt("id"));
                task.setIdProject(resultset.getInt("idProject"));
                task.setName(resultset.getString("name"));
                task.setDescription(resultset.getString("description"));
                task.setNotes(resultset.getString("notes"));
                task.setIsCompleted(resultset.getBoolean("completed"));
                task.setDeadline(resultset.getDate("deadline"));
                task.setCreatedAt(resultset.getDate("createdAt"));
                task.setUpdatedAt(resultset.getDate("updatedAt"));                
                tasks.add(task);
            }
            
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao inserir a tarefa" + ex.getMessage(), ex);
        }finally {
            ConnectionFactory.closeConnection(connection, statement, resultset);
        }
        
        //Lista de tarefas que foi criada e carregada do banco de dados.
        return tasks;
    }
}
