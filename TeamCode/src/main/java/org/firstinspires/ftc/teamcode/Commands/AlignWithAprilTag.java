package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Subsystems.AprilTagSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.SimpleMecanumDriveSubsystem;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

public class AlignWithAprilTag extends CommandBase {
    final SimpleMecanumDriveSubsystem driveSubsystem;

    final AprilTagSubsystem aprilTag;

    final double DESIRED_DISTANCE = 12.0;

    final double SPEED_GAIN = 0.02  ;
    final double STRAFE_GAIN = 0.015 ;
    final double TURN_GAIN = 0.01  ;

    final double MAX_AUTO_SPEED = 0.5;
    final double MAX_AUTO_STRAFE = 0.5;
    final double MAX_AUTO_TURN = 0.3;

    private static final int DESIRED_TAG_ID = 3;
    private AprilTagDetection desiredTag = null;

    private double rangeError;
    private double headingError;
    private double yawError;

    public AlignWithAprilTag(SimpleMecanumDriveSubsystem driveSubsystem, AprilTagSubsystem aprilTag) {
        this.driveSubsystem = driveSubsystem;
        this.aprilTag = aprilTag;
        addRequirements(driveSubsystem, aprilTag);
    }

    @Override
    public void execute() {

        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        for (AprilTagDetection detection : currentDetections) {

            if (detection.metadata != null) {

                if (detection.id == DESIRED_TAG_ID) {

                    desiredTag = detection;
                    break;

                }
            }

        }

        rangeError = (desiredTag.ftcPose.range - DESIRED_DISTANCE);
        headingError = desiredTag.ftcPose.bearing;
        yawError = desiredTag.ftcPose.yaw;

        double  drive = 0;
        double  strafe = 0;
        double  turn = 0;

        drive  = Range.clip(rangeError * SPEED_GAIN, -MAX_AUTO_SPEED, MAX_AUTO_SPEED);
        turn   = Range.clip(headingError * TURN_GAIN, -MAX_AUTO_TURN, MAX_AUTO_TURN) ;
        strafe = Range.clip(-yawError * STRAFE_GAIN, -MAX_AUTO_STRAFE, MAX_AUTO_STRAFE);

        driveSubsystem.drive(drive, strafe, turn);
    }

    @Override
    public void end(boolean interrupted) {
        driveSubsystem.stop();
    }

    @Override
    public boolean isFinished() {
        return rangeError == 0 && headingError == 0 && yawError == 0;
    }
}
