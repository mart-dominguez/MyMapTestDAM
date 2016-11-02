package ar.edu.utn.frsf.isi.dam.mymaptest;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final LatLng crai = new LatLng(-31.620816, -60.747582);
    private static final LatLng utn = new LatLng(-31.616579, -60.675307);
    private static final LatLng meduc = new LatLng(-31.662254, -60.711412);
    private static final LatLng gym = new LatLng(-31.646997, -60.704840);
    private static final LatLng casa = new LatLng(-31.632182, -60.792063);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // SELECCIONAR uno de los 4 métodos
        // actualizarMapa();
        // mostrarClub();
        // polilinea();
        poligono();
    }

    private void mostrarClub(){
        LatLng crai= new LatLng(-31.620816,-60.747582);
        Marker QLSG = mMap.addMarker(new MarkerOptions()
                .position(crai)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.crai))
                .title("CRAI")
                        .snippet("El que juega el TNC #QLSG"));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(crai)      // pone el centro de la camara en una coordenada
                .zoom(10)
                .bearing(45)
                .tilt(15)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),3000,null);
    }

    private void polilinea() {

        PolylineOptions rectOptions = new PolylineOptions()
                .add(casa).color(Color.BLUE)
                .add(meduc).color(Color.RED)
                .add(gym).color(Color.RED)
                .add(utn)
                .add(crai);
        Polyline polyline = mMap.addPolyline(rectOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gym, 9));
    }

    private void poligono(){
        PolygonOptions polOpt = new PolygonOptions().add(casa).add(meduc).add(utn);
        Polygon p = mMap.addPolygon(polOpt);
        p.setStrokeColor(Color.CYAN);
        p.setFillColor(Color.GREEN);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gym, 9));
    }

    private void actualizarMapa() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    9999);
            return;
        }
        mMap.setMyLocationEnabled(true);
        //UiSettings
        mMap.getUiSettings().setCompassEnabled(true);
        LatLng SYDNEY = new LatLng(-33.88,151.21);
        LatLng MOUNTAIN_VIEW = new LatLng(37.4, -122.1);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SYDNEY, 15));

        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());

        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

        // Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(MOUNTAIN_VIEW)      // Sets the center of the map to Mountain View
                .zoom(17)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        LatLngBounds AUSTRALIA = new LatLngBounds(new LatLng(-44, 113), new LatLng(-10, 154));
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(AUSTRALIA, 0));
        LatLng centro = AUSTRALIA.getCenter();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centro, 10));

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 9999: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // SELECCIONAR uno de los 4 métodos
                    // actualizarMapa();
                    // mostrarClub();
                    // polilinea();
                    poligono();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

        }
    }

}
