package com.lin.service;
import java.util.*;

import org.springframework.stereotype.Service;

@Service
public class mainService {
    public static Stack output;
    public static Set<String> set = new HashSet<>();
    public static Object[] objects = null;
    public static Map<Object, Integer> map = null;

    public static void main(String[] args) {
        //测试用例
        String str = "﹁p∨r∧a∨s";
        System.out.println("输入：" + str);
        //逆波兰
        Stack<Character> operators = new Stack<>(); //运算符
        output = new Stack(); //输出结果
        rpn(operators, output, str);
        System.out.println(output);

        //全排列
        objects = set.toArray();
        for (Object o : objects) {
            System.out.print(o + "\t");
        }
        System.out.println("结果");
        map = new HashMap<>();
        translation(objects, map, 0);
    }

    //全排列
    public static void translation(Object[] objects, Map<Object, Integer> map, int ind) {
        if(ind == objects.length) {
            allNum(map);
            return;
        }
        if (map.containsKey(objects[ind])) {
            translation(objects, map, ind + 1);
        } else {
            for(int i = 0; i < 2; i++){
                map.put(objects[ind], i);
                translation(objects, map, ind + 1);
                map.remove(objects[ind]);
            }
        }
    }

    //计算值
    public static void allNum(Map<Object, Integer> map) {
        Stack<Integer> stack = new Stack();
        Stack out = (Stack) output.clone();
        for (Object o : objects) {
            System.out.print(map.get(o) + "\t");
        }
        while (!out.empty()) {
            String x = String.valueOf(out.get(0));
            if(!Operator.isOperator(x.charAt(0))){
                stack.push(map.get(out.get(0)));
            } else {
                int a = stack.pop();
                if(x.charAt(0) == '﹁'){
                    stack.push(a == 0 ? 1 : 0);
                } else {
                    int b = stack.pop();
                    if(x.charAt(0) == '↔') {
                        stack.push(a == b ? 1 : 0);
                    } else if(x.charAt(0) == '→') {
                        if(a == 0 && b == 1) stack.push(0);
                        else stack.push(1);
                    } else if(x.charAt(0) == '∧') {
                        stack.push( a&b);
                    } else if(x.charAt(0) == '∨') {
                        stack.push( a|b);
                    }
                }

            }
            out.remove(0);
        }
        System.out.println(stack.get(0));
    }

    public static void rpn(Stack<Character> operators, Stack output, String str) {
        char[] chars = str.toCharArray();
        int pre = 0;
        boolean digital; //是否为数字（只要不是运算符，都是数字），用于截取字符串
        int len = chars.length;
        int bracket = 0; // 左括号的数量

        for (int i = 0; i < len; ) {
            pre = i;
            digital = Boolean.FALSE;
            //截取数字
            while (i < len && !Operator.isOperator(chars[i])) {
                i++;
                digital = Boolean.TRUE;
            }

            if (digital) {
                output.push(str.substring(pre, i));
                set.add(str.substring(pre, i));
            } else {
                char o = chars[i++]; //运算符
                if (o == '(') {
                    bracket++;
                }
                if (bracket > 0) {
                    if (o == ')') {
                        while (!operators.empty()) {
                            char top = operators.pop();
                            if (top == '(') {
                                break;
                            }
                            output.push(top);
                        }
                        bracket--;
                    } else {
                        //如果栈顶为 ( ，则直接添加，不顾其优先级
                        //如果之前有 ( ，但是 ( 不在栈顶，则需判断其优先级，如果优先级比栈顶的低，则依次出栈
                        while (!operators.empty() && operators.peek() != '(' && Operator.cmp(o, operators.peek()) <= 0) {
                            output.push(operators.pop());
                        }
                        operators.push(o);
                    }
                } else {
                    while (!operators.empty() && Operator.cmp(o, operators.peek()) <= 0) {
                        output.push(operators.pop());
                    }
                    operators.push(o);
                }
            }
        }
        //遍历结束，将运算符栈全部压入output
        while (!operators.empty()) {
            output.push(operators.pop());
        }
    }
}

enum Operator {
    EQUAL('↔', 1),
    BELONG('→', 2),
    OR('∨', 3),
    AND('∧', 4),
    NOT('﹁', 5),
    LEFT_BRACKET('(', 6), RIGHT_BRACKET(')', 6); //括号优先级最高
    char value;
    int priority;

    Operator(char value, int priority) {
        this.value = value;
        this.priority = priority;
    }

    /**
     * 比较两个符号的优先级
     *
     * @param c1
     * @param c2
     * @return c1的优先级是否比c2的高，高则返回正数，等于返回0，小于返回负数
     */
    public static int cmp(char c1, char c2) {
        int p1 = 0;
        int p2 = 0;
        for (Operator o : Operator.values()) {
            if (o.value == c1) {
                p1 = o.priority;
            }
            if (o.value == c2) {
                p2 = o.priority;
            }
        }
        return p1 - p2;
    }

    /**
     * 枚举出来的才视为运算符，用于扩展
     *
     * @param c
     * @return
     */
    public static boolean isOperator(char c) {
        for (Operator o : Operator.values()) {
            if (o.value == c) {
                return true;
            }
        }
        return false;
    }
}
