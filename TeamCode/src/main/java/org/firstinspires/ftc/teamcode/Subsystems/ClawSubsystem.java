package org.firstinspires.ftc.teamcode.Subsystems;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
@Config
public class ClawSubsystem extends SubsystemBase {
    private final Servo leftServo;
    private final Servo rightServo;

    public static double LEFT_OPEN_POSITION = 0.63;
    public static double LEFT_CLOSE_POSITION = 0.33;

    public static double RIGHT_OPEN_POSITION = 0;
    public static double RIGHT_CLOSE_POSITION = 0.41;

    private boolean leftOpen = true;
    private boolean rightOpen = true;

    public  ClawSubsystem(HardwareMap hardwareMap) {
        leftServo = hardwareMap.get(Servo.class, "leftClawServo");
        rightServo = hardwareMap.get(Servo.class, "rightClawServo");
    }

    public void openLeft() {
        leftServo.setPosition(LEFT_OPEN_POSITION);
        leftOpen = true;
    }

    public void openRight() {
        rightServo.setPosition(RIGHT_OPEN_POSITION);
        rightOpen = true;
    }

    public void closeLeft() {
        leftServo.setPosition(LEFT_CLOSE_POSITION);
        leftOpen = false;
    }

    public void closeRight() {
        rightServo.setPosition(RIGHT_CLOSE_POSITION);
        rightOpen = false;
    }

    public void toggleLeft() {
        if (leftOpen) {
            closeLeft();
        }else{
            openLeft();
        }
    }

    public void toggleRight() {
        if (rightOpen) {
            closeRight();
        }else{
            openRight();
        }
    }

    public void periodic() {
        telemetry.addData("leftpos", leftServo.getPosition());
        telemetry.addData("rightpos", rightServo.getPosition());
        telemetry.update();
    }
}
