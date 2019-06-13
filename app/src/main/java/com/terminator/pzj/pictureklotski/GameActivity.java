package com.terminator.pzj.pictureklotski;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    private TextView timerView;
    public static int TIMER_INDEX = 0;
    public static int COUNT_INDEX = 0;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private TextView mTvPuzzleMainCounts;
    private ImageButton klotski_button[] = new ImageButton[10];
    private int margin_left[] = new int [10];
    private int margin_top[] = new int[10];
    private int button_id[] = new int[10];
    private int button_width[] = new int[10];
    private int button_height[] = new int[10];
    private int windowWidth;
    private int windowHeight;
    private String levelNumber;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
       @Override
        public void handleMessage(Message msg) {
           switch (msg.what) {
               case 1:
                   TIMER_INDEX++;
                   timerView.setText("" + TIMER_INDEX + "秒");
                   break;
               default:
                   break;
           }
       }
    };

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        levelNumber = intent.getStringExtra(LevelList.EXTRA_MESSAGE);
        initGetButton();
        initialise();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initialise() {
        initTimer();
        initSteper();
        initSetButton(levelNumber);
        for(int i = 0; i < 10; i++) {
            klotski_button[i].setOnTouchListener(new View.OnTouchListener() {
                int lastX, lastY;
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            lastX = (int) event.getRawX();
                            lastY = (int) event.getRawY();
                            break;
                        case MotionEvent.ACTION_UP:
                            int distanceX = (int) event.getRawX() - lastX;
                            int distanceY = (int) event.getRawY() - lastY;
                            Log.d("test", " " + v.getId());
                            Log.d("test", distanceX + " " + distanceY);
                            checkCrash(distanceX, distanceY, v);
                            testSuccess();
                            break;
                    }
                    return false;
                }
            });
        }
    }

    private void initTimer() {
        mTimer = new Timer(true);
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 1;
                mHandler.sendMessage(msg);
            }
        };
        mTimer.schedule(mTimerTask, 0, 1000);
    }

    private void initSteper() {
        mTvPuzzleMainCounts = findViewById(
                R.id.steper_view);
        mTvPuzzleMainCounts.setText("" + COUNT_INDEX);
    }

    private void initGetButton() {
        klotski_button[0] = findViewById(R.id.button_huang_zhong);
        klotski_button[1] = findViewById(R.id.button_zhang_fei);
        klotski_button[2] = findViewById(R.id.button_cao_cao);
        klotski_button[3] = findViewById(R.id.button_zhao_yun);
        klotski_button[4] = findViewById(R.id.button_ma_chao);
        klotski_button[5] = findViewById(R.id.button_guan_yu);
        klotski_button[6] = findViewById(R.id.button_soldier_one);
        klotski_button[7] = findViewById(R.id.button_soldier_two);
        klotski_button[8] = findViewById(R.id.button_soldier_three);
        klotski_button[9] = findViewById(R.id.button_soldier_four);
        for (int i = 0; i < 10; i++) {
            button_id[i] = klotski_button[i].getId();
        }
        timerView = findViewById(R.id.timer_view);
        timerView.setText("0秒");
    }

    public static void setLayout(View view,int x,int y) {
        ViewGroup.MarginLayoutParams margin=new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(x, y, 0, 0);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
        view.setLayoutParams(layoutParams);
    }

    private void initSetButton(String levelNumber) {
        Rect rect = new Rect();
        this.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        TextView timeView = findViewById(R.id.time_view);
        TextView stepView = findViewById(R.id.step_view);
        TextView timerView = findViewById(R.id.timer_view);
        RelativeLayout relativeLayout = findViewById(R.id.button_layout);
        timeView.setHeight(rect.height() / 15);
        stepView.setHeight(rect.height() / 15);
        timerView.setHeight(rect.height() / 15);
        relativeLayout.getLayoutParams().height = rect.height() / 4 * 3;
        relativeLayout.getLayoutParams().width = rect.width() / 20 * 19;
        Log.d("test",  relativeLayout.getWidth() + " " + relativeLayout.getHeight());

        int initial_width = rect.width() / 20 * 19 / 4;
        int initial_height = rect.height() / 4 * 3 / 5;
        windowWidth = initial_width * 4;
        windowHeight = initial_height * 5;
        for(int i = 0; i < 10; i++) {
            if (i < 5) {
                button_height[i] = initial_height * 2;
            } else {
                button_height[i] = initial_height;
            }
        }
        for(int i = 0; i < 10; i++) {
            if (i == 2 || i == 5) {
                button_width[i] = initial_width * 2;
            } else {
                button_width[i] = initial_width;
            }
        }
        switch (levelNumber) {
            case "1":{
                for (int i = 0; i < 10; i++) {
                    if (i == 0 || i == 1) {
                        margin_left[i] = 0;
                    } else if (i == 2 || (i >= 5 && i <= 7)) {
                        margin_left[i] = initial_width;
                    } else if (i == 8 || i == 9) {
                        margin_left[i] = initial_width * 2;
                    } else {
                        margin_left[i] = initial_width * 3;
                    }
                }
                for (int i = 0; i < 10; i++) {
                    if (i == 0 || i == 2 || i == 3) {
                        margin_top[i] = 0;
                    } else if (i == 1 || i == 4 || i == 5) {
                        margin_top[i] = initial_height * 2;
                    } else if (i == 6 || i == 8) {
                        margin_top[i] = initial_height * 3;
                    } else {
                        margin_top[i] = initial_height * 4;
                    }
                }
                margin_left[5] = windowWidth / 4;
                margin_top[5] = 0;
                margin_left[2] = windowWidth / 4;
                margin_top[2] = windowHeight / 5 * 2;
                margin_left[6] = windowWidth / 4;
                margin_top[6] = windowHeight / 5;
                margin_left[7] = windowWidth / 2;
                margin_top[7] = windowHeight / 5;
                margin_left[8] = 0;
                margin_top[8] = windowHeight / 5 * 4;
                margin_left[9] = windowWidth / 4 * 3;
                setTitle("初出茅庐");
                break;
            }
            case "2":{
                for (int i = 0; i < 10; i++) {
                    if (i == 1 || i == 6 || i == 7) {
                        margin_left[i] = 0;
                    }
                    else if (i == 0 || i == 2 || i == 5) {
                        margin_left[i] = windowWidth / 4;
                    }
                    else if (i == 4) {
                        margin_left[i] = windowWidth / 2;
                    }
                    else{
                        margin_left[i] = windowWidth / 4 * 3;
                    }
                }
                for (int i = 0; i < 10; i++) {
                    if (i >= 1 && i <= 3) {
                        margin_top[i] = 0;
                    }
                    else if (i == 0 || i == 4){
                        margin_top[i] = windowHeight / 5 * 2;
                    }
                    else if (i == 6 || i == 8) {
                        margin_top[i] = windowHeight / 5 * 3;
                    }
                    else {
                        margin_top[i] = windowHeight / 5 * 4;
                    }
                }
                setTitle("虎口脱险");
                break;
            }
            case "3":{
                for (int i = 0; i < 10; i++) {
                    if (i == 0 || i == 1) {
                        margin_left[i] = 0;
                    } else if (i == 2 || (i >= 5 && i <= 7)) {
                        margin_left[i] = initial_width;
                    } else if (i == 8 || i == 9) {
                        margin_left[i] = initial_width * 2;
                    } else {
                        margin_left[i] = initial_width * 3;
                    }
                }
                for (int i = 0; i < 10; i++) {
                    if (i == 0 || i == 2 || i == 3) {
                        margin_top[i] = 0;
                    } else if (i == 1 || i == 4 || i == 5) {
                        margin_top[i] = initial_height * 2;
                    } else if (i == 6 || i == 8) {
                        margin_top[i] = initial_height * 3;
                    } else {
                        margin_top[i] = initial_height * 4;
                    }
                }
                setTitle("急中生智");
                break;
            }
            case "4":{
                for (int i = 0; i < 10; i++) {
                    if (i == 2 || i == 4 || i == 5) {
                        margin_left[i] = 0;
                    }
                    else if (i == 1 || i == 6 || i == 7) {
                        margin_left[i] = windowWidth / 2;
                    }
                    else if (i == 3 || i == 8 || i == 9) {
                        margin_left[i] = windowWidth / 4 * 3;
                    }
                    else
                    {
                        margin_left[i] = windowWidth / 4;
                    }
                }
                for (int i = 0; i < 10; i++) {
                    if (i >= 1 && i <= 3) {
                        margin_top[i] = 0;
                    }
                    else if (i == 5 || i == 6 || i == 8) {
                        margin_top[i] = windowHeight / 5 * 2;
                    }
                    else {
                        margin_top[i] = windowHeight / 5 * 3;
                    }
                }
                setTitle("破釜沉舟");
                break;
            }
            default:
                break;
        }

        for (int i = 0; i < 10; i++) {
            klotski_button[i].getLayoutParams().width = button_width[i];
            klotski_button[i].getLayoutParams().height = button_height[i];
            setLayout(klotski_button[i], margin_left[i], margin_top[i]);
        }
    }

    private boolean checkFill(int left, int top) {
        Log.d("checkfill", left + " " + top);
        for (int i = 0; i < 10; i++) {
            if (((left - margin_left[i] >= 0) && (left - margin_left[i] < button_width[i])) &&
                    ((top - margin_top[i] >= 0) && (top - margin_top[i] < button_height[i]))) {
                Log.d("checkfillTrue", i + "");
                return true;
            }
        }
        return false;
    }

    private void moveRight(int index, View v, int left, int top, int right, int bottom) {
        v.layout(left + windowWidth / 4, top, right + windowWidth / 4, bottom);
        margin_left[index] += windowWidth / 4;
        COUNT_INDEX++;
        mTvPuzzleMainCounts.setText("" + COUNT_INDEX);
    }

    private void stayStill(View v, int left, int top, int right, int bottom) {
        v.layout(left, top, right, bottom);
    }

    private void moveLeft(int index, View v, int left, int top, int right, int bottom) {
        v.layout(left - windowWidth / 4, top, right - windowWidth / 4, bottom);
        margin_left[index] -= windowWidth / 4;
        COUNT_INDEX++;
        mTvPuzzleMainCounts.setText("" + COUNT_INDEX);
    }

    private void moveDown(int index, View v, int left, int top, int right, int bottom) {
        v.layout(left, top + windowHeight / 5, right, bottom + windowHeight / 5);
        margin_top[index] += windowHeight / 5;
        COUNT_INDEX++;
        mTvPuzzleMainCounts.setText("" + COUNT_INDEX);
    }

    private void moveUp(int index, View v, int left, int top, int right, int bottom) {
        v.layout(left, top - windowHeight / 5, right, bottom - windowHeight / 5);
        margin_top[index] -= windowHeight / 5;
        COUNT_INDEX++;
        mTvPuzzleMainCounts.setText("" + COUNT_INDEX);
    }

    private void checkCrash(int distanceX, int distanceY, View v) {
        int index = -1;
        for(int i = 0; i < 10; i++) {
            if (v.getId() == button_id[i]) {
                index = i;
            }
        }
        Log.d("test", index + " ");
        int left = margin_left[index];
        int top = margin_top[index];
        int right = margin_left[index] + button_width[index];
        int bottom = margin_top[index] + button_height[index];

        if (distanceX >= Math.abs(distanceY)) {
            if (button_height[index] >windowHeight / 5) {
                if (checkFill(right, top) || checkFill(right, top + windowHeight / 5) || right == windowWidth) {
                    stayStill(v, left, top, right, bottom);
                }
                else {
                    moveRight(index, v, left, top, right, bottom);
                }
            }
            else {
                if (checkFill(right, top) || right == windowWidth) {
                    stayStill(v, left, top, right, bottom);
                }
                else {
                    moveRight(index, v, left, top, right, bottom);
                }
            }
        }
        else if (distanceX <= -Math.abs(distanceY)) {
            if (button_height[index] > windowHeight / 5) {
                if (checkFill(left - windowWidth / 4, top) ||
                        checkFill(left - windowWidth / 4, top + windowHeight / 5) ||
                        left == 0) {
                    stayStill(v, left, top, right, bottom);
                }
                else {
                    moveLeft(index, v, left, top, right, bottom);
                }
            }
            else {
                if(checkFill(left - windowWidth / 4, top) || left == 0) {
                    stayStill(v, left, top, right, bottom);
                }
                else {
                    moveLeft(index, v, left, top, right, bottom);
                }
            }
        }
        else if (distanceY >= Math.abs(distanceX)) {
            if (button_width[index] > windowWidth / 4) {
                if (checkFill(left, bottom) || checkFill(left + windowWidth / 4, bottom) || bottom == windowHeight) {
                    stayStill(v, left, top, right, bottom);
                }
                else {
                    moveDown(index, v, left, top, right, bottom);
                }
            }
            else {
                if (checkFill(left, bottom) || bottom == windowHeight) {
                    stayStill(v, left, top, right, bottom);
                }
                else {
                    moveDown(index, v, left, top, right, bottom);
                }
            }
        }
        else {
            if (button_width[index] > windowWidth / 4) {
                if(checkFill(left, top - windowHeight / 5) ||
                        checkFill(left + windowWidth / 4, top - windowHeight / 5) ||
                        top == 0) {
                    stayStill(v, left, top, right, bottom);
                }
                else {
                    moveUp(index, v, left, top, right, bottom);
                }
            }
            else {
                if (checkFill(left, top - windowHeight / 5) || top == 0) {
                    stayStill(v, left, top, right, bottom);
                }
                else {
                    moveUp(index, v, left, top, right, bottom);
                }
            }
        }
    }

    private void testSuccess() {
        int index = -1;
        for (int i = 0; i < 10; i++) {
            if (button_width[i] == windowWidth / 2 && button_height[i] == windowHeight / 5 * 2) {
                index = i;
            }
        }
        if (margin_left[index] == windowWidth / 4 && margin_top[index] == windowHeight / 5 * 3) {
            mTimer.cancel();
            mTimerTask.cancel();
            TIMER_INDEX = 0;
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("闯关成功")
                    .setMessage("曹操逃脱了蜀魏大军的围剿，期待来日东山再起")
                    .setPositiveButton("下一关", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (levelNumber) {
                                case "1": {
                                    levelNumber = "2";
                                    initialise();
                                    break;
                                }
                                case "2": {
                                    levelNumber = "3";
                                    initialise();
                                    break;
                                }
                                case "3": {
                                    levelNumber = "4";
                                    initialise();
                                    break;
                                }
                                case "4": {
                                    finish();
                                }
                            }
                        }
                    })
                    .setNeutralButton("返回关卡列表", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .create();
            alertDialog.show();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        mTimer.cancel();
        mTimerTask.cancel();
        COUNT_INDEX = 0;
        TIMER_INDEX = 0;
    }
}
