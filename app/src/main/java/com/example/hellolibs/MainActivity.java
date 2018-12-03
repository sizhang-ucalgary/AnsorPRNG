package com.example.hellolibs;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.SensorEventListener;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensorGyroscope;
    private Sensor mSensorAccelerometer;
    private Sensor mSensorMagneticfield;
    private Sensor mSensorPressure;
    private Sensor mSensorLight;
    private Sensor mSensorTemperature;
    private Sensor mSensorHumidity;


    private String randomString;
    private Button show_random;
    private Button clear_random;
    private CheckBox accelerometerCheckBox;
    private CheckBox gyroscopeCheckBox;
    private CheckBox magnetoCheckBox;
    private CheckBox pressureCheckBox;
    private CheckBox lightCheckBox;
    private CheckBox temperatureCheckBox;
    private CheckBox humidityCheckBox;
    private ProgressBar spinner;
    private RadioGroup radioGroupSrc;

    private byte[] seedGyroscope;
    private byte[] seedMagnetometer;
    private byte[] seedAccelerometer;
    private byte[] seedPressure;
    private byte[] seedLight;
    private byte[] seedTemperature;
    private byte[] seedHumidity;
    private byte[] allSensorDataBytes;

    private String filename;

    private SecureRandom rngJava;
    private CryptoRandom rngCpp;

    SharedPreferences sharedpreferences;
    Handler mainHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        randomString = "";

        sharedpreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        mainHandler = new Handler(this.getMainLooper());

        final EditText lineNum = findViewById(R.id.editText);
        show_random = findViewById(R.id.showRandom);
        clear_random = findViewById(R.id.clearRandom);
        accelerometerCheckBox = findViewById(R.id.checkBox1);
        gyroscopeCheckBox = findViewById(R.id.checkBox2);
        magnetoCheckBox = findViewById(R.id.checkBox3);
        pressureCheckBox = findViewById(R.id.checkBox4);
        lightCheckBox = findViewById(R.id.checkBox5);
        temperatureCheckBox = findViewById(R.id.checkBox6);
        humidityCheckBox = findViewById(R.id.checkBox7);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        radioGroupSrc = findViewById(R.id.radioSrc);

        lineNum.setText("");
//        List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
//        for (Sensor s : deviceSensors) {
//            tv.append("\n" + s.getName() + "\n");
//        }


        mSensorGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorMagneticfield = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSensorPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        mSensorHumidity = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        seedGyroscope = "CPSC630GroupProject".getBytes();


       rngJava = new SecureRandom();        // PRNG in Java Library
        rngCpp = new CryptoRandom();        // PRNG in C++ Library;

        /*
         * This Button Sets the seed based on the check boxes and generates random numbers.
         * */
        show_random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                filename = "Random";
                // for java -------------------------
                if (accelerometerCheckBox.isChecked() && (int)radioGroupSrc.getCheckedRadioButtonId() == 2131165238) {
                    rngJava.setSeed(seedAccelerometer);
                    filename += "_accelerometer";
                } else if (gyroscopeCheckBox.isChecked()&& (int)radioGroupSrc.getCheckedRadioButtonId() == 2131165238) {
                    rngJava.setSeed(seedGyroscope);
                    filename += "_gyroscope";
                } else if (magnetoCheckBox.isChecked()&& (int)radioGroupSrc.getCheckedRadioButtonId() == 2131165238) {
                    rngJava.setSeed(seedMagnetometer);
                    filename += "_magnetometer";
                } else if (pressureCheckBox.isChecked()&& (int)radioGroupSrc.getCheckedRadioButtonId() == 2131165238) {
                    rngJava.setSeed(seedPressure);
                    filename += "_pressure";
                } else if (lightCheckBox.isChecked()&& (int)radioGroupSrc.getCheckedRadioButtonId() == 2131165238) {
                    rngJava.setSeed(seedLight);
                    filename += "_light";
                } else if (temperatureCheckBox.isChecked()&& (int)radioGroupSrc.getCheckedRadioButtonId() == 2131165238) {
                    rngJava.setSeed(seedTemperature);
                    filename += "_temperature";
                } else if (humidityCheckBox.isChecked()&& (int)radioGroupSrc.getCheckedRadioButtonId() == 2131165238) {
                    rngJava.setSeed(seedHumidity);
                    filename += "_humidity";
                } else {
                    rngJava.setSeed(allSensorDataBytes);
                    filename += "_all";
                }
                // for java ------------------------- ends

                // for cpp -------------------------
                if (accelerometerCheckBox.isChecked() && (int)radioGroupSrc.getCheckedRadioButtonId() == 2131165267) {
                    rngCpp.setSeed(seedAccelerometer);
                    filename += "_accelerometer";
                } else if (gyroscopeCheckBox.isChecked()&& (int)radioGroupSrc.getCheckedRadioButtonId() == 2131165267) {
                    rngCpp.setSeed(seedGyroscope);
                    filename += "_gyroscope";
                } else if (magnetoCheckBox.isChecked()&& (int)radioGroupSrc.getCheckedRadioButtonId() == 2131165267) {
                    rngCpp.setSeed(seedMagnetometer);
                    filename += "_magnetometer";
                } else if (pressureCheckBox.isChecked()&& (int)radioGroupSrc.getCheckedRadioButtonId() == 2131165267) {
                    rngCpp.setSeed(seedPressure);
                    filename += "_pressure";
                } else if (lightCheckBox.isChecked()&& (int)radioGroupSrc.getCheckedRadioButtonId() == 2131165267) {
                    rngCpp.setSeed(seedLight);
                    filename += "_light";
                } else if (temperatureCheckBox.isChecked()&& (int)radioGroupSrc.getCheckedRadioButtonId() == 2131165267) {
                    rngCpp.setSeed(seedTemperature);
                    filename += "_temperature";
                } else if (humidityCheckBox.isChecked()&& (int)radioGroupSrc.getCheckedRadioButtonId() == 2131165267) {
                    rngCpp.setSeed(seedHumidity);
                    filename += "_humidity";
                } else {
                    rngCpp.setSeed(allSensorDataBytes);
                    filename += "_all";
                }
                // for cpp ------------------------- ends

                Thread t=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int number = Integer.parseInt(lineNum.getText().toString());

                        if((int)radioGroupSrc.getCheckedRadioButtonId() == 2131165238){
                            //getting random for java
                            for(int i=0;i<number;i++){
                                byte[] random = new byte[32];
                                rngJava.nextBytes(random);

                                StringBuilder result = new StringBuilder();
                                for (byte b : random) {
                                    result.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
    //                      result.append(String.format("%02X", b));
                                }
                                String resultString = result.toString();
                                randomString += resultString +"\n";
                            }
                        }else{
                            //getting random for cpp
                            for(int i=0;i<number;i++){
                                byte[] random = new byte[32];
                                rngCpp.nextBytes(random);

                                StringBuilder result = new StringBuilder();
                                for (byte b : random) {
                                    result.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
                                    //                      result.append(String.format("%02X", b));
                                }
                                String resultString = result.toString();
                                randomString += resultString +"\n";
                            }
                        }


                        FileOutputStream outputStream;

                        try {
                            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);

                            long start = System.nanoTime();
                            byte[] input_Bytes = randomString.getBytes();
                            long finish = System.nanoTime();
                            long timeElapsed = finish - start;
                            Log.d("onClick", "C Time Elapsed for getBytes() "+lineNum.getText()+": " + timeElapsed);

                            outputStream.write(input_Bytes);
                            outputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } // This is your code
                });
                t.start();
                try { t.join(); } catch (InterruptedException e) { e.printStackTrace(); }


                Log.d("onClick", filename);
                Context context = getApplicationContext();
                CharSequence text = "Random Numbers were saved in file "+filename;
                int duration = Toast.LENGTH_LONG;
//
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                //Log.d("whatever",radioGroupSrc.getCheckedRadioButtonId()+"");

                if(sharedpreferences.edit().putString("MyPrefs",randomString).commit()) {
                    Intent intent = new Intent(getBaseContext(), SensorDataPage.class);
                    intent.putExtra("Random Bytes", randomString);
                    startActivity(intent);
                }
            }
        });

        /*
         * This button clears the random numbers which are saved.
         * */
        clear_random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lineNum.setText("");
                randomString="";
            }
        });
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        // Do something with this sensor data.
        String msg = "" + event.timestamp;
        String gyroString = msg;
        String acceleroString = msg;
        String magnetoString = msg;
        String pressureString = msg;
        String lightString = msg;
        String temperatureString = msg;
        String humidityString = msg;

        int sensorType = event.sensor.getType();
        switch (sensorType) {
            case Sensor.TYPE_GYROSCOPE:
                gyroString += event.values[0] + "" + event.values[1] + "" + event.values[2];
                break;
            case Sensor.TYPE_ACCELEROMETER:
                acceleroString += event.values[0] + "" + event.values[1] + "" + event.values[2];
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                magnetoString += event.values[0] + "" + event.values[1] + "" + event.values[2];
                break;
            case Sensor.TYPE_PRESSURE:
                gyroString += event.values[0];
                break;
            case Sensor.TYPE_LIGHT:
                acceleroString += event.values[0];
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                magnetoString += event.values[0];
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                magnetoString += event.values[0];
                break;
            default:
                // do nothing
        }
//        Log.d(event.sensor.getName(), msg, new SecurityException());
        seedGyroscope = gyroString.getBytes();
        seedMagnetometer = magnetoString.getBytes();
        seedAccelerometer = acceleroString.getBytes();
        seedPressure = pressureString.getBytes();
        seedLight = lightString.getBytes();
        seedTemperature = temperatureString.getBytes();
        seedHumidity = humidityString.getBytes();
        allSensorDataBytes = (gyroString + magnetoString + acceleroString + pressureString +
                              lightString + temperatureString + humidityString).getBytes();
    }

    @Override
    protected void onStart() {
        // Register a listener for the sensor.
        super.onStart();
        mSensorManager.registerListener(this, mSensorGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorMagneticfield, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorPressure, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorLight, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorTemperature, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorHumidity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // do nothing
    }

}
