package org.rsbot.util;

import org.rsbot.log.LogFormatter;
import org.rsbot.log.SystemConsoleHandler;
import org.rsbot.log.TextAreaLogHandler;

import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;

public class GlobalConfiguration {

	public enum OperatingSystem {
		MAC, WINDOWS, LINUX, UNKNOWN
	}

	public static class Paths {
		public static class Resources {
			public static final String ROOT = "resources";
			public static final String SCRIPTS = Paths.SCRIPTS_NAME_SRC + "/";
			public static final String ROOT_IMG = "/" + Resources.ROOT + "/images";
			public static final String ICON = Resources.ROOT_IMG + "/icon.png";
			public static final String ICON_DELETE = Resources.ROOT_IMG + "/delete.png";
			public static final String ICON_PLAY = Resources.ROOT_IMG + "/control_play_blue.png";
			public static final String ICON_PAUSE = Resources.ROOT_IMG + "/control_pause.png";
			public static final String ICON_ADD = Resources.ROOT_IMG + "/add.png";
			public static final String ICON_ADD_OVER = Resources.ROOT_IMG + "/add_over.png";
			public static final String ICON_ADD_DOWN = Resources.ROOT_IMG + "/add_down.png";
			public static final String ICON_HOME = Resources.ROOT_IMG + "/home.png";
			public static final String ICON_BOT = Resources.ROOT_IMG + "/bot.png";
			public static final String ICON_CLOSE = Resources.ROOT_IMG + "/close.png";
			public static final String ICON_CLOSE_OVER = Resources.ROOT_IMG + "/close_over.png";
			public static final String ICON_TICK = Resources.ROOT_IMG + "/tick.png";
			public static final String ICON_MOUSE = Resources.ROOT_IMG + "/mouse.png";
			public static final String ICON_KEYBOARD = Resources.ROOT_IMG + "/keyboard.png";
			public static final String ICON_CONNECT = Resources.ROOT_IMG + "/connect.png";
			public static final String ICON_DISCONNECT = Resources.ROOT_IMG + "/disconnect.png";
			public static final String ICON_START = Resources.ROOT_IMG + "/control_play.png";
			public static final String ICON_SCRIPT_BDL = Resources.ROOT_IMG + "/script_bdl.png";
			public static final String ICON_SCRIPT_DRM = Resources.ROOT_IMG + "/script_drm.png";
			public static final String ICON_SCRIPT_PRE = Resources.ROOT_IMG + "/script_pre.png";
			public static final String ICON_SCRIPT_SRC = Resources.ROOT_IMG + "/script_src.png";

			public static final String VERSION = Resources.ROOT + "/version.txt";
		}

		public static class URLs {
			public static final String UPDATER = "http://links.powerbot.org/";
			public static final String DOWNLOAD = UPDATER + "update";
			public static final String UPDATE = UPDATER + "modscript";
			public static final String WEB = UPDATER + "webwalker.gz";
			public static final String EASTER_MATRIX = UPDATER + "matrix";
			public static final String VERSION = UPDATER + "version.txt";
			public static final String PROJECT = UPDATER + "git-project";
			public static final String SITE = "http://www.powerbot.org";
			public static final String STATS = "http://stats.powerbot.org/sync/";
			public static final String AD_INFO = UPDATER + "botad-info";
		}

		public static final String ROOT = "." + File.separator + "resources";

		public static final String COMPILE_SCRIPTS_BAT = "Compile-Scripts.bat";
		public static final String COMPILE_SCRIPTS_SH = "compile-scripts.sh";
		public static final String COMPILE_FIND_JDK = "FindJDK.bat";

		public static final String ROOT_IMG = Paths.ROOT + File.separator + "images";
		public static final String ICON = Paths.ROOT_IMG + File.separator + "icon.png";
		public static final String ICON_DELETE = Paths.ROOT_IMG + File.separator + "delete.png";
		public static final String ICON_PLAY = Paths.ROOT_IMG + File.separator + "control_play_blue.png";
		public static final String ICON_PAUSE = Paths.ROOT_IMG + File.separator + "control_pause.png";
		public static final String ICON_ADD = Paths.ROOT_IMG + File.separator + "add.png";
		public static final String ICON_ADD_OVER = Paths.ROOT_IMG + File.separator + "add_over.png";
		public static final String ICON_ADD_DOWN = Paths.ROOT_IMG + File.separator + "add_down.png";
		public static final String ICON_HOME = Paths.ROOT_IMG + File.separator + "home.png";
		public static final String ICON_BOT = Paths.ROOT_IMG + File.separator + "bot.png";
		public static final String ICON_CLOSE = Paths.ROOT_IMG + File.separator + "close.png";
		public static final String ICON_CLOSE_OVER = Paths.ROOT_IMG + File.separator + "close_over.png";
		public static final String ICON_TICK = Paths.ROOT_IMG + File.separator + "tick.png";
		public static final String ICON_MOUSE = Paths.ROOT_IMG + File.separator + "mouse.png";
		public static final String ICON_KEYBOARD = Paths.ROOT_IMG + File.separator + "keyboard.png";
		public static final String ICON_CONNECT = Paths.ROOT_IMG + File.separator + "connect.png";
		public static final String ICON_DISCONNECT = Paths.ROOT_IMG + File.separator + "disconnect.png";
		public static final String ICON_START = Paths.ROOT_IMG + File.separator + "control_play.png";
		public static final String ICON_SCRIPT_BDL = Paths.ROOT_IMG + File.separator + "script_bdl.png";
		public static final String ICON_SCRIPT_DRM = Paths.ROOT_IMG + File.separator + "script_drm.png";
		public static final String ICON_SCRIPT_PRE = Paths.ROOT_IMG + File.separator + "script_pre.png";
		public static final String ICON_SCRIPT_SRC = Paths.ROOT_IMG + File.separator + "script_src.png";

		public static final String SCRIPTS_NAME_SRC = "scripts";
		public static final String SCRIPTS_NAME_OUT = "Scripts";

		public static String getAccountsFile() {
			final String path;
			if (GlobalConfiguration.getCurrentOperatingSystem() == OperatingSystem.WINDOWS) {
				path = System.getenv("APPDATA") + File.separator
						+ GlobalConfiguration.NAME + "_Accounts.ini";
			} else {
				path = Paths.getUnixHome() + File.separator + "."
						+ GlobalConfiguration.NAME_LOWERCASE + "acct";
			}
			return path;
		}

		public static String getHomeDirectory() {
			final String env = System.getenv(GlobalConfiguration.NAME.toUpperCase() + "_HOME");
			if ((env == null) || env.isEmpty()) {
				return (GlobalConfiguration.getCurrentOperatingSystem() == OperatingSystem.WINDOWS ?
						FileSystemView.getFileSystemView().getDefaultDirectory().getAbsolutePath() :
						Paths.getUnixHome()) + File.separator + GlobalConfiguration.NAME;
			} else {
				return env;
			}
		}

		public static String getLogsDirectory() {
			return Paths.getHomeDirectory() + File.separator + "Logs";
		}

		public static String getMenuCache() {
			return Paths.getSettingsDirectory() + File.separator + "Menu.txt";
		}

		public static String getPathCache() {
			return Paths.getSettingsDirectory() + File.separator + "path.txt";
		}

		public static String getBootCache() {
			return Paths.getSettingsDirectory() + File.separator + "boot.txt";
		}

		public static String getUIDsFile() {
			return Paths.getSettingsDirectory() + File.separator + "uid.txt";
		}

		public static String getScreenshotsDirectory() {
			return Paths.getHomeDirectory() + File.separator + "Screenshots";
		}

		public static String getScriptsDirectory() {
			return Paths.getHomeDirectory() + File.separator + Paths.SCRIPTS_NAME_OUT;
		}

		public static String getScriptsSourcesDirectory() {
			return Paths.getScriptsDirectory() + File.separator + "Sources";
		}

		public static String getScriptsPrecompiledDirectory() {
			return Paths.getScriptsDirectory() + File.separator + "Precompiled";
		}

		public static String getCacheDirectory() {
			return Paths.getHomeDirectory() + File.separator + "Cache";
		}

		public static String getScriptsExtractedCache() {
			return Paths.getCacheDirectory() + File.separator + "script.dat";
		}

		public static String getVersionCache() {
			return Paths.getCacheDirectory() + File.separator + "info.dat";
		}

		public static String getModScriptCache() {
			return Paths.getCacheDirectory() + File.separator + "ms.dat";
		}

		public static String getClientCache() {
			return Paths.getCacheDirectory() + File.separator + "client.dat";
		}

		public static String getWebCache() {
			return Paths.getCacheDirectory() + File.separator + "web.dat";
		}

		public static String getHackCache() {
			return Paths.getCacheDirectory() + File.separator + "hack.dat";
		}

		public static String getSettingsDirectory() {
			return Paths.getHomeDirectory() + File.separator + "Settings";
		}

		public static String getUnixHome() {
			final String home = System.getProperty("user.home");
			return home == null ? "~" : home;
		}
	}

	public static final String NAME = "RSBot";
	public static final String NAME_LOWERCASE = NAME.toLowerCase();
	public static final String SITE_NAME = "powerbot";
	private static final OperatingSystem CURRENT_OS;
	public static boolean RUNNING_FROM_JAR = false;
	public static final boolean SCRIPT_DRM = true;

	static {
		final URL resource = GlobalConfiguration.class.getClassLoader().getResource(Paths.Resources.VERSION);
		if (resource != null) {
			GlobalConfiguration.RUNNING_FROM_JAR = true;
		}
		final String os = System.getProperty("os.name");
		if (os.contains("Mac")) {
			CURRENT_OS = OperatingSystem.MAC;
		} else if (os.contains("Windows")) {
			CURRENT_OS = OperatingSystem.WINDOWS;
		} else if (os.contains("Linux")) {
			CURRENT_OS = OperatingSystem.LINUX;
		} else {
			CURRENT_OS = OperatingSystem.UNKNOWN;
		}
		final ArrayList<String> dirs = new ArrayList<String>();
		dirs.add(Paths.getHomeDirectory());
		dirs.add(Paths.getLogsDirectory());
		dirs.add(Paths.getCacheDirectory());
		dirs.add(Paths.getSettingsDirectory());
		if (GlobalConfiguration.RUNNING_FROM_JAR) {
			dirs.add(Paths.getScriptsDirectory());
			dirs.add(Paths.getScriptsSourcesDirectory());
			dirs.add(Paths.getScriptsPrecompiledDirectory());
		}
		for (final String name : dirs) {
			final File dir = new File(name);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
		Properties logging = new Properties();
		String logFormatter = LogFormatter.class.getCanonicalName();
		String fileHandler = FileHandler.class.getCanonicalName();
		logging.setProperty("handlers", TextAreaLogHandler.class.getCanonicalName() + "," + fileHandler);
		logging.setProperty(".level", "INFO");
		logging.setProperty(SystemConsoleHandler.class.getCanonicalName() + ".formatter", logFormatter);
		logging.setProperty(fileHandler + ".formatter", logFormatter);
		logging.setProperty(TextAreaLogHandler.class.getCanonicalName() + ".formatter", logFormatter);
		logging.setProperty(fileHandler + ".pattern", Paths.getLogsDirectory() + File.separator + "%u.%g.log");
		logging.setProperty(fileHandler + ".count", "10");
		final ByteArrayOutputStream logout = new ByteArrayOutputStream();
		try {
			logging.store(logout, "");
			LogManager.getLogManager().readConfiguration(new ByteArrayInputStream(logout.toByteArray()));
		} catch (final Exception ignored) {
		}
		if (GlobalConfiguration.RUNNING_FROM_JAR) {
			String path = resource.toString();
			try {
				path = URLDecoder.decode(path, "UTF-8");
			} catch (final UnsupportedEncodingException ignored) {
			}
			final String prefix = "jar:file:/";
			if (path.indexOf(prefix) == 0) {
				path = path.substring(prefix.length());
				path = path.substring(0, path.indexOf('!'));
				if (File.separatorChar != '/') {
					path = path.replace('/', File.separatorChar);
				}
				try {
					final File pathfile = new File(Paths.getPathCache());
					if (pathfile.exists()) {
						pathfile.delete();
					}
					pathfile.createNewFile();
					Writer out = new BufferedWriter(new FileWriter(Paths.getPathCache()));
					out.write(path);
					out.close();
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static Image getImage(String resource, String path) {
		try {
			return Toolkit.getDefaultToolkit().getImage(
					GlobalConfiguration.RUNNING_FROM_JAR ? GlobalConfiguration.class.getResource(resource) :
							new File(path).toURI().toURL());
		} catch (Exception ignored) {
		}
		return null;
	}

	public static OperatingSystem getCurrentOperatingSystem() {
		return GlobalConfiguration.CURRENT_OS;
	}

	public static String getHttpUserAgent() {
		String plat = "Windows", os = "Windows NT 5.2";
		if (GlobalConfiguration.getCurrentOperatingSystem() == GlobalConfiguration.OperatingSystem.MAC) {
			plat = "Macintosh";
			os = "Intel Mac OS X 10_6_4";
		} else if (GlobalConfiguration.getCurrentOperatingSystem() != GlobalConfiguration.OperatingSystem.WINDOWS) {
			plat = "X11";
			os = "Linux i686";
		}
		StringBuilder buf = new StringBuilder(125);
		buf.append("Mozilla/5.0 (").append(plat).append("; U; ").append(os);
		buf.append("; en-US) AppleWebKit/534.7 (KHTML, like Gecko) Chrome/7.0.517.44 Safari/534.7");
		return buf.toString();
	}

	public static URLConnection getURLConnection(final URL url, final String referer) throws IOException {
		final URLConnection con = url.openConnection();
		con.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		con.addRequestProperty("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
		con.addRequestProperty("Accept-Encoding", "gzip,deflate");
		con.addRequestProperty("Accept-Language", "en-us,en;q=0.5");
		con.addRequestProperty("Connection", "keep-alive");
		con.addRequestProperty("Host", url.getHost());
		con.addRequestProperty("Keep-Alive", "300");
		if (referer != null) {
			con.addRequestProperty("Referer", referer);
		}
		con.addRequestProperty("User-Agent", getHttpUserAgent());
		return con;
	}

	public static int getVersion() {
		InputStreamReader is = null;
		BufferedReader reader = null;
		try {
			is = new InputStreamReader(RUNNING_FROM_JAR ?
					GlobalConfiguration.class.getClassLoader().getResourceAsStream(
							Paths.Resources.VERSION) : new FileInputStream(Paths.Resources.VERSION));
			reader = new BufferedReader(is);
			String s = reader.readLine().trim();
			return Integer.parseInt(s);
		} catch (Exception e) {
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (reader != null) {
					reader.close();
				}
			} catch (IOException ioe) {
			}
		}
		return -1;
	}
}
