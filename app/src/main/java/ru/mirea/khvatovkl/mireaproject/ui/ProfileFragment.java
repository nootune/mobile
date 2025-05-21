package ru.mirea.khvatovkl.mireaproject.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ru.mirea.khvatovkl.mireaproject.R;

public class ProfileFragment extends Fragment {

    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_NAME = "key_name";
    private static final String KEY_SURNAME = "key_surname";
    private static final String KEY_GROUP = "key_group";

    private EditText EditTextName;
    private EditText EditTextSurname;
    private EditText EditTextGroupMirea;
    private Button buttonSave;
    private Button buttonLoad;

    private SharedPreferences sharedPref;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditTextName = view.findViewById(R.id.EditTextname);
        EditTextSurname = view.findViewById(R.id.EditTextsurname);
        EditTextGroupMirea = view.findViewById(R.id.EditTextgroupmirea);
        buttonSave = view.findViewById(R.id.buttonsave);
        buttonLoad = view.findViewById(R.id.buttonload);

        sharedPref = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        buttonSave.setOnClickListener(v -> {
            savePreferences();
            Toast.makeText(getContext(), "Данные сохранены", Toast.LENGTH_LONG).show();
        });

        buttonLoad.setOnClickListener(v -> {
            loadPreferences();
            Toast.makeText(getContext(), "Данные загружены", Toast.LENGTH_SHORT).show();
        });
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_NAME, EditTextName.getText().toString());
        editor.putString(KEY_SURNAME, EditTextSurname.getText().toString());
        editor.putString(KEY_GROUP, EditTextGroupMirea.getText().toString());
        editor.apply();
    }

    private void loadPreferences() {
        EditTextName.setText(sharedPref.getString(KEY_NAME, ""));
        EditTextSurname.setText(sharedPref.getString(KEY_SURNAME, ""));
        EditTextGroupMirea.setText(sharedPref.getString(KEY_GROUP, ""));
    }
}