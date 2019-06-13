package com.terminator.pzj.pictureklotski;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialise();
    }

    public void playGame(View view) {
        Intent intent = new Intent(this, LevelList.class);
        startActivity(intent);
    }

    public static void setLayout(View view, int x, int y) {
        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(x, y, 0, 0);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
        view.setLayoutParams(layoutParams);
    }

    private void initialise() {
        Button startButton = findViewById(R.id.start_button);
        Rect rect = new Rect();
        this.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int windowWidth = rect.width();
        int windowHeight = rect.height();
        setLayout(startButton, windowWidth / 3, windowHeight / 3 * 2);
    }
}
