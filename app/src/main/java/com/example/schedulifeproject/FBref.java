package com.example.schedulifeproject;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FBref {
    public static FirebaseDatabase FBDB = FirebaseDatabase.getInstance();

    public static DatabaseReference refStudents=FBDB.getReference("");
    public static DatabaseReference refStuGrades=FBDB.getReference("");
}
