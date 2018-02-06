package com.util.categories;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;


/**
 * The type CollectionUtil.
 */
public class CollectionUtil {

    /**
     * New array list array list.
     *
     * @param <E> the type parameter
     * @return the array list
     */
    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<E>();
    }

    /**
     * New array list array list.
     *
     * @param <E>          the type parameter
     * @param startingSize the starting size
     * @return the array list
     */
    public static <E> ArrayList<E> newArrayList(int startingSize) {
        return new ArrayList<E>(startingSize);
    }

    /**
     * New hash map hash map.
     *
     * @param <K> the type parameter
     * @param <V> the type parameter
     * @return the hash map
     */
    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<K, V>();
    }

    /**
     * New hash set hash set.
     *
     * @param <E> the type parameter
     * @return the hash set
     */
    public static <E> HashSet<E> newHashSet() {
        return new HashSet<E>();
    }

    /**
     * New hash table hashtable.
     *
     * @param <K> the type parameter
     * @param <V> the type parameter
     * @return the hashtable
     */
    public static <K, V> Hashtable<K, V> newHashTable() {
        return new Hashtable<K, V>();
    }

    /**
     * New enum map enum map.
     *
     * @param <K>     the type parameter
     * @param <V>     the type parameter
     * @param keyType the key type
     * @return the enum map
     */
    public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(Class<K> keyType) {
        return new EnumMap<K, V>(keyType);
    }

    /**
     * New enum set enum set.
     *
     * @param <E>     the type parameter
     * @param keyType the key type
     * @return the enum set
     */
    public static <E extends Enum<E>> EnumSet<E> newEnumSet(Class<E> keyType) {
        return EnumSet.noneOf(keyType);
    }

    /**
     * Remove duplicates java . util . collection.
     *
     * @param c the c
     * @return the java . util . collection
     */
    public java.util.Collection removeDuplicates(java.util.Collection c) {
        java.util.Collection result = new ArrayList();

        for (Object o : c) {
            if (!result.contains(o)) {
                result.add(o);
            }
        }

        return result;
    }

    /**
     * Is a collection boolean.
     *
     * @param <T>  the type parameter
     * @param list the list
     * @return the boolean
     */
    public static <T> boolean isACollection(T list) {
        if (list == null) return false;

        boolean bool = false;

        try {
            T t = (T) list.getClass();
            T s = (T) ((List<?>) list).get(0);
            bool = true;
        } catch (ClassCastException e) {
        }

        return bool || list.getClass().isArray();

    }
}
