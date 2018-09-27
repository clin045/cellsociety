package model;

public class Shark {
    Cell myCell;
    int myAge = 0;
    int myEnergy = 3;


    Shark(Cell cell) {
        myCell = cell;
    }

    public int getEnergy() {
        return myEnergy;
    }

    public void setEnergy(int myEnergy) {
        this.myEnergy = myEnergy;
    }

    public int getAge() {
        return myAge;
    }

    public void setAge(int age) {
        myAge = age;
    }


    public Cell getCell() {
        return myCell;
    }

    public void setCell(Cell cell) {
        this.myCell = cell;
    }
}
