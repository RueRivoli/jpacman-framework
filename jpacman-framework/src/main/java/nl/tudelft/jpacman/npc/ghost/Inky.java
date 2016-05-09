package nl.tudelft.jpacman.npc.ghost;

import java.util.Map;
import java.util.Random;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.sprite.Sprite;

/**
 * <p>
 * An implementation of the classic Pac-Man ghost Bashful.
 * </p>
 * <p>
 * Nickname: Inky. Bashful has truly earned his name. In a game of chicken
 * between Pac-Man and Bashful, Bashful might just run away. This isn't always
 * the case, but if Blinky is right behind you, it's a safe bet. He can't be
 * scared off while he patrols the southeast portion of the maze. In Japan, his
 * name is Kimagure/Aosuke.
 * </p>
 * <p>
 * <b>AI:</b> Bashful has the most complicated AI of all. When the ghosts are
 * not patrolling their home corners, Bashful considers two things: Shadow's
 * location, and the location two grid spaces ahead of Pac-Man. Bashful draws a
 * line from Shadow to the spot that is two squares in front of Pac-Man and
 * extends that line twice as far. Therefore, if Bashful is alongside Shadow
 * when they are behind Pac-Man, Bashful will usually follow Shadow the whole
 * time. But if Bashful is in front of Pac-Man when Shadow is far behind him,
 * Bashful tends to want to move away from Pac-Man (in reality, to a point very
 * far ahead of Pac-Man). Bashful is affected by a similar targeting bug that
 * affects Speedy. When Pac-Man is moving or facing up, the spot Bashful uses to
 * draw the line is two squares above and left of Pac-Man.
 * </p>
 * <p>
 * Source: http://strategywiki.org/wiki/Pac-Man/Getting_Started
 * </p>
 *
 * @author Jeroen Roosen
 *
 */
public class Inky extends VulnerableGhost {

	private static final int SQUARES_AHEAD = 2;

	/**
	 * The variation in intervals, this makes the ghosts look more dynamic and
	 * less predictable.
	 */
	private static final int INTERVAL_VARIATION = 50;

	/**
	 * The base movement interval.
	 */
	private static final int MOVE_INTERVAL = 250;

	
	private Direction[] chemin= {Direction.WEST, Direction.WEST,Direction.WEST,
			Direction.WEST,Direction.WEST,Direction.WEST,Direction.WEST,Direction.WEST,
			Direction.WEST,Direction.NORTH,Direction.NORTH,Direction.EAST,Direction.EAST,
			Direction.EAST,Direction.NORTH,Direction.NORTH,Direction.EAST,Direction.EAST,
			Direction.SOUTH,Direction.SOUTH,Direction.EAST,Direction.EAST,Direction.EAST,
			Direction.EAST,Direction.SOUTH,Direction.SOUTH}; 
	/**
	 * Creates a new "Inky", a.k.a. Bashful.
	 *
	 * @param spriteMap
	 *            The sprites for this ghost.
	 */
	public Inky(Map<Direction, Sprite> spriteMap, boolean aH,String strategy,Direction[] dir) {
		super(spriteMap, aH, strategy);
		this.chemin=dir;
		this.cheminEnCours=dir;
		this.strategyMode=new DispersionInky(this);
	}

	public Inky(Map<Direction, Sprite> spriteMap){
		super(spriteMap);
	}

	@Override
	public void setPoursuiteMode() {
		strategyMode=new PoursuiteInky(this);
		this.cheminEnCours=this.chemin;
		this.atteintHome=false;
	}

	@Override
	public void setDispersionMode() {
		strategyMode=new DispersionInky(this);
	}

	@Override
	public long getInterval() {
		return MOVE_INTERVAL + new Random().nextInt(INTERVAL_VARIATION);
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * Bashful has the most complicated AI of all. When the ghosts are not
	 * patrolling their home corners, Bashful considers two things: Shadow's
	 * location, and the location two grid spaces ahead of Pac-Man. Bashful
	 * draws a line from Shadow to the spot that is two squares in front of
	 * Pac-Man and extends that line twice as far. Therefore, if Bashful is
	 * alongside Shadow when they are behind Pac-Man, Bashful will usually
	 * follow Shadow the whole time. But if Bashful is in front of Pac-Man when
	 * Shadow is far behind him, Bashful tends to want to move away from Pac-Man
	 * (in reality, to a point very far ahead of Pac-Man). Bashful is affected
	 * by a similar targeting bug that affects Speedy. When Pac-Man is moving or
	 * facing up, the spot Bashful uses to draw the line is two squares above
	 * and left of Pac-Man.
	 * </p>
	 *
	 * <p>
	 * <b>Implementation:</b> by lack of a coordinate system there is a
	 * workaround: first determine the square of Blinky (A) and the square 2
	 * squares away from Pac-Man (B). Then determine the shortest path from A to
	 * B regardless of terrain and walk that same path from B. This is the
	 * destination.
	 * </p>
	 */
	// CHECKSTYLE:OFF To keep this more readable.
	@Override
	public Direction nextMove() {

		if (!isHunter()) {
			return randomMove();
		}
		/*
		if (this.getStrategy() == "modePoursuite"){
			PoursuiteInky pi = new PoursuiteInky(this);
			return pi.nextMove();
		}
		else if (this.getStrategy() == "modeDispersion"){
			DispersionInky di = new DispersionInky(this);
			return di.nextMove();
		}
		return this.randomMove();
		}*/
		return strategyMode.nextMove();
	}
}

