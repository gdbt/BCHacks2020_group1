int trigPin = 9; //trigger pin
int echoPin = 10; //echopin
int led = 12; //led for checking is sensor is working
long duration; //duration the ping for the sensor takes
int distance; //calculated distance
int data[10][2]; //initializing an array
int dataId = 0; //int for the amount of times the robot detects
void setup() {
pinMode(trigPin, OUTPUT); //pin initialization
pinMode(echoPin, INPUT);
pinMode(led,OUTPUT);
Serial.begin(9600);
}

void loop() {
  digitalWrite(trigPin, LOW); //Ultra sonic ping
  delayMicroseconds(2);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  duration = pulseIn(echoPin, HIGH);
  distance= duration*0.034/2; //distance calculation
  if((distance > 40) && (dataId < 10)){ //checking if distance is greater then 40, then blinking the led
    digitalWrite(led,HIGH); //turning led on
    delay(100); //second delay
    digitalWrite(led,LOW); //turning led off
    int d; //initalizing for the for loops
    int z;
    int x;
    for(d = 0; d <1; d++){ //running it only once
      for(z = 0; z<2; z++){ //telling it to run only twice
        x = (int)random(0,100); //creating fake x and y coordinates for testing the bluetooth. 
          data[d][z]= x; //inputing value at either position 0 or 1 in the array
      }
      Serial.print(data[d][0]);  //printing out the x coord
      Serial.print(",");
      Serial.println(data[d][1]);//printing out the y cord
    }
    dataId++;   //increasing the data length
  }
  /*
  Serial.print("Distance: "); //printing the word distance in the serial monitor for testing
  Serial.println(distance); //printing out the distance variable. USE FOR TESTING
  */
  int fp; //initalizing the print
  if(dataId == 10){ //while the dataId is full go into this section
    for(fp = 0; fp < 10; fp++){ //for loop to print out all the values
      Serial.print(data[fp][0]); //print out x value
      Serial.print(","); 
      Serial.println(data[fp][1]); //print out y valur
    }
    dataId = 11;  //once done increment dataid to stop the repeater.
  }
}
