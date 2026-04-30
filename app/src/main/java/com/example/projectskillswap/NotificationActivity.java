package com.example.projectskillswap;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    private RecyclerView rvNotifications;
    private NotificationAdapter adapter;
    private List<NotificationItem> notificationList;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        // 1. Bind View (Pastikan ID sesuai activity_notification.xml)
        rvNotifications = findViewById(R.id.rv_notifications_today);
        ImageView btnBack = findViewById(R.id.btn_back);

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // 2. Setup Firebase & List
        mDatabase = FirebaseDatabase.getInstance().getReference("notifications");
        notificationList = new ArrayList<>();
        adapter = new NotificationAdapter(notificationList);
        
        if (rvNotifications != null) {
            rvNotifications.setLayoutManager(new LinearLayoutManager(this));
            rvNotifications.setAdapter(adapter);
        }

        loadNotifications();
    }

    private void loadNotifications() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    notificationList.clear();
                    for (DataSnapshot data : snapshot.getChildren()) {
                        String title = data.child("title").getValue(String.class);
                        String message = data.child("message").getValue(String.class);
                        
                        notificationList.add(new NotificationItem(
                                title != null ? title : "Notifikasi Baru",
                                message != null ? message : "Ada update terbaru untukmu.",
                                "Baru saja",
                                R.drawable.ic_ai_stars
                        ));
                    }
                    Collections.reverse(notificationList);
                    if (adapter != null) adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Log.e("NOTIF_ERROR", "Error parsing data: " + e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("NOTIF_ERROR", "Database error: " + error.getMessage());
            }
        });
    }
}
