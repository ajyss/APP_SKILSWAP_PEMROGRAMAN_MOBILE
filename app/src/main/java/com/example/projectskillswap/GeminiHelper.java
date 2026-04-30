package com.example.projectskillswap;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GeminiHelper {

    // API Key mas yang sudah di-enable fiturnya
    private static final String API_KEY = "AIzaSyAsGbDLY13Sl0DGRqal0C43lPrYtzJo1JA";
    private GenerativeModelFutures model;

    public GeminiHelper() {
        try {
            // Menggunakan gemini-1.5-flash (Versi terbaru & paling cepat)
            GenerativeModel gm = new GenerativeModel("gemini-1.5-flash", API_KEY);
            model = GenerativeModelFutures.from(gm);
        } catch (Exception e) {
            Log.e("GEMINI_INIT", "Gagal inisialisasi: " + e.getMessage());
        }
    }

    public interface AIResponseListener {
        void onResponse(String response);
        void onError(String errorMessage);
    }

    public void askGemini(String prompt, AIResponseListener listener) {
        if (model == null) {
            listener.onError("Sistem AI belum siap.");
            return;
        }

        // Memberikan instruksi agar AI tahu identitasnya
        String fullPrompt = "Kamu adalah 'SkillSwap AI Mentor'. Bantu pengguna belajar skill baru dengan ramah. Pertanyaan: " + prompt;

        Content content = new Content.Builder()
                .addText(fullPrompt)
                .build();

        ListenableFuture<GenerateContentResponse> responseFuture = model.generateContent(content);
        Executor executor = Executors.newSingleThreadExecutor();
        Handler mainHandler = new Handler(Looper.getMainLooper());

        Futures.addCallback(responseFuture, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                String text = result.getText();
                mainHandler.post(() -> {
                    if (text != null && !text.isEmpty()) {
                        listener.onResponse(text);
                    } else {
                        listener.onError("Google memblokir jawaban ini karena kebijakan keamanan.");
                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("GEMINI_DEBUG", "Error: " + t.getMessage());
                mainHandler.post(() -> {
                    String msg = t.getMessage();
                    if (msg != null && msg.contains("location")) {
                        listener.onError("Wilayah tidak didukung. Coba gunakan koneksi lain.");
                    } else {
                        listener.onError("Gagal terhubung: " + (msg != null ? msg : "Cek koneksi internet mas."));
                    }
                });
            }
        }, executor);
    }
}
