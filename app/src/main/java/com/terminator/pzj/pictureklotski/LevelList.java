package com.terminator.pzj.pictureklotski;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

public class LevelList extends AppCompatActivity {

    private Button level[] = new Button[4];
    public static final String EXTRA_MESSAGE = "com.terminator.pzj.pictureklotski.gameactivity.extra.MESSAGE";
    private String levelNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_list);
        initButton();
    }

    private void initButton() {

        level[0] = findViewById(R.id.level_one);
        level[1] = findViewById(R.id.level_two);
        level[2] = findViewById(R.id.level_three);
        level[3] = findViewById(R.id.level_four);
        Rect rect = new Rect();
        this.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int windowWidth = rect.width();
        int windowHeight = rect.height();
        for (int i = 0; i < 4; i++) {
            level[i].setWidth(windowWidth / 3);
            level[i].setHeight(windowHeight / 9);
            setLayout(level[i], windowWidth / 3, (2 * i) * windowHeight / 9 + windowHeight / 18);
        }
    }

    public static void setLayout(View view, int x, int y) {
        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(x, y, 0, 0);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
        view.setLayoutParams(layoutParams);
    }

    public void goToLevelOne(View view) {
        levelNumber = "1";
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(EXTRA_MESSAGE, levelNumber);
        startActivity(intent);
    }

    public void goToLevelTwo(View view) {
        levelNumber = "2";
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(EXTRA_MESSAGE, levelNumber);
        startActivity(intent);
    }

    public void goToLevelThree(View view) {
        levelNumber = "3";
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(EXTRA_MESSAGE, levelNumber);
        startActivity(intent);
    }

    public void goToLevelFour(View view) {
        levelNumber = "4";
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(EXTRA_MESSAGE, levelNumber);
        startActivity(intent);
    }
}
