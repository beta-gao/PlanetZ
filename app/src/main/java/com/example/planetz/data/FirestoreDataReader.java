package com.example.planetz.data;

import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class FirestoreDataReader {

    private static FirestoreDataReader instance;
    private FirebaseFirestore db;

    private static final String TAG = "FirestoreDataReader";

    private FirestoreDataReader() {
        db = FirebaseFirestore.getInstance();
        Log.d(TAG, "FirestoreDataReader: Singleton instance created");
    }

    public static synchronized FirestoreDataReader getInstance() {
        if (instance == null) {
            instance = new FirestoreDataReader();
        }
        return instance;
    }

    public void fetchEmissionData(String userId, EmissionDataCallback callback) {
        Log.d(TAG, "fetchEmissionData: Fetching emission data for userId " + userId);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("emissions").document(userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    UserEmissionData emissionData = document.toObject(UserEmissionData.class);
                    Log.d(TAG, "fetchEmissionData: Data fetched successfully");
                    callback.onSuccess(emissionData);
                } else {
                    Log.w(TAG, "fetchEmissionData: No such document for " + userId);
                    callback.onFailure("No such document for " + userId);
                }
            } else {
                Log.e(TAG, "fetchEmissionData: Error getting document", task.getException());
                callback.onFailure("Error getting document: " + task.getException());
            }
        });
    }

//    public void getCountryEmissionsData(FirestoreCallback callback) {
//        Log.d(TAG, "getCountryEmissionsData: Fetching country emissions data");
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//        db.collection("globalStats")
//                .document("emissionsPerCapitaMap")
//                .get()
//                .addOnSuccessListener(documentSnapshot -> {
//                    if (documentSnapshot.exists()) {
//                        Map<String, Object> countries = (Map<String, Object>) documentSnapshot.get("countries");
//                        Map<String, Double> countryEmissions = new HashMap<>();
//
//                        if (countries != null) {
//                            for (Map.Entry<String, Object> entry : countries.entrySet()) {
//                                countryEmissions.put(entry.getKey(), ((Number) entry.getValue()).doubleValue());
//                            }
//                        }
//
//                        Log.d(TAG, "getCountryEmissionsData: Data fetched successfully");
//                        callback.onSuccess(countryEmissions);
//                    } else {
//                        Log.w(TAG, "getCountryEmissionsData: Document does not exist");
//                        callback.onFailure("Document does not exist.");
//                    }
//                })
//                .addOnFailureListener(e -> {
//                    Log.e(TAG, "getCountryEmissionsData: Error retrieving data", e);
//                    callback.onFailure("Error retrieving data: " + e.getMessage());
//                });
//    }

    public interface EmissionDataCallback {
        void onSuccess(UserEmissionData data);
        void onFailure(String errorMessage);
    }

    public interface FirestoreCallback {
        void onSuccess(Map<String, Double> data);
        void onFailure(String errorMessage);
    }
}
