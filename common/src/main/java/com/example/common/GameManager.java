package com.example.common;

import java.util.ArrayList;
import java.util.Random;

public class GameManager {

    private int currentLevel;
    private ArrayList<Integer> gamePattern;
    private ArrayList<Integer> userPattern;
    private boolean started;



    public GameManager() {
        this.currentLevel = 0;
        this.gamePattern = new ArrayList<>();
        this.userPattern = new ArrayList<>();
        this.started = false;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }



    public GameManager setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
        return this;
    }

    public ArrayList<Integer> getGamePattern() {
        return gamePattern;
    }

    public GameManager setGamePattern(ArrayList<Integer> gamePattern) {
        this.gamePattern = gamePattern;
        return this;
    }

    public ArrayList<Integer> getUserPattern() {
        return userPattern;
    }

    public GameManager setUserPattern(ArrayList<Integer> userPattern) {
        this.userPattern = userPattern;
        return this;
    }
    public void addToUserPattern(int number){
        this.userPattern.add(number);
    }
    public boolean isStarted() {
        return started;
    }

    public GameManager setStarted(boolean started) {
        this.started = started;
        return this;
    }
    public void getNextSequence(){
        this.userPattern.clear();
        Random random = new Random();
        int randomNumber = random.nextInt(4);
        this.gamePattern.add(randomNumber);
        this.currentLevel++;
    }
}
