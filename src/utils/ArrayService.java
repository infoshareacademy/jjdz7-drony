package utils;

import exceptions.ElementIsNotUnique;
import exceptions.NoElementFound;

import java.util.Arrays;

public class ArrayService {
    private ArrayService() {
    }

    public static <T> T[] addToArray(T[] array, T element) throws ElementIsNotUnique {
        if (!isArrayEmpty(array) && getIndex(array, element) != -1)
            throw new ElementIsNotUnique();
        T[] resultArray = Arrays.copyOf(array, array.length + 1);
        for (int i = 0; i < array.length; i++) {
            resultArray[i] = array[i];
        }
        resultArray[resultArray.length - 1] = element;
        return resultArray;
    }

    public static <T> boolean isArrayEmpty(T[] array) {
        return array.length > 0 ? false : true;
    }

    private static <T> T[] removeFromArrayByIndex(T[] array, int index) {
        if (isArrayEmpty(array))
            return array;
        T[] resultArray = Arrays.copyOf(array, array.length - 1);
        int i = 0;
        for (; i < index; i++) {
            resultArray[i] = array[i];
        }
        for (int j = index + 1; j < array.length; j++) {
            resultArray[i++] = array[j];
        }
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

    public static <T> T[] removeElement(T[] array, T element) throws NoElementFound {
        int index = getIndex(array, element);
        if (index == -1)
            throw new NoElementFound();
        return removeFromArrayByIndex(array, index);
    }

    public static <T> void printArray(T[] array) {
        for (T element : array) {
            System.out.println(element);
        }
    }
}
