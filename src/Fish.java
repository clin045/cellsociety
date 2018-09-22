public class Fish {


    Cell myCell;
    int myAge;

    public int getAge() {
        return myAge;
    }
    public void setCell(Cell cell) {
        this.myCell = cell;
    }

    public void incAge(){
        myAge++;
    }


    public Cell getCell() {
        return myCell;
    }

    Fish(Cell cell, int age){
        myCell = cell;
        myAge = age;
    }
}
