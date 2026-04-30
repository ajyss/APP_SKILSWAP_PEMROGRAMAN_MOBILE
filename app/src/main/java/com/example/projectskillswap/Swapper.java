package com.example.projectskillswap;

public class Swapper {
    private String name;
    private String skills;
    private String description;
    private int profileImageResId;
    private double latitude;
    private double longitude;
    private String distance; // Tambahan untuk menyimpan jarak dalam teks (misal: "1.2 km")

    // Constructor LAMA (Masih dipertahankan agar tidak merusak kode lain)
    public Swapper(String name, String skills, String description, int profileImageResId, double latitude, double longitude) {
        this.name = name;
        this.skills = skills;
        this.description = description;
        this.profileImageResId = profileImageResId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = "0 km"; 
    }

    // Constructor BARU (Lebih simpel untuk tampilan Dashboard/Swap)
    public Swapper(String name, String skills, String description, String distance, int profileImageResId) {
        this.name = name;
        this.skills = skills;
        this.description = description;
        this.distance = distance;
        this.profileImageResId = profileImageResId;
    }

    public String getName() { return name; }
    public String getSkills() { return skills; }
    public String getDescription() { return description; }
    public int getProfileImage() { return profileImageResId; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getDistance() { return distance; }
}
