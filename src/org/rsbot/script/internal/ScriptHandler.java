package org.rsbot.script.internal;

import org.rsbot.bot.Bot;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.internal.event.ScriptListener;
import org.rsbot.script.randoms.*;

import java.util.*;

public class ScriptHandler {

	private ArrayList<org.rsbot.script.Random> randoms = new ArrayList<org.rsbot.script.Random>();
	private HashMap<Integer, Script> scripts = new HashMap<Integer, Script>();
	private HashMap<Integer, Thread> scriptThreads = new HashMap<Integer, Thread>();

	private Set<ScriptListener> listeners = Collections.synchronizedSet(new HashSet<ScriptListener>());

	private Bot bot;

	public ScriptHandler(Bot bot) {
		this.bot = bot;
	}

	public void init() {
		try {
			randoms.add(new LoginBot());
			randoms.add(new BankPins());
			randoms.add(new BeehiveSolver());
			randoms.add(new CapnArnav());
			randoms.add(new Certer());
			randoms.add(new CloseAllInterface());
			randoms.add(new DrillDemon());
			randoms.add(new FreakyForester());
			randoms.add(new FrogCave());
			randoms.add(new GraveDigger());
			randoms.add(new ImprovedRewardsBox());
			randoms.add(new LostAndFound());
			randoms.add(new Maze());
			randoms.add(new Mime());
			randoms.add(new Molly());
			randoms.add(new Exam());
			randoms.add(new Pillory());
			randoms.add(new Pinball());
			randoms.add(new Prison());
			randoms.add(new QuizSolver());
			randoms.add(new SandwhichLady());
			randoms.add(new ScapeRuneIsland());
			randoms.add(new TeleotherCloser());
			randoms.add(new FirstTimeDeath());
			randoms.add(new LeaveSafeArea());
			randoms.add(new SystemUpdate());
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (org.rsbot.script.Random r : randoms) {
			r.init(bot.getMethodContext());
		}
	}

	public void addScriptListener(ScriptListener l) {
		listeners.add(l);
	}

	public void removeScriptListener(ScriptListener l) {
		listeners.remove(l);
	}

	private void addScriptToPool(Script ss, Thread t) {
		for (int off = 0; off < scripts.size(); ++off) {
			if (!scripts.containsKey(off)) {
				scripts.put(off, ss);
				ss.setID(off);
				scriptThreads.put(off, t);
				return;
			}
		}
		ss.setID(scripts.size());
		scripts.put(scripts.size(), ss);
		scriptThreads.put(scriptThreads.size(), t);
	}

	public Bot getBot() {
		return bot;
	}

	public Collection<org.rsbot.script.Random> getRandoms() {
		return randoms;
	}

	public Map<Integer, Script> getRunningScripts() {
		return Collections.unmodifiableMap(scripts);
	}

	public void pauseScript(int id) {
		Script s = scripts.get(id);
		s.setPaused(!s.isPaused());
		if (s.isPaused()) {
			for (ScriptListener l : listeners) {
				l.scriptPaused(this, s);
			}
		} else {
			for (ScriptListener l : listeners) {
				l.scriptResumed(this, s);
			}
		}
	}

	public void stopScript(int id) {
		Script script = scripts.get(id);
		if (script != null) {
			script.deactivate(id);
			scripts.remove(id);
			scriptThreads.remove(id);
			for (ScriptListener l : listeners) {
				l.scriptStopped(this, script);
			}
		}
	}

	public boolean onBreak(int id) {
		Script script = scripts.get(id);
		if (script != null) {
			return script.onBreakStart();
		}
		return false;
	}

	public void onBreakConclude(int id) {
		Script script = scripts.get(id);
		if (script != null) {
			script.onBreakFinish();
		}
	}

	public void runScript(Script script) {
		script.init(bot.getMethodContext());
		for (ScriptListener l : listeners) {
			l.scriptStarted(this, script);
		}
		ScriptManifest prop = script.getClass().getAnnotation(ScriptManifest.class);
		Thread t = new Thread(script, "Script-" + prop.name());
		addScriptToPool(script, t);
		t.start();
	}

	public void stopAllScripts() {
		for (int i : scripts.keySet()) {
			stopScript(i);
		}
	}

	public void stopScript() {
		Thread curThread = Thread.currentThread();
		for (int i = 0; i < scripts.size(); i++) {
			Script script = scripts.get(i);
			if (script != null && script.isRunning()) {
				if (scriptThreads.get(i) == curThread) {
					stopScript(i);
				}
			}
		}
		if (curThread == null) {
			throw new ThreadDeath();
		}
	}

	public boolean onBreak() {
		Thread curThread = Thread.currentThread();
		for (int i = 0; i < scripts.size(); i++) {
			Script script = scripts.get(i);
			if (script != null && script.isRunning()) {
				if (scriptThreads.get(i) == curThread) {
					return onBreak(i);
				}
			}
		}
		if (curThread == null) {
			throw new ThreadDeath();
		}
		return false;
	}

	public void onBreakResume() {
		Thread curThread = Thread.currentThread();
		for (int i = 0; i < scripts.size(); i++) {
			Script script = scripts.get(i);
			if (script != null && script.isRunning()) {
				if (scriptThreads.get(i) == curThread) {
					onBreakConclude(i);
					return;
				}
			}
		}
		if (curThread == null) {
			throw new ThreadDeath();
		}
	}

	public void updateInput(Bot bot, int mask) {
		for (ScriptListener l : listeners) {
			l.inputChanged(bot, mask);
		}
	}

}
