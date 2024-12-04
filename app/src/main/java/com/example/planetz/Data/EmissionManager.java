package com.example.planetz.Data;

import android.content.Context;
import android.util.Log;

import com.example.planetz.LoginandRegister.UserManager;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmissionManager {
    private static final String TAG = "EmissionManager";
    private static EmissionManager instance;
    private FirebaseFirestore db;
    private String userId;
    private Map<String, Object> dailyData;
    private ListenerRegistration listenerRegistration;
    private List<EmissionDataListener> listeners = new ArrayList<>();

    private EmissionManager(Context context) {
        db = FirebaseFirestore.getInstance();
        userId = UserManager.getInstance(context).getUserId();
    }

    public static synchronized EmissionManager getInstance(Context context) {
        if (instance == null) {
            instance = new EmissionManager(context);
        }
        return instance;
    }

    public interface EmissionDataListener {
        void onDataChanged(Map<String, Object> dailyData);
    }

    public void addListener(EmissionDataListener listener) {
        listeners.add(listener);
    }

    public void removeListeners() {
        if (listenerRegistration != null) {
            listenerRegistration.remove();
        }
    }

    public void initDailyData(String selectedDate) {
        if (userId == null) {
            Log.e(TAG, "User ID is null");
            return;
        }
        DocumentReference docRef = db.collection("emissions").document(userId)
                .collection("dailyData").document(selectedDate);

        docRef.get().addOnSuccessListener(documentSnapshot -> {
            if (!documentSnapshot.exists()) {
                // Initialize the document with default values
                Map<String, Object> initialData = new HashMap<>();
                initialData.put("date", getDateComponents(selectedDate));
                Map<String, Object> consumption = new HashMap<>();
                consumption.put("totalEmissions", 0.0);
                initialData.put("consumption", consumption);

                docRef.set(initialData).addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Document for date " + selectedDate + " initialized.");
                }).addOnFailureListener(e -> {
                    Log.e(TAG, "Failed to initialize document for date " + selectedDate, e);
                });
            } else {
                Log.d(TAG, "Document for date " + selectedDate + " already exists.");
            }

            // Add listener for real-time updates
            addRealtimeListener(docRef);
        }).addOnFailureListener(e -> {
            Log.e(TAG, "Failed to get document for date " + selectedDate, e);
        });
    }

    private void addRealtimeListener(DocumentReference docRef) {
        if (listenerRegistration != null) {
            listenerRegistration.remove();
        }
        listenerRegistration = docRef.addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                Log.e(TAG, "Listen failed.", e);
                return;
            }
            if (snapshot != null && snapshot.exists()) {
                dailyData = snapshot.getData();
                notifyListeners(dailyData);
            } else {
                Log.d(TAG, "No document found for this date.");
                dailyData = null;
                notifyListeners(null);
            }
        });
    }

    private void notifyListeners(Map<String, Object> data) {
        for (EmissionDataListener listener : listeners) {
            listener.onDataChanged(data);
        }
        Log.d(TAG, "Notified listeners with data: " + data);
    }

    private Map<String, Object> getDateComponents(String selectedDate) {
        String[] dateParts = selectedDate.split("-");
        Map<String, Object> dateMap = new HashMap<>();
        dateMap.put("year", Integer.parseInt(dateParts[0]));
        dateMap.put("month", Integer.parseInt(dateParts[1]));
        dateMap.put("day", Integer.parseInt(dateParts[2]));
        dateMap.put("week", 1); // Replace with actual calculation if needed
        return dateMap;
    }

    public void updateEmission(String selectedDate, String category, String subcategory, double emissionValue) {
        if (userId == null) {
            Log.e(TAG, "User ID is null");
            return;
        }
        DocumentReference docRef = db.collection("emissions").document(userId)
                .collection("dailyData").document(selectedDate);

        String subcategoryPath = "consumption." + category + "." + subcategory;
        String categoryTotalPath = "consumption." + category + ".total";
        Map<String, Object> updates = new HashMap<>();
        updates.put(subcategoryPath, emissionValue);

        // Update totalEmissions
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Double existingTotalEmissions = getDoubleFromSnapshot(documentSnapshot, "consumption.totalEmissions");
                Double existingCategoryTotal = getDoubleFromSnapshot(documentSnapshot, categoryTotalPath);
                Double existingSubcategoryValue = getDoubleFromSnapshot(documentSnapshot, subcategoryPath);

                // Handle null values
                existingTotalEmissions = existingTotalEmissions != null ? existingTotalEmissions : 0.0;
                existingCategoryTotal = existingCategoryTotal != null ? existingCategoryTotal : 0.0;
                existingSubcategoryValue = existingSubcategoryValue != null ? existingSubcategoryValue : 0.0;

                // Calculate new totals
                double newCategoryTotal = existingCategoryTotal - existingSubcategoryValue + emissionValue;
                updates.put(categoryTotalPath, newCategoryTotal);

                double newTotalEmissions = existingTotalEmissions - existingSubcategoryValue + emissionValue;
                updates.put("consumption.totalEmissions", newTotalEmissions);

                docRef.set(updates, SetOptions.merge()).addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Emission updated for subcategory");
                }).addOnFailureListener(e -> {
                    Log.e(TAG, "Failed to update emission", e);
                });
            } else {
                Log.e(TAG, "Document does not exist");
            }
        }).addOnFailureListener(e -> {
            Log.e(TAG, "Failed to get document for emission update", e);
        });
    }

    private Double getDoubleFromSnapshot(DocumentSnapshot snapshot, String path) {
        Object value = snapshot.get(path);
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        } else {
            return null;
        }
    }
}
