package model;

import model.Cell;

public class Shark {
    Cell myCell;
    int myAge = 0;
    int myEnergy = 3;


    public int getEnergy() {
        return myEnergy;
    }

    public void setEnergy(int myEnergy) {
        this.myEnergy = myEnergy;
    }



    public int getAge() {
        return myAge;
    }
    public void setCell(Cell cell) {
        this.myCell = cell;
    }

    public void setAge(int age){
        myAge = age;
    }


    public Cell getCell() {
        return myCell;
    }

    Shark(Cell cell){
        myCell = cell;
    }
}
