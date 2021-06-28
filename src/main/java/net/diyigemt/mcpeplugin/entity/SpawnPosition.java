package net.diyigemt.mcpeplugin.entity;

import cn.nukkit.level.Location;
import cn.nukkit.math.Vector3;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "spawn_position")
public class SpawnPosition {
	// 主键id
	@DatabaseField(generatedId = true, columnName = "id", unique = true)
	private int id;
	// 家的名称
	@DatabaseField(columnName = "level_name")
	private String levelName;
	// 家的坐标
	@DatabaseField(columnName = "pos_x")
	private double posX;
	@DatabaseField(columnName = "pos_y")
	private double posY;
	@DatabaseField(columnName = "pos_z")
	private double posZ;

	public SpawnPosition() {

	}

	public SpawnPosition(Location pos) {
		this.setPosition(pos);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public SpawnPosition setPosition(Location location) {
		this.levelName = location.getLevel().getName();
		this.posX = Math.round(location.getX());
		this.posY = Math.round(location.getY());
		this.posZ = Math.round(location.getZ());
		return this;
	}

	public Vector3 getPosition() {
		return new Vector3(this.posX, this.posY, this.posZ);
	}
}
