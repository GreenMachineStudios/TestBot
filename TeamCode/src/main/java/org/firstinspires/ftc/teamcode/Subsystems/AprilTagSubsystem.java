package org.firstinspires.ftc.teamcode.Subsystems;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import android.util.Size;

import com.arcrobotics.ftclib.command.SubsystemBase;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

public class AprilTagSubsystem extends SubsystemBase {

    private final AprilTagProcessor tagProcessor;

    private final VisionPortal visionPortal;

    double heading = 0;

    public AprilTagSubsystem(HardwareMap hardwareMap) {

        tagProcessor = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagID(true)
                .build();

        visionPortal = new VisionPortal.Builder()
                .addProcessor(tagProcessor)
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .setCameraResolution(new Size(640, 480))
                .build();
    }

    public void getHeadingAprilTags(){
        if(tagProcessor.getDetections() != null) {
            if (tagProcessor.getDetections().size() > 0) {
                for(int i = 0; i < tagProcessor.getDetections().size(); i++){
                    //the tag which the processer will process is currently: 3
                    if(tagProcessor.getDetections().get(i).id == 3){
                        heading = tagProcessor.getDetections().get(i).ftcPose.bearing;
                    }
                }

            }
            else{
                heading = 0;
            }
        }

    }

    @Override
    public void periodic() {
        getHeadingAprilTags();

        telemetry.addData("heading: ", heading);
    }

}
