package com.pxk;

import java.util.*;

/**
 * @author pxk
 * <p>
 * 生成计算式
 */
public class GenerateExpression {

    /**
     * @param size               计算式的个数
     * @param range              数字的取值上限
     * @param operatorUpperLimit 运算符个数的上限
     */
    public static List<Expression> generateExpressionList(int size, int range, int operatorUpperLimit) {
        List<Expression> expressionList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            expressionList.add(i, generateExpression(range, operatorUpperLimit));
        }
        System.out.println(size + " 条算式已经成功生成，并且写入 Exercises.txt 文件！");
        return expressionList;
    }

    public static Expression generateExpression(int range, int operatorUpperLimit) {
        Expression expression = new Expression();

        String[] operators = {"+", "-", "×", "÷"};
        Random random = new Random();
        // 每条计算式实际生成的运算符个数
        int actualOperationCounts = 0;
        while (actualOperationCounts == 0) {
            actualOperationCounts = random.nextInt(operatorUpperLimit + 1);
        }
        // 每条计算式中分数的个数
        int dataCounts = actualOperationCounts + 1;

        expression.setData(new Fraction[dataCounts]);
        expression.setOperators(new String[actualOperationCounts]);
        expression.setLeftBrackets(new int[dataCounts]);
        expression.setRightBrackets(new int[dataCounts]);

        /*
            不知道为何，将第一个数字和运算符一起生成时，使用expression.getOperators()[i - 1] == '÷' && fraction.getValue() == 0无法避免除于0的情况
            所以将第一个数字先生成，在生成其他的数字和运算符
         */
        int i = 0;
        Fraction fraction = new Fraction(range);
        while (fraction.getValue() == 0) {
            fraction = new Fraction(range);
        }
        expression.getData()[i] = fraction;
        for (i = 1; i < dataCounts; i++) {
            //生成操作符号
            String operator = operators[random.nextInt(4)];
            expression.getOperators()[i - 1] = operator;
            fraction = new Fraction(range);
            while ((operator.equals("÷") && fraction.getValue() == 0)
                    || ((operator.equals("-") && ((expression.getData()[i - 1].getValue() - fraction.getValue()) < 0) || (expression.getData()[i - 1].getValue() - fraction.getValue()) == 0))
//                    || (operator.equals("-") && (expression.getData()[i - 1].getValue() - fraction.getValue()) == 0)
                    || (operator.equals("×") && fraction.getValue() == 0)) {
                fraction = new Fraction(range);
            }
            expression.getData()[i] = fraction;
        }

        // 以下将括号的位置记录下来
        // 假设运算符3，最多3个括号，所以应该是实际运算符个数+1 [0,4)
        Set<String> indexSet = new TreeSet<>();
        int bracketCounts = random.nextInt(actualOperationCounts + 1);  // 括号个数
        while (bracketCounts > 0) {
            int leftIndex = random.nextInt(actualOperationCounts);
            int rightIndex = random.nextInt(actualOperationCounts - leftIndex + 1) + leftIndex;
            if (leftIndex == rightIndex) {
                continue;
            }
            indexSet.add(leftIndex + ":" + rightIndex);
            bracketCounts--;
        }
        indexSet.remove("0:" + actualOperationCounts); // 移除最外层括号（无效括号）
        for (String temp : indexSet) {
            String[] index = temp.split(":");
            expression.getLeftBrackets()[Integer.parseInt(index[0])]++;
            expression.getRightBrackets()[Integer.parseInt(index[1])]++;
        }

        //优化重复括号和开头末尾括号
        //循环遍历左括号，除去(((Number)) + )的情况
        for (i = 0; i < actualOperationCounts; i++) {
            if (expression.getRightBrackets()[i] > 0) {
                int num = Math.min(expression.getLeftBrackets()[i], expression.getRightBrackets()[i]);
                expression.getLeftBrackets()[i] -= num;
                expression.getRightBrackets()[i] -= num;
            }
        }

//        System.out.println(expression);
        return expression;
    }

}
