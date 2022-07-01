package com.matrix_maeny.bottlespinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView bottleView;
    private ConstraintLayout constraintLayout;

    float layoutWidth = 0f;
    float layoutHeight = 0f;

    final Random random = new Random();
    private int stoppedDegrees;
    private boolean spin = false;

    private SoundPool soundPool;
    private int soundSpinId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottleView = findViewById(R.id.bottleView);
        constraintLayout = findViewById(R.id.contraintLayout);

        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        soundSpinId = soundPool.load(getApplicationContext(), R.raw.spin, 1);

        layoutHeight = constraintLayout.getMeasuredHeight();
        layoutWidth = constraintLayout.getMeasuredWidth();

        int dim = (int) Math.min(layoutHeight, layoutWidth);


        dim = dim - 100;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(dim, dim);

        bottleView.setLayoutParams(layoutParams);


    }


    // called when we click on the bottle
    public void BottleViewOnclick(View view) {

        if (!spin) {

            soundPool.play(soundSpinId, 1.0f, 1.0f, 0, 0, 1.0f);
            int num = random.nextInt(5000);

            float pX = bottleView.getWidth() / 2.0f;
            float pY = bottleView.getHeight() / 2.0f;


            Animation animation = new RotateAnimation(stoppedDegrees, num, pX, pY);
            animation.setDuration(2000);

            animation.setFillAfter(true);

            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    spin = true;
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    spin = false;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            stoppedDegrees = num;

            bottleView.startAnimation(animation);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(MainActivity.this, AboutActivity.class));

        return super.onOptionsItemSelected(item);
    }
}