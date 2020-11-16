package com.dialog.overmaps;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private View mThisView;
    private final LatLng mGreeceCoords = new LatLng(37.9833547,23.7280478);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mThisView = getLayoutInflater().inflate(R.layout.activity_maps,null);
        setContentView(mThisView);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mGreeceCoords));

        FloatingActionButton fabZoomOut = mThisView.findViewById(R.id.zoom_out);
        FloatingActionButton fabZoomIn = mThisView.findViewById(R.id.zoom_in);

        fabZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // bug not exists here
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mGreeceCoords, 1));
            }
        });

        fabZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // pop the dialog OR any other dialog, BEFORE starting the animation
                // WHILE ANIMATING, YOU CAN ALSO PULL THE QUICKSETTINGS PANEL. LAG HAPPENS TOO!
                popDialog();
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mGreeceCoords, 20));
            }
        });

    }

    private void popDialog(){
        new AlertDialog.Builder(this)
                .setTitle("test")
                .setMessage("close dialog?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}