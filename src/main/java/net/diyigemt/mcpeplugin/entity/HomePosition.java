package net.diyigemt.mcpeplugin.entity;

import cn.nukkit.level.Location;
import cn.nukkit.math.Vector3;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "home_position")
public class HomePosition {
	// 主键id
	@DatabaseField(generatedId = true, columnName = "id", unique = true)
	private int id;
	// 玩家名称
	@DatabaseField(columnName = "player_name", index = true)
	private String playerName;
	// 家的名称
	@DatabaseField(columnName = "home_name")
	private String homeName;
	// 家的世界
	@DatabaseField(columnName = "level_name")
	private String levelName;
	// 家的坐标
	@DatabaseField(columnName = "pos_x")
	private double posX;
	@DatabaseField(columnName = "pos_y")
	private double posY;
	@DatabaseField(columnName = "pos_z")
	private double posZ;

	public HomePosition() {

	}

	public HomePosition(String playerName, String homeName, Location pos) {
		this.playerName = playerName;
		this.homeName = homeName;
		this.levelName = pos.getLevel().getName();
		this.setPosition(pos);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getHomeName() {
		return homeName;
	}

	public void setHomeName(String homeName) {
		this.homeName = homeName;
	}

	public double getPosX() {
		return posX;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}

	public double getPosZ() {
		return posZ;
	}

	public void setPosZ(double posZ) {
		this.posZ = posZ;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public void setPosition(Location location) {
		this.levelName = location.getLevel().getName();
		this.posX = Math.round(location.getX());
		this.posY = Math.round(location.getY());
		this.posZ = Math.round(location.getZ());
	}

	public Vector3 getPosition() {
		return new Location(this.posX, this.posY, this.posZ);
	}
}
