package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drivetrain {

    public DcMotor frontLeft; //Port
    public DcMotor frontRight; //Port
    public DcMotor backLeft; //Port
    public DcMotor backRight; //Port


    public void rotateRight(double power) {
        frontRight.setPower(-power);
        backRight.setPower(-power);
        frontLeft.setPower(power);
        backLeft.setPower(power);
    }

    public void strafeLeft(double power) {
        frontRight.setPower(power);
        backRight.setPower(-power);
        frontLeft.setPower(-power);
        backLeft.setPower(power);
    }

    public void init(HardwareMap hardwareMap) {


        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        //Reverses ---- side
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        //Sets power to zero on initialization to prevent motors from moving unintentionally
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    public void setDriveUsingEncoders() {
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void driveAll(double power) { //Quick way of setting all of the motors to run at certain power
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);
    }

    //[button]: button to go into slow-mode [percentage]: how slow should the robot go
    public void setDrivePower(float button, double percentage, double y, double x, double rx) { //Code to drive robot in holonomic fashion

        double frontLeftPower = y + x + rx;
        double backLeftPower = y - x + rx;
        double frontRightPower = y - x - rx;
        double backRightPower = y + x - rx;

        if (Math.abs(frontLeftPower) > 1 || Math.abs(backLeftPower) > 1 ||
                Math.abs(frontRightPower) > 1 || Math.abs(backRightPower) > 1) {
            // Find the largest power
            double max = 0;
            max = Math.max(Math.abs(frontLeftPower), Math.abs(backLeftPower));
            max = Math.max(Math.abs(frontRightPower), max);
            max = Math.max(Math.abs(backRightPower), max);

            // Divide everything by max (it's positive so we don't need to worry
            // about signs)
            frontLeftPower /= max;
            backLeftPower /= max;
            frontRightPower /= max;
            backRightPower /= max;


        }

        if (button > 0) {
            frontLeft.setPower(frontLeftPower * percentage);
            backLeft.setPower(backLeftPower * percentage);
            frontRight.setPower(frontRightPower * percentage);
            backRight.setPower(backRightPower * percentage);

        } else {
            frontLeft.setPower(frontLeftPower);
            backLeft.setPower(backLeftPower);
            frontRight.setPower(frontRightPower);
            backRight.setPower(backRightPower);
        }

    }
}