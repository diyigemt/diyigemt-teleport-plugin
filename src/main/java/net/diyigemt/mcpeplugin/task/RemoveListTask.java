package net.diyigemt.mcpeplugin.task;

import cn.nukkit.scheduler.PluginTask;
import net.diyigemt.mcpeplugin.MainPlugin;

import java.util.List;

public class RemoveListTask<E> extends PluginTask<MainPlugin> {

	private final List<E> list;

	private final E value;

	private OnTaskRun<E> onTaskRun;

	public RemoveListTask(MainPlugin owner, List<E> list,E value) {
		super(owner);
		this.list = list;
		this.value = value;
	}

	public RemoveListTask(MainPlugin owner, List<E> list,E value, OnTaskRun<E> onRun) {
		super(owner);
		this.list = list;
		this.value = value;
		this.onTaskRun = onRun;
	}

	@Override
	public void onRun(int i) {
		list.remove(value);
		onTaskRun.onRun(list, value);
		cancel();
	}

	public interface OnTaskRun<E> {
		void onRun(List<E> list, E value);
	}
}