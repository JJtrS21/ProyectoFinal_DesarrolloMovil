package com.fei.proyectofinal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class OutlinedTextView extends AppCompatTextView {

    public OutlinedTextView(Context context) {
        super(context);
    }

    public OutlinedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OutlinedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int colorOriginal = getCurrentTextColor();

        // Dibuja el contorno negro
        getPaint().setStyle(Paint.Style.STROKE);
        getPaint().setStrokeWidth(12f);
        setTextColor(Color.BLACK);
        super.onDraw(canvas);

        // Dibuja el relleno del texto encima
        getPaint().setStyle(Paint.Style.FILL);
        setTextColor(colorOriginal);
        super.onDraw(canvas);
    }
}
