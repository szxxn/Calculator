package com.pxk;

/**
 * @author pxk
 * <p>
 * 算式类
 * 用于拼接括号、分数和运算符
 */
public class Expression {
    /**
     * 运算符
     */
    private String[] operators;

    /**
     * 分数
     */
    private Fraction[] data;

    /**
     * 左括号位置
     */
    private int[] leftBrackets;

    /**
     * 右括号位置
     */
    private int[] rightBrackets;

    public String[] getOperators() {
        return operators;
    }

    public void setOperators(String[] operators) {
        this.operators = operators;
    }

    public Fraction[] getData() {
        return data;
    }

    public void setData(Fraction[] data) {
        this.data = data;
    }

    public int[] getLeftBrackets() {
        return leftBrackets;
    }

    public void setLeftBrackets(int[] leftBrackets) {
        this.leftBrackets = leftBrackets;
    }

    public int[] getRightBrackets() {
        return rightBrackets;
    }

    public void setRightBrackets(int[] rightBrackets) {
        this.rightBrackets = rightBrackets;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            //拼接数字
            for (int j = 0; j < leftBrackets[i]; j++) {
                stringBuilder.append("( ");
            }
            stringBuilder.append(data[i].toString()+" ");
            for (int j = 0; j < rightBrackets[i]; j++) {
                stringBuilder.append(") ");
            }
            if (i < operators.length) {
                stringBuilder.append(operators[i]+" ");
            }
        }
        return stringBuilder.toString();
    }
}
