/**
 *
 */
package com.performanceactive.plugins.camera;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 *
 */
public class CustomCameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = CustomCameraPreview.class.getSimpleName();

    private final Camera camera;

    public CustomCameraPreview(Context context, Camera camera) {
        super(context);
        this.camera = camera;
        getHolder().addCallback(this);
    }

 public void toggleCamera(SurfaceHolder holder){
    Camera.stopPreview();
    int frontCameraId = 0;
    for (int camNo = 0; camNo < Camera.getNumberOfCameras(); camNo++) {
    CameraInfo camInfo = new CameraInfo();
        Camera.getCameraInfo(camNo, camInfo);
        if (camInfo.facing==(Camera.CameraInfo.CAMERA_FACING_FRONT)) {
            frontCameraId = camNo;
        }
    }
    Camera.release();
    Camera = Camera.open(frontCameraId);
    Camera.setPreviewDisplay(holder);
    Camera.startPreview();
    Log.d(TAG + "Restarting camera", +frontCameraId);
}
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        try {
            camera.stopPreview();
        } catch (Exception e) {
            Log.d(TAG, "Error stopping camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // only portrait full screen supported
    }

}
