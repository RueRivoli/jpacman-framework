package nl.tudelft.jpacman.level;

import java.util.Map;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.ghost.VulnerableGhost;
import nl.tudelft.jpacman.sprite.AnimatedSprite;
import nl.tudelft.jpacman.sprite.Sprite;

/**
 * A player operated unit in our game.
 * 
 * @author Jeroen Roosen 
 */
public class Player extends Unit{

	/**
	 * number of ghost eated.
	 */
	private int eatedGhost;
	/**
	 * number of super pellet eated.
	 */
	private int eatedSuperPellet;

	/**
	 * The amount of points accumulated by this player.
	 */
	private int score;
	
	/**
	 * The amount of remaining lifes the player has.
	 */
	private int lifes;

	/**
	 * The animations for every direction.
	 */
	private final Map<Direction, Sprite> sprites;

	/**
	 * The animation that is to be played when Pac-Man dies.
	 */
	private final AnimatedSprite deathSprite;

	/**
	 * <code>true</code> iff this player is alive.
	 */
	private boolean alive;

	private int maxLevel;

	/**
	 * Creates a new player with a score of 0 points.
	 * 
	 * @param spriteMap
	 *            A map containing a sprite for this player for every direction.
	 * @param deathAnimation
	 *            The sprite to be shown when this player dies.
	 */
	Player(Map<Direction, Sprite> spriteMap, AnimatedSprite deathAnimation) {
		this.score = 0;
		this.lifes = 3;
		this.maxLevel = 0;
		this.alive = true;
		this.sprites = spriteMap;
		this.deathSprite = deathAnimation;
		deathSprite.setAnimating(false);
		eatedSuperPellet=0;

	}

	/**
	 * Returns whether this player is alive or not.
	 * 
	 * @return <code>true</code> iff the player is alive.
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * Sets whether this player is alive or not.
	 * 
	 * @param isAlive
	 *            <code>true</code> iff this player is alive.
	 */
	public void setAlive(boolean isAlive) {
		if (isAlive) {
			deathSprite.setAnimating(false);
			this.alive = true;
		}
		if (!isAlive && isAlive()) {
			this.lifes--;
			deathSprite.restart();
			this.alive = false;
		}
	}

	/**
	 * Returns the amount of points accumulated by this player.
	 * 
	 * @return The amount of points accumulated by this player.
	 */
	public int getScore() {
		return score;
	}

	@Override
	public Sprite getSprite() {
		if (isAlive()) {
			return sprites.get(getDirection());
		}
		return deathSprite;
	}

	/**
	 * Adds points to the score of this player and handle the lifes bonus.
	 * 
	 * @param points
	 *            The amount of points to add to the points this player already
	 *            has.
	 */
	public void addPoints(int points) {
		int newScore = score + points;
		if(newScore / 10000 > score / 10000){
			lifes += (newScore / 10000) - (score / 10000);
		}
		score = newScore;
	}

	/**
	 * Returns true if the player has at least a remaining life
	 * 
	 * @return
	 */
	public boolean hasLifeRemaining() {
		// TODO Auto-generated method stub
		return lifes >= 0;
	}

	public void setMaxLevel(int level) {
		maxLevel = level;
		
	}

	public int getMaxLevel() {
		return maxLevel;
	}

	public int getLifes() {
		return lifes;
	}

	/**
	 *
	 * @return time within pacman is the hunter.
     */
	public int getTimeHunter() {
		if(eatedSuperPellet<=2)
			return 7000;
		else
			return 5000;
	}

	/**
	 * when a super pellet is eated.
	 */
	public void superPelletEated(){
		eatedSuperPellet+=1;
		eatedGhost=0;
	}

	/**
	 * first eated ghost =200
	 * the second = 400
	 * the third = 800
	 * the fourth 1600
	 * @return the score
     */
	public int getEatedGhost(){
		return VulnerableGhost.SCORE * (int) Math.pow(2, eatedGhost);
	}

	public void addEatedGhost(){
		eatedGhost+=1;
	}
}
