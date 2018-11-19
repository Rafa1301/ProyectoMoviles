package com.example.rafao.proyectomoviles.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rafao.proyectomoviles.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.MultiDetector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;

@SuppressLint("ValidFragment")
public class ScannerFragment extends Fragment {

    private final String code;
    private SurfaceView cameraview;
    private TextView textview;
    private CameraSource camerasource;

    private BarcodeDetector barcodeDetector;
    private TextRecognizer textRecognizer;

    final int RequestCameraPermissionID = 1001;

    public ScannerFragment(String codigo){
        code = codigo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.scanner,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cameraview = view.findViewById(R.id.surfaceview1);
        textview = view.findViewById(R.id.textView);

        barcodeDetector = new BarcodeDetector.Builder(getContext()).setBarcodeFormats(Barcode.CODE_128)
                .build();

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                SparseArray<Barcode> qrCodes =detections.getDetectedItems();
                if(qrCodes.size() != 0){
                    textview.post(() -> {
                        Vibrator vibrator = (Vibrator)getContext().getSystemService(Context.VIBRATOR_SERVICE);
                        vibrator.vibrate(100);
                        if(code == "") {
                            textview.setText(qrCodes.valueAt(0).displayValue);
                        }else{
                            String codigo = qrCodes.valueAt(0).displayValue;
                            if(code.equals(codigo)){
                                textview.setText("El texto es el mismo");
                            }
                        }
                    });
                }
            }
        });

        textRecognizer = new TextRecognizer.Builder(getContext()).build();
        textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<TextBlock> detections) {

                final SparseArray<TextBlock> items = detections.getDetectedItems();
                if(items.size()!= 0){
                    textview.post(() -> {
                        Vibrator vibrator = (Vibrator)getContext().getSystemService(Context.VIBRATOR_SERVICE);
                        vibrator.vibrate(100);
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i<items.size();++i){
                            TextBlock item = items.valueAt(i);
                            stringBuilder.append(item.getValue());
                            stringBuilder.append("\n");
                        }
                        String text = stringBuilder.toString();
                        if(code == "") {
                            textview.setText("No hay codigo");
                        }else{
                            if(code.equals(text)){
                                textview.setText("El texto es el mismo");
                            }else{
                                textview.setText(text);
                            }
                        }
                    });
                }

            }
        });

        MultiDetector multiDetector = new MultiDetector.Builder()
                .add(barcodeDetector)
                .build();

        camerasource = new CameraSource.Builder(getContext(), multiDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1280, 1024)
                .setRequestedFps(2.0f)
                .setAutoFocusEnabled(true)
                .build();

        cameraview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.CAMERA},
                                RequestCameraPermissionID);
                    }
                    camerasource.start(holder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                camerasource.stop();
            }
        });

    }
}
