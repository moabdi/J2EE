package annuaire.util;

import java.io.FileWriter;
import java.io.IOException;

public class Util {
	
	static DAO bdd = new DAO();

	public static void generateCsvFile(String sFileName) {
		try {
			if(sFileName == null || sFileName.trim().equals(""))
			{
				sFileName = "c:\\annuaire.csv";
			}
			FileWriter writer = new FileWriter(sFileName);

			writer.append("#Id;");
			writer.append("Nom;");
			writer.append("Prénom;");
			writer.append("Email;");
			writer.append("Téléphone;");
			writer.append("Ville;");
			writer.append("Code postal;");
			writer.append('\n');

			for (Contact contact : bdd.getListContacts())
			{
				writer.append(String.valueOf(contact.getId())+";");
				writer.append(contact.getNom()+";");
				writer.append(contact.getPrenom()+";");
				writer.append(contact.getEmail()+";");
				writer.append(contact.getTelephone()+";");
				writer.append(contact.getVille()+";");
				writer.append(contact.getCodePostal()+";");
				writer.append('\n');
			}
			
			// generate whatever data you want
			writer.flush();
			writer.close();
		} catch (IOException e) {
			Error.setErrorMessage("Un problème est servenu lors d'export des données"); 
		}
	}
}