#include <Keyboard.h>
#include <Mouse.h>
#include <MouseTo.h>

const float CORRECTION_FACTOR = 1;


void setup() {
  Serial.begin(9600);
  Serial.setTimeout(10);
  Keyboard.begin();
  Mouse.begin();
  
  MouseTo.setCorrectionFactor(CORRECTION_FACTOR);
}

void pressKey(char key) {
  Keyboard.press(key);
  delay(random(60, 130));
  Keyboard.releaseAll();
}

void pressKey(char first, char second) {
  Keyboard.press(first);
  Keyboard.press(second);
  delay(random(60, 130));
  Keyboard.releaseAll();
}

void moveTo(int x, int y, char button) {
  MouseTo.setTarget(x, y);
  while (MouseTo.move() == false) {}
  delay(random(1000, 1500));

  Mouse.press(button);   
  delay(random(120, 150));
  Mouse.release(button);
  delay(1500);
}

void test(){
 MouseTo.setTarget(0, 0);
 while (MouseTo.move() == false) {}
 delay(1000);
 MouseTo.setTarget(1500, 0);
 while (MouseTo.move() == false) {}
 delay(1000);

}

void changeRod(String touch){

  pressKey('i'); 
  
  int sx = touch.indexOf('[') + 1;
  int sy = touch.indexOf(',');
    
  int x = touch.substring(sx, sy).toInt();
  int y = touch.substring(sy + 1, touch.length() - 1).toInt();
  
  moveTo(x, y, MOUSE_RIGHT);

  pressKey('i');
}

void useSlot(String slot){
  int start = slot.indexOf('[');
  char key = slot.charAt(start + 1);
  pressKey(key);
}

void beer(String beer){

    useSlot(beer);

    delay(2000);

    moveTo(1507, 847, MOUSE_LEFT);
    delay(2000);

    moveTo(1192,494, MOUSE_LEFT);
    delay(2000);

    moveTo(1646,844, MOUSE_LEFT);
    delay(2000);

    pressKey(0xB1);

}

void takeLoot(String loot){

  pressKey(0x80); 
  
  int sx = loot.indexOf('[') + 1;
  int sy = loot.indexOf(',');
    
  int x = loot.substring(sx, sy).toInt();
  int y = loot.substring(sy + 1, loot.length() - 1).toInt();
  
  moveTo(x, y, MOUSE_RIGHT);

  pressKey(0x80);
  
}

char getKey(char key) {
  switch  (key) {
    case 'w': return 'w';
    case 's': return 's';
    case 'a': return 'a';
    case 'd': return 'd';
    case 'r': return 'r';
    default : return '@';
  }
}

void exitGame(){
  pressKey(0xB1);
  moveTo(373, 169, MOUSE_LEFT );
  moveTo(1227,626, MOUSE_LEFT );
  moveTo(1000,650, MOUSE_LEFT );
  pressKey(0xB1);
}

void skipCalendar(){
  pressKey(0xB1);
  pressKey(0xB1);
}

void loop() {

  String input = Serial.readString();
  int length = input.length();
  if (length != 0) {
    if (input.startsWith("space")) {
      pressKey(0x20);
    } else if (input.startsWith("Rod")) {
      changeRod(input);
    } else if (input.startsWith("Loot")) {
      takeLoot(input);
    } else if (input.startsWith("Beer")) {
      beer(input);
    } else if (input.startsWith("Slot")) {
      useSlot(input);
    } else if (input.startsWith("test")) {
      test();
    } else if (input.startsWith("Exit")) {
      exitGame();
    }else if (input.startsWith("Skip_calendar")) {
      skipCalendar();
    }else {
      for (int i = 0; i < length; i++) {
        delay(random(130, 210));
        char symbol = getKey(input[i]);
        if (symbol != '@') pressKey(symbol);
        
      }
    }

    Serial.print("END");
  }
  
}
