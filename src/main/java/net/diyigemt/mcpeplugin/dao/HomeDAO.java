package net.diyigemt.mcpeplugin.dao;

import cn.nukkit.level.Location;
import com.j256.ormlite.dao.Dao;
import net.diyigemt.mcpeplugin.entity.HomePosition;
import net.diyigemt.mcpeplugin.sql.DatabaseHelper;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeDAO {

	public static final String defaultHomeName = "home";
	private static Dao<HomePosition, Integer> dao;

	public static void init() {
		dao = DatabaseHelper.getInstance().getDAO(HomePosition.class, Integer.class);
	}

	public void setHome(String playerName, Location pos) throws SQLException {
		setHome(playerName, defaultHomeName, pos);
	}

	public void setHome(String playerName, String homeName, Location pos) throws SQLException {
		HomePosition homePosition = getHome(playerName, homeName);
		if (homePosition != null) {
			homePosition.setPosition(pos);
			dao.update(homePosition);
		}
		homePosition = new HomePosition(playerName, homeName, pos);
		dao.createIfNotExists(homePosition);
	}

	public HomePosition getHome(String playerName, String homeName) throws SQLException {
		Map<String, Object> params = new HashMap<>();
		params.put("player_name", playerName);
		params.put("home_name", homeName);
		List<HomePosition> homePositions = dao.queryForFieldValues(params);
		if (homePositions != null && !homePositions.isEmpty()) return homePositions.get(0);
		return null;
	}

	public List<HomePosition> getAllHome(String playerName) throws SQLException {
		return dao.queryForEq("player_name", playerName);
	}

	public int removeHome(HomePosition position) throws SQLException {
		return dao.delete(position);
	}
}
