package todoApp;

import java.sql.Connection;
import util.ConnectionFactory;


/**
 * Douglas Lima
 */
public class Main {

    public static void main(String[] args) {
        
        Connection c = ConnectionFactory.getConnection();
        
        ConnectionFactory.closeConnection(c);
    }
    
}
