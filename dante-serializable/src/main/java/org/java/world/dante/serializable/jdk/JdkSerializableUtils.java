package org.java.world.dante.serializable.jdk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;

public final class JdkSerializableUtils {
	/**
	 * 序列化,List
	 */
	@SuppressWarnings("unchecked")
	public static <T> boolean writeObject(List<T> list, File file) {
		T[] array = (T[]) list.toArray();
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
			out.writeObject(array);
			out.flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 反序列化,List
	 */
	@SuppressWarnings("unchecked")
	public static <E> List<E> readObjectForList(File file) {
		E[] object;
		try (ObjectInputStream out = new ObjectInputStream(new FileInputStream(file))) {
			object = (E[]) out.readObject();
			return Arrays.asList(object);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
