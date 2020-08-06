/*
* File: DivideByZero.java
* Author: John Kucera
* Date: January 21, 2020
* Purpose: This java program is meant to accompany P1GUI.java. It is a user
* defined checked exception that handles situations where expression input
* includes division by zero.
*/

// Constructor
public class DivideByZero extends Exception {
    public DivideByZero() {
        super();
    }
}