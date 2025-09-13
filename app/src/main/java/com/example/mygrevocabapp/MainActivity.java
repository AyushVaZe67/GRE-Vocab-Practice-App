package com.example.mygrevocabapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    TextView wordText, meaningText;
    RadioButton changeUI, resetUI;
    Button nextBtn, prevBtn, showMeaningBtn;
    LinearLayout bgLayout;

    ArrayList<String[]> vocabList;
    int currentIndex = 0;
    boolean isMeaningVisible = false;
    private boolean isDarkMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        wordText = findViewById(R.id.wordText);
        meaningText = findViewById(R.id.meaningText);
        nextBtn = findViewById(R.id.nextBtn);
        prevBtn = findViewById(R.id.prevBtn);
        showMeaningBtn = findViewById(R.id.showMeaningBtn);
        changeUI = findViewById(R.id.changeUI);
        resetUI = findViewById(R.id.resetUI);
        bgLayout = findViewById(R.id.bgLayout);

        // Load vocabulary
        vocabList = VocabData.getVocabList();
        Collections.shuffle(vocabList);

        showWord(currentIndex);

        // Setup theme toggle
        changeUI.setOnCheckedChangeListener(null);
        changeUI.setChecked(isDarkMode);
        changeUI.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                resetUI.setChecked(false);
                isDarkMode = true;
                applyTheme();
            }
        });

        // Setup reset toggle
        resetUI.setOnCheckedChangeListener(null);
        resetUI.setChecked(false);
        resetUI.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                changeUI.setChecked(false);
                isDarkMode = false;
                applyTheme();
            }
        });

        // Button listeners
        nextBtn.setOnClickListener(v -> {
            if (currentIndex < vocabList.size() - 1) {
                currentIndex++;
                showWord(currentIndex);
            }
        });

        prevBtn.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                showWord(currentIndex);
            }
        });

        showMeaningBtn.setOnClickListener(v -> {
            isMeaningVisible = !isMeaningVisible;
            meaningText.setVisibility(isMeaningVisible ? View.VISIBLE : View.INVISIBLE);
            if (isMeaningVisible) {
                meaningText.setText(vocabList.get(currentIndex)[1]);
            }
        });
    }

    private void showWord(int index) {
        String[] wordData = vocabList.get(index);
        wordText.setText(wordData[0]);
        meaningText.setVisibility(View.INVISIBLE);
        isMeaningVisible = false;
    }

    private void applyTheme() {
        int bgColor = isDarkMode ? Color.BLACK : Color.WHITE;
        int textColor = isDarkMode ? Color.WHITE : Color.BLACK;
        int buttonBgColor = isDarkMode ? Color.DKGRAY : Color.parseColor("#6200EE");

        // Apply colors to layout and text
        bgLayout.setBackgroundColor(bgColor);
        wordText.setTextColor(textColor);
        meaningText.setTextColor(textColor);
        changeUI.setTextColor(textColor);
        resetUI.setTextColor(textColor);

        // Style buttons
        styleButton(nextBtn, textColor, buttonBgColor);
        styleButton(prevBtn, textColor, buttonBgColor);
        styleButton(showMeaningBtn, textColor, buttonBgColor);
    }

    private void styleButton(Button button, int textColor, int bgColor) {
        button.setTextColor(textColor);
        button.setBackgroundColor(bgColor);
    }
}