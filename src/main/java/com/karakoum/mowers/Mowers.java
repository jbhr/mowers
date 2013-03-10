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
 * La m�thode principale: processInput
 * 
 * Pour lancer le traitement sur des donn�es dans un fichier texte le fichier doit �tre dans le package)
 * Des tests unitaires sont �galement �crits, le flux d'entr�e provenant cette fois d'une chaine de caract�res.
 * 
 * @author Jean-Baptiste
 *
 */
public class Mowers {
	
	static Logger log = Logger.getLogger(Mowers.class.getName());
	Surface lastProcessSurface = null;
	ArrayList<Unit> lastProcessMowers = null;
	
	/**
	 * Lancement du traitement � partir d'un fichier d'entr�e, en mode console:
	 * java -cp target\mowers-1.0-SNAPSHOT.jar com.karakoum.mowers.Mowers input.txt
	 * 
	 * @param args un seul param�tre, qui doit �tre le nom d'un fichier plac� dans le dossier resources
	 */
	public static void main (String[] args){
		
		String input_filename = "input.txt";
		
		if (args.length == 0) {
			log.info("Aucun nom de fichier sp�cifi� en param�tre, utilisation du fichier par d�faut input.txt)"); 
		} else {
			input_filename = args[0];
		}
		
		if (input_filename.length() > 0) {
			
			// Ouverture du fichier d'entr�e
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
				log.error("Le fichier d'entr�e "+ input_filename + " est introuvable");
			}
		 	
	 		
		} else {
			log.warn("Merci de sp�cifier le nom d'un fichier en entr�e");
		}
		
	}
	
	

	public Surface getLastProcessSurface() {
		return lastProcessSurface;
	}

	public List<Unit> getLastProcessMowers() {
		return lastProcessMowers;
	}
	/**
	 * Permet de r�initialiser les donn�es avant un nouveau traitement
	 */
	public void processReset() {
		lastProcessSurface = null;
		lastProcessMowers = new ArrayList<Unit>();
	}
	
	
	
	
	
}


