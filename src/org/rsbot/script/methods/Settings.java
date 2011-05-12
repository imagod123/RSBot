package org.rsbot.script.methods;

/**
 * Provides access to game settings.
 */
public class Settings extends MethodProvider {

	public static final int SETTING_COMBAT_STYLE = 43;
	public static final int SETTING_TOGGLE_RUN = 173;
	public static final int SETTING_BANK_TOGGLE_REARRANGE_MODE = 304;
	public static final int SETTING_TOGGLE_ACCEPT_AID = 427;
	public static final int SETTING_MOUSE_BUTTONS = 170;
	public static final int SETTING_CHAT_EFFECTS = 171;
	public static final int SETTING_SPLIT_PRIVATE_CHAT = 287;
	public static final int SETTING_ADJUST_SCREEN_BRIGHTNESS = 166;
	public static final int SETTING_ADJUST_MUSIC_VOLUME = 168;
	public static final int SETTING_ADJUST_SOUND_EFFECT_VOLUME = 169;
	public static final int SETTING_ADJUST_AREA_SOUND_EFFECT_VOLUME = 872;
	public static final int SETTING_AUTO_RETALIATE = 172;
	public static final int SETTING_SWAP_QUEST_DIARY = 1002;
	public static final int SETTING_PRAYER_THICK_SKIN = 83;
	public static final int SETTING_TOGGLE_LOOP_MUSIC = 19;
	public static final int SETTING_BANK_TOGGLE_WITHDRAW_MODE = 115;
	public static final int SETTING_TYPE_SHOP = 118;
	public static final int SETTING_SPECIAL_ATTACK_ENABLED = 301;

	Settings(final MethodContext ctx) {
		super(ctx);
	}

	/**
	 * Gets the settings array.
	 *
	 * @return An <tt>int</tt> array representing all of the settings values;
	 *         otherwise <tt>new int[0]</tt>.
	 */
	public int[] getSettingArray() {
		org.rsbot.client.Settings settingArray = methods.client
				.getSettingArray();
		if (settingArray == null || settingArray.getData() == null) {
			return new int[0];
		}
		return settingArray.getData().clone(); // NEVER return pointer
	}

	/**
	 * Gets the setting at a given index.
	 *
	 * @param setting The setting index to return the value of.
	 * @return <tt>int</tt> representing the setting of the given setting id;
	 *         otherwise <tt>-1</tt>.
	 */
	public int getSetting(final int setting) {
		int[] settings = getSettingArray();
		if (setting < settings.length) {
			return settings[setting];
		}
		return -1;
	}

}
