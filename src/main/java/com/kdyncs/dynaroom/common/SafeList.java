/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdyncs.dynaroom.common;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author peter
 */
public class SafeList {
    public static <T> List<T> get(List<T> other) {
        return other == null ? Collections.emptyList() : other;
    }
}
