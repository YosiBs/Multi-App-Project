package com.example.common;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Activity_PanelBase extends AppCompatActivity {

    private GameManager gm;
    private ShapeableImageView[] cardArray;
    private MaterialTextView panel_LBL_lvl;
    private MaterialButton start_BTN;
    private MaterialTextView panel_LBL_best_score;
    Handler handler = new Handler();
    public static final String BEST_SCORE ="BEST_SCORE";
    private int bestScore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_base);
        SharedPreferencesManager.init(getApplicationContext());
        findViews();
        initViews();
        gm = new GameManager();

    }

    private void findViews() {
        cardArray = new ShapeableImageView[]{
                findViewById(R.id.card_0),
                findViewById(R.id.card_1),
                findViewById(R.id.card_2),
                findViewById(R.id.card_3),
        };
        panel_LBL_lvl = findViewById(R.id.panel_LBL_lvl);
        start_BTN = findViewById(R.id.start_BTN);
        panel_LBL_best_score = findViewById(R.id.panel_LBL_best_score);
    }

    private void initViews() {
        start_BTN.setOnClickListener(v -> {
            if(gm.isStarted()) {
                gm = new GameManager();
            }
            start_BTN.setText("Reset");
            startGame();
        });
        for (int i = 0; i < cardArray.length; i++) {
            int finalI = i;
            cardArray[i].setOnClickListener(v -> {
                if(gm.isStarted()){
                    cardClickHandler(finalI);
                }
            });
        }
        bestScore = loadFromSharedPreferences();
        panel_LBL_best_score.setText(String.valueOf(bestScore));
    }

    private void cardClickHandler(int number) {
        ShapeableImageView card = cardArray[number];

        handler.postDelayed(() -> {
            // Highlight the card by scaling up
            card.animate().scaleX(1.2f).scaleY(1.2f).setDuration(250).withEndAction(() -> {
                handler.postDelayed(() -> {
                    // Revert the card size back to normal
                    card.animate().scaleX(1f).scaleY(1f).setDuration(250);
                }, 300); // Highlight duration
            });
        }, 50);
        if(gm.isStarted()){
            gm.addToUserPattern(number);
            checkAnswer(gm.getUserPattern().size() - 1);
        }
    }

    private void checkAnswer(int currentMatch) {
        if(gm.getGamePattern().get(currentMatch) == gm.getUserPattern().get(currentMatch)){
            //good
            if(gm.getGamePattern().size() == gm.getUserPattern().size()){
                handler.postDelayed(() -> {
                    playNextRound();
                }, 1500);
            }
        }else{
            gameOver();
        }

    }

    private void gameOver() {
        gm.setStarted(false);
        panel_LBL_lvl.setText("Game Over - lvl: " + gm.getCurrentLevel());

        if(gm.getCurrentLevel() > bestScore){
            bestScore = gm.getCurrentLevel();
            saveToSharedPreferences(bestScore);
            panel_LBL_best_score.setText(String.valueOf(bestScore));
        }
        gm = new GameManager();
    }

    private void startGame() {
        gm.setStarted(true);
        playNextRound();
    }

    private void playNextRound() {
        gm.getNextSequence();
        panel_LBL_lvl.setText("Level: " + gm.getCurrentLevel());
        showRoundPatternUI();


    }
    private void showRoundPatternUI() {
        ArrayList<Integer> gamePattern = gm.getGamePattern();

        for (int i = 0; i < gamePattern.size(); i++) {
            int cardIndex = gamePattern.get(i);
            ShapeableImageView card = cardArray[cardIndex];

            int delay = i * 1000; // 1 second interval

            handler.postDelayed(() -> {
                // Highlight the card by scaling up
                card.animate().scaleX(1.2f).scaleY(1.2f).setDuration(250).withEndAction(() -> {
                    handler.postDelayed(() -> {
                        // Revert the card size back to normal
                        card.animate().scaleX(1f).scaleY(1f).setDuration(250);
                    }, 500); // Highlight duration
                });
            }, delay);
        }
    }
    private void saveToSharedPreferences(int score) {
        SharedPreferencesManager.getInstance().putInt(BEST_SCORE, score);
    }
    public int loadFromSharedPreferences(){
        return SharedPreferencesManager.getInstance().getInt(BEST_SCORE, 0);
    }

}