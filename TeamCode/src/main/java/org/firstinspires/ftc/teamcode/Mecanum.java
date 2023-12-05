package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name="Mecanum Drive", group="Iterative Opmode")
public class Mecanum extends OpMode {

    // declare and initialize four DcMotors.
    private DcMotor front_left  = null;
    private DcMotor front_right = null;
    private DcMotor back_left   = null;
    private DcMotor back_right  = null;
    private DcMotor elbow  = null;
    private DcMotor rotator = null;

    Servo   servo1;
    Servo   servo0;
    Servo shooter;
    Servo wrist;
    Servo grabber;

    int ServoPosition = 1;
    int elevatorZero=0;
    @Override
    public void init() {

        // Name strings must match up with the config on the Robot Controller
        // app.
        front_left   = hardwareMap.get(DcMotor.class, "motor0");
        front_right  = hardwareMap.get(DcMotor.class, "BackL2");
        back_left    = hardwareMap.get(DcMotor.class, "FrontR1");
        back_right   = hardwareMap.get(DcMotor.class, "BackR3");
        elbow = hardwareMap.get(DcMotor.class,"FrontL0");
        rotator = hardwareMap.get(DcMotor.class,"motor1");

        servo0 = hardwareMap.get(Servo.class, "Servo0");
        servo1 = hardwareMap.get(Servo.class, "Servo1");
        shooter = hardwareMap.get(Servo.class, "Servo2");
        wrist = hardwareMap.get(Servo.class, "Servo3");
        grabber = hardwareMap.get(Servo.class,"Servo4");

        front_left.setDirection(DcMotor.Direction.REVERSE);
        back_left.setDirection(DcMotor.Direction.REVERSE);

        front_right.setDirection(DcMotor.Direction.REVERSE);
        back_right.setDirection(DcMotor.Direction.REVERSE);

        elbow.setDirection(DcMotor.Direction.FORWARD);

        shooter.setPosition(0);
    }

    @Override
    public void loop() {
        double drive;
        double strafe;
        double twist;

        if(gamepad2.right_bumper){
            drive = gamepad2.right_stick_x;
            strafe = gamepad2.left_stick_y;
            twist  = gamepad2.left_stick_x;
        }
        else{
            drive = gamepad2.right_stick_x*0.5;
            strafe  = gamepad2.left_stick_y*0.5;
            twist  = gamepad2.left_stick_x*0.5;
        }

        //Elbow - motor xy
        if(gamepad1.y){
            elbow.setPower(0.3);
        }

        else if(gamepad1.x){
            elbow.setPower(-0.3);
        }
        else{
            elbow.setPower(0);
        }

        //Rotator - motor
        if(gamepad1.left_stick_x>0.05||gamepad1.left_stick_x<-0.05){
            rotator.setPower(gamepad1.left_stick_x*0.4);
        }
        else{
            rotator.setPower(0);
        }

        //Wrist - servo - c2 right stick
        int wristInput = (int)(gamepad1.right_stick_y*10);
        wrist.setPosition(wristInput);

        //Grabber -servo - c2 ab
        if(gamepad1.a){
            grabber.setPosition(0.2);
        }

        if(gamepad1.b){
            grabber.setPosition(0);
        }

        //Shooter -servo up- up-dpad
        if(gamepad1.dpad_up){
            shooter.setPosition(0.23 );
        }
        else{
            shooter.setPosition(0);
        }



        // You may need to multiply some of these by -1 to invert direction of
        // the motor.  This is not an issue with the calculations themselves.
        double[] speeds = {
                (drive + strafe + twist),
                (drive - strafe - twist),
                (drive - strafe +twist),
                (drive + strafe - twist)
        };

        double max = Math.abs(speeds[0]);
        for(int i = 0; i < speeds.length; i++) {
            if ( max < Math.abs(speeds[i]) ) max = Math.abs(speeds[i]);
        }

        // If and only if the maximum is outside of the range we want it to be,
        // normalize all the other speeds based on the given speed value.
        if (max > 1) {
            for (int i = 0; i < speeds.length; i++) speeds[i] /= max;
        }

        front_left.setPower(speeds[0]);
        front_right.setPower(speeds[1]);
        back_left.setPower(speeds[2]);
        back_right.setPower(speeds[3]);

//        // 300 units per rotation
//        float elavatorPower=(gamepad2.right_trigger-gamepad2.left_trigger);
//        if (elavatorPower > 0) {
//            elevator.setPower(elavatorPower * 0.80);
//        } else {
//            elevator.setPower(elavatorPower * 0.50);
//        }

        telemetry.addData("strafe",strafe);
        telemetry.update();
    }
}

