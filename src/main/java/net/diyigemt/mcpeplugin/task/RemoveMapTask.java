package net.diyigemt.mcpeplugin.task;

import cn.nukkit.scheduler.PluginTask;
import net.diyigemt.mcpeplugin.MainPlugin;

import java.util.Map;

public class RemoveMapTask<K, V> extends PluginTask<MainPlugin> {

	private final Map<K, V> map;

	private final K key;

	private OnTaskRun<K, V> onTaskRun;

	public RemoveMapTask(MainPlugin owner, Map<K, V> map, K key) {
		super(owner);
		this.map = map;
		this.key = key;
	}

	public RemoveMapTask(MainPlugin owner, Map<K, V> map, K key, OnTaskRun<K, V> onRun) {
		super(owner);
		this.map = map;
		this.key = key;
		this.onTaskRun = onRun;
	}

	@Override
	public void onRun(int i) {
		map.remove(key);
		onTaskRun.onRun(map, key);
		cancel();
	}

	public interface OnTaskRun<K, V> {
		void onRun(Map<K, V> map, K key);
	}
}