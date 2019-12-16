/**
 * 
 */
package fr.diginamic.jdbc.recensement.test;

/* Classe qui va scruter le contenu d'un fichier CSV de recensement 
 * et va le mettre dans une base de données à distance
 * la Base de donnée à 3 tables REGION - DEPARTEMENT - VILLE */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.diginamic.jdbc.recensement.Dao.DepartementDAOJdbc;
import fr.diginamic.jdbc.recensement.Dao.RegionDAOJdbc;
import fr.diginamic.jdbc.recensement.Dao.VilleDAOJdbc;
import fr.diginamic.jdbc.recensement.entites.Departement;
import fr.diginamic.jdbc.recensement.entites.Region;
import fr.diginamic.jdbc.recensement.entites.Ville;

/**
 * @author DUMAS
 *
 */
public class IntegrationRecensement {
	/* Definition d'un Loggeur pour contrôler les erreurs eventuelles*/
	private static final Logger LOG = LoggerFactory.getLogger("file");

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		List<String> lignes = new ArrayList<String>(0);
		try {
			File file = new File("C:/tempSTS2/demo-jdbc/recensement population 2016.csv");
			lignes = FileUtils.readLines(file, "UTF-8");

			// On supprime la ligne d'entête avec les noms des colonnes
			lignes.remove(0);
			RegionDAOJdbc r = new RegionDAOJdbc();
			DepartementDAOJdbc d = new DepartementDAOJdbc();
			VilleDAOJdbc v = new VilleDAOJdbc();
			/* Positionne un marqueur de temps */
			long a = System.currentTimeMillis();
			for (int i = 0; i < lignes.size(); i++) {
				int j = i + 1;
				String[] morceaux = lignes.get(i).split(";");
				Integer codeRegion = Integer.parseInt(morceaux[0]);
				String nomRegion = morceaux[1];
				Integer codeDepartement;
				/*
				 * Met lesdepartements 2A et 2B à 2 pour pourvoir faire le
				 * traitement
				 */
				if (morceaux[2].equals("2A") || morceaux[2].equals("2B")) {
					codeDepartement = Integer.parseInt(morceaux[2].replace("2A", "2").replace("2B", "2"));
				} else {
					codeDepartement = Integer.parseInt(morceaux[2]);
				}
				System.out.println(codeRegion + "**" + j);
				String nomCommune = morceaux[3];
				Integer population = Integer.parseInt(morceaux[4].replace(" ", "").trim());

				/* insère dans la table REGION les données ID et NOM */
				r.insert(new Region(codeRegion, nomRegion));

				/*
				 * Insère dans la table DEPARTEMENT l'ID et la Foreign Key
				 * ID_REG
				 */
				d.insert(new Departement(codeDepartement, codeRegion));

				/*
				 * Insère dans la table VILLE l'ID , le NOM, la POPULATION, les
				 * Foreign key ID_DPT (DEPARTEMENT) et ID_REG(REGION)
				 */
				v.insert(new Ville(j, nomCommune, population, codeDepartement, codeRegion));
			}
			/* Clôture les connexions à la base */
			v.closeConnection();
			d.closeConnection();
			r.closeConnection();

			long b = System.currentTimeMillis();
			System.out.println(b - a);
		} catch (IOException e) {
			LOG.debug("Problème avec le fichier à lire");
		}
	}
}
