package surviveOrNot;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

public class TestEnemy {

	@Test
	public void testEnemyListComparator()
	{
		Enemy enemy1 = new Enemy(1, 1, "1", 100);
		Enemy enemy2 = new Enemy(1, 1, "1", 300);
		Enemy enemy3 = new Enemy(1, 1, "1", 50);
		Enemy enemy4 = new Enemy(1, 1, "1", 10);
		Enemy enemy5 = new Enemy(1, 1, "1", 450);
		Enemy enemy6 = new Enemy(1, 1, "1", 50);
		
		Game.addToEnemyListAndSortByPosition(enemy1);
		Game.addToEnemyListAndSortByPosition(enemy2);
		Game.addToEnemyListAndSortByPosition(enemy3);
		Game.addToEnemyListAndSortByPosition(enemy4);
		Game.addToEnemyListAndSortByPosition(enemy5);
		Game.addToEnemyListAndSortByPosition(enemy6);
		
		assertEquals("", 10, Game.enemyList.get(0).getPosition());
		assertEquals("", 50, Game.enemyList.get(1).getPosition());
		assertEquals("", 50, Game.enemyList.get(1).getPosition());
	}
	@Test
	public void enemiesSize()
	{
		JSONParser parser = new JSONParser();

		JSONArray enemies = null;

		try {
			enemies = (JSONArray) parser.parse(new FileReader("EnemyProperties.json"));
		} catch (FileNotFoundException e) {
			System.out.println("EnemyProperties.json file not found");
		} catch (IOException e) {
			System.out.println("Can not read EnemyProperties.json file");
		} catch (ParseException e) {
			System.out.println("Can not parse EnemyProperties.json file");
		}
		
		assertEquals(3, enemies.size());
	}
}
