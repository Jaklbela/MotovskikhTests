package com.release.motovskikh_tests;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import java.util.Random;

public class TestAdapter extends ArrayAdapter<String> {
    private final String[] testItems;
    private int selectedPosition = -1;
    private final Random random = new Random();

    public TestAdapter(@NonNull Context context, String[] items) {
        super(context, R.layout.adapter_tests, items);
        this.testItems = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_tests, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.test);
        textView.setText(testItems[position]);

        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.golos);
        if (typeface != null) {
            textView.setTypeface(typeface);
        }
        int colorTransparent = ContextCompat.getColor(getContext(), R.color.transparent);
        int colorText = ContextCompat.getColor(getContext(), R.color.text);
        int colorTextPressed = ContextCompat.getColor(getContext(), R.color.text_pressed);
        int colorBackgroundPressed = ContextCompat.getColor(getContext(), getRandomColor());

        convertView.setBackgroundColor(colorTransparent);
        textView.setTextColor(colorText);

        if (position == selectedPosition) {
            convertView.setBackgroundColor(colorBackgroundPressed);
            textView.setTextColor(colorTextPressed);
        } else {
            convertView.setBackgroundColor(colorTransparent);
            textView.setTextColor(colorText);
        }

        return convertView;
    }

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
        notifyDataSetChanged();
    }

    private int getRandomColor() {
        int[] colors = {R.color.red, R.color.green, R.color.yellow, R.color.blue, R.color.orange, R.color.magenta};
        int choice = random.nextInt(6);
        return colors[choice];
    }
}