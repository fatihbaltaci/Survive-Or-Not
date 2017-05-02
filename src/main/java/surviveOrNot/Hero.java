package surviveOrNot;

public class Hero implements Creature {

	private int hp;
	private int attack;
	private int position;
	private int remainingDistanceToResources;
	

	public Hero(int hp, int attack, int position, int remainingDistanceToResources) {
		super();
		this.hp = hp;
		this.attack = attack;
		this.position = position;
		this.remainingDistanceToResources = remainingDistanceToResources;
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
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public int getRemainingDistanceToResources() {
		return remainingDistanceToResources;
	}
	public void setRemainingDistanceToResources(int remainingDistanceToResources) {
		this.remainingDistanceToResources = remainingDistanceToResources;
	}


	public int takeDamage(int damageSize) {
		setHp(hp - damageSize);
		return hp;
	}

}
