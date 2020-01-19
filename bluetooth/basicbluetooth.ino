#include <SoftwareSerial.h>
SoftwareSerial BTserial(0,1);
char data = 0;
void setup() {
  BTserial.begin(9600);
  Serial.begin(9600);
}

void loop() {
  int x[10] = {1,2,3,4,5,6,7,8,9,10};
  if(Serial.available() > 0){
    data = Serial.read();
    if(data == '2'){
      int i;
      for(i = 0; i < 9; i++){
          Serial.print(x[i]);
          Serial.print(",");
          Serial.println(x[i+1]);
      }
      
    }
  }

}
