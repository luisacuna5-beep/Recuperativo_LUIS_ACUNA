package cl.certamen.actividad_fisica_inteligente;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
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
    double  latitudActual = 0.0;
    double


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

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
    private void verificarSensor()
    {
        StringBuilder sensores = new StringBuilder("Sensores:\n\n");
        for(Sensor sensor : listSensor){
            sensores.append(".").append(sensor.getName()).append("\n");
        }TVsensores.setText((sensores.toString()));
    }
}