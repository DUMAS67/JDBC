package fr.diginamic.props;

import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;

public class TestConfiguration {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ResourceBundle monFichierConf = ResourceBundle.getBundle("monfichier");
		String driverName = monFichierConf.getString("user");
		System.out.println(driverName);
		Set <String> a=monFichierConf.keySet();
		
		
		Iterator<String> iterator = a.iterator();
		while (iterator.hasNext()) {
		String clef = iterator.next();
		
		System.out.println(monFichierConf.getString(clef));
		}
		
	}
}
