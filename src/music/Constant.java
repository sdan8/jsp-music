package music;

import java.util.Date;

public class Constant {
	
	/**
	 * 性别：保密
	 */
	public static final int SEX_DEFAULT = 0;
	/**
	 * 性别：男
	 */
	public static final int SEX_MALE = 1;
	/**
	 * 性别：女
	 */
	public static final int SEX_FEMALE = 2;
	
	/**
	 * 帐号状态：正常
	 */
	public static final int USER_STATUS_NORMAL = 0;
	/**
	 * 帐号状态：封禁
	 */
	public static final int USER_STATUS_BAN = 1;
	
	
	/**
	 * 默认日期
	 */
	public static final Date DEFAULT_DATE = new Date();
	
	
	/**
	 * 用户未登录
	 */
	public static final int LOGIN_FAILURE = 0;
	/**
	 * 用户已经成功登录
	 */
	public static final int LOGIN_SUCCESS = 1;
	
	
	/**
	 * 默认头像名
	 */
	public static final String DEFAULT_AVATAR = "default.jpg";
	/**
	 * 默认头像全路径
	 */
	public static final String DEFAULT_AVATAR_PATH = "img/avatar/" + DEFAULT_AVATAR;
	/**
	 * 头像路径
	 */
	public static final String DEFAULT_AVATAR_SECPATH = "img/avatar/";
	/**
	 * 默认专辑和歌曲封面
	 */
	public static final String DEFAULT_ALBUM = "default.jpg";
	/**
	 * 默认歌手图片
	 */
	public static final String DEFAULT_SINGER = "default.jpg";
	
	/**
	 * 歌手图片路径
	 */
	public static final String SINGER_PATH = "img/singer/";
	/**
	 * 专辑图片路径
	 */
	public static final String ALBUM_PATH = "img/";
	
	/**
	 * 音乐路径
	 */
	public static final String MUSIC_PATH = "audio/";
}
