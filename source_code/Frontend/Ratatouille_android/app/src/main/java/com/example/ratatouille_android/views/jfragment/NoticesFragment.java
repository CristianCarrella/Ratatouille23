package com.example.ratatouille_android.views.jfragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ratatouille_android.R;
import com.example.ratatouille_android.views.HomeActivity;


public class NoticesFragment extends Fragment {
    HomeActivity homeActivity;

    public NoticesFragment(HomeActivity homeActivity){
        this.homeActivity = homeActivity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notices, container, false);

        LinearLayout layout = view.findViewById(R.id.linear_layoutCard);
        float factor = homeActivity.getResources().getDisplayMetrics().density;

        CardView card = new CardView(homeActivity);
        CardView card2 = new CardView(homeActivity);
        CardView card3 = new CardView(homeActivity);
        CardView card4 = new CardView(homeActivity);
        generateCard(factor, card);
        generateCard(factor, card2);
        generateCard(factor, card3);
        generateCard(factor, card4);

        layout.addView(card);
        layout.addView(card2);
        layout.addView(card3);
        layout.addView(card4);
        return view;
    }

    private void generateCard(float factor, CardView card) {
        card.setLayoutParams(new LinearLayout.LayoutParams((int)(322 * factor), (int)(112 * factor)));

        ConstraintLayout constraintLayout = new ConstraintLayout(homeActivity);
        constraintLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        constraintLayout.setBackgroundColor(Color.parseColor("#FFFBF3"));
        card.addView(constraintLayout);

        TextView header_message = new TextView(homeActivity);
        header_message.setId(R.id.header_message);
        header_message.setBackgroundResource(R.drawable.square_top_rounded);
        header_message.setText("TextView");
        header_message.setPadding(10,0,0,0);
        header_message.setTextColor(Color.parseColor("#FFFFFF"));
        LinearLayout.LayoutParams layout_566 = new LinearLayout.LayoutParams((int)(322 * factor), 0);
        header_message.setLayoutParams(layout_566);
        constraintLayout.addView(header_message);

        TextView show_message = new TextView(homeActivity);
        show_message.setId(R.id.show_message);
        show_message.setBackgroundResource(R.drawable.square_bottom_rounded);
        show_message.setPaddingRelative(0, 0, (int) (10/homeActivity.getResources().getDisplayMetrics().density), 0);
        show_message.setPadding(10,0,0,0);
        show_message.setText("TextView");
        LinearLayout.LayoutParams layout_343 = new LinearLayout.LayoutParams((int)(242 * factor), (int)(93 * factor));
        layout_343.setMarginEnd(2);
        show_message.setLayoutParams(layout_343);
        constraintLayout.addView(show_message);

        TextView spacing = new TextView(homeActivity);
        spacing.setId(R.id.spacing);
        spacing.setBackgroundResource(R.drawable.square_bottom_rounded2);
        LinearLayout.LayoutParams layout_274 = new LinearLayout.LayoutParams((int)(81 * factor), (int)(93 * factor));
        spacing.setLayoutParams(layout_274);
        constraintLayout.addView(spacing);

        ImageView hide_button = new ImageView(homeActivity);
        hide_button.setId(R.id.hide_button);
        hide_button.setImageResource(R.drawable.nascondi_button);
        ConstraintLayout.LayoutParams layout_325 = new ConstraintLayout.LayoutParams((int)(80 * factor), (int)(40 * factor));
        hide_button.setLayoutParams(layout_325);
        constraintLayout.addView(hide_button);

        TextView read_or_not = new TextView(homeActivity);
        read_or_not.setId(R.id.read_or_not);
        read_or_not.setBackgroundResource(R.drawable.circle_green);
        ConstraintLayout.LayoutParams layout_837 = new ConstraintLayout.LayoutParams((int)(10 * factor),(int)(10 * factor));
        read_or_not.setLayoutParams(layout_837);
        constraintLayout.addView(read_or_not);

        ConstraintSet set = new ConstraintSet();
        set.clone(constraintLayout);
        set.connect(R.id.header_message, ConstraintSet.BOTTOM, R.id.show_message, ConstraintSet.TOP);
        set.connect(R.id.header_message, ConstraintSet.END, R.id.layout_constraint, ConstraintSet.END);
        set.connect(R.id.header_message, ConstraintSet.START, R.id.layout_constraint, ConstraintSet.START);
        set.connect(R.id.header_message, ConstraintSet.TOP, R.id.layout_constraint, ConstraintSet.TOP);

        set.connect(R.id.show_message, ConstraintSet.BOTTOM, R.id.layout_constraint, ConstraintSet.BOTTOM);
        set.connect(R.id.show_message, ConstraintSet.END, R.id.hide_button, ConstraintSet.START);
        set.connect(R.id.show_message, ConstraintSet.START, R.id.layout_constraint, ConstraintSet.START);
        set.connect(R.id.show_message, ConstraintSet.TOP, R.id.header_message, ConstraintSet.BOTTOM);

        set.connect(R.id.spacing, ConstraintSet.BOTTOM, R.id.layout_constraint, ConstraintSet.BOTTOM);
        set.connect(R.id.spacing, ConstraintSet.END, R.id.layout_constraint, ConstraintSet.END);
        set.connect(R.id.spacing, ConstraintSet.START, R.id.show_message, ConstraintSet.END);
        set.connect(R.id.spacing, ConstraintSet.TOP, R.id.header_message, ConstraintSet.BOTTOM);

        set.connect(R.id.hide_button, ConstraintSet.BOTTOM, R.id.spacing, ConstraintSet.BOTTOM);
        set.connect(R.id.hide_button, ConstraintSet.END, R.id.spacing, ConstraintSet.END);
        set.connect(R.id.hide_button, ConstraintSet.START, R.id.spacing, ConstraintSet.START);
        set.connect(R.id.hide_button, ConstraintSet.TOP, R.id.spacing, ConstraintSet.TOP);

        set.connect(R.id.read_or_not, ConstraintSet.BOTTOM, R.id.spacing, ConstraintSet.BOTTOM);
        set.connect(R.id.read_or_not, ConstraintSet.END, R.id.spacing, ConstraintSet.END);
        set.connect(R.id.read_or_not, ConstraintSet.START, R.id.spacing, ConstraintSet.START);
        set.connect(R.id.read_or_not, ConstraintSet.TOP, R.id.hide_button, ConstraintSet.BOTTOM);

        set.applyTo(constraintLayout);
    }
}