package com.karakoum.mowers;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.karakoum.mowers.common.util.StringInputInterpreter;
import com.karakoum.mowers.common.util.UnitOrders;
import com.karakoum.mowers.domain.roundorders.RoundOrders;
import com.karakoum.mowers.domain.surface.impl.Surface;
import com.karakoum.mowers.domain.unit.impl.Unit;
import com.karakoum.mowers.service.SurfaceService;
import com.karakoum.mowers.service.UnitService;

/**
 * La méthode principale: processInput
 * 
 * Pour lancer le traitement sur des données dans un fichier texte le fichier doit être dans le package)
 * Des tests unitaires sont également écrits, le flux d'entrée provenant cette fois d'une chaine de caractères.
 * 
 * @author Jean-Baptiste
 *
 */
public class Mowers {
	
	static Logger log = Logger.getLogger(Mowers.class.getName());
	Surface lastProcessSurface = null;
	ArrayList<Unit> lastProcessMowers = null;
	
	/**
	 * Lancement du traitement à partir d'un fichier d'entrée, en mode console:
	 * java -cp target\mowers-1.0-SNAPSHOT.jar com.karakoum.mowers.Mowers input.txt
	 * 
	 * @param args un seul paramètre, qui doit être le nom d'un fichier placé dans le dossier resources
	 */
	public static void main (String[] args){
		
		String input_filename = "input.txt";
		
		if (args.length == 0) {
			log.info("Aucun nom de fichier spécifié en paramètre, utilisation du fichier par défaut input.txt)"); 
		} else {
			input_filename = args[0];
		}
		
		if (input_filename.length() > 0) {
			
			// Ouverture du fichier d'entrée
			InputStream input = Mowers.class.getResourceAsStream("/"+input_filename);
			
			if (input != null) {
				
				log.info("Start processing file "+input_filename);
				
				ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
				
				StringInputInterpreter inputstream_interpreter = new StringInputInterpreter();
		        
				SurfaceService surfaceService = context.getBean(SurfaceService.class);
				UnitService unitService = context.getBean(UnitService.class);
				
				RoundOrders roundorders = inputstream_interpreter.processInputStream(input);
				
				Surface surface = roundorders.getSurface();
		        log.info("Surface "+surface.getName()+" saved. Id="+surface.getId());
		        
		        List<UnitOrders> units_and_orders = roundorders.getUnitsAndOrders();
				
				for(UnitOrders unitorders:units_and_orders) {
					Unit unit = unitorders.getUnit();
					
					ArrayList<Character> orders = unitorders.getOrders();
					surfaceService.putUnitOnSurface(surface, unit, unit.getPositionX(), unit.getPositionY(), unit.getOrientationIndice());
					
					log.info("Unit "+unit.getName()+" saved. Id="+unit.getId());
					
					for(Character order:orders) {
						unitService.move(unit, order);
					}
				}
				
				log.info(surface.toString());
				
				for(UnitOrders unitorders:units_and_orders) {
					Unit unit = unitorders.getUnit();
					log.info(unit.toString());
				}
				
			} else {
				log.error("Le fichier d'entrée "+ input_filename + " est introuvable");
			}
		 	
	 		
		} else {
			log.warn("Merci de spécifier le nom d'un fichier en entrée");
		}
		
	}
	
	

	public Surface getLastProcessSurface() {
		return lastProcessSurface;
	}

	public List<Unit> getLastProcessMowers() {
		return lastProcessMowers;
	}
	/**
	 * Permet de réinitialiser les données avant un nouveau traitement
	 */
	public void processReset() {
		lastProcessSurface = null;
		lastProcessMowers = new ArrayList<Unit>();
	}
	
	
	
	
	
}


