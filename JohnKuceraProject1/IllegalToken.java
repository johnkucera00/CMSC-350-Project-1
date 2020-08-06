/*
* File: IllegalToken.java
* Author: John Kucera
* Date: January 21, 2020
* Purpose: This java program is meant to accompany P1GUI.java. It is a user
* defined checked exception that handles situations where expression input
* includes characters that are not among 0 to 9, +, -, *, /, (, ).
*/

// Constructor
public class IllegalToken extends Exception {
    public IllegalToken() {
        super();
    }
}