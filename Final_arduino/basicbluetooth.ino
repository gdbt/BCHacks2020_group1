#include <SoftwareSerial.h>
SoftwareSerial BTserial(0,1); //setting up the Bluetooth to use 0 and 1 pins for the transmissions
char data = 0;          //for holding the data that will be sent to the phone
int motorPinRight = 3; //pins for the motors
int motorPinLeft = 2;
int trigPin = 6;       //pins for the ultra sonic
int echoPin = 5;
long duration;         //other variables
int distance;

void setup() {
  BTserial.begin(9600); 
  Serial.begin(9600);
  pinMode(trigPin, OUTPUT); //intializing of the pins
  pinMode(echoPin, INPUT);
  pinMode(motorPinRight, OUTPUT);
  pinMode(motorPinLeft, OUTPUT); 
}

void loop() {
      digitalWrite(trigPin, LOW); //This chunk is the ultra sonic sensor
      delayMicroseconds(2);
      digitalWrite(trigPin, HIGH);
      delayMicroseconds(10);
      digitalWrite(trigPin, LOW);

      duration = pulseIn(echoPin, HIGH); //length in time it takes for a ping response
      distance = duration*0.034/2; //getting the distance from the time
      int leftspeed = 150; //setting variables for forward 150
      int rightspeed = 150;
      analogWrite(motorPinRight, rightspeed); //setting both wheels up
      analogWrite(motorPinLeft, leftspeed);
      
      int x[10] = {1,2,3,4,5,6,7,8,9,10}; //PROOF OF CONCEPT CODE. USED WHEN CONNECTING BLUE TOOTH
      if(Serial.available() > 0){ //checking that if there is a response go forward
        data = Serial.read(); //reading the response
        if(data == '2'){ //if the command send is the number 2 then move forward
          int i;
          for(i = 0; i < 9; i++){ //for loop for sending over the data to the app. 
            Serial.print(x[i]);
            Serial.print(",");
            Serial.println(x[i+1]);
          }
        }
      }
      if (distance < 40) { //this is checking the distance that the robot is from an object
        leftspeed = -100; //setting the server speeds for the robot to flee
        rightspeed = -100;
        analogWrite(motorPinRight, rightspeed);//changing wheel speeds to reverse
        analogWrite(motorPinLeft, leftspeed);
        delay(500); //giving it a delay of 0.5 seconds so it can reverse sufficiently

        rightspeed = 100; //setting turn speeds
        leftspeed = 150;
        analogWrite(motorPinRight, rightspeed); //changing wheel speeds
        analogWrite(motorPinLeft, leftspeed);
        delay(250);
      }

}
