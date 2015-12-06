package annuaire.util;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

//class singleton : private constructor 
// cr�er qu'un seul object  dans l'app
// methode static qui retourne l'objet cr�e
public class ConnectionBase{
  //URL de connexion
  private String url = "jdbc:mysql://localhost:3306/test";
  
  //Nom du user
  private String user = "root";
  
  //Mot de passe de l'utilisateur
  private String passwd = "";
  
  //Objet Connection
  private static Connection connect;
   
  //Constructeur priv�
  private ConnectionBase(){
    try {
      connect = (Connection) DriverManager.getConnection(url, user, passwd);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
   
  //M�thode qui va nous retourner notre instance et la cr�er si elle n'existe pas
   public static Connection getInstance(){
    if(connect == null){
      new ConnectionBase();
    }
    return connect;   
  }   
}