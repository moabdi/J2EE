package annuaire.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

/**
 * Class DAO class d'interaction avec la base de données
 * inclut toutes les requetes
 * @author 
 *
 */
public class DAO {

	/**
	 * Récuperer tous les contacts existe dans la base
	 * 
	 * @return ResultSetTableModel : résultat
	 */
	public ResultSetTableModel getAllContacts() {
		ResultSetTableModel resultat = null;
		Statement stmt = null;
		String query = "SELECT * FROM contact";
		try {
			stmt = (Statement) ConnectionBase.getInstance().createStatement();
			ResultSet resultSet = stmt.executeQuery(query);
			resultat = new ResultSetTableModel(resultSet);
		} catch (SQLException e) {
			Error.setErrorMessage("Probleme d'accès à la base de données");
		}
		return resultat;
	}

	/**
	 * Récuperer un contact par son identifiant
	 * 
	 * @param id
	 *            du contact
	 * @return L'objet contact
	 */
	public Contact getContactById(int pId) {
		Contact contact = new Contact();
		Statement stmt = null;
		String query = " SELECT * FROM contact WHERE id =" + pId;
		try {
			stmt = (Statement) ConnectionBase.getInstance().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				contact.setId(pId);
				contact.setNom(rs.getString("nom"));
				contact.setPrenom(rs.getString("prenom"));
				contact.setTelephone(rs.getString("telephone"));
				contact.setEmail(rs.getString("email"));
				contact.setVille(rs.getString("ville"));
				contact.setCodePostal(rs.getString("codepostal"));
			}
		} catch (SQLException e) {
			Error.setErrorMessage("Probleme d'accès à la base de données");
		}
		return contact;
	}

	/**
	 * Recherche avancé : permet de faire une recherche par critères setter dans
	 * l'objet contact
	 * 
	 * @param Contact
	 *            contact
	 * @return ResultSetTableModel
	 */
	public ResultSetTableModel searchAvance(Contact pContact) {
		ResultSetTableModel resultat = null;
		Statement stmt = null;
		StringBuilder query = new StringBuilder(
				"SELECT * FROM contact WHERE 1=1 ");
		if (pContact != null) {
			if (pContact.getNom() != null
					&& !pContact.getNom().trim().equals("")) {
				query.append(" AND nom = '" + pContact.getNom() + "' ");
			}
			if (pContact.getPrenom() != null
					&& !pContact.getPrenom().trim().equals("")) {
				query.append(" AND prenom = '" + pContact.getPrenom() + "' ");
			}
			if (pContact.getTelephone() != null
					&& !pContact.getTelephone().trim().equals("")) {
				query.append(" AND telephone = '" + pContact.getTelephone()
						+ "' ");
			}
			if (pContact.getEmail() != null
					&& !pContact.getEmail().trim().equals("")) {
				query.append(" AND email = '" + pContact.getEmail() + "' ");
			}
			if (pContact.getVille() != null
					&& !pContact.getVille().trim().equals("")) {
				query.append(" AND ville = '" + pContact.getVille() + "' ");
			}
			if (pContact.getCodePostal() != null
					&& !pContact.getCodePostal().trim().equals("")) {
				query.append(" AND codepostal = '" + pContact.getCodePostal()
						+ "' ");
			}
		}
		try {
			stmt = (Statement) ConnectionBase.getInstance().createStatement();
			ResultSet resultSet = stmt.executeQuery(query.toString());
			resultat = new ResultSetTableModel(resultSet);
		} catch (SQLException e) {
			Error.setErrorMessage("Probleme d'accès à la base de données");
		}
		return resultat;

	}

	/**
	 * Recherche rapide : permet de faire une recherche rapide dans la base par
	 * nom, prenom, ville et code postal
	 * 
	 * @param nomPrenom
	 *            : nom ou prenom
	 * @param villeCodePostal
	 *            : ville ou code postal
	 * @return ResultSetTableModel
	 */
	public ResultSetTableModel rechercheRapide(String nomPrenom,
			String villeCodePostal) {
		ResultSetTableModel resultat = null;
		Statement stmt = null;
		String query = "SELECT * FROM contact WHERE (nom like '%" + nomPrenom
				+ "%' " + " OR prenom like '%" + nomPrenom + "%') AND"
				+ " ( ville like '%" + villeCodePostal
				+ "%' OR codepostal like '%" + villeCodePostal + "%')";
		try {
			stmt = (Statement) ConnectionBase.getInstance().createStatement();
			ResultSet resultSet = stmt.executeQuery(query);
			resultat = new ResultSetTableModel(resultSet);
		} catch (SQLException e) {
			Error.setErrorMessage("Probleme d'accès à la base de données");
		}
		return resultat;
	}

	/**
	 * Supprimer un contact
	 * 
	 * @param id
	 *            : id contact
	 */
	public void supprimerContact(int id) {
		Statement stmt = null;
		String query = "DELETE FROM contact WHERE id =" + id;
		try {
			stmt = (Statement) ConnectionBase.getInstance().createStatement();
			stmt.execute(query);
		} catch (SQLException e) {
			Error.setErrorMessage("Probleme d'accès à la base de données");
		}
	}

	/**
	 * Ajouter ou modifier un objet contact
	 * 
	 * @param contact
	 */
	public void saveOrUpdateContact(Contact pContact) {
		if (pContact != null) {
			String query = "";

			if (pContact.getId() > 0) {
				query = " UPDATE contact SET nom = '" + pContact.getNom()
						+ "', prenom = '" + pContact.getPrenom()
						+ "', email = '" + pContact.getEmail()
						+ "', telephone = '" + pContact.getTelephone() 
						+ "', ville = '" + pContact.getVille()
						+ "', codepostal = '" + pContact.getCodePostal()
						+ "' WHERE id = " + pContact.getId();
			} else {
				query = "INSERT INTO contact (`nom`, `prenom`, `email`, `telephone`, `ville`, `codepostal`)"
						+ " values ('"
						+ pContact.getNom()
						+ "','"
						+ pContact.getPrenom()
						+ "','"
						+ pContact.getTelephone()
						+ "','"
						+ pContact.getEmail()
						+ "','"
						+ pContact.getVille()
						+ "','"
						+ pContact.getCodePostal() 
						+ "')";
			}

			Statement stmt = null;
			try {
				stmt = (Statement) ConnectionBase.getInstance().createStatement();
				stmt.execute(query);
			} catch (SQLException e) {
				Error.setErrorMessage("Probleme d'accès à la base de données");
			}
		}
	}
	
	/**
	 * login 
	 * 
	 * @param pLogin
	 * @param pPassword
	 * @return User
	 */
	public boolean login(String pLogin, String pPassword)
	{
		boolean trouve = false;
		Statement stmt = null;
		String query = " SELECT * FROM user WHERE login ='" + pLogin+ "'"
				+ " AND password = '"+pPassword+"'";
		try {
			stmt = (Statement) ConnectionBase.getInstance().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				trouve = true;
			}
		} catch (Exception e) {
			Error.setErrorMessage("Probleme d'accès à la base de données");
		}
		return trouve;
	}
	/**
	 * recuperer l'objet Utilisateur 
	 * 
	 * @param pLogin
	 * @param pPassword
	 * @return User
	 */
	public User getUser(String pLogin, String pPassword)
	{
		User user = new User();
		Statement stmt = null;
		String query = " SELECT * FROM user WHERE login ='" + pLogin+ "'"
				+ " AND password = '"+pPassword+"'";
		try {
			stmt = (Statement) ConnectionBase.getInstance().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				user.setId(rs.getInt("id"));
				user.setNom(rs.getString("nom"));
				user.setPrenom(rs.getString("prenom"));
				user.setLogin(rs.getString("login"));
				user.setPassword(rs.getString("password"));
			}
		} catch (SQLException e) {
			Error.setErrorMessage("Probleme d'accès à la base de données");
		}
		return user;
	}

	
	public List<Contact> getListContacts() { 
		List<Contact> listContact = new ArrayList<>();
		Statement stmt = null;
		String query = " SELECT * FROM contact " ;
		try {
			stmt = (Statement) ConnectionBase.getInstance().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Contact contact = new Contact();
				contact.setId(rs.getInt("id"));
				contact.setNom(rs.getString("nom"));
				contact.setPrenom(rs.getString("prenom"));
				contact.setEmail(rs.getString("email"));
				contact.setTelephone(rs.getString("telephone"));
				contact.setVille(rs.getString("ville"));
				contact.setCodePostal(rs.getString("codepostal"));
				listContact.add(contact);
			}
		} catch (SQLException e) {
			Error.setErrorMessage("Probleme d'accès à la base de données");
		}
		return listContact;
	}
}
