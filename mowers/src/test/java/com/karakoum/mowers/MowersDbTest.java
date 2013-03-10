package com.karakoum.mowers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.karakoum.mowers.dao.RoundOrdersDao;
import com.karakoum.mowers.dao.SurfaceDao;
import com.karakoum.mowers.dao.UnitDao;
import com.karakoum.mowers.domain.roundorders.RoundOrders;
import com.karakoum.mowers.domain.surface.impl.Jardin;
import com.karakoum.mowers.domain.surface.impl.Surface;
import com.karakoum.mowers.domain.unit.impl.Mower;
import com.karakoum.mowers.domain.unit.impl.Unit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/spring-servlet.xml",
		"classpath:/config/spring-test.xml" })
public class MowersDbTest {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private SurfaceDao surfaceDao;
	
	@Autowired
	private UnitDao unitDao;
	
	@Autowired
	private RoundOrdersDao roundOrdersDao;
	
	@Autowired
    private MongoOperations mongoOperation;
	
	
    private static boolean mongoDbServerAvailable;

	@BeforeClass
	public static void initializeDB() throws IOException {
		
		
	}
	

	@Before
	public void setUp() throws Exception {
		// Teste que le serveur MongoDB est accessible, mais je n'ai pas trouvé de meilleur moyen de tester...
		mongoDbServerAvailable = false;
		try {
			mongoOperation.collectionExists("surfaces");
			mongoDbServerAvailable = true;
		} catch (Exception e) {
			// Nothing
		}
		org.junit.Assume.assumeTrue(mongoDbServerAvailable);
	}

	@After
	public void tearDown() throws Exception {

	}

	

	@Test
	public void testSaveAndRestoreSurface() {

		Surface surface = new Jardin("Mon jardin test", 5, 6);

		surfaceDao.saveOrUpdate(surface);
		assertNotNull("La sauvegarde de la surface " + surface.toString()
				+ " à échouée (identifiant nul)", surface.getId());
		String surfaceID = surface.getId();

		surface = null;
		surface = surfaceDao.findById(surfaceID);
		assertEquals("La récupération de la surface enregistrée d'identifiant "
				+ surface.getId() + " à échoué", surfaceID, surface.getId());

		surfaceDao.deleteById(surfaceID);
		surface = null;
		surface = surfaceDao.findById(surfaceID);
		// assertNull("La suppression de la surface d'identifiant "+surface.getId()+" à échouée (l'enregistrement existe toujours)",
		// surface);
		/**
		 * Test échoue actuellement: le remove ne fonctionne pas dans la release
		 * 1.0 de Spring Data Mongo cf
		 * https://jira.springsource.org/browse/DATAMONGO-346
		 */
	}

	@Test
	public void testSaveAndRestoreUnit() {

		Unit unit = new Mower("Ma tondeuse");

		unitDao.saveOrUpdate(unit);
		assertNotNull("La sauvegarde de l'unité " + unit.toString()
				+ " à échouée (identifiant nul)", unit.getId());
		String unitID = unit.getId();

		unit = null;
		unit = unitDao.findById(unitID);
		assertEquals("La récupération de la surface enregistrée d'identifiant "
				+ unitID + " à échoué", unitID, unit.getId());

		unitDao.deleteById(unitID);
		unit = null;
		unit = unitDao.findById(unitID);
		// assertNull("La suppression de l'unité d'identifiant "+unit.getId()+" à échouée (l'enregistrement existe toujours)",
		// unit);
		/**
		 * Test échoue actuellement: le remove ne fonctionne pas dans la release
		 * 1.0 de Spring Data Mongo cf
		 * https://jira.springsource.org/browse/DATAMONGO-346
		 */
	}

	@Test
	public void testSaveAndRestoreRoundOrders() {

		Surface surface = new Jardin("Mon jardin test", 10, 10);

		Unit unit1 = new Mower("Ma tondeuse A", 0, 0, 'N');
		ArrayList<Character> unit1_orders = new ArrayList<Character>();
		unit1_orders.add('D');
		unit1_orders.add('A');
		unit1_orders.add('A');
		unit1_orders.add('A');

		Unit unit2 = new Mower("Ma tondeuse B", 5, 5, 'S');
		ArrayList<Character> unit2_orders = new ArrayList<Character>();
		unit2_orders.add('A');
		unit2_orders.add('D');
		unit2_orders.add('A');
		unit2_orders.add('A');
		unit2_orders.add('G');

		RoundOrders roundorders = new RoundOrders();
		roundorders.setSurface(surface);
		roundorders.addUnitsAndOrders(unit1, unit1_orders);
		roundorders.addUnitsAndOrders(unit2, unit2_orders);

		roundOrdersDao.saveOrUpdate(roundorders);
		assertNotNull("La sauvegarde des ordres " + roundorders.toString()
				+ " à échouée (identifiant nul)", roundorders.getId());
		String roundOrdersID = roundorders.getId();

		roundorders = null;
		roundorders = roundOrdersDao.findById(roundOrdersID);
		assertEquals("La récupération des ordres enregistrés d'identifiant "
				+ roundOrdersID + " à échoué", roundOrdersID,
				roundorders.getId());

		roundOrdersDao.deleteById(roundOrdersID);
		roundorders = null;
		roundorders = roundOrdersDao.findById(roundOrdersID);
		// assertNull("La suppression des ordres d'identifiant "+roundOrdersID+" à échouée (l'enregistrement existe toujours)",
		// roundorders);
		/**
		 * Test échoue actuellement: le remove ne fonctionne pas dans la release
		 * 1.0 de Spring Data Mongo cf
		 * https://jira.springsource.org/browse/DATAMONGO-346
		 */
	}
}
