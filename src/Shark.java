public class Shark {
    Cell myCell;
    int myAge;

    public int getMyAge() {
        return myAge;
    }

    public Cell getCell() {
        return myCell;
    }

    public void setMyAge(int myAge) {
        this.myAge = myAge;
    }

    Shark(Cell cell, int age){
        myCell = cell;
        myAge = age;
    }
}
