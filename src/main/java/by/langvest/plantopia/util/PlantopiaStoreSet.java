package by.langvest.plantopia.util;

import com.google.common.collect.Maps;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

public class PlantopiaStoreSet<T> {
	private final HashMap<String, T> store;

	public PlantopiaStoreSet() {
		store = Maps.newLinkedHashMap();
	}

	@Contract(" -> new")
	public static <T> @NotNull PlantopiaStoreSet<T> newStoreSet() {
		return new PlantopiaStoreSet<>();
	}

	public List<T> getAll() {
		return store.values().stream().toList();
	}

	public List<T> findAll(Predicate<T> predicate) {
		return store.values().stream().filter(predicate).toList();
	}

	@Nullable
	public T find(Predicate<T> predicate) {
		for(T value : store.values()) if(predicate.test(value)) return value;
		return null;
	}

	@Nullable
	public T getById(String id) {
		return store.get(id);
	}

	public void add(String id, T value) {
		if(store.containsKey(id)) throw new IllegalArgumentException("Duplicate adding " + value);
		store.put(id, value);
	}
}