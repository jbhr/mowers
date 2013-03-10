package com.karakoum.mowers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.karakoum.mowers.common.util.StringInputInterpreter;
import com.karakoum.mowers.common.util.UnitOrders;
import com.karakoum.mowers.domain.roundorders.RoundOrders;
import com.karakoum.mowers.domain.surface.impl.Surface;
import com.karakoum.mowers.domain.unit.impl.Unit;
import com.karakoum.mowers.exception.MowersException;
import com.karakoum.mowers.service.SurfaceService;
import com.karakoum.mowers.service.UnitService;

/**
 * Test des imports de fichiers d'ordres
 * Test également le résultat d'une série d'ordres 
 * 
 * @ContextConfiguration(locations={"classpath:/config/spring-servlet-test.xml", "classpath:/config/spring-test.xml"})
 * replaced by 
 * @ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring-servlet.xml", "classpath:/config/spring-test.xml"})
 * 
 * @author Jean-Baptiste
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring-servlet.xml", "classpath:/config/spring-test.xml"})
public class InputProcessTest {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	private StringInputInterpreter inputstream_interpreter;
	private RoundOrders default_round; 
	
	@Autowired
	private SurfaceService surfaceService;
	
	@Autowired
	private UnitService unitService;
	
	
	@Before
	public void setUp() throws Exception {
		inputstream_interpreter = new StringInputInterpreter();
		
		// Construction des données par défaut
		StringBuffer input = new StringBuffer();
		input.append("5 5\n");
		input.append("1 2 N\n");
		input.append("GAGAGAGAA\n");
		input.append("3 3 E\n");
		input.append("AADAADADDA\n");
		
        default_round = inputstream_interpreter.processInputStream(new ByteArrayInputStream(input.toString().getBytes()));
        
	}
	
	@After
	public void tearDown() throws Exception {
		inputstream_interpreter = null;
		default_round = null;
	}
	

	@Test
	public void testDefaultInputProcess() {
		
        List<UnitOrders> units_and_orders = default_round.getUnitsAndOrders();
        
		assertEquals("Le nombre de tondeuse créée ne correspond pas au nombre attendu", 2, units_and_orders.size());
	}
	
	@Test
	public void testDefaultRoundOrdersExecution() {
		
        
        Surface surface = default_round.getSurface();
        List<UnitOrders> units_and_orders = default_round.getUnitsAndOrders();
        
		assertEquals("Le nombre de tondeuse créée ne correspond pas au nombre attendu", 2, units_and_orders.size());
		
		// Test mouvements première tondeuse
		Unit mower1 = units_and_orders.get(0).getUnit();
		
		ArrayList<Character> mower1_orders = units_and_orders.get(0).getOrders();
		surfaceService.putUnitOnSurface(surface, mower1, mower1.getPositionX(), mower1.getPositionY(), mower1.getOrientationIndice());
		
		for(Character order:mower1_orders) {
			unitService.move(mower1, order);
		}
		assertEquals("La position x de la première tondeuse n'est pas celle attendue", 1, mower1.getPositionX());
		assertEquals("La position y de la première tondeuse n'est pas celle attendue", 3, mower1.getPositionY());
		assertEquals("L'orentation de la première tondeuse n'est pas celle attendue", 'N', mower1.getOrientationAsChar());
		
		
		// Test mouvements deuxième tondeuse
		Unit mower2 = units_and_orders.get(1).getUnit();
		
		ArrayList<Character> mower2_orders = units_and_orders.get(1).getOrders();
		surfaceService.putUnitOnSurface(surface, mower2, mower2.getPositionX(), mower2.getPositionY(), mower2.getOrientationIndice());
		
		for(Character order:mower2_orders) {
			unitService.move(mower2, order);
		}
		assertEquals("La position x de la seconde tondeuse n'est pas celle attendue", 5, mower2.getPositionX());
		assertEquals("La position y de la seconde tondeuse n'est pas celle attendue", 1, mower2.getPositionY());
		assertEquals("L'orentation de la seconde tondeuse n'est pas celle attendue", 'E', mower2.getOrientationAsChar());
	}
	
	@Test
	public void testInputInvalidType() {
		StringBuffer input = new StringBuffer();
		input.append("5 N\n"); // La seconde coordonnée est dans un mauvais format
		input.append("1 2 N\n");
		input.append("GAGAGAGAA\n");
		input.append("3 3 E\n");
		input.append("AADAADADDA\n");
		
		@SuppressWarnings("unused")
		RoundOrders roundorders = null;
		try {
			roundorders = inputstream_interpreter.processInputStream(new ByteArrayInputStream(input.toString().getBytes()));
        	fail("Le traitement de l'entrée n'a pas signalé un échec alors que cette denière est invalide");
        } catch (MowersException me) {
            // OK 
        }
		
	}
	
	@Test
	public void testInputInvalidLines() {
		StringBuffer input = new StringBuffer();
		input.append("5 5\n");
		input.append("1 2 N\n");
		//input.append("GAGAGAGAA\n");  La ligne a été oubliée
		input.append("3 3 E\n");
		input.append("AADAADADDA\n");
		
		
		try {
			inputstream_interpreter.processInputStream(new ByteArrayInputStream(input.toString().getBytes()));
        	fail("Le traitement de l'entrée n'a pas signalé un échec alors que cette denière est invalide");
        } catch (MowersException me) {
            // OK 
        }
	}
	
	
	@Test
	public void testInputMoveBeyondSurfaceBorders() {
		StringBuffer input = new StringBuffer();
		input.append("5 5\n");
		input.append("0 0 N\n");
		input.append("AAAAAAAADAAAAAAA\n"); // 7 mouvements en avant, pivote à droite, 7 mouvements en avant
		
		RoundOrders roundorders = null;
		try {
			 roundorders = inputstream_interpreter.processInputStream(new ByteArrayInputStream(input.toString().getBytes()));
        } catch (MowersException me) {
            fail("Le traitement de l'entrée à échoué alors que cette denière est valide: "+me.getExceptionMsg());
        }
        //boolean process_ok_flag = myapp.processInput(new ByteArrayInputStream(input.toString().getBytes()));
		
        Surface surface = roundorders.getSurface();
        
        List<UnitOrders> units_and_orders = roundorders.getUnitsAndOrders();
		assertEquals("Le nombre de tondeuse créée ne correspond pas au nombre attendu", 1, units_and_orders.size());
		
		
		// Test mouvements première tondeuse
		Unit mower1 = units_and_orders.get(0).getUnit();
		unitService.saveOrUpdate(mower1);
		
		ArrayList<Character> mower1_orders = units_and_orders.get(0).getOrders();
		surfaceService.putUnitOnSurface(surface, mower1, mower1.getPositionX(), mower1.getPositionY(), mower1.getOrientationIndice());
		
		try {
			for(Character order:mower1_orders) {
				unitService.move(mower1, order);
			}
			fail("Le déplacement aurait du générer une exception car en dehors des bords de la surface.");
		} catch (MowersException me) {
            // Ok
        }
		//assertEquals("La position x de la première tondeuse n'est pas celle attendue", 1, mower1.getPositionX());
		//assertEquals("La position y de la première tondeuse n'est pas celle attendue", 3, mower1.getPositionY());
		//assertEquals("L'orentation de la première tondeuse n'est pas celle attendue", 'N', mower1.getOrientationAsChar());
		
	}
	
	
}
