package com.epam.dziashko.aliaksei.materialdemo.adapter;


/**
 * @author Aliaksandr_Litskevic
 * @created 2/8/14.
 */
public interface Function<T1, T> {
    T apply(T1 inputValue);
}
