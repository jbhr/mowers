package com.karakoum.mowers.common.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.karakoum.mowers.Mowers;
import com.karakoum.mowers.domain.roundorders.RoundOrders;
import com.karakoum.mowers.domain.surface.impl.Jardin;
import com.karakoum.mowers.domain.surface.impl.Surface;
import com.karakoum.mowers.domain.unit.impl.Mower;
import com.karakoum.mowers.domain.unit.impl.Unit;
import com.karakoum.mowers.exception.MowersException;

public class StringInputInterpreter {
	
	static Logger log = Logger.getLogger(Mowers.class.getName());
	
	
	/**
	 * Méthode principale, pour traiter un flux d'entrée (que ce soit un fichier - type 
	 * -FileInputStream- ou une chaine de caractères -ByteArrayInputStream-, par exemple),
	 * correspondant à l'ensemble des éléments pour traiter un round de déplacement des unités
	 * sur une surface.
	 * 
	 * @param input le flux d'entrée, selon les spécifications de l'exercice
	 * @return une instance de l'objet RoundOrders contenant la surface, les unités et leurs ordres de déplacement
	 */
	public RoundOrders processInputStream(InputStream input) throws MowersException {
		
		RoundOrders roundorders = new RoundOrders();
		
		Scanner scan = new Scanner(input);
		
		log.info("Début du traitement");
		
		try {
			int surface_max_x = scan.nextInt();
			int surface_max_y = scan.nextInt();
			
			Surface surface = new Jardin("Mon Jardin", surface_max_x, surface_max_y);
			roundorders.setSurface(surface);
			
			log.info("Jardin créé: "+surface.toString());
			
			int mower_init_x;
			int mower_init_y;
			char mower_init_orientation;
			String move_orders;
			char nextmove;
			
			int mower_indice = 1;
			while (scan.hasNext()) {
				mower_init_x = scan.nextInt();
				mower_init_y = scan.nextInt();
				String line = scan.nextLine();
				mower_init_orientation = line.trim().charAt(0);
				
				Unit myMower = new Mower("Tondeuse n"+mower_indice, mower_init_x, mower_init_y, mower_init_orientation);
				
				log.info("Tondeuse créée:"+myMower.toString());
				
				move_orders = scan.nextLine();
				ArrayList<Character> orders = new ArrayList<Character>();
				for(int i=0; i < move_orders.length(); i++) {
					nextmove = move_orders.charAt(i);
					orders.add(nextmove);
				}
				
				roundorders.addUnitsAndOrders(myMower, orders);
			}
			
			
		} catch (MowersException me) {
			throw me;
		} catch (InputMismatchException input_e) {
			throw new MowersException("Donnée d'entrée invalide "+input_e.getMessage());
		} catch (StringIndexOutOfBoundsException index_e) {
			throw new MowersException("Donnée d'entrée invalide "+index_e.getMessage());
		} finally {
			scan.close();
		}
		
		log.info("Fin du traitement");
		
		return roundorders;
	}
	
}
