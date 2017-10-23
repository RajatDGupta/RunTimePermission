package runtimepermission.neerajgupta.com.runtimepermission;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.audiofx.BassBoost;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private static final int REQUEST_CAMERA = 125;
    private static final int REQUEST_CONTACT = 225;
    private static final int REQUEST_STORAGE = 325;
    private static final int REQUEST_GROUP_PERMISSION = 425;

    private static final int TXT_CAMERA = 1;
    private static final int TXT_CONTACT = 2;
    private static final int TXT_STORAGE = 3;

    private PermissionUtils permissionUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permissionUtils = new PermissionUtils(this);
    }


    /********************check permission*/
    private int checkPermission(int permission) {
        int status = PackageManager.PERMISSION_DENIED;
        switch (permission) {
            case TXT_CAMERA:
                status = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
                break;
            case TXT_STORAGE:
                status = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
            case TXT_CONTACT:
                status = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
                break;
        }
        return status;
    }

    /********************request permission*/
    private void requestPermission(int permission) {

        switch (permission) {
            case TXT_CAMERA:
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
                break;
            case TXT_STORAGE:
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE);
                break;
            case TXT_CONTACT:
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CONTACT);
                break;
        }

    }

    public void showPermissionExplanation(final int permission) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (permission == TXT_CAMERA) {
            builder.setMessage("This app need to access your device Camera.. please allow");
            builder.setTitle("Camera Permission Needed");
        } else if (permission == TXT_STORAGE) {
            builder.setMessage("This app need to access your device Storage.. please allow");
            builder.setTitle("Storage Permission Needed");

        } else if (permission == TXT_CONTACT) {
            builder.setMessage("This app need to access your device Contacts.. please allow");
            builder.setTitle("Contacts Permission Needed");
        }
        builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (permission == TXT_CAMERA)
                    requestPermission(TXT_CAMERA);
                if (permission == TXT_STORAGE)
                    requestPermission(TXT_STORAGE);
                if (permission == TXT_CONTACT)
                    requestPermission(TXT_CONTACT);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /*****************request group permission*/
    public void requestGroupPermission(ArrayList<String> permissions){
        String[] permissionList=new String[permissions.size()];
        permissions.toArray(permissionList);
        ActivityCompat.requestPermissions(MainActivity.this,permissionList,REQUEST_GROUP_PERMISSION);

    }


    /***********************************************************/
    public void openCamera(View view) {

        if (checkPermission(TXT_CAMERA) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAMERA))
            {
                showPermissionExplanation(TXT_CAMERA);
            }
            else if(!permissionUtils.checkPermissionPreference("camera")) {

                requestPermission(TXT_CAMERA);
                permissionUtils.updatePermissionPreference("camera");

            } else {
                Toast.makeText(this, "Please allow Camera permission from your app setting", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri=Uri.fromParts("package",this.getPackageName(),null);
                intent.setData(uri);
                this.startActivity(intent);
            }

        }else {
            Toast.makeText(this, "You have camera permission", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(MainActivity.this,ResultActivity.class);
            intent.putExtra("message","You can tack photo from camera");
            startActivity(intent);
        }
    }

    public void openContact(View view) {
        if (checkPermission(TXT_CONTACT) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CONTACTS))
            {
                showPermissionExplanation(TXT_CONTACT);
            }
            else if(!permissionUtils.checkPermissionPreference("contacts")) {

                requestPermission(TXT_CONTACT);
                permissionUtils.updatePermissionPreference("contacts");

            } else {
                Toast.makeText(this, "Please allow Contact permission from your app setting", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri=Uri.fromParts("package",this.getPackageName(),null);
                intent.setData(uri);
                this.startActivity(intent);
            }

        }else {
            Toast.makeText(this, "You have contact permission", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(MainActivity.this,ResultActivity.class);
            intent.putExtra("message","You can tack contact from contacts");
            startActivity(intent);
        }
    }

    public void openStorage(View view) {
        if (checkPermission(TXT_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {
                showPermissionExplanation(TXT_STORAGE);
            }
            else if(!permissionUtils.checkPermissionPreference("storage")) {

                requestPermission(TXT_STORAGE);
                permissionUtils.updatePermissionPreference("storage");

            } else {
                Toast.makeText(this, "Please allow Storage permission from your app setting", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri=Uri.fromParts("package",this.getPackageName(),null);
                intent.setData(uri);
                this.startActivity(intent);
            }

        }else {
            Toast.makeText(this, "You have storage permission", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(MainActivity.this,ResultActivity.class);
            intent.putExtra("message","You can store data");
            startActivity(intent);
        }
    }

    public void requestPermissionAll(View view) {

        ArrayList<String> permissionNeeded=new ArrayList<>();
        ArrayList<String> permissionAvailable=new ArrayList<>();
        permissionAvailable.add(Manifest.permission.CAMERA);
        permissionAvailable.add(Manifest.permission.READ_CONTACTS);
        permissionAvailable.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        for (String permission: permissionAvailable){
            if(ContextCompat.checkSelfPermission(this,permission)!=PackageManager.PERMISSION_GRANTED)
                permissionNeeded.add(permission);
        }
        requestGroupPermission(permissionNeeded);

    }
    /***********************************************************/
}
