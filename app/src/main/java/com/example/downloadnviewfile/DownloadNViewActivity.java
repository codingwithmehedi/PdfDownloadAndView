package com.example.downloadnviewfile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadNViewActivity extends AppCompatActivity {
    private TextView tvUrl;
    private Button btDownload,btnView;
    private String filePath = "http://www.africau.edu/images/default/sample.pdf";
    private URL url = null;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_nview);


        initView();
        setListeners();
        

    }

    private void initView() {

        tvUrl = findViewById(R.id.tvUrl);
        btDownload = findViewById(R.id.btnDownL);
        btnView = findViewById(R.id.btnView);

        try {
            url = new URL(filePath);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        fileName = url.getPath();
        fileName = fileName.substring(fileName.lastIndexOf('/')+1);
        tvUrl.setText(fileName);

    }

    private void setListeners(){
        btDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url+""));
                request.setTitle(fileName);
                request.setMimeType("application/pdf");
                request.allowScanningByMediaScanner();
                request.setAllowedOverMetered(true);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,fileName);
                //request.setDestinationInExternalFilesDir();
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + fileName);

                 Uri uri = FileProvider.getUriForFile(DownloadNViewActivity.this,"com.example.downloadnviewfile" + ".provider",file);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri,"application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(intent);

            }
        });
    }


}