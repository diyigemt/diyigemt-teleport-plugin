package net.diyigemt.mcpeplugin.dao;

import cn.nukkit.level.Location;
import com.j256.ormlite.dao.Dao;
import net.diyigemt.mcpeplugin.entity.HomePosition;
import net.diyigemt.mcpeplugin.entity.SpawnPosition;
import net.diyigemt.mcpeplugin.sql.DatabaseHelper;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpawnDAO {

	private static Dao<SpawnPosition, Integer> dao;

	public static void init() {
		dao = DatabaseHelper.getInstance().getDAO(SpawnPosition.class, Integer.class);
	}

	public void setSpawnLocation(Location pos) throws SQLException {
		setSpawnLocation(pos, pos.getLevel().getName());
	}

	public void setSpawnLocation(Location pos, String levelName) throws SQLException {
		SpawnPosition position = new SpawnPosition(pos);
		position.setLevelName(levelName);
		List<SpawnPosition> res = dao.queryForEq("level_name", levelName);
		if (res != null && !res.isEmpty()) {
			position = res.get(0).setPosition(pos);
			dao.update(position);
			return;
		}
		dao.createIfNotExists(position);
	}

	public SpawnPosition getSpawnLocation(String levelName) throws SQLException {
		List<SpawnPosition> res = dao.queryForEq("level_name", levelName);
		if (res != null && !res.isEmpty()) {
			return res.get(0);
		}
		return null;
	}
}
