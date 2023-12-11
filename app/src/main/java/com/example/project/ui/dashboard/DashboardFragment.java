package com.example.project.ui.dashboard;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.DashboardAdapter;
import com.example.project.R;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private static final String PREFS_NAME = "MyPrefs";
    private static final String BACKGROUND_COLOR_KEY = "BackgroundColor";
    private static final String TEXT_COLOR_KEY = "TextColor";

    private ConstraintLayout parentLayout; // Reference to the parent ConstraintLayout
    private RecyclerView recyclerView;
    private DashboardAdapter adapter;
    private SharedPreferences sharedPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        parentLayout = root.findViewById(R.id.parentLayout);
        recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        List<String> titles = new ArrayList<>();
        titles.add("Mission");
        titles.add("Vision");

        List<String> descriptions = new ArrayList<>();
        descriptions.add("Description for Mission");
        descriptions.add("Description for Vision");

        adapter = new DashboardAdapter(titles, descriptions);
        recyclerView.setAdapter(adapter);

        Button colorPickerButton = root.findViewById(R.id.colorPickerButton);
        colorPickerButton.setOnClickListener(v -> showColorPicker());

        Button textColorPickerButton = root.findViewById(R.id.textColorPickerButton);
        textColorPickerButton.setOnClickListener(v -> showTextColorPicker());

        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, 0);
        int savedBackgroundColor = sharedPreferences.getInt(BACKGROUND_COLOR_KEY, Color.WHITE);
        int savedTextColor = sharedPreferences.getInt(TEXT_COLOR_KEY, Color.BLACK);

        parentLayout.setBackgroundColor(savedBackgroundColor); // Set the saved background color
        adapter.setTextColor(savedTextColor); // Set the saved text color for RecyclerView

        return root;
    }

    private void showColorPicker() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Select Background Color");

        String[] colors = {"White", "Light Gray", "Dark Gray", "Black"}; // Add more colors as needed
        final int[] colorValues = {Color.WHITE, Color.LTGRAY, Color.DKGRAY, Color.BLACK}; // Corresponding color values
        builder.setItems(colors, (dialog, which) -> {
            int color = colorValues[which];
            parentLayout.setBackgroundColor(color);
            saveColorToPreferences(color, BACKGROUND_COLOR_KEY);
        });

        builder.show();
    }

    private void showTextColorPicker() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Select Text Color");

        String[] colors = {"Black", "Red", "Green", "Blue"}; // Add more colors as needed
        final int[] colorValues = {Color.BLACK, Color.RED, Color.GREEN, Color.BLUE}; // Corresponding color values
        builder.setItems(colors, (dialog, which) -> {
            int color = colorValues[which];
            adapter.setTextColor(color);
            saveColorToPreferences(color, TEXT_COLOR_KEY);
        });

        builder.show();
    }

    private void saveColorToPreferences(int color, String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, color);
        editor.apply();
    }
}
