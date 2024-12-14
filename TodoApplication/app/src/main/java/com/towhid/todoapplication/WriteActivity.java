package com.towhid.todoapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.towhid.todoapplication.api.WriteApi;
import com.towhid.todoapplication.apiClient.ApiClient;
import com.towhid.todoapplication.model.TodoModel;
import java.util.Calendar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextDescription, date;
    private Spinner spinnerTodotype;
    private RadioGroup radioGroupPriority;
    private Button btnSubmit;
    private Switch switchNightMode;  // Switch for night mode
    private SharedPreferences sharedPreferences; // To store the mode preference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Get saved user preference (if night mode is on or off)
        sharedPreferences = getSharedPreferences("ThemePref", MODE_PRIVATE);
        boolean isNightModeOn = sharedPreferences.getBoolean("isNightModeOn", false);

        // Apply the user's selected mode before the activity loads
        if (isNightModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        // Initialize views
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        spinnerTodotype = findViewById(R.id.spinnerTodotype);
        date = findViewById(R.id.date);
        radioGroupPriority = findViewById(R.id.radioGroupPriority);
        btnSubmit = findViewById(R.id.btnSubmit);
        switchNightMode = findViewById(R.id.switchNightMode);

        // Set up the spinner (dropdown) with values
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.todo_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTodotype.setAdapter(adapter);

        // Set date picker on EditText click
        date.setOnClickListener(v -> showDatePickerDialog());

        // Set the switch to match the current mode
        switchNightMode.setChecked(isNightModeOn);

        // Switch listener for Night Mode toggle
        switchNightMode.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                saveModePreference(true);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                saveModePreference(false);
            }
        });

        // Handle submit button click
        btnSubmit.setOnClickListener(v -> submitTodo());
    }

    private void saveModePreference(boolean isNightModeOn) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isNightModeOn", isNightModeOn);
        editor.apply();
    }

    private void showDatePickerDialog() {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, (view, selectedYear, selectedMonth, selectedDay) -> {
            // Set the selected date in the EditText
            String dateString = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
            date.setText(dateString);
        }, year, month, day);

        // Show the dialog
        datePickerDialog.show();
    }

    private void submitTodo() {
        // Retrieve values from inputs
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String selectedTodotype = spinnerTodotype.getSelectedItem().toString();
        String selectedDate = date.getText().toString().trim();

        // Get selected priority from RadioGroup
        int selectedPriorityId = radioGroupPriority.getCheckedRadioButtonId();
        String selectedPriority = "";

        // Check if priority is selected
        if (selectedPriorityId != -1) {
            RadioButton selectedPriorityButton = findViewById(selectedPriorityId);
            selectedPriority = selectedPriorityButton.getText().toString();
        } else {
            Toast.makeText(this, "Please select a priority", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate inputs
        if (title.isEmpty()) {
            Toast.makeText(this, "Title is required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (description.isEmpty()) {
            Toast.makeText(this, "Description is required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedDate.isEmpty()) {
            Toast.makeText(this, "Date is required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedTodotype.equals("Select Todo Type")) {
            Toast.makeText(this, "Please select a valid Todo type", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create TodoModel object
        TodoModel todo = new TodoModel(0, title, description, selectedDate, selectedTodotype, selectedPriority);

        // Call API to submit the todo
        WriteApi api = ApiClient.getRetrofit().create(WriteApi.class);
        Call<Void> call = api.createTodo(todo);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(WriteActivity.this, "Todo created successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(WriteActivity.this, ReadActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(WriteActivity.this, "Failed to create Todo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(WriteActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
