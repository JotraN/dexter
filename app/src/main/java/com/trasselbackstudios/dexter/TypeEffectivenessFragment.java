package com.trasselbackstudios.dexter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trasselbackstudios.dexter.data.TypeEffectivenessItem;

public class TypeEffectivenessFragment extends Fragment {

    public TypeEffectivenessFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_type_effectiveness, container, false);

        TextView weakness = (TextView) rootView.findViewById(R.id.text_view_weakness);
        TextView normal = (TextView) rootView.findViewById(R.id.text_view_normal);
        TextView resistance = (TextView) rootView.findViewById(R.id.text_view_resistance);
        TextView immune = (TextView) rootView.findViewById(R.id.text_view_immune);

        TypeEffectivenessItem typeEffectiveness = new TypeEffectivenessItem(rootView.getContext(), getArguments().getString(Intent.EXTRA_TEXT));

        if (((ActionBarActivity) getActivity()).getSupportActionBar() != null)
            ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(typeEffectiveness.name);

        weakness.setText(Html.fromHtml(typeEffectiveness.getWeakness()));
        normal.setText(Html.fromHtml(typeEffectiveness.getNormal()));
        resistance.setText(Html.fromHtml(typeEffectiveness.getResistance()));
        immune.setText(Html.fromHtml(typeEffectiveness.getImmune()));
        if(typeEffectiveness.getWeakness().length() == 0)
            weakness.setText("None");
        if(typeEffectiveness.getNormal().length() == 0)
            normal.setText("None");
        if(typeEffectiveness.getResistance().length() == 0)
            resistance.setText("None");
        if(typeEffectiveness.getImmune().length() == 0)
            immune.setText("None");
        return rootView;
    }
}
