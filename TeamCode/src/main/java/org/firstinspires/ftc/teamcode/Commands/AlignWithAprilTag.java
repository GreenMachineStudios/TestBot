package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;

public class AlignWithAprilTag extends CommandBase {

    double distance = 12.0;
    double heading = 0.0;

    public AlignWithAprilTag(double heading) {
        this.heading = heading;
    }

    @Override
    public void initialize() {

    }
}
