package com.example.ratatouille_android.views;

import android.graphics.Color;
import android.os.Bundle;

import com.example.ratatouille_android.R;
import com.example.ratatouille_android.controllers.NoticesController;
import com.example.ratatouille_android.models.Avviso;
import com.example.ratatouille_android.models.User;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.ratatouille_android.databinding.ActivityAvvisiNascostiBinding;

import java.util.ArrayList;

public class AvvisiNascostiActivity extends AppCompatActivity {

    User loggedUser;
    HomeActivity homeActivity;
    NoticesController noticesController;
    View view;
    LinearLayout layout;
    ArrayList<Avviso> avviso = new ArrayList<Avviso>();
    float factor;

    public AvvisiNascostiActivity(HomeActivity homeActivity, User loggedUser){
        this.homeActivity = homeActivity;
        this.loggedUser = loggedUser;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notices, container, false);
        layout = view.findViewById(R.id.linear_layoutCard);
        factor = homeActivity.getResources().getDisplayMetrics().density;
        noticesController = new NoticesController(homeActivity, loggedUser);
        noticesController.getReadNoticeFromServer();
        noticesController.getHiddenNoticeFromServer();
        noticesController.getNoticeFromServer();



        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        noticesController.getHiddenNoticeFromServer();


    }

    private void generateCard(float factor, CardView card, String header, String textMessage, boolean isRead) {
        card.setLayoutParams(new LinearLayout.LayoutParams((int)(322 * factor), (int)(112 * factor)));
        card.setBackgroundColor(Color.parseColor("#FFFBF3"));

        ConstraintLayout constraintLayout = new ConstraintLayout(homeActivity);
        constraintLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        constraintLayout.setBackgroundColor(Color.parseColor("#FFFBF3"));
        card.addView(constraintLayout);

        generateHeaderTextCard(factor, constraintLayout, header);
        generateShowMessageCard(factor, constraintLayout, textMessage);
        generateHideButtonSpaceCard(factor, constraintLayout, isRead);



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

    private void generateHideButtonSpaceCard(float factor, ConstraintLayout constraintLayout, boolean isRead) {
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

        CheckBox read_or_not = new CheckBox(homeActivity);
        read_or_not.setId(R.id.read_or_not);
        if(isRead){
            read_or_not.setChecked(true);
        } else {
            read_or_not.setChecked(false);
        }
        ConstraintLayout.LayoutParams layout_837 = new ConstraintLayout.LayoutParams((int)(33 * factor),(int)(33 * factor));
        read_or_not.setLayoutParams(layout_837);
        constraintLayout.addView(read_or_not);
    }

    private void generateShowMessageCard(float factor, ConstraintLayout constraintLayout, String textMessage) {
        TextView show_message = new TextView(homeActivity);
        show_message.setId(R.id.show_message);
        show_message.setBackgroundResource(R.drawable.square_bottom_rounded);
        show_message.setPaddingRelative(0, 0, (int) (10/homeActivity.getResources().getDisplayMetrics().density), 0);
        show_message.setPadding(10,0,0,0);
        show_message.setText(textMessage);
        LinearLayout.LayoutParams layout_343 = new LinearLayout.LayoutParams((int)(242 * factor), (int)(93 * factor));
        layout_343.setMarginEnd(2);
        show_message.setLayoutParams(layout_343);
        constraintLayout.addView(show_message);
    }

    private void generateHeaderTextCard(float factor, ConstraintLayout constraintLayout, String header) {
        TextView header_message = new TextView(homeActivity);
        header_message.setId(R.id.header_message);
        header_message.setBackgroundResource(R.drawable.square_top_rounded);
        header_message.setText(header);
        header_message.setPadding(10,0,0,0);
        header_message.setTextColor(Color.parseColor("#FFFFFF"));
        LinearLayout.LayoutParams layout_566 = new LinearLayout.LayoutParams((int)(322 * factor), 0);
        header_message.setLayoutParams(layout_566);
        constraintLayout.addView(header_message);
    }

    public void updateB(Avviso avviso) {
        float factor = homeActivity.getResources().getDisplayMetrics().density;
        CardView card = new CardView(homeActivity);
        generateCard(factor, card, avviso.getAutore() + " " + avviso.getDataOra(), avviso.getTesto(), avviso.isRead());
        layout.addView(card);
    }
}