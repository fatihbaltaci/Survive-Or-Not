package surviveOrNot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Game {

	static List<Enemy> enemyList = new ArrayList<Enemy>();	//List of enemies on the road with sorted order
	static Hero hero;

	public static void main(String[] args) {

		Scanner input = Files.readFile(args[0]);
		FileWriter output = Files.writeFile(args[1]);

		if (input == null || output == null) {
			System.exit(0);
		}

		initialiseGame(input);

		try {
			fightWithEnemies(output);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Files.closeOutputFile(output);

	}
	
	
	private static void initialiseGame(Scanner input) {

		int resourceDistance = tryParseStringToInt(
				input.nextLine().trim().split("Resources are ")[1].split(" ")[0].trim());
		int heroHp = tryParseStringToInt(input.nextLine().trim().split("Hero has ")[1].split(" ")[0].trim());
		int heroAttack = tryParseStringToInt(input.nextLine().trim().split("Hero attack is ")[1].trim());
		int heroPosition = 0;
		hero = new Hero(heroHp, heroAttack, heroPosition, resourceDistance);


		String lastLine = writeEnemyPropertiesToJson(input);

		deployEnemiesToPositions(lastLine, input);

	}
	
	private static void fightWithEnemies(FileWriter output) throws IOException {
		output.append("Hero started journey with "+ hero.getHp() +" HP!\n");
		for (int i = 0; i < enemyList.size(); i++) {
			Enemy enemy = enemyList.get(i);
			hero.setPosition(enemy.getPosition());
			int timeToKillForEnemy = (int) Math.ceil((double)enemy.getHp() / hero.getAttack());
			int timeToKillForHero = (int) Math.ceil((double)hero.getHp() / enemy.getAttack());
			
			
			int heroHp = hero.takeDamage(timeToKillForEnemy*enemy.getAttack());
			if(heroHp<=0)
			{//Hero dead
				enemy.takeDamage(timeToKillForHero*hero.getAttack());
				output.append(enemy.getName()+" defeated Hero with " +enemy.getHp()+ " HP remaining\n");
				output.append("Hero is Dead!! Last seen at position "+hero.getPosition()+"!!");
				break;
			} else{
				enemy.takeDamage(timeToKillForEnemy*hero.getAttack());
				output.append("Hero defeated " +enemy.getName()+ " with " +heroHp+ " HP remaining\n");
			}
		}
		if(hero.getHp() > 0){
			output.append("Hero Survived!");
		}
	}



	
	private static void deployEnemiesToPositions(String lastLine, Scanner input) {

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

		int counter = -1;
		while (input.hasNextLine()) {
			String nextLine = "";
			if (counter == -1)
				nextLine = lastLine;
			else
				nextLine = input.nextLine();

			if (nextLine.contains("There is a ")) {
				String enemyName = nextLine.split("There is a ")[1].trim().split(" at position ")[0];
				String enemyPosition = nextLine.split("There is a ")[1].trim().split(" at position ")[1];


				for (int i = 0; i < enemies.size(); i++) {
					JSONObject enemyJs = (JSONObject) enemies.get(i);
					if (enemyJs.get("name").equals(enemyName)) {
						Enemy enemy = new Enemy(tryParseStringToInt(enemyJs.get("hp").toString()),
								tryParseStringToInt(enemyJs.get("attack").toString()), enemyJs.get("name").toString(),
								tryParseStringToInt(enemyPosition.toString()));
						addToEnemyListAndSortByPosition(enemy);
					}
				}
			}
			counter = 0;
		}

	}

	@SuppressWarnings("unchecked")
	private static String writeEnemyPropertiesToJson(Scanner input) {

		List<JSONObject> enemy = new ArrayList<JSONObject>();
		JSONArray enemies = new JSONArray();
		String lastLine = "";
		int counter = 0;
		while (input.hasNextLine()) {
			String nextLine = input.nextLine();

			if (nextLine.contains("is Enemy")) {
				String enemyName = nextLine.trim().split(" is Enemy")[0].trim();

				enemy.add(new JSONObject());
				enemy.get(counter).put("name", enemyName);
				enemy.get(counter).put("attack", 0);
				enemy.get(counter).put("hp", 0);
				enemies.add(enemy.get(counter));
				counter++;
			}
			// Enemy HP
			else if (nextLine.contains("has")) {
				counter = 0;
				String enemyName = nextLine.trim().split(" has ")[0];
				String enemyHp = nextLine.trim().split(" has ")[1].trim().split(" ")[0].trim();
				for (int i = 0; i < enemy.size(); i++) {
					if (enemy.get(i).get("name").equals(enemyName)) {
						enemy.get(i).put("hp", enemyHp);
					}
				}
			}
			// Enemy Attack
			else if (nextLine.contains(" attack is ")) {
				counter = 0;
				String enemyName = nextLine.trim().split(" attack is ")[0];
				String enemyAttact = nextLine.trim().split(" attack is ")[1];
				for (int i = 0; i < enemy.size(); i++) {
					if (enemy.get(i).get("name").equals(enemyName)) {
						enemy.get(i).put("attack", enemyAttact);
					}
				}
			} else {
				lastLine = nextLine;
				break;
			}
		}
		File file = new File("EnemyProperties.json");
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(enemies.toJSONString());
			fw.close();
		} catch (IOException e) {
			System.out.println("Enemy Property JSON can not be created");
			System.exit(-2);
		}

		return lastLine;

	}

	private static Integer tryParseStringToInt(String text) {
		try {
			return Integer.parseInt(text);
		} catch (NumberFormatException e) {
			System.out.println("Number formats are not supported, please use integers on input file");
			System.exit(1);
		}
		return null;
	}

	static void addToEnemyListAndSortByPosition(Enemy enemy) {
		if (enemyList.add(enemy)) {
			Collections.sort(enemyList, new Comparator<Enemy>() {

				public int compare(Enemy o1, Enemy o2) {
					return o1.getPosition() - o2.getPosition();
				}
			});
		}

	}

}
