package com.example.scele.movielab.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scele.movielab.R;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

public class SettingsFragment extends PreferenceFragment
        implements Preference.OnPreferenceChangeListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

        Preference nameSurnamePref = findPreference("name_surname");
        Preference emailPref     = findPreference("email");
        Preference passwordPref = findPreference("password");
        Preference favoriesPref =findPreference("FavoritesListEnabled");
        Preference watchListPref =findPreference("WatchListEnabled");

        nameSurnamePref.setOnPreferenceChangeListener(this);
        emailPref.setOnPreferenceChangeListener(this);
        passwordPref.setOnPreferenceChangeListener(this);

        SharedPreferences name_prefs =  PreferenceManager
                .getDefaultSharedPreferences(this.getActivity().getApplicationContext());
        onPreferenceChange(nameSurnamePref, name_prefs.getString(nameSurnamePref.getKey(),""));

        SharedPreferences email_prefs = PreferenceManager
                .getDefaultSharedPreferences(this.getActivity().getApplicationContext());
        onPreferenceChange(emailPref, email_prefs.getString(emailPref.getKey(),""));

        SharedPreferences password_prefs = PreferenceManager
                .getDefaultSharedPreferences(this.getActivity().getApplicationContext());
        onPreferenceChange(passwordPref, password_prefs.getString(passwordPref.getKey(),""));

        SharedPreferences fvt_pref = PreferenceManager
                .getDefaultSharedPreferences(this.getActivity().getApplicationContext());
        onPreferenceChange(favoriesPref, fvt_pref.getBoolean(favoriesPref.getKey(), true));

        SharedPreferences wl_pref = PreferenceManager
                .getDefaultSharedPreferences(this.getActivity().getApplicationContext());
        onPreferenceChange(watchListPref, wl_pref.getBoolean(watchListPref.getKey(), true));



    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {

        String stringValue = value.toString();

        if (preference instanceof ListPreference) {

            ListPreference listPreference = (ListPreference) preference;
            int            prefIndex      = listPreference.findIndexOfValue(stringValue);

            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else {
            preference.setSummary(stringValue);
        }
        return true;
    }
}
