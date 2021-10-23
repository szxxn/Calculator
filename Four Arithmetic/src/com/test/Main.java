package com.test;

import com.pxk.Calculation;
import com.pxk.Expression;
import com.pxk.GenerateExpression;
import com.pxk.TxtIO;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int range = Integer.parseInt(args[0]);  // 取值范围
        List<Expression> expressionList = GenerateExpression.generateExpressionList(100, range, 3);

        // 先将需要写入文件的路径里面的内容清空
        TxtIO.clear("Exercises.txt");
        TxtIO.clear("Answers.txt");
        for (int i = 0; i < expressionList.size(); i++) {
            TxtIO.writeTxt(i+1 + "、 " + expressionList.get(i), "Exercises.txt");
            TxtIO.writeTxt(i+1 + "、 " + Calculation.getResult("" + expressionList.get(i)), "Answers.txt");
        }

        List<Integer> correct = new ArrayList<>();
        List<Integer> wrong = new ArrayList<>();
        List<String> exercisefileList = TxtIO.readTxt("Four Arithmetic/src/exercisefile.txt");
        List<String> answerfileList = TxtIO.readTxt("Four Arithmetic/src/answerfile.txt");
        for (int i = 0; i < exercisefileList.size(); i++) {
            if (Calculation.getResult(exercisefileList.get(i)) == Double.parseDouble(answerfileList.get(i))) {
                correct.add(i);
            } else {
                wrong.add(i);
            }
        }
        System.out.println("Correct:" + correct.size() + correct);
        System.out.println("Wrong:" + wrong.size() + wrong);
    }
}