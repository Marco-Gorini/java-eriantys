package it.polimi.ingsw.model.gamemodel;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bag {
    private final List<PawnColor> listOfStudentsInBag = new ArrayList<>();

    public List<PawnColor> getListOfStudentsInBag() {return listOfStudentsInBag;}

    /**
     * This method inizializate the bag in game, with 26 students for each color
     */
    public void initializateBag(){
        for(int i = 0; i < 26; i++){
            listOfStudentsInBag.add(PawnColor.RED);
        }
        for(int i = 0; i < 26; i++){
            listOfStudentsInBag.add(PawnColor.GREEN);
        }
        for(int i = 0; i < 26; i++){
            listOfStudentsInBag.add(PawnColor.YELLOW);
        }
        for(int i = 0; i < 26; i++){
            listOfStudentsInBag.add(PawnColor.BLUE);
        }
        for(int i = 0; i < 26; i++){
            listOfStudentsInBag.add(PawnColor.PINK);
        }
        Collections.shuffle(listOfStudentsInBag);
    }

    /**
     * This method extract the first student of the students list, that is an attribute of Bag
     * @return student: return the drawn student
     */

    public PawnColor drawStudent() {
        PawnColor student = listOfStudentsInBag.get(0);
        listOfStudentsInBag.remove(0);
        return student;
    }
}
