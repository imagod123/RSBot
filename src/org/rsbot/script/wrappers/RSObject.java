package org.rsbot.script.wrappers;

import java.awt.Point;

import org.rsbot.client.LDModel;
import org.rsbot.client.Model;
import org.rsbot.client.RSAnimable;
import org.rsbot.script.methods.MethodContext;
import org.rsbot.script.methods.MethodProvider;

/**
 * Represents a physical in-game object.
 */
public class RSObject extends MethodProvider {
	
	public static enum Type {
		INTERACTABLE, FLOOR_DECORATION, BOUNDARY, WALL_DECORATION
	}

	private org.rsbot.client.RSObject obj;
	private Type type;
	private int plane;

	public RSObject(
			final MethodContext ctx,
			final org.rsbot.client.RSObject obj,
			final Type type, final int plane) {
		super(ctx);
		this.obj = obj;
		this.type = type;
		this.plane = plane;
	}
	
	/**
	 * Gets the RSTile on which this object is centered.
	 * An RSObject may cover multiple tiles, in which case
	 * this will return the floored central tile.
	 * 
	 * @return The central RSTile.
	 * @see #getArea()
	 */
	public RSTile getLocation() {
		return new RSTile(methods.client.getBaseX() + obj.getX() / 512,
				methods.client.getBaseY() + obj.getY() / 512);
	}
	
	/**
	 * Gets the area of tiles covered by this object.
	 * 
	 * @return The RSArea containing all the tiles on which
	 * this object can be found.
	 */
	public RSArea getArea() {
		// TODO: hook X1, X2, Y1, Y2 for all objects
		if (obj instanceof RSAnimable) {
			RSAnimable a = (RSAnimable) obj;
			RSTile sw = new RSTile(methods.client.getBaseX() + a.getX1(), methods.client.getBaseY() + a.getY1());
			RSTile ne = new RSTile(methods.client.getBaseX() + a.getX2(), methods.client.getBaseY() + a.getY2());
			return new RSArea(sw, ne, plane);
		}
		RSTile loc = getLocation();
		return new RSArea(loc, loc, plane);
	}

	/**
	 * Gets the object definition of this object.
	 * 
	 * @return The RSObjectDef if available, otherwise <code>null</code>.
	 */
	public RSObjectDef getDef() {
		org.rsbot.client.Node ref = methods.nodes
				.lookup(methods.client.getRSObjectDefFactory(), getID());
		if (ref != null) {
			if (ref instanceof org.rsbot.client.HardReference) {
				return new RSObjectDef((org.rsbot.client.RSObjectDef)
						(((org.rsbot.client.HardReference) ref).get()));
			} else if (ref instanceof org.rsbot.client.SoftReference) {
				Object def = ((org.rsbot.client.SoftReference) ref).getReference().get();
				if (def != null) {
					return new RSObjectDef((org.rsbot.client.RSObjectDef) def);
				}
			}
		}
		return null;
	}

	/**
	 * Gets the ID of this object.
	 * 
	 * @return The ID.
	 */
	public int getID() {
		return obj.getID();
	}

	/**
	 * Gets the Model of this object.
	 * 
	 * @return The RSModel, or null if unavailable.
	 */
	public RSModel getModel() {
		try {
			Model model = obj.getModel();
			if (model != null) {
				return new RSObjectModel(methods, (LDModel) model, obj);
			}
		} catch (AbstractMethodError ignored) {
		}
		return null;
	}

	/**
	 * Determines whether or not this object is on the game screen.
	 * 
	 * @return <tt>true</tt> if the object is on screen.
	 */
	public boolean isOnScreen() {
		RSModel model = getModel();
		if (model == null) {
			return methods.calc.tileOnScreen(getLocation());
		} else {
			return methods.calc.pointOnScreen(model.getPoint());
		}
	}

	/**
	 * Returns this object's type.
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Performs the specified action on this object.
	 * 
	 * @param action
	 *            the menu item to search and click
	 * @return returns true if clicked, false if object does not contain the
	 *         desired action
	 */
	public boolean doAction(String action) {
		RSModel model = getModel();
		if (model != null) {
			methods.mouse.move(model.getPoint());
			int iters = random(10, 50);
			while (--iters > 0) {
				if (!methods.menu.contains(action)) {
					break;
				}
				sleep(3);
			}
			if (methods.menu.contains(action)) {
				return methods.menu.doAction(action);
			} else {
				methods.mouse.move(model.getPoint());
				return methods.menu.doAction(action);
			}
		}
		return methods.tiles.doAction(getLocation(), action);
	}
	
	/**
	 * Left-clicks this object.
	 * 
	 * @return <tt>true</tt> if clicked.
	 */
	public boolean doClick() {
		return doClick(true);
	}
	
	/**
	 * Clicks this object.
	 * 
	 * @param left
	 *            <tt>true</tt> to left-click; <tt>false</tt> to right-click.
	 * @return <tt>true</tt> if clicked.
	 */
	public boolean doClick(boolean left) {
		RSModel model = getModel();
		Point p;
		if (model != null) {
			p = model.getPoint();
		} else {
			p = methods.calc.tileToScreen(getLocation());
		}
		if (methods.calc.pointOnScreen(p)) {
			methods.mouse.move(p);
			if (methods.calc.pointOnScreen(p)) {
				methods.mouse.click(left);
				return true;
			} else {
				if (model != null) {
					p = model.getPoint();
				} else {
					p = methods.calc.tileToScreen(getLocation());
				}
				if (methods.calc.pointOnScreen(p)) {
					methods.mouse.move(p);
					methods.mouse.click(left);
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Moves the mouse over this object.
	 * 
	 * @return <tt>true</tt> if the mouse was moved.
	 */
	public boolean doHover() {
		RSModel model = getModel();
		Point p;
		if (model != null) {
			p = model.getPoint();
		} else {
			p = methods.calc.tileToScreen(getLocation());
		}
		if (methods.calc.pointOnScreen(p)) {
			methods.mouse.move(p);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean equals(Object o) {
		return (o instanceof RSObject) && ((RSObject) o).obj == obj;
	}
	
	@Override
	public int hashCode() {
		return obj.hashCode();
	}

}
