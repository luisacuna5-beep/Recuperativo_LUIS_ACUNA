package cl.certamen.actividad_fisica_inteligente;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Main extends AppCompatActivity {
    private static final int REQUEST_CODE_LOCATION = 100;

    TextView TVsensores, TVlatitud, TVlongitud;
    EditText ETusuario;
    Button BTiniciar, BTdetener;
    SensorManager sensormanager;
    List<Sensor> listSensor;
    FusedLocationProviderClient fusedLocationProviderClient;
    DatabaseReference databaseReference;
    double latitudActual = 0.0;
    double longitudActual = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        TVlatitud = findViewById(R.id.tvLatitud);
        TVlongitud = findViewById(R.id.tvLongitud);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("Ubicaciones");
        obtener

        TVsensores= findViewById(R.id.tvSensores);
        BTiniciar=findViewById(R.id.btInicioReg);
        BTdetener=findViewById(R.id.btDetenerReg);

        sensormanager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        listSensor = sensormanager.getSensorList(Sensor.TYPE_ALL);

        verificarSensor();



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }
    private void obtenerUbicacion(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new  String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE_LOCATION);
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this,location -> {
            if (location != null){
                latitudActual=location.getLatitude();
                longitudActual=location.getLongitude();
                TVlatitud.setText("latitud: " + latitudActual);
                TVlongitud.setText("longitud: "+ longitudActual);


            }
            else {
                TVlatitud.setText("Latitud: No disponible");
                TVlongitud.setText("Longitud: No disponible");
            }
        });
    }
    private void verificarSensor()
    {
        StringBuilder sensores = new StringBuilder("Sensores:\n\n");
        for(Sensor sensor : listSensor){
            sensores.append(".").append(sensor.getName()).append("\n");
        }TVsensores.setText((sensores.toString()));
    }
}