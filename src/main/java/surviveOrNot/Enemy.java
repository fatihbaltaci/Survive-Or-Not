package surviveOrNot;

public class Enemy implements Creature {

	private int hp;
	private int attack;
	private String name;
	private int position;
	
	public Enemy()
	{
		super();
	}
	
	public Enemy(int hp, int attack, String name, int position) {
		super();
		this.hp = hp;
		this.attack = attack;
		this.name = name;
		this.position = position;
	}
	
	
	//-------------Getters And Setters---------------
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}

	//---- METHODS -----
	public int takeDamage(int damageSize) {
		setHp(hp - damageSize);
		return hp;
	}
	
	@Override
	public String toString()
	{
		return String.valueOf(hp);
	}
	
	
	
	
}
