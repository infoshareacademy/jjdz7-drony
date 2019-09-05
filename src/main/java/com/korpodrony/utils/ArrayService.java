package com.korpodrony.utils;

import com.korpodrony.exceptions.ArrayLimitReachedException;
import com.korpodrony.exceptions.ElementIsNotUniqueException;
import com.korpodrony.exceptions.EmptyArrayException;
import com.korpodrony.exceptions.NoElementFoundException;

import java.util.Arrays;

public class ArrayService {
    private ArrayService() {
    }

    public static <T> T[] addToArray(T[] array, T element) throws ElementIsNotUniqueException {
        if (!isArrayEmpty(array) && getIndex(array, element) != -1)
            throw new ElementIsNotUniqueException();
        T[] resultArray = Arrays.copyOf(array, array.length + 1);
        resultArray[resultArray.length - 1] = element;
        return resultArray;
    }

    public static <T> T[] addToArray(T[] array, T element, int limit) throws ArrayLimitReachedException, ElementIsNotUniqueException {
        if (array.length == limit)
            throw new ArrayLimitReachedException();
        return addToArray(array, element);
    }

    private static <T> boolean isArrayEmpty(T[] array) {
        return array.length > 0 ? false : true;
    }

    public static <T> T[] removeFromArrayByIndex(T[] array, int index) throws EmptyArrayException, NoElementFoundException {
        if (isArrayEmpty(array))
            throw new EmptyArrayException();
        if (index < 0 && index > array.length - 1)
            throw new NoElementFoundException();
        T[] resultArray = Arrays.copyOf(array, array.length - 1);
        System.arraycopy(array, index + 1, resultArray, index, array.length - index - 1);
        return resultArray;
    }

    public static <T> int getIndex(T[] array, T element) {
        if (isArrayEmpty(array))
            return -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(element))
                return i;
        }
        return -1;
    }

    public static <T> T[] removeElement(T[] array, T element) throws NoElementFoundException, EmptyArrayException {
        int index = getIndex(array, element);
        if (index == -1)
            throw new NoElementFoundException();
        return removeFromArrayByIndex(array, index);
    }

    public static <T> T[] filtrArray(T[] array, T[] array2) {
        T[] resultArray = Arrays.copyOf(array, array.length);
        for (T element : array2) {
            try {
                resultArray = removeElement(resultArray, element);
            } catch (NoElementFoundException | EmptyArrayException e) {
            }
        }
        return resultArray;
    }

    public static <T> void printArray(T[] array) {
        for (T element : array) {
            System.out.println(element);
        }
    }
}
