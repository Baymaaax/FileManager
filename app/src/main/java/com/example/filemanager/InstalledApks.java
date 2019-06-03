package com.example.filemanager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.filemanager.tools.FileDeleter;
import com.example.filemanager.tools.FileSearcher;
import com.example.filemanager.tools.FileTools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InstalledApks extends AppCompatActivity {
    private ImageButton goBackButton;
    private Button deleteAllApks;
    private GridView installedApkGrid;
    private File[] installedApks;
    private FileSearcher searcher;
    private InstalledApkGridAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installed_apks);
        Log.i("installed", "created");
        goBackButtonInit();
        searchApks();
        deleteAllApksButtonInit();
    }

    private void deleteAllApksButtonInit() {
        deleteAllApks = findViewById(R.id.delete_all_apks);
        deleteAllApks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(InstalledApks.this);
                dialog.setTitle("删除");
                dialog.setMessage("是否要删除所有安装包");
                dialog.setCancelable(false);
                dialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (FileDeleter.deleteAll(installedApks)) {
                            installedApks = getInstalledApks(searcher.search());
                            adapter.changeFiles(installedApks);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(InstalledApks.this, "已全部删除", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    private void searchApks() {
        File root = new File(Environment.getExternalStorageDirectory().toString());
        searcher = new FileSearcher(root, FileTools.APK);
        installedApks = getInstalledApks(searcher.search());
        adapter = new InstalledApkGridAdapter(InstalledApks.this, installedApks);
        installedApkGrid = findViewById(R.id.installed_apk_grid);
        installedApkGrid.setAdapter(adapter);
        installedApkGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FileTools.openFile(installedApks[position], InstalledApks.this);
            }
        });
        installedApkGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(InstalledApks.this);
                dialog.setTitle("删除");
                dialog.setMessage("是否要删除此安装包");
                dialog.setCancelable(false);
                dialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (FileDeleter.deleteAll(installedApks[position])) {
                            installedApks = getInstalledApks(searcher.search());
                            adapter.changeFiles(installedApks);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(InstalledApks.this, "已删除", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return true;
            }
        });

    }

    private boolean isInstalled(Context mContext, String packageName) {
        boolean hasInstalled = false;
        PackageManager packageManager = mContext.getPackageManager();
        List<PackageInfo> list = packageManager.getInstalledPackages(PackageManager.PERMISSION_GRANTED);
        for (PackageInfo p : list) {
            if (packageName != null && packageName.equals(p.packageName)) {
                hasInstalled = true;
                break;
            }
        }
        return hasInstalled;


    }

    private File[] getInstalledApks(File[] apks) {
        ArrayList<File> apkList = new ArrayList<>();
        for (File f : apks) {
            String packageName = f.getName().substring(0, f.getName().length() - 4);
            if (isInstalled(InstalledApks.this, packageName)) {
                apkList.add(f);
            }
        }
        installedApks = new File[apkList.size()];
        for (int i = 0; i < apkList.size(); i++) {
            installedApks[i] = apkList.get(i);
        }
        return installedApks;

    }

    private void goBackButtonInit() {
        goBackButton = findViewById(R.id.go_back_button_apks);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InstalledApks.this.finish();
            }
        });
    }
}
