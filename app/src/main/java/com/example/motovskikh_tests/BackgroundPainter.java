package com.example.motovskikh_tests;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.Random;
public class BackgroundPainter extends View {
    private Paint paint;
    private Random random;

    public BackgroundPainter(Context context) {
        super(context);
        init();
    }

    public BackgroundPainter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BackgroundPainter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        random = new Random();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        for (int i = 0; i < 10; i++) {
            drawRandomShape(canvas, width, height);
        }
    }

    private void drawRandomShape(Canvas canvas, int width, int height) {

        float x = random.nextInt(width);
        float y = random.nextInt(height);

        float size = 100 + random.nextInt(500);

        paint.setColor(getResources().getColor(getRandomColor()));

        int shapeType = random.nextInt(3);
        switch (shapeType) {
            case 0:
                canvas.drawCircle(x, y, size / 2, paint);
                break;
            case 1:
                canvas.drawRect(x, y, x + size, y + size, paint);
                break;
            case 2:
                Path path = new Path();
                path.moveTo(x, y);
                path.lineTo(x - size / 1.73f, y + size);
                path.lineTo(x + size / 1.73f, y + size);
                path.close();
                canvas.drawPath(path, paint);
                break;
        }
    }

    private int getRandomColor() {
        int[] colors = {R.color.red, R.color.green, R.color.yellow, R.color.blue};
        int choice = random.nextInt(4);
        return colors[choice];
    }
}
