// @Time    : 2018/4/7 18:20
// @Author  : Zhengxin Tang 28453093
// @Mail    : ztan0030@student.monash.edu
// @File    : Server.java
// @Software: IntelliJ IDEA
// @LastModi: 2018/4/15 15:21
// @Instructions :  This file contains a dictionary for server site as well as some default words for query. Methods for
//                  query, delete, add, update is defined.

package com.bryan;

import java.util.HashMap;

public class Dictionary {
    private HashMap<String, String> map;
    //The dictionary that is used in server site.
    public Dictionary(){
        map = new HashMap<>();
        map.put("exchange","The act of changing one thing for another thing");
        map.put("heteroskedasticity","The circumstance in which the variability of a variable is unequal across the range of values of a second variable that predicts it.");
        map.put("apple","Fruit with red or yellow or green skin and sweet to tart crisp whitish flesh");
        map.put("multicollinearity","A phenomenon in which one predictor variable in a multiple regression model can be linearly predicted from the others with a substantial degree of accuracy.");
        map.put("Monash","I love Monash!");
    }

    public String getContent(String word) {
        return this.map.get(word);
    }

    public void addWord(String word, String content) {
        this.map.put(word, content);
    }

    public void deleteWord(String word) {
        this.map.remove(word);
    }

    public void updateWord(String word, String content) {
        this.map.put(word, content);
    }

    public Boolean ifExist(String word) {
        return this.map.containsKey(word);
    }
}
